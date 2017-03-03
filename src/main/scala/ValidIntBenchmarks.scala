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
import Parsers._

  @Param(Array("1", "100", "1000", "10000", "-2147483648", "2147483647"))
  var valid: String = _

  @Benchmark
  def intbaseline = java.lang.Integer.parseInt(valid)

  @Benchmark
  def alg1: Option[Int] = parseIntManually(valid)

  @Benchmark
  def alg2: Option[Int] = parseIntManually2(valid)

}

