package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.interfaces.CommentDAO;
import simonov.hotel.entity.Comment;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class CommentHibernateDAO extends AbstractDAO<Comment, Integer> implements CommentDAO {

    public CommentHibernateDAO() {
        super(Comment.class);
    }

    @Override
    public List<Comment> getCommentsByHotel(int hotelId) {
        Criteria criteria = getCurrentSession().createCriteria(Comment.class);
        criteria.add(Restrictions.eq("hotel.id", hotelId));
        return criteria.list();
    }

}
