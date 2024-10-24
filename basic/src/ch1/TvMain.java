package ch1;

public class TvMain {
    public static void main(String[] args) {
        // LgTv 사용
        // LgTV lgTV = new LgTV();

        // lgTV.setBritzSpeaker(new BritzSpeaker());

        // lgTV.powerOn();
        // lgTV.powerOff();
        // lgTV.volumeUp();
        // lgTV.volumeDown();

        // SamsungTV samsungTV = new SamsungTV();
        // samsungTV.setBritzSpeaker(new BritzSpeaker());
        // samsungTV.powerOn();
        // samsungTV.volumeUp();
        // samsungTV.volumeDown();
        // samsungTV.powerOff();

        Tv tv = new SamsungTV();

        // ((SamsungTV) tv).setBritzSpeaker(new SonySpeaker());

        tv = new LgTV();
        ((LgTV) tv).setSpeaker(new SonySpeaker());

        tv.powerOn();
        tv.volumeUp();
        tv.volumeDown();
        tv.powerOff();
    }
}
