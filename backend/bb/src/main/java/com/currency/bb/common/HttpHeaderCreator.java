package com.currency.bb.common;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class HttpHeaderCreator {
     public HttpHeaders okResponseHeader( HttpServletRequest request, String requestType ){
        // getting current timestamp in String
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTimestampInString = now.format(formatter);

        // getting url of the caller
        String requestUrl = request.getRequestURL().toString();

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.put("STATUS_MESSAGE", Collections.singletonList("1")                   );
        responseHeader.put("RECORD_DATE", Collections.singletonList(currentTimestampInString)   );
        responseHeader.put("HTTP_STATUS", Collections.singletonList(HttpStatus.OK.toString())   );

        responseHeader.put("REQUEST_TYPE", Collections.singletonList(requestType)  );
        responseHeader.put("USER", Collections.singletonList(requestUrl)                        );

        return responseHeader;
    }

    public HttpHeaders createdResponseHeader( HttpServletRequest request, String requestType ){
        // getting current timestamp in String
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTimestampInString = now.format(formatter);

        // getting url of the caller
        String requestUrl = request.getRequestURL().toString();

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.put("STATUS_MESSAGE", Collections.singletonList("1")                   );
        responseHeader.put("RECORD_DATE", Collections.singletonList(currentTimestampInString)   );
        responseHeader.put("HTTP_STATUS", Collections.singletonList(HttpStatus.CREATED.toString())   );

        responseHeader.put("REQUEST_TYPE", Collections.singletonList(requestType)  );
        responseHeader.put("USER", Collections.singletonList(requestUrl)                        );

        return responseHeader;
    }

    // if the insert is already exists
    public HttpHeaders conflictResponseHeader( HttpServletRequest request, String requestType ){
        // getting current timestamp in String
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTimestampInString = now.format(formatter);

        // getting url of the caller
        String requestUrl = request.getRequestURL().toString();

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.put("STATUS_MESSAGE", Collections.singletonList("0")                   );
        responseHeader.put("RECORD_DATE", Collections.singletonList(currentTimestampInString)   );
        responseHeader.put("HTTP_STATUS", Collections.singletonList(HttpStatus.CONFLICT.toString())   );

        responseHeader.put("REQUEST_TYPE", Collections.singletonList(requestType)  );
        responseHeader.put("USER", Collections.singletonList(requestUrl)                        );

        return responseHeader;
    }

    public HttpHeaders notFoundResponseHeader( HttpServletRequest request, String requestType ){
        // getting current timestamp in String
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTimestampInString = now.format(formatter);

        // getting url of the caller
        String requestUrl = request.getRequestURL().toString();

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.put("STATUS_MESSAGE", Collections.singletonList("0")                   );
        responseHeader.put("RECORD_DATE", Collections.singletonList(currentTimestampInString)   );
        responseHeader.put("HTTP_STATUS", Collections.singletonList(HttpStatus.NOT_FOUND.toString())   );

        responseHeader.put("REQUEST_TYPE", Collections.singletonList(requestType)  );
        responseHeader.put("USER", Collections.singletonList(requestUrl));

        return responseHeader;
    }

    public HttpHeaders internalServerErrorResponseHeader( HttpServletRequest request, String requestType ){
        // getting current timestamp in String
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTimestampInString = now.format(formatter);

        // getting url of the caller
        String requestUrl = request.getRequestURL().toString();

        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.put("STATUS_MESSAGE", Collections.singletonList("0")                   );
        responseHeader.put("RECORD_DATE", Collections.singletonList(currentTimestampInString)   );
        responseHeader.put("HTTP_STATUS", Collections.singletonList(HttpStatus.INTERNAL_SERVER_ERROR.toString())   );

        responseHeader.put("REQUEST_TYPE", Collections.singletonList(requestType)  );
        responseHeader.put("USER", Collections.singletonList(requestUrl));

        return responseHeader;
    }
}
