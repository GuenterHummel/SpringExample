package com.gh.example.shopping.services;

// Traditional three-layer architecture for Java apps:
// - Presentation Layer (controllers and views)
// - Service Layer (business logic and transaction boundaries)
// - Persistence lacer (convert entities to tables and rows)
// DB

import com.gh.example.shopping.dao.ProductRepository;
import com.gh.example.shopping.entities.Product;
import jakarta.transaction.TransactionScoped;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    private ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public void initializeDatabase() {
        if (repository.count() == 0){
            repository.saveAll(
                    List.of(
                            new Product("TV tray", BigDecimal.valueOf(4.95)),
                            new Product("Toaster",  BigDecimal.valueOf(19.95)),
                            new Product("Sofa", BigDecimal.valueOf(249.95))
                    )
            ).forEach(System.out::println);
        }
    }

    public List<Product> findAllProducts() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Product> findProductById(Long id) {
        return repository.findById(id);
    }

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public void deleteProduct(Product product) {
        repository.delete(product);
    }

    public void deleteAllProducts() {
        repository.deleteAllInBatch();
    }

}
