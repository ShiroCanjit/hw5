package hw5.dao;

import hw5.entity.Organization;
import hw5.entity.Waybill_items;
import initialize.DAO;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class Waybill_itemsDAO implements DAO<Waybill_items> {
    private final @NotNull Connection connection;

    public Waybill_itemsDAO(@NotNull Connection connection) {
        this.connection = connection;
    }

    @Override
    public @NotNull Waybill_items get(int id) {
        try (var statement = connection.createStatement()) {
            try (var resultSet = statement.executeQuery("SELECT id, waybill, price, product, number FROM waybill_items WHERE id = " + id)) {
                if (resultSet.next()) {
                    return new Waybill_items(resultSet.getInt("id"), resultSet.getInt("waybill"),resultSet.getInt("price"), resultSet.getInt("product"), resultSet.getInt("number"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        throw new IllegalStateException("Record with id " + id + "not found");
    }

    @Override
    public @NotNull List<Waybill_items> all() {
        final List<Waybill_items> result = new ArrayList<>();
        try (var statement = connection.createStatement()) {
            try (var resultSet = statement.executeQuery("SELECT * FROM waybill_items")) {
                while (resultSet.next()) {
                    result.add(new Waybill_items(resultSet.getInt("id"), resultSet.getInt("waybill"),resultSet.getInt("price"), resultSet.getInt("product"), resultSet.getInt("number")));
                }
                return result;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void save(@NotNull Waybill_items entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO waybill_items (id,waybill,price,product,number) VALUES(?,?,?,?,?)")) {
            int fieldIndex = 1;
            preparedStatement.setInt(fieldIndex++, entity.getId());
            preparedStatement.setInt(fieldIndex++, entity.getWaybill());
            preparedStatement.setInt(fieldIndex++, entity.getPrice());
            preparedStatement.setInt(fieldIndex++, entity.getProduct());
            preparedStatement.setInt(fieldIndex, entity.getNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void update(@NotNull Waybill_items entity) {
       try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE waybill_items SET waybill = ?, price = ?, product = ?, number = ? WHERE id = ?")) {
            int fieldIndex = 1;
            preparedStatement.setInt(fieldIndex++, entity.getWaybill());
            preparedStatement.setInt(fieldIndex++, entity.getPrice());
            preparedStatement.setInt(fieldIndex++, entity.getProduct());
            preparedStatement.setInt(fieldIndex++, entity.getNumber());
            preparedStatement.setInt(fieldIndex, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void delete(@NotNull Waybill_items entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM waybill_items WHERE id = ?")) {
            preparedStatement.setInt(1, entity.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with id = " + entity.getId() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
