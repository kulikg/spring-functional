package spring_functional

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType.*
import org.springframework.web.reactive.function.server.router

class Routes(private val dummy: DummyHandler, private val service: DummyService) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun router() = router {

        logger.info("routing the app")
        accept(ALL).nest {
            POST("/"  , dummy::select)
            POST("/b" , dummy::background)
            POST("/p" , dummy::publish)
            GET("/s"  , dummy::subscribe)
            GET("/a"  , dummy::all)
        }

    }

}
