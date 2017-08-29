package friendtracker.assignment1_s2_2017;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import friendtracker.assignment1_s2_2017.entities.Friend;
import friendtracker.assignment1_s2_2017.entities.FriendList;
import friendtracker.assignment1_s2_2017.entities.Location;
import friendtracker.assignment1_s2_2017.entities.Meeting;
import friendtracker.assignment1_s2_2017.entities.MeetingList;
import friendtracker.assignment1_s2_2017.services.LocationManager;
import friendtracker.assignment1_s2_2017.utility.AppUtility;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //request a Permissions for accessing the Contact
        requestContactPermissions();
        init();
    }

    protected void requestContactPermissions()
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED)
        {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_CONTACTS))
            {
            } else
            {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainActivity.this
                        ,new String[]{Manifest.permission.READ_CONTACTS}
                        ,1);
            }
        }
    }

    protected void init()
    {
        Button btn_add_friend = (Button)findViewById(R.id.btn_addFriend);
        btn_add_friend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openFriendListActivity(v);
            }
        });

        Button btn_add_meeting = (Button)findViewById(R.id.btn_addMeeting);
        btn_add_meeting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openMeetingListActivity(v);
            }
        });

        //init fake location data
        LocationManager.clearLocation();
        try
        {
            InputStream instream = getAssets().open("location.txt");

            if (instream != null)
            {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                String line_arr[] ;
                //read every line
                while (( line = buffreader.readLine()) != null)
                {
                    line_arr=line.split(",");
                    LocationManager.addLocation(line_arr[0],line_arr[1]);
                }
                instream.close();
            }
        }catch (IOException exp)
        {
            exp.printStackTrace();
        }
    }

    public void openFriendListActivity(View view)
    {
        Intent myIntent = new Intent(this, FriendListActivity.class);
        //startActivity(myIntent);
        startActivityForResult(myIntent, 1);
    }

    public void openMeetingListActivity(View view)
    {
        Intent myIntent = new Intent(this, MeetingListActivity.class);
        //startActivity(myIntent);
        startActivityForResult(myIntent, 1);
    }

    protected String[] getAndroidContacts()
    {
        String[] contactItems = new String[100];
        //what is ContentResolver
        int i=0;
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null ,null ,null, null);
        while (cursor.moveToNext())
        {
            String id   = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            //save to class variable
            contactItems[i] = name;
            i++;
            //get Phone Numbers
            Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                    ,null
                    ,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? "
                    ,new String[]{id}
                    ,null);
            Log.i("MY INFO", id + " : " + name);
            while (phoneCursor.moveToNext())
            {
                int columIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String phoneNumber = phoneCursor.getString(columIndex);
                Log.i("MY PHONENumber", phoneNumber);
                break;
            }
            phoneCursor.close();

            //get emails
            Cursor emailCursor = resolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI
                    ,null
                    ,ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ? "
                    ,new String[]{id}
                    ,null);
            while (emailCursor.moveToNext())
            {
                int columIndex = emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
                String myEmail = emailCursor.getString(columIndex);
                Log.i("MY Email ", myEmail);
                break;
            }
            emailCursor.close();

        }
        cursor.close();

        return contactItems;
    }

}
