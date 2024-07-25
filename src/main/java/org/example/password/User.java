package org.example.password;

public class User {
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void initPassword(PasswordGenerator passwordGenerator) {
//        RandomPasswordGenerator randomPasswordGenerator = new RandomPasswordGenerator();
        String password = passwordGenerator.generatePassword();

        if (password.length() >= 8 && password.length() <= 12) {
            this.password = password;
        }
    }
}
