package tienhung.service.service;

import java.util.regex.Pattern;

public class AccountService {

    public boolean registerAccount(String username, String password, String email) {
        if (username == null || username.isBlank()) return false;
        if (password == null || password.length() <= 6) return false;
        if (!isValidEmail(email)) return false;
        return true;
    }

    public boolean isValidEmail(String email) {
        if (email == null) return false;
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(regex, email);
    }

    //phân loại tài khoản theo domain email
    public String getAccountTypeByEmailDomain(String email) {
        if (!isValidEmail(email)) return "Invalid";
        if (email.endsWith("@gmail.com")) return "Personal";
        if (email.endsWith("@company.com")) return "Corporate";
        return "Other";
    }
}
