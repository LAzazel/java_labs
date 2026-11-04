package lab8;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.SplittableRandom;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class ParallelMonteCarloPi {
    private static final long DEFAULT_ITERATIONS = 1_000_000_000L;

    private ParallelMonteCarloPi() {
    }

    public static void main(String[] args) {
        int threadCount;
        try {
            if (args.length != 1) {
                throw new IllegalArgumentException("Потрібно вказати кількість потоків.");
            }
            threadCount = Integer.parseInt(args[0]);
            validateThreadCount(threadCount);
        } catch (NumberFormatException exception) {
            System.err.println("Кількість потоків має бути цілим числом.");
            return;
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
            return;
        }

        long start = System.nanoTime();
        double pi = calculatePi(threadCount, DEFAULT_ITERATIONS);
        double elapsedMilliseconds = (System.nanoTime() - start) / 1_000_000.0;

        System.out.printf(Locale.US, "PI %.5f%n", pi);
        System.out.println("THREADS " + threadCount);
        System.out.printf(Locale.US, "ITERATIONS %,d%n", DEFAULT_ITERATIONS);
        System.out.printf(Locale.US, "TIME %.2fms%n", elapsedMilliseconds);
    }

    public static double calculatePi(int threadCount, long iterations) {
        validateThreadCount(threadCount);
        if (iterations <= 0) {
            throw new IllegalArgumentException("Кількість ітерацій має бути додатною.");
        }

        long baseIterations = iterations / threadCount;
        long remainder = iterations % threadCount;
        long seed = System.nanoTime();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        try {
            List<Callable<Long>> tasks = new ArrayList<>(threadCount);
            for (int thread = 0; thread < threadCount; thread++) {
                long taskIterations = baseIterations + (thread < remainder ? 1 : 0);
                long taskSeed = seed + thread;
                tasks.add(() -> countPointsInsideCircle(taskIterations, taskSeed));
            }

            long pointsInsideCircle = 0;
            for (Future<Long> result : executor.invokeAll(tasks)) {
                pointsInsideCircle += result.get();
            }
            return 4.0 * pointsInsideCircle / iterations;
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Розрахунок було перервано.", exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException("Не вдалося виконати розрахунок.", exception.getCause());
        } finally {
            executor.shutdown();
        }
    }

    static long countPointsInsideCircle(long iterations, long seed) {
        SplittableRandom random = new SplittableRandom(seed);
        long pointsInsideCircle = 0;
        for (long iteration = 0; iteration < iterations; iteration++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            if (x * x + y * y <= 1.0) {
                pointsInsideCircle++;
            }
        }
        return pointsInsideCircle;
    }

    private static void validateThreadCount(int threadCount) {
        if (threadCount <= 0) {
            throw new IllegalArgumentException("Кількість потоків має бути більшою за нуль.");
        }
    }
}
