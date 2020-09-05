package com.together.smwu.domain.roomTag.domain;

import com.together.smwu.domain.room.domain.Room;
import com.together.smwu.domain.tag.domain.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "ROOM_TAG")
public class RoomTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_tag_id")
    private long roomTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
}
