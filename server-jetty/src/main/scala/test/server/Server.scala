package test.server

import jakarta.servlet.http.{HttpServletRequest, HttpServletResponse}
import org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory
import org.eclipse.jetty.server.{HttpConfiguration, HttpConnectionFactory, Request, Server, ServerConnector}
import org.eclipse.jetty.server.handler.AbstractHandler
import org.eclipse.jetty.util.thread.QueuedThreadPool
import org.slf4j.LoggerFactory

object Server extends App {
  private val log = LoggerFactory.getLogger(this.getClass)
  private val LargeBody = "x" * 1048576 * 100 // 100MiB

  val threadPool = new QueuedThreadPool()
  threadPool.setName("server")
  val server = new Server(threadPool)

  val httpConfig = new HttpConfiguration()
  val http11 = new HttpConnectionFactory(httpConfig)
  val h2c = new HTTP2CServerConnectionFactory(httpConfig)

  val connector = new ServerConnector(server, http11, h2c)
  connector.setPort(8080)

  server.addConnector(connector)
  server.setHandler(new AbstractHandler() {
    override def handle(target: String, jettyRequest: Request, request: HttpServletRequest, response: HttpServletResponse): Unit = {
      jettyRequest.setHandled(true)

      Thread.sleep(2000)

      response.setStatus(200)
      response.setContentType("text/plain")
      response.setContentLength(LargeBody.length)
      response.getWriter.write(LargeBody)

      log.info("Sending wait response")
    }
  })
  server.start()

  log.info("Started")
}
