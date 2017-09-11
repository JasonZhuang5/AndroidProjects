package friendtracker.assignment1_s2_2017.entities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shawn on 6/09/2017.
 */

public class TrackerBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "trackerBase.db";

    public TrackerBaseHelper(Context context){
        super(context,DATABASE_NAME, null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TrackerDbSchema.FriendTable.NAME + "(" +
                " _id integer primary key autoincrement," +
                TrackerDbSchema.FriendTable.Cols.ID + "," +
                TrackerDbSchema.FriendTable.Cols.NAME + "," +
                TrackerDbSchema.FriendTable.Cols.EMAIL + "," +
                TrackerDbSchema.FriendTable.Cols.BIRTHDAY + "," +
                TrackerDbSchema.FriendTable.Cols.PHOTOPATH +
                ")"
        );
        db.execSQL("create table " + TrackerDbSchema.MeetingTable.NAME + "(" +
                " _id integer primary key autoincrement," +
                TrackerDbSchema.MeetingTable.Cols.ID + "," +
                TrackerDbSchema.MeetingTable.Cols.TITLE + "," +
                TrackerDbSchema.MeetingTable.Cols.STARTTIME + "," +
                TrackerDbSchema.MeetingTable.Cols.ENDTIME + "," +
                TrackerDbSchema.MeetingTable.Cols.FRIENDS + "," +
                TrackerDbSchema.MeetingTable.Cols.LOCATION +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}