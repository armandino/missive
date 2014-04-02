package com.missive.server.service

import com.missive.server.domain.Message
import scala.collection.mutable.Buffer
import com.missive.server.domain.Location

trait MissiveService {

  //Id generator
  def uuid = java.util.UUID.randomUUID.toString
  
  //The database
  val m1 = Message(uuid, "user1", "hello world", Location("-123.456", "49.551"), "1 min ago")
  val m2 = Message(uuid, "user1", "wahooo", Location("-123.44356", "49.487"), "3 min ago")
  val m3 = Message(uuid, "user1", "working", Location("-123.354", "49.555"), "5 min ago")
  val messages = Buffer(m1, m2, m3)

  
 
  def getAllMessage() = {
    messages.toList
  }

  def getMessage(id: String) = {
    messages.find(_.id == id)
  }

  def addMessage(msg: Message) = {
    messages += msg
  }
  
}