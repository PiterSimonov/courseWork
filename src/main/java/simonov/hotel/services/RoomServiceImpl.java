package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.RoomHibernateDAO;
import simonov.hotel.entity.Request;
import simonov.hotel.entity.Room;
import simonov.hotel.services.interfaces.RoomService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomHibernateDAO roomDAO;

    @Override
    public void saveRoom(Room room) {
        roomDAO.save(room);
    }

    @Override
    public List<Room> getRooms() {
        return roomDAO.getAll();
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
    public List<Room> getRoomsByHotel(int hotelId) {
        return roomDAO.getRoomsByHotel(hotelId);
    }

    @Override
    public List<Room> getRoomsByType(int hotelId, String type) {
        return roomDAO.getRoomsByType(hotelId, type);
    }

    @Override
    public List<Room> getFreeRoomsByRequest(Request request) {
        return roomDAO.getFreeRoomsByRequest(request);
    }

    @Override
    public void update(Room room) {
        roomDAO.update(room);
    }
}
