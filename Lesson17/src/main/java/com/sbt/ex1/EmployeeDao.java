package com.sbt.ex1;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class EmployeeDao {
    private final static String SELECT_EMPLOYEE_PAYMENT_BETWEEN_DATES = "select emp.id as emp_id, emp.name as amp_name," +
            " sum(salary) as salary from employee emp left join salary_payments sp on emp.id = sp.employee_id " +
            "where emp.department_id = ? and sp.date >= ? and sp.date <= ? group by emp.id, emp.name";

    private Connection connection;

    public EmployeeDao(Connection databaseConnection) {
        this.connection = databaseConnection;
    }

    public List<Employee> getEmployeesWithPaymentInDataRange(String departmentId, LocalDate dateFrom, LocalDate dateTo)
            throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_PAYMENT_BETWEEN_DATES)) {
            preparedStatement.setString(0, departmentId);
            preparedStatement.setDate(1, new java.sql.Date(dateFrom.toEpochDay()));
            preparedStatement.setDate(2, new java.sql.Date(dateTo.toEpochDay()));

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Employee> employeeList = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("emp_id");
                String name = resultSet.getString("emp_name");
                double salary = resultSet.getDouble("salary");
                employeeList.add(new Employee(id, name, salary));
            }
            return employeeList;
        } catch (SQLException ex) {
            throw new SQLException("Exception with execute SQL in getEmployeesWithPaymentInDataRange", ex);
        }
    }
}
