package com.github.kubenext.uaa.service.mapper;

import com.github.kubenext.uaa.domain.User;
import com.github.kubenext.uaa.service.dto.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

/**
 * @author lishangjin
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * User mapping UserDTO
     * @param user
     * @return
     */
    UserDTO toUserDTO(User user);

    /**
     * UserDTO mapping User
     * @param userDTO
     * @return
     */
    User toUser(UserDTO userDTO);

    /**
     * Users mapping UserDTOs
     * @param users
     * @return
     */
    List<UserDTO> toUserDTOs(List<User> users);

    /**
     * UserDTOs mapping Users
     * @param userDTOs
     * @return
     */
    List<User> toUsers(List<UserDTO> userDTOs);

    /**
     * UserDTO update User
     * @param user
     * @param userDTO
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UserDTO userDTO);

    /**
     * Params Update User
     * @param user
     * @param firstName
     * @param lastName
     * @param email
     * @param langKey
     * @param imageUrl
     */
    void updateUser(@MappingTarget User user, String firstName, String lastName, String email, String langKey, String imageUrl);

}
