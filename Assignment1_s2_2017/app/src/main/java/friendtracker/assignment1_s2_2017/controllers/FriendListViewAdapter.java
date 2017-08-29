package friendtracker.assignment1_s2_2017.controllers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import friendtracker.assignment1_s2_2017.R;
import friendtracker.assignment1_s2_2017.entities.Friend;
import friendtracker.assignment1_s2_2017.entities.FriendList;
import friendtracker.assignment1_s2_2017.entities.Meeting;

/**
 * Created by Jason on 8/23/2017.
 */

public class FriendListViewAdapter extends BaseAdapter
{
    private ArrayList<HashMap<String,Object>> listItem;
    private Context pContext;
    private LayoutInflater mInflater=null;
    private static HashMap<Integer,Boolean> isSelected;

    static class ViewHolder
    {
        public ImageView friend_img;
        public TextView  friend_id;
        public TextView  friend_name;
        public TextView  friend_email;
        public TextView  friend_birthday;
        public LinearLayout friend_item_root;
        public CheckBox  friend_ckb;
    }
    public FriendListViewAdapter(Context aContext,ArrayList<HashMap<String,Object>> aListItem)
    {
        this.pContext = aContext;
        this.listItem = aListItem;
        this.mInflater = LayoutInflater.from(aContext);
        this.isSelected = new HashMap<Integer,Boolean>();
        initData();
    }

    private void initData()
    {
        for(int i=0;i<listItem.size();i++)
        {
            if ((int)listItem.get(i).get("friend_item_ckb")==1 )
            {
                getIsSelected().put(i,true);
            }else
            {
                getIsSelected().put(i,false);
            }
        }
    }

    @Override
    public int getCount()
    {
        return listItem.size();
    }

    @Override
    public Object getItem(int position)
    {
        return listItem.get(position) ;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            holder =  new ViewHolder();
            convertView = mInflater.inflate(R.layout.friend_item,null);
            holder.friend_item_root = (LinearLayout)convertView.findViewById
                    (R.id.friend_item_root) ;
            holder.friend_id = (TextView)convertView.findViewById(R.id
                    .friend_item_id);
            holder.friend_img = (ImageView)convertView.findViewById(R.id
                    .friend_item_icon);
            holder.friend_name = (TextView)convertView.findViewById(R.id
                    .friend_item_name);
            holder.friend_email = (TextView)convertView.findViewById(R.id
                    .friend_item_email);
            holder.friend_birthday = (TextView)convertView.findViewById(R.id
                    .friend_item_birthday);

            holder.friend_ckb = (CheckBox)convertView.findViewById(R.id
                    .friend_item_ckb);

            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.friend_id.setText((String)listItem.get(position).get
                ("friend_item_id"));
        holder.friend_name.setText((String)listItem.get(position).get
                ("friend_item_name"));
        holder.friend_email.setText((String)listItem.get(position).get
                ("friend_item_email"));
        holder.friend_ckb.setChecked(getIsSelected().get(position));

        holder.friend_ckb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                if (getIsSelected().get(position) == true)
                {
                    getIsSelected().put(position,false);
                }else
                {
                    getIsSelected().put(position,true);
                }
            }
        });

        return convertView;
    }

    public static HashMap<Integer, Boolean> getIsSelected()
    {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected)
    {
        FriendListViewAdapter.isSelected = isSelected;
    }

    public String[] getSelectedFriendId()
    {
        int listNumber = listItem.size();
        String[] retValue = new String[listNumber];
        int j=0;
        for (int i=0;i<listNumber;i++)
        {
            if (getIsSelected().get(i) == true)
            {
                HashMap<String,Object> hm_temp = new HashMap<String,Object>();
                hm_temp = listItem.get(i);

                retValue[j] = (String)hm_temp.get("friend_item_id");
                j = j + 1;
            }
        }
        return retValue;
    }



}
