package com.app.dflow.dto;

import com.app.dflow.constants.RoomStatus;
import com.app.dflow.constants.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private long id;
    private String roomIdentifier;
    private RoomStatus status;
    private RoomType type;
    private List<UserDTO> subscribers;
    private UserDTO owner;
    private int maxCapacity;
    private int actualSize;
}
