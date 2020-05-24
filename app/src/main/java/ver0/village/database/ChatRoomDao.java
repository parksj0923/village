package ver0.village.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ChatRoomDao {
    @Query("SELECT * FROM ChatRoom")
    LiveData<List<ChatRoom>> getAll();

    @Insert
    void insert(ChatRoom chatRoom);

    @Update
    void update(ChatRoom chatRoom);

    @Delete
    void delete(ChatRoom chatRoom);

    @Query("DELETE FROM ChatRoom")
    void deleteAll();

    @Query("SELECT * FROM ChatRoom WHERE `key` LIKE :key")
    ChatRoom getChatRoom(String key);

    @Query("SELECT * FROM ChatRoom WHERE active = 1")
    List<ChatRoom> getActiveChatRooms();

    @Query("DELETE FROM ChatRoom WHERE `key` LIKE :key")
    void deleteChatRoom(String key);

    @Query("UPDATE ChatRoom SET active=:flag WHERE `key` LIKE :key")
    void setActive(String key, boolean flag);

    @Query("SELECT `key` FROM ChatRoom")
    List<String> getChatRoomKeys();

    @Query("SELECT user_name FROM ChatRoom WHERE `key` LIKE :key")
    String getUserName(String key);
}
