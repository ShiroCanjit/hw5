package hw5.dao;

import hw5.entity.Waybill;
import initialize.DAO;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class WaybillDAO implements DAO<Waybill> {
    private final @NotNull Connection connection;

    public WaybillDAO(@NotNull Connection connection) {
        this.connection = connection;
    }

    @Override
    public @NotNull Waybill get(int id) {
        try (var statement = connection.createStatement()) {
            try (var resultSet = statement.executeQuery("SELECT id, number, date, sender FROM waybill WHERE id = " + id)) {
                if (resultSet.next()) {
                    return new Waybill(resultSet.getInt("id"), resultSet.getInt("number"),resultSet.getTimestamp("date"), resultSet.getInt("sender"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        throw new IllegalStateException("Record with id " + id + "not found");
    }

    @Override
    public @NotNull List<Waybill> all() {
        final List<Waybill> result = new ArrayList<>();
        try (var statement = connection.createStatement()) {
            try (var resultSet = statement.executeQuery("SELECT * FROM waybill")) {
                while (resultSet.next()) {
                    result.add(new Waybill(resultSet.getInt("id"), resultSet.getInt("number"), resultSet.getTimestamp("date"), resultSet.getInt("sender")));
                }
                return result;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void save(@NotNull Waybill entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO waybill (id,number,date,sender) VALUES(?,?,?,?)")) {
            int fieldIndex = 1;
            preparedStatement.setInt(fieldIndex++, entity.getId());
            preparedStatement.setInt(fieldIndex++, entity.getNumber());
            preparedStatement.setTimestamp(fieldIndex++, entity.getDate());
            preparedStatement.setInt(fieldIndex, entity.getSender());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void update(@NotNull Waybill entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE waybill SET number = ?, date = ?, sender = ? WHERE id = ?")) {
            int fieldIndex = 1;
            preparedStatement.setInt(fieldIndex++, entity.getNumber());
            preparedStatement.setTimestamp(fieldIndex++, entity.getDate());
            preparedStatement.setInt(fieldIndex++, entity.getSender());
            preparedStatement.setInt(fieldIndex, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void delete(@NotNull Waybill entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM waybill WHERE id = ?")) {
            preparedStatement.setInt(1, entity.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with id = " + entity.getId() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

}
