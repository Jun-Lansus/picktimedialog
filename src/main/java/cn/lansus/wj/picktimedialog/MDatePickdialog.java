package cn.lansus.wj.picktimedialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;


import java.util.Calendar;

import cn.lansus.wj.picktimedialog.listener.IPickTimeDialogListener;


/**
 * Created by lansu on 2017/2/21.
 */

public class MDatePickdialog {

    private Calendar mCalender=Calendar.getInstance();
    private Calendar maxCalender=Calendar.getInstance();
    private Calendar minCalender;
    private Calendar initCalender=Calendar.getInstance();

    public String getType() {
        return type;
    }

    public MDatePickdialog setType(String type) {
        this.type = type;
        return  this;
    }

    private String type=null;   //控制是否需要顺带选择时间

    /**
     * 设置最大时间范围
     * @param calendar
     */
    public MDatePickdialog  setMaxTime(Calendar calendar){
        maxCalender=calendar;
        return  this;

    }

    /**
     * 初始化时间
     */
    public  MDatePickdialog initCalender(Calendar calendar){
        initCalender=calendar;
        return  this;
    }
    /**
     * 设置最小日期
     *
     */
    public MDatePickdialog  setMinTime(Calendar calendar){
        minCalender=calendar;
        return this;

    }
    public MDatePickdialog  Builder(final Context mContext, final IPickTimeDialogListener pickTimeDialogListener){
        DatePickerDialog date = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, final int month, int dayOfMonth) {
                mCalender.set(year,month,dayOfMonth);
                if(type==null) {
                    final TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            mCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            mCalender.set(Calendar.MINUTE, minute);
                            pickTimeDialogListener.onSelected(mCalender);
                        }
                    }, initCalender.get(Calendar.HOUR), initCalender.get(Calendar.MINUTE), true);
                    timePickerDialog.show();
                }else {
                    pickTimeDialogListener.onSelected(mCalender);
                }

            }
        },
                initCalender.get(Calendar.YEAR), initCalender.get(Calendar.MONTH),
                initCalender.get(Calendar.DAY_OF_MONTH));

        maxCalender.set(Calendar.HOUR_OF_DAY, maxCalender.getMaximum(Calendar.HOUR_OF_DAY));
        maxCalender.set(Calendar.MINUTE, maxCalender.getMaximum(Calendar.MINUTE));
        maxCalender.set(Calendar.SECOND, maxCalender.getMaximum(Calendar.SECOND));
        maxCalender.set(Calendar.MILLISECOND, maxCalender.getMaximum(Calendar.MILLISECOND));
        date.getDatePicker().setMaxDate(maxCalender.getTimeInMillis());
        if(minCalender!=null) {
            minCalender.set(Calendar.HOUR_OF_DAY, minCalender.getMinimum(Calendar.HOUR_OF_DAY));
            minCalender.set(Calendar.MINUTE, minCalender.getMinimum(Calendar.MINUTE));
            minCalender.set(Calendar.SECOND, minCalender.getMinimum(Calendar.SECOND));
            minCalender.set(Calendar.MILLISECOND, minCalender.getMinimum(Calendar.MILLISECOND));
            date.getDatePicker().setMinDate(minCalender.getTimeInMillis());
        }
        date.show();
        return  this;

    }

}
