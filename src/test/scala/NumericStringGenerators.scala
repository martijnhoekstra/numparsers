package scala

import org.scalacheck.Gen

object NumericStringGenerators {

  val nearOverflowByteUpper = {
    val lower: Int = Byte.MaxValue.toInt / 10
    val upper: Int = Byte.MaxValue.toInt * 10
    Gen.choose(lower, upper).map(_.toString)
  }

  val nearOverflowByteLower = {
    val upper: Int = Byte.MinValue.toInt / 10
    val lower: Int = Byte.MinValue.toInt * 10
    Gen.choose(lower, upper).map(_.toString)
  }

  val nearOverflowShortUpper = {
    val lower: Int = Short.MaxValue.toInt / 10
    val upper: Int = Short.MaxValue.toInt * 10
    Gen.choose(lower, upper).map(_.toString)
  }


  val nearOverflowShortLower = {
    val upper: Int = Short.MinValue.toInt / 10
    val lower: Int = Short.MinValue.toInt * 10
    Gen.choose(lower, upper).map(_.toString)
  }

  val nearOverflowIntUpper = {
    val lower: Long = Int.MaxValue.toLong / 10
    val upper: Long = Int.MaxValue.toLong * 10
    Gen.choose(lower, upper).map(_.toString)
  }

  val nearOverflowIntLower = {
    val upper: Long = Int.MinValue.toLong / 10
    val lower: Long = Int.MinValue.toLong * 10
    Gen.choose(lower, upper).map(_.toString)
  }

  val nearOverflowLongUpper = {
    val base = (Long.MaxValue / 10).toString
    for {
      end <- Gen.choose(0, 100)
    } yield base + end.toString
  }

  val nearOverflowLongLower = {
    val base = (Long.MinValue / 10).toString
    for {
      end <- Gen.choose(0, 100)
    } yield base + end.toString
  }

  val overflowByteUpper = Gen.choose(Byte.MaxValue.toInt + 1, Short.MaxValue.toInt).map(_.toString)
  val overflowByteLower = Gen.choose(Short.MinValue.toInt, Byte.MinValue.toInt - 1).map(_.toString)
  val overflowShortUpper = Gen.choose(Short.MaxValue.toInt + 1, Int.MaxValue).map(_.toString)
  val overflowShortLower = Gen.choose(Int.MinValue, Short.MinValue.toInt - 1).map(_.toString)
  val overflowIntUpper = Gen.choose(Int.MaxValue.toLong + 1, Long.MaxValue).map(_.toString)
  val overflowIntLower = Gen.choose(Long.MinValue, Int.MinValue.toLong - 1).map(_.toString)
  
  val validBytes = 
    for {
      byte <- Gen.choose(Byte.MinValue, Byte.MaxValue)
      zeroes <- Gen.listOf(Gen.const('0')).map(_.mkString)
      signum <- Gen.oneOf(List("+", "-", "").filterNot(s => (s == "+" && byte < 0) || (s == "-" && byte > 0)))
    } yield {
      val numbers = if (byte >= 0) byte.toString else byte.toString.substring(1)
      signum + zeroes + numbers
    }

  val validShorts = 
    for {
      short <- Gen.choose(Short.MinValue, Short.MaxValue)
      zeroes <- Gen.listOf(Gen.const('0')).map(_.mkString)
      signum <- Gen.oneOf(List("+", "-", "").filterNot(s => (s == "+" && short < 0) || (s == "-" && short > 0)))
    } yield {
      val numbers = if (short >= 0) short.toString else short.toString.substring(1)
      signum + zeroes + numbers
    }

  val validInts = 
    for {
      int <- Gen.choose(Int.MinValue, Int.MaxValue)
      zeroes <- Gen.listOf(Gen.const('0')).map(_.mkString)
      signum <- Gen.oneOf(List("+", "-", "").filterNot(s => (s == "+" && int < 0) || (s == "-" && int > 0)))
    } yield {
      val numbers = if (int >= 0) int.toString else int.toString.substring(1)
      signum + zeroes + numbers
    }

  val validLongs = 
    for {
      long <- Gen.choose(Long.MinValue, Long.MaxValue)
      zeroes <- Gen.listOf(Gen.const('0')).map(_.mkString)
      signum <- Gen.oneOf(List("+", "-", "").filterNot(s => (s == "+" && long < 0) || (s == "-" && long > 0)))
    } yield {
      val numbers = if (long >= 0) long.toString else long.toString.substring(1)
      signum + zeroes + numbers
    }
    

  val digitsByValue = {
    val allnumbers = (Char.MinValue.toChar to Char.MaxValue.toChar).filter(ch => java.lang.Character.isDigit(ch))
    allnumbers.groupBy(n => java.lang.Character.digit(n, 10))
  }

  def nonStandardNumbers(numeric: String) = {
    val listOfGens: List[Gen[Char]] = numeric.toList.map(ch => {
      val n = java.lang.Character.digit(ch, 10)
      if (n >= 0) Gen.oneOf(digitsByValue(n))
      else Gen.const(ch)
    })
    val sequenced = Gen.sequence(listOfGens)
    sequenced.map(l => scala.collection.JavaConverters.asScalaBuffer(l).mkString) //sequence gives me a java.util.ArrayList ¯\_(ツ)_/¯
  }

}