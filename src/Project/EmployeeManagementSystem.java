package Project;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Employee class must implement Serializable
class Employee implements Serializable {
    int id;
    String name;
    String department;
    double salary;

    Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + department + " | " + salary;
    }
}

public class EmployeeManagementSystem {

    static Map<Integer, Employee> employees = new HashMap<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "employees.dat";

    // Load employees from file
    @SuppressWarnings("unchecked")
    public static void loadEmployees() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            employees = (Map<Integer, Employee>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found, starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    // Save employees to file
    public static void saveEmployees() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(employees);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public static void addEmployee() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // consume newline
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Department: ");
        String dept = sc.nextLine();
        System.out.print("Enter Salary: ");
        double sal = sc.nextDouble();
        employees.put(id, new Employee(id, name, dept, sal));
        saveEmployees();
        System.out.println("Employee Added!");
    }

    public static void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found!");
        } else {
            employees.values().forEach(System.out::println);
        }
    }

    public static void updateSalary() {
        System.out.print("Enter Employee ID to update: ");
        int id = sc.nextInt();
        if (employees.containsKey(id)) {
            System.out.print("Enter new salary: ");
            double sal = sc.nextDouble();
            employees.get(id).salary = sal;
            saveEmployees();
            System.out.println("Salary Updated!");
        } else {
            System.out.println("Employee not found!");
        }
    }

    public static void deleteEmployee() {
        System.out.print("Enter Employee ID to delete: ");
        int id = sc.nextInt();
        if (employees.remove(id) != null) {
            saveEmployees();
            System.out.println("Employee Deleted!");
        } else {
            System.out.println("Employee not found!");
        }
    }

    public static void main(String[] args) {
        loadEmployees(); // Load data at startup
        while (true) {
            System.out.println("\n--- Employee Management System ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Salary");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> viewEmployees();
                case 3 -> updateSalary();
                case 4 -> deleteEmployee();
                case 5 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
