package com.missive.server
import spray.routing._
import spray.http._
import spray.http.MediaTypes._
import com.missive.server.domain.Message
import com.missive.server.domain.Location
import spray.json._
import spray.json.DefaultJsonProtocol._
import spray.httpx.SprayJsonSupport._

trait MissiveJsonProtocol extends DefaultJsonProtocol {
  implicit val locationFormat = jsonFormat2(Location)
  implicit val messageFormat = jsonFormat5(Message)
}
