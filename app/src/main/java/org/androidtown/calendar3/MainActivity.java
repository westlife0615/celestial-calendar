package org.androidtown.calendar3;

import java.util.ArrayList;
import java.util.Calendar;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static java.util.Calendar.MAY;
import static java.util.Calendar.getInstance;

public class MainActivity extends Activity implements OnItemClickListener, OnClickListener

        //android.widget.AdapterView.OnItemClickListener
        //android.view.View.OnCLickListener
{
    public static int SUNDAY = 1;
    public static int MONDAY = 2;
    public static int TUESDAY = 3;
    public static int WEDNSESDAY = 4;
    public static int THURSDAY = 5;
    public static int FRIDAY = 6;
    public static int SATURDAY = 7;


    private int num;

    private ImageView mTvCalendarTitle; // 액티비티 상단에 해당하는 月을 표시합니다.
                                            // 예를 들어, january , february .. december 처럼 표시합니다.

    private GridView mGvCalendar;           // 해당 日을 표시합니다.

    private ArrayList<DayInfo> mDayList;
    private Adapter mCalendarAdapter;
    private TextView time;

    Calendar mLastMonthCalendar;
    Calendar mThisMonthCalendar;
    Calendar mNextMonthCalendar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        ImageView bLastMonth = (ImageView) findViewById(R.id.gv_calendar_activity_b_last);
        ImageView bNextMonth = (ImageView) findViewById(R.id.gv_calendar_activity_b_next);
/*      前月(before) 과 後月(after) 로 월정보를 넘기는 방향키입니다. */

        mTvCalendarTitle = (ImageView) findViewById(R.id.gv_calendar_activity_tv_title);
                    // 해당 월정보를 표시하는 이미지뷰 , 액티비티 상단에 위치합니다.

        mGvCalendar = (GridView) findViewById(R.id.gv_calendar_activity_gv_calendar);
      /*       1일부터 말일을 표시하는 gridView입니다. */


        time = (TextView) findViewById(R.id.time);


        bLastMonth.setOnClickListener(this);
        bNextMonth.setOnClickListener(this);            // 달력을 넘기는 방향키입니다.


        mGvCalendar.setOnItemClickListener(this);

        //View를 상속하는 ImageView는 OnClickListener를 통해 클릭 이벤트를 등록
        //AdapterView를 상속하는 GridView는 OnItemClickListener를 등록한다.

        mDayList = new ArrayList<DayInfo>();
        //mDayList라는 라는 ArrayList 참조 변수에
        //DayInfo 객체의 집합을 대입한다.


        mThisMonthCalendar = getInstance();
        //이번달의 인스턴스 생성 , Calendar는 추상클래스로 생성자가 존재하지 않기에 getInstance()메소드 사용
        //System.out.println(mThisMonthCalendar.get(DAY_OF_MONTH)); --> 오늘 날짜 출력


        mThisMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        /*
        mThisMonthCalendar 는 이번달 1일을 가리키는 달력객체입니다.
        * */


        getCalendar(mThisMonthCalendar);
        //오늘의 요일=dayofweek // 이달 마지막 일=thisMonthLastDay // 지난달 마지막일=lastMonthStartDay

    }

    @Override
    protected void onResume()               //paused 상태의 액티비티가 focus를 획득시에 호출되는 메소드입니다. 
    {
        super.onResume();


        /*mThisMonthCalendar = getInstance();
        //이번달의 인스턴스 생성 , Calendar는 추상클래스로 생성자가 존재하지 않기에 getInstance()메소드 사용
        //System.out.println(mThisMonthCalendar.get(DAY_OF_MONTH)); --> 오늘 날짜 출력


        mThisMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);
                                            //void set (int field,int value)
                                            //value is set to the field.
                                            //System.out.println(mThisMonthCalendar.get(DAY_OF_MONTH));
                                            //= 1    get()은 set()이 선행되어야만 사용할 수 있다.
                                            //오늘 날짜는 1일로 강제 변환
        getCalendar(mThisMonthCalendar);
        //오늘의 요일=dayofweek // 이달 마지막 일=thisMonthLastDay // 지난달 마지막일=lastMonthStartDay
*/
    }

    /**
     * 달력을 셋팅한다.
     *
     * @param calendar 달력에 보여지는 이번달의 Calendar 객체
     */
    private void getCalendar(Calendar calendar) {


        int lastMonthStartDay;
        int dayOfWeek;
        int thisMonthLastDay;           //이번달의 마지막 날짜

        TextView bottom = (TextView) findViewById(R.id.bottom);

        mDayList.clear();                           //private ArrayList<DayInfo> mDayList 내부를 비운다.

        // 이번달 시작일의 요일을 구한다. 시작일이 일요일인 경우 인덱스를
        // 1(일요일)에서 8(다음주 일요일)로 바꾼다.)

        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // dayOfWeek 은 이번달 1일의 요일을 가리킵니다.

        thisMonthLastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);            //이번달의 마지막 날짜

        //calendar : 올해 그리고 이달의 1일 을 뜻하는 시간객체
        //이 달의 마지막 날짜를 thisMonthLastDay에 대입

        calendar.add(Calendar.MONTH, -1);
        //void add (int field,int amount)
        //올해 지난 달의 1일
        //calendar의 상태 : 올해 지난달의 1일

        Log.e("지난달 마지막일", calendar.get(Calendar.DAY_OF_MONTH) + "");
        //int e (String tag,String msg) 에러 메시지는 전달한다.
        //Log 클래스의 static int e ();


        lastMonthStartDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 지난달의 마지막 일자를 구한다.


        calendar.add(Calendar.MONTH, 1);            //올해 이번 달, 1일
        Log.e("이번달 시작일", calendar.get(Calendar.DAY_OF_MONTH) + "");


            // 이번달 1일에 해당하는 요일
        if (dayOfWeek == SUNDAY)                     //if firstday of this month is sunday,
        {
            dayOfWeek += 7;                 //월2, 화3 ... 일8
        }

        lastMonthStartDay -= (dayOfWeek - 1) - 1;           // 목요일은 5이며, 2를 빼면 3이 된다.
                                                            // 화요일은 3이며, 2를 뺴면 1이 된다.
        //지난달 마지막 일의 요일

        Calendar today = getInstance();
        // 캘린더 타이틀(년월 표시)을 세팅한다.


        time.setText(today.get(Calendar.YEAR) + "."
                + (today.get(Calendar.MONTH) + 1) + "."
                + today.get(Calendar.DAY_OF_MONTH));

        //calendar : 지난 달 1일
        switch (calendar.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                mTvCalendarTitle.setImageResource(R.drawable.jan);
                bottom.setText("1,3,4,6,10,12,19,20,26,31日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.FEBRUARY:
                mTvCalendarTitle.setImageResource(R.drawable.feb);
                bottom.setText("1,4,6,11,15,19,26日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.MARCH:
                mTvCalendarTitle.setImageResource(R.drawable.mar);
                bottom.setText("1,5,10,11,12,15,20日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.APRIL:
                mTvCalendarTitle.setImageResource(R.drawable.apr);
                bottom.setText("1,4,8,11,17,19,22,26,30日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.MAY:
                mTvCalendarTitle.setImageResource(R.drawable.may);
                bottom.setText("3,4,6,9,11,18,19,26,31日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.JUNE:
                mTvCalendarTitle.setImageResource(R.drawable.jun);
                bottom.setText("1,3,5,9,15,17,24日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.JULY:
                mTvCalendarTitle.setImageResource(R.drawable.jul);
                bottom.setText("1,7,9,17,20,23,30,31日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.AUGUST:
                mTvCalendarTitle.setImageResource(R.drawable.aug);
                bottom.setText("7,8,13,15,22,29,30日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.SEPTEMBER:
                mTvCalendarTitle.setImageResource(R.drawable.sep);
                bottom.setText("5,6,7,12,13,17,18,20,23,27日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.OCTOBER:
                mTvCalendarTitle.setImageResource(R.drawable.oct);
                bottom.setText("6,8,10,12,18,20,21,23,24,28日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.NOVEMBER:
                mTvCalendarTitle.setImageResource(R.drawable.nov);
                bottom.setText("4,7,11,12,18,22,24,27日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.DECEMBER:
                mTvCalendarTitle.setImageResource(R.drawable.dec);
                bottom.setText("4,7,9,14,18,21,23,26日");
                bottom.setTextColor(Color.WHITE);
                break;
        }


        DayInfo day;

                                //dayOfWeek 이번달 시작일의 요일
        Log.e("dayOfWeek", dayOfWeek + "");





        for (int i = 0; i < dayOfWeek - 1; i++)                //이달 1일이 수요일이라면 dayofweek =4이고
        {                                               //for문은 4번 반복한다. date에 1씩 증가한 4개의 값이 대입된다.
            /*int date = lastMonthStartDay+i;*/             //그리고 4개의 date가 day 객체에 정의된다.
            day = new DayInfo();                        //
            day.setDay("");         //
            day.setInMonth(false);                      //

            mDayList.add(day);
        }

                        //thisMonthLastDay  : 이번달의 마지막 날짜
        for (int i = 1; i <= thisMonthLastDay; i++) {
            day = new DayInfo();                        //이 달이 2월 이라면 1부터 28까지의 day 객체가 만들어지고
            day.setDay(Integer.toString(i));            //각 객체는 1~28의 text를 가진다.
            day.setInMonth(true);                       //그리고 28개의 객체들은 mDayList에 저장된다.

            mDayList.add(day);
        }
        for (int i = 1; i < 42 - (thisMonthLastDay + dayOfWeek - 1) + 1; i++) {
            day = new DayInfo();
            day.setDay("");
            day.setInMonth(false);
            mDayList.add(day);
        }


        initCalendarAdapter();
    }

    /**
     * 지난달의 Calendar 객체를 반환합니다.
     *
     * @param calendar
     * @return LastMonthCalendar
     */
    private Calendar getLastMonth(Calendar calendar) {
        TextView bottom = (TextView) findViewById(R.id.bottom);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);


        calendar.add(Calendar.MONTH, -1);       //


        switch (calendar.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                mTvCalendarTitle.setImageResource(R.drawable.jan);
                bottom.setText("1,3,4,6,10,12,19,20,26,31日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.FEBRUARY:
                mTvCalendarTitle.setImageResource(R.drawable.feb);
                bottom.setText("1,4,6,11,15,19,26日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.MARCH:
                mTvCalendarTitle.setImageResource(R.drawable.mar);
                bottom.setText("1,5,10,11,12,15,20日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.APRIL:
                mTvCalendarTitle.setImageResource(R.drawable.apr);
                bottom.setText("1,4,8,11,17,19,22,26,30日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.MAY:
                mTvCalendarTitle.setImageResource(R.drawable.may);
                bottom.setText("3,4,6,9,11,18,19,26,31日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.JUNE:
                mTvCalendarTitle.setImageResource(R.drawable.jun);
                bottom.setText("1,3,5,9,15,17,24日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.JULY:
                mTvCalendarTitle.setImageResource(R.drawable.jul);
                bottom.setText("1,7,9,17,20,23,30,31日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.AUGUST:
                mTvCalendarTitle.setImageResource(R.drawable.aug);
                bottom.setText("7,8,13,15,22,29,30日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.SEPTEMBER:
                mTvCalendarTitle.setImageResource(R.drawable.sep);
                bottom.setText("5,6,7,12,13,17,18,20,23,27日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.OCTOBER:
                mTvCalendarTitle.setImageResource(R.drawable.oct);
                bottom.setText("6,8,10,12,18,20,21,23,24,28日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.NOVEMBER:
                mTvCalendarTitle.setImageResource(R.drawable.nov);
                bottom.setText("4,7,11,12,18,22,24,27日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.DECEMBER:
                mTvCalendarTitle.setImageResource(R.drawable.dec);
                bottom.setText("4,7,9,14,18,21,23,26日");
                bottom.setTextColor(Color.WHITE);
                break;
        }

        return calendar;
    }

    /**
     * 다음달의 Calendar 객체를 반환합니다.
     *
     * @param calendar
     * @return NextMonthCalendar
     */



    private Calendar getNextMonth(Calendar calendar) {
        TextView bottom = (TextView) findViewById(R.id.bottom);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        calendar.add(Calendar.MONTH, +1);
        switch (calendar.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                mTvCalendarTitle.setImageResource(R.drawable.jan);
                bottom.setText("1,3,4,6,10,12,19,20,26,31日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.FEBRUARY:
                mTvCalendarTitle.setImageResource(R.drawable.feb);
                bottom.setText("1,4,6,11,15,19,26日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.MARCH:
                mTvCalendarTitle.setImageResource(R.drawable.mar);
                bottom.setText("1,5,10,11,12,15,20日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.APRIL:
                mTvCalendarTitle.setImageResource(R.drawable.apr);
                bottom.setText("1,4,8,11,17,19,22,26,30日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.MAY:
                mTvCalendarTitle.setImageResource(R.drawable.may);
                bottom.setText("3,4,6,9,11,18,19,26,31日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.JUNE:
                mTvCalendarTitle.setImageResource(R.drawable.jun);
                bottom.setText("1,3,5,9,15,17,24日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.JULY:
                mTvCalendarTitle.setImageResource(R.drawable.jul);
                bottom.setText("1,7,9,17,20,23,30,31日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.AUGUST:
                mTvCalendarTitle.setImageResource(R.drawable.aug);
                bottom.setText("7,8,13,15,22,29,30日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.SEPTEMBER:
                mTvCalendarTitle.setImageResource(R.drawable.sep);
                bottom.setText("5,6,7,12,13,17,18,20,23,27日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.OCTOBER:
                mTvCalendarTitle.setImageResource(R.drawable.oct);
                bottom.setText("6,8,10,12,18,20,21,23,24,28日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.NOVEMBER:
                mTvCalendarTitle.setImageResource(R.drawable.nov);
                bottom.setText("4,7,11,12,18,22,24,27日");
                bottom.setTextColor(Color.WHITE);
                break;
            case Calendar.DECEMBER:
                mTvCalendarTitle.setImageResource(R.drawable.dec);
                bottom.setText("4,7,9,14,18,21,23,26日");
                bottom.setTextColor(Color.WHITE);
                break;
        }

        return calendar;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        num = mThisMonthCalendar.get(Calendar.MONTH);
        if (num == 0) {
            if (position == 7) {
                Intent intent4 = new Intent(MainActivity.this, January1.class);
                startActivity(intent4);
            }
            if (position == 8) {
                Intent intent4 = new Intent(MainActivity.this, January2.class);
                startActivity(intent4);
            }
            if (position == 9) {
                Intent intent4 = new Intent(MainActivity.this, January3.class);
                startActivity(intent4);
            }
            if (position == 10) {
                Intent intent4 = new Intent(MainActivity.this, January4.class);
                startActivity(intent4);
            }
            if (position == 12) {
                Intent intent4 = new Intent(MainActivity.this, January6.class);
                startActivity(intent4);
            }
            if (position == 16) {
                Intent intent4 = new Intent(MainActivity.this, January10.class);
                startActivity(intent4);
            }
            if (position == 18) {
                Intent intent4 = new Intent(MainActivity.this, January12.class);
                startActivity(intent4);
            }
            if (position == 25) {
                Intent intent4 = new Intent(MainActivity.this, January19.class);
                startActivity(intent4);
            }
            if (position == 26) {
                Intent intent4 = new Intent(MainActivity.this, January20.class);
                startActivity(intent4);
            }
            if (position == 32) {
                Intent intent4 = new Intent(MainActivity.this, January26.class);
                startActivity(intent4);
            }
            if (position == 37) {
                Intent intent4 = new Intent(MainActivity.this, January31.class);
                startActivity(intent4);
            }


        }
        if (num == 1) {
            if (position == 3) {
                Intent intent4 = new Intent(MainActivity.this, February1.class);
                startActivity(intent4);
            }
            if (position == 6) {
                Intent intent4 = new Intent(MainActivity.this, February4.class);
                startActivity(intent4);
            }
            if (position == 8) {
                Intent intent4 = new Intent(MainActivity.this, February6.class);
                startActivity(intent4);
            }
            if (position == 13) {
                Intent intent4 = new Intent(MainActivity.this, February11.class);
                startActivity(intent4);
            }
            if (position == 17) {
                Intent intent4 = new Intent(MainActivity.this, February15.class);
                startActivity(intent4);
            }
            if (position == 21) {
                Intent intent4 = new Intent(MainActivity.this, February19.class);
                startActivity(intent4);
            }
            if (position == 28) {
                Intent intent4 = new Intent(MainActivity.this, February26.class);
                startActivity(intent4);
            }


        }
        if (num == 2) {
            if (position == 3) {
                Intent intent4 = new Intent(MainActivity.this, March1.class);
                startActivity(intent4);
            }
            if (position == 7) {
                Intent intent4 = new Intent(MainActivity.this, March5.class);
                startActivity(intent4);
            }
            if (position == 12) {
                Intent intent4 = new Intent(MainActivity.this, March10.class);
                startActivity(intent4);
            }
            if (position == 13) {
                Intent intent4 = new Intent(MainActivity.this, March11.class);
                startActivity(intent4);
            }
            if (position == 17) {
                Intent intent4 = new Intent(MainActivity.this, March15.class);
                startActivity(intent4);
            }
            if (position == 22) {
                Intent intent4 = new Intent(MainActivity.this, March20.class);
                startActivity(intent4);
            }
            if (position == 14) {
                Intent intent4 = new Intent(MainActivity.this, March12.class);
                startActivity(intent4);
            }
        }
        if (num == 3) {
            if (position == 6) {
                Intent intent4 = new Intent(MainActivity.this, April1.class);
                startActivity(intent4);
            }
            if (position == 9) {
                Intent intent4 = new Intent(MainActivity.this, April4.class);
                startActivity(intent4);
            }
            if (position == 13) {
                Intent intent4 = new Intent(MainActivity.this, April8.class);
                startActivity(intent4);
            }
            if (position == 16) {
                Intent intent4 = new Intent(MainActivity.this, April11.class);
                startActivity(intent4);
            }
            if (position == 24) {
                Intent intent4 = new Intent(MainActivity.this, April19.class);
                startActivity(intent4);
            }
            if (position == 22) {
                Intent intent4 = new Intent(MainActivity.this, April17.class);
                startActivity(intent4);
            }
            if (position == 27) {
                Intent intent4 = new Intent(MainActivity.this, April22.class);
                startActivity(intent4);
            }
            if (position == 31) {
                Intent intent4 = new Intent(MainActivity.this, April26.class);
                startActivity(intent4);
            }
            if (position == 35) {
                Intent intent4 = new Intent(MainActivity.this, April30.class);
                startActivity(intent4);
            }
        }
        if (num == 4) {
            if (position == 3) {
                Intent intent4 = new Intent(MainActivity.this, May3.class);
                startActivity(intent4);
            }
            if (position == 4) {
                Intent intent4 = new Intent(MainActivity.this, May4.class);
                startActivity(intent4);
            }
            if (position == 6) {
                Intent intent4 = new Intent(MainActivity.this, May6.class);
                startActivity(intent4);
            }
            if (position == 9) {
                Intent intent4 = new Intent(MainActivity.this, May9.class);
                startActivity(intent4);
            }
            if (position == 11) {
                Intent intent4 = new Intent(MainActivity.this, May11.class);
                startActivity(intent4);
            }
            if (position == 18) {
                Intent intent4 = new Intent(MainActivity.this, May18.class);
                startActivity(intent4);
            }
            if (position == 19) {
                Intent intent4 = new Intent(MainActivity.this, May19.class);
                startActivity(intent4);
            }
            if (position == 26) {
                Intent intent4 = new Intent(MainActivity.this, May26.class);
                startActivity(intent4);
            }
            if (position == 31) {
                Intent intent4 = new Intent(MainActivity.this, May31.class);
                startActivity(intent4);
            }
        }
        if (num == 5) {
            if (position == 4) {
                Intent intent4 = new Intent(MainActivity.this, June1.class);
                startActivity(intent4);
            }
            if (position == 6) {
                Intent intent4 = new Intent(MainActivity.this, June3.class);
                startActivity(intent4);
            }
            if (position == 8) {
                Intent intent4 = new Intent(MainActivity.this, June5.class);
                startActivity(intent4);
            }
            if (position == 12) {
                Intent intent4 = new Intent(MainActivity.this, June9.class);
                startActivity(intent4);
            }
            if (position == 18) {
                Intent intent4 = new Intent(MainActivity.this, June15.class);
                startActivity(intent4);
            }
            if (position == 20) {
                Intent intent4 = new Intent(MainActivity.this, June17.class);
                startActivity(intent4);
            }
            if (position == 27) {
                Intent intent4 = new Intent(MainActivity.this, June24.class);
                startActivity(intent4);
            }
            if (position == 24) {
                Intent intent4 = new Intent(MainActivity.this, Jun21.class);
                startActivity(intent4);
            }
        }
        if (num == 6) {
            if (position == 6) {
                Intent intent4 = new Intent(MainActivity.this, July1.class);
                startActivity(intent4);
            }
            if (position == 12) {
                Intent intent4 = new Intent(MainActivity.this, July7.class);
                startActivity(intent4);
            }
            if (position == 14) {
                Intent intent4 = new Intent(MainActivity.this, July9.class);
                startActivity(intent4);
            }
            if (position == 22) {
                Intent intent4 = new Intent(MainActivity.this, July17.class);
                startActivity(intent4);
            }
            if (position == 25) {
                Intent intent4 = new Intent(MainActivity.this, July20.class);
                startActivity(intent4);
            }
            if (position == 28) {
                Intent intent4 = new Intent(MainActivity.this, July23.class);
                startActivity(intent4);
            }
            if (position == 35) {
                Intent intent4 = new Intent(MainActivity.this, July30.class);
                startActivity(intent4);
            }
            if (position == 36) {
                Intent intent4 = new Intent(MainActivity.this, July31.class);
                startActivity(intent4);
            }


        }
        if (num == 7) {
            if (position == 8) {
                Intent intent4 = new Intent(MainActivity.this, August7.class);
                startActivity(intent4);
            }
            if (position == 9) {
                Intent intent4 = new Intent(MainActivity.this, August8.class);
                startActivity(intent4);
            }
            if (position == 14) {
                Intent intent4 = new Intent(MainActivity.this, August13.class);
                startActivity(intent4);
            }
            if (position == 16) {
                Intent intent4 = new Intent(MainActivity.this, August15.class);
                startActivity(intent4);
            }
            if (position == 23) {
                Intent intent4 = new Intent(MainActivity.this, August22.class);
                startActivity(intent4);
            }
            if (position == 30) {
                Intent intent4 = new Intent(MainActivity.this, August29.class);
                startActivity(intent4);
            }
            if (position == 31) {
                Intent intent4 = new Intent(MainActivity.this, August30.class);
                startActivity(intent4);
            }
        }
        if (num == 8) {
            if (position == 9) {
                Intent intent21 = new Intent(MainActivity.this, September5.class);
                startActivity(intent21);
            }
            if (position == 10) {
                Intent intent21 = new Intent(MainActivity.this, September6.class);
                startActivity(intent21);
            }
            if (position == 11) {
                Intent intent21 = new Intent(MainActivity.this, September7.class);
                startActivity(intent21);
            }
            if (position == 16) {
                Intent intent21 = new Intent(MainActivity.this, September12.class);
                startActivity(intent21);
            }
            if (position == 17) {
                Intent intent21 = new Intent(MainActivity.this, September13.class);
                startActivity(intent21);
            }
            if (position == 21) {
                Intent intent21 = new Intent(MainActivity.this, September17.class);
                startActivity(intent21);
            }
            if (position == 22) {
                Intent intent21 = new Intent(MainActivity.this, September18.class);
                startActivity(intent21);
            }
            if (position == 27) {
                Intent intent21 = new Intent(MainActivity.this, September23.class);
                startActivity(intent21);
            }
            if (position == 31) {
                Intent intent21 = new Intent(MainActivity.this, September27.class);
                startActivity(intent21);
            }
            if (position == 24) {
                Intent intent21 = new Intent(MainActivity.this, September20.class);
                startActivity(intent21);
            }

        }
        if (num == 9) {
            if (position == 12) {
                Intent intent21 = new Intent(MainActivity.this, October6.class);
                startActivity(intent21);
            }
            if (position == 14) {
                Intent intent21 = new Intent(MainActivity.this, October8.class);
                startActivity(intent21);
            }
            if (position == 16) {
                Intent intent21 = new Intent(MainActivity.this, October10.class);
                startActivity(intent21);
            }
            if (position == 18) {
                Intent intent21 = new Intent(MainActivity.this, October12.class);
                startActivity(intent21);
            }
            if (position == 24) {
                Intent intent21 = new Intent(MainActivity.this, October18.class);
                startActivity(intent21);
            }
            if (position == 26) {
                Intent intent21 = new Intent(MainActivity.this, October20.class);
                startActivity(intent21);
            }
            if (position == 27) {
                Intent intent21 = new Intent(MainActivity.this, October21.class);
                startActivity(intent21);
            }
            if (position == 29) {
                Intent intent21 = new Intent(MainActivity.this, October23.class);
                startActivity(intent21);
            }
            if (position == 30) {
                Intent intent21 = new Intent(MainActivity.this, October24.class);
                startActivity(intent21);
            }
            if (position == 34) {
                Intent intent21 = new Intent(MainActivity.this, October28.class);
                startActivity(intent21);
            }

        }
        if (num == 10) {
            if (position == 6) {
                Intent intent21 = new Intent(MainActivity.this, November4.class);
                startActivity(intent21);
            }
            if (position == 9) {
                Intent intent21 = new Intent(MainActivity.this, November7.class);
                startActivity(intent21);
            }
            if (position == 13) {
                Intent intent21 = new Intent(MainActivity.this, November11.class);
                startActivity(intent21);
            }
            if (position == 14) {
                Intent intent21 = new Intent(MainActivity.this, November12.class);
                startActivity(intent21);
            }
            if (position == 20) {
                Intent intent21 = new Intent(MainActivity.this, November18.class);
                startActivity(intent21);
            }
            if (position == 24) {
                Intent intent21 = new Intent(MainActivity.this, November22.class);
                startActivity(intent21);
            }
            if (position == 26) {
                Intent intent21 = new Intent(MainActivity.this, November24.class);
                startActivity(intent21);
            }
            if (position == 29) {
                Intent intent21 = new Intent(MainActivity.this, November27.class);
                startActivity(intent21);
            }


        }


        if (num == 11) {
            if (position == 25) {
                Intent intent21 = new Intent(MainActivity.this, December21.class);
                startActivity(intent21);
            }
            if (position == 8) {
                Intent intent4 = new Intent(MainActivity.this, December4.class);
                startActivity(intent4);
            }
            if (position == 11) {
                Intent intent7 = new Intent(MainActivity.this, December7.class);
                startActivity(intent7);
            }
            if (position == 13) {
                Intent intent9 = new Intent(MainActivity.this, December9.class);
                startActivity(intent9);
            }
            if (position == 18) {
                Intent intent = new Intent(MainActivity.this, December14.class);
                startActivity(intent);
            }
            if (position == 22) {
                Intent intent7 = new Intent(MainActivity.this, December18.class);
                startActivity(intent7);
            }
            if (position == 25) {
                Intent intent7 = new Intent(MainActivity.this, December21.class);
                startActivity(intent7);
            }
            if (position == 27) {
                Intent intent7 = new Intent(MainActivity.this, December23.class);
                startActivity(intent7);
            }
            if (position == 30) {
                Intent intent7 = new Intent(MainActivity.this, December26.class);
                startActivity(intent7);
            }

        }


    }


    @Override
    public void onClick(View v) {
        if (mThisMonthCalendar.get(Calendar.MONTH) == Calendar.JANUARY) {
            switch (v.getId()) {
                case R.id.gv_calendar_activity_b_last:
                    Toast toast1 = Toast.makeText(this, "2017년의 첫 달입니다.",
                            Toast.LENGTH_SHORT);
                    toast1.show();
                    break;
                case R.id.gv_calendar_activity_b_next:
                    mThisMonthCalendar = getNextMonth(mThisMonthCalendar);
                    getCalendar(mThisMonthCalendar);
                    break;
            }
        }


        if (mThisMonthCalendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
            switch (v.getId()) {
                case R.id.gv_calendar_activity_b_next:
                    Toast toast2 = Toast.makeText(this, "2017년의 마지막 달입니다.",
                            Toast.LENGTH_SHORT);
                    toast2.show();
                    break;
                case R.id.gv_calendar_activity_b_last:
                    mThisMonthCalendar = getLastMonth(mThisMonthCalendar);
                    getCalendar(mThisMonthCalendar);
                    break;

            }

        }


        if (mThisMonthCalendar.get(Calendar.MONTH) != Calendar.DECEMBER &&
                mThisMonthCalendar.get(Calendar.MONTH) != Calendar.JANUARY) {
            switch (v.getId()) {
                case R.id.gv_calendar_activity_b_last:
                    mThisMonthCalendar = getLastMonth(mThisMonthCalendar);
                    getCalendar(mThisMonthCalendar);
                    break;
                case R.id.gv_calendar_activity_b_next:
                    mThisMonthCalendar = getNextMonth(mThisMonthCalendar);
                    getCalendar(mThisMonthCalendar);
                    break;
            }
        }
    }

    private void initCalendarAdapter() {
        mCalendarAdapter = new Adapter(this, R.layout.day, mDayList);               // day.layout은 text 1개로 구성된 레이아웃, 그 텍스트엔 날짜가 들어있습니다.

        mGvCalendar.setAdapter(mCalendarAdapter);
    }
}