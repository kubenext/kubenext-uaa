package com.github.kubenext.uaa.web.rest;

import com.github.kubenext.uaa.security.AuthoritiesConstants;
import com.github.kubenext.uaa.service.UserService;
import com.github.kubenext.uaa.service.dto.CreateUserDTO;
import com.github.kubenext.uaa.service.dto.UpdateUserDTO;
import com.github.kubenext.uaa.service.dto.UserDTO;
import com.github.kubenext.uaa.utils.HeaderUtil;
import com.github.kubenext.uaa.utils.PaginationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

import static com.github.kubenext.uaa.utils.ResponseUtil.createdOrFailure;
import static com.github.kubenext.uaa.utils.ResponseUtil.wrapOrNotFound;

/**
 * @author shangjin.li
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api")
public class UserResource {

    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;


    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "创建用户")
    @PostMapping("/users")
    @PreAuthorize("hasRole(\""+ AuthoritiesConstants.ADMIN +"\")")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) throws URISyntaxException {
        logger.debug("REST request to save User : {}", createUserDTO);
        return createdOrFailure(userService.createUser(createUserDTO),"/api/users/" + createUserDTO.getLogin(), HeaderUtil.createAlert("userManagement.created", createUserDTO.getLogin()));
    }

    @PutMapping("/users")
    @PreAuthorize("hasRole(\""+ AuthoritiesConstants.ADMIN +"\")")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UpdateUserDTO updateUserDTO) {
        logger.debug("REST request to update User : {}", updateUserDTO);
        return wrapOrNotFound(userService.updateUser(updateUserDTO));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
        final Page<UserDTO> page = userService.getAllManaedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}