/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employees;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author andreicristea
 */
public class Employee {
    private String firstName;
    private String lastName;
    private String job;
    private int salary;

    public String getFirstName() {
        return this.firstName;
    }

    public Employee(String firstName, String lastName, String job, int salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.job = job;
        this.salary = salary;
    }

    public String getLastName() {
        return lastName;
    }

    public String getJob() {
        return job;
    }

    public int getSalary() {
        return salary;
    }
    
    
    public void increaseSalary(int percent) {
        salary = (int) (this.salary * (1.0 +  percent / 100.0));
    }
    
    @Override
    public String toString() {
        return "{ firstName: " + this.firstName + "; lastName: " + this.lastName + "; job: " + this.job + "; salary: " + this.salary + " }\n";
    }
    
    public static String readString(Scanner sc, String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }  
    
    public static int readInt(Scanner sc, String msg) {
        System.out.print(msg);
        int i = sc.nextInt();
        sc.nextLine();
        
        return i;
    }  
    
    private static Employee readEmployee(Scanner sc) {
        String firstName = Employee.readString(sc, "Print first name: ");
        String secondName = Employee.readString(sc, "Print second name: ");
        String position = Employee.readString(sc, "Print the Employee position: ");
        int salary = Employee.readInt(sc, "Print Salary: ");
        Employee john = new Employee(firstName, secondName, position, salary);
        return john;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.firstName);
        hash = 29 * hash + Objects.hashCode(this.lastName);
        hash = 29 * hash + Objects.hashCode(this.job);
        hash = 29 * hash + this.salary;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Employee other = (Employee) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        return true;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Employee> employees = new ArrayList<>();
        for (var i = 0; i < 3; i++) {
            Employee e = readEmployee(sc);
            employees.add(e);
        }
        
        
        String job = readString(sc, "Job to raise salary: ");
        int raise = readInt(sc, "Salary increase percent: ");
        
        
        for (Employee e : employees) {
            if (e.getJob().equals(job)) {
                e.increaseSalary(raise);
                System.out.println(e);
            }
        }
        
        Employee richest = employees.get(0);
        for (Employee e : employees) {
            if (e.getSalary() > richest.getSalary()) {
                richest = e;
            }
        }
        
        System.out.println(richest);
//        john.increaseSalary(30);
//        System.out.println(john);
    }
    
}
