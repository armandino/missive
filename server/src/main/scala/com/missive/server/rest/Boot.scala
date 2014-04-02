package com.missive.server.rest

import akka.actor.{Props, ActorSystem}
import spray.servlet.WebBoot

// this class is instantiated by the servlet initializer
// it needs to have a default constructor and implement
// the spray.servlet.WebBoot trait
class Boot extends WebBoot {

  val system = ActorSystem("missiveActorSystem")

  // the service actor replies to incoming HttpRequests
  val serviceActor = system.actorOf(Props[MissiveServiceActor])

}