import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPiCalculator {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Ex811 <numSteps> <numThreads> <acceptableSize>");
            System.exit(1);
        }

        int numSteps = Integer.parseInt(args[0]);
        int numThreads = Integer.parseInt(args[1]);
        int acceptableSize = Integer.parseInt(args[2]);

        ForkJoinPool pool = new ForkJoinPool(numThreads);

        long startTime = System.currentTimeMillis();
        RecursivePiCalc task = new RecursivePiCalc(0, numSteps, acceptableSize);
        double pi = pool.invoke(task);
        long endTime = System.currentTimeMillis();

        System.out.printf("ForkJoin π result with %d steps\n", numSteps);
        System.out.printf("computed pi = %.20f\n", pi);
        System.out.printf("difference = %.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time = %.4f seconds\n", (endTime - startTime) / 1000.0);
    }
}

class RecursivePiCalc extends RecursiveTask<Double> {
    private int start, end, threshold;
    private static final double step = 1.0;  

    public RecursivePiCalc(int start, int end, int threshold) {
        this.start = start;
        this.end = end;
        this.threshold = threshold;
    }

    @Override
    protected Double compute() {
        int length = end - start;
        if (length <= threshold) {
            double sum = 0.0;
            double stepSize = 1.0 / (double) (end);
            for (int i = start; i < end; i++) {
                double x = (i + 0.5) * stepSize;
                sum += 4.0 / (1.0 + x * x);
            }
            return sum * stepSize;
        } else {
            // Split task
            int mid = start + length / 2;
            RecursivePiCalc left = new RecursivePiCalc(start, mid, threshold);
            RecursivePiCalc right = new RecursivePiCalc(mid, end, threshold);
            left.fork(); // run asynchronously
            double rightResult = right.compute(); // compute right in current thread
            double leftResult = left.join(); // wait for left
            return leftResult + rightResult;
        }
    }
}