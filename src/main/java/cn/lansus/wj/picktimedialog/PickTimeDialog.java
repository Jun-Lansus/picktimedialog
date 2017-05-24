package cn.lansus.wj.picktimedialog;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.lansus.wj.picktimedialog.listener.IPickTimeDialogListener;
import cn.lansus.wj.picktimedialog.model.VehicleTraceTime;


/**
 * 选择时间的弹出框
 *   1> 封装时间范围的限制
 *
 * @author 王俊
 */
public class PickTimeDialog implements DialogInterface.OnKeyListener, View.OnClickListener {


    AlertDialog canDialog;
    AlertDialog.Builder canDialogBuilder;
    private Calendar maxCalender=Calendar.getInstance();
    private Calendar minCalender;
    private VehicleTraceTime initCalender;
    private Calendar startCanlender;
    private Calendar endCanlender;
    private boolean isCancel=true;
    private TextView tvDialogPickTimeStart;
    private TextView tvDialogPickTimeEnd;
    SimpleDateFormat format;
    TimePickerView.Type dialogType= TimePickerView.Type.ALL;
    public PickTimeDialog setOnKeyListener(PickTimeDialog.onKeyListener onKeyListener) {
        this.onKeyListener = onKeyListener;
        return  this;
    }

    private onKeyListener onKeyListener;
    public PickTimeDialog setType(TimePickerView.Type type) {
        this.dialogType = type;
        format=getFormate(dialogType);
        return this;
    }

    private SimpleDateFormat getFormate(TimePickerView.Type dialogType) {
        String formateStr="yyyy-MM-dd  HH:mm:ss";
        if(dialogType== TimePickerView.Type.ALL){
            formateStr="yyyy-MM-dd  HH:mm:ss";
        }
        else
           if(dialogType== TimePickerView.Type.YEAR_MONTH_DAY){
            formateStr="yyyy-MM-dd";
        }
        else
           if(dialogType== TimePickerView.Type.HOURS_MINS){
            formateStr="HH:mm";
        }
        else
           if(dialogType== TimePickerView.Type.MONTH_DAY_HOUR_MIN){
            formateStr="MM-dd  HH:mm";
        }
        else
           if(dialogType== TimePickerView.Type.YEAR_MONTH){
            formateStr="yyyy-MM";
        }   else
           if(dialogType== TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN){
            formateStr="yyyy-MM-dd  HH:mm";
        }
        return new SimpleDateFormat(formateStr, Locale.CHINA);

    }

    /**
     * 设置最大时间范围
     */
    public PickTimeDialog  setMaxTime(Calendar calendar){
        maxCalender=calendar;
        return  this;

    }
  private int duration=-1;
    /**
     * 初始化时间
     */
    public  PickTimeDialog initCalender(VehicleTraceTime calendar){
        initCalender=calendar;
        return  this;
    }
    /**
     * 设置最小日期
     *
     */
    public PickTimeDialog  setMinTime(Calendar calendar){
        minCalender=calendar;
        return this;

    }
    public PickTimeDialog(Activity mContext) {
        this.mContext = mContext;
        format=getFormate(dialogType);
    }
    private Activity mContext;
    private VehicleTraceTime time;
    public PickTimeDialog setOnPickTimeDialogListener(IPickTimeDialogListener iPickeTimeDialogListener) {
        this.iPickTimeDialogListener = iPickeTimeDialogListener;
        return  this;
    }

    private IPickTimeDialogListener iPickTimeDialogListener;

    public  PickTimeDialog setCancel(boolean isCancel){
      this.isCancel=isCancel;

        return this;
    }
    /**
     * 初始化界面
     *
     * @return boolean  初始化是否成功
     */
    public PickTimeDialog create() {
        View  view = LayoutInflater.from(mContext).inflate(R.layout.view_dialog_pick_time, null);
         canDialogBuilder = new AlertDialog.Builder(mContext);
        canDialog=canDialogBuilder.create();
     tvDialogPickTimeStart= (TextView) view.findViewById(R.id.tv_dialog_pick_time_start);
     tvDialogPickTimeEnd= (TextView) view.findViewById(R.id.tv_dialog_pick_time_end);
        view.findViewById(R.id.rl_dialog_pick_time_start).setOnClickListener(this);
        view.findViewById(R.id.rl_dialog_pick_time_end).setOnClickListener(this);
        view.findViewById(R.id.btn_dialog_pick_time_confirm).setOnClickListener(this);
        //todo  根据timetype做类型
        if(initCalender==null){
            initCalender=new VehicleTraceTime(Calendar.getInstance(),Calendar.getInstance());
        }
        startCanlender=initCalender.getStartTime();
        endCanlender=initCalender.getEndTime();
        tvDialogPickTimeStart.setText(format.format(initCalender.getStartTime().getTime()));
        tvDialogPickTimeEnd.setText(format.format(initCalender.getEndTime().getTime()));
        //        canDialog=dialog.setCircularRevealAnimator(CanDialog.CircularRevealStatus.TOP_LEFT).show();

        canDialog.setView(view);
        canDialog.show();
        canDialog.setCancelable(isCancel);
         canDialog.setOnKeyListener(this);
        return this;
    }





    public void onClick(View view) {


        int i = view.getId();
        if (i == R.id.rl_dialog_pick_time_start) {
            new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {//选中事件回调
                    startCanlender = Calendar.getInstance();
                    startCanlender.setTime(date);
                    initCalender.setStartTime(startCanlender);
                    tvDialogPickTimeStart.setText(format.format(startCanlender.getTime()));
                    setViable(true);
                }

                @Override
                public void onCaneled() {
                    setViable(true);
                }
            })
                    .setType(dialogType)//default is all
                    .setCancelText("取消")
                    .setSubmitText("确认")
                    .setContentSize(16)
                    .setTitleSize(18)
                    .setTitleText("请选择时间")
                    .setOutSideCancelable(false)// default is true
                    //  .isCyclic(true)// default is false
                    .setTitleColor(Color.BLUE)
                    //.setDate(new Date())// default system*/
               /*.setDividerColor(Color.WHITE)//设置分割线的颜色
                .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
                .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
                .setSubmitColor(Color.WHITE)
                .setCancelColor(Color.WHITE)*/
               /* .gravity(Gravity.RIGHT)// default is center*/
                    .setContentSize(17)
                    .setLabel("年", "月", "日", ":", ":", "") //设置空字符串以隐藏单位提示   hide label
                    .setDate(initCalender.getStartTime())
                    .setRangDate(minCalender, maxCalender)
                    .build().show();
            setViable(false);


        } else if (i == R.id.rl_dialog_pick_time_end) {
            if (startCanlender == null) {
                ToastUtils.showShort(mContext, "请先选择开始时间");
                return;
            }
            Calendar maxCa = maxCalender;
            Calendar minCa = startCanlender;
            if (duration != -1) {
                maxCa = startCanlender;
                maxCa.add(Calendar.DAY_OF_MONTH, 1);
                minCa.add(Calendar
                        .DAY_OF_MONTH, -1);
            } else {
                maxCa = Calendar.getInstance();
            }
            new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {//选中事件回调
                    endCanlender = Calendar.getInstance();
                    endCanlender.setTime(date);

                    tvDialogPickTimeEnd.setText(format.format(endCanlender.getTime()));
                    setViable(true);
                }

                @Override
                public void onCaneled() {
                    setViable(true);
                }
            })
                    .setType(dialogType)//default is all
                    .setCancelText("取消")
                    .setSubmitText("确认")
                    .setContentSize(16)
                    .setTitleSize(18)
                    .setTitleText("请选择时间")
                    .setOutSideCancelable(false)// default is true
                    //  .isCyclic(true)// default is false
                    .setTitleColor(Color.BLUE)
                    //.setDate(new Date())// default system*/
               /*.setDividerColor(Color.WHITE)//设置分割线的颜色
                .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
                .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
                .setSubmitColor(Color.WHITE)
                .setCancelColor(Color.WHITE)*/
               /* .gravity(Gravity.RIGHT)// default is center*/
                    .setContentSize(20)
                    .setLabel("年", "月", "日", ":", ":", "") //设置空字符串以隐藏单位提示   hide label
                    .setDate(initCalender.getStartTime())
                    .setRangDate(minCa, maxCa)
                    .build().show();
            setViable(false);


        } else if (i == R.id.btn_dialog_pick_time_confirm) {
            if (startCanlender == null || endCanlender == null) {
                ToastUtils.showShort(mContext, "请先选择时间区间");
                return;
            }

            if (startCanlender.after(endCanlender)) {
                ToastUtils.showShort(mContext, "结束时间不能小于开始时间");
                return;
            }

            if (endCanlender.after(Calendar.getInstance())) {
                ToastUtils.showShort(mContext, "结束时间不能超过当前时间");
                return;
            }
            if (duration != -1 && daysBetween(startCanlender, endCanlender) > duration - 1) {
                ToastUtils.show(mContext, "暂时只支持一天内轨迹查询，请重新选择时间区间");
                return;
            }
            if (startCanlender.compareTo(endCanlender) == 0) {
                ToastUtils.show(mContext, "不能选择相同时间，请重新选择");

                return;
            }
            time = new VehicleTraceTime(startCanlender, endCanlender);
            if (iPickTimeDialogListener != null) {
                iPickTimeDialogListener.onSelected(time);
                dismiss();
            }


        }

    }
  public void dismiss(){
      if(canDialog!=null)
          canDialog.dismiss();
  }
  public void setViable(boolean isshow){
      if(canDialog!=null)
          if(isshow)
          canDialog.show();
      else
              canDialog.hide();
  }



    public PickTimeDialog cantlable() {
        canDialog.setCancelable(false);
        return this;
    }

    public  PickTimeDialog setDuration(int days ){

        duration=days;
        return this;
    }
    public static int daysBetween(Calendar ca1,Calendar ca2)
    {

        long time1 = ca1.getTimeInMillis();
        long time2 = ca2.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if(onKeyListener!=null){

            onKeyListener.run(dialog,keyCode,event);


        }


        return false;
    }

    public interface  onKeyListener{

        void  run(DialogInterface dialog, int keyCode, KeyEvent event);
    }
}
