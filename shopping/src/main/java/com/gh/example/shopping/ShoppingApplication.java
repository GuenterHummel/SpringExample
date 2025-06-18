package com.gh.example.shopping;

import com.gh.example.shopping.dao.ProductRepository;
import com.gh.example.shopping.entities.Product;
import com.gh.example.shopping.services.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class ShoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingApplication.class, args);
    }

    @Bean
    public CommandLineRunner initializeDatabase(ProductService productService) {
        return args  -> productService.initializeDatabase();
    }
}
