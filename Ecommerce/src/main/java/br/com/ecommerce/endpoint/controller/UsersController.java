package br.com.ecommerce.endpoint.controller;

import br.com.ecommerce.endpoint.dto.UserSaveDTO;
import br.com.ecommerce.endpoint.dto.UserUpdateDTO;
import br.com.ecommerce.endpoint.entity.UserEntity;
import br.com.ecommerce.endpoint.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/user")
@Api(value = "Endpoint to users")
public class UsersController {

    private UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ApiOperation(value = "List all users")
    public ResponseEntity<Iterable<UserEntity>> listUsers() {
        Iterable<UserEntity> userEntities = userService.listUser();
        return new ResponseEntity<Iterable<UserEntity>>(userEntities, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Save user")
    public ResponseEntity<UserEntity> saveUser(@RequestBody UserSaveDTO userSaveDTO) {
        UserEntity userSaved = userService.saveUser(userSaveDTO);
        return new ResponseEntity<UserEntity>(userSaved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update user by id")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        UserEntity userUpdated = userService.updateUser(id, userUpdateDTO);
        return new ResponseEntity<UserEntity>(userUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete user by id")
    public ResponseEntity<UserEntity> deleteUserById(@PathVariable Long id) {
        UserEntity userDeleted = userService.deleteUserById(id);
        return new ResponseEntity<UserEntity>(userDeleted, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find user by id")
    public ResponseEntity<UserEntity> findUserById(@PathVariable Long id) {
        UserEntity userFinded = userService.findUserById(id);
        return new ResponseEntity<UserEntity>(userFinded, HttpStatus.OK);
    }

}
