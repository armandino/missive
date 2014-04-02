package com.missive.server.service

import com.missive.server.domain.Message
import scala.collection.mutable.Buffer
import com.missive.server.domain.Location
import grizzled.slf4j.Logging

trait MissiveService extends Logging {

  //Id generator
  def uuid = java.util.UUID.randomUUID.toString

  //The database
  val m1 = Message(uuid, "user1", "hello world", Location("-123.456", "49.551"), "1 min ago")
  val m2 = Message(uuid, "user1", "wahooo", Location("-123.44356", "49.487"), "3 min ago")
  val m3 = Message(uuid, "user1", "working", Location("-123.354", "49.555"), "5 min ago")
  val messages = Buffer(m1, m2, m3)

  def getAllMessage() = {
    logger.info("Get All Messages")
    messages.toList
  }

  def getMessage(id: String) = {
    logger.info(s"Get Message $id")
    messages.find(_.id == id)
  }

  def addMessage(msg: Message) = {
    logger.info("Add Message " + msg)
    messages += msg
  }

}