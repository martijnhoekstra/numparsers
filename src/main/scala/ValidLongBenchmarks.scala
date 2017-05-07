package parsenums

import org.openjdk.jmh.annotations._
import java.util.concurrent.TimeUnit


@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 15, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Benchmark)
class ValidLongBenchmarks {
import StringConversions._

  @Param(Array("1", "10000000", "1000000000000", "1000000000000", "1000000000000000", "-9223372036854775808", "9223372036854775807"))
  var value: String = _

  @Benchmark
  def intbaseline = java.lang.Long.parseLong(value)

  @Benchmark
  def safeValid: Option[Long] = parseLong(value)

  @Benchmark
  def fastValid: Long = fastLong(value)

}

