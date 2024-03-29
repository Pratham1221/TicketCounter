package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

// TicketCounter Application
public class TicketCounter implements ActionListener {
    private static final String JSON_STORE = "./data/ticketCounter.json";
    private Organiser currentOrganiser;
    private User currentUser;
    private static int checkUser;
    Scanner input = new Scanner(System.in);
    boolean quit = false;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    JFrame frame;
    JScrollPane scrollPane1;
    JButton myShows;
    JButton addShow;
    JButton openPdf;
    JList<Event> list;
    DefaultListModel<Event> model;
    JPanel panel;
    JSplitPane splitPane;
    JLabel label;
    JLabel label2;
    JLabel label3;
    JLabel label4;
    int count = 0;

    //Effects: Starts the  first frame
    public TicketCounter() throws Exception {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        //loadTicketCounter();

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        firstFrame();
                    }
                }, 2000
        );

        startingWindow();
        //runApp();
    }

    //Effects : Creates the first Logo Screen
    private void startingWindow() {
        frame = new JFrame();
        setFrame();
        ImageIcon logo = new ImageIcon("./data/ticket-counter-logo.png");
        Image image = logo.getImage();
        Image modifiedimage = image.getScaledInstance(800, 800, Image.SCALE_SMOOTH);
        logo = new ImageIcon(modifiedimage);
        JLabel label1 = new JLabel();
        label1.setBounds(0, 0, 800, 800);
        label1.setIcon(logo);
        frame.add(label1);
        frame.setVisible(true);
    }

    //Effects : Creates the login screen
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void firstFrame() {
        frame.dispose();
        frame = new JFrame();
        frame.setTitle("TicketCounter");
        frame.setSize(800, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        if (count == 0) {
            int load = JOptionPane.showConfirmDialog(null, "Do you want to load prevoius data",
                    "loading",JOptionPane.YES_NO_OPTION);
            if (load == 0) {
                loadTicketCounter();
            }
            count++;
        }
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                Iterator i = EventLog.getInstance().iterator();
                while (i.hasNext()) {
                    Events event = (Events)i.next();
                    System.out.println(event.toString());
                }
                int result = JOptionPane.showConfirmDialog(frame,
                        "Do you want to save your progress ?", "saving",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    saveTicketCounter();
                }
            }
        });
        ImageIcon logo = new ImageIcon("./data/logo.jpeg");
        frame.setIconImage(logo.getImage());
        frame.getContentPane().setBackground(new Color(211, 211, 211));
        JLabel label = new JLabel("Welcome to Ticket Counter!");
        label.setBounds(180, 40, 500, 200);
        label.setFont(new Font("MV Boli", Font.BOLD, 30));

        JPanel login1 = createOrganiserLoginPanel();
        JPanel login2 = createUserLoginPanel();
        frame.add(login1);
        frame.add(login2);
        frame.add(label);
        frame.setVisible(true);
        //this.pack();
    }

    //Effects : Reinitialize the global frame
    public void setFrame() {
        frame.setTitle("TicketCounter");
        frame.setSize(800, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        ImageIcon logo = new ImageIcon("./data/logo.jpeg");
        frame.setIconImage(logo.getImage());
        frame.getContentPane().setBackground(new Color(211, 211, 211));
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                Iterator i = EventLog.getInstance().iterator();
                while (i.hasNext()) {
                    Events event = (Events)i.next();
                    System.out.println(event.toString());
                }
            }
        });
    }

    //Effects : Creates a panel and sub text fields for organiser login
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public JPanel createOrganiserLoginPanel() {
        JPanel login = new JPanel();
        JTextField name = new JTextField();
        name.setBounds(20, 70, 130, 20);
        JTextField username = new JTextField();
        username.setBounds(20, 140, 130, 20);
        JLabel userName = new JLabel("Username");
        userName.setForeground(new Color(255, 255, 255));
        userName.setBounds(20, 120, 130, 20);
        JLabel heading = new JLabel("Name");
        heading.setForeground(new Color(255, 255, 255));
        heading.setBounds(20, 50, 130, 20);
        JLabel label = new JLabel("Organiser Account");
        label.setForeground(new Color(255, 255, 255));
        label.setBounds(70, 10, 130, 40);
        login.setBounds(50, 400, 260, 230);
        JButton loginButton = new JButton("Login");
        JButton createButton = new JButton("Create Account");
        loginButton.setBounds(20, 200, 80, 20);
        createButton.setBounds(120, 200, 125, 20);
        loginButton.addActionListener(e -> loginInOrganiser(name.getText(), username.getText()));
        createButton.addActionListener(e -> createAccountOrganiser(name.getText(), username.getText()));
        login.add(userName);
        login.add(heading);
        login.add(username);
        login.add(createButton);
        login.add(loginButton);
        login.add(name);
        login.add(label);
        login.setBackground(Color.darkGray);
        //username.setLayout(null);
        login.setLayout(null);
        return login;
    }

    //Effects : Creates a panel and sub text fields for user login
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public JPanel createUserLoginPanel() {
        JPanel login = new JPanel();
        login.setBounds(50, 400, 260, 230);
        JTextField name = new JTextField();
        name.setBounds(20, 70, 130, 20);
        JTextField username = new JTextField();
        username.setBounds(20, 140, 130, 20);
        JLabel userName = new JLabel("Username");
        userName.setForeground(new Color(255, 255, 255));
        userName.setBounds(20, 120, 130, 20);
        JLabel heading = new JLabel("Name");
        heading.setForeground(new Color(255, 255, 255));
        heading.setBounds(20, 50, 130, 20);
        JLabel label = new JLabel("User Account");
        label.setForeground(new Color(255, 255, 255));
        label.setBounds(90, 10, 130, 40);
        JButton loginButton = new JButton("Login");
        JButton createButton = new JButton("Create Account");
        loginButton.setBounds(20, 200, 80, 20);
        createButton.setBounds(120, 200, 125, 20);
        loginButton.addActionListener(e -> loginInUser(name.getText(), username.getText()));
        createButton.addActionListener(e -> createAccountUser(name.getText(), username.getText()));
        login.add(userName);
        login.add(heading);
        login.add(username);
        login.add(createButton);
        login.add(loginButton);
        login.add(name);
        login.add(label);
        login.setBackground(Color.darkGray);
        login.setBounds(450, 400, 260, 230);
        login.setLayout(null);
        return login;
    }

    //Effects : logs in the user if it is already in the system
    public void loginInUser(String name, String userName) {
        int count = 0;
        if (User.getUsersList().size() == 0) {
            //System.out.println("No username exist, please try again!");
            JOptionPane.showMessageDialog(null, "No such user exists. Try again!",
                    "Login fail", JOptionPane.PLAIN_MESSAGE);
            createUserLoginPanel();
        } else {
            for (int i = 0; i < User.getUsersList().size(); i++) {
                if (User.getUsersList().get(i).getUserName().equals(userName)
                        && User.getUsersList().get(i).getName().equals(name)) {
                    //System.out.println("welcome");
                    currentUser = User.getUsersList().get(i);
                    count++;
                    userFrame();
                }
            }
            if (count == 0) {
                JOptionPane.showMessageDialog(null, "No such user exists. Try again!",
                        "Login fail", JOptionPane.PLAIN_MESSAGE);
                createUserLoginPanel();
            }
        }
    }

    //Effects: Checks if the organiser is already in the system
    public boolean containsInOrganiser(String userName) {
        if (Organiser.getOrganisersList().size() == 0) {
            return false;
        } else {
            for (int i = 0; i < Organiser.getOrganisersList().size(); i++) {
                if (Organiser.getOrganisersList().get(i).getUserName().equals(userName)) {
                    return true;
                }
            }
        }
        return false;
    }

    //Effects: Checks if the user is already in the system
    public boolean containsInUser(String userName) {
        if (User.getUsersList().size() == 0) {
            return false;
        } else {
            for (int i = 0; i < User.getUsersList().size(); i++) {
                if (User.getUsersList().get(i).getUserName().equals(userName)) {
                    return true;
                }
            }
        }
        return false;
    }

    //Effects: logs in the organiser if it is already in the system
    public void loginInOrganiser(String name, String userName) {
        int count = 0;
        if (Organiser.getOrganisersList().size() == 0) {
            //System.out.println("No username exist, please try again!");
            JOptionPane.showMessageDialog(null, "No such user exists. Try again!",
                    "Login fail", JOptionPane.PLAIN_MESSAGE);
            createOrganiserLoginPanel();
        } else {
            for (int i = 0; i < Organiser.getOrganisersList().size(); i++) {
                if (Organiser.getOrganisersList().get(i).getUserName().equals(userName)
                        && Organiser.getOrganisersList().get(i).getName().equals(name)) {
                    //System.out.println("welcome");
                    currentOrganiser = Organiser.getOrganisersList().get(i);
                    count++;
                    organiserFrame();
                }
            }
            if (count == 0) {
                JOptionPane.showMessageDialog(null, "No such user exists. Try again!",
                        "Login fail", JOptionPane.PLAIN_MESSAGE);
                createOrganiserLoginPanel();
            }
        }
    }

    //Effects: Creates a new Organiser account if it does not exist already
    public void createAccountOrganiser(String name, String username) {
        if (containsInOrganiser(username)) {
            JOptionPane.showMessageDialog(null, "User already exists!",
                    "Login fail", JOptionPane.PLAIN_MESSAGE);
            createOrganiserLoginPanel();
        } else {
            Organiser o = new Organiser(name, username);
            currentOrganiser = o;
            //System.out.println("welcome");
            organiserFrame();
        }
    }

    //Effects: Creates a new user account if it does not exist already
    public void createAccountUser(String name, String username) {
        if (containsInUser(username)) {
            JOptionPane.showMessageDialog(null, "User already exists!",
                    "Login fail", JOptionPane.PLAIN_MESSAGE);
            createUserLoginPanel();
        } else {
            User u = new User(name, username);
            currentUser = u;
            //System.out.println("welcome");
            userFrame();
        }
    }

    //Effects: creates the organiser frame
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void organiserFrame() {
        frame.dispose();
        frame = new JFrame();
        setFrame();
        JButton logout = new JButton("Logout");
        logout.setBounds(600, 70, 100, 30);
        logout.addActionListener(e -> firstFrame());
        label = new JLabel("User: " + Character.toUpperCase(currentOrganiser.getName().charAt(0))
                + currentOrganiser.getName().substring(1, currentOrganiser.getName().length()));
        label.setBounds(10, 0, 500, 50);
        label.setFont(new Font("MV Boli", Font.BOLD, 30));
        label2 = new JLabel();
        label2.setBounds(10, 70, 500, 50);
        label2.setFont(new Font("MV Boli", Font.BOLD, 20));
        if (Event.getEventList().size() == 0) {
            label2.setText("There are no shows");
        } else {
            label2.setText("Shows List :");
        }
        label3 = new JLabel();
        list = new JList<Event>();
        model = new DefaultListModel<>();
        splitPane = new JSplitPane();
        panel = new JPanel();
        list.setModel(model);
        for (int i = 0; i < Event.getEventList().size(); i++) {
            model.addElement(Event.getEventList().get(i));
        }
        list.getSelectionModel().addListSelectionListener(e -> {
            Event p = list.getSelectedValue();
            label3.setText("<html>" + p.displayDetailed().replaceAll("<", "&lt;").replaceAll(">",
                    "&gt;").replaceAll("\n", "<br/>") + "</html>");
        });
        splitPane.setBounds(10, 140, 600, 300);
        splitPane.setLeftComponent(new JScrollPane(list));
        panel.add(label3);
        splitPane.setRightComponent(panel);
        myShows = new JButton("MyShows");
        myShows.setBounds(0, 500, 400, 100);
        myShows.addActionListener(this);
        addShow = new JButton("Create Event");
        addShow.setBounds(400, 500, 400, 100);
        addShow.addActionListener(this);
        frame.add(myShows);
        frame.add(logout);
        frame.add(addShow);
        frame.add(splitPane);
        frame.add(label2);
        frame.add(label);
        frame.setVisible(true);
    }

    //Effects: Creates the user frame
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void userFrame() {
        frame.dispose();
        frame = new JFrame();
        setFrame();
        JLabel label = new JLabel("User: " + Character.toUpperCase(currentUser.getName().charAt(0))
                + currentUser.getName().substring(1, currentUser.getName().length()));
        label.setBounds(10, 0, 500, 50);
        label.setFont(new Font("MV Boli", Font.BOLD, 30));
        JLabel info = new JLabel();
        info.setFont(new Font("MV Boli", Font.BOLD, 20));
        info.setBounds(10, 600, 400, 40);
        JButton logout = new JButton("Logout");
        logout.setBounds(600, 70, 100, 30);
        logout.addActionListener(e -> firstFrame());
        JLabel label2 = new JLabel();
        label2.setBounds(10, 70, 500, 50);
        label2.setFont(new Font("MV Boli", Font.BOLD, 20));
        label4 = new JLabel();
        label4.setFont(new Font("MV Boli", Font.BOLD, 10));
        label4.setBounds(10, 600, 600, 50);
        if (Event.getEventList().size() == 0) {
            label2.setText("There are no shows");
        } else {
            label2.setText("Shows List :");
        }
        addShow = new JButton("Buy Ticket");
        addShow.setBounds(400, 500, 400, 100);
        label3 = new JLabel();
        list = new JList<Event>();
        model = new DefaultListModel<>();
        splitPane = new JSplitPane();
        panel = new JPanel();
        list.setModel(model);
        for (int i = 0; i < Event.getEventList().size(); i++) {
            model.addElement(Event.getEventList().get(i));
        }
        list.getSelectionModel().addListSelectionListener(e -> {
            Event p = list.getSelectedValue();
            label3.setText("<html>" + p.displayDetailed().replaceAll("<", "&lt;").replaceAll(">",
                    "&gt;").replaceAll("\n", "<br/>") + "</html>");
            if (!list.isSelectionEmpty()) {
                addShow.addActionListener(e1 -> {
                    try {
                        currentUser.buyTicket(list.getSelectedValue());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    //frame.add(label4);
                });
            }
        });
        addShow.addActionListener(e1 -> {
            if (!list.isSelectionEmpty()) {
                try {
                    currentUser.buyTicket(list.getSelectedValue());
                    info.setText("Thanks for buying the ticket!");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                info.setText("Please select an event!");
            }
            //frame.add(label4);
        });
        splitPane.setBounds(10, 140, 600, 300);
        splitPane.setLeftComponent(new JScrollPane(list));
        panel.add(label3);
        splitPane.setRightComponent(panel);
        myShows = new JButton("MyShows");
        myShows.setBounds(0, 500, 400, 100);
        myShows.addActionListener(e -> userShows());
//        addShow = new JButton("Buy Ticket");
//        addShow.setBounds(400, 500, 400, 100);
//        addShow.addActionListener(this);
        frame.add(splitPane);
        frame.add(info);
        frame.add(logout);
        frame.add(myShows);
        frame.add(addShow);
        frame.add(label2);
        frame.add(label);
        frame.setVisible(true);
    }

    //Effects: Creates a new frame to show all the events of the current user
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void userShows() {
        frame.dispose();
        frame = new JFrame();
        setFrame();
        JButton back = new JButton("Go Back");
        back.setBounds(280, 500, 150, 40);
        back.addActionListener(e -> userFrame());
        openPdf = new JButton("Open Pdf");
        openPdf.setBounds(280, 600, 150, 40);
        label2 = new JLabel("Your Shows: ");
        label2.setBounds(10, 70, 500, 50);
        label2.setFont(new Font("MV Boli", Font.BOLD, 20));
        label3 = new JLabel();
        list = new JList<Event>();
        model = new DefaultListModel<>();
        splitPane = new JSplitPane();
        panel = new JPanel();
        list.setModel(model);
        for (int i = 0; i < currentUser.getMyShows().size(); i++) {
            model.addElement(currentUser.getMyShows().get(i));
        }
        list.getSelectionModel().addListSelectionListener(e -> {
            Event p = list.getSelectedValue();
            label3.setText("<html>" + p.displayDetailed().replaceAll("<", "&lt;").replaceAll(">",
                    "&gt;").replaceAll("\n", "<br/>") + "</html>");
//            if (!list.isSelectionEmpty()) {
//                openPdf.addActionListener(e1 -> {
//                    try {
//                        File file = new File("./" + list.getSelectedValue().getEventName() + ".pdf");
//                        if (file.exists()) {
//                            if (Desktop.isDesktopSupported()) {
//                                Desktop.getDesktop().open(file);
//                            } else {
//                                JOptionPane.showMessageDialog(null, "Not supported!");
//                            }
//                        } else {
//                            JOptionPane.showMessageDialog(null, "File does not exist!");
//                        }
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                });
//            }
        });
        openPdf.addActionListener(e1 -> {
            if (!list.isSelectionEmpty()) {
                try {
                    File file = new File("./" + list.getSelectedValue().getEventName() + ".pdf");
                    if (file.exists()) {
                        if (Desktop.isDesktopSupported()) {
                            Desktop.getDesktop().open(file);
                        } else {
                            JOptionPane.showMessageDialog(null, "Not supported!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "File does not exist!");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        splitPane.setBounds(10, 140, 600, 300);
        splitPane.setLeftComponent(new JScrollPane(list));
        panel.add(label3);
        splitPane.setRightComponent(panel);
        frame.add(openPdf);
        frame.add(splitPane);
        frame.add(label2);
        frame.add(back);
        frame.setVisible(true);
    }

    //Effects: Creates a new frame to show all the events of the current organiser
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void organiserShows() {
        frame.dispose();
        frame = new JFrame();
        setFrame();
        JButton back = new JButton("Go Back");
        back.setBounds(280, 500, 150, 40);
        back.addActionListener(e -> organiserFrame());
        label2 = new JLabel("Your Shows: ");
        label2.setBounds(10, 70, 500, 50);
        label2.setFont(new Font("MV Boli", Font.BOLD, 20));
        label3 = new JLabel();
        list = new JList<Event>();
        model = new DefaultListModel<>();
        splitPane = new JSplitPane();
        panel = new JPanel();
        list.setModel(model);
        for (int i = 0; i < currentOrganiser.getMyShows().size(); i++) {
            model.addElement(currentOrganiser.getMyShows().get(i));
        }
        list.getSelectionModel().addListSelectionListener(e -> {
            Event p = list.getSelectedValue();
            label3.setText("<html>" + p.displayDetailed().replaceAll("<", "&lt;").replaceAll(">",
                    "&gt;").replaceAll("\n", "<br/>") + "</html>");
        });
        splitPane.setBounds(10, 140, 600, 300);
        splitPane.setLeftComponent(new JScrollPane(list));
        panel.add(label3);
        splitPane.setRightComponent(panel);
        frame.add(splitPane);
        frame.add(label2);
        frame.add(back);
        frame.setVisible(true);
//        textarea = new JTextArea(5, 20);
//        textarea.setBackground(Color.WHITE);
//        textarea.setForeground(new Color(0, 0, 0));
//
//        textarea.setBounds(10, 140, 600, 300);
//        textarea.setFont(new Font("Arial", Font.ITALIC, 15));
//        scrollPane1 = new JScrollPane(textarea);
//
//        for (Event event : currentOrganiser.getMyShows()) {
//            textarea.append(event.toString() + "\n");
//        }
        //scrollPane1.setBounds(10, 140, 600, 300);
//        scrollPane1.setBorder(BorderFactory.createEmptyBorder());
//        scrollPane1.setViewportBorder(null);
//
//        textarea.setEditable(false);
//        frame.add(scrollPane1);
//        frame.setVisible(true);

    }

    //Effects: Creates a new frame for the organiser to add new events
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void addShow() {
        frame.dispose();
        frame = new JFrame();
        setFrame();
        JLabel info = new JLabel();
        info.setFont(new Font("MV Boli", Font.BOLD, 20));
        info.setBounds(10, 600, 400, 40);
        JPanel addEvent = new JPanel();
        label2 = new JLabel("Add Event: ");
        label2.setBounds(10, 70, 500, 50);
        label2.setFont(new Font("MV Boli", Font.BOLD, 20));
        addEvent.setBounds(10, 130, 300, 300);
        addEvent.setBackground(Color.darkGray);
        JTextField event = new JTextField();
        event.setBounds(20, 40, 130, 20);
        JTextField tickets = new JTextField();
        tickets.setBounds(20, 80, 130, 20);
        JLabel ticketsName = new JLabel("No. of Tickets");
        ticketsName.setForeground(new Color(255, 255, 255));
        ticketsName.setBounds(20, 60, 130, 20);
        JLabel eventName = new JLabel("Event Name");
        eventName.setForeground(new Color(255, 255, 255));
        eventName.setBounds(20, 20, 130, 20);
        JTextField date = new JTextField();
        date.setBounds(20, 120, 130, 20);
        JLabel dateName = new JLabel("Date");
        dateName.setForeground(new Color(255, 255, 255));
        dateName.setBounds(20, 100, 130, 20);
        JTextField time = new JTextField();
        time.setBounds(20, 160, 130, 20);
        JLabel timeName = new JLabel("Time");
        timeName.setForeground(new Color(255, 255, 255));
        timeName.setBounds(20, 140, 130, 20);
        JTextArea desc = new JTextArea();
        desc.setBounds(20, 200, 200, 50);
        JLabel descName = new JLabel("Description");
        descName.setForeground(new Color(255, 255, 255));
        descName.setBounds(20, 180, 130, 20);
        JButton add = new JButton("Add Event");
        add.setBounds(600, 300, 100, 50);
        add.addActionListener(e -> {
            currentOrganiser.createEvent(event.getText(), Integer.parseInt(tickets.getText()),
                    desc.getText(), date.getText(), time.getText());
            info.setText("Succesfully added the event!");
        });
        JButton back = new JButton("Go Back");
        back.setBounds(600, 250, 100, 50);
        back.addActionListener(e -> organiserFrame());
        addEvent.add(desc);
        addEvent.add(descName);
        addEvent.add(time);
        addEvent.add(timeName);
        addEvent.add(ticketsName);
        addEvent.add(date);
        addEvent.add(dateName);
        addEvent.add(eventName);
        addEvent.add(tickets);
        addEvent.add(event);
        frame.add(info);
        frame.add(add);
        frame.add(back);
        frame.add(label2);
        frame.add(addEvent);
        addEvent.setLayout(null);
        addEvent.setVisible(true);
        frame.setVisible(true);
    }

    //Effects: Runs the Application
    public void runApp() throws Exception {
        System.out.println("Do you want to load previously saved data?");
        System.out.printf("(1)Yes%n"
                + "(2)No%n");
        int user = input.nextInt();
        if (user == 1) {
            //loadTicketCounter();
            loadTicketCounter();
        }
        while (!quit) {
            menu();
        }
        System.out.println("Do you want to save your data?");
        System.out.printf("(1)Yes%n"
                + "(2)No%n");
        user = input.nextInt();
        if (user == 1) {
            saveTicketCounter();
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
            //organisersList.add(o);
            System.out.println("Congratulations you have joined TicketCounter!");
            logInScreen();
        } else {
            System.out.print("Enter your name: ");
            String name = input.nextLine();
            String userName = checkUserName();
            User u = new User(name, userName);
            currentUser = u;
            //usersList.add(u);
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
            if (User.getUsersList().size() == 0) {
                matched = false;
            } else {
                for (int i = 0; i < User.getUsersList().size(); i++) {
                    if (User.getUsersList().get(i).getUserName().equals(userName)) {
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
            if (Organiser.getOrganisersList().size() == 0) {
                matched = false;
            } else {
                for (int i = 0; i < Organiser.getOrganisersList().size(); i++) {
                    if (Organiser.getOrganisersList().get(i).getUserName().equals(userName)) {
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
    public void logInOrganiser() throws Exception {
        boolean accepted = false;
        String username = null;
        while (!accepted) {
            System.out.print("Enter your username: ");
            username = input.nextLine();
            if (Organiser.getOrganisersList().size() == 0) {
                System.out.println("No username exist, please try again!");
                accepted = false;
            } else {
                for (int i = 0; i < Organiser.getOrganisersList().size(); i++) {
                    if (Organiser.getOrganisersList().get(i).getUserName().equals(username)) {
                        accepted = true;
                        currentOrganiser = Organiser.getOrganisersList().get(i);
                        break;
                    }
                    accepted = false;
                }
                if (accepted == false) {
                    System.out.println("No username exist, please try again!");
                }
            }
        }
        logInScreen();
    }

    //Effects : Asks for users credentials to make a user account
    public void logInUser() throws Exception {
        boolean accepted = false;
        String username = null;
        while (!accepted) {
            System.out.print("Enter your username: ");
            //input.nextLine();
            username = input.nextLine();
            if (User.getUsersList().size() == 0) {
                System.out.println("No username exist, please try again!");
                accepted = false;
            } else {
                for (int i = 0; i < User.getUsersList().size(); i++) {
                    if (User.getUsersList().get(i).getUserName().equals(username)) {
                        accepted = true;
                        currentUser = User.getUsersList().get(i);
                        break;
                    }
                    accepted = false;
                }
                if (accepted == false) {
                    System.out.println("No username exist, please try again!");
                }
            }
        }
        //System.out.println("Welcome Back");
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
            //userMessages();
        } else {
            logInScreen();
        }
    }

    //Effects: Displays users messages
//    public void userMessages() throws Exception {
//        if (currentUser.getMessages().size() == 0) {
//            System.out.println("You have no messages!");
//        } else {
//            for (int i = 0; i < currentUser.getMessages().size(); i++) {
//                System.out.println(currentUser.getMessages().get(i));
//            }
//        }
//        System.out.println("Press enter to back");
//        input.nextLine();
//        userMenu();
//    }

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
    public void modifyOrganiserEvent(Event e) throws Exception {
        System.out.printf("(1) Modify Name%n" + "(2) Modify Date/Time%n" + "(3) Modify Tickets Available%n"
                + "(4) Modify Event Description%n" + "(5) Go Back%n");
        int user = input.nextInt();
        input.nextLine();
        if (user == 1) {
//            System.out.print("Enter the new Event Name: ");
//            String name = input.nextLine();
//            e.setEventName(name);
            //currentOrganiser.notifyChange(e, "Event Name", name);
            modifyName(e);
        } else if (user == 2) {
            modifyDateTime(e);
            //currentOrganiser.notifyChange(e, "date/time", date + " " + time);
        } else if (user == 3) {
            modifyTicketsAvailable(e);
        } else if (user == 4) {
            modifyDescription(e);
        } else {
            chooseOrganiserAction(e);
        }
        chooseOrganiserAction(e);
    }

    //Effects: modify the name of the event
    public void modifyName(Event e) {
        System.out.print("Enter the new Event Name: ");
        String name = input.nextLine();
        e.setEventName(name);
        //currentOrganiser.notifyChange(e, "Event Name", name);
        System.out.println("Change made Successfully");
    }

    //Effects: Modifies the date and time of the event
    public void modifyDateTime(Event e) {
        System.out.print("Enter the new date(dd-mm-yyyy): ");
        String date = input.nextLine();
        e.setDate(date);
        System.out.print("Enter the new time(eg. 7:30-9:30): ");
        String time = input.nextLine();
        e.setTime(time);
        System.out.println("Change made Successfully");
    }

    //Effects: Modify the description of the event
    public void modifyDescription(Event e) {
        System.out.println("Please write a new description: ");
        String desc = input.nextLine();
        e.setDescription(desc);
        System.out.println("Change made Successfully");
    }

    //Effects: modify the tickets available of the event
    public void modifyTicketsAvailable(Event e) {
        System.out.print("Enter the new amount of Tickets: ");
        int tickets = input.nextInt();
        input.nextLine();
        e.setTickets(tickets);
        System.out.println("Change made Successfully");
    }

    //Effects: Writes the all the program data in a json file and saves it
    private void saveTicketCounter() {
        try {
            jsonWriter.open();
            jsonWriter.write();
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //Effects: Loads the saved data from a json file
    private void loadTicketCounter() {
        try {
            jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //Effects: Checks button clicks and choose the desired outcome
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == myShows) {
            organiserShows();
        }
        if (e.getSource() == addShow) {
            addShow();
        }

    }

//    public JSONArray organisersListToJson() {
//        JSONArray jsonArray = new JSONArray();
//
//        for (User t : attendees) {
//            jsonArray.put(t.toJson());
//        }
//
//        return jsonArray;
//    }

}
