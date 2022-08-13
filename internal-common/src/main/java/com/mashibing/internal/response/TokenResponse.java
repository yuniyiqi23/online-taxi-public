package com.mashibing.internal.response;

import lombok.Data;

@Data
public class TokenResponse {

    /**
     * TokenAccess
     */
    private String tokenAccess;
    /**
     * TokenRefrash
     */
    private String tokenRefrash;


}
