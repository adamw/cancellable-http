package test.hc

import org.slf4j.LoggerFactory

import java.net.URI
import java.net.http.HttpResponse.BodyHandlers
import java.net.http.{HttpClient, HttpRequest}
import scala.sys.process._

object Run2 extends App {
//  val log = LoggerFactory.getLogger(this.getClass)
//
//  val client = HttpClient.newHttpClient()
//
//  //  log.info("Press any key to send request ...")
//  //  StdIn.readLine()
//
//  val p = "/Applications/Wireshark.app/Contents/MacOS/wireshark -i lo0 -k -a duration:10".run()
//  Thread.sleep(3000)
//
//  log.info("Starting virtual thread ...")
//
//  val t = Thread.startVirtualThread(() => {
//    try {
//      log.info("Sending ...")
//      val r = client.send(
//        HttpRequest.newBuilder(new URI("http://localhost:8080/wait")).GET().version(HttpClient.Version.HTTP_1_1).build(),
//        BodyHandlers.ofString()
//      )
//      log.info(s"Received body length: ${r.body().length}")
//    } finally {
//      log.info("Cleaning up ...")
//      Thread.sleep(1000)
//      log.info("Cleaned up")
//    }
//  })
//
//  Thread.sleep(1000)
//  log.info("Interrupting ...")
//  t.interrupt()
//  log.info("Interrupted ...")
//  t.join()
//  log.info("Joined ...")
//
//  log.info("Done.")
//  // log.info("Press any key to exit ...")
//  // StdIn.readLine()
}
