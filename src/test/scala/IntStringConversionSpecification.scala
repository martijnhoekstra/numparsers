package scala

import org.scalacheck._
import org.scalacheck.Arbitrary._
import NumericStringGenerators._
import StringConversions._

object IntStringConversionSpecification extends Properties("StringConversions") {
  import Prop.forAll
  import scala.util.Try

  implicit val noShrink: Shrink[String] = Shrink.shrinkAny

  property("nearUpperInt") = forAll(nearOverflowIntUpper){ 
    intstring: String => parseInt(intstring) == Try(java.lang.Integer.parseInt(intstring)).toOption
  }

  property("nearUpperIntNonstandard") = forAll(nearOverflowIntUpper.flatMap(nonStandardNumbers)){ 
    intstring: String => parseInt(intstring) == Try(java.lang.Integer.parseInt(intstring)).toOption
  }

  property("nearLowerInt") = forAll(nearOverflowIntLower){ 
    intstring: String => parseInt(intstring) == Try(java.lang.Integer.parseInt(intstring)).toOption
  }

  property("nearLowerIntNonstandard") = forAll(nearOverflowIntLower.flatMap(nonStandardNumbers)){ 
    intstring: String => parseInt(intstring) == Try(java.lang.Integer.parseInt(intstring)).toOption
  }

  property("overflowIntUpper") = forAll(overflowIntUpper){ 
    intstring: String => parseInt(intstring) == None
  }

  property("overflowIntLower") = forAll(overflowIntLower){ 
    intstring: String => parseInt(intstring) == None
  }

  property("validInts") = forAll(validInts){
    intstring: String => {
      val parsed = parseInt(intstring)
      parsed.isDefined &&
      parsed == Some(java.lang.Integer.parseInt(intstring))
    }
  }

  property("validIntsNonstandard") = forAll(validInts.flatMap(nonStandardNumbers)){
    intstring: String => {
      val parsed = parseInt(intstring)
      parsed.isDefined &&
      parsed == Some(java.lang.Integer.parseInt(intstring))
    }
  }

  property("nearUpperIntFast") = forAll(nearOverflowIntUpper){ 
    intstring: String => Try(fastInt(intstring)).toOption == Try(java.lang.Integer.parseInt(intstring)).toOption
  }

  property("nearLowerIntFast") = forAll(nearOverflowIntLower){ 
    intstring: String => Try(fastInt(intstring)).toOption == Try(java.lang.Integer.parseInt(intstring)).toOption
  }

  property("overflowIntUpperFast") = forAll(overflowIntUpper){ 
    intstring: String => !Try(fastInt(intstring)).isSuccess
  }

  property("overflowIntLowerFast") = forAll(overflowIntLower){ 
    intstring: String => !Try(fastInt(intstring)).isSuccess
  }

  property("validIntsFast") = forAll(validInts){
    intstring: String => {
      val parsed = fastInt(intstring)
      parsed == java.lang.Integer.parseInt(intstring)
    }
  }

}