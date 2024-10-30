package lambda;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ForeachEx2 {
    public static void main(String[] args) {
        List<Student> list = Arrays.asList(new Student("홍길동", 90, 96),
                new Student("김수정", 80, 85));

        for (Student student : list) {
            System.out.println(student.getName() + ": " + student.getEng() + "," + student.getMath());
        }

        list.forEach((student) -> {
            System.out.println(student.getName() + ": " + student.getEng() + "," + student.getMath());
        });

        Map<String, Integer> items = new HashMap<>();
        items.put("A", 10);
        items.put("B", 20);
        items.put("C", 30);
        items.put("D", 40);
        items.put("E", 50);

        System.out.println(items.get("A"));

        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
        }

        // List, Set, Map
        // List : 데이터 중복 가능, 차례대로 접근 => 향상된 for 가능
        // Set : 데이터 중복 불가, 순서 의미 없음 => 향상된 for 가능
        // Map : Key 값만 중복 안됨(Value 중복 가능)

        // 각 자료구조에 대해 접근하는 방법에 대한 통일성 부여 ==> Iterator
        // Set
        Set<String> keySet = items.keySet();
        Iterator<String> keyIterator = keySet.iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            Integer value = items.get(key);
            System.out.println("Item : " + key + " Count : " + value);
        }

        // .forEach 로 변환
        items.forEach((k, v) -> {
            System.out.println("Item : " + k + " Count : " + v);
        });
    }
}
