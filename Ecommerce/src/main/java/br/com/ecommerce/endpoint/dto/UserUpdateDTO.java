package br.com.ecommerce.endpoint.dto;

import br.com.ecommerce.endpoint.entity.UserEntity;

public class UserUpdateDTO {

    private String username;
    private String password;
    private String address;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserEntity parseUserEntity(){
        UserEntity user = new UserEntity();
        user.setUsername(this.getUsername());
        user.setPassword(this.getPassword());
        user.setAddress(this.getAddress());
        return user;
    }

    public UserUpdateDTO() {
    }
}
