package com.yearup.dealership.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InventoryDao {
    private DataSource dataSource;

    public InventoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addVehicleToInventory(String vin, int dealershipId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO inventory (dealership_id, Vin) values (?, ?)")){
            preparedStatement.setInt(1, dealershipId);
            preparedStatement.setString(2, vin);

            int rows = preparedStatement.executeUpdate();
            System.out.println("Rows Inserted: " + rows);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void removeVehicleFromInventory(String vin) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM Inventory WHERE vin = ?")){
            preparedStatement.setString(1, vin);

            preparedStatement.executeUpdate();

        }  catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
