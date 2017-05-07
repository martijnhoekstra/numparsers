resolvers += "Local Maven Repository" at "file:///"+Path.userHome.absolutePath+"/.m2/repository"

enablePlugins(JmhPlugin)

name := "parsenums"

scalaVersion := "2.12.1"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"

scalacOptions ++= List("encoding", "UTF8")