package simonov.hotel.services.interfaces;

import org.springframework.stereotype.Service;
import simonov.hotel.entity.Request;
import simonov.hotel.entity.Room;

import java.time.LocalDate;
import java.util.List;

@Service
public interface RoomService {

    void saveRoom(Room room);

    List<Room> getRooms();

    boolean isFree(LocalDate start, LocalDate end, int roomId);

    Room getRoomById(int id);

    List<Room> getRoomsByHotel(int hotelId);

    List<Room> getRoomsByType(int hotelId, String type);

    List<Room> getFreeRoomsByRequest(Request request);

    void update(Room room);
}
