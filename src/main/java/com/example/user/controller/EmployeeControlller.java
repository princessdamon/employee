package com.example.user.controller;

import com.example.user.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@RestController

public class EmployeeControlller {

    @PostMapping(path = "/employee")
    public String addEmployee(@RequestBody Employee request) {


        Connection c = null;                //เชื่อมต่อด้าต้าเบส
        PreparedStatement stmt = null;
        boolean flag = false;               //เช็ค
        try {
            Class.forName("org.postgresql.Driver");  // อ้างอิงดาต้าเบส
            c = DriverManager.getConnection("jdbc:postgresql://35.197.145.177:5432/employee",  //เชื่อมต่อดาต้า url > user
                    "postgres", "postgres");
            c.setAutoCommit(false);
            System.out.println("Opan");


            StringBuffer sql = new StringBuffer("INSERT INTO employee (employee_id,firstname, lastname, email, sex, age) VALUES (?,?,?,?,?,?)"); //คำสั่ง sql
            stmt = c.prepareStatement(sql.toString());
            stmt.setString(1, request.getEmployeeId());
            stmt.setString(2, request.getFirstName());
            stmt.setString(3, request.getLastName());
            stmt.setString(4, request.getEmail());
            stmt.setString(5, request.getSex());
            stmt.setString(6, request.getAge());

            System.out.println("After set stmt");

            int result = stmt.executeUpdate(); //สั่งให้ทำงานรีเทรสค่าจำนวนเต็ม เป็น int
            if (result > 0) {
                flag = true;
                System.out.println("flag = true");
            } else {
                flag = false;
                System.out.println("flag = false");
            }
            stmt.close();  //ปิดคอนเน็กชั่น ปิดดาตดาเบส
            c.commit(); //ปิดคอนเน็กชั่น ปิดดาตดาเบส
            c.close(); //ปิดคอนเน็กชั่น ปิดดาตดาเบส
            System.out.println("Close database");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            //    System.exit();
        }

        if (flag) {  //ตรวจเซ็คค่า
            System.out.println("Records created successfuly");
            return "Records created successfuly";
        } else {
            System.out.println("Records created failed");
            return "Records created failed";
        }

    }


    @GetMapping(path = "/employees")
    public ArrayList<Employee> getEmployee() {
        Connection c = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        ArrayList<Employee> list = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://35.197.145.177:5432/employee", "postgres", "postgres");
            c.setAutoCommit(false);

            StringBuffer sql = new StringBuffer("SELECT * FROM employee");
            stmt = c.prepareStatement(sql.toString());
            rs = stmt.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getString("id"));
                employee.setEmployeeId(rs.getString("employee_id"));
                employee.setFirstName(rs.getString("firstname"));
                employee.setLastName(rs.getString("lastname"));
                employee.setEmail(rs.getString("email"));
                employee.setSex(rs.getString("sex"));
                employee.setAge(rs.getString("age"));


                list.add(employee);
            }

            stmt.close();
            c.commit();
            c.close();
            System.out.println("Close database");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return list;
    }


    @DeleteMapping(path = "/employee/{employeeId}")
    public String deleteemployee(@PathVariable String employeeId) {
        Connection c = null;
        PreparedStatement stmt = null;
        boolean flag = false;               //เช็ค

        ArrayList<Employee> list = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://35.197.145.177:5432/employee", "postgres", "postgres");
            c.setAutoCommit(false);

            StringBuffer sql = new StringBuffer("DELETE FROM employee WHERE employee_id = ? ");
            stmt = c.prepareStatement(sql.toString());
            stmt.setString(1, employeeId);

            int result = stmt.executeUpdate(); //สั่งให้ทำงานรีเทรสค่าจำนวนเต็ม เป็น int
            if (result > 0) {
                flag = true;
                System.out.println("flag = true");
            } else {
                flag = false;
                System.out.println("flag = false");
            }


            stmt.close();
            c.commit();
            c.close();
            System.out.println("Close database");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        if (flag) {  //ตรวจเซ็คค่า
            System.out.println("Delete successfuly");
            return "Delete successfuly";
        } else {
            System.out.println("Delete failed");
            return "Delete failed";
        }

    }


    @PutMapping(path = "/employees")
    public String putemployee(@RequestBody Employee request) {
        Connection c = null;
        PreparedStatement stmt = null;
        boolean flag = false;               //เช็ค

        ArrayList<Employee> list = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://35.197.145.177:5432/employee", "postgres", "postgres");
            c.setAutoCommit(false);

            StringBuffer sql = new StringBuffer("UPDATE employee SET firstname = ?,lastname = ?, email = ?, sex = ?, age = ? WHERE employee_id =?");
            stmt = c.prepareStatement(sql.toString());
            stmt.setString(1, request.getFirstName());
            stmt.setString(2, request.getLastName());
            stmt.setString(3, request.getEmail());
            stmt.setString(4, request.getSex());
            stmt.setString(5, request.getAge());
            stmt.setString(6, request.getEmployeeId());

            int result = stmt.executeUpdate(); //สั่งให้ทำงานรีเทรสค่าจำนวนเต็ม เป็น int
            if (result > 0) {
                flag = true;
                System.out.println("flag = true");
            } else {
                flag = false;
                System.out.println("flag = false");
            }


            stmt.close();
            c.commit();
            c.close();
            System.out.println("Close database");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        if (flag) {  //ตรวจเซ็คค่า
            System.out.println("Update successfuly");
            return "Update successfuly";
        } else {
            System.out.println("Update failed");
            return "Update failed";
        }
    }


    @GetMapping(path = "/employee/{employeeId}")
    public ArrayList<Employee> getemployeeById(@PathVariable String employeeId) {
        Connection c = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Employee> list = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://35.197.145.177:5432/employee", "postgres", "postgres");
            c.setAutoCommit(false);

            StringBuffer sql = new StringBuffer("SELECT * FROM employee WHERE employee_id = ? ");
            stmt = c.prepareStatement(sql.toString());

            stmt.setString(1, employeeId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getString("id"));
                employee.setEmployeeId(rs.getString("employee_id"));
                employee.setFirstName(rs.getString("firstname"));
                employee.setLastName(rs.getString("lastname"));
                employee.setEmail(rs.getString("email"));
                employee.setSex(rs.getString("sex"));
                employee.setAge(rs.getString("age"));

                list.add(employee);
            }

            stmt.close();
            c.commit();
            c.close();
            System.out.println("Close database");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
        }

        return list;
    }

}

