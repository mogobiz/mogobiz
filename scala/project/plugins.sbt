resolvers in ThisBuild += "xerial repo" at "http://repo1.maven.org/maven2/org/xerial/sbt/"


addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.2")

addSbtPlugin("org.xerial.sbt" % "sbt-pack" % "0.7.7")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.5.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.8.5")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.5.0")

addSbtPlugin("com.geirsson" % "sbt-scalafmt" % "0.2.8")
