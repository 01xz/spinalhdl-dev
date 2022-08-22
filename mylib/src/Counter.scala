package mylib

import spinal.core._
import spinal.lib._

class Counter extends Component {
  val io = new Bundle {
    val cond0 = in Bool ()
    val cond1 = in Bool ()
    val flag = out Bool ()
    val state = out UInt (8 bits)
  }
  val counter = Reg(UInt(8 bits)) init (0)

  when(io.cond0) {
    counter := counter + 1
  }

  io.state := counter
  io.flag := (counter === 0) | io.cond1
}

object ConterConfig
    extends SpinalConfig(
      defaultConfigForClockDomains = ClockDomainConfig(resetKind = SYNC, resetActiveLevel = LOW),
      targetDirectory = "rtl"
    )

object ConterMain {
  def main(args: Array[String]): Unit = {
    ConterConfig.generateVerilog(new Counter)
  }
}
