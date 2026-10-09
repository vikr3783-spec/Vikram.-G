package com.example

import com.example.model.PrimeCalculator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun primeCalculator_verifiesPrimeNumbers() {
        assertTrue(PrimeCalculator.isPrime(2))
        assertTrue(PrimeCalculator.isPrime(3))
        assertTrue(PrimeCalculator.isPrime(5))
        assertTrue(PrimeCalculator.isPrime(7))
        assertTrue(PrimeCalculator.isPrime(11))
        assertTrue(PrimeCalculator.isPrime(13))
        assertTrue(PrimeCalculator.isPrime(17))
        assertTrue(PrimeCalculator.isPrime(19))
        assertTrue(PrimeCalculator.isPrime(23))
        assertTrue(PrimeCalculator.isPrime(29))
        assertTrue(PrimeCalculator.isPrime(31))
        assertTrue(PrimeCalculator.isPrime(101))
    }

    @Test
    fun primeCalculator_verifiesCompositeNumbers() {
        assertFalse(PrimeCalculator.isPrime(0))
        assertFalse(PrimeCalculator.isPrime(1))
        assertFalse(PrimeCalculator.isPrime(4))
        assertFalse(PrimeCalculator.isPrime(6))
        assertFalse(PrimeCalculator.isPrime(8))
        assertFalse(PrimeCalculator.isPrime(9))
        assertFalse(PrimeCalculator.isPrime(10))
        assertFalse(PrimeCalculator.isPrime(100))
    }

    @Test
    fun primeCalculator_nextPrimeCalculatesCorrectly() {
        assertEquals(2, PrimeCalculator.nextPrime(1))
        assertEquals(3, PrimeCalculator.nextPrime(2))
        assertEquals(5, PrimeCalculator.nextPrime(3))
        assertEquals(5, PrimeCalculator.nextPrime(4))
        assertEquals(7, PrimeCalculator.nextPrime(5))
        assertEquals(11, PrimeCalculator.nextPrime(7))
        assertEquals(101, PrimeCalculator.nextPrime(97))
        assertEquals(103, PrimeCalculator.nextPrime(101))
    }
}
