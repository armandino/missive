package com.missive.server.rest
import spray.routing._
import spray.http._
import spray.http.MediaTypes._
import com.missive.server.domain.Message
import spray.json._
import spray.json.DefaultJsonProtocol._
import spray.httpx.SprayJsonSupport._
import spray.httpx.marshalling.ToResponseMarshallable.isMarshallable
import spray.routing.Directive.pimpApply
import spray.routing.directives.ParamDefMagnet.apply
import spray.httpx.SprayJsonSupport._
import com.missive.server.service.MissiveService

trait MissiveServiceRest extends HttpService with MissiveJsonProtocol with MissiveService {
 
  val route =
    path("messages") {
      get {
        parameters('id?) { (idOptional) =>
          idOptional match {
            case None => complete(getAllMessage)
            case Some(id) =>
              getMessage(id) match {
                case Some(m) => complete(m)
                case None => complete(StatusCodes.NotFound)
              }
          }
        }
      } ~
        post {
          entity(as[Message]) { msg =>
            addMessage(msg)
            complete(StatusCodes.NoContent)
          }
        }
    }

}
