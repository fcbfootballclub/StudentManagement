package bai7;

import java.util.Scanner;

public class ClassPT {
    private int id;
    private String name;
    private int grade;

    private static int ID = 0;

    //constructor
    public ClassPT() {
        this.id = ++ID;
    }

    public ClassPT(String name, int grade) {
        this.name = name;
        this.grade = grade;
        this.id = ++ID;
    }

    //Add class
    public void addClass() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Class's name: ");
        this.name = sc.nextLine();
        System.out.println("Class's grade: ");
        this.grade = sc.nextInt();
    }

    // getter and setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "ClassPT{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                '}';
    }
}
