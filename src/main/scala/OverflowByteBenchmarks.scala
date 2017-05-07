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
  import StringConversions._
  import scala.util.Try

  @Param(Array("+128", "-129"))
  var value: String = _

  @Benchmark
  def wrappedBaselineOverflow: Option[Byte] = Try(value.toByte).toOption

  @Benchmark
  def wrappedFastOverflow: Option[Byte] = Try(fastByte(value)).toOption

  @Benchmark
  def byteOverflow: Option[Byte] = parseByte(value)

  

}

