package com.missive.server

import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest
import spray.http._
import StatusCodes._
import com.missive.server.domain.Message
import spray.httpx.SprayJsonSupport._

class MissiveServiceSpec extends Specification with Specs2RouteTest with MissiveRESTService with MissiveJsonProtocol {
  def actorRefFactory = system
  
  "MissiveService" should {

    "fetch all messages" in {
      Get("/allmessages") ~> route ~> check {
        responseAs[List[Message]].size == 3
      }
    }

    "return a MethodNotAllowed error for PUT requests to the root path" in {
      Put() ~> sealRoute(route) ~> check {
        status === MethodNotAllowed
        responseAs[String] must contain ("HTTP method not allowed")
      }
    }
  }
}
