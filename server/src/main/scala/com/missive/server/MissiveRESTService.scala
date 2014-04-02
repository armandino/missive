package com.missive.server
import spray.routing._
import spray.http._
import spray.http.MediaTypes._
import com.missive.server.domain.Message
import com.missive.server.domain.Location
import spray.json._
import spray.json.DefaultJsonProtocol._
import spray.httpx.SprayJsonSupport._
import scala.collection.mutable.Buffer
import spray.httpx.marshalling.ToResponseMarshallable.isMarshallable
import spray.routing.Directive.pimpApply
import spray.routing.directives.ParamDefMagnet.apply
import spray.httpx.SprayJsonSupport._
trait MissiveRESTService extends HttpService with MissiveJsonProtocol {

  val m1 = Message(uuid, "user1", "hello world", Location("-123.456", "49.551"), "1 min ago")
  val m2 = Message(uuid, "user1", "wahooo", Location("-123.44356", "49.487"), "3 min ago")
  val m3 = Message(uuid, "user1", "working", Location("-123.354", "49.555"), "5 min ago")
  val messages = Buffer(m1, m2, m3)

  def uuid = java.util.UUID.randomUUID.toString
  
  val route =
    get {
      path("allmessages") {
        complete(messages.toList)
      } ~
        path("messages") {
          parameters('lat, 'long) { (lat, long) =>
            complete(s"You entered $lat $long")
          }
        }
    } ~
      post {
        path("message") {
          entity(as[Message]) { msg: Message =>
            
            //Set the ID, and add it to the set
            val msgWithIDSet = msg.copy(id = uuid)
            messages += msgWithIDSet
            
            complete(StatusCodes.NoContent)
          }
        }
      }
}
