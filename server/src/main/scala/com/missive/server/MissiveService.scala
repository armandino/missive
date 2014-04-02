package com.missive.server

import akka.actor.Actor
import spray.routing._
import spray.http._
import MediaTypes._
import com.missive.server.domain.Message
import com.missive.server.domain.Location
import spray.json._
import DefaultJsonProtocol._
import spray.httpx.SprayJsonSupport
import spray.httpx.SprayJsonSupport._
import scala.collection.mutable.Buffer

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class MissiveServiceActor extends Actor with MissiveService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(myRoute)
}

// this trait defines our service behavior independently from the service actor
trait MissiveService extends HttpService with DefaultJsonProtocol {

  implicit val locationFormat = jsonFormat2(Location)
  implicit val messageFormat = jsonFormat4(Message)

  val m1 = Message("user1", "hello world", Location("-123.456", "49.551"), "1 min ago")
  val m2 = Message("user1", "wahooo", Location("-123.44356", "49.487"), "3 min ago")
  val m3 = Message("user1", "working", Location("-123.354", "49.555"), "5 min ago")
  val messages = Buffer(m1, m2, m3)

  val myRoute =
    get {
      path("allmessages") {
        complete(messages.toList)
      } ~
        path("messages") {
          parameters('lat, 'long) { (lat, long) =>
            complete {
              s"You entered $lat $long"
            }
          }
        }
    } ~
      post {
        path("message") {
          entity(as[Message]) { m:Message =>
            messages += m
            complete("")
          }
        }
      }

}