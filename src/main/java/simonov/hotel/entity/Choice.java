package simonov.hotel.entity;

import java.util.Collections;
import java.util.List;

public class Choice {

    private List<Integer> roomsIds;

    public List<Integer> getRoomsIds() {

        return roomsIds != null ? roomsIds : Collections.emptyList();
    }

    public void setRoomsIds(List<Integer> roomsIds) {
        this.roomsIds = roomsIds;
    }

}
