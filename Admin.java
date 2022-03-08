package Mysql_JAVA.atm_example;

import java.util.Scanner;

public class Admin extends Consumer {
    private int emp_no;
    private String firstname;
    private String lastname;
    private String title;
    private String password;
    static Scanner scanner = new Scanner(System.in);

    public Admin() {

        this.emp_no = Integer.parseInt(createCardNo());
        System.out.println("Your employee no: " + this.emp_no);
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        this.firstname = name;
        System.out.println("Enter your last name: ");
        String surname = scanner.nextLine();
        this.lastname = surname;
        System.out.println("Job title: ");
        String jobTitle = scanner.nextLine();
        this.title = jobTitle;
        System.out.println("Create a password: ");
        String pwd = scanner.nextLine();
        System.out.println("Verify your password: ");
        String pwd2 = scanner.nextLine();

        while (!pwd.equals(pwd2)) {
            System.out.println("Make sure both passwords are the same!");
            System.out.println("Create a password: ");
            pwd = scanner.nextLine();
            System.out.println("Verify your password: ");
            pwd2 = scanner.nextLine();
        }
        this.password = pwd;

        DbCreate.insertInfoAdmin(emp_no,firstname,lastname,title,pwd);
    }

    public Admin(int emp_no) {

        boolean exists = DbCreate.checkAdminIfExists(emp_no);
        while (exists == false) {
            System.out.println("No such an admin account exists!");
            System.out.println("Enter the employee no: ");
            emp_no = scanner.nextInt();
            exists =  DbCreate.checkAdminIfExists(emp_no);
        }
        //TODO: drop user, check whole accounts, delete whole accounts..
    }



    @Override
    public String createCardNo() {
        return super.createCardNo();
    }
}
