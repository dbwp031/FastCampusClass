package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @DisplayName("패스워드를 초기화한다")
    @Test
    void 패스워드초기화테스트() {
        // given
        User user = new User();

        //when
        user.initPassword(new CorrectPasswordGenerator());

        //then
        assertThat(user.getPassword()).isNotNull();
    }

    @DisplayName("패스워드가 조건에 맞지 않아 실패한다.")
    @Test
    void 패스워드초기화실패테스트() {
        // given
        User user = new User();

        //when
        user.initPassword(() -> "1234567");

        //then
        assertThat(user.getPassword()).isNull();
    }
}