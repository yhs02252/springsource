package ch1;

/* 변수
 * 1) 멤버변수 : 클래스 안에 선언한 변수
 *               객체인 경우 null로 초기화 됨
 *               기본 타입 변수인 경우 0 or '' or 0.0 or false 등 으로 초기화 됨
 * 2) 지역변수 : 블럭{} 안쪽에 선언된 변수
 * 3) 매개변수 : 호출될 변수 - 예) 메서드 안쪽(a) 변수 a
 * 
 */

public class LgTV implements Tv {

    // 멤버변수(== 인스턴스 변수, 필드, 특성, 속성)
    private Speaker speaker;

    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker; // 예) ((LgTV) tv).setSpeaker(new SonySpeaker());
        // => this.speaker = new SonySpeaker 와 같아짐
    }

    @Override
    public void powerOn() {
        System.out.println("LgTV - 전원 On");
    }

    @Override
    public void powerOff() {
        System.out.println("LgTV - 전원 Off");
    }

    @Override
    public void volumeUp() {
        // System.out.println("LgTV - 볼륨 Up");
        speaker.volumeUp();

        // => SonySpeaker sonySpeaker = new SonySpeaker
        // -> sonySpeaker.volumeup 과 같은 효과
    }

    @Override
    public void volumeDown() {
        // System.out.println("LgTV - 볼륨 Down");
        speaker.volumeDown();
    }

}
