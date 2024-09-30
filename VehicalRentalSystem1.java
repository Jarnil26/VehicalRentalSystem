

// import Statement..
// import CarRental.bookCarForRent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.Deflater;
import javax.swing.text.AbstractDocument;
public class VehicalRentalSystem1 {
    static Scanner sc = new Scanner(System.in);
    static java.sql.Connection con;
    static  Statement st;
    static  boolean userLoginStatus = false;
    static  boolean adminLoginStatus = false;
    static int count1;

    // Main Method..
        // load the driver..
    public static void main(String[] args) throws  Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");

        // connect to DataBase
        String dburl = "jdbc:mysql://localhost:3306/vehical_rental";
        String dbuser = "root";
        String dbpass ="";
        con = DriverManager.getConnection(dburl, dbuser, dbpass);
        StackCar scar = new StackCar(con);
        StackCar scar2 = new StackCar();   
        StackBike sbike = new StackBike(con);
        StackBike sbike2 = new StackBike();
       
        
        if (con != null) {
            System.out.println("~~~~~~~~~ Welcome To Shiv-Shakti: Vehical Rental System ~~~~~~~~~");
            int choice1;
            
            do{
                System.out.println("Press 1 > Admin Login ");
                System.out.println("Press 2 > User Login ");
                System.out.println("Press 3 > Exit ");
                choice1 = sc.nextInt();
                sc.nextLine();
                switch (choice1) {
                    case 1:
                        adminLoginStatus = administrator_login();
                        if (adminLoginStatus) {
                        
                            int choice;
                            do {
                                //  System.out.println("hyyy");
                                System.out.println("1 > Add A Car : ");
                                System.out.println("2 > Delete A Car");
                                System.out.println("3 > Show All Car Details");
                                System.out.println("4 > Add A Bike : ");
                                System.out.println("5 > Delete A Bike : ");
                                System.out.println("6 > Show All Bike Details : ");
                                System.out.println("7 > Show All Booking : ");
                                System.out.println("8 > Add A Admin : ");
                                System.out.println("9 > Remove A User : ");
                                System.out.println("10 > Remove A Admin : ");
                                System.out.println("11 > Show All Users Info : ");
                                System.out.println("12 > Update Admin Information : ");
                                System.out.println("0 > Exit");
                                choice = sc.nextInt();
                                sc.nextLine();
                                
                                switch (choice) {
                                    
                                    case 1:
                                        System.out.println("Enter Car Name : ");
                                        String car_name= sc.nextLine();
                                        System.out.println("Enter Car No : ");
                                        int car_no = sc.nextInt();
                                        System.out.println("Enter Rent Prize Per Day : ");
                                        int rent_prize = sc.nextInt();
                                        System.out.println("Enter Car Rating : ");
                                        double rating = sc.nextDouble();
                                        Car car = new Car(car_name, car_no, rating, rent_prize);
                                        scar.push(car);
                                    break;
                                    
                                    case 2:
                                        System.out.println("Enter Car No To Delete : ");
                                        int del_car_no = sc.nextInt();
                                        deleteByCarNo(del_car_no);  
                                    break;
                                    
                                    case 3:
                                        scar.displayCarList();
                                    break;
            
                                    case 4:
                                        System.out.println("Enter Bike Name : ");
                                        String bike_name= sc.nextLine();
                                        System.out.println("Enter Bike No : ");
                                        int bike_no = sc.nextInt();
                                        System.out.println("Enter Rent Prize Per Day : ");
                                        int rent_prize_bike = sc.nextInt();
                                        System.out.println("Enter Rating : ");
                                        double ratingBike = sc.nextDouble();
                                        Bike bike = new Bike(bike_name, bike_no, ratingBike, rent_prize_bike);
                                        sbike.push(bike);
                                    break;
            
                                    case 5:
                                        System.out.println("Enter Bike No To Delete : ");    
                                        int del_bike_no = sc.nextInt();
                                        deleteByBikeNo(del_bike_no);
                                    break;
            
                                    case 6:
                                        sbike.displayBikeList();
                                    break;
            
                                    case 7:
                                        displayAllPayments();
                                    break;
                                    
                                    case 8:
                                        addAdmin();    
                                    break;
                                    case 9:
                                        System.out.println("Enter User Name To Delete : ");
                                        String del_user_name = sc.nextLine();
                                        removeUser(del_user_name);
                                    break;
            
                                    case 10:
                                        System.out.println("Enter Administrator Id To Delete : ");
                                        int del_administrator = sc.nextInt();
                                        removeAdmin(del_administrator);   
                                    break;
            
                                    case 11:
                                        displayUsers();    
                                    break;
                                    case 12:
                                        changeInformationOfAdmin();

                                        
                                    break;

                                    case 0:
                                    System.out.println("Exiting...Thanks For Using Shiv-Shakti: Vehical Rental System");               
                                        
                                    break;
                            
                                    default:
                                        System.out.println("Invalid Choice");
                                    break;
                                }
                            } while (choice != 0);
                        }
                        break;

                    case 2:
                        int ch;
                    
                        do {
                            System.out.println("1 > Login");
                            System.out.println("2 > Sing Up");
                            System.out.println("3 > Exit");
                            ch = sc.nextInt();
                
                            switch(ch){
                                case 1 :
                                    boolean temp = user_login();
                                    if (temp) {
                                        int temp1;
                                        do{
        
                                            System.out.println("1 > Car List");
                                            System.out.println("2 > Rent A Car Now");
                                            System.out.println("3 > Bike List");
                                            System.out.println("4 > Rent A Bike Now");
                                            System.out.println("5 > Top 3 Cars");
                                            System.out.println("6 > Top 3 Bike");
                                            System.out.println("7 > Search a Car ");
                                            System.out.println("8 > Search a Bike ");
                                            System.out.println("9 > Update Your Information");
                                            System.out.println("0 > Exit");
                                            temp1 = sc.nextInt();
                                            sc.nextLine();
                                            
        
                                            switch (temp1) {
                                                case 1:
                                                    scar.displayCarList();
                                                    System.out.println("top Cars");
                                                    
                                                break;
        
                                                case 2 :
                                                    System.out.println("Enter User Name : ");
                                                    String u_name = sc.nextLine();
                                                    CarRental.bookCarForRent(u_name);
                                                break;
        
                                                case 3:
                                                    sbike.displayBikeList();    
                                                break;                                        
                                                
                                                case 4:
                                                    System.out.println("Enetr User Name : ");
                                                    String u_name1 = sc.nextLine();
                                                    BikeRental.bookBikeForRent(u_name1);    
                                                break;
        
                                                case 5:
                                                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                                                    scar2.peek(1);
                                                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        
                                                break;
        
                                                case 6:
                                                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                                                    sbike2.peek(1);
                                                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        
                                                break;
        
                                                case 7:
                                                    System.out.println("Enter Car No To Search : ");
                                                    int searchCarNO = sc.nextInt();
                                                    SearchCar searchCar = new SearchCar();
                                                    searchCar.search(searchCarNO);    
                                                    //searchCar.display();
                                                break;
        
                                                case 8:
                                                    System.out.println("Enter Bike No To Search : ");
                                                    int searchBikeNo = sc.nextInt();
                                                    SearchBike searchBike = new SearchBike();
                                                    searchBike.search(searchBikeNo);
                                                    // SearchBike.search(searchBikeNo);
                                                break;
                                                
                                                case 9 :
                                                    changeInformationOfUser();
                                                    break;
                                                case 0:
                                                    System.out.println("Exiting...Thanks For Using Shiv-Shakti: Vehical Rental System");               
                                                default:
                                                    break;
                                            }
                                        }while(temp1 != 0);
                                        
                                    }
                                    break;
                                
                            case 2 :
                                user_singUp();
                                break;
    
                            case 3:
                                System.out.println("Exiting... Thanks For Visiting");
                                break;
    
                            default:
                                System.out.println("Invalid Choice");
                                break;
                        }
                    
            
                    }while (ch != 3);
                        break;

                    case 3:
                        System.out.println("Thanks For Using Shiv-Shakti Vehical Rental System");
                        break;

                    default:
                        System.out.println("Invalid Choice");
                        break;

                }

            }while(choice1 != 3);
            
        }else{
            System.out.println("Connection To DataBase Is Not Done !");
        }
       
       
    }

    // Login Method For Users..
    static boolean user_login()throws  Exception{
        System.out.println("Enter User Name: ");
        String userName = sc.next();
        System.out.println("Enter Password: ");
        String password = sc.next();
        String sql = "{call user_login(?,?,?)}";
        // String sql = "SELECT * FROM users WHERE user_name = ? AND password = ?";
        // PreparedStatement pstmt = con.prepareStatement(sql);
        // pstmt.setString(1, userName);
        // pstmt.setString(2, password);
        CallableStatement cst = con.prepareCall(sql);
        // ResultSet rs = pstmt.executeQuery();
        cst.setString(1, userName);
        cst.setString(2, password);
        cst.registerOutParameter(3, java.sql.Types.INTEGER);
        cst.execute();
        int result = cst.getInt(3);
        if (result == 1) {
            System.out.println("~~~~~~~~~~~~~~~~~~ Welcome Back " + userName + " ~~~~~~~~~~~~~~~~~~");
            userLoginStatus = true;
            return true;
        } else {
            System.out.println("Invalid user name or password. Please try again.");
            return false;
        }
            

    }
    // Sing Up Method Users..
    static void user_singUp() throws Exception{
        System.out.println("~~~~~~~~~ Registration From ~~~~~~~~~");
        System.out.println("Enter User Name : ");
        String user_name = sc.next();
        System.out.println("Enter Password : ");
        String password = sc.next();
        System.out.println("Enter Your Email : ");
        String email = sc.next();
        int digit;
        String phone_no="";
        do {
            System.out.println("Enter Your Phone Number{10-Digits only} : ");
            phone_no = sc.next();
        
        if(phone_no.length()==10){
            count1=0;
            for(int i=0;i<phone_no.length();i++){
                if(Character.isDigit((int)phone_no.charAt(i))){
                    count1++;
                }else{
                    break;
                }
            }
            if(count1!=10){
                System.out.println("Invalid Mobile Number.");
            }else{
               // b=false;
                
            }
        }else{
            System.out.println("Mobile Number contains only 10 digits.");
        }
        } while (count1 != 10);
        
        String sqlCheck = "select user_name from users";
        PreparedStatement pstCheck = con.prepareStatement(sqlCheck);
        ResultSet rsCheck = pstCheck.executeQuery();
        while (rsCheck.next()) {
            if(user_name.equals(rsCheck.getString("user_name"))){
                throw  new SameUserName("User Name Already Exist");
            }
        }

        String sql = "insert into users(user_name, password, email, phone_no) values('"+user_name+"', '"+password+"', '"+email+"', '"+phone_no+"') ";
        st = con.createStatement(); // Statement obj declaration
        int rs = st.executeUpdate(sql);
        // return true if query is updated or not
        if (rs > 0) {
            System.out.println("Registration Successed...!!");
            
        } else {
            System.out.println("Registration Failed...!!");
          
        }
        
    }

    static boolean  administrator_login() throws SQLException{
        System.out.println("Enter Administrator ID: ");
        int adminId = sc.nextInt();
        System.out.println("Enter Password: ");
        String password = sc.next();

        String sql = "SELECT * FROM administrator WHERE administrator_id = ? AND password = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, adminId);
        pstmt.setString(2, password);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String adminName = rs.getString("administrator_name");
            System.out.println("~~~~~~~~~~~~~~~~~~ Welcome Back " + adminName + " ~~~~~~~~~~~~~~~~~~");
            adminLoginStatus = true;
            return true;
        } else {
            System.out.println("Invalid administrator ID or password. Please try again.");
            return false;
        }
    }


    //Method To Add Admin
    public static void addAdmin() throws SQLException {
       
            System.out.println("Enter Administrator ID: ");
            int administrator_id = sc.nextInt();
            sc.nextLine(); // consume the newline
            System.out.println("Enter Administrator Name: ");
            String administrator_name = sc.nextLine();
            System.out.println("Enter Password: ");
            String password = sc.nextLine();
            System.out.println("Enter Administrator Email: ");
            String administrator_email = sc.nextLine();
    
            String sql = "INSERT INTO administrator (administrator_id, administrator_name, password, administrator_email) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, administrator_id);
            pstmt.setString(2, administrator_name);
            pstmt.setString(3, password);
            pstmt.setString(4, administrator_email);
    
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Administrator added successfully.");
            } else {
                System.out.println("Failed to add administrator.");
            }
        
    }

    // Method To Remove User {only admin can use this method}
    static void removeUser(String userName) throws SQLException {
       
            String sql = "DELETE FROM users WHERE user_name = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userName);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User '" + userName + "' removed successfully.");
            } else {
                System.out.println("User '" + userName + "' not found in the database.");
            }
        
    }

    //Method to Remove Admin {only admin can use this method}
    static void removeAdmin(int administrator_id) throws SQLException {
       
            String sql = "DELETE FROM administrator WHERE administrator_id  = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, administrator_id);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Admin  '" + administrator_id + "' removed successfully.");
            } else {
                System.out.println("Admin  '" + administrator_id  + "' not found in the database.");
            }
        
    }

    static void changeInformationOfUser(){
        int choiceInfo;
        do { 
            System.out.println("Press 1 > For Change User Name :");
            System.out.println("Press 2 > For Change User Email :");
            System.out.println("Press 3 > For Change User Password :");
            System.out.println("Press 4 > For Change User Phone Number :");
            System.out.println("Press 0 > For Exit :");
            choiceInfo = sc.nextInt();
            switch (choiceInfo) {
                case 1:
                    System.out.println("Enter New User Name :");
                    String newUserName = sc.next();
                    System.out.println("Enter Old User Name :");
                    String oldUserName = sc.next();
                    try {
                        String sql = "UPDATE users SET user_name = ? WHERE user_name = ?";
                        PreparedStatement pstmt = con.prepareStatement(sql);
                        pstmt.setString(1, newUserName);
                        pstmt.setString(2, oldUserName);
                        pstmt.executeUpdate();
                        System.out.println("User Name Changed Successfully");
                    }catch(Exception e){
                        System.out.println(e);

                    }
                break;

                case 2:
                    System.out.println("Enter New User Email :");
                    String newEmail = sc.next();
                    System.out.println("Enter Old User Email :");
                    String oldEmail = sc.next();
                    try {
                        String sql = "UPDATE users SET email = ? WHERE email = ?";
                        PreparedStatement pstmt = con.prepareStatement(sql);
                        pstmt.setString(1, newEmail);
                        pstmt.setString(2, oldEmail);
                        pstmt.executeUpdate();
                        System.out.println("User Email Changed Successfully");
                    }catch(Exception e){
                        System.out.println(e);
                    }
                break;

                case 3:
                    System.out.println("Enter New Password : ");
                    String newPassword = sc.next();
                    System.out.println("Enter Old Password : ");
                    String oldPassword = sc.next();
                    try {
                        String sql = "UPDATE users SET password = ? WHERE password = ?";
                        PreparedStatement pstmt = con.prepareStatement(sql);
                        pstmt.setString(1, newPassword);
                        pstmt.setString(2,oldPassword);
                        pstmt.executeUpdate();
                        System.out.println("User Password Changed Successfully");
                    }catch(Exception e){
                        System.out.println(e);
                    }
                break;

                case 4:
                    int digit;
                    String phone_no="";
                    do {
                        System.out.println("Enter Your New Phone Number{10-Digits only} : ");
                        phone_no = sc.next();
                    
                        if(phone_no.length()==10){
                            count1=0;
                            for(int i=0;i<phone_no.length();i++){
                                if(Character.isDigit((int)phone_no.charAt(i))){
                                    count1++;
                                }else{
                                    break;
                                }
                            }
                            if(count1!=10){
                                System.out.println("Invalid Mobile Number.");
                            }else{
                               // b=false;

                            }
                        }else{
                            System.out.println("Mobile Number contains only 10 digits.");
                        }
                    } while (count1 != 10);

                    //
                    int digit1;
                    String oldphone_no="";
                    do {
                        System.out.println("Enter Your Old Phone Number{10-Digits only} : ");
                        oldphone_no = sc.next();
                    
                        if(oldphone_no.length()==10){
                            count1=0;
                            for(int i=0;i<oldphone_no.length();i++){
                                if(Character.isDigit((int)oldphone_no.charAt(i))){
                                    count1++;
                                }else{
                                    break;
                                }
                            }
                            if(count1!=10){
                                System.out.println("Invalid Mobile Number.");
                            }else{
                               // b=false;

                            }
                        }else{
                            System.out.println("Mobile Number contains only 10 digits.");
                        }
                    } while (count1 != 10);

                    String sql = "UPDATE users SET phone_no = ? WHERE phone_no = ?";
                    try {
                        PreparedStatement pstmt = con.prepareStatement(sql);
                        pstmt.setString(1, phone_no);
                        pstmt.setString(2, oldphone_no);
                        pstmt.executeUpdate();
                        System.out.println("User Phone Number Change Successfully");
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    
                    
                break;

                default:
                    System.out.println("Invalid Option");
                break;

            }
        } while (choiceInfo != 0);
    }


    static void changeInformationOfAdmin(){
        int choiceInfo;
        do { 
            System.out.println("Press 1 > For Change User Name :");
            System.out.println("Press 2 > For Change User Email :");
            System.out.println("Press 3 > For Change User Password :");
            System.out.println("Press 0 > For Exit :");
            choiceInfo = sc.nextInt();
            switch (choiceInfo) {
                case 1:
                    System.out.println("Enter New Admin Name :");
                    String newUserName = sc.next();
                    System.out.println("Enter Admin ID :");
                    int id = sc.nextInt();
                    try {
                        String sql = "UPDATE administrator SET administrator_name = ? WHERE = administrator_id= ?";
                        PreparedStatement pstmt = con.prepareStatement(sql);
                        pstmt.setString(1, newUserName);
                        pstmt.setInt(2, id);
                        pstmt.executeUpdate();
                        System.out.println("Admin Name Changed Successfully");
                    }catch(Exception e){
                        System.out.println(e);

                    }
                break;

                case 2:
                    System.out.println("Enter New Admin Email :");
                    String newEmail = sc.next();
                    System.out.println("Enter Admin ID :");
                    int id1 = sc.nextInt();
                    try {
                        String sql = "UPDATE administrator SET administrator_email=? WHERE administrator_id=?";
                        PreparedStatement pstmt = con.prepareStatement(sql);
                        pstmt.setString(1, newEmail);
                        pstmt.setInt(2, id1);
                        pstmt.executeUpdate();
                        System.out.println("Admin Email Changed Successfully");
                    }catch(Exception e){
                        System.out.println(e);
                    }
                break;

                case 3:
                    System.out.println("Enter New Admin Password : ");
                    String newPassword = sc.next();
                    System.out.println("Enter Admin Id : ");
                    int id2 = sc.nextInt();
                    try {
                        String sql = "UPDATE users SET password = ? WHERE password = ?";
                        PreparedStatement pstmt = con.prepareStatement(sql);
                        pstmt.setString(1, newPassword);
                        pstmt.setInt(2, id2);
                        pstmt.executeUpdate();
                        System.out.println("User Password Changed Successfully");
                    }catch(Exception e){
                        System.out.println(e);
                    }
                break;

                default:
                    System.out.println("Invalid Choice");
                break;

            }
        } while (choiceInfo != 0);
    }

    // Method to delete a car by car_no {only admin can use this method}
    public static void deleteByCarNo(int carNo) throws SQLException {
        
        String deleteSql = "DELETE FROM car_list WHERE car_no = ?";
        PreparedStatement pstmt = con.prepareStatement(deleteSql);
        pstmt.setInt(1, carNo);
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Car with car no " + carNo + " deleted successfully.");
        } else {
            System.out.println("Car with car no " + carNo + " not found in database.");
        }    
    }

    //Method To Delete Bike By Bike_no
    public static void deleteByBikeNo(int BikeNo) throws SQLException {
        
        String deleteSql = "DELETE FROM bike_list WHERE bike_no = ?";
        PreparedStatement pstmt = con.prepareStatement(deleteSql);
        pstmt.setInt(1, BikeNo);
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Car with car no " + BikeNo + " deleted successfully.");
        } else {
            System.out.println("Car with car no " + BikeNo + " not found in database.");
        }    
    }
    //method to dislay user's info
    public static void displayUsers() throws Exception {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT user_name, email FROM users");
        System.out.println("Users Details.. ");
        while (rs.next()) {
            String user_name = rs.getString("user_name");
            String email = rs.getString("email");
            System.out.println("User Name: " + user_name);
            System.out.println("User Email: " + email);
            System.out.println("--------------------");
        }
    }
    public static void displayAllPayments() throws Exception {
        String sql = "SELECT * FROM payment";
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        System.out.printf("%-10s %-20s %-15s %-15s %-10s%n", "Vehical No", "Username", "Rent Date From", "Rent Date To", "Total Amount");

        while (rs.next()) {
            int vehicalNo = rs.getInt("vehical_no");
            String username = rs.getString("username");
            java.sql.Date rentDateFrom = rs.getDate("rent_date_from");
            java.sql.Date rentDateTo = rs.getDate("rent_date_to");
            double totalAmount = rs.getDouble("total_amount");

            String formattedRentDateFrom = (rentDateFrom != null && !rentDateFrom.toString().equals("0000-00-00")) ? sdf.format(rentDateFrom) : "N/A";
            String formattedRentDateTo = (rentDateTo != null && !rentDateTo.toString().equals("0000-00-00")) ? sdf.format(rentDateTo) : "N/A";

            System.out.printf("%-10d %-20s %-15s %-15s %-10.2f%n", vehicalNo, username, formattedRentDateFrom, formattedRentDateTo, totalAmount);
        }
    }

    //Method To Show All Rental History
    
    
    // Method to book a car for rent
    
    //Method to Book Bike For Rent
    
}

// Data Structure
class Car{
    String car_name;
    int car_no;
    int rent_prize;
    double rating;

    public Car() {
    }

    public Car(String car_name, int car_no, double rating, int rent_prize) {
        this.car_name = car_name;
        this.car_no = car_no;
        this.rating = rating;
        this.rent_prize = rent_prize;
    }

   
}


 class StackCar {
    private static Connection con;
    int top;
    Car car1[] =  new Car[10];
    
    

    // Constructor to initialize the database connection
    public StackCar(Connection con) {
        this.con = con;
    }

    public StackCar() throws  Exception{
        Car car = null;
        Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM car_list ORDER BY rating ");
           // System.out.println("Car Details.. ");
            while (rs.next()) {
                String car_name = rs.getString("car_name");
                int car_no = rs.getInt("car_no");
                int rent_prize = rs.getInt("rent_prize");
                double rating = rs.getDouble("rating");
                car = new Car(car_name, car_no, rating, rent_prize);
                top++;
                car1[top] = car; 
                
            }
    }

    // Method to push a car into the database
    public void push(Car car) throws Exception {
       if (top>=car1.length-1) {

       } else {
            top++;
            car1[top]=car;
            addCar();
       }
            
    }

    void addCar()throws  Exception{
        String sql = "insert into car_list (car_no, car_name, rent_prize, rating) values (?, ? ,?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, car1[top].car_no);
            pstmt.setString(2, car1[top].car_name);
            pstmt.setFloat(3, car1[top].rent_prize);
            pstmt.setDouble(4, car1[top].rating);
            pstmt.executeUpdate();
            System.out.println("Car with car no " + car1[top].car_no + " added successfully.");
    }

    

    // Method to pop a car from the database
    public void  pop() throws SQLException {
        
        // return car;
    }
    ArrayList<Car> cars24 = new ArrayList<>();

    // Method to peek at the top car in the database
    public void peek(int i) throws Exception {
        int temp = 0;

        while(temp!=3){
            if (top-i+1 <= -1) {
                System.out.println("No cars in top 3 list");
                break;
            }else{
                System.out.println("Top "+(temp+1)+" Car "+car1[top-i+1].car_name);
                temp++;
                top--;
            }
        }
  
    }

     public void peep() throws Exception{
        Car car = null;
        Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM car_list ORDER BY rating ");
           // System.out.println("Car Details.. ");
            while (rs.next()) {
                String car_name = rs.getString("car_name");
                int car_no = rs.getInt("car_no");
                int rent_prize = rs.getInt("rent_prize");
                double rating = rs.getDouble("rating");
                car = new Car(car_name, car_no, rating, rent_prize);
                top++;
                car1[top] = car; 
                
            }
    }

    // Method to check if the database stack is empty
    public boolean isEmpty() throws SQLException {
       
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM car_list");
            rs.next();
            int count = rs.getInt("count");
            return (count == 0);
        
    }

    // Method to display all cars in the database stack
    public void displayCarList() throws SQLException {
       
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM car_list ORDER BY rating");
            System.out.println("Car Details.. ");
            while (rs.next()) {
                String car_name = rs.getString("car_name");
                int car_no = rs.getInt("car_no");
                int rent_prize = rs.getInt("rent_prize");
                double rating = rs.getDouble("rating");
                System.out.println("Car Name: " + car_name);
                System.out.println("Car No: " + car_no);
                System.out.println("Rating : "+rating);
                System.out.println("Rent Per Day : " + rent_prize);
                System.out.println("--------------------");
            }
       
    }

    public void display() throws  Exception{
        int i;
        for(i = top ; i>=0 ; i--){
            System.out.println("Car Name : "+car1[i].car_name);
            System.out.println("Car No : "+car1[i].car_no);
            System.out.println("Car Rating : "+car1[i].rating);
            System.out.println("Car Rent Prize : "+car1[i].rent_prize);
        }
    }

     
}


class Bike {
    String bike_name;
    int bike_no;
    double rating;
    int rent_prize;

    // Constructor for Bike class
    public Bike(String bike_name, int bike_no, double rating, int rent_prize) {
        this.bike_name = bike_name;
        this.bike_no = bike_no;
        this.rating = rating;
        this.rent_prize = rent_prize;
    }
}

class StackBike {
    private static Connection con;
    int top;
    Bike bike1[] = new Bike[10];

    // Constructor to initialize the database connection
    public StackBike(Connection con) {
        this.con = con;
    }

    public StackBike() throws Exception {
        Bike bike = null;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM bike_list ORDER BY rating ");
        while (rs.next()) {
            String bike_name = rs.getString("bike_name");
            int bike_no = rs.getInt("bike_no");
            int rent_prize = rs.getInt("rent_prize_bike");
            double rating = rs.getDouble("rating");
            bike = new Bike(bike_name, bike_no, rating, rent_prize);
            top++;
            bike1[top] = bike;
        }
    }

    // Method to push a bike into the database
    public void push(Bike bike) throws Exception {
        if (top >= bike1.length - 1) {
            System.out.println("Stack is full.");
        } else {
            top++;
            bike1[top] = bike;
            addBike();
        }
    }

    void addBike() throws Exception {
        String sql = "INSERT INTO bike_list (bike_no, bike_name, rent_prize_bike, rating) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, bike1[top].bike_no);
        pstmt.setString(2, bike1[top].bike_name);
        pstmt.setFloat(3, bike1[top].rent_prize);
        pstmt.setDouble(4, bike1[top].rating);
        pstmt.executeUpdate();
        System.out.println("Bike with bike no " + bike1[top].bike_no + " added successfully.");
    }

    // Method to pop a bike from the database
    public void pop() throws SQLException {
        // Implement the pop logic if required
    }

    // Method to peek at the top bike in the database
    public void peek(int i) throws Exception {
        int tp = top;
        int temp = 0;

        while (temp != 3) {
            if (tp - i + 1 <= -1) {
                System.out.println("No bikes in top 3 list");
                break;
            } else {
                System.out.println("Top " + (temp + 1) + " Bike " + bike1[tp - i + 1].bike_name);
                temp++;
                tp--;
            }
        }
    }

    public void peep() throws Exception {
        Bike bike = null;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM bike_list ORDER BY rating ");
        while (rs.next()) {
            String bike_name = rs.getString("bike_name");
            int bike_no = rs.getInt("bike_no");
            int rent_prize = rs.getInt("rent_prize_bike");
            double rating = rs.getDouble("rating");
            bike = new Bike(bike_name, bike_no, rating, rent_prize);
            top++;
            bike1[top] = bike;
        }
    }

    // Method to check if the database stack is empty
    public boolean isEmpty() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM bike_list");
        rs.next();
        int count = rs.getInt("count");
        return (count == 0);
    }

    // Method to display all bikes in the database stack
    public void displayBikeList() throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM bike_list ORDER BY bike_no DESC");
        System.out.println("Bike Details.. ");
        while (rs.next()) {
            String bike_name = rs.getString("bike_name");
            int bike_no = rs.getInt("bike_no");
            int rent_prize = rs.getInt("rent_prize_bike");
            double rating = rs.getDouble("rating");
            System.out.println("Bike Name: " + bike_name);
            System.out.println("Bike No: " + bike_no);
            System.out.println("Rating : "+rating);
            System.out.println("Rent Per Day: " + rent_prize);
            System.out.println("--------------------");
        }
    }
}

class SameUserName extends Exception{
    public SameUserName(String message) {
        super(message);
        }
}
