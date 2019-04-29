package com.sbt.ex1;

import com.sbt.ex1.mailer.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class Main {
    //клиентский код
    public static void main(String[] args) throws Exception {
        Connection connection = null;
        LocalDate dateFrom = null, dateTo = null;
        String departmentId = "1";
        String recipients = null;
        String emailFrom = "mail.google.com";

        EmployeeDao employeeDao = new EmployeeDao(connection);
        List<Employee> employeeList = employeeDao.getEmployeesWithPaymentInDataRange(departmentId, dateFrom, dateTo);

        SalaryReport salaryReport = new SalaryHtmlReport(employeeList);
        String report = salaryReport.generateReport();

        new EmailMailer().send(emailFrom, recipients, report);
    }
}
