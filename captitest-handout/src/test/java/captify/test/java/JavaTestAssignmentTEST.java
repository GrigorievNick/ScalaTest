package captify.test.java;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;
import org.testng.annotations.Test;

import static captify.test.java.TestAssignment.approximatesFor;
import static captify.test.java.TestAssignment.mergeIterators;
import static captify.test.java.TestAssignment.sampleAfter;
import static captify.test.java.TestAssignment.valueAt;
import static java.math.BigInteger.valueOf;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyIterator;
import static org.testng.Assert.assertEquals;

public class JavaTestAssignmentTEST {

    @Test
    public void testSampleAfter() {
        Iterator<BigInteger> expectedResultOverIndexOne = prepareIterator(2L, 3L, 4L, 5L, 6L);
        Iterator<BigInteger> expectedResultOverIndexZero = prepareIterator(1L, 2L, 3L, 4L, 5L);
        Iterator<BigInteger> expectedResultOverIndexLast = prepareIterator(6L);

        assertEquals(sampleAfter(prepareIterator(1L, 2L, 3L, 4L, 5L, 6L), 1, 5), expectedResultOverIndexOne);
        assertEquals(sampleAfter(prepareIterator(1L, 2L, 3L, 4L, 5L, 6L), 0, 5), expectedResultOverIndexZero);
        assertEquals(sampleAfter(prepareIterator(1L, 2L, 3L, 4L, 5L, 6L), 5, 6), expectedResultOverIndexLast);
    }

    @Test
    public void testSampleAfterInfiniteIterator() {
        final Iterator<BigInteger> input = SparseIterators.iteratorFromOne();
        final Iterator<BigInteger> actual = sampleAfter(input, 1, 5);
        Iterator<BigInteger> expectedResultOverIndexOne = prepareIterator(2L, 3L, 4L, 5L, 6L);
        assertEquals(actual, expectedResultOverIndexOne);
    }

    @Test
    public void testSampleAfterOutOfBound() {
        assertEquals(sampleAfter(prepareIterator(1L, 2L, 3L, 4L, 5L, 6L), 7, 6), emptyIterator());
    }

    @Test
    public void testValueAt() {
        assertEquals(valueAt(prepareIterator(1L, 2L, 3L, 4L, 5L, 6L), 1), valueOf(2));
    }

    @Test
    public void testValueAtInfiniteIterator() {
        final Iterator<BigInteger> input = SparseIterators.iteratorFromOne();
        assertEquals(valueAt(input, 1), valueOf(2));
    }

    @Test
    public void testValueAtZero() {
        assertEquals(valueAt(prepareIterator(1L, 2L, 3L, 4L, 5L, 6L), 0), valueOf(1));
    }

    @Test
    public void testValueAtLast() {
        assertEquals(valueAt(prepareIterator(1L, 2L, 3L, 4L, 5L, 6L), 5), valueOf(6));
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testValueAtOutOfBound() {
        valueAt(prepareIterator(1L, 2L, 3L, 4L, 5L, 6L), 6);
    }

    @Test
    public void testMergeIteratorsSorted() {
        final List<Iterator<BigInteger>> input =
            asList(prepareIterator(1L, 2L, 3L), prepareIterator(4L, 5L, 6L), prepareIterator(7L, 8L, 9L));
        final Iterator<BigInteger> expected = prepareIterator(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L);
        assertEquals(mergeIterators(input), expected);
    }
    @Test
    public void testMergeIteratorsInfinite() {
        final Iterator<BigInteger> mergeIterators
            = mergeIterators(asList(SparseIterators.iteratorFromOne(), SparseIterators.iteratorSparse(2)));
        sampleAfter(mergeIterators, 10, 10).forEachRemaining(System.out::println);
    }

    @Test
    public void testMergeIteratorsDuplicate() {
        final List<Iterator<BigInteger>> input =
            asList(prepareIterator(4L, 5L, 6L), prepareIterator(1L, 2L, 2L), prepareIterator(1L, 2L, 3L));
        final Iterator<BigInteger> expected = prepareIterator(4L, 5L, 6L, 1L, 2L, 2L, 1L, 2L, 3L);
        assertEquals(mergeIterators(input), expected);
    }

    @Test
    public void testApproximatesFor() {
        final ConcurrentSkipListMap<Integer, Future<Double>> actual =
            (ConcurrentSkipListMap<Integer, Future<Double>>) approximatesFor(2, 4, 1000);
        assertEquals(actual.keySet().toArray(), IntStream.rangeClosed(2, 4).toArray());
        // Don't see any reason to check value of Futures, because approximateSparsity
        // provided as already imlemented
        assertEquals(approximatesFor(2, 15, 1000).keySet().toArray(), IntStream.rangeClosed(2, 15).toArray());
    }

    @Test(expectedExceptions = ExecutionException.class,
        expectedExceptionsMessageRegExp = "java.lang.IllegalArgumentException: sparsity of 1 is not supported")
    public void testApproximatesForExceptionOne() throws ExecutionException, InterruptedException {
        final ConcurrentSkipListMap<Integer, Future<Double>> actual =
            (ConcurrentSkipListMap<Integer, Future<Double>>) approximatesFor(1, 4, 1000);
        // Don't see any reason to check value of Futures, because approximateSparsity
        // provided as already imlemented
        actual.get(1).get();
    }

    @Test(expectedExceptions = ExecutionException.class,
        expectedExceptionsMessageRegExp = "java.lang.ArithmeticException: / by zero")
    public void testApproximatesForExceptionZero() throws ExecutionException, InterruptedException {
        final ConcurrentSkipListMap<Integer, Future<Double>> actual =
            (ConcurrentSkipListMap<Integer, Future<Double>>) approximatesFor(0, 4, 1000);
        // Don't see any reason to check value of Futures, because approximateSparsity
        // provided as already imlemented
        actual.get(0).get();
    }

    private Iterator<BigInteger> prepareIterator(Long... integers) {
        return Arrays.stream(integers).map(BigInteger::valueOf).iterator();
    }

}
