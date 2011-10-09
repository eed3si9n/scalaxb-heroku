package scalaxb.servlet

import unfiltered.request._
import unfiltered.response._

object Main extends App {
  val port = Properties.envOrElse("PORT", "8080").toInt
  unfiltered.jetty.Http(port).plan(new CompilerFilter).run
}