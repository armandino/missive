organization := "com.missive.server"

name := "MissiveServer"

version := "1.0"

scalaVersion := "2.10.4"



seq(webSettings: _*)

Revolver.settings



libraryDependencies += "io.spray" % "spray-servlet" % "1.3.1" 

libraryDependencies += "io.spray" % "spray-routing" % "1.3.1"

libraryDependencies += "io.spray" %%  "spray-json" % "1.2.5"

libraryDependencies += "org.eclipse.jetty" % "jetty-webapp" % "9.1.0.v20131115" % "container"

libraryDependencies += "org.eclipse.jetty" % "jetty-plus" % "9.1.0.v20131115" % "container"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.1"
            
libraryDependencies += "javax.servlet" % "javax.servlet-api" % "3.0.1" % "container"




libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.3.1" % "test"

libraryDependencies += "io.spray" % "spray-testkit" % "1.3.1" % "test"

libraryDependencies += "org.specs2" %% "specs2" % "2.2.3" % "test"

