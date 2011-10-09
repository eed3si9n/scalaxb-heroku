package scalaxb.servlet

import unfiltered.request._
import unfiltered.response._

object Main extends App {
  unfiltered.jetty.Http(8080).plan(new CompilerFilter).run
}
