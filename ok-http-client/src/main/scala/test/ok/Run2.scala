package test.ok

import okhttp3._
import org.slf4j.LoggerFactory

import scala.sys.process._

object Run2 extends App {
//  val log = LoggerFactory.getLogger(this.getClass)
//
//  val client = new OkHttpClient()
//
//  val p = "/Applications/Wireshark.app/Contents/MacOS/wireshark -i lo0 -k -a duration:10".run()
//  Thread.sleep(3000)
//
//  log.info("Starting virtual thread ...")
//
//  val t = Thread.startVirtualThread(() => {
//    try {
//      log.info("Sending ...")
//      val r = client.newCall(new Request.Builder().url("http://localhost:8080/wait").build()).execute()
//      log.info("Sent ...")
//      log.info(s"Received body length: ${r.body().string().length}")
//    } finally {
//      log.info(s"Cleaning up ... ${Thread.interrupted()}")
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
}
