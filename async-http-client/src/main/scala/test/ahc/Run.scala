package test.ahc

import org.asynchttpclient.DefaultAsyncHttpClient
import org.slf4j.LoggerFactory

import scala.io.StdIn

object Run extends App {
  val log = LoggerFactory.getLogger(this.getClass)

  val client = new DefaultAsyncHttpClient()

  log.info("Press any key to send request ...")
  StdIn.readLine()

  val f = client.executeRequest(client.prepareGet("http://localhost:8080/wait").build())
  log.info("Sent ...")
  Thread.sleep(1000)
  f.cancel(true) // receives 0B
//  f.abort(new RuntimeException("abort")) // receives 400KB in the background
//  f.get()

  log.info("Done. Press any key to exit ...")
  StdIn.readLine()

  log.info("Keeping the reference: " + f)
  client.close()
}
