package friendtracker.assignment1_s2_2017;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import friendtracker.assignment1_s2_2017.controllers.MeetingRecyclerViewAdapter;
import friendtracker.assignment1_s2_2017.controllers.ViewInterface;
import friendtracker.assignment1_s2_2017.entities.Meeting;
import friendtracker.assignment1_s2_2017.entities.MeetingList;
import friendtracker.assignment1_s2_2017.utility.AppUtility;
import friendtracker.assignment1_s2_2017.views.DividerItemDecoration;

public class MeetingListActivity extends AppCompatActivity implements
        ViewInterface
{
    //private static final String TAG = "RecyclerViewExample";
    private static final String EXTRA_MEETING_ID = "EXTRA_MEETING_ID";

    private ArrayList<Meeting> meetingList = new ArrayList<Meeting>();
    private RecyclerView meetingRecyclerView;
    private MeetingRecyclerViewAdapter mAdapter = null;
    private int sortType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_list);
        initView();
    }
    private void initView()
    {
        meetingRecyclerView = (RecyclerView) findViewById(R.id.rcv_meeting_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        meetingRecyclerView.setLayoutManager(layoutManager);
        meetingRecyclerView.addItemDecoration(new DividerItemDecoration
            (this,DividerItemDecoration.VERTICAL_LIST));

        meetingList = getMeetingList();
        mAdapter = new MeetingRecyclerViewAdapter(meetingList,MeetingListActivity.this);
        meetingRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mItem)
    {
        int mItemId = mItem.getItemId();
        switch (mItemId)
        {
            case R.id.action_new_meeting:
                startDetailActivity(EXTRA_MEETING_ID,null);
                break;
            case R.id.action_sort:
                this.meetingList = AppUtility.doInsertSort(getMeetingList(),3,sortType);
                mAdapter.setMeetingItemList(this.meetingList);
                if (sortType==0)
                {
                    mItem.setTitle("DESC");
                    sortType = 1;

                }else
                {
                    mItem.setTitle("ASC");
                    sortType = 0;
                }
                this.mAdapter.notifyDataSetChanged();
                break;
            case R.id.action_settings:
                Toast.makeText(MeetingListActivity.this,"You clicked settings",Toast.LENGTH_SHORT).show();
                break;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(mItem);
        }

        return true;
    }

    private ArrayList<Meeting> getMeetingList()
    {
        ArrayList<Meeting> retValue = new ArrayList<Meeting>();
        HashMap<String,Meeting> hm_meetings =  MeetingList.getMeetings();
        for (Meeting meeting : hm_meetings.values())
        {
            retValue.add(meeting);
        }
        return retValue;
    }

    @Override
    public void startDetailActivity(String id, View viewRoot)
    {
        Intent intent = new Intent(this, MeetingDetailActivity.class);
        intent.putExtra(EXTRA_MEETING_ID, id);
        startActivityForResult(intent,1);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            this.meetingList = AppUtility.doInsertSort(getMeetingList(),3,sortType);
            this.mAdapter.setMeetingItemList(meetingList);
            this.mAdapter.notifyDataSetChanged();
        }
        if(requestCode==1 && resultCode==RESULT_CANCELED)
        {
            this.mAdapter.notifyDataSetChanged();
        }

        if(requestCode==2 && resultCode==RESULT_OK)
        {
            this.mAdapter.notifyDataSetChanged();
        }
    }
}
