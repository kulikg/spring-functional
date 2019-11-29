package spring_functional

data class Request(val ids: List<Long>)
data class Response(val name: List<Person?>)
data class Person(val name: String, val id: Long)
data class Message(val id :Long = sequence.getAndAdd(1), val content: String)

