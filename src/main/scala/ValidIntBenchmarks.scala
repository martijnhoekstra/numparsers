package parsenums

import org.openjdk.jmh.annotations._
import java.util.concurrent.TimeUnit


@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 15, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Benchmark)
class ValidIntBenchmarks {
import StringConversions._

  @Param(Array("1", "100000", "-2147483648", "2147483647"))
  var value: String = _

  @Benchmark
  def intbaseline = java.lang.Integer.parseInt(value)

  @Benchmark
  def safeValid: Option[Int] = parseInt(value)

  @Benchmark
  def fastValid: Int = fastInt(value)

}

