package com.operatoroverloaded.hotel.stores.roomstore;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.operatoroverloaded.hotel.models.Room;

@Component
@Profile("sql")
public class SQLRoomStore implements RoomStore {
    private static final SQLRoomStore instance = new SQLRoomStore(new JdbcTemplate());

    public static SQLRoomStore getInstance() {
        return instance;
    }

    private final JdbcTemplate jdbcTemplate;

    public SQLRoomStore(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addRoom(Room room) {
        String sql = "INSERT INTO rooms (room_number, room_type) VALUES (?, ?)";
        jdbcTemplate.update(sql, room.getId(), room.getRoomType());
    }

    @Override
    public List<Room> getRooms() {
        String sql = "SELECT * FROM rooms";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Room(
            // rs.getInt("room_number"),
            // rs.getString("room_type")
        ));
    }
    
    @Override
    public Room deleteRoom(int roomNumber) {
        String sql = "DELETE FROM rooms WHERE room_number = ?";
        jdbcTemplate.update(sql, roomNumber);
        return new Room();
    }

    @Override
    public Room findRoom(int roomNumber) {
        String sql = "SELECT * FROM rooms WHERE room_number = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{roomNumber}, (rs, rowNum) -> 
            // rs.getInt("room_number"), rs.getString("room_type")
            new Room()
        );
    }

    @Override
    public void saveToFile() {
        
    }
    @Override
    public void updateRoom(int roomId, Room room) {
        String sql = "UPDATE rooms SET room_number = ?, room_type = ? WHERE room_number = ?";
        jdbcTemplate.update(sql, room.getId(), room.getRoomType(), roomId);
    }
    @Override
    public void loadFromFile(){
        // NOT NEEDED.
    };
}