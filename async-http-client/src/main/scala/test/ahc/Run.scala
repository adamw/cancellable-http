package test.ahc

import org.asynchttpclient.DefaultAsyncHttpClient
import org.slf4j.LoggerFactory

import scala.io.StdIn

import sys.process._

object Run extends App {
  val log = LoggerFactory.getLogger(this.getClass)

  val client = new DefaultAsyncHttpClient()

//  log.info("Press any key to send request ...")
//  StdIn.readLine()

  val p = "/Applications/Wireshark.app/Contents/MacOS/wireshark -i lo0 -k -a duration:10".run()
  Thread.sleep(3000)

  log.info("Sending ...")
//  val f = client.executeRequest(client.prepareGet("http://localhost:8080/stream").build())
  val f = client.executeRequest(client.prepareGet("http://localhost:8080/wait").build())
  log.info("Sent ...")
  Thread.sleep(1000)
  f.cancel(true) // receives 0B-128KB
//  f.abort(new RuntimeException("abort")) // receives 128-~400KB
//  f.get()

  log.info("Done.")
  // log.info("Press any key to exit ...")
  // StdIn.readLine()

  log.info("Keeping the reference: " + f)
  client.close()
}
