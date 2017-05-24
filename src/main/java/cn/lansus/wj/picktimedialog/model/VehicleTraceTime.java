package cn.lansus.wj.picktimedialog.model;

import java.util.Calendar;

/**
 * Created by lansus on 2017/2/13.
 *
 *
 */

public class VehicleTraceTime {
    private Calendar startTime;

    public VehicleTraceTime(Calendar startTime, Calendar endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private Calendar endTime;

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }
}
