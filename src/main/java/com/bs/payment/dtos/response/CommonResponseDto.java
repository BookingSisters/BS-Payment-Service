package com.bs.payment.dtos.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class CommonResponseDto {

    private final String status;
    private final String message;
}
