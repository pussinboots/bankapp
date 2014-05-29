import sbt._
import sbt.Keys._
import play.Project._
import java.io.File

play.Project.playScalaSettings

ScctPlugin.instrumentSettings

ScoverageSbtPlugin.instrumentSettings

CoverallsPlugin.coverallsSettings

name := "StockManager"

version := "0.1"

scalaVersion := "2.10.3"

Keys.fork in Test := false

parallelExecution in Test := false

parallelExecution in ScctTest := false

parallelExecution in ScoverageSbtPlugin.scoverageTest := false

ScoverageSbtPlugin.ScoverageKeys.highlighting := true

ScoverageSbtPlugin.ScoverageKeys.excludedPackages in ScoverageSbtPlugin.scoverage := "Reverse*;controllers..*Reverse*;controllers.javascript;controllers.ref;app.tools;Routes"

ScctPlugin.scctExcludePackages in ScctPlugin.ScctTest := "Reverse*,controllers.javascript,controllers.ref,app.tools,Routes"

envVars := Map("aes_key" -> "16rdKQfqN3L4TY7YktgxBw==", "sparkasse_username"->"username", "sparkasse_password"->"password") // setted for EasyCryptSpec

libraryDependencies ++= Seq(
    "net.databinder.dispatch" %% "dispatch-core" % "0.10.0",
    "net.sourceforge.htmlcleaner" % "htmlcleaner" % "2.2"
)

//embedded jetty dependencies
libraryDependencies ++= Seq(
  "org.eclipse.jetty.orbit" % "javax.servlet" % "2.5.0.v201103041518" % "test" artifacts Artifact("javax.servlet", "jar", "jar"),
  "org.eclipse.jetty.orbit" % "javax.security.auth.message" % "1.0.0.v201108011116" % "test" artifacts Artifact("javax.security.auth.message", "jar", "jar"),
  "org.eclipse.jetty.orbit" % "javax.mail.glassfish" % "1.4.1.v201005082020" % "test" artifacts Artifact("javax.mail.glassfish", "jar", "jar"),
  "org.eclipse.jetty.orbit" % "javax.activation" % "1.1.0.v201105071233" % "test" artifacts Artifact("javax.activation", "jar", "jar"),
  "org.eclipse.jetty.orbit" % "javax.annotation" % "1.1.0.v201108011116" % "test" artifacts Artifact("javax.annotation", "jar", "jar"),
  "org.eclipse.jetty.aggregate" % "jetty-all-server" % "7.6.3.v20120416" % "test"
)

// test dependencies
libraryDependencies ++= Seq(
    "org.hsqldb" %  "hsqldb" % "[2,)" % "test",
    "co.freeside" % "betamax" % "1.1.2" % "test",
    "org.codehaus.groovy" % "groovy-all" % "1.8.8" % "test"
)

libraryDependencies ++= Seq(
    "com.typesafe.slick" %% "slick" % "1.0.1",
    "mysql" % "mysql-connector-java" % "5.1.18",
    "c3p0" % "c3p0" % "0.9.1.2"
)
