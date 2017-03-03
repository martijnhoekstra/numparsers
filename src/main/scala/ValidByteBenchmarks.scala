package parsenums

import org.openjdk.jmh.annotations._
import java.util.concurrent.TimeUnit


@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 15, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Benchmark)
class ValidByteBenchmarks {
import Parsers._

  
  @Param(Array("1", "100", "127", "-128", "-0000000123"))
  var validbyte: String = _

/*
  @Benchmark
  def baselineValid: Byte = java.lang.Byte.parseByte(validbyte)

  @Benchmark
  def wrappedbaselineValid: Option[Byte] = parseByteWrapped(validbyte)


  @Benchmark
  def byte1Valid: Option[Byte] = parseByteManually(validbyte)
  */

  @Benchmark
  def byte2Valid: Option[Byte] = parseByteManually2(validbyte)

}

