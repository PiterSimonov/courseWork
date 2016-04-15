package simonov.hotel.services.interfaces;

import org.springframework.stereotype.Service;
import simonov.hotel.entity.Room;
import simonov.hotel.entity.SearchRequest;

import java.time.LocalDate;
import java.util.List;

@Service
public interface RoomService {

    void saveRoom(Room room);

    boolean isFree(LocalDate start, LocalDate end, int roomId);

    Room getRoomById(int id);

    List<Room> getRoomsByHotel(int hotelId, int firstRooms, int limit);

    List<Room> getFreeRoomsByRequest(SearchRequest searchRequest);

    void update(Room room);

    boolean roomNumberIsFree(int number, int hotelId);
}
