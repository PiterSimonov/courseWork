package simonov.hotel.services.interfaces;

import org.springframework.stereotype.Service;
import simonov.hotel.entity.Convenience;

import java.util.List;

@Service
public interface ConvenienceService {
    List<Convenience> getConveniencesByHotel(Integer hotelId);

    Convenience getConvenienceById(Integer id);

    List<Convenience> getAll();
}
