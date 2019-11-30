package spring_functional

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.concurrent.Queues
import java.lang.Thread.sleep


class DummyService {

    /*
        Forking blocking operation to multiple threads, execute them at once, and collect the result
     */
    fun find(request: Mono<Request>): Mono<Response> = request
        .map { it.ids }
        .flatMapMany { Flux.fromIterable(it) }
        .limitRate(Queues.XS_BUFFER_SIZE)
        .parallel(Queues.XS_BUFFER_SIZE)
        .runOn(slowPool)
        .map { blockingInvocation(it) }
        .sequential(Queues.XS_BUFFER_SIZE)
        .collectList()
        .map { Response(it) }

    /*
        Return the the same mono instance
     */
    fun all(emptyRequest: Mono<Void>) :Mono<Response> = emptyRequest.thenReturn ( Response(repository.values.toList()) )

    /*
        Forking blocking operation, and detach the client
     */
    fun background(request: Mono<Request>): Mono<Response> =
        request
            .map { it.ids }
            .flatMapMany { Flux.fromIterable(it) }
            .onBackpressureBuffer()
            .limitRate(64)
            .doOnNext { Mono.just(it).publishOn(slowPool).map(this::blockingInvocation).subscribe() }
            .map { repository[it] }
            .collectList()
            .map { Response(it) }

    private fun blockingInvocation(id: Long): Person? {
        sleep(5000)
        logger.info("***************************************************************** async execution completed $id")
        return repository[id]
    }
}
