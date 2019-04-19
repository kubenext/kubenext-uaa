package com.github.kubenext.uaa.service.mapper;

import com.github.kubenext.uaa.domain.Authority;
import com.github.kubenext.uaa.domain.User;
import com.github.kubenext.uaa.service.dto.CreateUserDTO;
import com.github.kubenext.uaa.service.dto.UpdateUserDTO;
import com.github.kubenext.uaa.service.dto.UserDTO;
import org.mapstruct.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lishangjin
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "authorities", target = "authorities", qualifiedByName = "formatAuthorityToSet")
    User toUser(CreateUserDTO createUserDTO);

    @Mapping(source = "authorities", target = "authorities", qualifiedByName = "formatAuthorityToSet")
    User toUser(UpdateUserDTO updateUserDTO);

    /**
     * User mapping UserDTO
     * @param user
     * @return
     */
    @Mapping(source = "authorities", target = "authorities", qualifiedByName = "formatAuthorityToArray")
    UserDTO toUserDTO(User user);

    /**
     * UserDTO mapping User
     * @param userDTO
     * @return
     */
    @Mapping(source = "authorities", target = "authorities", qualifiedByName = "formatAuthorityToSet")
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
    @Mapping(source = "authorities", target = "authorities", qualifiedByName = "formatAuthorityToSet")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UserDTO userDTO);


    @Mapping(source = "authorities", target = "authorities", qualifiedByName = "formatAuthorityToSet")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UpdateUserDTO updateUserDTO);

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

    @Named("formatAuthorityToSet")
    default Set<Authority> formatAuthorityToSet(String[] source) {
        if (source == null) {
            return null;
        }
        return Arrays.stream(source).map(authority -> {
            Authority auth = new Authority();
            auth.setName(authority);
            return auth;
        }).collect(Collectors.toSet());
    }

    @Named("formatAuthorityToArray")
    default String[] formatAuthorityToArray(Set<Authority> source) {
        if (source == null) {
            return null;
        }
        return source.stream().map(Authority::getName).toArray(String[]::new);
    }

}
