package captify.test.java

import captify.test.scala.SparseIterators.iteratorFromOne
import captify.test.scala.TestAssignment.{sampleAfter, valueAt, mergeIterators, approximatesFor}
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import scala.util.Try

@RunWith(classOf[JUnitRunner])
class ScalaTestAssignmentTEST extends FunSuite {

  test("testSampleAfterIndexOne") {
    assert(sampleAfter(iteratorFromOne, 1, 5) sameElements Iterator(BigInt(2), BigInt(3), BigInt(4), BigInt(5), BigInt(6)))
  }

  test("testSampleAfterIndexZero") {
    assert(sampleAfter(iteratorFromOne, 0, 5) sameElements Iterator(BigInt(1), BigInt(2), BigInt(3), BigInt(4), BigInt(5)))
  }

  test("testSampleAfterIndexLast") {
    val input: Iterator[BigInt] = Iterator(BigInt(1), BigInt(2), BigInt(3), BigInt(4), BigInt(5), BigInt(6))
    assert(sampleAfter(input, 5, 6) sameElements Iterator(BigInt(6)))
  }

  test("testSampleAfterIndexOutOfBound") {
    val input: Iterator[BigInt] = Iterator(BigInt(1), BigInt(2), BigInt(3), BigInt(4), BigInt(5), BigInt(6))
    assert(sampleAfter(input, 6, 6) sameElements Iterator())
  }

  test("testValueAt") {
    assert(valueAt(iteratorFromOne, 1) == BigInt(2))
  }

  test("testValueAtFirst") {
    assert(valueAt(iteratorFromOne, 0) == BigInt(1))
  }

  test("testValueAtLast") {
    val input: Iterator[BigInt] = Iterator(BigInt(1), BigInt(2), BigInt(3), BigInt(4), BigInt(5), BigInt(6))
    assert(valueAt(input, 5) == BigInt(6))
  }


  test("valueAtIndexOutOfBound") {
    intercept[IndexOutOfBoundsException] {
      val input: Iterator[BigInt] = Iterator(BigInt(1), BigInt(2), BigInt(3), BigInt(4), BigInt(5), BigInt(6))
      valueAt(input, 6)
    }
  }

  test("testMergeIteratorsSorted") {
    assert(sampleAfter(mergeIterators(List(iteratorFromOne, iteratorFromOne, iteratorFromOne)), 5, 10) sameElements
      Iterator(6, 7, 8, 9, 10, 11, 12, 13, 14, 15))

  }


  test("testApproximatesFor") {
    val actual: Seq[(Int, Try[Double])] = approximatesFor(2, 4, 1000)
    assert(actual == Seq(2 -> Try(0.5), 3 -> Try(0.330, 4 -> Try(0.25))))
  }

  //
  //  @throws(classOf[ExecutionException])
  //  @throws(classOf[InterruptedException])
  //  def testApproximatesForExceptionOne {
  //    val actual: ConcurrentSkipListMap[Integer, Future[Double]] = approximatesFor(1, 4, 1000).asInstanceOf[ConcurrentSkipListMap[Integer, Future[Double]]]
  //    actual.get(1).get
  //  }
  //
  //  @throws(classOf[ExecutionException])
  //  @throws(classOf[InterruptedException])
  //  def testApproximatesForExceptionZero {
  //    val actual: ConcurrentSkipListMap[Integer, Future[Double]] = approximatesFor(0, 4, 1000).asInstanceOf[ConcurrentSkipListMap[Integer, Future[Double]]]
  //    actual.get(0).get
  //  }

}