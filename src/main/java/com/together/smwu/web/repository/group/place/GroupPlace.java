package com.together.smwu.web.repository.group.place;

import com.together.smwu.web.repository.group.Group;
import com.together.smwu.web.repository.place.Place;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Table(name = "GROUP_PLACE")
public class GroupPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_place_id")
    private long groupPlaceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Column(name = "post_time", nullable = false)
    Timestamp postTime;
}
