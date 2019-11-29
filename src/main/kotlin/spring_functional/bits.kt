package spring_functional

import org.slf4j.LoggerFactory
import reactor.core.scheduler.Schedulers
import java.util.concurrent.atomic.AtomicLong

val sequence = AtomicLong(0)
var logger = LoggerFactory.getLogger("global")
val slowPool = Schedulers.newElastic("lazy", 2)

val repository = mapOf<Long, Person>(
        Pair(1, Person("jozsi", 1)),
        Pair(2, Person("geza", 2)),
        Pair(3, Person("jancsi", 3)),
        Pair(4, Person("juliska", 4))
)
