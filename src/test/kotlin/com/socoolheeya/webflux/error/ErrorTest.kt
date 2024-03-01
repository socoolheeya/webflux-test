package com.socoolheeya.webflux.error

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux

@SpringBootTest
class ErrorTest {

    companion object {
        val log: Logger = LoggerFactory.getLogger(ErrorTest::class.java)
    }


    @Test
    @DisplayName("doOnError 테스트")
    fun doOnErrorTest() {
        Flux.range(1,5)
            .handle { it, sink ->
                if ( it == 3) {
                    sink.error(IllegalArgumentException("error value : $it"))
                    return@handle
                }
                sink.next(it)
            }
            .doOnError {
                log.error("Error : ${it.message}")
            }
            .log()
            .subscribe()
    }

    @Test
    @DisplayName("doOnComplete 테스트")
    fun doOnCompleteTest() {
        Flux.range(1, 10)
            .doOnComplete {
                log.warn("do complete method called")
            }
            .handle { it, sink ->
                if ( it == 11) {
                    sink.error(IllegalArgumentException("error value : $it"))
                    return@handle
                }
                sink.next(it)
            }
            .log()
            .subscribe()
    }

    @Test
    @DisplayName("doOnCancel 테스트")
    fun doOnCancelTest() {
        Flux.range(1, 10)
            .doOnCancel {
                log.warn("do doOnCancel method called")
            }
            .handle { it, sink ->
                if ( it == 11) {
                    sink.error(IllegalArgumentException("error value : $it"))
                    return@handle
                }
                sink.next(it)
            }
            .log()
            .subscribe()
    }

    @Test
    @DisplayName("onErrorComplete 테스트")
    fun onErrorCompleteTest() {
        Flux.range(1, 10)
            .onErrorComplete {
                log.error("onErrorComplete : " + it.message)
                false
            }
            .handle { it, sink ->
                if ( it == 11) {
                    sink.error(IllegalArgumentException("error value : $it"))
                    return@handle
                }
                sink.next(it)
            }
            .log()
            .subscribe()
    }

    @Test
    @DisplayName("onErrorContinue 테스트")
    fun onErrorContinueTest() {
        Flux.range(1, 10)
            .onErrorContinue { t, u ->
                log.error("### onErrorContinue ${t.message}")
                log.info("#### next value : ${u.toString()}")
            }
            .handle { it, sink ->
                if ( it == 3) {
                    sink.error(IllegalArgumentException("error value : $it"))
                    return@handle
                }
                sink.next(it)
            }
            .log()
            .subscribe()
    }

}