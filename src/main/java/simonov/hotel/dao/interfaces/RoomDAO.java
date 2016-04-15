package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Room;
import simonov.hotel.entity.SearchRequest;

import java.time.LocalDate;
import java.util.List;

public interface RoomDAO extends GenericDAO<Room, Integer> {
    boolean isFree(LocalDate start, LocalDate end, int roomId);

    boolean setLock(int id);

    void unlock(int id);

    List<Room> getRoomsByHotel(int hotelId, int firstRooms, int limit);

    List<Room> getFreeRoomsByRequest(SearchRequest searchRequest);

    boolean roomNumberIsFree(int number, int hotelId);
}
