package com.gh.example.shopping.controllers;

import com.gh.example.shopping.entities.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class ProductRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<Long> getIds() {
        return jdbcTemplate.queryForList("SELECT id FROM product", Long.class);
    }

    private final RowMapper<Product> productRowMapper =
            (resultSet, rowNum) ->  new Product(resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getBigDecimal("price"));


    private Product getProduct(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM product WHERE id = ?",
                (rs, rowId) -> new Product(rs.getLong("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price")),
                id);
    }

    private Product getProduct2(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM product WHERE id = ?",
                productRowMapper,
                id);
    }

    @Test
    void getAllProducts() {
        List<Long> productIds = getIds();
        System.out.println("There are " + productIds.size() + " products in the database");
        webTestClient.get()
                .uri("/products")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Product.class).hasSize(3)
                .consumeWith(System.out::println);
    }

    @ParameterizedTest(name = "Product ID: {0}")
    @MethodSource("getIds")
    void getProductsThatExists(Long productId) {
        webTestClient.get()
                .uri("/products/{id}", productId)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(productId);
    }

    @Test
    void getProductThatDoesNotExists() {
        List<Long> productIds = getIds();
        assertThat(productIds).doesNotContain(999L);
        System.out.println("There are " + productIds.size() + " products in the database");
        webTestClient.get()
                .uri("/products/999")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void insertProduct() {
        List<Long> productIds = getIds();
        assertThat(productIds).doesNotContain(999L);
        System.out.println("There are " + productIds.size() + " products in the database");
        Product product = new Product("Chair", BigDecimal.valueOf(149.99));

        webTestClient.post()
                .uri("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.name").isEqualTo("Chair")
                .jsonPath("$.price").isEqualTo(BigDecimal.valueOf(149.99));
    }

    @Test
    void updateProduct() {
        Product product = getProduct(getIds().get(0));
        product.setPrice(product.getPrice().add(BigDecimal.ONE));

        webTestClient.put()
                .uri("/products/{id}", product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(product), Product.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println);
    }

    @Test
    void deleteSingleProduct() {
        List<Long> productIds = getIds();
        System.out.println("There are " + productIds.size() + " products in the database");
        if (productIds.isEmpty()) {
            System.out.println("Nothing to delete");
            return;
        }

        // given:
        webTestClient.get()
                .uri("/products/{id}", productIds.get(0))
                .exchange()
                .expectStatus().isOk();

        //when:
        webTestClient.delete()
                .uri("/products/{id}", productIds.get(0))
                .exchange()
                .expectStatus().isNoContent();

        //then:
        webTestClient.get()
                .uri("/products/{id}", productIds.get(0))
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    void deleteAllProducts() {
        List<Long> productIds = getIds();
        System.out.println("There are " + productIds.size() + " products in the database");
        if (productIds.isEmpty()) {
            System.out.println("Nothing to delete");
            return;
        }

        //when:
        webTestClient.delete()
                .uri("/products")
                .exchange()
                .expectStatus().isNoContent();


        //then:
        webTestClient.get()
                .uri("/products")
                .exchange()
                .expectBodyList(Product.class).hasSize(0);

    }
}