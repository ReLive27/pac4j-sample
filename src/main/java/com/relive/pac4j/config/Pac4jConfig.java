package com.relive.pac4j.config;

import com.relive.pac4j.authorizer.CustomAuthorizer;
import com.relive.pac4j.authorizer.RolesPermissionsMappingAuthorizationGenerator;
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.config.Config;
import org.pac4j.springframework.annotation.AnnotationConfig;
import org.pac4j.springframework.component.ComponentConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * @Author ReLive
 * @Date 2021/4/7-16:12
 */
@Configuration
@Import({ComponentConfig.class, AnnotationConfig.class})
@EnableConfigurationProperties(Pac4jConfigurationProperties.class)
public class Pac4jConfig {

    @Autowired
    private Pac4jConfigurationProperties pac4j;


    @Bean
    public Config config() {
        /** 配置*/
        PropertiesConfigExtensionsFactory propertiesConfigFactory = new PropertiesConfigExtensionsFactory(pac4j.getCallbackUrl(), pac4j.getClientsProperties());
        propertiesConfigFactory.setAuthorizationGenerator(rolesPermissionsMappingAuthorizationGenerator());
        final Config config = propertiesConfigFactory.build();
        config.addAuthorizer("ReLive27", new RequireAnyRoleAuthorizer("ROLE_ADMIN"));
        config.addAuthorizer("custom", new CustomAuthorizer());
        return config;
    }

    @Bean
    public RolesPermissionsMappingAuthorizationGenerator rolesPermissionsMappingAuthorizationGenerator() {
        return new RolesPermissionsMappingAuthorizationGenerator(pac4j.getSecurityConstraints());
    }

//    @Bean
//    public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
//        HttpURLConnectionFactory httpURLConnectionFactory = new HttpURLConnectionFactory() {
//            @Override
//            public HttpURLConnection buildHttpURLConnection(URLConnection url) {
//                SSLContext sslContext = null;
//                try {
//                    sslContext = SSLContext.getInstance("SSL");
//
//                    sslContext.init(new KeyManager[]{}, new TrustManager[]{new X509TrustManager() {
//                        @Override
//                        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//
//                        }
//
//                        @Override
//                        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//
//                        }
//
//                        @Override
//                        public X509Certificate[] getAcceptedIssuers() {
//                            return new X509Certificate[0];
//                        }
//                    }}, null);
//                    SSLSocketFactory socketFactory = sslContext.getSocketFactory();
//
//                    if (url instanceof HttpsURLConnection) {
//                        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url;
//                        httpsURLConnection.setSSLSocketFactory(socketFactory);
//                        httpsURLConnection.setHostnameVerifier((s, l) -> true);
//                    }
//
//                    return (HttpsURLConnection) url;
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        };
//        Cas20ServiceTicketValidator cas20ServiceTicketValidator = new Cas20ServiceTicketValidator(pac4j.getClientsProperties().get("cas2.serverUrl"));
//        cas20ServiceTicketValidator.setURLConnectionFactory(httpURLConnectionFactory);
//        Map<String, String> parameter = new HashMap<>();
//        parameter.put("format", "xml");
//        cas20ServiceTicketValidator.setCustomParameters(parameter);
//        return cas20ServiceTicketValidator;
//    }

}
