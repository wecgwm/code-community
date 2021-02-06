package com.code.community.auth.config;

import com.code.community.auth.component.CustomTokenEnhancer;
import com.code.community.auth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.Arrays;

@EnableAuthorizationServer
@Configuration
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private CustomUserDetailsService userDetailsService;
    private CustomTokenEnhancer customTokenEnhancer;

    @Autowired
    public AuthServerConfig(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, CustomTokenEnhancer customTokenEnhancer) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.customTokenEnhancer = customTokenEnhancer;
    }

    @Value("${oauth2.client-id}")
    private String clientId;
    @Value("${oauth2.client-password}")
    private String clientPassword;
    @Value("${oauth2.keypair-fileName}")
    private String keypairFileName;
    @Value("${oauth2.keypair-password}")
    private String keyPairPassword;

    /**
     * 定义令牌端点上的安全约束。
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 支持将client参数放在header或body中
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("isAuthenticated()")
                //oauth/check_token公开
                .checkTokenAccess("permitAll()");
    }

    /**
     * 定义客户端详细信息服务的配置程序。可以初始化客户详细信息，或者可以仅引用现有store。
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientId)
                .secret(passwordEncoder.encode(clientPassword))
                .scopes("all")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(3600*24)
                .refreshTokenValiditySeconds(3600*24*7);
    }

    /**
     * 定义授权和令牌端点以及令牌服务。
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain());
    }

    @Bean
    public TokenEnhancerChain tokenEnhancerChain(){
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer,jwtAccessTokenConverter()));
        return tokenEnhancerChain;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    /**
     * 从classpath下的证书中获取秘钥对
     * @return
     */
    @Bean
    public KeyPair keyPair() {
        //证书设置以及密码
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(keypairFileName),
                keyPairPassword.toCharArray());
        return keyStoreKeyFactory.getKeyPair("jwt");
    }
}
