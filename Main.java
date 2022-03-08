package Mysql_JAVA.atm_example;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws InterruptedException {
        DbCreate.createDatabase();
        DbCreate.createTable();
        DbCreate.createAdminTable();
        String islemler = "CiftlikBank---Hosgeldiniz... \n" +
                "1. Log in\n"+
                "2. Create an account\n"+
                "3. Admin Panel\n"+
                "Press 'q' to quit...";

        String atmiciIslemler = "1. Check the balance\n"+
                                "2. Put money\n"+
                                "3. Get money\n"+
                                "4. Transfer money to another account\n" +
                                "5. User Information\n"+
                                " Press q to quit.";

        boolean quit = false;
        while (quit != true) {
            System.out.println(islemler);
            String islem = scanner.nextLine();

            if (islem.equals("q")) {
                System.out.println("Quitting...");
                Thread.sleep(2000);
                quit = true;
            }
            else if (islem.equals("1")) {

                System.out.println("Enter the card number: ");
                int card_no = scanner.nextInt();
                scanner.nextLine();
                boolean userexists = DbCreate.checkUserIfExists(card_no);

                if (userexists == true) {
                    Consumer consumer = new Consumer(card_no); // second constructor

                    boolean setquit = false;
                    while (setquit != true) {
                        System.out.println(atmiciIslemler);
                        String newislem = scanner.nextLine();

                        if (newislem.equals("q")) {
                            System.out.println("Logging out...");
                            Thread.sleep(2000);
                            setquit = true;

                        } else if (newislem.equals("1")) {
                            DbCreate.checkBalance(card_no);
                        }

                        else if (newislem.equals("2")) {
                            System.out.println("Enter the amount of money you want to put: ");
                            int amount = scanner.nextInt();
                            scanner.nextLine();
                            DbCreate.addMoney(amount,card_no);

                        }
                        else if (newislem.equals("3")) {
                            System.out.println("Enter the amount of money you want to get: ");
                            int amount = scanner.nextInt();
                            scanner.nextLine();
                            boolean haveCash = DbCreate.getMoney(amount,card_no);
                            if (haveCash == false) {
                                System.out.println("You don't have enough cash in your account...");
                            }
                            else {
                                System.out.println("Your balance has been successfully updated...");
                            }
                        }

                        else if (newislem.equals("4")) {
                            System.out.println("Enter the card number of account to where you want to transfer your money.");
                            int toCardNo = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Enter the amount of money you want to transfer: ");
                            int amount = scanner.nextInt();
                            scanner.nextLine();

                            boolean accountExists = DbCreate.checkUserIfExists(toCardNo);

                            if (accountExists == true) {
                                boolean successfulTransfer = DbCreate.transferMoney(amount, card_no, toCardNo);
                                if (successfulTransfer == true) {
                                    System.out.println("The cash has been successfully transferred to card no : " + toCardNo);
                                }

                            }
                            else {
                                System.out.println(toCardNo + " does not exist.");
                            }
                        }

                        else if (newislem.equals("5")) {
                            DbCreate.checkUserAccount(consumer.getCardNo());
                        }

                    }
                }
                else {
                    System.out.println(card_no + " does not exist.");
                }


            }

            else if (islem.equals("2")) {
                Consumer consumer = new Consumer();
                consumer.addNewCustomer();
            }

            else if (islem.equals("3")) {
                //Admin admin = new Admin();
                String islemAdmin1 = "1.)Log in\n" +
                                    "2.) Create An Admin Account\n"+
                                    "Press q to return main menu.";

                boolean quit2 = false;
                while (quit2 == false) {

                    System.out.println(islemAdmin1);
                    String islem2 = scanner.nextLine();
                    if (islem2.equals("q")) {
                        System.out.println("Returning to main menu...");
                        Thread.sleep(1500);
                        quit2 = true;
                    }

                    else if (islem2.equals("1")) {
                        System.out.println("emp no :");
                        int emp_no = scanner.nextInt();
                        Admin admin = new Admin(emp_no);

                        String islemAdmin3 = "1.) Check the Whole Accounts\n"+
                                "2.) Delete An Account\n"+
                                "3.) Delete The Whole Accounts\n"+
                                "Press q to quit.";

                        boolean exit = false;
                        while (exit == false) {
                            System.out.println(islemAdmin3);
                            String islem3 = scanner.nextLine();
                            if (islem3.equals("q")) {
                                System.out.println("Returning to main menu...");
                                Thread.sleep(1500);
                                exit = true;
                            }
                            else if (islem3.equals("1")) {
                                DbCreate.checkWholeAccounts();

                            }
                            else if (islem3.equals("2")) {
                                System.out.println("Enter the card number of the consumer: ");
                                int card = scanner.nextInt();
                                boolean exists = DbCreate.checkUserIfExists(card);
                                while (exists == false) {
                                    System.out.println("No such an user with card no " + card);
                                    System.out.println("Enter the card number of the consumer: ");
                                    card = scanner.nextInt();
                                    exists = DbCreate.checkUserIfExists(card);
                                }
                                DbCreate.dropAnAccount(card);
                            }
                        }

                    }
                    else if (islem2.equals("2")) {
                        Admin admin = new Admin();
                        quit2 = true;
                    }



                }


            }


        }
    }


}
