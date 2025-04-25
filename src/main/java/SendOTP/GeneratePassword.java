package SendOTP;

import java.security.SecureRandom;



public class GeneratePassword {
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()-_=+<>?";
    static final String ALL_CHARS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARS;

    public String getPassword() {
        return password;
    }

    private String password;

    public GeneratePassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(ALL_CHARS.length());
            password.append(ALL_CHARS.charAt(index));
        }
        this.password = password.toString();
    }
}
