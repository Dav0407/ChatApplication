import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AccountManager {
    private final Map<String, List<String>> users = new HashMap<>(3);
    private final List<String> passwordDateName = new ArrayList<>(3);
    private final List<List<String>> messagesPro = new ArrayList<>(4);

    private final String FILE_PATH = "resources/UserData.txt";
    private final String MESSAGE_FILE_PATH = "resources/Messages.txt";

    public void addToMap(String email, String password, LocalDateTime localDateTime, String name) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d MMM uuuu HH:mm");
        String date = localDateTime.format(dateTimeFormatter);
        passwordDateName.add(password);
        passwordDateName.add(date);
        passwordDateName.add(name);
        users.put(email, passwordDateName);
    }

    public void saveUsers() {
        File file = new File(FILE_PATH);
        users.forEach((k, v) -> {
            try (FileWriter fileWriter = new FileWriter(file, true)) {
                fileWriter.write(" " + k + "\n" + v.get(0) + "\n" + v.get(1) + "\n" + v.get(2) + "\n");
            } catch (IOException e) {
                System.out.println("File not found. Please make sure that you have created a file");
            }
        });
    }

    public void putUsersToMap() {
        users.clear();

        File file = new File(FILE_PATH);
        String key;
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while (bufferedReader.read() != -1) {
                passwordDateName.clear();
                key = bufferedReader.readLine();
                passwordDateName.add(bufferedReader.readLine());
                passwordDateName.add(bufferedReader.readLine());
                passwordDateName.add(bufferedReader.readLine());
                ArrayList<String> temp = new ArrayList<>(passwordDateName);
                users.put(key, temp);
            }
        } catch (IOException e) {
            System.out.println("File not found. Please make sure that you have created a file");
        }
    }

    public boolean doesAccountExist(String email, String password) {
        for (Map.Entry<String, List<String>> entry : users.entrySet()) {
            if (entry.getKey().equals(email)) {
                if (entry.getValue().get(0).equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Map.Entry<String, List<String>> findAccount(String email, String password) {
        for (Map.Entry<String, List<String>> entry : users.entrySet())
            if (entry.getKey().equals(email))
                if (entry.getValue().get(0).equals(password))
                    return entry;
        return null;
    }

    // ________________________________________________________________________

    public void putMessagesToMap(String receiverAccountEmail, String senderAccountEmail, String text, LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d MMM uuuu HH:mm");
        String date = localDateTime.format(dateTimeFormatter);
        messagesPro.add(new ArrayList<>(List.of(receiverAccountEmail, senderAccountEmail, text, date)));
    }

    public void saveMessages() {
        File file = new File(MESSAGE_FILE_PATH);

        messagesPro.forEach((list) -> {
            try (FileWriter fileWriter = new FileWriter(file, true)) {
                // list.get(0) -> receiver's email, list.get(1) -> sender's email, list.get(2) -> text, list.get(3) -> date
                fileWriter.write(" " + list.get(0) + "\n" + list.get(1) + "\n" + list.get(2) + "\n" + list.get(3) + "\n");

            } catch (IOException e) {
                System.out.println("File not found. Please make sure that you have created a file");
            }
        });
    }

    public void readMessages() {
        File file = new File(MESSAGE_FILE_PATH);
        messagesPro.clear();
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while (bufferedReader.read() != -1) {

                List<String> senderTextDate = new ArrayList<>(2);
                senderTextDate.add(bufferedReader.readLine());
                senderTextDate.add(bufferedReader.readLine());
                senderTextDate.add(bufferedReader.readLine());
                senderTextDate.add(bufferedReader.readLine());

                messagesPro.add(senderTextDate);
            }
        } catch (IOException e) {
            System.out.println("File not found. Please make sure that you have created a file");
        }
    }

    public List<List<String>> getMessages(String accountEmail) {
        System.out.println(messagesPro);

        List<List<String>> onlyMessages = new ArrayList<>();
        for (List<String> entry : messagesPro) {
            if (accountEmail.equals(entry.get(0))) {
                onlyMessages.add(entry);
            }
        }
        return onlyMessages;
    }


}