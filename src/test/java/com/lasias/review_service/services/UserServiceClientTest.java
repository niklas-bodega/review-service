package com.lasias.review_service.services;

import com.lasias.review_service.dtos.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;
import org.testcontainers.mysql.MySQLContainer;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceClientTest {

    @Mock
    private RestClient userRestClient;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RestClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    private UserServiceClient userServiceClient;


    @BeforeEach
    void setUp(){
        //Skapar en riktigt userServiceClient och lägger in den mockade RestClienten. Så det blir ingen nätverks anslutning, men man "låtsas" som det.
        userServiceClient = new UserServiceClient(userRestClient);
    }

    private void mockSuccessfulChainUpToRetrieve(){
        //Säger åt testet vad den ska göra när get blir anropat, nu får den bara tillbaka en requestHeadersUriSpec
        // som säger att jag bygger request, men behöver fortfarante en URL.
        when(userRestClient.get()).thenReturn(requestHeadersUriSpec);

        //Denna säger när /api/user blir anropat då ska mockito returnera en requestHeadersSpec
        //requestHeadersSpec säger åt den att den fortfarande saknar en header för att kunna "skickas".
        when(requestHeadersUriSpec.uri("/api/user")).thenReturn(requestHeadersSpec);

        //Här säger vi att header ska vara Authorization och sen vilken sträng som helst. Mockito bryr sig inte om att det ska vara
        //Bearer asdadasdasdasdasdasd (jwt token).
        when(requestHeadersSpec.header(eq("Authorization"),anyString())).thenReturn(requestHeadersSpec);

        //Nu har requesten blivit "skickad" och responseSpec är objektet som vi vill få tillbaka. I vårat fall UserDto.
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    void getUsername_returnsName_whenCallSucceeds() {
        mockSuccessfulChainUpToRetrieve();
        when(responseSpec.body(UserDto.class)).thenReturn(new UserDto("email@test.com", "Ivan", "USER", null, true));
        String result = userServiceClient.getUsername("fake-jwt");
        assertEquals("Ivan", result);
    }

    @Test
    void getUsername_returnsUnknownUsername_whenResponseBodyIsNull(){
        mockSuccessfulChainUpToRetrieve();
        when(responseSpec.body(UserDto.class)).thenReturn(null);
        assertEquals("Unknown username", userServiceClient.getUsername("fake-jwt"));
    }

    @Test
    void getUsername_returnsUnknownUser_whenCallThrowsException(){
        when(userRestClient.get()).thenThrow(new RuntimeException("Connection refused"));
        assertEquals("Unknown user", userServiceClient.getUsername("fake-jwt"));
    }

}