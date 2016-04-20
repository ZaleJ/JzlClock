package com.jzlclock.jzl.jzlclock;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

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

        adapter.add(new AlarmData(System.currentTimeMillis()));

        btnAddAlarm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addAlarm();
            }
        });
    }

    private void addAlarm(){
        //TODO
    }

    private Button btnAddAlarm;
    private ListView lvAlarmList;
    private ArrayAdapter<AlarmData> adapter;

    private static class AlarmData{
        public AlarmData(long time){
            this.time = time;

            date = new Date(time);
            timeLabel = date.getHours() + ":" + date.getMinutes();
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
        private Date date;
    }
}
