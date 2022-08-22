import mill._
import mill.scalalib._
import mill.scalalib.scalafmt.ScalafmtModule
import mill.bsp._

val spinalVersion = "latest.release"

object mylib extends ScalaModule with ScalafmtModule {
  def scalaVersion = "2.12.14"
  def ivyDeps = Agg(
    ivy"com.github.spinalhdl::spinalhdl-core:$spinalVersion",
    ivy"com.github.spinalhdl::spinalhdl-lib:$spinalVersion"
  )
  def scalacPluginIvyDeps = Agg(
    ivy"com.github.spinalhdl::spinalhdl-idsl-plugin:$spinalVersion"
  )
}
