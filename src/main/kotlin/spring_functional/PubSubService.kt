package spring_functional

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.DirectProcessor
import reactor.core.publisher.toMono

private val input: DirectProcessor<Message> = DirectProcessor.create()
private val output = input.publish().autoConnect()

class PubSubService {

    fun publish(msg: Flux<Message>) = msg.doOnNext {
        logger.info( "message dispatched $it")
        input.onNext(it)
    }

    fun receive(request: Mono<Void>) = request.transform{ output.toMono() }

}
