package parsenums

import org.scalacheck._
import org.scalacheck.Arbitrary._
import NumericStringGenerators._
import PreProcessors._

/*
val smallInteger = Gen.choose(0,100)

val propSmallInteger = Prop.forAll(smallInteger) { n =>
  n >= 0 && n <= 100
}
*/

object ParseSpecification extends Properties("String") {
  import Prop.forAll
  import scala.util.Try

  property("parses") = forAll(arbitrary[Int].flatMap(genIntString)) { intstring: String =>
    parseIntManually(intstring).isDefined
  }

  property("parsesSame") = forAll(arbitrary[Int].flatMap(genIntString)) { intstring: String =>
    Try(intstring.toInt).toOption == parseIntManually(intstring)
  }

  property("anystring") = forAll {str: String => 
    Try(str.toInt).toOption == parseIntManually(str)
  }

  property("anyshort") = forAll {str: String => 
    Try(str.toShort).toOption == parseShortManually(str)
  }

  property("parsesSameShort") = forAll(arbitrary[Short].flatMap(sh => genIntString(sh.toInt))) { intstring: String =>
    Try(intstring.toShort).toOption == parseShortManually(intstring)
  }

  property("parsesSameByte") = forAll(arbitrary[Short].flatMap(sh => genIntString(sh.toByte))) { intstring: String =>
    Try(intstring.toByte).toOption == parseByteManually(intstring)
  }

  property("parsesSameByteKnown") = forAll(genKnownByteStrings) { bytestring: String =>
    Try(bytestring.toByte).toOption == parseByteManually(bytestring)
  }

  property("parsesDigitsSame") = forAll(genDigitString){ bytestring: String =>
    Try(bytestring.toByte).toOption == parseByteManually(bytestring)
  }

    property("parsesDigitsIntSame") = forAll(genDigitString){ intstring: String =>
    Try(intstring.toInt).toOption == parseIntManually2(intstring)
  }


}