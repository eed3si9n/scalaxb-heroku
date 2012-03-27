name := "scalaxb-heroku"

version := "0.7.0"

organization := "org.scalaxb"

scalaVersion := "2.9.1"

libraryDependencies <++= version { v => Seq(
  "org.scalaxb" %% "scalaxb" % v,
  "net.databinder" %% "dispatch-http" % "0.8.5",
  "net.databinder" %% "unfiltered-filter" % "0.5.0",
  "net.databinder" %% "unfiltered-uploads" % "0.5.0",  
  "net.databinder" %% "unfiltered-jetty" % "0.5.0",
  "javax.servlet" % "servlet-api" % "2.3" % "provided"
)}

resolvers ++= Seq(
  "sonatype-public" at "https://oss.sonatype.org/content/repositories/public",
  "repo.codahale.com" at "http://repo.codahale.com")

seq(startScriptForClassesSettings: _*)
