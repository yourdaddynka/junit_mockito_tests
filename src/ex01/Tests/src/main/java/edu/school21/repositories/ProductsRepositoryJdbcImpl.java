package edu.school21.repositories;

import edu.school21.models.Product;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private DataSource db;
    private static final String SQLfindAll = "SELECT * FROM product";
    private static final String SQLfindById = "SELECT * FROM product WHERE = ";
    private static final String SQLdelete ="DELETE FROM product WHERE product.id = ";
    public void seDataSource(DataSource db) {
        this.db = db;
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = db.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQLfindAll);
            while (resultSet.next()) {
                productList.add(new Product(resultSet.getInt("id"), resultSet.getString("names"), resultSet.getDouble("price")));
            }
//            return productList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return productList;
    }

    @Override
    public Optional<Product> findById(Long id) {
        try (ResultSet resultSet = db.getConnection().createStatement().executeQuery(SQLfindById + id)) {
            Product temp = new Product(resultSet.getInt("id"), resultSet.getString("names"), resultSet.getDouble("price"));
            return Optional.of(temp);
        }
        catch (SQLException e) {System.out.println(e.getMessage());}
        return Optional.empty();
    }

    @Override
    public void update(Product productNew) {
        try (Connection connection = db.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE product SET id = ? ,names = '?', price = ? WHERE product.id = ?");
            preparedStatement.setInt(1, productNew.getId());
            preparedStatement.setString(2, productNew.getName());
            preparedStatement.setDouble(3, productNew.getPrice());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {System.out.println(e.getMessage());}
    }

    @Override
    public void save(Product productNew) {
        try(Connection connection = db.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO product VALUES (?,?,?)");
            preparedStatement.setInt(1,productNew.getId());
            preparedStatement.setString(2, productNew.getName());
            preparedStatement.setDouble(3, productNew.getPrice());
        }catch (SQLException e) {System.out.println(e.getMessage());}
    }

    @Override
    public void delete(Long id) {
        try(Connection connection = db.getConnection()){
            Statement statement = connection.createStatement();
            statement.executeUpdate(SQLdelete + id);
        }catch (SQLException e) {System.out.println(e.getMessage());}
    }
}
