package com.Queirozq.onlinestore.service;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest({"server.port:0", "eureka.client.enabled:false"})
@AutoConfigureMockMvc
public class LoadBalancingTest {


    @TestConfiguration
    public static class TestConfig{
        @Bean
        public ServiceInstanceListSupplier serviceInstanceListSupplier(){
            return new TestServiceInstanceListSupplier("user-session-service", 8082, 9092);
        }
    }

    @RegisterExtension
    static WireMockExtension USER_SESSION_SERVICE_1 = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().port(8082))
            .build();

    @RegisterExtension
    static WireMockExtension USER_SESSION_SERVICE_2 = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().port(9092))
            .build();

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testLoadBalancingForUserSessionServiceWorks() throws Exception {
        String responseBody = "{ \"sessionId\": \"5d9d5eb2-c4b3-4b6e-85fa-1d8aacf6b7b2\", \"valid\": true}";
        String invalidResponseBody = "{ \"sessionId\": \"5d9d5eb2-c4b3-4b6e-85fa-1d8aacf6b7b2\", \"valid\": false}";

        USER_SESSION_SERVICE_1.stubFor(get(urlPathEqualTo("/user-sessions/validate"))
                .withQueryParam("sessionId", equalTo("5d9d5eb2-c4b3-4b6e-85fa-1d8aacf6b7b2"))
                .willReturn(okJson(responseBody)));

        USER_SESSION_SERVICE_2.stubFor(get(urlPathEqualTo("/user-sessions/validate"))
                .withQueryParam("sessionId", equalTo("5d9d5eb2-c4b3-4b6e-85fa-1d8aacf6b7b2"))
                .willReturn(okJson(invalidResponseBody)));

        int firstStatus = mockMvc.perform(MockMvcRequestBuilders.get("/actuator/health")
                        .header("X-Session-Id", "5d9d5eb2-c4b3-4b6e-85fa-1d8aacf6b7b2"))
                .andReturn().getResponse().getStatus();

        int secondStatus = mockMvc.perform(MockMvcRequestBuilders.get("/actuator/health")
                        .header("X-Session-Id", "5d9d5eb2-c4b3-4b6e-85fa-1d8aacf6b7b2"))
                .andReturn().getResponse().getStatus();

        assertThat(List.of(firstStatus, secondStatus)).containsExactlyInAnyOrder(200, 403);
    }
}
