package com.github.kubenext.uaa.web.rest;

import com.github.kubenext.uaa.domain.Authority;
import com.github.kubenext.uaa.security.AuthoritiesConstants;
import com.github.kubenext.uaa.service.UserService;
import com.github.kubenext.uaa.service.dto.UserDTO;
import com.github.kubenext.uaa.utils.HeaderUtil;
import com.github.kubenext.uaa.utils.PaginationUtil;
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
@RestController
@RequestMapping("/api")
public class UserResource {

    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;


    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @PreAuthorize("hasRole(\""+ AuthoritiesConstants.ADMIN +"\")")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        logger.debug("REST request to save User : {}", userDTO);
        return createdOrFailure(userService.createUser(userDTO),"/api/users/" + userDTO.getLogin(), HeaderUtil.createAlert("userManagement.created", userDTO.getLogin()));
    }

    @PutMapping("/users")
    @PreAuthorize("hasRole(\""+ AuthoritiesConstants.ADMIN +"\")")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        logger.debug("REST request to update User : {}", userDTO);
        return wrapOrNotFound(userService.updateUser(userDTO));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
        final Page<UserDTO> page = userService.getAllManaedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


}
