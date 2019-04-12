package com.github.kubenext.uaa.utils;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author shangjin.li
 */
public final class PaginationUtil {

    public static <T> HttpHeaders generatePaginationHttpHeaders(Page<T> page, String baseUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Long.toString(page.getTotalElements()));
        StringBuilder link = new StringBuilder();
        if ((page.getNumber() + 1) < page.getTotalPages()) {
            link.append("<").append(generateUri(baseUrl,page.getNumber() + 1, page.getSize())).append(">; rel=\"next\",");
        }
        if ((page.getNumber()) > 0) {
            link.append("<").append(generateUri(baseUrl, page.getNumber() -1, page.getSize())).append(">; rel=\"prev\",");
        }
        int lastPage = 0;
        if (page.getTotalPages() > 0) {
            lastPage = page.getTotalPages() - 1;
        }
        link.append("<").append(generateUri(baseUrl, lastPage, page.getSize())).append(">; rel=\"last\",");
        link.append("<").append(generateUri(baseUrl, 0, page.getSize())).append(">; rel=\"first\"");
        headers.add(HttpHeaders.LINK, link.toString());
        return headers;
    }

    private static String generateUri(String baseUrl, int page, int size) {
        return UriComponentsBuilder.fromUriString(baseUrl).queryParam("page", page).queryParam("size", size).toUriString();
    }



}
