package com.eshop.product;

import com.eshop.utils.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductResponse> list(String name, Long categoryId, Pageable pageable) {
        Specification<Product> spec = (root, query, cb) -> cb.conjunction(); // matches everything

        if (name != null && !name.isBlank()) {
            spec = spec.and(ProductSpecs.nameContains(name.trim()));
        }
        if (categoryId != null) {
            spec = spec.and(ProductSpecs.inCategory(categoryId));
        }
        return repository.findAll(spec, pageable).map(ProductResponse::from);
    }

    @Transactional(readOnly = true)
    public ProductResponse get(Long id) {
        return ProductResponse.from(findOrThrow(id));
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        Product product = new Product();
        apply(product, request);
        return ProductResponse.from(repository.save(product));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = findOrThrow(id);
        apply(product, request);
        return ProductResponse.from(product); // no save() needed, see note below
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    private Product findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }

    private void apply(Product product, ProductRequest r) {
        product.setName(r.name());
        product.setDescription(r.description());
        product.setPrice(r.price());
        product.setStock(r.stock());
    }
}