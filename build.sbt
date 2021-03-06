lazy val unfilteredVersion = "0.8.2"

name := "scalaxb-heroku"

version := "1.4.0"

organization := "org.scalaxb"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.scalaxb" %% "scalaxb" % version.value,
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",
  "net.databinder" %% "unfiltered-filter" % unfilteredVersion,
  "net.databinder" %% "unfiltered-filter-uploads" % unfilteredVersion, 
  "net.databinder" %% "unfiltered-jetty" % unfilteredVersion,
  "javax.servlet" % "servlet-api" % "2.3" % "provided"
)

resolvers ++= Seq(
  "sonatype-public" at "https://oss.sonatype.org/content/repositories/public",
  "repo.codahale.com" at "http://repo.codahale.com")

seq(startScriptForClassesSettings: _*)
