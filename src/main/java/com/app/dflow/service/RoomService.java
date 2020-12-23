package com.app.dflow.service;

import com.app.dflow.convertors.RoomConvertor;
import com.app.dflow.dto.RoomDTO;
import com.app.dflow.object.Room;
import com.app.dflow.repository.RoomRepository;
import com.app.dflow.template.IRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoomService implements IRoomService {

    private static final Logger LOG = LoggerFactory.getLogger(RoomService.class);

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public ResponseEntity<RoomDTO> getRoomById(long id) {
        try {
            Room room = roomRepository.findById(id).orElse(null);
            if (room == null) {
                LOG.warn("Room with id {} not found.", id);
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            RoomDTO roomDTO = RoomConvertor.getRoomDTO(room);
            return new ResponseEntity<>(roomDTO, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error while get the room.", e);
        }
        LOG.warn("Bad request on get room with id: {}.", id);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Boolean> removeRoom(long id) {
        try {
            roomRepository.deleteById(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error while remove the room {}.", id, e);
        }
        LOG.warn("Bad request on remove room with id: {}.", id);
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Boolean> addRoom(RoomDTO roomDTO) {
        try {
            Room room = RoomConvertor.getRoom(roomDTO);
            roomRepository.save(room);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error while adding the room.", e);
        }
        LOG.warn("Bad request on adding room with id: {}.", roomDTO.getId());
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Boolean> updateRoom(RoomDTO roomDTO) {
        try {
            Room room = RoomConvertor.getRoom(roomDTO);
            roomRepository.saveAndFlush(room);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error while adding the room.", e);
        }
        LOG.warn("Bad request on adding room with id: {}.", roomDTO.getId());
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
}
