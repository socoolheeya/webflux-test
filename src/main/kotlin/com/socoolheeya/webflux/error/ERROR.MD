Error 관련 메서드 정리
-------------
-------------

`onErrorContinue`는 리액티브 프로그래밍에서 사용되는 에러 처리 연산자 중 하나이다.
이 연산자는 에러가 발생했을 때, 일부 또는 전체 요소를 건너뛰고 계속 진행하도록 도와주는데, 주로 다음과 같은 상황에서 사용된다.

1. **부분적인 실패 처리 (Partial Failure Handling):**
   * 전체 시퀀스에서 일부 요소에 대한 처리 중 에러가 발생해도, 그 에러가 포함된 요소만 건너뛰고 나머지 요소들은 계속 처리하고 싶을 때 사용된다.

```java
public void onErrorContinueExample() {
   Flux<Integer> numbersFlux = Flux.just(1, 2, 3, 4, 5);

   numbersFlux
      .map(number -> {
         if (number == 3) {
            throw new RuntimeException("Error processing element 3");
         }
        return number * 2;
      })
      .onErrorContinue((error, value) -> {
         // 에러가 발생한 경우, 해당 요소를 건너뛰고 계속 진행
         System.err.println("Error occurred: " + error.getMessage());
         System.err.println("Skipping element: " + value);
      })
      .subscribe(
         value -> System.out.println("Processed value: " + value),
         error -> System.err.println("Final error: " + error),
         () -> System.out.println("Sequence completed")
    );
}
```
위의 예제에서는 3이라는 값에 대한 처리에서 에러가 발생하면, onErrorContinue가 해당 요소를 건너뛰고 나머지 요소들에 대한 처리를 계속한다.


2. 에러 로깅 및 처리:
   * 에러가 발생했을 때 로깅하거나 특정 동작을 수행하고, 그 후 계속 진행하고 싶을 때 사용한다.
```java
public void test() {
   Flux<Integer> numbersFlux = Flux.just(1, 2, 3, 4, 5);

   numbersFlux
     .map(number -> {
        if (number == 3) {
           throw new RuntimeException("Error processing element 3");
        }
        return number * 2;
     })
     .onErrorContinue((error, value) -> {
        // 에러 로깅 및 특정 동작 수행
        System.err.println("Error occurred: " + error.getMessage());
        System.err.println("Skipping element: " + value);
        // 여기서 추가적인 로깅이나 에러 처리 가능
     })
     .subscribe(
             value -> System.out.println("Processed value: " + value),
             error -> System.err.println("Final error: " + error),
             () -> System.out.println("Sequence completed")
     );
}
```
`onErrorContinue`는 특정 에러가 발생하면 해당 요소를 건너뛰고 계속 진행하는 유연한 에러 처리 방식을 제공한다.

-------------