package ver0.village.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChatDataDao {
    @Query("SELECT * FROM ChatData")
    LiveData<List<ChatData>> getAll();

    @Insert
    void insert(ChatData chatData);

    @Update
    void update(ChatData chatData);

    @Delete
    void delete(ChatData chatData);

    @Query("DELETE FROM ChatData")
    void deleteAll();

    @Query("SELECT * FROM ChatData WHERE room_id=:room_id ORDER BY datetime ASC")
    List<ChatData> getRoomChat(int room_id);

    @Query("SELECT * FROM ChatData WHERE room_id=:room_id ORDER BY datetime DESC LIMIT 1")
    ChatData getLatestChat(int room_id);

    @Query("DELETE FROM ChatData WHERE room_id=:room_id")
    void deleteRoomChat(int room_id);

    @Query("SELECT COUNT(*) FROM ChatData WHERE room_id=:room_id and read=0")
    int getUnreadChatNumber(int room_id);
}
