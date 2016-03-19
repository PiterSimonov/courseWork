package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.interfaces.ConvenienceDAO;
import simonov.hotel.entity.Convenience;
import simonov.hotel.services.interfaces.ConvenienceService;

import java.util.List;

@Service
@Transactional
public class ConvenienceServiceImpl implements ConvenienceService {

    @Autowired
    ConvenienceDAO convenienceDAO;

    @Override
    public List<Convenience> getConveniencesByHotel(Integer hotelId) {
        return convenienceDAO.getConveniencesByHotel(hotelId);
    }

    @Override
    public Convenience getConvenienceById(Integer id) {
        return convenienceDAO.get(id);
    }

    @Override
    public List<Convenience> getAll() {
        return convenienceDAO.getAll();
    }
}
