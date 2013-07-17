name := "scalaxb-heroku"

version := "1.1.1"

organization := "org.scalaxb"

scalaVersion := "2.9.2"

libraryDependencies <++= version { v => Seq(
  "org.scalaxb" %% "scalaxb" % v,
  "net.databinder.dispatch" %% "dispatch-core" % "0.9.5",
  "net.databinder" %% "unfiltered-filter" % "0.6.4",
  "net.databinder" %% "unfiltered-filter-uploads" % "0.6.4", 
  "net.databinder" %% "unfiltered-jetty" % "0.6.4",
  "javax.servlet" % "servlet-api" % "2.3" % "provided"
)}

resolvers ++= Seq(
  "sonatype-public" at "https://oss.sonatype.org/content/repositories/public",
  "repo.codahale.com" at "http://repo.codahale.com")

seq(startScriptForClassesSettings: _*)
