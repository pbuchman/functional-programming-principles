object Lists {
  var list = List[(Char, Int)](('a', 1), ('b', 2), ('c', 3))

  list = list ::: List(('a', 9))
  list = ('a', 66) :: list

  val b = list.filter(p => p._1 == 'a')
  b.size
  b.head

  def times(chars: List[Char]): List[(Char, Int)] = {
    def _times(subChars: List[Char], acc: List[(Char, Int)]): List[(Char, Int)] = {
      if (subChars.isEmpty) acc else {
        val char = subChars.head
        val charCount = acc.filter(p => p._1 == char)
        if (charCount.isEmpty) {
          _times(subChars.tail, acc .:: (char, 1))
        } else {
          val newCount = charCount.head._2 + 1
          _times(subChars.tail, acc.filter(p => p._1 != char) .:: (char, newCount))
        }
      }
    }
    _times(chars, List())
  }

  times(List('a', 'a', 'a', 'a', 'b', 'c', 'd'))

}