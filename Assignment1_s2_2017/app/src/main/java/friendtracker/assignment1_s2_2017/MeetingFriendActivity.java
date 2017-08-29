package friendtracker.assignment1_s2_2017;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import friendtracker.assignment1_s2_2017.controllers.FriendListViewAdapter;
import friendtracker.assignment1_s2_2017.entities.Friend;
import friendtracker.assignment1_s2_2017.entities.FriendList;

public class MeetingFriendActivity extends AppCompatActivity
{
    private FriendListViewAdapter pAdapter;
    private ArrayList<HashMap<String,Object>> listItem;
    ListView listview;
    private String[] friendsId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_friend);

        init();
        constructData();
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
