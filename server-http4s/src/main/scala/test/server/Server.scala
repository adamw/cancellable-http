package test.server

import cats.effect._
import com.comcast.ip4s.IpLiteralSyntax
import fs2._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Router
import org.http4s.server.middleware.Logger
import org.slf4j.LoggerFactory

import scala.concurrent.duration.DurationInt

object Server extends IOApp {
  private val log = LoggerFactory.getLogger(this.getClass)
  private val SmallBody = "x" * 1024 // 1KiB
  private val LargeBody = "x" * 1048576 * 100 // 100MiB

  private val services = HttpRoutes.of[IO] {
    case GET -> Root / "wait" =>
      IO.sleep(2.seconds) *> IO(log.info("Sending wait response")) *> Ok(LargeBody)
    case GET -> Root / "stream" =>
      Ok(
        Stream
          .emit(SmallBody)
          .covary[IO]
          .repeat
          .take(20)
          .metered(100.millis)
          .map(x => { log.info("Sending chunk"); x })
          .handleErrorWith(e => Stream.exec(IO(log.info("Got error", e))) ++ Stream.raiseError[IO](e))
          .onFinalizeCase(e => IO(log.info(s"Finalize: $e")))
      )
  }

  private val server = EmberServerBuilder
    .default[IO]
    .withPort(port"8080")
    .withHttpApp(Router("/" -> Logger.httpRoutes(false, false)(services)).orNotFound)
    .build

  override def run(args: List[String]): IO[ExitCode] = server.use(_ => IO(log.info("Started")) *> IO.never).as(ExitCode.Success)
}
