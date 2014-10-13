package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
  trait TestTrees {
    val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
    val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
  }

  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }

  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a','b','d'))
    }
  }

  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }

  test("times should count correctly") {
    val list = List('a', 'b', 'a', 'a', 'b', 'c', 'd')
    val result = times(list)
    assert(result.size === 4)
    assert(result.filter(p => p._1 == 'a').head._2 === 3)
    assert(result.filter(p => p._1 == 'b').head._2 === 2)
    assert(result.filter(p => p._1 == 'c').head._2 === 1)
    assert(result.filter(p => p._1 == 'd').head._2 === 1)
  }

  test("singleton: list is a singleton") {
    assert(singleton(List(new Leaf('a', 3))) === true)
  }

  test("singleton: list is not a singleton") {
    assert(singleton(List(new Leaf('a', 3), new Leaf('b', 6))) === false)
  }

  test("until: should combine many elements into one") {
    new TestTrees {
      val result = until(singleton, combine)(List(t1, t2))
      assert(result.size === 1)
    }
  }
}
