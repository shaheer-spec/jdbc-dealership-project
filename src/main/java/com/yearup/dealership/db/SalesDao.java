package com.yearup.dealership.db;

import com.yearup.dealership.models.SalesContract;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.SQLException;

public class SalesDao {
    private DataSource dataSource;

    public SalesDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addSalesContract(SalesContract salesContract) {
        String salesContractQuery = """
            INSERT INTO sales_contracts (VIN, sale_date, price)
            VALUES (?, ?, ?)
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(salesContractQuery, Statement.RETURN_GENERATED_KEYS)) {

            Date saleDate = Date.valueOf(salesContract.getSaleDate());

            preparedStatement.setString(1, salesContract.getVin());
            preparedStatement.setDate(2, saleDate);
            preparedStatement.setDouble(3, salesContract.getPrice());

            preparedStatement.executeUpdate();

            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    System.out.println("A new key was added: " + keys.getInt(1));
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
