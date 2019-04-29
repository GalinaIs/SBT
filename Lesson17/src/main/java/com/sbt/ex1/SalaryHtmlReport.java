package com.sbt.ex1;

import java.util.List;

public class SalaryHtmlReport implements SalaryReport {
    private final List<Employee> employeeList;

    public SalaryHtmlReport(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public String generateReport() {
        StringBuilder reportHtml = new StringBuilder();
        reportHtml.append("<html><body><table><tr><td>Employee</td><td>Salary</td></tr>");

        double totalSalary = 0;
        for (Employee employee : employeeList) {
            reportHtml.append("<tr>");
            reportHtml.append("<td>").append(employee.getName()).append("</td>");
            reportHtml.append("<td>").append(employee.getSalary()).append("</td>");
            reportHtml.append("</tr>");
            totalSalary += employee.getSalary();
        }

        reportHtml.append("<tr><td>Total</td><td>").append(totalSalary).append("</td></tr>");
        reportHtml.append("</table></body></html>");

        return reportHtml.toString();
    }
}
