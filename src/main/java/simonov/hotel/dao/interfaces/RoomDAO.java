package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Request;
import simonov.hotel.entity.Room;

import java.time.LocalDate;
import java.util.List;

public interface RoomDAO extends GenericDAO<Room, Integer> {
    boolean isFree(LocalDate start, LocalDate end, int roomId);

    boolean setLock(int id);

    void unlock(int id);

    List<Room> getRoomsByHotel(int hotelId);

    List<Room> getFreeRoomsByRequest(Request request);

    boolean roomNumberIsFree(int number, int hotelId);
}
