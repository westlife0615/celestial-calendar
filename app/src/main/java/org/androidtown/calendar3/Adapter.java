package org.androidtown.calendar3;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;



public class  Adapter extends BaseAdapter
{
    private ArrayList<DayInfo> mDayList;
                                                    //日의 객체를 담는 배열 객체
    private Context mContext;
                                                    //inflate()를 호출하기위한 도구
    private int mResource;

    private LayoutInflater mLiInflater;
                                                    //Context로부터 접근한 inflater를 담는 변수수
    /**
    * Adpater 생성자
     *
     * @param context
     *            컨텍스트
     * @param textResource
     *            레이아웃 리소스
     * @param dayList
     *            날짜정보가 들어있는 리스트
     */


    public Adapter(Context context, int textResource, ArrayList<DayInfo> dayList)
    {                                   //mCalendarAdapter = new Adapter(this, R.layout.day, mDayList)
                                        // textResource로 R.layout.day가 쓰임
        this.mContext = context;
                                         //LAYOUT_INFLATER_SERVICE에 접근하기 위한 Context 객체
        this.mDayList = dayList;
                                        //1장의 달력을 채울 날들을 ArrayList<DayInfo> 에 담는다.



        this.mResource = textResource;                          //R.layout.day을 Adapter 클래스에서 사용할 수 있게됨
        this.mLiInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }                                   //Context는 시스템을 관리하는 매개체이다. 고로 LayoutInflater 서비스를 공급하기 위해
                                        //Context 객체가 필요하다.

    @Override               //Adapter 클래스의 메소드들을 재정의해야한다.
    public int getCount()
    {
        // TODO Auto-generated method stub
        return mDayList.size();
                                            //日의 기록을 담은 ArrayList 객체 의 크기
    }

    @Override
    public Object getItem(int position)
    {
                                        // Object getItem (int position)
                                        //int: Position of the item whose data we want within the adapter's data set
        return mDayList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        DayInfo day = mDayList.get(position);
                                //해당 index의 DayInfo 객체를 day에 반환한다.
        DayViewHolde dayViewHolder;
                                //뷰홀더 객체 선언
        if(convertView == null)
        {
            convertView = mLiInflater.inflate(mResource, null);
                                            //convertView에 R.layout.day를 넣음
            if(position % 7 == 6)
            {
                convertView.setLayoutParams(new GridView.LayoutParams(getCellWidthDP()+getRestCellWidthDP(), getCellHeightDP()));
            }                           //LayoutParams는 ViewGroup의 static inner class
                                        // 레이아웃의 파라미터를 정의하는 클래스로 가로 세로 규격을 결정한다.
            else
            {
                convertView.setLayoutParams(new GridView.LayoutParams(getCellWidthDP(), getCellHeightDP()));
            }                           //convertView의 파라미터를 결정한다.


            dayViewHolder = new DayViewHolde();

            dayViewHolder.llBackground = (LinearLayout)convertView.findViewById(R.id.day_cell_ll_background);
            dayViewHolder.tvDay = (TextView) convertView.findViewById(R.id.day_cell_tv_day);

            convertView.setTag(dayViewHolder);
                                        //View 내부에 데이터를 집어 넣는 방법
                                        //void setTag (Object tag)
                                        //setTag의 매개변수인 object의 멤버를 View 객체에 적용시킨다.
        }
        else                            //convertVIew가 빈 값이 아닌 경우
        {
            dayViewHolder = (DayViewHolde) convertView.getTag();
        }

        if(day != null)
        {
            dayViewHolder.tvDay.setText(day.getDay());

            if(day.isInMonth())
            {
                if(position % 7 == 0)
                {
                    dayViewHolder.tvDay.setTextColor(Color.RED);

                }
                else if(position % 7 == 6)
                {
                    dayViewHolder.tvDay.setTextColor(Color.BLUE);
                }
                else
                {
                    dayViewHolder.tvDay.setTextColor(Color.BLACK);
                }
            }
            else
            {
                dayViewHolder.tvDay.setTextColor(Color.GRAY);
            }


        }

        return convertView;
    }

    public class DayViewHolde
    {
        public LinearLayout llBackground;
        public TextView tvDay;

    }

    private int getCellWidthDP()
    {
//      int width = mContext.getResources().getDisplayMetrics().widthPixels;
        int cellWidth = 480/7;

        return cellWidth;
    }

    private int getRestCellWidthDP()
    {
//      int width = mContext.getResources().getDisplayMetrics().widthPixels;
        int cellWidth = 480%7;

        return cellWidth;
    }

    private int getCellHeightDP()
    {
//      int height = mContext.getResources().getDisplayMetrics().widthPixels;
        int cellHeight = 480/6;

        return cellHeight;
    }

}