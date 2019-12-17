package gov.uk.api.web.controller;

import gov.uk.api.user.geo.CoordinatesNotFoundException;
import gov.uk.api.user.model.User;
import gov.uk.api.user.query.UserQuery;
import gov.uk.api.web.WebConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static gov.uk.api.user.model.TestData.aUser;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {WebConfiguration.class})
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserQuery userQuery;

    @Test
    void findUsersByCity() throws Exception {
        List<User> theUsers = asList(
                aUser().withId(101).build(),
                aUser().withId(102).build());
        when(userQuery.findByCity("theCity")).thenReturn(theUsers);

        mvc.perform(get("/cities/theCity/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(hasJsonPath("$.[0].id", equalTo(101))))
                .andExpect(content().string(hasJsonPath("$.[1].id", equalTo(102))));
    }

    @Test
    void findUsersByLocationAndRadius() throws Exception {
        List<User> theUsers = asList(
                aUser().withId(110).build(),
                aUser().withId(120).build());
        when(userQuery.findByLocationAndRadius("theLocation", 19)).thenReturn(theUsers);

        mvc.perform(get("/users?location=theLocation&radiusInMiles=19").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(hasJsonPath("$.[0].id", equalTo(110))))
                .andExpect(content().string(hasJsonPath("$.[1].id", equalTo(120))));
    }

    @Test
    void findUsersByLocationAndRadius_usesDefaultRadiusWhenNotSupplied() throws Exception {
        List<User> theUsers = asList(
                aUser().withId(110).build(),
                aUser().withId(120).build());
        when(userQuery.findByLocationAndRadius("theLocation", 1)).thenReturn(theUsers);

        mvc.perform(get("/users?location=theLocation").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(hasJsonPath("$.[0].id", equalTo(110))))
                .andExpect(content().string(hasJsonPath("$.[1].id", equalTo(120))));
    }

    @Test
    void findUsersByLocationAndRadius_ignoresRadiusAndReturnsAllWhenLocationNotSpecified() throws Exception {
        List<User> theUsers = asList(
                aUser().withId(110).build(),
                aUser().withId(120).build());
        when(userQuery.getAll()).thenReturn(theUsers);

        mvc.perform(get("/users?radiusInMiles=19").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(hasJsonPath("$.[0].id", equalTo(110))))
                .andExpect(content().string(hasJsonPath("$.[1].id", equalTo(120))));
    }


    @Test
    void findUsersByLocationAndRadius_unknownLocation() throws Exception {
        when(userQuery.findByLocationAndRadius(eq("theLocation"), anyInt())).thenThrow(new CoordinatesNotFoundException("the error message"));

        mvc.perform(get("/users?location=theLocation&radiusInMiles=19").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("the error message"));
    }
}
