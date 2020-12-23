package com.app.dflow.template;

import com.app.dflow.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    ResponseEntity<UserDTO> getUserById(long id);
    ResponseEntity<Boolean> removeUser(long id);
    ResponseEntity<Boolean> addUser(UserDTO userDTO);
    ResponseEntity<Boolean> updateUser(UserDTO userDTO);
}
