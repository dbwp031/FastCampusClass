package org.example;
@FunctionalInterface // 1개의 추상 메소드를 가지는 인터페이스
public interface PasswordGenerator {
    String generatePassword();
}
