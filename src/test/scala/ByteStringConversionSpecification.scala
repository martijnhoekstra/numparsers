package scala

import org.scalacheck._
import org.scalacheck.Arbitrary._
import NumericStringGenerators._
import StringConversions._

object ByteStringConversionSpecification extends Properties("StringConversions") {
  import Prop.forAll
  import scala.util.Try

  implicit val noShrink: Shrink[String] = Shrink.shrinkAny

  property("nearUpperByte") = forAll(nearOverflowByteUpper){ 
    bytestring: String => parseByte(bytestring) == Try(java.lang.Byte.parseByte(bytestring)).toOption
  }

  property("nearUpperByteNonstandard") = forAll(nearOverflowByteUpper.flatMap(nonStandardNumbers)){ 
    bytestring: String => parseByte(bytestring) == Try(java.lang.Byte.parseByte(bytestring)).toOption
  }

  property("nearLowerByte") = forAll(nearOverflowByteLower){ 
    bytestring: String => parseByte(bytestring) == Try(java.lang.Byte.parseByte(bytestring)).toOption
  }

  property("nearLowerByteNonstandard") = forAll(nearOverflowByteLower.flatMap(nonStandardNumbers)){ 
    bytestring: String => parseByte(bytestring) == Try(java.lang.Byte.parseByte(bytestring)).toOption
  }

  property("overflowByteUpper") = forAll(overflowByteUpper){ 
    bytestring: String => parseByte(bytestring) == None
  }

  property("overflowByteLower") = forAll(overflowByteLower){ 
    bytestring: String => parseByte(bytestring) == None
  }

  property("validBytes") = forAll(validBytes){
    bytestring: String => {
      val parsed = parseByte(bytestring)
      parsed.isDefined &&
      parsed == Some(java.lang.Byte.parseByte(bytestring))
    }
  }

  property("validBytesNonstandard") = forAll(validBytes.flatMap(nonStandardNumbers)){
    bytestring: String => {
      val parsed = parseByte(bytestring)
      parsed.isDefined &&
      parsed == Some(java.lang.Byte.parseByte(bytestring))
    }
  }

  property("nearUpperByteFast") = forAll(nearOverflowByteUpper){ 
    bytestring: String => Try(fastByte(bytestring)).toOption == Try(java.lang.Byte.parseByte(bytestring)).toOption
  }

  property("nearLowerByteFast") = forAll(nearOverflowByteLower){ 
    bytestring: String => Try(fastByte(bytestring)).toOption == Try(java.lang.Byte.parseByte(bytestring)).toOption
  }

  property("overflowByteUpperFast") = forAll(overflowByteUpper){ 
    bytestring: String => !Try(fastByte(bytestring)).isSuccess
  }

  property("overflowByteLowerFast") = forAll(overflowByteLower){ 
    bytestring: String => !Try(fastByte(bytestring)).isSuccess
  }

  property("validBytesFast") = forAll(validBytes){
    bytestring: String => {
      val parsed = fastByte(bytestring)
      parsed == java.lang.Byte.parseByte(bytestring)
    }
  }

}