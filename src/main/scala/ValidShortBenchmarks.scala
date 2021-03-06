package parsenums

import org.openjdk.jmh.annotations._
import java.util.concurrent.TimeUnit


@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 15, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Benchmark)
class ValidShortBenchmarks {
import StringConversions._

  @Param(Array("1", "100", "-32768", "32767"))
  var value: String = _

  @Benchmark
  def intbaseline = java.lang.Short.parseShort(value)

  @Benchmark
  def safeValid: Option[Short] = parseShort(value)

  @Benchmark
  def fastValid: Short = fastShort(value)

}

