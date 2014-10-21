#Test on MacBook Pro (15-inch, Early 2011)

* CPU: 2 GHz Intel Core i7
* RAM: 16 GB 1600 MHz DDR3
* JDK: java version "1.8.0_25"
     Java(TM) SE Runtime Environment (build 1.8.0_25-b17)
     Java HotSpot(TM) 64-Bit Server VM (build 25.25-b02, mixed mode)



Result:18,230 ±(99.9%) 0,203 ops/s [Average]
Statistics: (min, avg, max) = (16,681, 18,230, 18,613), stdev = 0,361
Confidence interval (99.9%): [18,027, 18,433]


Run complete. Total time: 00:12:27

    Benchmark                                                          Mode  Samples    Score  Score error  Units
    c.b.m.MemoryAccessTest.testLinearAccessToIntBuffer                thrpt       40   60,856        0,679  ops/s
    c.b.m.MemoryAccessTest.testLinearAccessToIntDirectBuffer          thrpt       40  177,928        0,576  ops/s
    c.b.m.MemoryAccessTest.testLinearAccessToIntDirectBufferUnsafe    thrpt       40  290,922        1,101  ops/s
    c.b.m.MemoryAccessTest.testLinearAccessToLArray                   thrpt       40  189,896        0,688  ops/s
    c.b.m.MemoryAccessTest.testLinearAccessToPrimitiveArray           thrpt       40  233,885        3,106  ops/s
    c.b.m.MemoryAccessTest.testRandomAccesToIntBuffer                 thrpt       40    9,930        0,061  ops/s
    c.b.m.MemoryAccessTest.testRandomAccesToIntDirectBuffer           thrpt       40   18,347        0,229  ops/s
    c.b.m.MemoryAccessTest.testRandomAccesToIntDirectBufferUnsafe     thrpt       40   30,649        1,218  ops/s
    c.b.m.MemoryAccessTest.testRandomAccesToLArray                    thrpt       40   17,180        0,508  ops/s
    c.b.m.MemoryAccessTest.testRandomAccesToPrimitiveArray            thrpt       40   18,230        0,203  ops/s



