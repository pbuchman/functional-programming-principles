package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1: Pascal’s Triangle
   */
  def pascal(c: Int, r: Int): Int = {
    if (c > r || c < 0 || r < 0) throw new IllegalArgumentException

    if (c == 0 || c == r) 1 else {
      pascal(c - 1, r - 1) + pascal(c, r - 1);
    }
  }

  /**
   * Exercise 2: Parentheses Balancing
   */
  def balance(chars: List[Char]): Boolean = {
    def _balance(subChars: List[Char], toBalance: Int): Boolean = {
      if (subChars.isEmpty) {
        toBalance == 0
      } else if (subChars.head == '(') {
        _balance(subChars.tail, toBalance + 1)
      } else if (subChars.head == ')') {
        if (toBalance > 0)
          _balance(subChars.tail, toBalance - 1)
        else
          false
      } else {
        _balance(subChars.tail, toBalance)
      }
    }

    _balance(chars, 0)
  }

  /**
   * Exercise 3: Counting Change
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def _countChange(subMoney: Int, coins: List[Int], count: Int): Int = {
      if (subMoney < 0)
        count
      else if(coins.isEmpty)  {
        if (subMoney == 0) count + 1 else count
      } else _countChange(subMoney, coins.tail, count) + _countChange(subMoney - coins.head, coins, count)
    }
    if (money > 0) _countChange(money, coins, 0) else 0
  }
}
