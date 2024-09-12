package dev.tilod;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;

@State(Scope.Benchmark)
public class InstantiationCost {
    private static final Random rand = new Random();

    private double[] doubleArray;

    @AllArgsConstructor
    static class SimpleCalculation {
        @Getter private double sum;
        private final double d;

        public void executeAdd() {
            this.sum += this.d;
        }
    }

    @AllArgsConstructor
    static class ComplexCalculation {
        @Getter private double sum;
        private final double d;

        public void executeAdd() {
            this.sum += Math.sqrt(Math.pow(d, 2.5d));
        }
    }

    @Setup
    public void setup() {
        this.doubleArray = new double[1000];

        for (int i = 0; i < 1000; ++i) {
            this.doubleArray[i] = rand.nextDouble();
        }
    }

    @Benchmark
    public double simpleCalculationWithoutInstantiation() {
        double sum = 0.0d;

        for (double d : this.doubleArray) {
            sum += d;
        }

        return sum;
    }

    @Benchmark
    public double complexCalculationWithoutInstantiation() {
        double sum = 0.0d;

        for (double d : this.doubleArray) {
            sum += Math.sqrt(Math.pow(d, 2.5d));
        }

        return sum;
    }

    @Benchmark
    public double simpleCalculationWithInstantiation() {
        double sum = 0.0d;

        for (double d : this.doubleArray) {
            SimpleCalculation simpleCalculation = new SimpleCalculation(sum, d);
            simpleCalculation.executeAdd();
            sum = simpleCalculation.getSum();
        }

        return sum;
    }

    @Benchmark
    public double complexCalculationWithInstantiation() {
        double sum = 0.0d;

        for (double d : this.doubleArray) {
            ComplexCalculation complexCalculation = new ComplexCalculation(sum, d);
            complexCalculation.executeAdd();
            sum = complexCalculation.getSum();
        }

        return sum;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(InstantiationCost.class.getSimpleName()).forks(1).build();

        new Runner(opt).run();
    }
}
