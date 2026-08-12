package io.github.gabriellisartori.services;

import org.springframework.stereotype.Service;

@Service
public class MathService {
    public Double sum(Double numberOne, Double numberTwo) {
        return numberOne + numberTwo;
    }

    public Double subtraction(Double numberOne, Double numberTwo) {
        return numberOne - numberTwo;
    }

    public Double multiplication(Double numberOne, Double numberTwo) {
        return numberOne * numberTwo;
    }

    public Double division(Double numberOne, Double numberTwo) {
        if (numberTwo.equals(0.0)) {
            throw new UnsupportedOperationException("Division by zero is not allowed.");
        }
        return numberOne / numberTwo;
    }

    public Double average(Double numberOne, Double numberTwo) {
        return (numberOne + numberTwo) / 2.0;
    }

    public Double squareRoot(Double number) {
        if (number < 0) {
            throw new UnsupportedOperationException("Square root of negative number is not supported for real numbers.");
        }
        return Math.sqrt(number);
    }
}
