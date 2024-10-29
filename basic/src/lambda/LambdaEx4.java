package lambda;

import java.time.LocalDateTime;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

// 함수형 인터페이스(@FunctionalInterface)
// 메서드는 프로토타입이 거의 일정하기 때문에 자주쓰이는 형식의 메소드를 가지고 있는 함수형 인터페이스 선언 가능 => java.util.function 패키지에 정의함

public class LambdaEx4 {
    public static void main(String[] args) {
        // Supplier 매개값(객체)만을 가지고 따로 실행 시켜줘야함 (void result값은 return 불가)

        Supplier<Integer> s = () -> (int) (Math.random() * 100) + 1; // Supplier Interface
        System.out.println("1 ~ 100 사이의 임의의 수 : " + s.get());

        // System.out.println(LocalDateTime.now());

        Supplier<LocalDateTime> s1 = () -> LocalDateTime.now();
        System.out.println(s1.get());

        Supplier<String> str1 = () -> "안녕하세요";
        System.out.println(str1);

        // Consumer - 매개값을 가진다면 본인이 실행(본인이 Method)
        Consumer<Integer> c = (i) -> System.out.println(i * i);
        c.accept(5);

        // String => 화면 출력
        Consumer<String> c1 = (str) -> System.out.println(str);
        c1.accept("안녕하세요");

        // 오늘날짜 / 시간 출력
        Consumer<LocalDateTime> c2 = (localdatetime) -> System.out.println(localdatetime);
        c2.accept(LocalDateTime.now());

        // Function : 앞 (T)타입으로 값을 받아서 뒤 (t)타입으로 return
        Function<Integer, Integer> f1 = (i) -> i * i;
        System.out.println(f1.apply(45));

        // 일의 자리 없앤 후 리턴
        f1 = (i) -> i / 10 * 10;
        System.out.println(f1.apply(73));

        // Function<String, Integer> f2 = (i) -> Integer.parseInt(i)
        // Function<String, Integer> f2 = (i) -> Integer.valueOf(i);
        Function<String, Integer> f2 = Integer::parseInt;
        System.out.println(f2.apply("50"));

        // Predicate : T(타입) 객체로 받고, boolean 으로 리턴
        Predicate<Integer> p1 = (i) -> i > 10;
        System.out.println(p1.test(75) ? "10보다 큼" : "10보다 작음");

        // 문자열 길이가 6자리보다 크냐?
        Predicate<String> p2 = (sstr) -> sstr.length() > 6;
        System.out.println(p2.test("abcdefg") ? "문자열의 6자리 초과" : "문자열의 6자리 미만");

        // 문자열에 대문자 A의 포함 여부
        p2 = (ssstr) -> ssstr.contains("A");
        System.out.println(p2.test("abAcdefg") ? "A가 포함됨" : "A가 포함되지 않음");

        // BiFunction : 타입(인자) 2개를 받고 1개를 리턴
        BiFunction<Integer, Integer, Integer> function = (x, y) -> x + y;
        System.out.println(function.apply(5, 3));

        function = (x, y) -> x > y ? x : y;
        System.out.println(function.apply(5, 3));

        // 받은 x y 을 리턴해서 연결
        // BiFunction<String, String, String> function1 = (s3, s4) -> s3 + s4;
        BiFunction<String, String, String> function1 = (s3, s4) -> s3.concat(s4);
        System.out.println(function1.apply("java8", "람다"));

        BiFunction<Integer, Integer, String> function2 = (f7, f8) -> String.valueOf(f7) + String.valueOf(f8);
        String numSum = function2.apply(15, 35);
        System.out.println(numSum);

    }
}
