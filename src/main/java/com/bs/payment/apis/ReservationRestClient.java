package com.bs.payment.apis;

import com.bs.payment.dtos.response.CommonResponseDto;
import com.bs.payment.exceptions.internalServer.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationRestClient {

    private final RestTemplate restTemplate;

    @Value("${external.service.reservation.url}")
    private String externalServiceReservationUrl;

    @Transactional
    public void makeReservationComplete(final Long id) {

        log.info("makeReservationComplete with ID: {}", id);

        String url = externalServiceReservationUrl + "/reservations/" + id;
        HttpEntity<?> request = new HttpEntity<>(null);

        ResponseEntity<CommonResponseDto> response = restTemplate.exchange(url, HttpMethod.PUT, request, CommonResponseDto.class);

        if(!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ExternalServiceException("Sessions seats not created. HTTP Status: " + response.getStatusCode());
        }
    }

}
