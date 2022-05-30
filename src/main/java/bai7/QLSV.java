package bai7;

import vd1.App;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class QLSV {
    public static void main(String[] args) {
        QLSV app = new QLSV();
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        while(choice != 6) {
            app.menu();
            while(true) {
                try {
                    choice = sc.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Number only!");
                    sc.nextLine();
                }
            }
            switch (choice) {
                case 1:
                    //check nếu có tổn tại một lớp nào không, nếu không có lớp yêu cầu tạo lớp trước
                    Connection con = app.accessDB();
                    try {
                        Statement statement = con.createStatement();
                        ResultSet rs = statement.executeQuery("select * from ClassPT");
                        if(!rs.next()) {
                            System.out.println("Tao lop truoc!");
                            break;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            con.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                    Student student = new Student();
                    student.addStudent();
                    app.addSinhvien(student.getName(), student.getAge(), student.getId_lop(), app.accessDB());
                    break;

                case 2:
                    ClassPT classPT = new ClassPT();
                    classPT.addClass();
                    app.addClassPT(classPT.getName(), classPT.getGrade(), app.accessDB());
                    break;
                case 3:
                    app.displayByAge(app.accessDB());
                    break;
                case 4:
                    app.displayByClass(app.accessDB());
                    break;
                case 5:
                    app.displayByGrade(app.accessDB());
                    break;
                case 6:
                    System.out.println("bye");
                    break;
                default:
                    System.out.println("invalid input!");
            }
        }


    }

    public Connection accessDB() {
        Connection con = null;
        try {
            //load driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //tao connection
            String url = "jdbc:sqlserver://localhost:1433;DatabaseName=JavaQLSV";
            con = DriverManager.getConnection(url, "sa", "123456");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    //them sinh vien
    public void addSinhvien(String name, int age, int id_lop, Connection con) {
        try {
            //create statement
            String sql = "insert into Student(name, age, id_lop) values (?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setInt(3, id_lop);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //them class
    public void addClassPT(String name, int grade, Connection con) {
        try {
            //create statement
            String sql = "insert into ClassPT(name, grade) values (?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, grade);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //tim kiem sinh vien theo tuoi
    public void displayByAge(Connection con) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhap tuoi can tim: ");
        int age;
        while(true) {
            try {
                age = sc.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Number only!");
                sc.nextLine();
            }
        }
        try {
            ArrayList<Student> list = new ArrayList<>();
            String sql = "select * from Student where age = " + String.valueOf(age);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setAge(rs.getInt("age"));
                list.add(student);
            }
            if(list.size() > 0) {
                for(Student x : list) {
                    System.out.println(x);
                }
            } else {
                System.out.println("Khong co hoc sinh nao tuoi " + age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //nhap ma lop va hien thi danh sach sinh vien cua lop
    public void displayByClass(Connection con) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhap ten lop: ");
        String className = sc.nextLine();

        try {
            ArrayList<Student> list = new ArrayList<>();
            String sql = "select Student.id, Student.name, Student.age from Student join ClassPT on Student.id_lop = ClassPT.id where ClassPT.name = \'" + className + "\'";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setAge(rs.getInt("age"));
                list.add(student);
            }
            if(list.size() > 0) {
                for(Student x : list) {
                    System.out.println(x);
                }
            } else {
                System.out.println("Khong co hoc sinh nao trong lop " + className);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //hiển thị danh sách sinh viên theo khối nhập vào
    public void displayByGrade(Connection con) {
        Scanner sc = new Scanner(System.in);
        int grade;
        System.out.println("Nhap vao khoi: ");
        while(true) {
            try{
                grade = sc.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Number only!");
                sc.nextLine();
            }
        }

        try {
            ArrayList<Student> list = new ArrayList<>();
            String sql = "select Student.id, Student.name, Student.age from Student join ClassPT on Student.id_lop = ClassPT.id where ClassPT.grade = " + String.valueOf(grade);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setAge(rs.getInt("age"));
                list.add(student);
            }
            if(list.size() > 0) {
                for(Student x : list) {
                    System.out.println(x);
                }
            } else {
                System.out.println("Khong co hoc sinh nao khoi: " + grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void menu() {
        System.out.println("*****Menu*****");
        System.out.println("1. Add student");
        System.out.println("2. Add classPT");
        System.out.println("3. Tim sinh vien theo tuoi");
        System.out.println("4. Danh sach sinh vien theo lop");
        System.out.println("5. Danh sach sinh vien theo khoi");
        System.out.println("6. Exit");
        System.out.println("Your choice: ?");
    }


}
