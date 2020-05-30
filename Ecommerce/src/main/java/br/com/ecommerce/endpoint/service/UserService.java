package br.com.ecommerce.endpoint.service;

import br.com.ecommerce.endpoint.dto.UserUpdateDTO;
import br.com.ecommerce.endpoint.entity.UserEntity;
import br.com.ecommerce.endpoint.dto.UserSaveDTO;

public interface UserService {

    public Iterable<UserEntity> listUser();

    public UserEntity saveUser(UserSaveDTO userSaveDTO);

    public UserEntity updateUser(Long id, UserUpdateDTO updateDTO);

    public UserEntity findUserById(Long id);

    public UserEntity deleteUserById(Long id);

    public UserEntity validateUserExist(Long id);

}
