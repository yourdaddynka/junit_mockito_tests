package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImplTest {
    private static DataSource db;
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = new ArrayList<>(Arrays.asList(
            new Product(1, "Product A", 10.99),
            new Product(2, "Product B", 15.49),
            new Product(3, "Product C", 20.99),
            new Product(4, "Product D", 7.99),
            new Product(5, "Product E", 12.99)
    ));
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(3, "Product C", 20.99);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(3, "Product Test", 10.99);

    final Product EXPECTED_SAVE_PRODUCT = new Product(6, "Product F", 9.99);

    @BeforeEach
    public void init() {
        db = new EmbeddedDatabaseBuilder().
                generateUniqueName(true).
                setType(EmbeddedDatabaseType.HSQL).
                ignoreFailedDrops(true).
                addScript("schema.sql").
                addScripts("data.sql").
                build();
    }

    @Test
    public void findAllEqualsTest() {
        ProductsRepositoryJdbcImpl repositoryJdbc = new ProductsRepositoryJdbcImpl(db);
        List<Product> productList = repositoryJdbc.findAll();
        Assertions.assertEquals(productList, EXPECTED_FIND_ALL_PRODUCTS);
    }

    @Test
    public void findByIdEqualsTest() {
        ProductsRepositoryJdbcImpl prodRepJdbc = new ProductsRepositoryJdbcImpl(db);
        Optional<Product> optionalProduct = prodRepJdbc.findById(3L);
        optionalProduct.ifPresent(product -> Assertions.assertEquals(product, EXPECTED_FIND_BY_ID_PRODUCT));
    }

    @Test
    public void updateEqualsTest() {
        ProductsRepositoryJdbcImpl prodRepJdbc = new ProductsRepositoryJdbcImpl(db);
        prodRepJdbc.update(EXPECTED_FIND_BY_ID_PRODUCT);
        Optional<Product> productOptional = prodRepJdbc.findById(3L);
        productOptional.ifPresent(product -> Assertions.assertEquals(product, EXPECTED_UPDATED_PRODUCT));
    }

    @Test
    public void saveEqualsTest() {
        ProductsRepositoryJdbcImpl prodRepJdbc = new ProductsRepositoryJdbcImpl(db);
        prodRepJdbc.save(new Product(6, "Product F", 9.99));
        Optional<Product> productOptional = prodRepJdbc.findById(6L);
        productOptional.ifPresent(product -> Assertions.assertEquals(product, EXPECTED_SAVE_PRODUCT));
    }

    @Test
    public void deleteEqualsTest() {
        ProductsRepositoryJdbcImpl prodRepJdbc = new ProductsRepositoryJdbcImpl(db);
        prodRepJdbc.delete(3L);
        Optional<Product> optionalProduct = prodRepJdbc.findById(3L);
        Assertions.assertFalse(optionalProduct.isPresent());
    }
}
