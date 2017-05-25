# picktimedialog
The view of choice  time range,    and you can  do   everything   you wan to do 


个人提供了自己封装的第三方时间选择器，能够自定义时间格式，以及设置时间范围。

##基础使用
``` java
   PickTimeDialog  pickDilog=  new PickTimeDialog(this).setMaxTime(Calendar.getInstance()).setOnPickTimeDialogListener(this).create().cantlable().setOnKeyListener(this);

```
##设置时间格式
 内部封装了6类时间显示格式


``` java
    public enum Type {
        ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR_MIN, YEAR_MONTH, YEAR_MONTH_DAY_HOUR_MIN
    } // 六种选择模式，年月日时分秒，年月日，时分，月日时分，年月，年月日时分
```
使用方法： `PickTimeDialog.setType()`来使用 

##设置最大和最小的时间范围限制
对应的方法：
   

 - `setMaxTime(Calender calender)；`
 - `setMinTime(Calender calender)；`
 - `initCalender(VehicleTraceTime calendar)；`

 ##设置时间的回调以及自定义按键事件：
 - ` setOnKeyListener(PickTimeDialog.onKeyListener onKeyListener)`;
 - `setOnPickTimeDialogListener(IPickTimeDialogListener iPickeTimeDialogListener)；`

效果图如下：
      ![这里写图片描述](http://oh4ubdyvu.bkt.clouddn.com/Screenshot_2017-05-25-09-32.png)




欢迎使用以及指出错误：
github地址：https://github.com/Jun-Lansus/picktimedialog.git

