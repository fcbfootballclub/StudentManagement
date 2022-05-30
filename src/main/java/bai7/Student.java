package bai7;

import java.sql.*;
import java.util.Scanner;

public class Student {
    private int id;
    private String name;
    private int age;
    private int id_lop;

    private static int ID = 0;

    //constructor
    public Student() {
        this.id = ++ID;
    }


    public Student(String name, int age, int id_lop) {
        this.name = name;
        this.age = age;
        this.id_lop = id_lop;
        this.id = ++ID;
    }

    public void addStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Student's name: ");
        this.name = sc.nextLine();
        System.out.println("Student's age: ");
        this.age = sc.nextInt();
        sc.nextLine();

        id_lop = -1;
        String className = "";
        while(id_lop == -1) {
            System.out.println("Student's class: ");
            className = sc.nextLine();
            this.id_lop = validClass(className);
            if(id_lop == -1) {
                System.out.println("Invalid class, try again!");
            }
        }
    }



    //getter and setter
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId_lop() {
        return id_lop;
    }

    public void setId_lop(int id_lop) {
        this.id_lop = id_lop;
    }


    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", id_lop=" + id_lop +
                '}';
    }


    public int validClass(String className) {
        Connection con = null;
        try {
            //load driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            //tao connection
            String url = "jdbc:sqlserver://localhost:1433;DatabaseName=JavaQLSV";
            con = DriverManager.getConnection(url, "sa", "123456");

            //create statement
            String sql = "select * from ClassPT where name = \'" + className + "\'";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                return id;
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }
}
