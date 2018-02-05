//import scalariform.formatter.preferences._

name := "mogobiz-app"

organization in ThisBuild := "com.mogobiz"

logLevel in Global := Level.Info

scalaVersion in ThisBuild := "2.11.7"

resolvers in ThisBuild ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "spray repo" at "http://repo.spray.io/",
  //"ebiz repo" at "http://art.ebiznext.com/artifactory/libs-release-local",
  "scribe-java-mvn-repo" at "https://raw.github.com/fernandezpablo85/scribe-java/mvn-repo",
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
  "BoneCP Repository" at "http://jolbox.com/bonecp/downloads/maven"
)

git.useGitDescribe := true

git.gitUncommittedChanges in ThisBuild := false


val akkaV = "2.3.9"

val sprayV = "1.3.4"

val scalikeV = "2.3.5"

val jacksonV = "2.8.8"

val json4sV = "3.5.3"

val elastic4sV = "5.6.0"

val configV = "1.2.1"

val elasticsearchAnalysisICUV = "2.7.0"

val elasticsearchMapperAttachmentsV = "2.7.1"

val itextV = "5.5.4"

val metricsVersion = "3.1.0"

val scalaTestV = "3.0.1"

libraryDependencies in ThisBuild ++= Seq(
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "mysql" % "mysql-connector-java" % "5.1.12",
  "com.h2database" % "h2" % "1.4.177" % "test",
  "org.apache.derby" % "derbyclient" % "10.11.1.1",
  "org.scalikejdbc" %% "scalikejdbc" % scalikeV,
  "org.scalikejdbc" %% "scalikejdbc-config" % scalikeV,
  "org.scalikejdbc" %% "scalikejdbc-interpolation" % scalikeV,
  "org.scalikejdbc" %% "scalikejdbc-test" % scalikeV % "test",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0",
  "com.itextpdf" % "itextpdf" % itextV,
  "com.itextpdf.tool" % "xmlworker" % itextV,
  "joda-time" % "joda-time" % "2.7",
  "org.bouncycastle" % "bcprov-jdk15on" % "1.58",
  "org.joda" % "joda-convert" % "1.7",
  "com.github.fernandospr" % "javapns-jdk16" % "2.3.1",
  "org.scribe" % "scribe" % "1.3.7",
  "com.typesafe" % "config" % configV,
  "com.h2database" % "h2" % "1.4.177" % "test",
  "com.jolbox" % "bonecp" % "0.8.0.RELEASE",
  "com.typesafe.akka" %% "akka-http" % "10.0.11",
  "com.typesafe.akka" %% "akka-stream" % "2.5.7", // or whatever the latest version is
  "com.typesafe.akka" %% "akka-actor"  % "2.5.7",  // or whatever the latest version is;
  "org.json4s" %% "json4s-native" % json4sV,
  "org.json4s" %% "json4s-ext" % json4sV,
  "org.json4s" %% "json4s-jackson" % json4sV,
  "org.json4s" %% "json4s-ext" % json4sV,
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonV,
  "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % jacksonV,
  "com.fasterxml.jackson.core" % "jackson-annotations" % jacksonV,
  "com.fasterxml.jackson.core" % "jackson-core" % jacksonV,
  "com.fasterxml.jackson.core" % "jackson-databind" % jacksonV,
  "org.scalatest" %% "scalatest" % scalaTestV % "test",
  "org.apache.commons" % "commons-email" % "1.5",
  "org.apache.commons" % "commons-lang3" % "3.7",
  "org.apache.shiro" % "shiro-all" % "1.4.0",
  "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sV,
  "com.sksamuel.elastic4s" %% "elastic4s-http" % elastic4sV,
  "com.sksamuel.elastic4s" %% "elastic4s-embedded" % elastic4sV,
  "com.google.zxing" % "core" % "1.7",
  "com.sun.xml.messaging.saaj" % "saaj-impl" % "1.4.0",
  "net.authorize" % "anet-java-sdk" % "1.8.3",
  "com.mortennobel" % "java-image-scaling" % "0.8.6",
  "org.apache.httpcomponents" % "httpcore" % "4.4.8",
  "org.apache.httpcomponents" % "httpclient" % "4.5.3",
  "org.apache.tika" % "tika-core" % "1.16",
  "com.easypost" % "easypost-api-client" % "3.3.5",
  "com.google.code.gson" % "gson" % "2.2.4",
  "com.google.maps" % "google-maps-services" % "0.1.7",
  "de.heikoseeberger" %% "akka-http-json4s" % "1.18.1"
)

lazy val msys = project.in(file("mogobiz-system"))

lazy val template = project.in(file("mogobiz-template"))

lazy val html2pdf = project.in(file("mogobiz-html2pdf"))

lazy val utils = project.in(file("mogobiz-utils")).dependsOn(msys)

lazy val json = project.in(file("mogobiz-json")).dependsOn(utils)

lazy val es = project.in(file("mogobiz-es")).dependsOn(utils, json)

lazy val session = project.in(file("mogobiz-session")).dependsOn(es, json, utils)

lazy val mnotify = project.in(file("mogobiz-notify")).dependsOn(html2pdf, es, json, session, utils)

lazy val mauth = project.in(file("mogobiz-auth")).dependsOn(html2pdf, es, json, session, utils)

lazy val mogopayCommon = project.in(file("mogopay-common")).dependsOn(json)

lazy val mogopayCore = project.in(file("mogopay-core")).dependsOn(msys, mauth, mnotify, html2pdf, es, json, session, utils, template, mogopayCommon)

lazy val mogobizRun = project.in(file("mogobiz-run")).dependsOn(mogopayCore)

lazy val mogobizLaunch = project.in(file("mogobiz-launch")).dependsOn(mogobizRun, mogopayCore)

lazy val mogopayLaunch = project.in(file("mogopay-launch")).dependsOn(mogopayCore)

lazy val mogobizSelenium = project.in(file("mogobiz-selenium")).dependsOn(mogobizLaunch)

lazy val root = project.in(file(".")).aggregate(
  utils,
  template,
  json,
  es,
  html2pdf,
  session,
  mnotify,
  mauth,
  mogopayCommon,
  mogopayCore,
  mogopayCommon,
  mogobizRun,
  msys,
  mogobizLaunch,
  mogopayLaunch,
  mogobizSelenium).enablePlugins(GitVersioning, GitBranchPrompt /*, BuildInfoPlugin*/).settings(
//    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
//    buildInfoPackage := "com.mogobiz"
  )


isSnapshot in ThisBuild := version.value.trim.endsWith("SNAPSHOT")

publishTo in ThisBuild := {
  val artifactory = "http://art.ebiznext.com/artifactory/"
  if (isSnapshot.value)
    Some("snapshots" at artifactory + "libs-snapshot-local")
  else
    Some("releases" at artifactory + "libs-release-local")
}

credentials in ThisBuild += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishArtifact in(ThisBuild, Compile, packageSrc) := false

publishArtifact in(ThisBuild, Test, packageSrc) := false

parallelExecution in(ThisBuild, Test) := false



//publishTo in ThisBuild := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))

publishMavenStyle in ThisBuild := true

publishArtifact in (ThisBuild, Test) := false

pomIncludeRepository := { _ => false }


//pomExtra := (
//  <url>http://jsuereth.com/scala-arm</url>
//    <licenses>
//      <license>
//        <name>BSD-style</name>
//        <url>http://www.opensource.org/licenses/bsd-license.php</url>
//        <distribution>repo</distribution>
//      </license>
//    </licenses>
//    <scm>
//      <url>git@github.com:jsuereth/scala-arm.git</url>
//      <connection>scm:git:git@github.com:jsuereth/scala-arm.git</connection>
//    </scm>
//    <developers>
//      <developer>
//        <id>jsuereth</id>
//        <name>Josh Suereth</name>
//        <url>http://jsuereth.com</url>
//      </developer>
//    </developers>)



//parallelExecution in (ThisBuild, Test) := true


addCommandAlias("cc", ";clean;compile")

addCommandAlias("pl", ";clean;publishLocal")

addCommandAlias("pr", ";clean;publish")

packSettings

publishPackArchiveTgz


packMain := Map(
  "mogopay" -> "com.mogobiz.launch.pay.Rest",
  "mogobiz" -> "com.mogobiz.launch.run.Rest",
  "mogolearn" -> "com.mogolearn.launch.run.Rest",
  "mogobiz-all" -> "com.mogobiz.launch.run.RestAll",
  "mogobiz-selenium" -> "com.mogobiz.selenium.run.RestSelenium"
)

packExtraClasspath := Map("mogobiz-all" -> Seq("${PROG_HOME}/conf"), "mogopay" -> Seq("${PROG_HOME}/conf"), "mogobiz" -> Seq("${PROG_HOME}/conf"), "mogobiz-selenium" -> Seq("${PROG_HOME}/conf"))

packGenerateWindowsBatFile := true

packExpandedClasspath := false

fork in ThisBuild := true

scalafmtConfig in ThisBuild := Some(file(".scalafmt"))


