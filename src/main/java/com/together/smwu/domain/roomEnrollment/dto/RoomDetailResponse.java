package com.together.smwu.domain.roomEnrollment.dto;

import com.together.smwu.domain.room.dto.Master;
import com.together.smwu.domain.room.dto.RoomDetailInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomDetailResponse {
    private RoomInfo room;
    private Master master;

    public RoomDetailResponse(RoomDetailInfo roomDetailInfo) {
        this.room = new RoomInfo(roomDetailInfo.getRoom());
        this.master = roomDetailInfo.getMaster();
    }
}
