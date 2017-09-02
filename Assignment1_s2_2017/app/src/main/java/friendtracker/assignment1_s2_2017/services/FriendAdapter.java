package friendtracker.assignment1_s2_2017.services;

import android.content.Context;
import java.util.ArrayList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import friendtracker.assignment1_s2_2017.R;
import friendtracker.assignment1_s2_2017.entities.Friend;


/**
 * Created by shawn on 2017/8/16.
 */
/*
 * This class is the adapter of the friend list recycler view
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendAdapterViewHolder>
{
    private ArrayList<Friend> friends;
    private ListItemClickListener mOnclickListener;
    //inner class view holder to get list item
    class FriendAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView listItemName;
        TextView listItemEmail;
        ImageButton listItemImage;
        public FriendAdapterViewHolder(View view)
        {
            super(view);
            listItemName = (TextView)view.findViewById(R.id.friend_name);
            listItemEmail = (TextView)view.findViewById(R.id.friend_email);
            listItemImage = (ImageButton) view.findViewById(R.id.friend_photo);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            int clickedPosition = getAdapterPosition();
            mOnclickListener.onListItemClick(clickedPosition);

        }
    }
    //a click listener interface
    public interface ListItemClickListener
    {
        void onListItemClick(int clickedItemIndex);
    }

    public FriendAdapter(ArrayList<Friend> list,ListItemClickListener listener)
    {
        friends = list;
        mOnclickListener = listener;
    }

    public void setFriends(ArrayList<Friend> friends)
    {
        this.friends = friends;
    }

    @Override
    public int getItemCount()
    {
        return friends.size();
    }

    @Override
    //bind the data to the view holder
    public void onBindViewHolder(FriendAdapterViewHolder holder, int position)
    {
        Friend friend = friends.get(position);
        String name = friend.getName();
        String email = friend.getEmail();
        holder.listItemName.setText(name);
        holder.listItemEmail.setText(email);
    }

    @Override
    //creat the view holder
    public FriendAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.friend_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttatchedToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttatchedToParentImmediately);
        FriendAdapterViewHolder fViewHolder = new FriendAdapterViewHolder(view);
        return fViewHolder;
    }
}
