package com.github.kubenext.uaa.config;

import com.github.kubenext.uaa.domain.PersistentAuditEvent;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author shangjin.li
 */
@Component
public class AuditEventConverter {

    public List<AuditEvent> convertToAuditEvent(Iterable<PersistentAuditEvent> persistentAuditEvents) {
        if (persistentAuditEvents == null) {
            return Collections.emptyList();
        }
        List<AuditEvent> auditEvents = new ArrayList<>();
        persistentAuditEvents.forEach(persistentAuditEvent -> auditEvents.add(convertToAuditEvent(persistentAuditEvent)));
        return auditEvents;
    }

    public AuditEvent convertToAuditEvent(PersistentAuditEvent persistentAuditEvent) {
        if (persistentAuditEvent == null) {
            return null;
        }
        return new AuditEvent(
            persistentAuditEvent.getEventDate(),
            persistentAuditEvent.getPrincipal(),
            persistentAuditEvent.getEventType(),
            convertDataToObjects(persistentAuditEvent.getData())
        );
    }

    public Map<String, Object> convertDataToObjects(Map<String, String> data) {
        Map<String, Object> results = new HashMap<>(data.size());
        if (data != null) {
            data.forEach((k,v) -> {
                results.put(k, v);
            });
        }
        return results;
    }

    public Map<String, String> convertDataToStrings(Map<String, Object> data) {
        Map<String, String> results = new HashMap<>(data.size());
        if (data != null) {
            data.forEach((k,v) -> {
                if (v instanceof WebAuthenticationDetails) {
                    WebAuthenticationDetails authenticationDetails = (WebAuthenticationDetails) v;
                    results.put("remoteAddress", authenticationDetails.getRemoteAddress());
                    results.put("sessionId", authenticationDetails.getSessionId());
                } else {
                    results.put(k, Objects.toString(v));
                }
            });
        }
        return results;
    }

}
