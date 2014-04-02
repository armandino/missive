organization := "com.missive.server"

name := "MissiveServer"

version := "1.0"

scalaVersion := "2.10.4"


seq(webSettings: _*)


libraryDependencies += "io.spray" % "spray-servlet" % "1.3.1" 

libraryDependencies += "io.spray" % "spray-routing" % "1.3.1"

libraryDependencies += "io.spray" %%  "spray-json" % "1.2.5"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.1"

libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % "2.3.1" 

libraryDependencies += "org.eclipse.jetty" % "jetty-webapp" % "9.1.0.v20131115" % "container"

libraryDependencies += "org.eclipse.jetty" % "jetty-plus" % "9.1.0.v20131115" % "container"
            
libraryDependencies += "javax.servlet" % "javax.servlet-api" % "3.0.1" % "container"

libraryDependencies += "org.clapper" %% "grizzled-slf4j" % "1.0.1"
                       
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.0.13" % "runtime"



libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.3.1" % "test"

libraryDependencies += "io.spray" % "spray-testkit" % "1.3.1" % "test"

libraryDependencies += "org.specs2" %% "specs2" % "2.2.3" % "test"

libraryDependencies += "junit" % "junit" % "4.8.1" % "test"
