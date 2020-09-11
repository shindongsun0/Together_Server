package com.together.smwu.domain.roomPlace.domain;

import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.place.domain.Place;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Table(name = "ROOM_PLACE")
public class RoomPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_place_id")
    private long roomPlaceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "post_time", nullable = false)
    Timestamp postTime;
}
