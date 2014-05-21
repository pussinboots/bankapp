import sbt._
import sbt.Keys._
import play.Project._
import java.io.File

play.Project.playScalaSettings

name := "StockManager"

version := "0.1"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
    "net.databinder.dispatch" %% "dispatch-core" % "0.10.0",
    "net.sourceforge.htmlcleaner" % "htmlcleaner" % "2.2"
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
