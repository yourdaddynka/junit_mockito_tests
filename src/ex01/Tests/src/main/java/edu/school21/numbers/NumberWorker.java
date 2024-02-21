package edu.school21.numbers;

public class NumberWorker {

    public boolean isPrime(int number) {
        if (number <= 1) throw new IllegalNumberException("Illegal number ");
        for (int i = 2, count = 0; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    public int digitsSum(int number) {
        if (number <= 0) throw new IllegalNumberException("Illegal number ");
        int sum = 0;
        while(number != 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }

}
