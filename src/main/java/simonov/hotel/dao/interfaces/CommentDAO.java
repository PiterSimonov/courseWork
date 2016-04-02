package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Comment;

import java.util.List;

public interface CommentDAO extends GenericDAO<Comment, Integer> {
    List<Comment> getCommentsByHotel(int hotelId, int firstResult, int limit);

    double getAvgRatingByHotel(int hotelId);
}
