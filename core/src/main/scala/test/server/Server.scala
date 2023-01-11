package test.server

import org.slf4j.LoggerFactory
import sttp.tapir._
import sttp.tapir.server.netty.NettyFutureServer

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Server extends App {
  private val log = LoggerFactory.getLogger(this.getClass)
  private val LargeBody = "x" * 1048576 * 100 // 100MiB

  val waitEndpoint = endpoint.get
    .in("wait")
    .out(stringBody)
    .serverLogicSuccess { _ =>
      Future {
        Thread.sleep(2000L)
        log.info("Sending response")
        LargeBody
      }
    }

  Await.result(NettyFutureServer().addEndpoint(waitEndpoint).start(), Duration.Inf)

  log.info("Started")
}
