package test.http4s

import cats.effect._
import org.http4s.ember.client.EmberClientBuilder
import org.slf4j.LoggerFactory

import scala.concurrent.duration.DurationInt
import sys.process._

object Run extends IOApp {
  private val log = LoggerFactory.getLogger(this.getClass)

  override def run(args: List[String]): IO[ExitCode] = EmberClientBuilder
    .default[IO]
    .build
    .use { client =>
      for {
        _ <- IO("/Applications/Wireshark.app/Contents/MacOS/wireshark -i lo0 -k -a duration:10".run())
        _ <- IO.sleep(3.seconds)
        _ <- IO(log.info("Sending ..."))
        f <- client
          .expect[String]("http://localhost:8080/wait")
          .guarantee(IO(log.info("Cleaning up ...")) *> IO.sleep(1.second) *> IO(log.info("Cleaned up")))
          .start
        _ <- IO(log.info("Sent ..."))
        _ <- IO.sleep(1.second)
        _ <- IO(log.info("Cancelling ..."))
        _ <- f.cancel
        _ <- IO(log.info("Done."))
      } yield ()
    }
    .as(ExitCode.Success)
}
