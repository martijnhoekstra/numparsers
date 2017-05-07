resolvers += Resolver.bintrayRepo("ktosopl", "sbt-plugins")

resolvers += "Local Maven Repository" at "file:///"+Path.userHome.absolutePath+"/.m2/repository"

addSbtPlugin("pl.project13.scala" % "sbt-jmh" % "0.2.22-SNAPSHOT")