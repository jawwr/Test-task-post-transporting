package com.example.post;

import com.example.post.models.PackageType;
import com.example.post.models.PostPackage;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PostTransportationApplication.class)
@AutoConfigureMockMvc
@Sql(value = "/scriptAfterPackageDeliveryTests.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = "/scriptBeforePackageDeliveryTests.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PackageDeliveryControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegisterPackage() throws Exception {
        var body = convertObjectToJson(new PostPackage(123123, "receive name", "address", PackageType.PACKAGE));
        mockMvc.perform(post("/api/v1/office/123123/packages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void testRegisterPackageWithNotExistOriginPostOffice() throws Exception {
        var body = convertObjectToJson(new PostPackage(123123, "receive name", "address", PackageType.PACKAGE));
        mockMvc.perform(post("/api/v1/office/1/packages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testRegisterPackageWithNotExistReceivePostOffice() throws Exception {
        var body = convertObjectToJson(new PostPackage(1, "receive name", "address", PackageType.PACKAGE));
        mockMvc.perform(post("/api/v1/office/123123/packages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testUpdateStatus() throws Exception {
        mockMvc.perform(put("/api/v1/office/123123/packages/123/status?status=DEPART"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateNotExistStatus() throws Exception {
        mockMvc.perform(put("/api/v1/office/123123/packages/123/status?status=TEST"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateEmptyStatus() throws Exception {
        mockMvc.perform(put("/api/v1/office/123123/packages/123/status"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateStatusWithNotExistPackage() throws Exception {
        mockMvc.perform(put("/api/v1/office/123123/packages/1/status?status=DEPART"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testUpdateStatusWithNotExistPostOffice() throws Exception {
        mockMvc.perform(put("/api/v1/office/1/packages/123/status?status=DEPART"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testUpdateStatusReceivedPackage() throws Exception {
        mockMvc.perform(put("/api/v1/office/123123/packages/123/status?status=RECEIVE"))
                .andReturn();

        mockMvc.perform(put("/api/v1/office/123123/packages/123/status?status=DEPART"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testReceivedPackageInOtherPostOffice() throws Exception {
        mockMvc.perform(put("/api/v1/office/123124/packages/123/status?status=RECEIVE"))
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
