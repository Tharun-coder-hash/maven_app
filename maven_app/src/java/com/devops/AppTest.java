package com.devops;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

public class AppTest {

    @Test
    @DisplayName("Should correctly reverse a standard string")
    void testReverse() {
        assertEquals("olleh", App.reverse("hello"));
        assertEquals("12345", App.reverse("54321"));
        assertEquals("", App.reverse(""));
    }

    @Test
    @DisplayName("Should identify valid palindromes (case-insensitive)")
    void testIsPalindrome() {
        assertTrue(App.isPalindrome("madam"));
        assertTrue(App.isPalindrome("Racecar"));
        assertTrue(App.isPalindrome("a"));
    }

    @Test
    @DisplayName("Should identify non-palindromes")
    void testIsNotPalindrome() {
        assertFalse(App.isPalindrome("hello"));
        assertFalse(App.isPalindrome("devops"));
    }

    @Test
    @DisplayName("Should handle null inputs gracefully")
    void testNullHandling() {
        assertNull(App.reverse(null));
        assertFalse(App.isPalindrome(null));
    }
}
