package lambda;

// interface 선언
// new 를 직접적으로 할 수 없음

// 1) 구현 클래스 생성 필요
// 2) 익명 구현 클래스 사용 필요

@FunctionalInterface // 다른 클래스에서 부르는 함수형 인터페이스는 반드시 하나의 추상 메서드만 있어야함 -> 일반 메소드가 2개이상 들어오는 걸 컴파일 단계에서 체크
interface MyFunctionalInterface1 {
    void method();

    static void print() {
    }

    default void print1() {
    }
    // static, default 메소드는 가능
}

public class LambdaEx2 {
    public static void main(String[] args) {
        // MyFunctionalInterface1 obj = new MyFunctionalInterface1() {

        // @Override
        // public void method() {
        // System.out.println("인터페이스 구현");
        // }
        // };
        // obj.method();

        // 익명구현객체를 식으로 작성 : 람다식
        MyFunctionalInterface1 obj = () -> System.out.println("인터페이스 구현");
        obj.method();

        obj = () -> {
            int i = 10;
            System.out.println(i * i);
        };
        MyFunctionalInterface1.print();
        obj.print1();
    }
}
