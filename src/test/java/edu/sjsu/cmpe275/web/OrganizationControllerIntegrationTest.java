package edu.sjsu.cmpe275.web;

import edu.sjsu.cmpe275.Main;
import edu.sjsu.cmpe275.domain.exception.OrganizationNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "bargemayur05@gmail.com")
public class OrganizationControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql("/db/organization_crud_operations.sql")
    public void getOrganization_withValidRequest_shouldReturnOrganization() throws Exception {
        mockMvc.perform(get("/organizations/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(notNullValue())));
    }

    @Test
    @Sql("/db/organization_crud_operations.sql")
    public void getOrganization_withInvalidOrganizationId_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/organizations/3"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

}