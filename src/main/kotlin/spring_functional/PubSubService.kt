package spring_functional

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.DirectProcessor
import reactor.core.publisher.toMono

private val input: DirectProcessor<Message> = DirectProcessor.create()
private val output = input.publish().autoConnect()

class PubSubService {

    /*
        When called it will manually trigger the DirectProcessor's onNext to trigger
        a publish signal, and emit the message.

        The output is told to autoconnect on publish signals to receive the message
     */
    fun publish(msg: Flux<Message>) = msg.doOnNext {
        logger.info( "message dispatched $it")
        input.onNext(it)
    }

    /*
        The subscribing Mono is appended to the output flux by a transformation
     */
    fun receive(request: Mono<Void>) = request.transform{ output.toMono() }

}
