package friendtracker.assignment1_s2_2017;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import friendtracker.assignment1_s2_2017.controllers.FriendListViewAdapter;
import friendtracker.assignment1_s2_2017.entities.Friend;
import friendtracker.assignment1_s2_2017.entities.FriendList;
import friendtracker.assignment1_s2_2017.services.DummyLocationService;

public class MeetingFriendActivity extends AppCompatActivity
{
    Context context = MeetingFriendActivity.this;
    private FriendListViewAdapter pAdapter;
    private ArrayList<HashMap<String,Object>> listItem;
    ListView listview;
    private String[] friendsId;
    private SimpleDateFormat sdfDate
            = new SimpleDateFormat("MMM dd,yyyy,h.m aaa");
    //private SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss aaa");
    private int pre_minutes = 30;
    private int pre_seconds = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_friend);

        init();
        //constructData();
        constructDataDummy();
    }

    protected void init()
    {
        Button btn_pick = (Button)findViewById(R.id.meeting_friend_list_btn_ok);
        listview = (ListView)findViewById(R.id.meeting_friend_list_view);
        listItem = new ArrayList<HashMap<String,Object>>();
        btn_pick.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MeetingFriendActivity.this,MeetingDetailActivity.class);
                String[] isSelectedFriends = pAdapter.getSelectedFriendId();
                intent.putExtra("isSelectedFriends",isSelectedFriends);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    protected void constructDataDummy()
    {
        String meetingStartTime="";
        Date dMeetingStartTime = new Date();
        Intent myIntent = getIntent();
        friendsId = myIntent.getStringArrayExtra("friendId");
        meetingStartTime = myIntent.getStringExtra("meetingTime");
        try
        {
            dMeetingStartTime =DateFormat.getTimeInstance(DateFormat.MEDIUM).parse(meetingStartTime);

                   // sdfDate.parse(meetingStartTime);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }

        //call dummy Location
        DummyLocationService dummyLocationService=DummyLocationService.getSingletonInstance(context);
        //dummyLocationService.logAll();
        List<DummyLocationService.FriendLocation> matched = null;
        matched = dummyLocationService.getFriendLocationsForTime(
                      dMeetingStartTime
                    , pre_minutes
                    , pre_seconds);
        //dummyLocationService.log(matched);

        HashMap<String,Friend> hm = FriendList.getFriends();
        Iterator<DummyLocationService.FriendLocation> iter = matched.iterator();
        while(iter.hasNext())
        {
            DummyLocationService.FriendLocation fLocation = iter.next();

            Iterator<Map.Entry<String, Friend>> iterFriendList = hm.entrySet()
                    .iterator();

            while(iterFriendList.hasNext())
            {
                Map.Entry entry = iterFriendList.next();
                Friend f = (Friend) entry.getValue();
                if (fLocation.getName().equals(f.getName()))
                {
                    HashMap<String,Object> hm_temp = new HashMap<String,Object>();
                    hm_temp.put("friend_item_id",f.getId());
                    hm_temp.put("friend_item_icon",f.getPhotoPath());
                    hm_temp.put("friend_item_name",f.getName());
                    hm_temp.put("friend_item_email",f.getEmail());
                    hm_temp.put("friend_item_birthday",f.getBirthDay());
                    hm_temp.put("friend_item_location",fLocation.getLatitude
                            ()+","+fLocation.getLongitude());
                    if (friendsId==null || friendsId.length==0)
                    {
                        hm_temp.put("friend_item_ckb",0);
                    }else
                    {
                        for (int i=0;i<friendsId.length;i++)
                        {
                            if (f.getId().equals(friendsId[i]))
                            {
                                hm_temp.put("friend_item_ckb",1);
                                break;
                            }else
                            {
                                hm_temp.put("friend_item_ckb",0);
                            }
                        }
                    }
                    listItem.add(hm_temp);
                    break;
                }else
                {
                    continue;
                }
            }

        }
        pAdapter = new FriendListViewAdapter(this,listItem);
        listview.setAdapter(pAdapter);
    }

    protected void constructData()
    {
        Intent myIntent = getIntent();
        friendsId = myIntent.getStringArrayExtra("friendId");

        HashMap<String,Friend> hm = FriendList.getFriends();
        Iterator<Map.Entry<String, Friend>> iter = hm.entrySet().iterator();
        while(iter.hasNext())
        {
            Map.Entry entry = iter.next();
            Friend f = (Friend) entry.getValue();
            HashMap<String,Object> hm_temp = new HashMap<String,Object>();
            hm_temp.put("friend_item_id",f.getId());
            hm_temp.put("friend_item_icon",f.getPhotoPath());
            hm_temp.put("friend_item_name",f.getName());
            hm_temp.put("friend_item_email",f.getEmail());
            hm_temp.put("friend_item_birthday",f.getBirthDay());
            if (friendsId==null || friendsId.length==0)
            {
                hm_temp.put("friend_item_ckb",0);
            }else
            {
                for (int i=0;i<friendsId.length;i++)
                {
                    if (f.getId().equals(friendsId[i]))
                    {
                        hm_temp.put("friend_item_ckb",1);
                        break;
                    }else
                    {
                        hm_temp.put("friend_item_ckb",0);
                    }
                }
            }
            listItem.add(hm_temp);
        }
        pAdapter = new FriendListViewAdapter(this,listItem);
        listview.setAdapter(pAdapter);
    }
}
