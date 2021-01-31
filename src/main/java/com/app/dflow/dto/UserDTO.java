package com.app.dflow.dto;

import com.app.dflow.constants.AuthProvider;
import com.app.dflow.constants.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private long id;
    private List<RoomDTO> rooms;
    private UserType type;
    private String username;
    private String password;
    private AuthProvider auth_provider;
}
