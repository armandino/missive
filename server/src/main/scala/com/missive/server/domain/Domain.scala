package com.missive.server.domain

case class User(id: String, userIdentifier: String)

case class Location(lat: String, long: String)

case class Message(id:String, userid: String, body: String, loc: Location, time: String)


