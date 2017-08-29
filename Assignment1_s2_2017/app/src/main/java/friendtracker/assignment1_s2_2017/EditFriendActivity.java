package friendtracker.assignment1_s2_2017;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import friendtracker.assignment1_s2_2017.entities.Friend;
import friendtracker.assignment1_s2_2017.entities.FriendList;
import friendtracker.assignment1_s2_2017.services.ContactDataManager;
import friendtracker.assignment1_s2_2017.utility.AppUtility;

public class EditFriendActivity extends AppCompatActivity {

    protected static final int PICK_CONTACTS = 100;
    private EditText editName;
    private EditText editEmail;
    private EditText editBirthday;
    private String friendId;
    //private String getId;
    private String nameOld;
    private String emailOld;
    private String birthdayOld;
    private SimpleDateFormat sdfBirthDate = new SimpleDateFormat("dd/MM/yyyy");
    //private ArrayList<Friend> arrayListFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friend);

        Button done = (Button)findViewById(R.id.done);
        editName = (EditText) findViewById(R.id.edit_name);
        editEmail = (EditText) findViewById(R.id.edit_email);
        editBirthday = (EditText)findViewById(R.id.edit_birthday);
        editBirthday.setInputType(InputType.TYPE_NULL);
        editBirthday.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(hasFocus){
                    Calendar c = Calendar.getInstance();
                    new DatePickerDialog(EditFriendActivity.this, new DatePickerDialog.OnDateSetListener()
                    {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                        {
                            editBirthday.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

                }
            }
        });

        friendId = this.getIntent().getStringExtra("EXTRA_ID");
        if (friendId == null || friendId.equals(""))
        {
        } else
        {
            Friend f    = FriendList.getFriendById(friendId);
            nameOld     = f.getName();
            emailOld    = f.getEmail();
            birthdayOld =  sdfBirthDate.format(f.getBirthDay());

            editName.setText(nameOld);
            editEmail.setText(emailOld);
            editBirthday.setText(birthdayOld);
        }
        /*
        editBirthday.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(EditFriendActivity.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editBirthday.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        */
        done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String nameSave     = editName.getText().toString();
                String emailSave    = editEmail.getText().toString();
                String birthdaySave = editBirthday.getText().toString();

                Friend f = null;
                if (friendId == null || friendId.equals(""))
                {
                    friendId = AppUtility.generateIdForFriend();
                    f = new Friend(friendId);
                    f.setName(nameSave);
                    f.setEmail(emailSave);
                    try
                    {
                        f.setBirthDay(sdfBirthDate.parse(birthdaySave));
                    } catch (ParseException e)
                    {
                        e.printStackTrace();
                    }
                    FriendList.addFriend(f);
                }else
                {
                    f = FriendList.getFriendById(friendId);
                    f.setName(nameSave);
                    f.setEmail(emailSave);
                    try
                    {
                        f.setBirthDay(sdfBirthDate.parse(birthdaySave));
                    } catch (ParseException e)
                    {
                        e.printStackTrace();
                    }
                }
                finish();
            }
        });

        Button addFriendFromContact = (Button)findViewById(R.id.addFriendFromContract);
        addFriendFromContact.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(contactPickerIntent, PICK_CONTACTS);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_CONTACTS)
        {
            if (resultCode == RESULT_OK)
            {
                ContactDataManager contactsManager = new ContactDataManager(this, data);
                String name = "";
                String email = "";
                try {
                    name = contactsManager.getContactName();
                    email = contactsManager.getContactEmail();
                } catch (ContactDataManager.ContactQueryException e) {
                };

                  editName = (EditText) findViewById(R.id.edit_name);
                  editEmail = (EditText) findViewById(R.id.edit_email);
                  editName.setText(name);
                  editEmail.setText(email);
            }
        }
    }
}
