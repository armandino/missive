package com.missive.server.rest

import akka.actor.Actor
import akka.actor.ActorLogging

class MissiveServiceActor extends Actor with MissiveServiceRest {
  def actorRefFactory = context
  def receive = runRoute(route)
}
