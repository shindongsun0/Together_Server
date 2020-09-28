package com.together.smwu.domain.roomPlace.domain;

import com.together.smwu.domain.place.domain.Place;
import com.together.smwu.domain.room.domain.Room;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Table(
        name = "ROOM_PLACE",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"room_id", "place_id"})}
)
public class RoomPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_place_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    private String comment;

    @CreationTimestamp
    @Column(name = "post_time", nullable = false)
    Timestamp postTime;

    @Builder
    public RoomPlace(Room room, Place place, String comment) {
        this.room = room;
        this.place = place;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public Place getPlace() {
        return place;
    }

    public String getComment() {
        return comment;
    }

    public Timestamp getPostTime() {
        return postTime;
    }
}
