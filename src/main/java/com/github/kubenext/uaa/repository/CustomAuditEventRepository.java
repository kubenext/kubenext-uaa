package com.github.kubenext.uaa.repository;

import com.github.kubenext.uaa.config.AuditEventConverter;
import com.github.kubenext.uaa.config.Constants;
import com.github.kubenext.uaa.domain.PersistentAuditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shangjin.li
 */
@Repository
public class CustomAuditEventRepository implements AuditEventRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuditEventRepository.class);


    private static final String AUTHORIZATION_FAILURE = "AUTHORIZATION_FAILURE";

    protected static final int EVENT_DATA_COLUMN_MAX_LENGTH = 255;

    private final PersistenceAuditEventRepository persistenceAuditEventRepository;

    private final AuditEventConverter auditEventConverter;

    public CustomAuditEventRepository(PersistenceAuditEventRepository persistenceAuditEventRepository, AuditEventConverter auditEventConverter) {
        this.persistenceAuditEventRepository = persistenceAuditEventRepository;
        this.auditEventConverter = auditEventConverter;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void add(AuditEvent event) {
        if (!AUTHORIZATION_FAILURE.equals(event.getType())) {
            if (!Constants.ANONYMOUS_USER.equals(event.getPrincipal())) {
                PersistentAuditEvent persistentAuditEvent = new PersistentAuditEvent();
                persistentAuditEvent.setPrincipal(event.getPrincipal());
                persistentAuditEvent.setEventType(event.getType());
                persistentAuditEvent.setEventDate(event.getTimestamp());
                Map<String, String> eventData = auditEventConverter.convertDataToStrings(event.getData());
                persistentAuditEvent.setData(truncate(eventData));
                persistenceAuditEventRepository.save(persistentAuditEvent);
            }
        }
    }

    @Override
    public List<AuditEvent> find(String principal, Instant after, String type) {
        Iterable<PersistentAuditEvent> persistentAuditEvents = persistenceAuditEventRepository.findByPrincipalAndEventDateAfterAndEventType(principal, after, type);
        return auditEventConverter.convertToAuditEvent(persistentAuditEvents);
    }

    /**
     * Truncate event data that might exceed column length.
     * @param data
     * @return
     */
    private Map<String, String> truncate(Map<String, String> data) {
        Map<String, String> result = new HashMap<>();
        if (data == null) {
            return result;
        }
        data.forEach((k,v) -> {
            if (v != null) {
                int length = v.length();
                if (length > EVENT_DATA_COLUMN_MAX_LENGTH) {
                    v = v.substring(0, EVENT_DATA_COLUMN_MAX_LENGTH);
                    logger.warn(
                        "Event data for {} too long ({}) has been truncated to {}. Consider increasing column width.",
                        k, length, EVENT_DATA_COLUMN_MAX_LENGTH
                    );
                }
            }
            result.put(k, v);
        });
        return result;
    }

}
