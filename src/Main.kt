import kotlinx.coroutines.*

// launch() 및 async()는 CoroutineScope의 확장 함수
// 범위에서 launch() 또는 async()를 호출하여 이 범위 내에서 새 코루틴을 만듦
fun main() {
    //Android 앱 코드에서는 runBlocking()이 필요하지 않
    //runBlocking()은 다시 시작할 준비가 되면 각 태스크를 중단된 지점에서 계속 진행하여 여러 태스크를 한 번에 처리할 수 있는 이벤트 루프를 실행
    runBlocking {
        //여기의 코드는 새 코루틴에서 실행됨
        //여기는 동기식
        println("Weather forecast")



//        //launch() 함수를 사용하여 새 코루틴을 실행
//        //printForecast와 printTemperature은 각각 다른 코루틴에서 실행됨
//        //이전에는 printForecast() 정지 함수가 완전히 완료될 때까지 기다려야 printTemperature() 함수로 이동할 수 있었습니다.
//        // 이제 printForecast()와 printTemperature()가 별개의 코루틴에 있으므로 동시에 실행할 수 있습니다.
//        launch {
//            printForecast()
//        }
//        launch {
//            printTemperature()
//        }
        try {
            println(getWeatherReport())
        } catch (e: AssertionError) {
            println("Caught exception in runBlocking(): $e")
            println("Report unavailable at this time")
        }

        //위의 두개 launch가 완료되는 걸 기다리지 않고 바로 실행
        println("Have a good day!")



    }

}

//Sunny 30°C의 결합 문자열을 반환하는 함수
//coroutineScope는 범위를 만듬!
//이 범위 내에서 실행된 코루틴은 이 범위 내에 그룹화
//coroutineScope는 모든 작업이 완료된 후에만 반환
// 함수가 내부적으로 동시에 작업을 하고 있더라도 모든 작업 완료 전까지는 coroutineScope가 반환되지 않으므로 호출자에는 함수가 동기 작업처럼 보입
suspend fun getWeatherReport() = coroutineScope {
//코루틴의 반환 값이 필요 async() 함수는 Deferred 유형의 객체를 반환
    //deferred의 값은 .await()로 받아올 수 있음
    val forecast = async {
        getForecast()
    }
    val temperature = async {
        getTemperature()
    }

    "${forecast.await()} ${temperature.await()}"
}

//정지 함수(delay)는 코루틴이나 다른 정지 함수에서만 호출
suspend fun printForecast() {
    delay(1000)
    println("Sunny")
}

suspend fun printTemperature() {
    delay(1000)
    println("30\u00b0C")
}

suspend fun getForecast(): String {
    delay(1000)
    return "Sunny"
}

suspend fun getTemperature(): String {
    delay(500)
    throw AssertionError("Temperature is invalid")
    return "30\u00b0C"
}