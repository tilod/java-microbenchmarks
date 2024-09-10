package dev.tilod;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.Random;

@State(Scope.Benchmark)
public class ForVsStream {
    private static final Random rand = new Random();

    private long[] longArray;

    @Setup
    public void setup() {
        this.longArray = new long[1000];

        for (int i = 0; i < 1000; ++i) {
            this.longArray[i] = rand.nextLong();
        }
    }

    @Benchmark
    public long classicForLoop() {
        long sum = 0;

        for (int i = 0; i < this.longArray.length; ++i) {
            sum += this.longArray[i];
        }

        return sum;
    }

    @Benchmark
    public long enhancedForLoop() {
        long sum = 0;

        for (long l : this.longArray) {
            sum += l;
        }

        return sum;
    }

    @Benchmark
    public long streamReduce() {
        return Arrays.stream(this.longArray).reduce(0L, Long::sum);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(ForVsStream.class.getSimpleName()).forks(1).build();

        new Runner(opt).run();
    }
}
