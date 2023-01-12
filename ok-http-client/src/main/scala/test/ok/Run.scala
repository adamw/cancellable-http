package test.ok

import okhttp3.{Call, Callback, OkHttpClient, Request, Response}
import org.slf4j.LoggerFactory

import java.io.IOException
import scala.sys.process._

object Run extends App {
  val log = LoggerFactory.getLogger(this.getClass)

  val client = new OkHttpClient()

//  log.info("Press any key to send request ...")
//  StdIn.readLine()

  val p = "/Applications/Wireshark.app/Contents/MacOS/wireshark -i lo0 -k -a duration:10".run()
  Thread.sleep(3000)

  log.info("Sending ...")
//  val f = client.executeRequest(client.prepareGet("http://localhost:8080/stream").build())
  val f = client.newCall(new Request.Builder().url("http://localhost:8080/wait").build())
  f.enqueue(new Callback {
    override def onFailure(call: Call, e: IOException): Unit = {
      println("FAILURE")
      e.printStackTrace()
    }
    override def onResponse(call: Call, response: Response): Unit = println("RESPONSE")
  })
  log.info("Sent ...")
  Thread.sleep(1000)
  f.cancel()
//  f.get()

  log.info("Done.")
  // log.info("Press any key to exit ...")
  // StdIn.readLine()
}
