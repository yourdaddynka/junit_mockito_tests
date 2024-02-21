package edu.school21.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import javax.sql.DataSource;

import java.sql.SQLException;


public class EmbeddedDataSourceTest {
    private static DataSource dbBuilder;
    @BeforeEach
    public void init() {
        dbBuilder = new EmbeddedDatabaseBuilder().
                generateUniqueName(true).
                setType(EmbeddedDatabaseType.HSQL).
                setScriptEncoding("UTF-8").
                ignoreFailedDrops(true).
                addScript("schema.sql").
                addScripts("data.sql").
                build();
    }

    @Test
    public void getConnection() throws SQLException {
            Assertions.assertNotNull(dbBuilder.getConnection());
    }

}
