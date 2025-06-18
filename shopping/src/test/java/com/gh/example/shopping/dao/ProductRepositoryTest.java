package com.gh.example.shopping.dao;

import com.gh.example.shopping.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductRepositoryTest {
    @Autowired
    private ProductRepository repository;

    @Test
    void countProducts() {
        assertEquals(3, repository.count());
    }

    @Test
    void findById() {
        assertTrue(repository.findById(1L).isPresent());
    }

    @Test
    void findAll() {
        repository.findAll().forEach(System.out::println);
        assertEquals(3, repository.findAll().size());
    }

    @Test
    void insertProduct() {
        Product product = new Product("cricket bat", BigDecimal.valueOf(35.00));
        repository.save(product);
        assertAll(
                () -> assertNotNull(product.getId()),
                () -> assertEquals(4, repository.count())
        );
    }

    @Test
    void deleteProduct() {
        Optional<Product> product = repository.findById(1L);
        if (product.isPresent()) {
            repository.delete(product.get());
        }
        assertEquals(2, repository.count());
    }

    @Test
    void deleteProduct2() {
        repository.deleteById(2L);
        assertEquals(2, repository.count());
    }

    @Test
    void deleteAllInBatch() {
        repository.deleteAllInBatch();
        assertEquals(0, repository.count());
    }

    @Test
    void deleteAllProducts() {
        repository.deleteAll();
        assertEquals(0, repository.count());
    }
}