/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author andreicristea
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    
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
    
    public static Boolean readBool(Scanner sc, String msg) {
        System.out.println(msg);
        Boolean i = sc.nextBoolean();
        sc.nextLine();
        
        return i;
    }
    
    public static Student readStudent(Scanner sc) {
        String name = readString(sc, "Please provide name of student: ");
        String mothersName = readString(sc, "Please provide mothers name of student: ");
        int mark = readInt(sc, "Please provide student's mark: ");
        Boolean active = readBool(sc, "Please state is student status is active: ");
        
        return new Student(name, mothersName, mark, active);
        
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
        
        for (var i = 0; i < 3; i++) {
            students.add(readStudent(sc));
        }
        
        Student bestAverage = students.get(0);
        for (Student st : students) {
            if (st.getMark() > bestAverage.getMark()) {
                bestAverage = st;
            }
        }
        
        System.out.println("Best average: " + bestAverage);
        
        ArrayList<Student> scholarships = new ArrayList<>();
        
        for (Student st : students) {
            if (st.getMark() >= 4) {
                scholarships.add(st);
                System.out.println("Get's scholarship: " + st);
            }
        }
       
    }
}
