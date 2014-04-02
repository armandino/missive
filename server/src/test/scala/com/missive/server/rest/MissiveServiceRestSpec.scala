package com.missive.server.rest

import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest
import spray.http._
import StatusCodes._
import com.missive.server.domain.Message
import spray.httpx.SprayJsonSupport._
import com.missive.server.domain.Location
import spray.json._

class MissiveServiceRestSpec extends Specification with Specs2RouteTest with MissiveServiceRest {
  def actorRefFactory = system
  
  "MissiveServiceRest" should {

    "fetch all messages" in {
      Get("/messages") ~> route ~> check {
        responseAs[List[Message]].size === 3
      }
    }

    "fetch single messages" in {
      Get("/messages?id=" + messages(0).id) ~> route ~> check {
        responseAs[Message].id === messages(0).id
      }
    }

    "fetch no messages" in {
      Get("/messages?id=111") ~> route ~> check {
        status === StatusCodes.NotFound
      }
    }

    "add new messages" in {
      val id = uuid
      val json = Message(id, "user1", "hello world222", Location("-123.456", "49.551"), "1 min ago")
      Post("/messages", json) ~> route ~> check {
        status === StatusCodes.NoContent
      }
      Get("/messages?id=" + id) ~> route ~> check {
        responseAs[Message].id === id
      }
    }

  }
}
