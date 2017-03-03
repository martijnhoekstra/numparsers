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
import Parsers._

  
  @Param(Array("1", "100", "1000", "１000", "10000", "-32768", "32767"))
  var valid: String = _

  @Benchmark
  def short1valid: Option[Short] = parseShortManually(valid)

  @Benchmark
  def short2valid: Option[Short] = parseShortManually2(valid)

}

