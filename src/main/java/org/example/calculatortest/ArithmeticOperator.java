package org.example.calculatortest;

import java.util.Arrays;

// 사칙연산을 나타내는 열거형
public enum ArithmeticOperator {
    // 덧셈 연산을 나타내는 상수
    ADDITION("+") {
        @Override
        public int arithmaticCalculate(int operand1, int operand2) {
            // 덧셈 연산을 수행하고 결과 반환
            return operand1 + operand2;
        }
    },
    // 뺄셈 연산을 나타내는 상수
    SUBTRACTION("-") {
        @Override
        public int arithmaticCalculate(int operand1, int operand2) {
            // 뺄셈 연산을 수행하고 결과 반환
            return operand1 - operand2;
        }
    },
    // 곱셈 연산을 나타내는 상수
    MULTIPLICATION("*") {
        @Override
        public int arithmaticCalculate(int operand1, int operand2) {
            // 곱셈 연산을 수행하고 결과 반환
            return operand1 * operand2;
        }
    },
    // 나눗셈 연산을 나타내는 상수
    DIVISION("/") {
        @Override
        public int arithmaticCalculate(int operand1, int operand2) {
            // 나눗셈 연산을 수행하고 결과 반환
            return operand1 / operand2;
        }
    };

    // 각 상수에 대응하는 연산자 문자열
    private final String operator;

    // 생성자
    ArithmeticOperator(String operator) {
        this.operator = operator;
    }

    // 정적 메서드: 입력된 연산자에 해당하는 ArithmeticOperator 반환
    public static int calculate(int operand1, String operator, int operand2) {
        // 입력된 연산자에 해당하는 ArithmeticOperator 찾아서 반환
        ArithmeticOperator arithmeticOperator = Arrays.stream(values())
                .filter(v -> v.operator.equals(operator))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 사칙연산이 아닙니다."));

        // 해당 연산자에 대한 계산을 수행
        return arithmeticOperator.arithmaticCalculate(operand1, operand2);
    }

    // 추상 메서드: 실제 연산을 수행하는 메서드
    public abstract int arithmaticCalculate(final int operand1, final int operand2);
}

