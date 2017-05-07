/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2002-2013, LAMP/EPFL             **
**  __\ \/ /__/ __ |/ /__/ __ |    http://scala-lang.org/               **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
\*                                                                      */

package scala //is this the right place for this? StringLike and StringOps live in collection.immutable, but these are "plain" strings (should they?)

final object StringConversions {

  private def decValue(ch: Char): Int = java.lang.Character.digit(ch, 10)
  private def fastDec(ch: Char): Int = {
    val value = ch - '0'
    if (value >= 0 && value <= 9) value else -1
  }

  private def badNumber(src: String) = throw new NumberFormatException(s"For input string $src")

  //Int.MinValue == -2147483648
  private final val intOverflowBoundary = -214748364
  private final val intOverflowDigit = 9
  //Long.MinValue == -9223372036854775808L
  private final val longOverflowBoundary = -922337203685477580L
  private final val longOverflowDigit = 9

  /**
    * Parses a numeric string to its Long representation
    * String may contain those characters that are defined as digits, and are in the BMP
  **/
  def parseInt(str: String): Option[Int] = { //or implement in terms of parseLong
    val len = str.length()
    
    @scala.annotation.tailrec
    def step(i: Int, agg: Int, isPositive: Boolean): Option[Int] = {
      if (i == len) {
        if(!isPositive) Some(agg)
        else if (agg == Int.MinValue) None
        else Some(-agg)
      }
      else if(agg < intOverflowBoundary) None
      else {
        val digit = decValue(str.charAt(i))
        if (digit == -1 || (agg == intOverflowBoundary && digit == intOverflowDigit)) None
        else step(i + 1, (agg * 10) - digit, isPositive)
      }
    }
    //empty strings parse to None
    if (len == 0) None
    else {
      val first = str.charAt(0)
      val v = decValue(first)
      if (len == 1) {
        //"+" and "-" parse to None
        if (v > -1) Some(v)
        else None
      }
      else if (v > -1) step(1, -v, true)
      else if (first == '+') step(1, 0, true)
      else if (first == '-') step(1, 0, false)
      else None
    }
  }

  def parseLong(str: String): Option[Long] = {
    //as parseInt, but Long
    val len = str.length()
    @scala.annotation.tailrec
    def step(i: Int, agg: Long, isPositive: Boolean): Option[Long] = {
      if (i == len) {
        if(isPositive && agg == Long.MinValue) None
        else if (isPositive) Some(-agg)
        else Some(agg)
      }
      else if(agg < longOverflowBoundary) None
      else {
        val digit = decValue(str.charAt(i))
        if (digit == -1 || (agg == longOverflowBoundary && digit == longOverflowDigit)) None
        else step(i + 1, agg * 10 - digit, isPositive)
      }
    }
    //empty strings parse to None
    if (len == 0) None
    else {
      val first = str.charAt(0)
      val v = decValue(first).toLong
      if (len == 1) {
        //"+" and "-" parse to None
        if (v > -1) Some(v)
        else None
      }
      else if (v > -1) step(1, -v, true)
      else if (first == '+') step(1, 0, true)
      else if (first == '-') step(1, 0, false)
      else None
    }
  }

  def parseByte(str: String): Option[Byte] = {
    val len = str.length()
    @scala.annotation.tailrec
    def step(i: Int, agg: Int, isPositive: Boolean): Option[Byte] = {
      //implemented with Int, so overflow checks are simpler
      if (agg < Byte.MinValue) None
      else if (i == len) {
        if(!isPositive) Some(agg.toByte)
        else if (agg == Byte.MinValue) None
        else Some((-agg).toByte)
      }
      else {
        val digit = decValue(str.charAt(i))
        if (digit == -1) None
        else step(i + 1, agg * 10 - digit, isPositive)
      }
    }
    //empty strings parse to None
    if (len == 0) None
    else {
      val first = str.charAt(0)
      val v = decValue(first)
      if (len == 1) {
        //"+" and "-" parse to None
        if (v > -1) Some(v.toByte)
        else None
      }
      else if (v > -1) step(1, -v, true)
      else if (first == '+') step(1, 0, true)
      else if (first == '-') step(1, 0, false)
      else None
    }
  }

  def parseShort(str: String): Option[Short] = {
    val len = str.length()
    @scala.annotation.tailrec
    def step(i: Int, agg: Int, isPositive: Boolean): Option[Short] = {
      //implemented with Int, so overflow checks are simpler
      if (agg < Short.MinValue) None
      else if (i == len) {
        if(!isPositive) Some(agg.toShort)
        else if (agg == Short.MinValue) None
        else Some((-agg).toShort)
      }
      else {
        val digit = decValue(str.charAt(i))
        if (digit == -1) None
        else step(i + 1, agg * 10 - digit, isPositive)
      }
    }
    //empty strings parse to None
    if (len == 0) None
    else {
      val first = str.charAt(0)
      val v = decValue(first)
      if (len == 1) {
        //"+" and "-" parse to None
        if (v > -1) Some(v.toShort)
        else None
      }
      else if (v > -1) step(1, -v, true)
      else if (first == '+') step(1, 0, true)
      else if (first == '-') step(1, 0, false)
      else None
    }
  }

    /**
    * Parses a numeric string to its Long representation
    * String may contain those characters that are defined as digits, and are in the BMP
  **/
  def fastInt(str: String): Int = { //or implement in terms of fastLong
    val len = str.length()
    
    @scala.annotation.tailrec
    def step(i: Int, agg: Int, isPositive: Boolean): Int = {
      if (i == len) {
        if(!isPositive) agg
        else if (agg == Int.MinValue) badNumber(str)
        else -agg
      }
      else if(agg < intOverflowBoundary) badNumber(str)
      else {
        val digit = fastDec(str.charAt(i))
        if (digit == -1 || (agg == intOverflowBoundary && digit == intOverflowDigit)) badNumber(str)
        else step(i + 1, agg * 10 - digit, isPositive)
      }
    }
    //empty strings fail to parse
    if (len == 0) badNumber(str)
    else {
      val first = str.charAt(0)
      val v = fastDec(first)
      if (len == 1) {
        //"+" and "-" fail to parse
        if (v > -1) v
        else badNumber(str)
      }
      else if (v > -1) step(1, -v, true)
      else if (first == '+') step(1, 0, true)
      else if (first == '-') step(1, 0, false)
      else badNumber(str)
    }
  }

  def fastLong(str: String): Long = {
    //as fastInt, but Long
    val len = str.length()
    @scala.annotation.tailrec
    def step(i: Int, agg: Long, isPositive: Boolean): Long = {
      if (i == len) {
        if(isPositive && agg == Long.MinValue) badNumber(str)
        else if (isPositive) -agg
        else agg
      }
      else if(agg < longOverflowBoundary) badNumber(str)
      else {
        val digit = fastDec(str.charAt(i))
        if (digit == -1 || (agg == longOverflowBoundary && digit == longOverflowDigit)) badNumber(str)
        else step(i + 1, agg * 10 - digit, isPositive)
      }
    }
    //empty strings parse to None
    if (len == 0) badNumber(str)
    else {
      val first = str.charAt(0)
      val v = fastDec(first).toLong
      if (len == 1) {
        //"+" and "-" parse to None
        if (v > -1) v
        else badNumber(str)
      }
      else if (v > -1) step(1, -v, true)
      else if (first == '+') step(1, 0, true)
      else if (first == '-') step(1, 0, false)
      else badNumber(str)
    }
  }

  def fastByte(str: String): Byte = {
    val len = str.length()
    @scala.annotation.tailrec
    def step(i: Int, agg: Int, isPositive: Boolean): Byte = {
      //implemented with Int, so overflow checks are simpler
      if (agg < Byte.MinValue) badNumber(str)
      else if (i == len) {
        if(!isPositive) agg.toByte
        else if (agg == Byte.MinValue) badNumber(str)
        else (-agg).toByte
      }
      else {
        val digit = fastDec(str.charAt(i))
        if (digit == -1) badNumber(str)
        else step(i + 1, agg * 10 - digit, isPositive)
      }
    }
    //empty strings parse to None
    if (len == 0) badNumber(str)
    else {
      val first = str.charAt(0)
      val v = fastDec(first)
      if (len == 1) {
        //"+" and "-" parse to None
        if (v > -1) v.toByte
        else badNumber(str)
      }
      else if (v > -1) step(1, -v, true)
      else if (first == '+') step(1, 0, true)
      else if (first == '-') step(1, 0, false)
      else badNumber(str)
    }
  }

  def fastShort(str: String): Short = {
    val len = str.length()
    @scala.annotation.tailrec
    def step(i: Int, agg: Int, isPositive: Boolean): Short = {
      //implemented with Int, so overflow checks are simpler
      if (agg < Short.MinValue) badNumber(str)
      else if (i == len) {
        if(!isPositive) agg.toShort
        else if (agg == Short.MinValue) badNumber(str)
        else (-agg).toShort
      }
      else {
        val digit = fastDec(str.charAt(i))
        if (digit == -1) badNumber(str)
        else step(i + 1, agg * 10 - digit, isPositive)
      }
    }
    //empty strings parse to None
    if (len == 0) badNumber(str)
    else {
      val first = str.charAt(0)
      val v = fastDec(first)
      if (len == 1) {
        //"+" and "-" parse to None
        if (v > -1) v.toShort
        else badNumber(str)
      }
      else if (v > -1) step(1, -v, true)
      else if (first == '+') step(1, 0, true)
      else if (first == '-') step(1, 0, false)
      else badNumber(str)
    }
  }
  
  def parseBoolean(str: String): Option[Boolean] = 
    if(str == null) None
    else str.toLowerCase match {
      case "true" => Some(true)
      case "false" => Some(false)
      case _ => None
    }

  def fastBoolean(s: String): Boolean =
    if (s != null) s.toLowerCase match {
      case "true" => true
      case "false" => false
      case _ => throw new IllegalArgumentException("For input string: \""+s+"\"")
    }
    else
      throw new IllegalArgumentException("For input string: \"null\"")

}