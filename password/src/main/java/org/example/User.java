package org.example;

public class User {
    private  String password;

    //결합도를 낮추기 위해 인터페이스 사용
    public void initPassword(PasswordGenerator passwordGenerator){

        // as-is 강한 결합도
//        RandomPasswordGenerator randomPasswordGenerator = new RandomPasswordGenerator()

        //to-be 낮은 결합도
        String password = passwordGenerator.generatePassword();
        /**
         * 비밀번호는 최소 8자 이상 12자 이하여야 한다.
         */
        if(password.length() >= 8 && password.length() <= 12){
            this.password = password ;
        }
    }

    public String getPassword() {
        return password;
    }
}
