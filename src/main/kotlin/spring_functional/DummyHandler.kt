package spring_functional

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters.fromPublisher
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

private fun body(result: Mono<Response>) = ok()
    .contentType(MediaType.APPLICATION_JSON)
    .body(fromPublisher(result, Response::class.java))

class DummyHandler(private val dummyService: DummyService, private val pubsub: PubSubService) {

    fun select(request: ServerRequest) = body(request.bodyToMono(Request::class.java).transform { dummyService.find(it) })

    fun all(emptyRequest: ServerRequest) = body(dummyService.all(emptyRequest.bodyToMono(Void::class.java)))

    fun background(request: ServerRequest): Mono<ServerResponse> =
        body(request.bodyToMono(Request::class.java).transform { dummyService.background(it) })

    fun publish(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToFlux(Message::class.java)
            .transform(pubsub::publish)
            .then(ok().contentType(MediaType.APPLICATION_JSON).body(fromPublisher(Mono.just("ok"), String::class.java)))

    fun subscribe(request: ServerRequest): Mono<ServerResponse> = ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(fromPublisher(pubsub.receive(request.bodyToMono(Void::class.java)), Message::class.java))

}
