package funsets

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 * - run the "test" command in the SBT console
 * - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  import funsets.FunSets._

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   * val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("singletonSet does not contain element") {
    new TestSets {
      assert(!contains(s1, 2))
    }
  }

  test("singletonSet contains element") {
    new TestSets {
      assert(contains(s3, 3))
    }
  }

  test("union contains both elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1))
      assert(contains(s, 2))
    }
  }

  test("intersect does not contain any element") {
    new TestSets {
      val s = intersect(s1, s2)
      assert(!contains(s, 1))
      assert(!contains(s, 2))
    }
  }

  test("intersect contains element") {
    new TestSets {
      val s = intersect(s1, union(s1, s2))
      assert(contains(s, 1))
    }
  }

  test("intersect does not contain element") {
    new TestSets {
      val s = intersect(s1, union(s1, s2))
      assert(!contains(s, 2))
    }
  }

  test("diff contains only s1 elements") {
    new TestSets {
      val s = diff(s1, s2)
      assert(contains(s, 1))
      assert(!contains(s, 2))
      assert(!contains(s, 3))
    }
  }

  test("filter from set with element 1 the elements that are not greater than one") {
    new TestSets {
      val s = filter(s1, x => x > 1)
      assert(!contains(s, 1))
      assert(!contains(s, 2))
      assert(!contains(s, 3))
    }
  }

  test("filter from set with element 3 the elements that are not greater than one") {
    new TestSets {
      val s = filter(s3, x => x > 1)
      assert(!contains(s, 1))
      assert(!contains(s, 2))
      assert(contains(s, 3))
    }
  }

  test("forall positive numbers") {
    new TestSets {
      val s = union(s1, union(s2, s3))
      assert(forall(s, x => x > 0), "forall positive")
    }
  }

  test("forall not all numbers bigger than 1") {
    new TestSets {
      val s = union(s2, union(s2, s1))
      assert(!forall(s, x => x > 1), "forall greater than 1")
    }
  }

  test("forall for empty set") {
    new TestSets {
      assert(forall(intersect(s1, s2), x => x > 1), "forall empty set")
    }
  }

  test("exists for empty set") {
    new TestSets {
      assert(!exists(intersect(s1, s2), x => x > 1), "exists empty set")
    }
  }

  test("exists at least a number greater than 1") {
    new TestSets {
      assert(exists(union(s1, s2), x => x > 1), "exists greater 1")
    }
  }

  test("map multiplying by 3") {
    new TestSets {
      assert(contains(map(s3, x => x * 3), 9), "map multiplying by 3 set s3")
    }
  }

  test("map subtracting 2 to set with several elements") {
    new TestSets {
      assert(contains(map(union(s1, union(s2, s3)), x => x - 2), -1), "map subtracting 2 to s1")
      assert(contains(map(union(s1, union(s2, s3)), x => x - 2), 0), "map subtracting 2 to s2")
      assert(contains(map(union(s1, union(s2, s3)), x => x - 2), 1), "map subtracting 2 to s3")
    }
  }
}
