package com.app.dflow.controller;

import com.app.dflow.dto.RoomDTO;
import com.app.dflow.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
@CrossOrigin(origins = "*")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/get")
    public ResponseEntity<RoomDTO> getRoomById(long id) {
        return roomService.getRoomById(id);
    }

    @GetMapping("/remove")
    public ResponseEntity<Boolean> removeRoom(long id) {
        return roomService.removeRoom(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addRoom(@RequestBody RoomDTO roomDTO) {
        return roomService.addRoom(roomDTO);
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateRoom(@RequestBody RoomDTO roomDTO) {
        return roomService.updateRoom(roomDTO);
    }

}
