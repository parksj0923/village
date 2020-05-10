package ver0.village.Alarm;

import android.graphics.drawable.Drawable;

public class AlarmItem {
    private String alarm_title;
    private String alarm_text;
    private int alarm_time;
    private Drawable img_alarm;

    public Drawable getImg_alarm() {
        return img_alarm;
    }

    public int getAlarm_time() {
        return alarm_time;
    }

    public String getAlarm_text() {
        return alarm_text;
    }

    public String getAlarm_title() {
        return alarm_title;
    }

    public void setAlarm_text(String alarm_text) {
        this.alarm_text = alarm_text;
    }

    public void setAlarm_time(int alarm_time) {
        this.alarm_time = alarm_time;
    }

    public void setAlarm_title(String alarm_title) {
        this.alarm_title = alarm_title;
    }

    public void setImg_alarm(Drawable img_alarm) {
        this.img_alarm = img_alarm;
    }
}
