package org.example;

import org.example.calculate.PositiveNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

/*
*  요구 사항
*  간단한 사칙연산을 할 수 있다.
*  양수로만 계산할 수 있다. => 각 Operator에서 처리하는 것은 비효율적
*  나눈셈에서 0을 나누는 경우 IllegalArgument 예외를 발생시킨다. ->
*  MVC패턴 기반으로 구현한다.
* */
public class CalculatorTest {
    //as-is: 각각 따로
//    @DisplayName("덧셈 연산을 수행한다.")
//    @Test
//    void additionTest() {
//        int result = Calculator.calculate(1, "+", 2);
//        assertThat(result).isEqualTo(3);
//    }
//
//    @DisplayName("뺄셈 연산을 수행한다.")
//    @Test
//    void subtractionTest() {
//        int result = Calculator.calculate(1, "-", 2);
//        assertThat(result).isEqualTo(-1);
//    }
    //to-be
    @DisplayName("덧셈, 뺄셈, 곱셈 ,나눗셈을 수행한다.")
    @ParameterizedTest
    @MethodSource("formulaAndResult")
    void calculateTest(int operand1, String operator, int operand2, int result) {
        int calculatedResult = Calculator.calculate(new PositiveNumber(operand1), operator, new PositiveNumber(operand2));
        assertThat(calculatedResult).isEqualTo(result);
    }

    private static Stream<Arguments> formulaAndResult() {
        return Stream.of(
                Arguments.arguments(1, "+", 2, 3),
                Arguments.arguments(1, "-", 2, -1),
                Arguments.arguments(3, "*", 2, 6),
                Arguments.arguments(4, "/", 2, 2)

        );
    }
//    @DisplayName("나눗셈에서 0을 나누는 경우 IllegalArgument 예외를 발생시킨다.")
//    @Test
//    void calculateExceptionTest() {
//        assertThatCode(() -> Calculator.calculate(10, "/", 0))
//                .isInstanceOf(IllegalArgumentException.class)
//                .hasMessage("나눗셈에서 0을 나누는 경우 IllegalArgument 예외를 발생시킨다.");
//    }
}
