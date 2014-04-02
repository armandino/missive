package com.missive.server.rest

import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest
import spray.http._
import StatusCodes._
import com.missive.server.domain.Message
import spray.httpx.SprayJsonSupport._
import com.missive.server.domain.Location
import spray.json._
import com.missive.server.service.MissiveService

class MissiveServiceSpec extends Specification with MissiveService {
  
  "MissiveService" should {

    "fetch all messages" in {
      getAllMessage must not be empty 
    }

    "fetch single messages" in {
      val id = messages(0).id
      getMessage(id) must not be (None)
    }

    "fetch no messages" in {
      getMessage("0") must be (None)
    }
    
    "add new messages" in {
      val id = uuid
      val msg = messages(0).copy(id=id)
      addMessage(msg)
      getMessage(id) must not be (None)
      getAllMessage must have size(4)
    }

  }
}
