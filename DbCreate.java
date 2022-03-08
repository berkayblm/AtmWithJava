package Mysql_JAVA.atm_example;


import java.sql.*;

public class DbCreate {
    private static final String DB_url = "jdbc:mysql://localhost:3306/";
    private static final String username = "root";
    private static final String password = "python123";
    private static Connection conn;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void createDatabase() {
        try {
            conn = DriverManager.getConnection(DB_url, username, password);
            statement = conn.createStatement();
            String query = "CREATE DATABASE IF NOT EXISTS ATMJAVA";
            statement.executeUpdate(query);
            System.out.println("Database created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTable() {
        try {
            statement.executeUpdate("USE ATMJAVA");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Account(" +
                    "id int not null," +
                    "firstname varchar(20)," +
                    "lastname varchar(20)," +
                    "gender varchar(1)," +
                    "cardNo int not null,"+
                    "currentMoney int ,"+
                    "PRIMARY KEY (id, cardNo),"+
                    "UNIQUE KEY (id, cardNo))");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public static void createAdminTable() {

        String query = "CREATE TABLE IF NOT EXISTS admin("+
                "emp_no int AUTO_INCREMENT,"+
                "firstname varchar(20) not null,"+
                "lastname varchar(20) not null,"+
                "title varchar(30),"+
                "password varchar(16),"+
                "PRIMARY KEY(emp_no))";
        try {
            statement.executeUpdate("USE ATMJAVA");
            statement.executeUpdate(query);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Admin Table successfully created...");

    }

    public static void insertInfoAccount(int id, String name, String surname, String gender, int cardNo, int money) {


            try {
                String query = "INSERT INTO account(id, firstname, lastname, gender, cardNo, currentMoney ) VALUES (?,?,?,?,?,?) ";

                PreparedStatement prpstat = conn.prepareStatement(query);

                prpstat.setInt(1, id);
                prpstat.setString(2, name);
                prpstat.setString(3, surname);
                prpstat.setString(4, gender);
                prpstat.setInt(5, cardNo);
                prpstat.setInt(6, money);

                prpstat.execute();
                System.out.println("Added successfully");

            } catch (SQLException e) {
                System.out.println("id:" + id + " or card no: " + cardNo + " already exists.");
            }

    }

    public static void insertInfoAdmin(int emp_no, String first, String last, String title, String pwd) {

        try {
            String query = "INSERT INTO Admin(emp_no, firstname, lastname, title, password) VALUES(?,?,?,?,?)";
            PreparedStatement prepstat = conn.prepareStatement(query);
            prepstat.setInt(1,emp_no);
            prepstat.setString(2,first);
            prepstat.setString(3,last);
            prepstat.setString(4,title);
            prepstat.setString(5,pwd);

            prepstat.execute();
            System.out.println("Added successfully");

        }
        catch (SQLException e) {

            System.out.println("emp_no:" + emp_no +  " already exists.");

        }

    }
    public static boolean checkAdminIfExists(int emp_no) {
        try {
            String query = "SELECT firstname, lastname FROM Admin WHERE emp_no ="+ emp_no;
            resultSet = statement.executeQuery(query);
            if (resultSet.next() == false) {
                return false;
            }

        } catch (SQLException e) {
            return false;
        }
        return true;
    }


    public static boolean checkUserIfExists(int cardNo) {
        try {
            String query = "SELECT firstname, lastname FROM account WHERE cardNo ="+ cardNo;
            resultSet = statement.executeQuery(query);

            if(resultSet.next() == false) {
                return false;
            }


        } catch (SQLException e) {
            //System.out.println(cardNo + " does not exist!");
            return false;
        }

        return true;
    }




    public static void checkUserAccount(int cardno) {
        String query = "SELECT * FROM account WHERE cardNo =" + cardno;
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("First Name: " + resultSet.getString("firstname"));
                System.out.println("Last Name: " + resultSet.getString("lastname"));
                System.out.println("Gender: " + resultSet.getString("gender"));
                System.out.println("Card No: " + resultSet.getInt("cardNo"));
                System.out.println("Current Money: " + resultSet.getInt("currentMoney"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void checkBalance(int cardNo) {
        String query = "SELECT currentMoney FROM account WHERE cardNo = " + cardNo;
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println("Current Balance = " + resultSet.getInt(1));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void addMoney(int amount, int cardNo) {

        String query = "SELECT currentMoney FROM account WHERE cardNo = " + cardNo;
        try {
            resultSet = statement.executeQuery(query);
            resultSet.next();
            int currentBalance = resultSet.getInt(1);
            int newBalance = currentBalance + amount;
            System.out.println(newBalance);
            String query2 = "UPDATE account SET currentMoney = ? WHERE cardNo = ?";

            PreparedStatement prepstat = conn.prepareStatement(query2);
            prepstat.setInt(1,newBalance);
            prepstat.setInt(2,cardNo);
            prepstat.execute();
            System.out.println("Your balance has been successfully updated.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public static boolean getMoney(int amount, int cardNo) {
        String query = "SELECT currentMoney FROM account WHERE cardNo = " + cardNo;
        try {
            resultSet = statement.executeQuery(query);
            resultSet.next();
            int currentBalance = resultSet.getInt(1);
            if (currentBalance < amount) {
                return false;
            }

            int newBalance = currentBalance - amount;
            System.out.println(newBalance);
            String query2 = "UPDATE account SET currentMoney = ? WHERE cardNo = ?";

            PreparedStatement prepstat = conn.prepareStatement(query2);
            prepstat.setInt(1,newBalance);
            prepstat.setInt(2,cardNo);
            prepstat.execute();


        }
        catch (SQLException e) {
            System.out.println("No such account exists");
            return false;
        }
        return true;
    }

    public static boolean transferMoney(int amount, int from, int to) {

        if (getMoney(amount, from) == false) {
            System.out.println("You don't have enough cash in your account..");
            return false;
        }

        else {

            String query2 = "SELECT currentMoney FROM account WHERE cardNo = " + to;
            try {
                resultSet = statement.executeQuery(query2);
                resultSet.next();
                int balanceTo = resultSet.getInt(1);
                int newBalance = balanceTo + amount;

                String query3 = "UPDATE account SET currentMoney = ? WHERE cardNo = ?";
                PreparedStatement prepstat = conn.prepareStatement(query3);
                prepstat.setInt(1,newBalance);
                prepstat.setInt(2,to);
                prepstat.execute();


            }
            catch (SQLException e) {
                System.out.println("No such account found...");
                addMoney(amount, from); // money gets back to account
                return false;
            }

        }

        return true;
        }

        public static void checkWholeAccounts() {

            try {
                String query = "SELECT * FROM account";
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    System.out.println(resultSet.getRow());

                    System.out.println(" | "+resultSet.getString(1) +" | " + resultSet.getString(2)
                    +" | "+resultSet.getString(3)+" | "+resultSet.getString(4)+" | "+resultSet.getString(5)
                    +" | "+resultSet.getString(6));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        public static void dropAnAccount(int card) {
            try {
                String query = "DELETE FROM account WHERE cardNo= ?";
                PreparedStatement prepstat = conn.prepareStatement(query);
                prepstat.setInt(1,card);
                prepstat.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }



            System.out.println(card + " successfully deleted!");

        }



}
