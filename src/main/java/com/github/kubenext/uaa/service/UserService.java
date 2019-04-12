package com.github.kubenext.uaa.service;

import com.github.kubenext.uaa.config.Constants;
import com.github.kubenext.uaa.domain.Authority;
import com.github.kubenext.uaa.domain.User;
import com.github.kubenext.uaa.repository.AuthorityRepository;
import com.github.kubenext.uaa.repository.UserRepository;
import com.github.kubenext.uaa.security.AuthoritiesConstants;
import com.github.kubenext.uaa.security.SecurityUtils;
import com.github.kubenext.uaa.service.dto.UserDTO;
import com.github.kubenext.uaa.service.mapper.UserMapper;
import com.github.kubenext.uaa.utils.RandomUtil;
import com.github.kubenext.uaa.web.rest.errors.BadRequestAlertException;
import com.github.kubenext.uaa.web.rest.errors.EmailAlreadyUsedException;
import com.github.kubenext.uaa.web.rest.errors.InvalidPasswordException;
import com.github.kubenext.uaa.web.rest.errors.LoginAlreadyUsedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shangjin.li
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final CacheManager cacheManager;
    private final UserMapper userMapper;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, CacheManager cacheManager, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
        this.userMapper = userMapper;
    }

    /**
     * 激活用户
     * @param key
     * @return
     */
    public Optional<UserDTO> activateRegistration(String key) {
        logger.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
                .map(user -> {
                    user.setActivated(true);
                    user.setActivationKey(null);
                    this.clearUserCaches(user);
                    logger.debug("Activated user: {}", user);
                    return user;
                }).map(userMapper::toUserDTO);
    }

    /**
     * 重置密码验证
     * @param newPassword
     * @param key
     * @return
     */
    public Optional<UserDTO> completePasswordReset(String newPassword, String key) {
        logger.debug("Reset user password for reset key {}", key);
        return userRepository.findOneByResetKey(key)
                .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetKey(null);
                    user.setResetDate(null);
                    this.clearUserCaches(user);
                    return user;
                }).map(userMapper::toUserDTO);
    }

    /**
     * 通过Email请求重置密码
     * @param mail
     * @return
     */
    public Optional<UserDTO> requestPasswordReset(String mail) {
        return userRepository.findOneByEmailIgnoreCase(mail)
                .filter(User::isActivated)
                .map(user -> {
                    user.setResetKey(RandomUtil.generateResetKey());
                    user.setResetDate(Instant.now());
                    this.clearUserCaches(user);
                    return user;
                }).map(userMapper::toUserDTO);
    }

    /**
     * 注册用户
     * @param userDTO
     * @param password
     * @return
     */
    public Optional<UserDTO> registerUser(UserDTO userDTO, String password) {
        userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).ifPresent(user -> {
            boolean removed = removeNonActivatedUser(user);
            if (!removed) {
                throw new LoginAlreadyUsedException();
            }
        });
        userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(user -> {
            boolean removed = removeNonActivatedUser(user);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });
        User newUser = userMapper.toUser(userDTO);
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encryptedPassword);
        newUser.setActivated(false);
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        userRepository.save(newUser);
        this.clearUserCaches(newUser);
        logger.debug("Created Information for User: {}", newUser);
        return Optional.ofNullable(userMapper.toUserDTO(newUser));
    }

    /**
     * 创建用户
     * @param userDTO
     * @return
     */
    public Optional<UserDTO> createUser(UserDTO userDTO) {
        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
        }
        User user = userMapper.toUser(userDTO);
        if (user.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE);
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        if (!CollectionUtils.isEmpty(user.getAuthorities())) {
            Set<Authority> authorities = user.getAuthorities().stream()
                    .map(authority -> authorityRepository.findById(authority.getName()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }
        userRepository.save(user);
        this.clearUserCaches(user);
        logger.debug("Created Information for User: {}", user);
        return Optional.ofNullable(userMapper.toUserDTO(user));
    }

    /**
     * 修改用户信息
     * @param firstName
     * @param lastName
     * @param email
     * @param langKey
     * @param imageUrl
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByLogin)
                .ifPresent(user -> {
                    userMapper.updateUser(user, firstName, lastName, email, langKey, imageUrl);
                    this.clearUserCaches(user);
                    logger.debug("Changed Infomation for User: {}", user);
                });
    }

    /**
     * 修改用户所有信息
     * @param userDTO
     * @return
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository.findById(userDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    this.clearUserCaches(user);
                    userMapper.updateUser(user, userDTO);
                    Set<Authority> authorities = user.getAuthorities();
                    authorities.clear();
                    userDTO.getAuthorities().stream()
                            .map(authority -> authorityRepository.findById(authority.getName()))
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .forEach(authorities::add);
                    this.clearUserCaches(user);
                    logger.debug("Changed Information for User: {}", user);
                    return userMapper.toUserDTO(user);
                });
    }

    /**
     * 删除用户
     * @param login
     */
    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            this.clearUserCaches(user);
            logger.debug("Deleted User: {}", user);
        });
    }

    /**
     * 修改密码
     * @param currentClearTextPassword
     * @param newPassword
     */
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByLogin)
                .ifPresent(user -> {
                    String currentEncryptedPassword = user.getPassword();
                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                        throw new InvalidPasswordException();
                    }
                    String encryptedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encryptedPassword);
                    this.clearUserCaches(user);
                    logger.debug("Changed password for User: {}", user);
                });
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Page<UserDTO> getAllManaedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(userMapper::toUserDTO);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Optional<UserDTO> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login).map(userMapper::toUserDTO);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Optional<UserDTO> getUserWithAuthoritiesById(Long id) {
        return userRepository.findOneWithAuthoritiesById(id).map(userMapper::toUserDTO);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Optional<UserDTO> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin).map(userMapper::toUserDTO);
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
                .findAllByActivatedIsFalseAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
                .forEach(user -> {
                    logger.debug("Deleting not activated user {}", user.getLogin());
                    userRepository.delete(user);
                    this.clearUserCaches(user);
                });
    }

    /**
     * 获取权限
     * @return
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    /**
     * 删除没有激活的用户
     * @param user
     * @return
     */
    private boolean removeNonActivatedUser(User user) {
        if (user.isActivated()) {
            return false;
        }
        userRepository.delete(user);
        userRepository.flush();
        this.clearUserCaches(user);
        return true;
    }

    /**
     * 清理用户缓存
     * @param user
     */
    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
    }

}
