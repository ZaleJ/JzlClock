package com.jzlclock.jzl.jzlclock;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/4/20 0020.
 */
public class AlarmView extends LinearLayout{

    public AlarmView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    public AlarmView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public AlarmView(Context context){
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        btnAddAlarm = (Button) findViewById(R.id.btnAddAlarm);
        lvAlarmList = (ListView) findViewById(R.id.lvAlarmList);

        adapter = new ArrayAdapter<AlarmData>(getContext(), android.R.layout.simple_list_item_1);
        lvAlarmList.setAdapter(adapter);

        readSaveAlarmList();     // 读取已经保存好的闹钟数据

        //adapter.add(new AlarmData(System.currentTimeMillis()));

        btnAddAlarm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addAlarm();
            }
        });
    }

    private void addAlarm(){
        //TODO
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                Calendar currentTime = Calendar.getInstance();
                if (calendar.getTimeInMillis() <= currentTime.getTimeInMillis()) {
                    calendar.setTimeInMillis(calendar.getTimeInMillis()+24*60*60*1000);
                }

                adapter.add(new AlarmData(calendar.getTimeInMillis()));
                saveAlarmList();     // 保存新建闹钟数据
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
    }
    private void saveAlarmList(){
        SharedPreferences.Editor editor = getContext().getSharedPreferences(AlarmView.class.getName(), Context.MODE_PRIVATE).edit();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < adapter.getCount(); i++) {
            sb.append(adapter.getItem(i).getTime()).append(","); //传入数据、时间， 多个中间用逗号隔开
        }

        String content = sb.toString().substring(0, sb.length()-1);

        editor.putString(KEY_ALARM_LIST, content);     // 输出除最后一个类型数据

        System.out.println(content);

        editor.commit();  // 提交数据
    }

    private void readSaveAlarmList() {
        SharedPreferences sp = getContext().getSharedPreferences(AlarmView.class.getName(), Context.MODE_PRIVATE);
        String content = sp.getString(KEY_ALARM_LIST, null);

        if (content!=null) {
            String[] timeStrings = content.split(",");
            for (String string : timeStrings) {
                adapter.add(new AlarmData(Long.parseLong(string)));
            }
        }
    }

    private Button btnAddAlarm;
    private ListView lvAlarmList;
    private static final String KEY_ALARM_LIST = "alarmList";
    private ArrayAdapter<AlarmData> adapter;

    private static class AlarmData{
        public AlarmData(long time){
            this.time = time;

            date = Calendar.getInstance();
            date.setTimeInMillis(time);
            timeLabel = String.format("%d月%d日 %d:%d",
                    date.get(Calendar.MONTH)+1,
                    date.get(Calendar.DAY_OF_MONTH),
                    date.get(Calendar.HOUR_OF_DAY),
                    date.get(Calendar.MINUTE));
        }

        public long getTime() {
            return time;
        }

        public String getTimeLabel() {
            return timeLabel;
        }

        @Override
        public String toString() {
            return getTimeLabel();
        }

        private String timeLabel = "";
        private long time = 0;
        private Calendar date;
    }
}
