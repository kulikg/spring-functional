package spring_functional

import org.springframework.context.support.StaticApplicationContext
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.server.adapter.WebHttpHandlerBuilder
import reactor.netty.http.server.HttpServer
import java.time.Duration

class Application

fun main() {
    HttpServer
        .create()
        .host("127.0.0.1")
        .port(8080)
        .handle(
            ReactorHttpHandlerAdapter(WebHttpHandlerBuilder.applicationContext(
                StaticApplicationContext().apply {
                    beans.initialize(this)
                    refresh()
                }
            ).build())
        )
        .bindUntilJavaShutdown(Duration.ofSeconds(1)) {
            logger.info("Instance ready")
        }
}
