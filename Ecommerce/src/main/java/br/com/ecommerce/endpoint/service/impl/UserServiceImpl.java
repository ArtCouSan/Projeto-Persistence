package br.com.ecommerce.endpoint.service.impl;

import br.com.ecommerce.endpoint.dto.UserSaveDTO;
import br.com.ecommerce.endpoint.dto.UserUpdateDTO;
import br.com.ecommerce.endpoint.entity.UserEntity;
import br.com.ecommerce.endpoint.repository.UserRepository;
import br.com.ecommerce.endpoint.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable(value = "allUsersCache", unless = "#result.size() == 0")
    @Override
    public Iterable<UserEntity> listUser() {
        System.out.println("All users");
        return userRepository.findAll();
    }

    @Caching(
            put = {@CachePut(value = "userCache", key = "#result.id")},
            evict = {@CacheEvict(value = "allUserCache", allEntries = true)}
    )
    @Override
    public UserEntity saveUser(UserSaveDTO userSaveDTO) {
        UserEntity userEntity = userSaveDTO.parseUserEntity();
        System.out.println("Save user");
        return userRepository.save(userEntity);
    }

    @Caching(
            put = {@CachePut(value = "userCache", key = "#result.id")},
            evict = {@CacheEvict(value = "allUserCache", allEntries = true)}
    )
    @Override
    public UserEntity updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        UserEntity userEntity = validateUserExist(id);

        userEntity.setUsername(userUpdateDTO.getUsername() != null ? userUpdateDTO.getUsername() : userEntity.getUsername());
        userEntity.setPassword(userUpdateDTO.getPassword() != null ? userUpdateDTO.getPassword() : userEntity.getPassword());
        userEntity.setAddress(userUpdateDTO.getAddress() != null ? userUpdateDTO.getAddress() : userEntity.getAddress());

        System.out.println("Update user: ".concat(id.toString()));
        return userRepository.save(userEntity);
    }

    @Cacheable(value = "userCache", key = "#p0")
    @Override
    public UserEntity findUserById(Long id) {
        UserEntity userFinded = validateUserExist(id);
        System.out.println("Finded user: ".concat(id.toString()));
        return userFinded;
    }

    @Caching(
            put = {@CachePut(value = "userCache", key = "#result.id")},
            evict = {@CacheEvict(value = "allUserCache", allEntries = true)}
    )
    @Override
    public UserEntity deleteUserById(Long id) {
        UserEntity userEntity = validateUserExist(id);
        userEntity.setStatus(false);
        System.out.println("Delete user: ".concat(id.toString()));
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity validateUserExist(Long idUser) {
        Optional<UserEntity> user = this.userRepository.findById(idUser);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Client not found");
        }
    }

}
