/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author andreicristea
 */
public class Student {
    private String name;
    private String mothersName;
    private int mark;
    private Boolean active;

    public Student(String name, String mothersName, int mark, Boolean active) {
        this.name = name;
        this.mothersName = mothersName;
        this.mark = mark;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public String getMothersName() {
        return mothersName;
    }

    public int getMark() {
        return mark;
    }

    public Boolean getActive() {
        return active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMothersName(String mothersName) {
        this.mothersName = mothersName;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Student{" + "name=" + name + ", mothersName=" + mothersName + ", mark=" + mark + ", active=" + active + '}';
    }
    
}
