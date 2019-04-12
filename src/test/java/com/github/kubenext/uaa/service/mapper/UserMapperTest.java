package com.github.kubenext.uaa.service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.kubenext.uaa.domain.User;
import com.github.kubenext.uaa.service.dto.UserDTO;
import com.mysql.cj.xdevapi.JsonArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shangjin.li
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testToUserDTO() {
        User user = new User();
        user.setLogin("aaaaa");
        UserDTO dto = userMapper.toUserDTO(user);
        System.out.println(dto);
    }

    @Test
    public void testToUserDTOs() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setLogin("aaaaa");
        User user2 = new User();
        user2.setLogin("bbbbb");
        users.add(user1);
        users.add(user2);
        List<UserDTO> dtos = userMapper.toUserDTOs(users);
        System.out.println(dtos.size());
    }

    @Test
    public void testUpdateUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin("bbbb");
        User user = new User();
        user.setId(123L);
        user.setLogin("aaa");
        System.out.println(user);
        userMapper.updateUser(user, userDTO);
        System.out.println(user);
    }

}
