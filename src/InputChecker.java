import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputChecker {
    public boolean isStrongPassword(String password) {
        String strongPasswordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!_])(?=\\S+$).{8,}$";

        Pattern pattern = Pattern.compile(strongPasswordPattern);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public boolean isValidEmail(String email) {
        String validEmailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(validEmailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
