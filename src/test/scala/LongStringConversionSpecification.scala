package scala

import org.scalacheck._
import org.scalacheck.Arbitrary._
import NumericStringGenerators._
import StringConversions._

object LongStringConversionSpecification extends Properties("StringConversions") {
  import Prop.forAll
  import scala.util.Try

  implicit val noShrink: Shrink[String] = Shrink.shrinkAny

  property("nearUpperLong") = forAll(nearOverflowLongUpper){ 
    longstring: String => parseLong(longstring) == Try(java.lang.Long.parseLong(longstring)).toOption
  }

  property("nearUpperLongNonstandard") = forAll(nearOverflowLongUpper.flatMap(nonStandardNumbers)){ 
    longstring: String => parseLong(longstring) == Try(java.lang.Long.parseLong(longstring)).toOption
  }

  property("nearLowerLong") = forAll(nearOverflowLongLower){ 
    longstring: String => parseLong(longstring) == Try(java.lang.Long.parseLong(longstring)).toOption
  }

  property("nearLowerLongNonstandard") = forAll(nearOverflowLongLower.flatMap(nonStandardNumbers)){ 
    longstring: String => parseLong(longstring) == Try(java.lang.Long.parseLong(longstring)).toOption
  }

  property("validLongs") = forAll(validLongs){
    longstring: String => {
      val parsed = parseLong(longstring)
      parsed.isDefined &&
      parsed == Some(java.lang.Long.parseLong(longstring))
    }
  }

  property("validLongsNonstandard") = forAll(validLongs.flatMap(nonStandardNumbers)){
    longstring: String => {
      val parsed = parseLong(longstring)
      parsed.isDefined &&
      parsed == Some(java.lang.Long.parseLong(longstring))
    }
  }

  property("nearUpperLongFast") = forAll(nearOverflowLongUpper){ 
    longstring: String => Try(fastLong(longstring)).toOption == Try(java.lang.Long.parseLong(longstring)).toOption
  }

  property("nearLowerLongFast") = forAll(nearOverflowLongLower){ 
    longstring: String => Try(fastLong(longstring)).toOption == Try(java.lang.Long.parseLong(longstring)).toOption
  }

  property("validLongsFast") = forAll(validLongs){
    longstring: String => {
      val parsed = fastLong(longstring)
      parsed == java.lang.Long.parseLong(longstring)
    }
  }


  property("validLongsNonstandard") = forAll(validLongs.flatMap(nonStandardNumbers)){
    longstring: String => {
      val parsed = parseLong(longstring)
      parsed.isDefined &&
      parsed == Some(java.lang.Long.parseLong(longstring))
    }
  }

/*Fast*/

  property("nearUpperLongFast") = forAll(nearOverflowLongUpper){ 
    longstring: String => Try(fastLong(longstring)).toOption == Try(java.lang.Long.parseLong(longstring)).toOption
  }

  property("nearLowerLongFast") = forAll(nearOverflowLongLower){ 
    longstring: String => Try(fastLong(longstring)).toOption == Try(java.lang.Long.parseLong(longstring)).toOption
  }

  property("validLongsFast") = forAll(validLongs){
    longstring: String => {
      val parsed = fastLong(longstring)
      parsed == java.lang.Long.parseLong(longstring)
    }
  }
}