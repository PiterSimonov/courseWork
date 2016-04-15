package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.RoomHibernateDAO;
import simonov.hotel.dao.interfaces.HotelDAO;
import simonov.hotel.entity.Hotel;
import simonov.hotel.entity.Room;
import simonov.hotel.entity.SearchRequest;
import simonov.hotel.services.interfaces.RoomService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomHibernateDAO roomDAO;
    @Autowired
    HotelDAO hotelDAO;

    @Override
    public void saveRoom(Room room) {
        Hotel hotel = hotelDAO.get(room.getHotel().getId());
        room.setHotel(hotel);
        roomDAO.save(room);
    }

    @Override
    public boolean isFree(LocalDate start, LocalDate end, int roomId) {
        return roomDAO.isFree(start, end, roomId);
    }

    @Override
    public Room getRoomById(int id) {
        return roomDAO.get(id);
    }

    @Override
    public List<Room> getRoomsByHotel(int hotelId, int firstRooms, int limit) {
        return roomDAO.getRoomsByHotel(hotelId, firstRooms, limit);
    }

    @Override
    public List<Room> getFreeRoomsByRequest(SearchRequest searchRequest) {
        return roomDAO.getFreeRoomsByRequest(searchRequest);
    }

    @Override
    public boolean roomNumberIsFree(int number, int hotelId) {
        return roomDAO.roomNumberIsFree(number, hotelId);
    }

    @Override
    public void update(Room room) {
        roomDAO.update(room);
    }
}
