package com.together.smwu.domain.room.dto;

import com.together.smwu.domain.room.domain.Room;

public class RoomWithMasterInfo {
    private Room room;
    private Master master;

    public RoomWithMasterInfo(Room room, Master master) {
        this.room = room;
        this.master = master;
    }

    public RoomWithMasterInfo() {
    }

    public Room getRoom() {
        return this.room;
    }

    public Master getMaster() {
        return this.master;
    }
}
