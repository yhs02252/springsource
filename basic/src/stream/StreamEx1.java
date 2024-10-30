package stream;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class StreamEx1 {
    public static void main(String[] args) {
        List<String> strList = Arrays.asList("사과", "딸기", "수박", "바나나", "배", "메론");
        String[] strArr = { "비행기", "자동차", "배", "자전거", "버스" };

        // System.out.println("정렬 전" + Arrays.toString(strArr));
        // Arrays.sort(strArr); // 원본 자체가 정렬
        // System.out.println("정렬 후" + Arrays.toString(strArr));

        // // 컬렉션(List, Set) 정렬
        // System.out.println("정렬 전" + strList);
        Collections.sort(strList);
        // System.out.println("정렬 후" + strList);

        // 원본이 아닌 복사본 정렬 => 스트림 처리
        // .stream() <= 스트림으로 변환
        Stream<String> stream = Arrays.stream(strArr);
        Stream<String> stream2 = strList.stream();

        // 정렬 후
        stream.sorted().forEach(System.out::print);
        System.out.println();
        stream2.sorted().forEach(System.out::print);

        System.out.println();
        // 원본
        System.out.println("원본" + Arrays.toString(strArr));
        System.out.println("원본" + strList);
    }
}
