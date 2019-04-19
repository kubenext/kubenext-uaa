package com.github.kubenext.uaa.service;

import com.github.kubenext.uaa.domain.Client;
import com.github.kubenext.uaa.repository.ClientRepository;
import com.github.kubenext.uaa.service.dto.ClientDTO;
import com.github.kubenext.uaa.service.mapper.ClientMapper;
import com.github.kubenext.uaa.utils.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * @author shangjin.li
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final CacheManager cacheManager;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper, CacheManager cacheManager) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.cacheManager = cacheManager;
    }

    /**
     * 新建
     * @param clientDTO
     * @return
     */
    public Optional<ClientDTO> create(ClientDTO clientDTO) {
        Client client = clientMapper.toClient(clientDTO);
        client.setClientId(RandomUtil.generateResetKey());
        client.setClientSecret(RandomUtil.generatePassword());
        clientRepository.save(client);
        this.clearClientCaches(client);
        logger.debug("Created Information for Client: {}", client);
        return Optional.ofNullable(client).map(clientMapper::toClientDTO);
    }

    /**
     * 更新
     * @param clientDTO
     * @return
     */
    public Optional<ClientDTO> update(ClientDTO clientDTO) {
        return Optional.of(clientRepository.findById(clientDTO.getName()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(client -> {
                    clientMapper.updateClient(client, clientDTO);
                    this.clearClientCaches(client);
                    return clientMapper.toClientDTO(client);
                });
    }

    /**
     * 删除
     * @param clientId
     */
    public void delete(String clientId) {
        clientRepository.findOneByClientId(clientId).ifPresent(client -> {
            clientRepository.delete(client);
            this.clearClientCaches(client);
        });
    }

    /**
     * 获取
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public Page<ClientDTO> getAll(Pageable pageable) {
        return clientRepository.findAll(pageable).map(clientMapper::toClientDTO);
    }


    /**
     * 清理缓存
     * @param client
     */
    private void clearClientCaches(Client client) {
        Objects.requireNonNull(cacheManager.getCache(ClientRepository.CLIENTS_BY_CLIENT_ID_CACHE)).evict(client.getClientId());
    }

}
