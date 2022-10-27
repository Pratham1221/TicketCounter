package ui;

import model.Event;
import model.Organiser;
import model.User;
import org.json.JSONArray;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// TicketCounter Application
public class TicketCounter {
    private static final String JSON_STORE = "./data/workroom.json";
//    private ArrayList<User> usersList = new ArrayList<User>();
//    private ArrayList<Organiser> organisersList = new ArrayList<Organiser>();
    private Organiser currentOrganiser;
    private User currentUser;
    private static int checkUser;
    Scanner input = new Scanner(System.in);
    boolean quit = false;
    private JsonWriter jsonWriter;
    //private JsonReader jsonReader;

    //Effects: Starts the runApp method
    public TicketCounter() throws Exception {
        jsonWriter = new JsonWriter(JSON_STORE);
        //jsonReader = new JsonReader(JSON_STORE);
        runApp();
    }

    //Effects: Runs the Application
    public void runApp() throws Exception {
        while (!quit) {
            menu();
        }
    }

    //Effects: Processes user input
    public void menu() throws Exception {
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
            saveTicketCounter();
            quit = true;
        }
    }

    // Effects : Creates an Organiser login page
    public void organiserLogin() throws Exception {
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

    // Effects : Creates a user login page
    public void userLogin() throws Exception {
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

    // Effects:  opens a create account page for new users
    public void createAccount() throws Exception {
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

    // Effects : Checks if the username already exists or not and returns the
    //           username if its accepted
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

    // Effects : Checks if the username already exists or not and returns the
    //           username if its accepted
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

    // Effects : asks for organisers credentials to make and account
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void logInOrganiser() throws Exception {
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

    //Effects : Asks for users credentials to make a user account
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void logInUser() throws Exception {
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

    //Effects: Shows the user a login screen
    public void logInScreen() throws Exception {
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

    //Effects : displays the organisers environment
    public void organiserMenu() throws Exception {
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

    //Effects: Add an event to the organisersList and eventsList
    public void addEvent() throws Exception {
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

    //Effects : Shows a users environment
    public void userMenu() throws Exception {
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

    //Effects: Displays users messages
    public void userMessages() throws Exception {
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

    //Effects: Displays user events
    public void userEvents() throws Exception {
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

    //Effects: Displays all the events in the made
    public void showEvent() throws Exception {
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

    //Effects: Shows a menu to user to buy a tickets and if the user buys a tickets
    //         remove one tickets from the total tickets and saves a pdf ticket
    public void buyTickets(Event e) throws Exception {
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

    //Effects: Displays all the events in the organisersList
    public void organiserEvents() throws Exception {
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

    //Effects: Displays all the modification options the organiser can make
    public void chooseOrganiserAction(Event e) throws Exception {
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

    //Effects: Shows a menu to organiser to modify events, once done reverts backs to chooseOrganiserAction()
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void modifyOrganiserEvent(Event e) throws Exception {
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

    private void saveTicketCounter() {
        try {
            jsonWriter.open();
            jsonWriter.write(Event.getEventList());
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    public JSONArray organisersListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (User t : attendees) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

}
