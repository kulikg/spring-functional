package spring_functional

import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.result.view.HttpMessageWriterView
import org.springframework.web.server.adapter.WebHttpHandlerBuilder

val beans = beans {
    bean<DummyService>()
    bean<PubSubService>()
    bean<DummyHandler>()
    bean<Routes>()
    bean { bean<HttpMessageWriterView>() }
    bean(WebHttpHandlerBuilder.WEB_HANDLER_BEAN_NAME) {
        RouterFunctions.toWebHandler(ref<Routes>().router())
    }
}
