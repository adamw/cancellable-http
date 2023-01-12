package test.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, MediaTypes}
import akka.http.scaladsl.server.Directives._
import akka.stream.scaladsl.Source
import akka.util.ByteString
import org.slf4j.LoggerFactory

import scala.concurrent.duration.{Duration, DurationInt}
import scala.concurrent.{Await, Future}

object Server extends App {
  private val log = LoggerFactory.getLogger(this.getClass)
  private val SmallBody = "x" * 1024 // 1KiB
  private val LargeBody = "x" * 1048576 * 100 // 100MiB

  private implicit val system = ActorSystem()
  private implicit val executionContext = system.dispatcher

  val routes =
    path("wait") {
      get {
        complete {
          Future {
            Thread.sleep(2000L)
            log.info("Sending wait response")
            HttpEntity(LargeBody)
          }
        }
      }
    } ~ path("stream") {
      get {
        complete {
          HttpEntity.apply(
            MediaTypes.`text/plain`.withMissingCharset,
            Source
              .repeat(ByteString(SmallBody))
              .take(30)
              .throttle(1, 100.millis)
              .map(x => { log.info("Sending chunk"); x })
              .mapError { case e: Exception =>
                log.info("Got error", e)
                e
              }
          )
        }
      }
    }

  Await.result(Http().newServerAt("localhost", 8080).bind(routes), Duration.Inf)
  log.info("Started")
}
