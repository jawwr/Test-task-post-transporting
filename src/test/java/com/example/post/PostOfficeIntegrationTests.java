package com.example.post;

import com.example.post.models.PostOffice;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PostTransportationApplication.class)
@AutoConfigureMockMvc
@Sql(value = "/scriptAfterPostOfficeTests.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PostOfficeIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreatePostOffice() throws Exception {
        var body = convertObjectToJson(new PostOffice(123123, "name", "receiver address"));
        mockMvc.perform(post("/api/v1/office")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreatePostOfficeWithZeroIndex() throws Exception {
        var body = convertObjectToJson(new PostOffice(0, "name", "receiver address"));
        mockMvc.perform(post("/api/v1/office")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testCreatePostOfficeWithExistId() throws Exception {
        var body = convertObjectToJson(new PostOffice(123123, "name", "receiver address"));
        mockMvc.perform(post("/api/v1/office")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
        mockMvc.perform(post("/api/v1/office")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testGetPostOffice() throws Exception {
        testCreatePostOffice();
        mockMvc.perform(get("/api/v1/office/123123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.index").value(123123));
    }

    @Test
    public void testGetNotExistPostOffice() throws Exception {
        testCreatePostOffice();
        mockMvc.perform(get("/api/v1/office/1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    private <T> String convertObjectToJson(T obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        return writer.writeValueAsString(obj);
    }
}
