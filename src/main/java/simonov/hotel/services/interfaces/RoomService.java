package simonov.hotel.services.interfaces;

import org.springframework.stereotype.Service;
import simonov.hotel.entity.Room;

import java.time.LocalDate;
import java.util.List;

@Service
public interface RoomService {

    void saveRoom(Room room);
    boolean isFree(LocalDate start, LocalDate end, int roomId);
    Room getRoomById(int id);
    List<Room> getRoomsByHotel(int hotelId);
}
