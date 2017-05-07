package scala

import org.scalacheck._
import org.scalacheck.Arbitrary._
import NumericStringGenerators._
import StringConversions._

object ShortStringConversionSpecification extends Properties("StringConversions") {
  import Prop.forAll
  import scala.util.Try

  implicit val noShrink: Shrink[String] = Shrink.shrinkAny

  property("nearUpperShort") = forAll(nearOverflowShortUpper){ 
    shortstring: String => parseShort(shortstring) == Try(java.lang.Short.parseShort(shortstring)).toOption
  }

  property("nearUpperShortNonstandard") = forAll(nearOverflowShortUpper.flatMap(nonStandardNumbers)){ 
    shortstring: String => parseShort(shortstring) == Try(java.lang.Short.parseShort(shortstring)).toOption
  }

  property("nearLowerShort") = forAll(nearOverflowShortLower){ 
    shortstring: String => parseShort(shortstring) == Try(java.lang.Short.parseShort(shortstring)).toOption
  }

  property("nearLowerShortNonstandard") = forAll(nearOverflowShortLower.flatMap(nonStandardNumbers)){ 
    shortstring: String => parseShort(shortstring) == Try(java.lang.Short.parseShort(shortstring)).toOption
  }

  property("overflowShortUpper") = forAll(overflowShortUpper){ 
    shortstring: String => parseShort(shortstring) == None
  }

  property("overflowShortLower") = forAll(overflowShortLower){ 
    shortstring: String => parseShort(shortstring) == None
  }

  property("validShorts") = forAll(validShorts){
    shortstring: String => {
      val parsed = parseShort(shortstring)
      parsed.isDefined &&
      parsed == Some(java.lang.Short.parseShort(shortstring))
    }
  }

  property("validShortsNonstandard") = forAll(validShorts.flatMap(nonStandardNumbers)){
    shortstring: String => {
      val parsed = parseShort(shortstring)
      parsed.isDefined &&
      parsed == Some(java.lang.Short.parseShort(shortstring))
    }
  }

  property("nearUpperShortFast") = forAll(nearOverflowShortUpper){ 
    shortstring: String => Try(fastShort(shortstring)).toOption == Try(java.lang.Short.parseShort(shortstring)).toOption
  }

  property("nearLowerShortFast") = forAll(nearOverflowShortLower){ 
    shortstring: String => Try(fastShort(shortstring)).toOption == Try(java.lang.Short.parseShort(shortstring)).toOption
  }

  property("overflowShortUpperFast") = forAll(overflowShortUpper){ 
    shortstring: String => !Try(fastShort(shortstring)).isSuccess
  }

  property("overflowShortLowerFast") = forAll(overflowShortLower){ 
    shortstring: String => !Try(fastShort(shortstring)).isSuccess
  }

  property("validShortsFast") = forAll(validShorts){
    shortstring: String => {
      val parsed = fastShort(shortstring)
      parsed == java.lang.Short.parseShort(shortstring)
    }
  }

}