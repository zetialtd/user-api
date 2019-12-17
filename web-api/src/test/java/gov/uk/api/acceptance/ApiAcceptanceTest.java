package gov.uk.api.acceptance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasNoJsonPath;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebClient
@AutoConfigureMockRestServiceServer
@TestPropertySource(properties = {"datasource.http.base.uri=http://apiBaseUri"})
public class ApiAcceptanceTest {
    private static final String JSON_PATH_PREFIX = "$.";

    @Value("${datasource.http.base.uri}")
    private String theBaseUri;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private MockRestServiceServer server;

    @Test
    void findByCity() throws Exception {
        server.expect(requestTo(theBaseUri + "/city/theCity/users"))
                .andRespond(withSuccess(new ClassPathResource("/usersByCityResponse.json"), MediaType.APPLICATION_JSON));

        mvc.perform(get("/cities/theCity/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(bodyIsJsonWith("[0].id", 135))
                .andExpect(bodyIsJsonWith("[0].firstName", "Mechelle"))
                .andExpect(bodyIsJsonWith("[0].lastName", "Boam"))
                .andExpect(bodyIsJsonWith("[0].emailAddress", "mboam3q@thetimes.co.uk"))
                .andExpect(bodyIsJsonWith("[0].ipAddress", "113.71.242.187"))
                .andExpect(bodyIsJsonWith("[0].latitudeDegrees", -6.5115909))
                .andExpect(bodyIsJsonWith("[0].longitudeDegrees", 105.652983))
                .andExpect(bodyIsJsonWith("[1].id", 396))
                .andExpect(bodyIsJsonWith("[1].firstName", "Terry"))
                .andExpect(bodyIsJsonWith("[1].lastName", "Stowgill"))
                .andExpect(bodyIsJsonWith("[1].emailAddress", "tstowgillaz@webeden.co.uk"))
                .andExpect(bodyIsJsonWith("[1].ipAddress", "143.190.50.240"))
                .andExpect(bodyIsJsonWith("[1].latitudeDegrees", -6.7098551))
                .andExpect(bodyIsJsonWith("[1].longitudeDegrees", 111.3479498))
                .andExpect(bodyIsJsonWithout("[2]"));
    }

    @Test
    void findUsersReturnsAllWhenNoCriteriaSupplied() throws Exception {
        server.expect(requestTo(theBaseUri + "/users"))
                .andRespond(withSuccess(new ClassPathResource("/allUsersResponse.json"), MediaType.APPLICATION_JSON));

        mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(bodyIsJsonWith("[0].id", 1))
                .andExpect(bodyIsJsonWith("[0].firstName", "Maurise"))
                .andExpect(bodyIsJsonWith("[0].lastName", "Shieldon"))
                .andExpect(bodyIsJsonWith("[0].emailAddress", "mshieldon0@squidoo.com"))
                .andExpect(bodyIsJsonWith("[0].ipAddress", "192.57.232.111"))
                .andExpect(bodyIsJsonWith("[0].latitudeDegrees", 34.003135))
                .andExpect(bodyIsJsonWith("[0].longitudeDegrees", -117.7228641))
                .andExpect(bodyIsJsonWith("[1].id", 266))
                .andExpect(bodyIsJsonWith("[1].firstName", "Ancell"))
                .andExpect(bodyIsJsonWith("[1].lastName", "Garnsworthy"))
                .andExpect(bodyIsJsonWith("[1].emailAddress", "agarnsworthy7d@seattletimes.com"))
                .andExpect(bodyIsJsonWith("[1].ipAddress", "67.4.69.137"))
                .andExpect(bodyIsJsonWith("[1].latitudeDegrees", 51.6553959))
                .andExpect(bodyIsJsonWith("[1].longitudeDegrees", 0.0572553))
                .andExpect(bodyIsJsonWithout("[2]"));
    }

    @Test
    void findUsersByLocationAndRadius() throws Exception {
        server.expect(requestTo(theBaseUri + "/users"))
                .andRespond(withSuccess(new ClassPathResource("/allUsersResponse.json"), MediaType.APPLICATION_JSON));

        mvc.perform(get("/users?location=London&radiusInMiles=50").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(bodyIsJsonWith("[0].id", 266))
                .andExpect(bodyIsJsonWith("[0].firstName", "Ancell"))
                .andExpect(bodyIsJsonWith("[0].lastName", "Garnsworthy"))
                .andExpect(bodyIsJsonWith("[0].emailAddress", "agarnsworthy7d@seattletimes.com"))
                .andExpect(bodyIsJsonWith("[0].ipAddress", "67.4.69.137"))
                .andExpect(bodyIsJsonWith("[0].latitudeDegrees", 51.6553959))
                .andExpect(bodyIsJsonWith("[0].longitudeDegrees", 0.0572553))
                .andExpect(bodyIsJsonWithout("[1]"));
    }

    private ResultMatcher bodyIsJsonWith(String path, Object value) {
        return content().string(hasJsonPath(JSON_PATH_PREFIX + path, equalTo(value)));
    }

    private ResultMatcher bodyIsJsonWithout(String path) {
        return content().string(hasNoJsonPath(JSON_PATH_PREFIX + path));
    }
}
