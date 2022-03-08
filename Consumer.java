package Mysql_JAVA.atm_example;

import java.util.Random;
import java.util.Scanner;

public class Consumer {
    private int id;
    private String name;
    private String surname;
    private String gender;
    private int cardNo;
    private int money;
    private static Scanner scanner = new Scanner(System.in);

    public Consumer() {  //Main const
    }
    public Consumer(int cardno) { // 2. constructor
        this.cardNo = cardno;
    }



    public void addNewCustomer() {
        System.out.println("id: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        this.id = id;
        System.out.println("Name: ");
        String name = scanner.nextLine();
        this.name = name;
        System.out.println("Last name: ");
        String lastname = scanner.nextLine();
        this.surname = lastname;
        System.out.println("Gender: ");
        String gender = scanner.nextLine();
        this.gender = gender;

        System.out.println("Card no (I you want system to create one press 1 otherwise press 2: ");
        String answer = scanner.nextLine();
        int cardno;
        if (answer.equals("1")) {
            cardno = Integer.parseInt(createCardNo());
        }
        else {
            System.out.println("Enter the card no: ");
            cardno = scanner.nextInt();

        }

        boolean exists = DbCreate.checkUserIfExists(cardno);
        while (exists == true) {
            System.out.println(cardno + " already exists. Try again: ");
            cardno = scanner.nextInt();

            exists = DbCreate.checkUserIfExists(cardno);
        }
        this.cardNo = cardno;

        System.out.println("money: ");
        int money = scanner.nextInt();
        this.money = money;


        DbCreate.insertInfoAccount(id,name,surname,gender,cardNo,money);
    }

    public  String createCardNo() {
        Random random = new Random();
        String pwd = "";
        for (int i = 0; i < 7; i++) {
            pwd += String.valueOf(random.nextInt(10));
        }
        return pwd;
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
    }

    public int getCardNo() {
        return cardNo;
    }

    public int getMoney() {
        return money;
    }



}
