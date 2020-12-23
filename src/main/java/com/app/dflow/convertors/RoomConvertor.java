package com.app.dflow.convertors;

import com.app.dflow.dto.RoomDTO;
import com.app.dflow.dto.UserDTO;
import com.app.dflow.object.Room;
import com.app.dflow.object.User;

import java.util.List;
import java.util.stream.Collectors;

public class RoomConvertor {
    public static RoomDTO getRoomDTO(Room room) {
       List<UserDTO> subscribers = room.getSubscribers().stream()
               .map(user -> UserConvertor.getUserDTO(user)).collect(Collectors.toList());

       UserDTO owner = UserConvertor.getUserDTO(room.getOwner());

        return new RoomDTO(room.getId(), room.getRoomIdentifier(),
                room.getStatus(), room.getType(), subscribers,
                owner, room.getMaxCapacity(), room.getActualSize());
    }

    public static Room getRoom(RoomDTO room) {
        List<User> subscribers = room.getSubscribers().stream()
                .map(user -> UserConvertor.getUser(user)).collect(Collectors.toList());

        User owner = UserConvertor.getUser(room.getOwner());

        return new Room(room.getId(), room.getRoomIdentifier(),
                room.getStatus(), room.getType(), subscribers,
                owner, room.getMaxCapacity(), room.getActualSize());
    }
}