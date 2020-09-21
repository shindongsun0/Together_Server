package com.together.smwu.domain.room.dto;

import com.together.smwu.domain.room.domain.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetailInfo {
    private Room room;
    private Master master;
}
