package com.check_ip.controller;

import com.check_ip.service.GeoIpService;
import com.check_ip.service.IpDetectionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class IpInfoController {
    private final IpDetectionService ipDetectionService;
    private final GeoIpService geoIpService;

    @GetMapping("/loading")
    public ResponseEntity<String> checkIp(HttpServletRequest request) {
        String ip = ipDetectionService.getClientIp(request);
        String userAgent = request.getHeader("User-Agent");

        // Логируем все данные
        log.info("New request from IP: {}, User-Agent: {}", ip, userAgent);

        // Всегда возвращаем страницу загрузки
        return ResponseEntity.ok(getLoadingPage());
    }

    private String getLoadingPage() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>System Check</title>
                    <style>
                        .loading-container {
                            display: flex;
                            flex-direction: column;
                            align-items: center;
                            justify-content: center;
                            height: 100vh;
                            font-family: Arial, sans-serif;
                            background-color: #f5f5f5;
                        }
                        .spinner {
                            width: 50px;
                            height: 50px;
                            border: 5px solid #e0e0e0;
                            border-top: 5px solid #3498db;
                            border-radius: 50%;
                            animation: spin 1s linear infinite;
                            margin-bottom: 20px;
                        }
                        @keyframes spin {
                            0% { transform: rotate(0deg); }
                            100% { transform: rotate(360deg); }
                        }
                        h1 {
                            color: #333;
                            margin-bottom: 10px;
                        }
                        p {
                            color: #666;
                        }
                    </style>
                </head>
                <body>
                    <div class="loading-container">
                        <div class="spinner"></div>
                        <h1>Web-Server is Loading</h1>
                        <p>Please wait while the system initializes...</p>
                    </div>
                </body>
                </html>
                """;
    }
}


/*@RestController
@RequiredArgsConstructor
public class IpInfoController {
    private final IpDetectionService ipDetectionService;
    private final GeoIpService geoIpService;

    @GetMapping("/check-ip")
    public ResponseEntity<String> checkIp(HttpServletRequest request) {
        String ip = ipDetectionService.getClientIp(request);

        // Для локального тестирования
        if (isLocalIp(ip)) {
            ip = "8.8.8.8"; // Google DNS
        }

        String country;
        try {
            country = geoIpService.getCountryByIp(ip);
        } catch (Exception e) {
            // Возвращаем HTML страницу с анимацией загрузки
            return ResponseEntity.ok(getLoadingPage(ip));
        }

        return ResponseEntity.ok(getResultPage(ip, country, request.getHeader("User-Agent")));
    }

    private String getLoadingPage(String ip) {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>IP Checker</title>
                    <style>
                        .loader {
                            border: 16px solid #f3f3f3;
                            border-radius: 50%;
                            border-top: 16px solid #3498db;
                            width: 120px;
                            height: 120px;
                            animation: spin 2s linear infinite;
                            margin: 50px auto;
                        }
                        @keyframes spin {
                            0% { transform: rotate(0deg); }
                            100% { transform: rotate(360deg); }
                        }
                        .container {
                            text-align: center;
                            font-family: Arial, sans-serif;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>Определяем местоположение...</h1>
                        <div class="loader"></div>
                        <p>IP: %s</p>
                        <script>
                            // Автоматическое обновление через 5 секунд
                            setTimeout(function() {
                                location.reload();
                            }, 5000);
                        </script>
                    </div>
                </body>
                </html>
                """.formatted(ip);
    }

    private String getResultPage(String ip, String country, String userAgent) {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>IP Checker - Результат</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            max-width: 800px;
                            margin: 0 auto;
                            padding: 20px;
                        }
                        .result-card {
                            border: 1px solid #ddd;
                            border-radius: 8px;
                            padding: 20px;
                            margin-top: 20px;
                            background-color: #f9f9f9;
                        }
                    </style>
                </head>
                <body>
                    <h1>Результат проверки IP</h1>
                    <div class="result-card">
                        <p><strong>IP-адрес:</strong> %s</p>
                        <p><strong>Страна:</strong> %s</p>
                        <p><strong>Браузер:</strong> %s</p>
                    </div>
                </body>
                </html>
                """.formatted(ip, country, userAgent);
    }

    private boolean isLocalIp(String ip) {
        return ip == null || ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1");
    }
}*/


//
//import com.check_ip.service.GeoIpService;
//import com.check_ip.service.IpDetectionService;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Collections;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Slf4j
//@RestController
//public class IpInfoController {
//    private final IpDetectionService ipDetectionService;
//    private final GeoIpService geoIpService;
//
//    public IpInfoController(IpDetectionService ipDetectionService, GeoIpService geoIpService) {
//        this.ipDetectionService = ipDetectionService;
//        this.geoIpService = geoIpService;
//    }
//
//    @GetMapping("/check-ip")
//    public ResponseEntity<Map<String, String>> checkIp(
//            HttpServletRequest request,
//            @RequestHeader(value = "User-Agent", required = false) String userAgent) {
//
//        try {
//            String ip = ipDetectionService.getClientIp(request);
//
//            // Логирование входящего запроса
//            logRequestDetails(request, ip, userAgent);
//
//            // Для локального тестирования используем публичный IP
//            if (isLocalIp(ip)) {
//                ip = "8.8.8.8"; // Google DNS для теста
//                log.warn("Using test IP address for local request");
//            }
//
//            String country;
//            country = geoIpService.getCountryByIp(ip);
//
//            // Формируем ответ
//            Map<String, String> response = Map.of(
//                    "ip", ip,
//                    "country", country,
//                    "userAgent", userAgent != null ? userAgent : "Not specified"
//            );
//
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            log.error("Error processing IP check request", e);
//            return ResponseEntity.internalServerError().body(
//                    Map.of("error", "Unable to process request", "details", e.getMessage())
//            );
//        }
//    }
//
//    private boolean isLocalIp(String ip) {
//        return ip == null ||
//                ip.equals("0:0:0:0:0:0:0:1") ||
//                ip.equals("127.0.0.1") ||
//                ip.startsWith("192.168.") ||
//                ip.startsWith("10.");
//    }
//
//    private void logRequestDetails(HttpServletRequest request, String ip, String userAgent) {
//        String headers = Collections.list(request.getHeaderNames())
//                .stream()
//                .map(header -> header + ": " + request.getHeader(header))
//                .collect(Collectors.joining("\n"));
//
//        log.info("""
//                === IP Check Request ===
//                Client IP: {}
//                User-Agent: {}
//                Headers:
//                {}
//                ========================
//                """, ip, userAgent, headers);
//    }
//}