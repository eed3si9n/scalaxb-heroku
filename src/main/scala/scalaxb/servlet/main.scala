package scalaxb.servlet

import util.Properties
import unfiltered.request._
import unfiltered.response._

object Main extends App {
  val port = Properties.envOrElse("PORT", "8080").toInt
  unfiltered.jetty.Http(port).plan(CompilerFilter.filter).run
}
