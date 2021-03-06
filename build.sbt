import sbt._
import sbt.Keys._
import play.Project._
import java.io.File
import scala.sys.process._

play.Project.playScalaSettings

instrumentSettings

CoverallsPlugin.coverallsSettings

name := "StockManager"

version := "0.1"

scalaVersion := "2.10.3"

parallelExecution := false //disable parallel execution for all tasks the below configuration could be deleted but for documentation purpose they are still there

parallelExecution in Test := false

parallelExecution in ScoverageTest := false

Keys.fork in Test := false

val logger = ProcessLogger(
    (o: String) => println("out " + o),
    (e: String) => println("err " + e))

lazy val npm = taskKey[Unit]("npm install")

npm := scala.sys.process.Process( "npm" :: "install" :: Nil) ! logger

//(compile in Compile) <<= (compile in Compile) dependsOn (npm)

//ScoverageSbtPlugin.ScoverageKeys.highlighting in ScoverageSbtPlugin.scoverage := true

ScoverageKeys.excludedPackages in ScoverageCompile := "controllers.javascript;controllers.ref;tools.imports;Routes;controllers.ReverseAssets;controllers.ReverseApplication;controllers.ReverseStockController;controllers.ReverseGoogleController;controllers.ReverseBalanceController"

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
    "org.hsqldb" %  "hsqldb" % "[2,)",
    "co.freeside" % "betamax" % "1.1.2" % "test",
    "org.codehaus.groovy" % "groovy-all" % "1.8.8" % "test"
)

libraryDependencies ++= Seq(
    "com.typesafe.slick" %% "slick" % "1.0.1",
    "mysql" % "mysql-connector-java" % "5.1.18",
    "c3p0" % "c3p0" % "0.9.1.2"
)
