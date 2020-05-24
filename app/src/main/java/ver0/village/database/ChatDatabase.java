package ver0.village.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ChatData.class, ChatRoom.class}, version = 1)
public abstract class ChatDatabase extends RoomDatabase {
    private static ChatDatabase INSTANCE;
    public abstract ChatDataDao chatDataDao();
    public abstract ChatRoomDao chatRoomDao();

    public static ChatDatabase getAppDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context, ChatDatabase.class, "ChatDatabase")
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}

