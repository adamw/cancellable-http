package test.hc

import org.slf4j.LoggerFactory

import java.net.URI
import java.net.http.HttpResponse.BodyHandlers
import java.net.http.{HttpClient, HttpRequest}
import scala.sys.process._

object Run extends App {
  val log = LoggerFactory.getLogger(this.getClass)

  val client = HttpClient.newHttpClient()

  //  log.info("Press any key to send request ...")
  //  StdIn.readLine()

  val p = "/Applications/Wireshark.app/Contents/MacOS/wireshark -i lo0 -k -a duration:10".run()
  Thread.sleep(3000)

  log.info("Sending ...")
  val f = client.sendAsync(
    HttpRequest.newBuilder(new URI("http://localhost:8080/wait")).GET().version(HttpClient.Version.HTTP_1_1).build(),
    BodyHandlers.ofString()
  )
  log.info("Sent ...")
  Thread.sleep(1000)
  f.cancel(true) // java 11: no effect; java 20: works
//  f.get()

  log.info("Done.")
  // log.info("Press any key to exit ...")
  // StdIn.readLine()

  log.info("Keeping the reference: " + f)
}
