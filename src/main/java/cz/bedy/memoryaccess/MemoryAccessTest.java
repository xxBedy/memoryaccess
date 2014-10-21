package cz.bedy.memoryaccess;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import sun.misc.Unsafe;
import sun.nio.ch.DirectBuffer;
import xerial.larray.LIntArray;
import xerial.larray.japi.LArrayJ;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Random;

@State(Scope.Benchmark)
public class MemoryAccessTest {
    private static final int NO_OF_ITEMS = 5_000_000;
    private static final int INT_TYPE_SIZE = 4;

    private int[] randomOrder = new int[NO_OF_ITEMS];
    private int[] linearOrder = new int[NO_OF_ITEMS];

    private Unsafe unsafe = getTheUnsafe();

    private LIntArray larray = LArrayJ.newLIntArray(NO_OF_ITEMS);
    private int[] primitiveArray = new int[NO_OF_ITEMS];
    private IntBuffer intBuffer = ByteBuffer.allocate(NO_OF_ITEMS * INT_TYPE_SIZE).order(ByteOrder.nativeOrder()).asIntBuffer();
    private IntBuffer intDirectBuffer = ByteBuffer.allocateDirect(NO_OF_ITEMS * INT_TYPE_SIZE).order(ByteOrder.nativeOrder()).asIntBuffer();
    private ByteBuffer directByteBuffer = ByteBuffer.allocateDirect(NO_OF_ITEMS * INT_TYPE_SIZE).order(ByteOrder.nativeOrder());
    private long directByteBufferAddress = ((DirectBuffer) directByteBuffer).address();


    private Random r;


    @Setup
    public void setUp() {
        r = new Random();
        for (int i = 0; i < NO_OF_ITEMS; i++) {
            randomOrder[i] = i;
            linearOrder[i] = i;

            int value = r.nextInt();
            larray.update(i, value);
            primitiveArray[i] = value;
            intBuffer.put(value);
            intDirectBuffer.put(value);
            unsafe.putInt(directByteBufferAddress + i, value);
        }
        shuffleArray(randomOrder);

    }

    @Benchmark
    public int testLinearAccessToLArray() {
        int res = 0;
        for (int i : linearOrder) {
            res += larray.apply(i);
        }
        return res;
    }

    @Benchmark
    public int testRandomAccesToLArray() {
        int res = 0;
        for (int i : randomOrder) {
            res += larray.apply(i);
        }
        return res;
    }

    @Benchmark
    public int testLinearAccessToPrimitiveArray() {
        int res = 0;
        for (int i : linearOrder) {
            res += primitiveArray[i];
        }
        return res;
    }

    @Benchmark
    public int testRandomAccesToPrimitiveArray() {
        int res = 0;
        for (int i : randomOrder) {
            res += primitiveArray[i];
        }
        return res;
    }

    @Benchmark
    public int testLinearAccessToIntBuffer() {
        int res = 0;
        for (int i : linearOrder) {
            res += intBuffer.get(i);
        }
        return res;
    }

    @Benchmark
    public int testRandomAccesToIntBuffer() {
        int res = 0;
        for (int i : randomOrder) {
            res += intBuffer.get(i);
        }
        return res;
    }

    @Benchmark
    public int testLinearAccessToIntDirectBuffer() {
        int res = 0;
        for (int i : linearOrder) {
            res += intDirectBuffer.get(i);
        }
        return res;
    }

    @Benchmark
    public int testRandomAccesToIntDirectBuffer() {
        int res = 0;
        for (int i : randomOrder) {
            res += intDirectBuffer.get(i);
        }
        return res;
    }

    @Benchmark
    public int testLinearAccessToIntDirectBufferUnsafe() {
        int res = 0;
        for (int i : linearOrder) {
            res += unsafe.getInt(directByteBufferAddress + i);
        }
        return res;
    }

    @Benchmark
    public int testRandomAccesToIntDirectBufferUnsafe() {
        int res = 0;
        for (int i : randomOrder) {
            res += unsafe.getInt(directByteBufferAddress + i);
        }
        return res;
    }

    public static void main(String[] args) throws Throwable {
        Options opt = new OptionsBuilder()
                .include(MemoryAccessTest.class.getName() + ".*")
                .warmupIterations(10)
                .measurementIterations(20)
                .threads(1)
                .forks(2)
                .build();

        new Runner(opt).run();
    }

    static void shuffleArray(int[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    private static Unsafe getTheUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

}