package friendtracker.assignment1_s2_2017.entities;


/**
 * Created by shawn on 6/09/2017.
 */

public class TrackerDbSchema {
    public static final class FriendTable {
        public static final String NAME = "friends";
        public static final class Cols {
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String EMAIL = "email";
            public static final String BIRTHDAY = "birthday";
            public static final String PHOTOPATH = "photoPath";
        }
    }
    public static final class MeetingTable {
        public static final String NAME = "meetings";
        public static final class Cols {
            public static final String ID = "id";
            public static final String TITLE = "title";
            public static final String STARTTIME = "startTime";
            public static final String ENDTIME = "endTime";
            public static final String FRIENDS = "friends";
            public static final String LOCATION = "location";
        }
    }
}
