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
import StringConversions._
    
  @Param(Array("1", "127", "-128"))
  var value: String = _

  @Benchmark
  def baselineValid: Byte = java.lang.Byte.parseByte(value)

  @Benchmark
  def fastValid: Byte = fastByte(value)

  @Benchmark
  def safeValid: Option[Byte] = parseByte(value)

}

