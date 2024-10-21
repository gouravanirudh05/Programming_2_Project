package com.operatoroverloaded.hotel.stores.room;

import com.hotelmanagement.models.Room;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("in-memory")
public class InMemoryRoomStore implements RoomStore, Serializable {
    private static final long serialVersionUID = 1L;
    private final List<Room> rooms = new ArrayList<>();

    @Override
    public void addRoom(Room room) {
        rooms.add(room);
    }
    @Override
    public List<Room> getRooms() {
        return rooms;
    }
    @Override
    public Room deleteRoom(int roomId) {
        return rooms.removeIf(room -> room.getId() == roomId);
    }
    @Override
    public void updateRoom(int roomId, Room room) {
        rooms.removeIf(room -> room.getId() == roomId);
        rooms.add(room);
    }
    @Override
    public void findRoom(int roomId) {
        rooms.stream().filter(room -> room.getId() == roomId).findFirst();
    }
    @Override
    private void saveToFile(List<Room> list) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream("Room.tmp");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(list);
        }catch(IOException e){
            System.err.println("Error while saving data"+"(Room.tmp)"+": " + e.getMessage());
        }
    }
}
