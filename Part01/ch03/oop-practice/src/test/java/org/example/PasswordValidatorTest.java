package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

/*
 *  비밀번호는 최소 8자 최대 12자 이하여야 한다.
 *  자리수가 조건에 맞지 않을경우 IllegalArgumentException 에러를 발생시킨다.
 * */
public class PasswordValidatorTest {
    @DisplayName("패스워드는 8자 이상 12자 이하여야 한다.")
    @Test
    void validatePasswordTest() {
        //given, when
        String password = "123456789";
        PasswordValidator passwordValidator = new PasswordValidator();

        //then
        assertThatCode(() -> passwordValidator.validate("123456789"));
    }

    @DisplayName("8자 미만, 12자 초과에서는 IllegalArgumentException을 발생시킨다")
    @ParameterizedTest
    @ValueSource(strings = {"1234567", "1234567890abc"})
    void validatePasswordTest2(String value) {
        //given
        PasswordValidator passwordValidator = new PasswordValidator();

        //when, then
        assertThatCode(() -> passwordValidator.validate(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호는 최소 8자 이상 12자 이하여야 합니다.");
    }


}
