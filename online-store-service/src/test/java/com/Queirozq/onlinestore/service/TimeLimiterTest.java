package com.Queirozq.onlinestore.service;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.StopWatch;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest({"server.port:0", "eureka.client.enabled:false"})
@AutoConfigureMockMvc
public class TimeLimiterTest {

    @TestConfiguration
    public static class TestConfig {
        @Bean
        public ServiceInstanceListSupplier serviceInstanceListSupplier() {
            return new TestServiceInstanceListSupplier("user-session-service", 8082);
        }
    }

    @RegisterExtension
    static WireMockExtension USER_SESSION_SERVICE = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().port(8082))
            .build();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testTimeLimiterWorks() throws Exception {
        String responseBody = "{ \"sessionId\": \"5d9d5eb2-c4b3-4b6e-85fa-1d8aacf6b7b2\", \"valid\": true}";

        USER_SESSION_SERVICE.stubFor(get(urlPathEqualTo("/user-sessions/validate"))
                .withQueryParam("sessionId", equalTo("5d9d5eb2-c4b3-4b6e-85fa-1d8aacf6b7b2"))
                .willReturn(aResponse().withBody(responseBody).withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).withFixedDelay(7000)));

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        mockMvc.perform(MockMvcRequestBuilders.get("/actuator/health")
                        .header("X-Session-Id", "5d9d5eb2-c4b3-4b6e-85fa-1d8aacf6b7b2"))
                .andExpect(MockMvcResultMatchers.status().is(500));
        stopWatch.stop();
        assertThat(stopWatch.getTotalTimeMillis()).isLessThan(5500);
    }
}
