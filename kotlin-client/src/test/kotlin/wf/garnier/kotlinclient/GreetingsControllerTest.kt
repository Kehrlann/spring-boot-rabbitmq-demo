package wf.garnier.kotlinclient

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class GreetingsControllerTest

{

    @Test
    fun `it is fun fun fuuuun` () {
        listOf(1,2,3,4).map{ it * 2}
                .filter { it < 5}
                .forEach { println(it) }
    }


    @Test
    fun `it contains daniel` () {
        val myString = "Hello my name is Daniel"
        assertThat(myString.containsDaniel()).isTrue()

        val myNullableString: String? = null
        assertThat(myNullableString.isNullOrEmpty()).isTrue()
    }
}