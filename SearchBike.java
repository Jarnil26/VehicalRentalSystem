import java.sql.*;
import java.util.*;

public class SearchBike {
    static Connection con;
    static Statement st;
    static Scanner sc = new Scanner(System.in);

    // Default constructor
    public SearchBike() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Connect to the database
        String dburl = "jdbc:mysql://localhost:3306/vehical_rental";
        String dbuser = "root";
        String dbpass = "";
        con = DriverManager.getConnection(dburl, dbuser, dbpass);
        if (con != null) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM bike_list");
            while (rs.next()) {
                String bike_name = rs.getString("bike_name");
                int bike_no = rs.getInt("bike_no");
                int bike_prize = rs.getInt("rent_prize_bike");
                double rating = rs.getDouble("rating");
                SrcBike bike = new SrcBike(bike_no, bike_name, bike_prize, rating);
                insert(bike);
            }
        } else {
            System.out.println("Connection Failed...!!!");
        }
    }

    class Node {
        SrcBike bike;
        Node left;
        Node right;

        Node(SrcBike bike) {
            this.bike = bike;
            left = null;
            right = null;
        }
    }

    static Node root = null;

    void insert(SrcBike bike) {
        root = insertInto(root, bike);
    }

    Node insertInto(Node root,SrcBike bike) {
        if (root == null) {
            root = new Node(bike);
            return root;
        }
        if (bike.bike_no < root.bike.bike_no) {
            root.left = insertInto(root.left, bike);
        } else if (bike.bike_no > root.bike.bike_no) {
            root.right = insertInto(root.right, bike);
        }
        return root;
    }

    public static boolean search(int bikeNo) {
        Node result = searchBike(root, bikeNo);
        if (result != null) {
            System.out.println("~ Bike Found....!!!!");
            System.out.println("Bike Name: " + result.bike.bike_name);
            System.out.println("Bike No: " + result.bike.bike_no);
            System.out.println("Bike Rent Prize: " + result.bike.rent_prize);
            System.out.println("Bike Rating: " + result.bike.rating);
            return true;
        } else {
            System.out.println("~ No Bike Found");
            return false;
        }
    }

    public static Node searchBike(Node root, int bikeNo) {
        if (root == null || root.bike.bike_no == bikeNo) {
            return root;
        }
        if (bikeNo < root.bike.bike_no) {
            return searchBike(root.left, bikeNo);
        } else {
            return searchBike(root.right, bikeNo);
        }
    }

    
}

class SrcBike {
    int bike_no;
    String bike_name;
    float rent_prize;
    double rating;

    public SrcBike(int bike_no, String bike_name, float rent_prize, double rating) {
        this.bike_no = bike_no;
        this.bike_name = bike_name;
        this.rent_prize = rent_prize;
        this.rating = rating;
    }
}
