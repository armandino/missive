package com.missive.server

import akka.actor.Actor

class MissiveServiceActor extends Actor with MissiveRESTService {
  def actorRefFactory = context
  def receive = runRoute(route)
}
