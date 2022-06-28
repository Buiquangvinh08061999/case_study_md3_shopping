package com.cg.service;

import com.cg.dto.UserDTO;
import com.cg.model.User;

import java.util.List;

public interface IUserService extends IGeneralService<User> {

    List<UserDTO> findAllUserDTO();

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    boolean existsByUserName(String username);

    List<UserDTO> searchUser(String keyword);
}
