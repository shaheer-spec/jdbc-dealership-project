package com.yearup.dealership.db;

import com.yearup.dealership.models.Vehicle;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao {
    private DataSource dataSource;

    public VehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addVehicle(Vehicle vehicle) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT into vehicles (Vin, make, model, year, sold, color, vehicleType, odometer, price) values (?, ?, ?, ?, ?, ?, ?, ?, ?)")){
            preparedStatement.setString(1, vehicle.getVin());
            preparedStatement.setString(2, vehicle.getMake());
            preparedStatement.setString(3, vehicle.getModel());
            preparedStatement.setInt(4, vehicle.getYear());
            preparedStatement.setBoolean(5, vehicle.isSold());
            preparedStatement.setString(6, vehicle.getColor());
            preparedStatement.setString(7, vehicle.getVehicleType());
            preparedStatement.setInt(8, vehicle.getOdometer());
            preparedStatement.setDouble(9, vehicle.getPrice());

            int rows = preparedStatement.executeUpdate();
            System.out.println("Rows Inserted: " + rows);

        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void removeVehicle(String VIN) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM vehicles WHERE vin = ?")){
            preparedStatement.setString(1, VIN);

            int rows = preparedStatement.executeUpdate();
            System.out.println("Rows Deleted: " + rows);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Vehicle> searchByPriceRange(double minPrice, double maxPrice) {
        List<Vehicle> vehiclesByPrice = new ArrayList<>();
        String byPriceQuery = """
                Select *
                From vehicles
                where price > ?
                and price < ?""";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(byPriceQuery)){

            preparedStatement.setDouble(1, minPrice);
            preparedStatement.setDouble(1, maxPrice);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    do {
                        String vin = resultSet.getString("Vin");
                        String make = resultSet.getString("make");
                        String model = resultSet.getString("model");
                        int year = resultSet.getInt("year");
                        Boolean sold = resultSet.getBoolean("sold");
                        String color = resultSet.getString("color");
                        String type = resultSet.getString("vehicleType");
                        int odometer = resultSet.getInt("odometer");
                        double price = resultSet.getDouble("price");


                        vehiclesByPrice.add(new Vehicle(vin, make, model, year, sold, color, type, odometer, price));
                    }while (resultSet.next());
                } else {
                    System.out.println("No vehicle found");
                }
            }


        } catch (Exception ex) {
            System.err.println("An error has occurred!");
            ex.printStackTrace();
        }

        return vehiclesByPrice;
    }

    public List<Vehicle> searchByMakeModel(String make, String model) {

        return new ArrayList<>();
    }

    public List<Vehicle> searchByYearRange(int minYear, int maxYear) {
        // TODO: Implement the logic to search vehicles by year range
        return new ArrayList<>();
    }

    public List<Vehicle> searchByColor(String color) {
        // TODO: Implement the logic to search vehicles by color
        return new ArrayList<>();
    }

    public List<Vehicle> searchByMileageRange(int minMileage, int maxMileage) {
        // TODO: Implement the logic to search vehicles by mileage range
        return new ArrayList<>();
    }

    public List<Vehicle> searchByType(String type) {
        // TODO: Implement the logic to search vehicles by type
        return new ArrayList<>();
    }

    private Vehicle createVehicleFromResultSet(ResultSet resultSet) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setVin(resultSet.getString("VIN"));
        vehicle.setMake(resultSet.getString("make"));
        vehicle.setModel(resultSet.getString("model"));
        vehicle.setYear(resultSet.getInt("year"));
        vehicle.setSold(resultSet.getBoolean("SOLD"));
        vehicle.setColor(resultSet.getString("color"));
        vehicle.setVehicleType(resultSet.getString("vehicleType"));
        vehicle.setOdometer(resultSet.getInt("odometer"));
        vehicle.setPrice(resultSet.getDouble("price"));
        return vehicle;
    }
}
