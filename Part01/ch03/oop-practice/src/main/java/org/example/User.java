package org.example;

public class User {
    private String password;
// as-is: randomPasswordGenerator와 initPassword가 강하게 결합되어 있다. -> 현재 randomPasswordGenerator를 컨트롤하지 못하는 상황
//    public void initPassword() {
//        RandomPasswordGenerator randomPasswordGenerator = new RandomPasswordGenerator();
//        String randomPassword = randomPasswordGenerator.generatePassword();
//        int length = randomPassword.length();
//        if (length >= 8 && length <= 12) {
//            this.password = randomPassword;
//        }
//    }

    //to-be: PasswordGenerator 인터페이스를 생성, initPassword의 인자로 받게함으로써 Generator를 컨트롤할 수 있음.
    // PasswordGenerator의 구현체로 CorrectPasswordGenerator, WrongPasswordGenerator를 생성해서 테스트에 사용할 수 있음.
    public void initPassword(PasswordGenerator passwordGenerator) {
        String randomPassword = passwordGenerator.generatePassword();
        int length = randomPassword.length();
        if (length >= 8 && length <= 12) {
            this.password = randomPassword;
        }
    }
    public String getPassword() {
        return this.password;
    }
}
