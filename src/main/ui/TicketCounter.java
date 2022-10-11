package ui;

import model.Organiser;
import model.User;

import java.util.ArrayList;
import java.util.Scanner;

public class TicketCounter {
    private ArrayList<User> usersList = new ArrayList<User>();
    private ArrayList<Organiser> organisersList = new ArrayList<Organiser>();
    private Organiser currentOrganiser;
    Scanner input = new Scanner(System.in);
    boolean quit = false;

    public TicketCounter() {
        runApp();
    }

    public void runApp() {
        while (!quit) {
            menu();
        }
    }

    public void menu() {
        System.out.printf("Welcome to TicketCounter %n"
                + "1 Organiser %n"
                + "2 User %n"
                + "3 Quit %n"
                + "Type 1 for creating an event and 2 to buy tickets %n");
        int menuInput = input.nextInt();
        if (menuInput == 1) {
            organiserLogin();
        } else if (menuInput == 2) {
            // userLogin();
        } else {
            quit = true;
        }
    }

    public void organiserLogin() {
        System.out.printf("(1) Create Organiser Account%n"
                + "(2) Log in into Current Account%n"
                + "(3) Main menu%n");
        int userInput = input.nextInt();
        input.nextLine();
        if (userInput == 1) {
            createAccount();
        } else if (userInput == 2) {
            logIn();
        } else {
            menu();
        }

    }

    public void createAccount() {
        System.out.print("Enter your name: ");
        String name = input.nextLine();
        String userName = createUserName();
        Organiser o = new Organiser(name, userName);
        currentOrganiser = o;
        organisersList.add(o);
        System.out.println("Congratulations you have joined TicketCounter!");
        organiserMenu();
    }

    public String createUserName() {
        String userName = null;
        boolean matched = true;
        while (matched) {
            System.out.print("Please enter a username for your account: ");
            userName = input.nextLine();
            if (organisersList.size() == 0) {
                matched = false;
            } else {
                for (int i = 0; i < organisersList.size(); i++) {
                    if (organisersList.get(i).getUserName().equals(userName)) {
                        System.out.println("Please try a different username");
                        matched = true;
                        break;
                    }
                    matched = false;
                }
            }
        }
        return userName;
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void logIn() {
        boolean accepted = false;
        String username = null;
        while (!accepted) {
            System.out.print("Enter your username: ");
            //input.nextLine();
            username = input.nextLine();
            if (organisersList.size() == 0) {
                System.out.println("No username exist, please try again!");
                accepted = false;
            } else {
                for (int i = 0; i < organisersList.size(); i++) {
                    if (organisersList.get(i).getUserName().equals(username)) {
                        accepted = true;
                        currentOrganiser = organisersList.get(i);
                        break;
                    }
                    accepted = false;
                }
                if (accepted == false) {
                    System.out.println("No username exist, please try again!");
                }
            }
        }
        System.out.println("Welcome Back");
        organiserMenu();
    }

    public void organiserMenu() {
        System.out.printf("(1) Create new event%n"
                + "(2) Show my events%n"
                + "(3) Main menu%n");
        int userInput = input.nextInt();
//        if (userInput == 1) {
//            addEvent();
//        } else if (userInput == 2) {
//
//        }
        if (userInput == 3) {
            menu();
        }
    }
}
