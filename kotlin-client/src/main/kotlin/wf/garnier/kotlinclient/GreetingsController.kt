package wf.garnier.kotlinclient

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingsController {

    @GetMapping("/hello")
    fun sayHi() = "Hello world !"

}

class IcanPutTwoClassesHere {

}

data class MyClass(val age: Int, val name: String)