package com.app.dflow.convertors;

import com.app.dflow.dto.RoomDTO;
import com.app.dflow.dto.UserDTO;
import com.app.dflow.model.Room;
import com.app.dflow.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserConvertor {
    public static UserDTO getUserDTO(User user) {
        List<RoomDTO> rooms = user.getRooms().stream()
                .map(room -> RoomConvertor.getRoomDTO(room)).collect(Collectors.toList());
        return new UserDTO(user.getId(), rooms, user.getType(), user.getUsername(), user.getPassword());
    }

    public static User getUser(UserDTO user) {
        List<Room> rooms = user.getRooms().stream()
                .map(room -> RoomConvertor.getRoom(room)).collect(Collectors.toList());
        return new User(user.getId(), rooms, user.getType(), user.getUsername(), user.getPassword());
    }
}
