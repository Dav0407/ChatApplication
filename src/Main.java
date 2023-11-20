import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.*;

public class Main {
    public static Map.Entry<String, List<String>> CURRENT_ACCOUNT;

    public static void main(String[] args) {
        int button = 0;
        String email, password, name;
        Scanner scanner = new Scanner(System.in);
        AccountManager accountManager = new AccountManager();
        InputChecker inputChecker = new InputChecker();
        Logger logger = Logger.getLogger("Sign up logger");
        logger.setUseParentHandlers(false);
        Handler handler = new ConsoleHandler();
        LogRecord logRecord1 = new LogRecord(Level.INFO, "Signed up successfully.");
        LogRecord logRecord2 = new LogRecord(Level.INFO, "Signed in successfully.");
        LogRecord logRecord3 = new LogRecord(Level.INFO, "Message sent successfully.");
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);


        LocalDateTime localDateTime;
        try {
            System.out.println("Welcome to Chat App");
            System.out.println("1->Sign up");
            System.out.println("2->Sign in");
            button = Integer.parseInt(scanner.nextLine());

            switch (button) {
                case 1 -> {

                    System.out.println("Enter your full name: ");
                    name = scanner.nextLine();


                    System.out.println("Enter a valid email: ");
                    email = scanner.nextLine();
                    while (!inputChecker.isValidEmail(email)) {
                        System.out.println("You have entered an invalid email, please try again\nEmail: ");
                        email = scanner.nextLine();
                    }

                    System.out.println("Enter a strong password: ");
                    password = scanner.nextLine();
                    while (!inputChecker.isStrongPassword(password)) {
                        System.out.println("Password is weak\n" +
                                "Include digits, uppercase and lowercase charter," +
                                " at least one of @#$%^&+=!_ and with minimum length of 8 symbols: ");
                        password = scanner.nextLine();
                    }
                    localDateTime = LocalDateTime.now();
                    accountManager.addToMap(email, password, localDateTime, name);
                    accountManager.saveUsers();
                    CURRENT_ACCOUNT = accountManager.findAccount(email, password);
                    logger.log(logRecord1);
                }
                case 2 -> {
                    System.out.println("Enter your email: ");
                    email = scanner.nextLine();
                    System.out.println("Verify your password: ");
                    password = scanner.nextLine();
                    accountManager.putUsersToMap();
                    if (accountManager.doesAccountExist(email, password)) {
                        CURRENT_ACCOUNT = accountManager.findAccount(email, password);
                        System.out.printf("Hi, %s !\n", CURRENT_ACCOUNT.getValue().get(2));
                        logger.log(logRecord2);
                    } else System.out.println("Email or password is incorrect.");
                }
            }
            System.out.println("1->Send a message");
            System.out.println("2->Display inbox");
            button = Integer.parseInt(scanner.nextLine());
            List<List<String>> messages = new ArrayList<>();
            String receiverEmail, senderEmail, text;
            switch (button) {
                case 1 -> {
                    System.out.println("Please enter the receiver's email: ");
                    receiverEmail = scanner.nextLine();
                    senderEmail = CURRENT_ACCOUNT.getKey();
                    System.out.println("Write your message: ");
                    text = scanner.nextLine();
                    localDateTime = LocalDateTime.now();
                    accountManager.putMessagesToMap(receiverEmail, senderEmail, text, localDateTime);
                    accountManager.saveMessages();
                    logger.log(logRecord3);
                }
                case 2 -> {
                    accountManager.readMessages();
                    messages = accountManager.getMessages(CURRENT_ACCOUNT.getKey());
                    int count = 0;
                    for (List<String> entry : messages) {
                        System.out.printf("Message №%d", count + 1);
                        System.out.println("\n\n\nSender: " + entry.get(1) + "\n\n");
                        System.out.println("Message:\n" + textFormatter(entry.get(2)) + "\n\n");
                        System.out.println("Date:\n" + entry.get(3));
                        System.out.println("________________________________________________________________________");
                        count++;
                    }
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
        }
    }

    private static String textFormatter(String oneLineString) {
        String[] parts = oneLineString.split("\\.");

        for (int i = 0; i < parts.length; i++)
            parts[i] = parts[i].trim() + ".";

        return String.join("\n", parts);
    }
}
