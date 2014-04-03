package com.missive.server.rest
import spray.routing._
import spray.http._
import spray.http.MediaTypes._
import com.missive.server.domain.Message
import com.missive.server.domain.Location
import spray.json._
import spray.json.DefaultJsonProtocol._
import spray.httpx.SprayJsonSupport._


/**
 * Case class that converts our Domain classes into JSON
 */
trait MissiveJsonProtocol extends DefaultJsonProtocol {
  implicit val locationFormat = jsonFormat2(Location)
  implicit val messageFormat = jsonFormat5(Message)
}
