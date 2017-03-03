package parsenums

import org.scalacheck.Gen



object Teststrings1 {

  //bool: true | false case insensitive

  //byte: -128 to (+)127
  //short: -32,768 to (+)32,767
  //int: -2,147,483,648 to (+)2,147,483,647
  //long: -9,223,372,036,854,775,808 to (+)9,223,372,036,854,775,807

  val invalid0 = ""
  val invalid1 = "-"
  val invalid2 = "+"

  val byte1 = "123"
  val byte2 = "-123"
  val byte3 = "+123"
  
  val byte4 = "000123"
  val byte5 = "-000123"
  val byte6 = "+000123"

  val byteoverflow1 = "128"
  val byteoverflow2 = "-129"
  val byteoverflow3 = "+128"

  val ninechars = "123456789"
  val b8 = "-123456789"
  val b9 = "+123456789"

}

object NumericStringGenerators {

  val digits = (Char.MinValue.toChar to Char.MaxValue.toChar).filter(ch => java.lang.Character.isDigit(ch)).toList

  val genDigit: Gen[Char] = Gen.oneOf[Char](digits)

  val genDigitString: Gen[String] = Gen.listOf(genDigit).map(_.mkString)

 def genIntString(n: Int) = {
  if (n >= 0) {
    val z = zeroed(Gen.const(n.toString))
    z.flatMap(s => Gen.oneOf(s, "+" + s))
  } else {
    val numbers = n.toString.substring(1)
    val z = zeroed(Gen.const(numbers))
    z.map(s => "-" + s)
  }
}

def genKnownByteStrings = {
  import Teststrings1._

  val i1 = Teststrings1.invalid0

  Gen.oneOf(
    invalid0, invalid1, invalid2,
    byte1, byte2, byte3,
    byte4, byte5, byte6,
    byteoverflow1, byteoverflow2, byteoverflow3)
}


 def zeroed(gstr: Gen[String]): Gen[String] = 
  for {
    zeroes <- Gen.listOf(Gen.const('0')).map(_.mkString)
    str <- gstr
  } yield zeroes + str




}