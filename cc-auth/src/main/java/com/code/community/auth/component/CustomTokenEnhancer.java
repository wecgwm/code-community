package com.code.community.auth.component;

import com.code.community.auth.dto.CustomUserDetails;
import com.code.community.comment.base.constant.FiledConstant;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义JWT内容增强器
 */
@Component
public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> stringObjectHashMap = new HashMap<>();
        Object principal = oAuth2Authentication.getPrincipal();
        if(principal instanceof CustomUserDetails){
            stringObjectHashMap.put(FiledConstant.ID, ((CustomUserDetails) principal).getId());
        }
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(stringObjectHashMap);
        return oAuth2AccessToken;
    }
}
