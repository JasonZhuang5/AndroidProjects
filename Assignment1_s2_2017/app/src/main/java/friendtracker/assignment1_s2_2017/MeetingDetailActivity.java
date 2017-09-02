package friendtracker.assignment1_s2_2017;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import friendtracker.assignment1_s2_2017.entities.Friend;
import friendtracker.assignment1_s2_2017.entities.FriendList;
import friendtracker.assignment1_s2_2017.entities.Meeting;
import friendtracker.assignment1_s2_2017.entities.MeetingList;
import friendtracker.assignment1_s2_2017.utility.AppUtility;

import static friendtracker.assignment1_s2_2017.entities.FriendList.getFriendById;

public class MeetingDetailActivity extends AppCompatActivity
{
    Context context;
    private static final String TAG =   "MeetingDetailActivity";
    //private DatePickerDialog.OnDateSetListener mDateSetListener_s;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener_s;
    //private DatePickerDialog.OnDateSetListener mDateSetListener_e;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener_e;
    private SimpleDateFormat sdfDate = new SimpleDateFormat("MMM dd,yyyy,h.m " +
            "aaa");
    private SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss aaa");

    private int editFlag = 0;

    private Calendar dStart = Calendar.getInstance();
    private Calendar dEnd   = Calendar.getInstance();

    private TextView tv_id_meeting;
    private String meeting_id="";

    private EditText et_title_meeting;
    private String meeting_title="";

    private EditText et_start_meeting;
    private Date    meeting_start_date = null;
    //private ImageButton img_date_start_meeting;
    private ImageButton img_time_start_meeting;

    private EditText et_end_meeting;
    private Date    meeting_end_date = null;
    //private ImageButton img_date_end_meeting;
    private ImageButton img_time_end_meeting;

    private EditText et_location_meeting;
    private String meeting_location="";

    private EditText et_persions_numbers;
    private int    meeting_friends_numbers;
    private List   lstMeetingFriends;

    private Button btn_save_meeting;
    private Button btn_cancel_meeting;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_detail);
        context = getApplicationContext();
        initView();
    }

    private void initView()
    {
        tv_id_meeting = (TextView)findViewById(R.id.id_meeting_detail);
        et_title_meeting = (EditText)findViewById(R.id.title_meeting_detail);
        et_start_meeting = (EditText)findViewById(R.id.start_time_meeting_detail);
        et_start_meeting.setInputType(InputType.TYPE_NULL);
        //img_date_start_meeting=(ImageButton)findViewById(R.id.imbtn_start_date_detail);
        img_time_start_meeting=(ImageButton)findViewById(R.id.imbtn_start_time_detail);

        et_end_meeting = (EditText)findViewById(R.id.end_time_meeting_detail);
        et_end_meeting.setInputType(InputType.TYPE_NULL);
        //img_date_end_meeting =(ImageButton)findViewById(R.id.imbtn_end_date_detail);
        img_time_end_meeting =(ImageButton)findViewById(R.id.imbtn_end_time_detail);
        et_location_meeting = (EditText)findViewById(R.id.location_meeting_detail);
        et_location_meeting.setInputType(InputType.TYPE_NULL);
        et_persions_numbers = (EditText)findViewById(R.id.numbers_meeting_detail);
        et_persions_numbers.setInputType(InputType.TYPE_NULL);
        btn_save_meeting   = (Button)findViewById(R.id.btn_save_meeting);
        btn_cancel_meeting = (Button)findViewById(R.id.btn_cancel_meeting);
        //===========================================================//
        Intent intent = getIntent();
        meeting_id = intent.getStringExtra("EXTRA_MEETING_ID");
        if (meeting_id==null || meeting_id=="" ||meeting_id.equals
                ("EXTRA_MEETING_ID" ))
        {
            meeting_id = AppUtility.generateIdForMeeting();
            editFlag = 0;
            //tv_id_meeting.setText(meeting_id);
        }else
        {
            Meeting meeting = MeetingList.getMeetingById(meeting_id);
            meeting_title = meeting.getTitle();
            meeting_start_date = meeting.getStartTime();
            meeting_end_date = meeting.getEndTime();
            meeting_location = meeting.getLocation();
            int iCount =0;
            if (meeting.getFriends()!=null)
            {
                iCount = meeting.getFriends().size();
                lstMeetingFriends = meeting.getFriends();
            }
            meeting_friends_numbers = iCount;

            et_title_meeting.setText(meeting.getTitle());
            et_start_meeting.setText(sdfDate.format(meeting.getStartTime()));
            et_end_meeting.setText(sdfDate.format(meeting.getEndTime()));
            et_location_meeting.setText(meeting.getLocation());
            et_persions_numbers.setText(String.valueOf(iCount));
            editFlag = 1;
        }
        /*
        img_date_start_meeting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day   = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        MeetingDetailActivity.this,
                        mDateSetListener_s,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable
                        (Color.YELLOW));
                dialog.show();
            }
        });
        */
        /*
        mDateSetListener_s = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                dStart.set(year,month,dayOfMonth);
                et_start_meeting.setText(sdfDate.format(dStart.getTime()));
                meeting_start_date = dStart.getTime();
            }
        };
        */
        img_time_start_meeting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                    Calendar c = Calendar.getInstance();
                    int hour = c.get(Calendar.HOUR);
                    int minute = c.get(Calendar.MINUTE);
                    TimePickerDialog dialog = new TimePickerDialog(
                            MeetingDetailActivity.this,
                            mTimeSetListener_s,
                            hour, minute,true);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable
                            (Color.YELLOW));
                    dialog.show();
            }
        });
        mTimeSetListener_s = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                dStart.set(dStart.get(Calendar.YEAR)
                        ,dStart.get(Calendar.MONTH)
                        ,dStart.get(Calendar.DAY_OF_MONTH)
                        ,hourOfDay,minute);
                et_start_meeting.setText(sdfDate.format(dStart.getTime()));
                meeting_start_date = dStart.getTime();
            }
        };

        //=============================================================//
        /*
        img_date_end_meeting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar c = Calendar.getInstance();
                int year  = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day   = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        MeetingDetailActivity.this,
                        mDateSetListener_e,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable
                        (Color.YELLOW));
                dialog.show();
            }
        });
        */
        /*
        mDateSetListener_e = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                dEnd.set(year,month,dayOfMonth);
                et_end_meeting.setText(sdfDate.format(dEnd.getTime()));
                meeting_end_date = dEnd.getTime();
            }
        };
        */
        img_time_end_meeting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                    Calendar c = Calendar.getInstance();
                    int hour = c.get(Calendar.HOUR);
                    int minute = c.get(Calendar.MINUTE);
                    TimePickerDialog dialog = new TimePickerDialog(MeetingDetailActivity.this, mTimeSetListener_e, hour, minute, true);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.YELLOW));
                    dialog.show();
            }
        });
        mTimeSetListener_e = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                dEnd.set(dEnd.get(Calendar.YEAR)
                        ,dEnd.get(Calendar.MONTH)
                        ,dEnd.get(Calendar.DAY_OF_MONTH)
                        ,hourOfDay,minute);
                et_end_meeting.setText(sdfDate.format(dEnd.getTime()));
                meeting_end_date = dEnd.getTime();
            }
        };

        //================================//
        et_location_meeting.setOnFocusChangeListener(new View
                .OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(hasFocus)
                {
                    Intent myIntent = new Intent(MeetingDetailActivity.this, LocationActivity.class);
                    startActivityForResult(myIntent, 1);
                }
            }
        });
        //===============================//
        et_persions_numbers.setOnFocusChangeListener(new View
                .OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                String s_start_meeting_time = et_start_meeting.getText().toString();
                if (s_start_meeting_time==null || s_start_meeting_time.equals(""))
                {
                    return;
                }
                if(hasFocus)
                {
                    openFriendActivity(s_start_meeting_time);
                }
            }
        });

        et_persions_numbers.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String s_start_meeting_time = et_start_meeting.getText().toString();
                if (s_start_meeting_time==null || s_start_meeting_time.equals(""))
                {
                    return;
                }
                openFriendActivity(s_start_meeting_time);
            }
        });

        //===============================//
        btn_save_meeting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                saveMeeting(v);
            }
        });

        btn_cancel_meeting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                cancelMeeting(v);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            String latitude=data.getStringExtra("latitude");
            String longitude=data.getStringExtra("longitude");
            et_location_meeting.setText(longitude+","+latitude);
            meeting_location = longitude+","+latitude;
        }

        if(requestCode==2 && resultCode==RESULT_OK)
        {
            lstMeetingFriends = new ArrayList();
            String[] temp = data.getStringArrayExtra("isSelectedFriends");
            int friendNumbers = 0;
            int lengthOfArray = 0;
            if (temp != null)
            {
                lengthOfArray = temp.length;
                for (int i=0;i<lengthOfArray;i++)
                {
                    if (temp[i] != null)
                    {
                        Friend f = FriendList.getFriendById(temp[i]);
                        lstMeetingFriends.add(f);
                        friendNumbers = friendNumbers + 1;
                    }
                }
            }else
            {
                friendNumbers = 0;
            }
            et_persions_numbers.setText(String.valueOf(friendNumbers));
            meeting_friends_numbers = friendNumbers;
        }
    }

    protected void saveMeeting(View v)
    {
        //title cannot be null
        meeting_title = et_title_meeting.getText().toString();
        if (meeting_title.equals(""))
        {
            Toast.makeText(this,"The title of Meeting can not be empty.",
                Toast.LENGTH_SHORT).show();
            et_title_meeting.setFocusable(true);
            return;
        }
        //start date cannot be null
        if (et_start_meeting.getText()==null||et_start_meeting.getText()
                .equals(""))
        {
            Toast.makeText(this,"The Start Time of Meeting can not be empty.",
                    Toast.LENGTH_SHORT).show();
            return;
        }else
        {
            try
            {
                meeting_start_date = sdfDate.parse(et_start_meeting.getText()
                        .toString());
            } catch (ParseException e)
            {
                Toast.makeText(this,"The Start Time is not correct.",
                        Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return;
            }
        }

        //end date cannot be null
        if (et_end_meeting.getText()==null||et_end_meeting.getText()
                .equals(""))
        {
            Toast.makeText(this,"The End Time of Meeting can not be empty.",
                    Toast.LENGTH_SHORT).show();
            return;
        }else
        {
            try
            {
                meeting_end_date = sdfDate.parse(et_end_meeting.getText()
                        .toString());
            } catch (ParseException e)
            {
                Toast.makeText(this,"The End Time is not correct.",
                        Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return;
            }
        }

        //start date must be before end date
        long difference_time=0l;
        difference_time = meeting_end_date.getTime() - meeting_start_date
                .getTime();
        if (difference_time<=0)
        {
            Toast.makeText(this,"The end time of a meeting must be later than start date"
                    ,Toast.LENGTH_SHORT).show();
            return;
        }
        //location cannot be null
        meeting_location = et_location_meeting.getText().toString().trim();
        if (meeting_location.equals(""))
        {
            Toast.makeText(this,"The Location of a meeting cannot be empty."
                    ,Toast.LENGTH_SHORT).show();
            return;
        }
        //friend
        if (lstMeetingFriends == null || lstMeetingFriends.size()==0)
        {
            Toast.makeText(this,"The Friends of a meeting cannot be empty."
                    ,Toast.LENGTH_SHORT).show();
            return;
        }

        if (editFlag==0) ////create a new meeting and save it to meetinglist
        {
            Meeting meeting = new Meeting();
            meeting.setId(meeting_id);
            meeting.setTitle(meeting_title);
            meeting.setStartTime(meeting_start_date);
            meeting.setEndTime(meeting_end_date);
            meeting.setLocation(meeting_location);
            meeting.setFriends(lstMeetingFriends);
            MeetingList.addMeeting(meeting);
        }else
        {
            Meeting meeting = MeetingList.getMeetingById(meeting_id);
            meeting.setTitle(meeting_title);
            meeting.setStartTime(meeting_start_date);
            meeting.setEndTime(meeting_end_date);
            meeting.setLocation(meeting_location);
            meeting.setFriends(lstMeetingFriends);
        }

        // set back value = 1 means parent activity needs refresh
        Intent intent = new Intent(MeetingDetailActivity.this,MeetingListActivity.class);
        intent.putExtra("saveMeeting",1);
        setResult(RESULT_OK,intent);
        finish();
    }

    protected void cancelMeeting(View v)
    {
        Intent intent = new Intent(MeetingDetailActivity.this,MeetingListActivity.class);
        setResult(RESULT_CANCELED,intent);
        finish();
    }

    private void openFriendActivity(String a_start_meeting_time)
    {
        Intent myIntent = new Intent(MeetingDetailActivity.this, MeetingFriendActivity.class);
        if (lstMeetingFriends == null || lstMeetingFriends.size() == 0)
        {
            myIntent.putExtra("friendId", new String[]{});
        } else
        {
            String[] arr_friend_id = new String[lstMeetingFriends.size()];
            Iterator<Friend> iterator = lstMeetingFriends.iterator();
            int i = 0;
            while (iterator.hasNext())
            {
                Friend f = iterator.next();
                arr_friend_id[i] = f.getId();
                i = i + 1;
            }
            myIntent.putExtra("friendId", arr_friend_id);
        }
        //put argument meeting time
        //String s_start_meeting_time = et_start_meeting.getText().toString();
        try
        {
            meeting_start_date =   sdfDate.parse(a_start_meeting_time);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }

        myIntent.putExtra("meetingTime", sdfTime.format(meeting_start_date));

        startActivityForResult(myIntent, 2);
    }
}
