package edu.school21.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberWorkerTest {
    NumberWorker numberWorker = new NumberWorker();

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97})
    public void isPrimeForPrimes(int numbers) {
        Assertions.assertTrue(numberWorker.isPrime(numbers));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 9, 12, 14, 15, 16, 18, 20, 22, 24, 25, 26})
    public void isPrimeForNotPrimes(int numbers) {
        Assertions.assertFalse(numberWorker.isPrime(numbers));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, 1, -2, -23})
    public void isPrimeForIncorrectNumbers(int numbers) {
        Assertions.assertThrows(IllegalNumberException.class, () -> numberWorker.isPrime(numbers));
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    public void digitsSumForDigits(int number, int res) {
     Assertions.assertEquals(numberWorker.digitsSum(number),res);
    }
}

