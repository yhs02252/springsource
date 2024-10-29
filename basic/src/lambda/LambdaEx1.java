package lambda;

// 람다식 : 메서드를 하나의 식으로 표현
// 1) 리턴타입(int) 메소드명(max) 제거
// (int a, int b) -> {return a > b ? a : b;}
// 2) return 구문 생략가능 (실행할 구문이 하나인 경우 / {} 생략)
// (int a, int b) -> a > b ? a : b;
// 3) 매개변수가 추측이 가능한 상태라면 타입 생략 가능
// (a, b) -> a > b ? a : b;
// ※일반 클래스는 불가능

// square : (x) -> x * x;

// roll : () -> (int) (Math.random() * 6);

// sumArr (int[] arr) -> {안쪽 내용 동일 (여러줄일경우)}

class Lambda1 {
    int max(int a, int b) {
        return a > b ? a : b;
    }

    int square(int x) {
        return x * x;
    }

    int roll() {
        return (int) (Math.random() * 6);
    }

    int sumArr(int[] arr) {
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        return sum;
    }
}

public class LambdaEx1 {
    public static void main(String[] args) {
        Lambda1 lambda1 = new Lambda1();
        System.out.println(lambda1.max(6, 3));
    }

}
