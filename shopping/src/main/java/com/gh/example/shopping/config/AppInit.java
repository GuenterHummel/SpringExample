package com.gh.example.shopping.config;

import com.gh.example.shopping.dao.ProductRepository;
import com.gh.example.shopping.entities.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AppInit implements CommandLineRunner {
    private ProductRepository productRepository;

    public AppInit(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(productRepository.count()==0){
            productRepository.saveAll(
                List.of(
                        new Product("TV tray", new BigDecimal(4.95)),
                        new Product("Toaster", new BigDecimal(19.95)),
                        new Product("Sofa", new BigDecimal(249.95))
                )
            );
        }
    }
}
