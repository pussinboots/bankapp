// Comment to get more information during initialization
logLevel := Level.Warn

resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0-SNAPSHOT")

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" %% "sbt-plugin" % "2.2.3")

addSbtPlugin("com.sqality.scct" % "sbt-scct" % "0.3")

addSbtPlugin("com.sksamuel.scoverage" % "sbt-scoverage" % "0.95.9")

addSbtPlugin("com.sksamuel.scoverage" %% "sbt-coveralls" % "0.0.5")


