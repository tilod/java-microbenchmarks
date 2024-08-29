package dev.tilod;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.math.BigDecimal;
import java.util.Random;

@State(Scope.Benchmark)
public class DoubleVsBigDecimal {
    private static final Random rand = new Random();

    private float[] floatArray;
    private double[] doubleArray;
    private Float[] floatArray2;
    private Double[] doubleArray2;
    private BigDecimal[] bigDecimalArray;

    @Setup
    public void setup() {
        this.floatArray = new float[1000];
        this.doubleArray = new double[1000];
        this.floatArray2 = new Float[1000];
        this.doubleArray2 = new Double[1000];
        this.bigDecimalArray = new BigDecimal[1000];

        for (int i = 0; i < 1000; ++i) {
            this.floatArray[i] = rand.nextFloat();
            this.doubleArray[i] = rand.nextDouble();
            this.floatArray2[i] = rand.nextFloat();
            this.doubleArray2[i] = rand.nextDouble();
            this.bigDecimalArray[i] = BigDecimal.valueOf(rand.nextDouble());
        }
    }

    @Benchmark
    public float floatPrimitive() {
        float startValue = rand.nextFloat(900.0f, 1100.0f);
        float endValue = 0.0f;

        for (int i = 0; i < floatArray.length; ++i) {
            floatArray[i] *= startValue;
            endValue += floatArray[i];
        }

        return endValue;
    }

    @Benchmark
    public Float floatObject() {
        Float startValue = rand.nextFloat(900.0f, 1100.0f);
        Float endValue = 0.0f;

        for (int i = 0; i < floatArray2.length; ++i) {
            floatArray2[i] *= startValue;
            endValue += floatArray2[i];
        }

        return endValue;
    }

    @Benchmark
    public double doublePrimitive() {
        double startValue = rand.nextDouble(900.0d, 1100.0d);
        double endValue = 0.0d;

        for (int i = 0; i < doubleArray.length; ++i) {
            doubleArray[i] *= startValue;
            endValue += doubleArray[i];
        }

        return endValue;
    }

    @Benchmark
    public Double doubleObject() {
        Double startValue = rand.nextDouble(900.0d, 1100.0d);
        Double endValue = 0.0d;

        for (int i = 0; i < doubleArray2.length; ++i) {
            doubleArray2[i] *= startValue;
            endValue += doubleArray2[i];
        }

        return endValue;
    }

    @Benchmark
    public BigDecimal bigDecimal() {
        BigDecimal startValue = BigDecimal.valueOf(rand.nextDouble(900.0d, 1100.0d));
        BigDecimal endValue = BigDecimal.valueOf(0.0d);

        for (int i = 0; i < bigDecimalArray.length; ++i) {
            bigDecimalArray[i] = bigDecimalArray[i].multiply(startValue);
            endValue = endValue.add(bigDecimalArray[i]);
        }

        return endValue;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(DoubleVsBigDecimal.class.getSimpleName()).forks(1).build();

        new Runner(opt).run();
    }
}
