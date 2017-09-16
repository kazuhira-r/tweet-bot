package org.littlewings.tweetbot.rest

import javax.ws.rs.core.MediaType
import javax.ws.rs.ext.{ContextResolver, Provider}
import javax.ws.rs.{Consumes, Produces}

import com.fasterxml.jackson.databind.{ObjectMapper, SerializationFeature}
import com.fasterxml.jackson.module.scala.DefaultScalaModule

@Provider
@Consumes(Array(MediaType.APPLICATION_JSON))
@Produces(Array(MediaType.APPLICATION_JSON))
class ScalaObjectMapperProvider extends ContextResolver[ObjectMapper] {
  override def getContext(typ: Class[_]): ObjectMapper = {
    val objectMapper = new ObjectMapper
    objectMapper
      .registerModule(DefaultScalaModule)
      .enable(SerializationFeature.INDENT_OUTPUT)
    objectMapper
  }
}