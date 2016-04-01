package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.interfaces.CommentDAO;
import simonov.hotel.entity.Comment;
import simonov.hotel.services.interfaces.CommentService;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDAO commentDAO;

    @Override
    public double getAvgRatingByHotel(int hotelId) {
        return commentDAO.getAvgRatingByHotel(hotelId);
    }

    @Override
    public void save(Comment comment) {
        commentDAO.save(comment);
    }

    @Override
    public List<Comment> getCommentsByHotel(int hotelId) {
        return commentDAO.getCommentsByHotel(hotelId);
    }
}
