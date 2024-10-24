package ch1;

public class BritzSpeaker implements Speaker {

    @Override
    public void volumeUp() {
        System.out.println("BritzSpeaker 볼륨 Up");
    }

    @Override
    public void volumeDown() {
        System.out.println("BritzSpeaker 볼륨 Down");
    }

}
