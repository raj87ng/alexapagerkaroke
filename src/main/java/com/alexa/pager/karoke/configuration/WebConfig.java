package com.alexa.pager.karoke.configuration;

import com.alexa.pager.karoke.properties.PropertiesUtils;
import com.alexa.pager.karoke.servlet.AlexaServlet;
import com.amazon.ask.servlet.ServletConstants;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServlet;

@Configuration
public class WebConfig {
    @Bean
    public ServletRegistrationBean<HttpServlet> alexaServlet() {

        loadProperties();

        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new AlexaServlet());
        servRegBean.addUrlMappings("/alexa/*");
        servRegBean.setLoadOnStartup(1);
        return servRegBean;
    }

    private void loadProperties() {
        System.setProperty(ServletConstants.TIMESTAMP_TOLERANCE_SYSTEM_PROPERTY, PropertiesUtils.getPropertyValue(ServletConstants.TIMESTAMP_TOLERANCE_SYSTEM_PROPERTY));
        System.setProperty(ServletConstants.DISABLE_REQUEST_SIGNATURE_CHECK_SYSTEM_PROPERTY, PropertiesUtils.getPropertyValue(ServletConstants.DISABLE_REQUEST_SIGNATURE_CHECK_SYSTEM_PROPERTY));
        System.setProperty(Constants.SSL_KEYSTORE_FILE_PATH_KEY, Constants.SSL_KEYSTORE_FILE_PATH_KEY);
        System.setProperty(Constants.SSL_KEYSTORE_PASSWORD_KEY, Constants.SSL_KEYSTORE_PASSWORD_KEY);
    }

}