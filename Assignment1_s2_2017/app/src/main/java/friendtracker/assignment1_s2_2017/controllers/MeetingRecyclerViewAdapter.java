package friendtracker.assignment1_s2_2017.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import friendtracker.assignment1_s2_2017.MeetingDetailActivity;
import friendtracker.assignment1_s2_2017.MeetingListActivity;
import friendtracker.assignment1_s2_2017.R;
import friendtracker.assignment1_s2_2017.entities.Friend;
import friendtracker.assignment1_s2_2017.entities.Meeting;
import friendtracker.assignment1_s2_2017.entities.MeetingList;

/**
 * Created by Jason on 8/17/2017.
 */

public class MeetingRecyclerViewAdapter
        extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.CustomViewHolder>
{
    protected ArrayList<Meeting> meetingItemList = new ArrayList<Meeting>();
    private Context mContext;
    SimpleDateFormat sdfDate = new java.text.SimpleDateFormat("MMM dd," +
        "yyyy,h.m aaa");

    class CustomViewHolder  extends RecyclerView.ViewHolder
    {
        protected TextView tv_id_meeting_item;
        protected TextView tv_title_meeting_item;
        protected TextView tv_start_meeting_item;
        protected TextView tv_end_meeting_item;
        protected TextView tv_location_meeting_item;
        protected TextView tv_person_meeting_item;

        public CustomViewHolder(View view)
        {
            super(view);
            this.tv_id_meeting_item  = (TextView) view.findViewById(R.id
                    .id_meeting_item);
            this.tv_title_meeting_item  = (TextView) view.findViewById(R.id
                    .title_meeting_item);
            this.tv_start_meeting_item  = (TextView) view.findViewById(R.id
                    .start_meeting_item);
            this.tv_end_meeting_item  = (TextView) view.findViewById(R.id
                    .end_meeting_item);
            this.tv_location_meeting_item = (TextView) view.findViewById(R.id
                    .location_meeting_item);
            this.tv_person_meeting_item = (TextView) view.findViewById(R.id
                    .numbers_meeting_item);
        }
    }

    /**
     * Constructor method
     * @param meetingItemList
     * @param mContext
     */
    public MeetingRecyclerViewAdapter(ArrayList<Meeting> meetingItemList, Context mContext)
    {
        this.meetingItemList = meetingItemList;
        this.mContext = mContext;
    }
    public ArrayList<Meeting> getMeetingItemList()
    {
        return meetingItemList;
    }

    public void setMeetingItemList(ArrayList<Meeting> meetingItemList)
    {
        this.meetingItemList = meetingItemList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.meeting_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position)
    {
        Meeting meetingItem = meetingItemList.get(position);
        //Setting text view title
        holder.tv_id_meeting_item.setText(meetingItem.getId());
        holder.tv_title_meeting_item.setText(meetingItem.getTitle());
        holder.tv_start_meeting_item.setText(sdfDate.format(meetingItem.getStartTime()));
        holder.tv_end_meeting_item.setText(sdfDate.format(meetingItem.getEndTime()));
        holder.tv_location_meeting_item.setText(meetingItem.getLocation());
        ArrayList<Friend> tempList = (ArrayList<Friend>) meetingItem.getFriends();
        int number_friends=0;
        if (tempList==null)
        {
            number_friends = 0;
        }else
        {
            number_friends = tempList.size();
        }
        holder.tv_person_meeting_item.setText(String.valueOf(number_friends));

        //set listener for itemView
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editItem(position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                PopupMenu popupMenu = new PopupMenu(mContext,holder.itemView);
                popupMenu.inflate(R.menu.pop_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        switch (item.getItemId())
                        {
                            case R.id.pop_menu_delete:

                                deleteItem(position);
                                break;
                            case R.id.pop_menu_edit:

                                editItem(position);
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return  false;
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return (null != meetingItemList ? meetingItemList.size() : 0);
    }

    protected void deleteItem(int aPosition)
    {
        Meeting meeting =meetingItemList.get(aPosition);
        meetingItemList.remove(aPosition);
        MeetingList.removeMeetingByKey(meeting.getId());
        this.notifyDataSetChanged();
    }
    protected void editItem(int aPosition)
    {
        Meeting meeting =meetingItemList.get(aPosition);
        Intent intent = new Intent(mContext,MeetingDetailActivity.class);
        intent.putExtra("EXTRA_MEETING_ID", meeting.getId());
        ((Activity)mContext).startActivityForResult(intent,2);
        //mContext.startActivity(intent);

    }
}
