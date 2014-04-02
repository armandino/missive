package com.missive.server.rest

import akka.actor.Actor

class MissiveServiceActor extends Actor with MissiveServiceRest {
  def actorRefFactory = context
  def receive = runRoute(route)
}
