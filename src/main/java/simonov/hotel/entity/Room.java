package simonov.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private int number;

    @Column
    private String type;

    @Column
    private String description;

    @Column
    private int price;

    @Column
    private int seats;

    @Column
    private String imageLink;

    @Column
    private boolean locked = false;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Object toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("type", type);
        obj.put("number", number);
        obj.put("seats", seats);
        obj.put("price", price);
        obj.put("imageLink", imageLink);
        obj.put("hotelId", hotel.getId());
        return obj;
    }
}
