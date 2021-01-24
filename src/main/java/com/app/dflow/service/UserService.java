package com.app.dflow.service;

import com.app.dflow.convertors.UserConvertor;
import com.app.dflow.dto.UserDTO;
import com.app.dflow.model.User;
import com.app.dflow.repository.UserRepository;
import com.app.dflow.template.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<UserDTO> getUserById(long id) {
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user == null) {
                LOG.warn("User with id {} not found.", id);
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            UserDTO userDTO = UserConvertor.getUserDTO(user);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error while get the user.", e);
        }
        LOG.warn("Bad request on get user with id: {}.", id);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Boolean> removeUser(long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error while remove the user {}.", id, e);
        }
        LOG.warn("Bad request on remove user with id: {}.", id);
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Boolean> addUser(UserDTO userDTO) {
        try {
            User user = UserConvertor.getUser(userDTO);
            userRepository.save(user);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error while adding the user.", e);
        }
        LOG.warn("Bad request on adding user with id: {}.", userDTO.getId());
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Boolean> updateUser(UserDTO userDTO) {
        try {
            User user = UserConvertor.getUser(userDTO);
            userRepository.saveAndFlush(user);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            LOG.error("Error while adding the user.", e);
        }
        LOG.warn("Bad request on adding user with id: {}.", userDTO.getId());
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

}
