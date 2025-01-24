package com.example.gitworkshop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {
    Calculator calculator = new Calculator();

    @Test
    void testAdd() {
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    void testSubtract() {
        assertEquals(1, calculator.subtract(3, 2));
    }

    @Test
    void testDivide() {
        assertThrows(IllegalArgumentException.class, () -> calculator.divide(1, 0));
        assertEquals(2, calculator.divide(6, 3));
    }
}