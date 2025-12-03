package com.yearup.dealership.db;

import com.yearup.dealership.models.LeaseContract;

import javax.sql.DataSource;
import java.sql.*;

public class LeaseDao {
    private DataSource dataSource;

    public LeaseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addLeaseContract(LeaseContract leaseContract) {
        String leaseContractQuery = """
                INSERT INTO lease_contracts (VIN, lease_start, lease_end, monthly_payment)
                VALUES (?, ?, ?, ?)""";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(leaseContractQuery, Statement.RETURN_GENERATED_KEYS)){

            Date startDate = Date.valueOf(leaseContract.getLeaseStart());
            Date endDate = Date.valueOf(leaseContract.getLeaseEnd());

            preparedStatement.setString(1, leaseContract.getVin());
            preparedStatement.setDate(2, startDate);
            preparedStatement.setDate(3, endDate);
            preparedStatement.setDouble(4, leaseContract.getMonthlyPayment());

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
