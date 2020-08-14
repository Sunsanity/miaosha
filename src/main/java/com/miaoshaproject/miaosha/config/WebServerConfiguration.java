package com.miaoshaproject.miaosha.config;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class WebServerConfiguration implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {


    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        //使用对应工厂类提供的接口定制化tomcat connector
        ((TomcatServletWebServerFactory)factory).addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            Http11NioProtocol protocolHandler = (Http11NioProtocol) connector.getProtocolHandler();

            //定制化长连接，如果长连接30秒内没有请求自动断开长连接，避免DDOS攻击
            protocolHandler.setKeepAliveTimeout(30000);
            //当客户端发送超过10000个请求时自动断开长连接
            protocolHandler.setMaxKeepAliveRequests(10000);
        });
    }
}
