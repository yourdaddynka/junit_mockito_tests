package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class UsersServiceImplTest {

    private static DataSource db;

    @BeforeEach
    public void init() {
        db = new EmbeddedDatabaseBuilder().
                generateUniqueName(true).
                setType(EmbeddedDatabaseType.HSQL).
                setScriptEncoding("UTF-8").
                ignoreFailedDrops(true).
                addScript("schema.sql").
                addScripts("data.sql").
                build();
    }

    final List<User> EXPECTED_ALL_USERS_THROW = new ArrayList<>(Arrays.asList(
            new User(1, "john_doe", "qwerty123", true),
            new User(3, "mike_jones", "password123", true),
            new User(5, "alex_wilson", "123456", true)
    ));
    final List<User> EXPECTED_ALL_USERS_TRUE = new ArrayList<>(Arrays.asList(
            new User(2, "jane_smith", "abc456", false),
            new User(4, "sarah_davis", "letmein", false)
    ));

    @Test
    public void ExTRUE() {
        UsersServiceImpl usersService = new UsersServiceImpl(db);
        for (int i = 0; i < EXPECTED_ALL_USERS_TRUE.size(); i++) {
//            User user = EXPECTED_ALL_USERS_TRUE.get(i);
            Assertions.assertTrue(usersService.authenticate(EXPECTED_ALL_USERS_TRUE.get(i).getLogin(), EXPECTED_ALL_USERS_TRUE.get(i).getPassword()));
        }
    }

    @Test
    public void ExTHROW() {
        UsersServiceImpl usersService = new UsersServiceImpl(db);
        Assertions.assertThrows(AlreadyAuthenticatedException.class, () -> {
            for (int i = 0; i < EXPECTED_ALL_USERS_THROW.size(); i++) {
                usersService.authenticate(EXPECTED_ALL_USERS_THROW.get(i).getLogin(), EXPECTED_ALL_USERS_THROW.get(i).getPassword());
            }
        });
    }

}

