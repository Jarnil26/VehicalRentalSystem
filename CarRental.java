import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
class SameUserName extends Exception{
    public SameUserName(String message) {
        super(message);
        }
}

public class CarRental {
    static java.sql.Connection con;
    static  Statement st;
    private static Scanner sc = new Scanner(System.in);
    static int comparation;
    static boolean comparation1;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

              
    public static void bookCarForRent(String username) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        // connect to DataBase
        String dburl = "jdbc:mysql://localhost:3306/vehical_rental";
        String dbuser = "root";
        String dbpass ="";
        con = DriverManager.getConnection(dburl, dbuser, dbpass);
        
        System.out.println("Enter Car No to Rent: ");
        int car_no = sc.nextInt();
        sc.nextLine();  // Consume newline

        LocalDate rentDateFrom = null;
        LocalDate rentDateTo = null;

        while (rentDateFrom == null || rentDateTo == null) {
            do{
            try {
                System.out.println("Enter start date (yyyy-mm-dd): ");
                String startDateStr = sc.nextLine();
                rentDateFrom = LocalDate.parse(startDateStr, formatter);
                LocalDate ld = LocalDate.now();
                comparation = ld.compareTo(rentDateFrom);

                while (true) {
                    System.out.println("Enter end date (yyyy-mm-dd): ");
                    String endDateStr = sc.nextLine();
                    rentDateTo = LocalDate.parse(endDateStr, formatter);
        
                    // Check if endDate is before startDate
                    if (rentDateTo.isBefore(rentDateFrom)) {
                        System.out.println("End date cannot be before start date. Please re-enter the dates.");
                    } else {
                        break;
                    }
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in yyyy-mm-dd format.");
            }
            }while(comparation>0);
        }

        // Check if the car is available in the car list
        String checkCarSql = "SELECT * FROM car_list WHERE car_no = ?";
        PreparedStatement pstmt = con.prepareStatement(checkCarSql);
        pstmt.setInt(1, car_no);
        ResultSet rs = pstmt.executeQuery();
    
        if (rs.next()) {
            // Car is available in the list, now check availability for the date range
            if (isCarAvailable(car_no, rentDateFrom, rentDateTo)) {
                // Car is available for the selected date range
                String car_name = rs.getString("car_name");
                int rent_prize = rs.getInt("rent_prize");

                int rentalDays = (int) (java.time.temporal.ChronoUnit.DAYS.between(rentDateFrom, rentDateTo)) + 1;
                int totalAmount = processPayment(rent_prize, rentalDays);

                // Insert payment details
                String insertPaymentSql = "INSERT INTO payment (vehical_no, rent_date_from, rent_date_to, total_amount, username) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertPaymentStmt = con.prepareStatement(insertPaymentSql);
                insertPaymentStmt.setInt(1, car_no);
                insertPaymentStmt.setDate(2, Date.valueOf(rentDateFrom));
                insertPaymentStmt.setDate(3, Date.valueOf(rentDateTo));
                insertPaymentStmt.setInt(4, totalAmount);
                insertPaymentStmt.setString(5, username);
                insertPaymentStmt.executeUpdate();

                System.out.println("Car booked successfully from " + rentDateFrom + " to " + rentDateTo);
                System.out.println("Total amount to be paid: " + totalAmount);
            } else {
                // Car is not available for the selected date range, suggest next available date range
                Date nextAvailableFrom = getNextAvailableFromDate(car_no, Date.valueOf(rentDateTo));
                System.out.println("Car is not available for the selected date range.");
                System.out.println("The car is available from " + nextAvailableFrom + " onwards.");
            }
        } else {
            System.out.println("Car not found in the database.");
        }
    }

    private static boolean isCarAvailable(int car_no, LocalDate rentDateFrom, LocalDate rentDateTo) throws SQLException {
        String checkAvailabilitySql = "SELECT * FROM payment WHERE vehical_no = ? AND (rent_date_from <= ? AND rent_date_to >= ?)";
        PreparedStatement pstmt = con.prepareStatement(checkAvailabilitySql);
        pstmt.setInt(1, car_no);
        pstmt.setDate(2, Date.valueOf(rentDateTo));
        pstmt.setDate(3, Date.valueOf(rentDateFrom));
        ResultSet rs = pstmt.executeQuery();

        return !rs.next();  // If no records are returned, the car is available
    }

    private static Date getNextAvailableFromDate(int car_no, Date rentDateTo) throws SQLException {
        String getNextAvailableSql = "SELECT MAX(rent_date_to) AS last_rent_date FROM payment WHERE vehical_no = ?";
        PreparedStatement pstmt = con.prepareStatement(getNextAvailableSql);
        pstmt.setInt(1, car_no);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next() && rs.getDate("last_rent_date") != null) {
            Date lastRentDate = rs.getDate("last_rent_date");
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(lastRentDate);
            cal.add(java.util.Calendar.DAY_OF_MONTH, 1);  // Next available date is the day after the last rent date
            return new Date(cal.getTimeInMillis());
        } else {
            return new Date(System.currentTimeMillis());  // If no previous rentals, car is available from today
        }
    }

    private static int processPayment(int rent_prize, int rentalDays) {
        int totalRentforvehical = rent_prize * rentalDays; 
        System.out.println("Total rent for " + rentalDays + " days is: " + totalRentforvehical);
        boolean isPaymentDone = false;
        do{
        System.out.println("Press 1 for Debit Card Payment");
        System.out.println("Press 2 for UPI Payment");
        System.out.println("Enter your Payment Type: ");
        int ch = sc.nextInt();
        int flag = 0;
        int payedAmount;

        switch (ch) {
            case 1: 
                while (true) {
                    int flag2 = 0;
                    //System.out.println("Amount to Pay Is : " + price +" For "+rentalDays+" Days");
                    System.out.println("Enter Amount To Pay :");
                    payedAmount = sc.nextInt();
                    System.out.println("Enter your 12 Digit Debit Card Number");
                    String card = sc.next();

                    if (card.length() == 12) {
                        for (int i = 0; i < card.length(); i++) {
                            if (card.charAt(i) >= '0' && card.charAt(i) <= '9') {
                                flag2 = 1;
                                continue;
                            } else {
                                System.out.println("Please Enter a Valid Card Number ");  
                                flag2 = 0;
                                break;
                            }
                        }
                    } else {
                        System.out.println("Please Enter 12-digit Number");
                        continue;
                    }
                    if (flag2 == 1) {
                        System.out.println("Please Enter Your Card CVV: ");
                        String cvv = sc.next();

                        if (cvv.length() == 3) {
                            for (int i = 0; i < cvv.length(); i++) {
                                if (card.charAt(i) >= '0' && card.charAt(i) <= '9') {
                                    flag2 = 1;
                                    continue;
                                } else {
                                    System.out.println("Please Enter a valid CVV Number");
                                    flag2 = 0;
                                    break;
                                }
                            }
                        } else {
                            System.out.println("Please Enter 3 Digit CVV" );
                            flag2 = 0;
                            continue;
                        }
                    }

                    if (flag2 == 1) {
                        //System.out.println("Amount To Pay Is : " + price);
                        get_OTP();
                        System.out.println("Enter OTP: ");
                        int otp_card = sc.nextInt();

                        if (otp_card == a && payedAmount == totalRentforvehical) {
                            flag = 1;
                            System.out.println("Payment Successfully Thanks For Using Shiv-Shakti Vehical Rental System...");
                            isPaymentDone = true;
                            break;
                        } else {
                            System.out.println("Payment Failed!");
                            continue;
                        }
                    }

                }
                break;
            

            // UPI Payment using upi pin of 4 digits only & payment done
            case 2: 
                // UPI Payment
                //System.out.println("Payable Amount is: " + price);
                while (true) {
                    System.out.println("Enter Amount To Pay : ");
                    payedAmount = sc.nextInt();
                    System.out.println("Enter UPI Pin: ");
                    String pass = sc.next();
                    System.out.println(pass);
                    if (pass.length() == 4) {
                        flag = 1;
                        break;
                    } else {
                        System.out.println("Enter 4 digit UPI Pin");
                    }
                }
                if(payedAmount == totalRentforvehical){
                    System.out.println("Payment Successfully Thanks For Using Shiv-Shakti Vehical Rental System...");
                    isPaymentDone = true;
                }

                break;
            
            default: 
                System.out.println("Please Enter Valid Option");
                break;
        }

            }while(isPaymentDone != true);
        return totalRentforvehical;
        }
    
        static int a = 0;
    
        // Sends otp to other window using java swing concept
        public static void get_OTP() {
            JFrame OTP = new JFrame();
            OTP.setBounds(500, 350, 200, 200);
            OTP.setTitle("OTP-One Time Password");
            // OTP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Container c = OTP.getContentPane();
            a = (int) (Math.random() * 10000);
            int ans = countdigi(a);
            if (ans == 3) {
                a *= 10;
            }
            if (ans == 2) {
                a *= 100;
            }
            if (ans == 1) {
                a *= 1000;
            }
            if (ans == 0) {
                a *= 10000;
            }
            JLabel otp = new JLabel();
            otp.setText("OTP: " + a);
            c.add(otp);
            OTP.setVisible(true);
        }
    
        public static int countdigi(int n) {
            int count = 0;
            while (n > 0) {
                count++;
                n /= 10;
            }
            return count;
        }
    }

