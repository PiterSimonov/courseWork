package simonov.hotel.entity;

import java.time.LocalDate;
import java.util.Map;

public class Request {

    private int countryId;
    private int cityId;
    private int hotelId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Map<Integer, Integer> seats;
    private int stars;
    private int firstResult;
    private int limit;
    private RoomSort roomSort = RoomSort.SeatsAsc;
    private int roomsFirstResult;
    private int roomsLimit = 5;
    private int roomHotelId;




    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Map<Integer, Integer> getSeats() {
        return seats;
    }

    public void setSeats(Map<Integer, Integer> seats) {
        this.seats = seats;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public RoomSort getRoomSort() {
        return roomSort;
    }

    public void setRoomSort(RoomSort roomSort) {
        this.roomSort = roomSort;
    }

    public int getRoomsFirstResult() {
        return roomsFirstResult;
    }

    public void setRoomsFirstResult(int roomsFirstResult) {
        this.roomsFirstResult = roomsFirstResult;
    }

    public int getRoomsLimit() {
        return roomsLimit;
    }

    public void setRoomsLimit(int roomsLimit) {
        this.roomsLimit = roomsLimit;
    }

    public int getRoomHotelId() {
        return roomHotelId;
    }

    public void setRoomHotelId(int roomHotelId) {

        this.roomHotelId = roomHotelId;
    }
}
