package edu.sjsu.cmpe275.web;

import edu.sjsu.cmpe275.Main;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class OrganizationControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql("/db/user_organization_membership.sql")
    public void createOrganizationMembership() throws Exception {
        final MvcResult mvcResult =
                mockMvc.perform(post("/organizations/1/memberships?requesterId=2"))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
    }
}