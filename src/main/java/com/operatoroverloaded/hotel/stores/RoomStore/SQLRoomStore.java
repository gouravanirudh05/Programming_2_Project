package com.operatoroverloaded.hotel.stores.room;

import com.hotelmanagement.models.Room;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("sql")
public class SQLRoomStore implements RoomStore {

    private final JdbcTemplate jdbcTemplate;

    public SQLRoomStore(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addRoom(Room room) {
        String sql = "INSERT INTO rooms (room_number, room_type) VALUES (?, ?)";
        jdbcTemplate.update(sql, room.getRoomNumber(), room.getRoomType());
    }

    @Override
    public List<Room> getRooms() {
        String sql = "SELECT * FROM rooms";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Room(
            rs.getInt("room_number"),
            rs.getString("room_type")
        ));
    }

    @Override
    public Room getRoomByNumber(int roomNumber) {
        String sql = "SELECT * FROM rooms WHERE room_number = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{roomNumber}, (rs, rowNum) -> 
            new Room(rs.getInt("room_number"), rs.getString("room_type"))
        );
    }

    @Override
    public void deleteRoom(int roomNumber) {
        String sql = "DELETE FROM rooms WHERE room_number = ?";
        jdbcTemplate.update(sql, roomNumber);
    }

    @Override
    public void saveAll() {
        // No-op for SQL since the data is persisted in the database
    }
}