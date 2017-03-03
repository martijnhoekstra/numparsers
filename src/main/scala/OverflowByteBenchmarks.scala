package parsenums

import org.openjdk.jmh.annotations._
import java.util.concurrent.TimeUnit

@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 15, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Benchmark)
class OverflowByteBenchmarks {
  import Parsers._

  @Param(Array("+128", "-129", "-123456789"))
  var overflow: String = _

  @Benchmark
  def wrappedbaselineOverflow: Option[Byte] = parseByteWrapped(overflow)

  @Benchmark
  def byte1Overflow: Option[Byte] = parseByteManually(overflow)

  @Benchmark
  def byte2Overflow: Option[Byte] = parseByteManually2(overflow)

}

