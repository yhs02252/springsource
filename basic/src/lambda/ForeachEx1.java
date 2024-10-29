package lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ForeachEx1 {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("사과", "딸기", "수박", "바나나", "배", "메론");

        for (String s : list) {
            System.out.println(s);
        }

        // forEach
        // <? super String> : String 혹은 그 조상타입
        // Consumer<String> c = (x) -> System.out.println(x);
        // c.accept
        // list.forEach(Consumer<? super String> action)

        // 하나씩 꺼내서 출력하는 용도
        list.forEach((x) -> System.out.println(x)); // <-- ()안쪽은 List에 선언된 것과 같은 타입
    }
}
