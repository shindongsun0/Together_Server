package com.together.smwu.domain.room.domain;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name = "TAG")
public class Tag {

    public Tag() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Builder
    public Tag(final String name, final Room room){
        this.name = name;
        this.room = room;
    }

    public String getName(){
        return name;
    }

    public Room getRoom(){
        return room;
    }

    public Long getId(){
        return id;
    }

}
