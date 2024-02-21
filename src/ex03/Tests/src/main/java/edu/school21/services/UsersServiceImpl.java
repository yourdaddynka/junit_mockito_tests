package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

import javax.sql.DataSource;
import java.sql.*;

public class UsersServiceImpl implements UsersRepository {
    private DataSource db;
    private static final String SQLfindByLogin = "SELECT * FROM users WHERE users.login = ";
    private static final String SQLupdate = "UPDATE users SET login = ? ,password = ?, successstatus = ? WHERE users.id = ?";

    public UsersServiceImpl(DataSource db) {
        this.db = db;
    }

    @Override
    public User findByLogin(String login) {
        try (Connection connection = db.getConnection()) {
            ResultSet resSet = connection.createStatement().executeQuery(SQLfindByLogin + "'" +login+ "'");
            if (resSet.next()) {
                return new User(
                        resSet.getInt("id"),
                        resSet.getString("login"),
                        resSet.getString("password"),
                        resSet.getBoolean("successstatus")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        throw new EntityNotFoundException("Человек не найден");
    }

    @Override
    public void update(User user) {
        try {
            try (Connection connection = db.getConnection()) {
                PreparedStatement prepareStatement = connection.prepareStatement(SQLupdate);
                prepareStatement.setString(1, user.getLogin());
                prepareStatement.setString(2, user.getPassword());
                prepareStatement.setBoolean(3, user.isSuccessStatus());
                prepareStatement.setInt(4, user.getId());
                prepareStatement.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean authenticate(String login, String password) {
            User checkUser = findByLogin(login);
            if (checkUser.getPassword().equals(password)) {
                if (checkUser.isSuccessStatus()) {
                    throw new AlreadyAuthenticatedException("человек уже авторизован");
                } else {
                    checkUser.setSuccessStatus(true);
                    update(checkUser);
                    return checkUser.isSuccessStatus();
                }
            }
            return checkUser.isSuccessStatus();
        }
}
