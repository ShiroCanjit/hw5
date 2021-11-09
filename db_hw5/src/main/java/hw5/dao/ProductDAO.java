package hw5.dao;

import hw5.entity.Product;
import hw5.entity.Waybill;
import initialize.DAO;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class ProductDAO implements DAO<Product> {
    private final @NotNull Connection connection;

    public ProductDAO(@NotNull Connection connection) {
        this.connection = connection;
    }

    @Override
    public @NotNull Product get(int id) {
        try (var statement = connection.createStatement()) {
            try (var resultSet = statement.executeQuery("SELECT id, name, code FROM product WHERE id = " + id)) {
                if (resultSet.next()) {
                    return new Product(resultSet.getInt("id"), resultSet.getString("name"),resultSet.getInt("code"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        throw new IllegalStateException("Record with id " + id + "not found");
    }

    @Override
    public @NotNull List<Product> all() {
        final List<Product> result = new ArrayList<>();
        try (var statement = connection.createStatement()) {
            try (var resultSet = statement.executeQuery("SELECT * FROM product")) {
                while (resultSet.next()) {
                    result.add(new Product(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("code")));
                }
                return result;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void save(@NotNull Product entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO product (id,name,code) VALUES(?,?,?)")) {
            int fieldIndex = 1;
            preparedStatement.setInt(fieldIndex++, entity.getId());
            preparedStatement.setString(fieldIndex++, entity.getName());
            preparedStatement.setInt(fieldIndex, entity.getCode());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void update(@NotNull Product entity) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE product SET name = ?, code = ? WHERE id = ?")) {
            int fieldIndex = 1;
            preparedStatement.setString(fieldIndex++, entity.getName());
            preparedStatement.setInt(fieldIndex++, entity.getCode());
            preparedStatement.setInt(fieldIndex, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void delete(@NotNull Product entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM product WHERE id = ?")) {
            preparedStatement.setInt(1, entity.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with id = " + entity.getId() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}
