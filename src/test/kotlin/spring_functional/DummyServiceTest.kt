package spring_functional

import org.junit.jupiter.api.Test
import reactor.test.StepVerifier
import reactor.test.publisher.TestPublisher

class DummyServiceTest {

    private val service = DummyService()
    private val given = TestPublisher.create<Void>()
    private fun then() = StepVerifier.create( service.all(given.mono()).log() )

    @Test
    fun `dummy should provide repository data`() {
        given.complete()
        then().expectNextCount(1).verifyComplete()
    }

}

