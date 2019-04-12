package com.github.kubenext.uaa.utils;

import com.github.kubenext.uaa.web.rest.errors.InternalServerErrorException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * @author shangjin.li
 */
public interface ResponseUtil {

    static <X>ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
        return wrapOrNotFound(maybeResponse, null);
    }

    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse, HttpHeaders headers) {
        return maybeResponse.map(response -> ResponseEntity.ok().headers(headers).body(response))
                .orElse(ResponseEntity.notFound().build());
    }

    static <X> ResponseEntity<X> createdOrFailure(Optional<X> maybeResponse, String resourceURI, HttpHeaders headers) {
        return maybeResponse.map(response -> {
            try {
                return ResponseEntity.created(new URI(resourceURI)).headers(headers).body(response);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                throw new InternalServerErrorException("Build Response location ex.");
            }
        }).orElse(ResponseEntity.unprocessableEntity().build());
    }

}
