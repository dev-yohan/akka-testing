name := """hello-scala"""

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
libraryDependencies += "com.typesafe.akka" % "akka-stream-experimental_2.11" % "2.0.3"
libraryDependencies += "net.databinder.dispatch" %% "dispatch-core" % "0.11.2"
libraryDependencies +=  "com.typesafe.akka" %% "akka-http-core-experimental" % "2.0.1"
libraryDependencies +=  "com.typesafe.akka" %% "akka-http-experimental" % "2.0.1"
libraryDependencies +=  "com.typesafe.akka" %% "akka-http-spray-json-experimental"  % "2.0.1"
libraryDependencies +=  "com.typesafe.akka" %% "akka-http-testkit-experimental"  % "2.0.1"

fork in run := true