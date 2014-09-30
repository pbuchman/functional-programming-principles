package recfun

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BalanceSuite extends FunSuite {
  import Main.balance

  test("balance: empty string") {
    assert(balance("".toList))
  }

  test("balance: one parenthesis") {
    assert(!balance("(".toList))
  }

  test("balance: one reverse parenthesis") {
    assert(!balance(")".toList))
  }

  test("balance: one parenthesis among chars") {
    assert(!balance("x(d".toList))
  }

  test("balance: one closing parenthesis among chars") {
    assert(!balance("xx)ff".toList))
  }

  test("balance: one char") {
    assert(balance("x".toList))
  }

  test("balance: very simple case") {
    assert(balance("abc(defghi)jkl".toList))
  }

  test("balance: a little bit harder") {
    assert(balance("abc(de(fxxxg)hi)jkl".toList))
  }

  test("balance: '(if (zero? x) max (/ 1 x))' is balanced") {
    assert(balance("(if (zero? x) max (/ 1 x))".toList))
  }

  test("balance: 'I told him ...' is balanced") {
    assert(balance("I told him (that it's not (yet) done).\n(But he wasn't listening)".toList))
  }

  test("balance: ':-)' is unbalanced") {
    assert(!balance(":-)".toList))
  }

  test("balance: counting is not enough") {
    assert(!balance("())(".toList))
  }

  test("balance: only closing parentheses") {
    assert(!balance(")x0x0x0))".toList))
  }
}
