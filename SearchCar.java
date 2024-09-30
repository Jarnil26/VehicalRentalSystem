import java.sql.*;
import java.util.*;

public class SearchCar {
    static Connection con;
    static Statement st;
    static Scanner sc = new Scanner(System.in);

    // Default constructor
    public SearchCar() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Connect to the database
        String dburl = "jdbc:mysql://localhost:3306/vehical_rental";
        String dbuser = "root";
        String dbpass = "";
        con = DriverManager.getConnection(dburl, dbuser, dbpass);
        if (con != null) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM car_list ORDER BY rating");
            while (rs.next()) {
                String car_name = rs.getString("car_name");
                int car_no = rs.getInt("car_no");
                int rent_prize = rs.getInt("rent_prize");
                double rating = rs.getDouble("rating");
                SrcCar car = new SrcCar(car_no, car_name, rent_prize, rating);
                insert(car);
            }
        } else {
            System.out.println("Connection Failed...!!!");
        }
    }

    class Node {
        SrcCar car;
        Node left;
        Node right;

        Node(SrcCar car) {
            this.car = car;
            left = null;
            right = null;
        }
    }

    static Node root = null;

    void insert(SrcCar car) {
        root = insertInto(root, car);
    }

    Node insertInto(Node root, SrcCar car) {
        if (root == null) {
            root = new Node(car);
            return root;
        }
        if (car.car_no < root.car.car_no) {
            root.left = insertInto(root.left, car);
        } else if (car.car_no > root.car.car_no) {
            root.right = insertInto(root.right, car);
        }
        return root;
    }

    public static boolean search(int carNo) {
        Node result = searchCar(root, carNo);
        if (result != null) {
            System.out.println("~ Car Found....!!!!");
            System.out.println("Car Name : " + result.car.car_name);
            System.out.println("Car No : " + result.car.car_no);
            System.out.println("Car Rent Prize : " + result.car.rent_prize);
            System.out.println("Car Rating : " + result.car.rating);
            return true;
        } else {
            System.out.println("~ No Car Found");
            return false;
        }
    }

    public static Node searchCar(Node root, int carNO) {
        if (root == null || root.car.car_no == carNO) {
            return root;
        }
        if (carNO < root.car.car_no) {
            return searchCar(root.left, carNO);
        } else {
            return searchCar(root.right, carNO);
        }
    }
    
}

class SrcCar {
    int car_no;
    String car_name;
    float rent_prize;
    double rating;

    public SrcCar(int car_no, String car_name, float rent_prize, double rating) {
        this.car_no = car_no;
        this.car_name = car_name;
        this.rent_prize = rent_prize;
        this.rating = rating;
    }
}
