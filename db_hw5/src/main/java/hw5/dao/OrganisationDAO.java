package hw5.dao;

import hw5.entity.Organization;
import hw5.entity.Product;
import initialize.DAO;
import org.apache.tools.ant.taskdefs.condition.Or;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class OrganisationDAO implements DAO<Organization> {
    private final @NotNull Connection connection;

    public OrganisationDAO(@NotNull Connection connection) {
        this.connection = connection;
    }

    @Override
    public @NotNull Organization get(int id) {
        try (var statement = connection.createStatement()) {
            try (var resultSet = statement.executeQuery("SELECT id, name, inn, account FROM organisation WHERE id = " + id)) {
                if (resultSet.next()) {
                    return new Organization(resultSet.getInt("id"), resultSet.getString("name"),resultSet.getInt("inn"), resultSet.getInt("account"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        throw new IllegalStateException("Record with id " + id + "not found");
    }

    @Override
    public @NotNull List<Organization> all() {
        final List<Organization> result = new ArrayList<>();
        try (var statement = connection.createStatement()) {
            try (var resultSet = statement.executeQuery("SELECT * FROM organization")) {
                while (resultSet.next()) {
                    result.add(new Organization(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("inn"), resultSet.getInt("account")));
                }
                return result;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public void save(@NotNull Organization entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO organization (id,name,inn,account) VALUES(?,?,?,?)")) {
            int fieldIndex = 1;
            preparedStatement.setInt(fieldIndex++, entity.getId());
            preparedStatement.setString(fieldIndex++, entity.getName());
            preparedStatement.setInt(fieldIndex++, entity.getInn());
            preparedStatement.setInt(fieldIndex, entity.getAccount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void update(@NotNull Organization entity) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE organization SET name = ?, inn = ?, account = ? WHERE id = ?")) {
            int fieldIndex = 1;
            preparedStatement.setString(fieldIndex++, entity.getName());
            preparedStatement.setInt(fieldIndex++, entity.getInn());
            preparedStatement.setInt(fieldIndex++, entity.getAccount());
            preparedStatement.setInt(fieldIndex, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void delete(@NotNull Organization entity) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM organization WHERE id = ?")) {
            preparedStatement.setInt(1, entity.getId());
            if (preparedStatement.executeUpdate() == 0) {
                throw new IllegalStateException("Record with id = " + entity.getId() + " not found");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
