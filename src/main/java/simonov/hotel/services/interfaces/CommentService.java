package simonov.hotel.services.interfaces;

import org.springframework.stereotype.Service;
import simonov.hotel.entity.Comment;

import java.util.List;

@Service
public interface CommentService {
    void save(Comment comment);

    List<Comment> getCommentsByHotel(int hotelId, int firstResult, int limit);

    double getAvgRatingByHotel(int hotelId);
}
