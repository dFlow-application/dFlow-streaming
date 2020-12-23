package com.app.dflow.template;

import com.app.dflow.dto.RoomDTO;
import org.springframework.http.ResponseEntity;

public interface IRoomService {
    ResponseEntity<RoomDTO> getRoomById(long id);
    ResponseEntity<Boolean> removeRoom(long id);
    ResponseEntity<Boolean> addRoom(RoomDTO room);
    ResponseEntity<Boolean> updateRoom(RoomDTO room);
}
