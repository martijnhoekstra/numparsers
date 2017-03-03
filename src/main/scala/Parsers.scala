package parsenums

object Parsers {
  import scala.util.Try

  def parseIntManually(str: String): Option[Int] = {
    val len = str.length()
    @scala.annotation.tailrec
    def step(i: Int, agg: Int): Option[Int] = {
      if (agg > 0) None
      else if(i == len) Some(agg)
      else {
        val digit = decValue(str(i))
        if (digit == -1) None
        else step(i + 1, agg * 10 - digit)
      }
    }

    if(len == 0) None
    else {
      val first = str(0)
      if (first == '-')
        if (len == 1) None
        else step(1, 0)
      else if (first == '+')
        if (len == 1) None
        else step(1, 0).flatMap(neg => if (neg == Int.MinValue) None else Some(-neg))
      else step(0, 0).flatMap(neg => if (neg == Int.MinValue) None else Some(-neg))
    }
  }

  def parseIntManually2(str: String): Option[Int] = {
    val len = str.length()
    @scala.annotation.tailrec
    def step(i: Int, agg: Int): Option[Int] = {
      if (agg > 0) None
      else if(i == len) Some(agg)
      else {
        val digit = decValue(str(i))
        if (digit == -1) None
        else step(i + 1, agg * 10 - digit)
      }
    }

    if(len == 0) None
    else {
      val first = str(0)
      val v = decValue(first)
      if (len == 1){
        if (v > -1) Some(v)
        else None
      } else {
        if (v > -1) step(1, -v).flatMap(neg => if (neg == Int.MinValue) None else Some(-neg))
        else if (first == '+') step(1, 0).flatMap(neg => if (neg == Int.MinValue) None else Some(-neg))
        else if (first == '-') step(1, 0)
        else None
      }
    }
  }

  def parseByteWrapped(str: String): Option[Byte] = Try(java.lang.Byte.parseByte(str)).toOption

  def parseByteManually(str: String): Option[Byte] = {
    val len = str.length()
    @scala.annotation.tailrec
    def step(i: Int, agg: Int): Option[Int] = {
      if (agg < Byte.MinValue) None
      else if(i == len) Some(agg)
      else {
        val digit = decValue(str(i))
        if (digit == -1) None
        else step(i + 1, agg * 10 - digit)
      }
    }

    if(len == 0) None
    else {
      val first = str(0)
      if (first == '-')
        if (len == 1) None
        else step(1, 0).map(_.toByte)
      else if (first == '+')
        if (len == 1) None
        else step(1, 0).flatMap(neg => if (neg == Byte.MinValue) None else Some((-neg).toByte))
      else step(0, 0).flatMap(neg => if (neg == Byte.MinValue) None else Some((-neg).toByte))
    }
  }

  def parseByteManually2(str: String): Option[Byte] = {
    val len = str.length()
    @scala.annotation.tailrec
    def step(i: Int, agg: Int): Option[Int] = {
      if (agg < Byte.MinValue) None
      else if(i == len) Some(agg)
      else {
        val digit = decValue(str(i))
        if (digit == -1) None
        else step(i + 1, agg * 10 - digit)
      }
    }

    if(len == 0) None
    else {
      val first = str(0)
      val v = decValue(first)
      if (len == 1){
        if (v > -1) Some(v.toByte)
        else None
      } else {
        if (v > -1) step(1, v).flatMap(neg => if (neg == Byte.MinValue) None else Some((-neg).toByte))
        else if (first == '+') step(1, 0).flatMap(neg => if (neg == Byte.MinValue) None else Some((-neg).toByte))
        else if (first == '-') step(1, 0).map(_.toByte)
        else None
      }
    }
  }


  def parseShortManually(str: String): Option[Short] = {
    val len = str.length()
    @scala.annotation.tailrec
    def step(i: Int, agg: Int): Option[Int] = {
      if (agg < Short.MinValue) None
      else if(i == len) Some(agg)
      else {
        val digit = decValue(str(i))
        if (digit == -1) None
        else step(i + 1, agg * 10 - digit)
      }
    }

    if(len == 0) None
    else {
      val first = str(0)
      if (first == '-')
        if (len == 1) None
        else step(1, 0).map(_.toShort)
      else if (first == '+')
        if (len == 1) None
        else step(1, 0).flatMap(neg => if (neg == Short.MinValue) None else Some((-neg).toShort))
      else step(0, 0).flatMap(neg => if (neg == Short.MinValue) None else Some((-neg).toShort))
    }
  }

    def parseShortManually2(str: String): Option[Short] = {
    val len = str.length()
    @scala.annotation.tailrec
    def step(i: Int, agg: Int): Option[Int] = {
      if (agg < Short.MinValue) None
      else if(i == len) Some(agg)
      else {
        val digit = decValue(str(i))
        if (digit == -1) None
        else step(i + 1, agg * 10 - digit)
      }
    }

    if(len == 0) None
    else {
      val first = str(0)
      val v = decValue(first)
      if (len == 1){
        if (v > -1) Some(v.toShort)
        else None
      } else {
        if (v > -1) step(1, v).flatMap(neg => if (neg == Short.MinValue) None else Some((-neg).toShort))
        else if (first == '+') step(1, 0).flatMap(neg => if (neg == Short.MinValue) None else Some((-neg).toShort))
        else if (first == '-') step(1, 0).map(_.toShort)
        else None
      }
    }
  }

  def parseLongManually(str: String): Option[Long] = {
    val len = str.length()
    @scala.annotation.tailrec
    def step(i: Int, agg: Long): Option[Long] = {
      if (agg > 0) None
      else if(i == len) Some(agg)
      else {
        val digit = decValue(str(i))
        if (digit == -1) None
        else step(i + 1, (agg * 10) - digit)
      }
    }

    if(len == 0) None
    else {
      val first = str(0)
      if (first == '-')
        if (len == 1) None
        else step(1, 0L)
      else if (first == '+')
        if (len == 1) None
        else step(1, 0L).flatMap(neg => if (neg == Long.MinValue) None else Some(-neg))
      else step(0, 0L).flatMap(neg => if (neg == Long.MinValue) None else Some(-neg))
    }
  }

  def parseByteFromInt(str: String): Option[Byte] =
    parseIntManually(str).collect { case i if i >= Byte.MinValue && i <= Byte.MaxValue => i.toByte}

  def parseShortFromInt(str: String): Option[Short] =
    parseIntManually(str).collect { case i if i >= Short.MinValue && i <= Short.MaxValue => i.toShort}

  def decValue(ch: Char): Int = java.lang.Character.digit(ch, 10)
}
