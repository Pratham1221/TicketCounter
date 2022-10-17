package ui;

import model.Event;
import model.Organiser;
import model.User;

import java.util.ArrayList;
import java.util.Scanner;

public class TicketCounter {
    private ArrayList<User> usersList = new ArrayList<User>();
    private ArrayList<Organiser> organisersList = new ArrayList<Organiser>();
    private Organiser currentOrganiser;
    private User currentUser;
    private static int checkUser;
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
                + "Organiser : Creates events  |   User : Buy Shows%n");
        int menuInput = input.nextInt();
        if (menuInput == 1) {
            checkUser = 0;
            organiserLogin();
        } else if (menuInput == 2) {
            checkUser = 1;
            userLogin();
        } else {
            quit = true;
        }
    }

    public void organiserLogin() {
        System.out.printf("(1) Create Organiser Account%n"
                + "(2) Log in into Current Account%n"
                + "(3) Main menu%n"
                + "If you are using the software for the first time please \"create account\"%n");
        int userInput = input.nextInt();
        input.nextLine();
        if (userInput == 1) {
            createAccount();
        } else if (userInput == 2) {
            logInOrganiser();
        } else {
            menu();
        }

    }

    public void userLogin() {
        System.out.printf("(1) Create Your Account%n"
                + "(2) Log in into Current Account%n"
                + "(3) Main menu%n"
                + "If you are using the software for the first time please \"create account\"%n");
        int userInput = input.nextInt();
        input.nextLine();
        if (userInput == 1) {
            createAccount();
        } else if (userInput == 2) {
            logInUser();
        } else {
            menu();
        }
    }

    public void createAccount() {
        if (checkUser == 0) {
            System.out.print("Enter your name: ");
            String name = input.nextLine();
            String userName = createUserName();
            Organiser o = new Organiser(name, userName);
            currentOrganiser = o;
            organisersList.add(o);
            System.out.println("Congratulations you have joined TicketCounter!");
            logInScreen();
        } else {
            System.out.print("Enter your name: ");
            String name = input.nextLine();
            String userName = checkUserName();
            User u = new User(name, userName);
            currentUser = u;
            usersList.add(u);
            System.out.println("Congratulations you have joined TicketCounter!");
            logInScreen();
        }
    }

    public String checkUserName() {
        String userName = null;
        boolean matched = true;
        while (matched) {
            System.out.print("Please enter a username for your account: ");
            userName = input.nextLine();
            if (usersList.size() == 0) {
                matched = false;
            } else {
                for (int i = 0; i < usersList.size(); i++) {
                    if (usersList.get(i).getUserName().equals(userName)) {
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
    public void logInOrganiser() {
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
        logInScreen();
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void logInUser() {
        boolean accepted = false;
        String username = null;
        while (!accepted) {
            System.out.print("Enter your username: ");
            //input.nextLine();
            username = input.nextLine();
            if (usersList.size() == 0) {
                System.out.println("No username exist, please try again!");
                accepted = false;
            } else {
                for (int i = 0; i < usersList.size(); i++) {
                    if (usersList.get(i).getUserName().equals(username)) {
                        accepted = true;
                        currentUser = usersList.get(i);
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
        logInScreen();
    }

    public void logInScreen() {
        if (checkUser == 0) {
            System.out.println("User: " + currentOrganiser.getUserName());
            System.out.printf("(1) Organiser Tab%n"
                    + "(2) Login Screen%n");
            int user = input.nextInt();
            input.nextLine();
            if (user == 1) {
                organiserMenu();
            } else {
                menu();
            }
        } else {
            System.out.println("User: " + currentUser.getUserName());
            System.out.printf("(1) User Tab%n"
                    + "(2) Login Screen%n");
            int user = input.nextInt();
            input.nextLine();
            if (user == 1) {
                userMenu();
            } else {
                menu();
            }
        }
    }

    public void organiserMenu() {
        System.out.printf("(1) Create new event%n"
                + "(2) Show/Manage my events%n"
                + "(3) Main menu%n");
        int userInput = input.nextInt();
        input.nextLine();
        if (userInput == 1) {
            addEvent();
        } else if (userInput == 2) {
            organiserEvents();
        } else {
            logInScreen();
        }
    }

    public void addEvent() {
        System.out.print("Enter the event name: ");
        String eventName = input.nextLine();
        System.out.print("Enter the number of tickets available: ");
        int tickets = input.nextInt();
        input.nextLine();
        System.out.println("Enter a brief description of the event.(Use \"\\n\" to go to next line)");
        String description = input.nextLine();
        System.out.print("Enter the date of the event (dd-mm-yyyy) : ");
        String date = input.nextLine();
        System.out.print("Enter the time of the event (for example: 6:30-7:30) : ");
        String time = input.nextLine();
        currentOrganiser.createEvent(eventName, tickets, description, date, time);
        System.out.println("Your event has been successfully created");
        organiserMenu();
    }

    public void userMenu() {
        System.out.printf("(1) Show all events%n"
                + "(2) Show/Manage my events%n"
                + "(3) Check messages%n"
                + "(4) Main Menu%n");
        int userInput = input.nextInt();
        input.nextLine();
        if (userInput == 1) {
            showEvent();
        } else if (userInput == 2) {
            userEvents();
        } else if (userInput == 3) {
            userMessages();
        } else {
            logInScreen();
        }
    }

    public void userMessages() {
        if (currentUser.getMessages().size() == 0) {
            System.out.println("You have no messages!");
        } else {
            for (int i = 0; i < currentUser.getMessages().size(); i++) {
                System.out.println(currentUser.getMessages().get(i));
            }
        }
        System.out.println("Press enter to back");
        input.nextLine();
        userMenu();
    }

    public void userEvents() {
        System.out.println(currentUser.allEvents());
        if (currentUser.getMyShows().size() == 0) {
            System.out.printf("(2) Go Back%n");
        } else {
            System.out.printf("(1) Choose a show%n" + "(2) Go Back%n");
        }
        int user = input.nextInt();
        input.nextLine();
        if (currentUser.getMyShows().size() == 0) {
            user = 2;
        }
        if (user == 1) {
            System.out.print("Enter the number of the Show to choose: ");
            user = input.nextInt();
            input.nextLine();
            Event selectedEvent = currentUser.getMyShows().get(user - 1);
            System.out.println(selectedEvent.displayDetailed());
            userMenu();
        } else {
            userMenu();
        }

    }

    public void showEvent() {
//        if (Event.getEventList().size() == 0) {
//            System.out.println("There are no events");
//            System.out.printf("(2) Go Back%n");
//        } else {
//            int[] arr = new int[Event.getEventList().size()];
//            for (int i = 0; i < arr.length; i++) {
//                arr[i] = i + 1;
//            }
//            for (int i = 0; i < Event.getEventList().size(); i++) {
//                System.out.println(arr[i] + " " + Event.getEventList().get(i).display());
//            }
//            System.out.printf("(1) Choose a show%n" + "(2) Go Back%n");
//        }
        System.out.println(Event.showEvent());
        if (Event.getEventList().size() == 0) {
            System.out.printf("(2) Go Back%n");
        } else {
            System.out.printf("(1) Choose a show%n" + "(2) Go Back%n");
        }
        int user = input.nextInt();
        input.nextLine();
        if (Event.getEventList().size() == 0) {
            user = 2;
        }
        if (user == 1) {
            System.out.print("Enter the number of the Show to choose: ");
            user = input.nextInt();
            input.nextLine();
            Event selectedEvent = Event.getEventList().get(user - 1);
            System.out.println(selectedEvent.displayDetailed());
            buyTickets(selectedEvent);
        } else {
            userMenu();
        }
    }

    public void buyTickets(Event e) {
        System.out.printf("(1) Buy ticket for this Show%n"
                + "(2) Go Back%n");
        int user = input.nextInt();
        input.nextLine();
        if (user == 1) {
            System.out.println(currentUser.buyTicket(e));
            userMenu();
        } else {
            showEvent();
        }
    }

    public void organiserEvents() {
        System.out.println(currentOrganiser.allEvents());
        if (currentOrganiser.getMyShows().size() == 0) {
            System.out.printf("(2) Go Back%n");
        } else {
            System.out.printf("(1) Choose a show%n" + "(2) Go Back%n");
        }
        int user = input.nextInt();
        input.nextLine();
        if (currentOrganiser.getMyShows().size() == 0) {
            user = 2;
        }
        if (user == 1) {
            System.out.print("Enter the number of the Show to choose: ");
            user = input.nextInt();
            input.nextLine();
            Event selectedEvent = currentOrganiser.getMyShows().get(user - 1);
            System.out.println(selectedEvent.displayDetailed());
            chooseOrganiserAction(selectedEvent);
        } else {
            organiserMenu();
        }
    }

    public void chooseOrganiserAction(Event e) {
        System.out.printf("(1) Modify Event(Will send a message about the modification to all attendees)%n"
                + "(2) Cancel Event(Will notify the attendees as well)%n"
                + "(3) Go Back%n");
        int user = input.nextInt();
        input.nextLine();
        if (user == 1) {
            modifyOrganiserEvent(e);
        } else if (user == 2) {
            currentOrganiser.cancelShow(e);
            System.out.println("Your event has been cancelled and all the attendees have been notified.");
            organiserEvents();
        } else {
            organiserEvents();
        }
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void modifyOrganiserEvent(Event e) {
        System.out.printf("(1) Modify Name%n" + "(2) Modify Date/Time%n" + "(3) Modify Tickets Available%n"
                + "(4) Modify Event Description%n" + "(5) Go Back%n");
        int user = input.nextInt();
        input.nextLine();
        if (user == 1) {
            System.out.print("Enter the new Event Name: ");
            String name = input.nextLine();
            e.setEventName(name);
            currentOrganiser.notifyChange(e, "Event Name", name);
            System.out.println("Change made Successfully");
        } else if (user == 2) {
            System.out.print("Enter the new date(dd-mm-yyyy): ");
            String date = input.nextLine();
            e.setDate(date);
            System.out.print("Enter the new time(eg. 7:30-9:30): ");
            String time = input.nextLine();
            e.setTime(time);
            currentOrganiser.notifyChange(e, "date/time", date + " " + time);
            System.out.println("Change made Successfully");
        } else if (user == 3) {
            System.out.print("Enter the new amount of Tickets: ");
            int tickets = input.nextInt();
            input.nextLine();
            e.setTickets(tickets);
            System.out.println("Change made Successfully");
        } else if (user == 4) {
            System.out.println("Please write a new description: ");
            String desc = input.nextLine();
            e.setDescription(desc);
            System.out.println("Change made Successfully");
        } else {
            chooseOrganiserAction(e);
        }
        chooseOrganiserAction(e);
    }

//    public void notifyChange(Event e, String changeType, String change) {
//        String message = "Organiser of " + e.getEventName() + " changed the " + changeType
//                + " to " + change;
//        for (int i = 0; i < e.getAttendees().size(); i++) {
//            e.getAttendees().get(i).getMessages().add(message);
//        }
//    }

    //public void cancelOrganiserEvent(Event e) {
//        String message = "Organiser of " + e.getEventName() + " has cancelled the event\nIt has been removed "
//                + "from your events list";
//        for (int i = 0; i < e.getAttendees().size(); i++) {
//            e.getAttendees().get(i).getMessages().add(message);
//        }
//        currentOrganiser.cancelShow(e);
//        System.out.println("Your event has been cancelled and all the attendees have been notified.");
//        chooseOrganiserAction(e);
   // }
}
