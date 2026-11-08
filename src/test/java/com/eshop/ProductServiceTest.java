package com.eshop;

import com.eshop.product.Product;
import com.eshop.product.ProductRepository;
import com.eshop.product.ProductService;
import com.eshop.utils.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.springframework.data.jpa.domain.Specification;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository repository;
    @InjectMocks
    ProductService service;

    @Test
    void get_throwsWhenProductMissing() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.get(99L));
    }

    @Test
    void list_usesSpecification_whenFiltersGiven() {
        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findAll(ArgumentMatchers.<Specification<Product>>any(), eq(pageable)))
                .thenReturn(Page.empty());

        service.list("phone", 1L, pageable);

        verify(repository).findAll(ArgumentMatchers.<Specification<Product>>any(), eq(pageable));
    }
}
