:large_blue_diamond: Error 관련 메서드 정리
-------------
### 1. onErrorContinue

> `onErrorContinue`는 리액티브 프로그래밍에서 사용되는 에러 처리 연산자 중 하나이다. <br>
이 연산자는 에러가 발생했을 때, 일부 또는 전체 요소를 건너뛰고 계속 진행하도록 도와주는데, 주로 다음과 같은 상황에서 사용된다.

#### 1.1. 부분적인 실패 처리 (Partial Failure Handling):
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
<br><br>

#### 1.2. 에러 로깅 및 처리:
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
### 2.doOnError 

> `doOnError`는 리액티브 프로그래밍에서 사용되는 연산자 중 하나로, 에러가 발생했을 때 어떤 동작을 수행하고자 할 때 사용된다. <br>
주로 다음과 같은 상황에서 `doOnError`를 활용할 수 있다:

##### 2.1. 에러 로깅:
* 에러가 발생했을 때 로깅을 수행하고자 할 때 사용됩니다. 주로 디버깅이나 로깅 목적으로 활용된다.

````java
public void doOnErrorExample1() {
    Flux<Integer> numbersFlux = Flux.just(1, 2, 3, 4, 5);

    numbersFlux
            .map(number -> {
                if (number == 3) {
                    throw new RuntimeException("Error processing element 3");
                }
                return number * 2;
            })
            .doOnError(error -> {
                // 에러가 발생했을 때 로깅 수행
                System.err.println("Error occurred: " + error.getMessage());
            })
            .subscribe(
                    value -> System.out.println("Processed value: " + value),
                    error -> System.err.println("Final error: " + error),
                    () -> System.out.println("Sequence completed")
            );
}
````
에러가 발생하면 `doOnError` 블록이 실행되어 에러를 로깅하고, 그 후에 에러 처리를 계속 진행한다.
<br><br>

#### 2.2. 에러 발생 시 특정 동작 수행:
* 에러가 발생했을 때 특정 동작을 수행하고자 할 때 사용된다. 예를 들어, 에러가 발생하면 대체값을 사용하거나, 다른 경로로 데이터 처리를 진행하는 등의 상황에 유용하다.

````java
public void doOnErrorExample2() {
    Flux<Integer> numbersFlux = Flux.just(1, 2, 3, 4, 5);

    numbersFlux
            .map(number -> {
                if (number == 3) {
                    throw new RuntimeException("Error processing element 3");
                }
                return number * 2;
            })
            .doOnError(error -> {
                // 에러가 발생하면 특정 동작 수행
                System.err.println("Error occurred: " + error.getMessage());
                // 여기서 추가적인 에러 처리 또는 복구 동작 수행 가능
            })
            .subscribe(
                    value -> System.out.println("Processed value: " + value),
                    error -> System.err.println("Final error: " + error),
                    () -> System.out.println("Sequence completed")
            );
}
````
`doOnError`를 사용하면 에러가 발생한 경우에 어떤 동작을 수행할지 유연하게 정의할 수 있다. 이를 통해 코드를 유지보수하거나 에러 처리 전략을 개발하는 데 도움이 된다.