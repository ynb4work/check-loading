package com.check_ip.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class GeoIpService {
    private final RestTemplate restTemplate;

    public GeoIpService() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        this.restTemplate = new RestTemplate(factory);
    }

    public String getCountryByIp(String ip) {
        try {
            String apiUrl = "https://ipapi.co/" + ip + "/country_name/";
            return restTemplate.getForObject(apiUrl, String.class);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}