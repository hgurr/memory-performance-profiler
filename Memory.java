import java.util.Random;
import java.util.TreeSet;
import java.util.LinkedList;

public class Memory {
    private static volatile int volatileCounter;

    public static void main(String[] args) {

        // TASK 1
        System.out.println("Task 1");

        int size = Integer.parseInt(args[0]);
        int experiments = Integer.parseInt(args[1]);

        double totalRegularTime = 0.0;
        double totalVolatileTime = 0.0;

        long totalRegularTimeSum = 0;
        long totalVolatileTimeSum = 0;

        // Regular loop
        for (int e = 0; e < experiments; e++) {
            long start = System.nanoTime();
            long runningTotal = 0;

            for (int r = 0; r < size; r++) {
                if (r % 2 == 0) {
                    runningTotal += r;
                } else {
                    runningTotal -= r;
                }
            }

            long end = System.nanoTime();

            totalRegularTime += (end - start);
            totalRegularTimeSum += runningTotal;
        }

        // Volatile loop
        for (int e = 0; e < experiments; e++) {
            long start = System.nanoTime();
            long runningTotal = 0;

            for (volatileCounter = 0; volatileCounter < size; volatileCounter++) {
                if (volatileCounter % 2 == 0) {
                    runningTotal += volatileCounter;
                } else {
                    runningTotal -= volatileCounter;
                }
            }

            long end = System.nanoTime();

            totalVolatileTime += (end - start);
            totalVolatileTimeSum += runningTotal;
        }

        double avgRegularTimeInSecs = (totalRegularTime / experiments) / 1000000000.0;
        double avgVolatileTimeInSecs = (totalVolatileTime / experiments) / 1000000000.0;
        double avgRegularTimeSum = totalRegularTimeSum / (double) experiments;
        double avgVolatileTimeSum = totalVolatileTimeSum / (double) experiments;

        System.out.printf("Regular: %.5f seconds%n", avgRegularTimeInSecs);
        System.out.printf("Volatile: %.5f seconds%n", avgVolatileTimeInSecs);
        System.out.printf("Avg regular sum: %.2f%n", avgRegularTimeSum);
        System.out.printf("Avg volatile sum: %.2f%n", avgVolatileTimeSum);

        // TASK 2
        System.out.println("Task 2");

        long seed = Long.parseLong(args[2]);
        Random rand = new Random(seed);
        int[] arr = new int[size]; // Integer[] arr = new Integer[size];

        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt();
        }

        int firstTenPercent = size / 10;
        int lastTenPercent = size - firstTenPercent;

        double totalKnownTime = 0.0;
        double totalRandomTime = 0.0;
        double totalSum = 0.0;

        for (int e = 0; e < experiments; e++) {
            long sum = 0;
            // First 10%
            long startKnown = System.nanoTime();

            for (int f = 0; f < firstTenPercent; f++) {
                sum += arr[f];
            }

            long endKnown = System.nanoTime();
            
            totalKnownTime += (endKnown - startKnown) / (double) firstTenPercent;

            // Last 10%
            int randomIndex = lastTenPercent + rand.nextInt(firstTenPercent);
            
            long startRandom = System.nanoTime();

            sum += arr[randomIndex];

            long endRandom = System.nanoTime();

            totalRandomTime += (endRandom - startRandom);
            totalSum += sum;
        }

        System.out.printf("Avg time to access known element: %.2f nanoseconds%n", totalKnownTime / experiments);
        System.out.printf("Avg time to access random element: %.2f nanoseconds%n", totalRandomTime / experiments);
        System.out.printf("Sum: %.2f%n", totalSum / experiments);
    
        // TASK 3
        System.out.println("Task 3");

        TreeSet<Integer> treeSet = new TreeSet<>();
        LinkedList<Integer> linkedList = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            treeSet.add(i);
            linkedList.add(i);
        }

        long totalTreeSetTime = 0;
        long totalLinkedListTime = 0;

        for (int e = 0; e < experiments; e++) {
            int randomValue = rand.nextInt(size);

            long start = System.nanoTime();

            treeSet.contains(randomValue);

            totalTreeSetTime += System.nanoTime() - start;

            start = System.nanoTime();

            linkedList.contains(randomValue);

            totalLinkedListTime += System.nanoTime() - start;
        }

        System.out.printf("Avg time to find in set: %.2f nanoseconds%n", totalTreeSetTime / (double) experiments);
        System.out.printf("Avg time to find in list: %.2f nanoseconds%n", totalLinkedListTime / (double) experiments);
    }
}

