package com.genuinedeveloper.restfulclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.Arrays;
import javax.net.ssl.SSLContext;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RESTConfig {

	@Bean
	public RESTAPI RESTAPIConfig ( ) {
		return new RESTAPI();
	}
	
	@Bean //(name="TLS Template")
	RestTemplate TLSRestTemplate (RestTemplateBuilder builder) throws Exception {
		
		SSLContext sslContext = new SSLContextBuilder()
			      .loadTrustMaterial(new File("./OpenEHRS.p12"), "OpenEHRS".toCharArray())
			      .build();
			    SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
			    HttpClient httpClient = HttpClients.custom()
			      .setSSLSocketFactory(socketFactory)
			      .build();
			    HttpComponentsClientHttpRequestFactory factory = 
			      new HttpComponentsClientHttpRequestFactory(httpClient);
			    return new RestTemplate(factory);
	}
}
