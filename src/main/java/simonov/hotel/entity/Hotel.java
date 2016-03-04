package simonov.hotel.entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@FilterDef(name = "RoomFilter",
        parameters = {
                @ParamDef(name = "startDate", type = "java.time.LocalDate"),
                @ParamDef(name = "endDate", type = "java.time.LocalDate")
        })
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @ManyToOne
    private City city;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private int stars;

    @OneToMany(mappedBy = "hotel")
    @Filter(name = "RoomFilter", condition = "id NOT IN (select b.room_id from Booking b " +
            "where b.startDate<=:endDate and b.endDate>=:startDate)")
    private List<Room> rooms;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "hotel")
    private List<Comment> comments;

    @Column
    private Double rating;

    @ManyToMany(mappedBy = "hotels")
    private List<Convenience> conveniences;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public List<Room> getRooms() {
        if (rooms == null) {
            rooms = new ArrayList<>();
        }
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        getRooms().add(room);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<Convenience> getConveniences() {
        return conveniences;
    }

    public void setConveniences(List<Convenience> conveniences) {
        this.conveniences = conveniences;
    }
}
