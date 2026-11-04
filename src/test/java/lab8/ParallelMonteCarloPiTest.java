package lab8;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParallelMonteCarloPiTest {
    @Test
    void calculatesPiCloseToExpectedValue() {
        double pi = ParallelMonteCarloPi.calculatePi(4, 1_000_000);

        assertEquals(Math.PI, pi, 0.01);
    }

    @Test
    void supportsIterationsThatAreNotDivisibleByThreadCount() {
        double pi = ParallelMonteCarloPi.calculatePi(3, 10_001);

        assertEquals(Math.PI, pi, 0.1);
    }

    @Test
    void rejectsInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> ParallelMonteCarloPi.calculatePi(0, 100));
        assertThrows(IllegalArgumentException.class, () -> ParallelMonteCarloPi.calculatePi(2, 0));
    }
}
