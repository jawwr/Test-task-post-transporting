package com.example.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PostTransportationApplication.class)
@AutoConfigureMockMvc
@Sql(value = "/scriptAfterPackageTests.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(value = "/scriptBeforePackageTests.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PackageControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetPackageInfo() throws Exception {
        mockMvc.perform(get("/api/v1/package/123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(123));
    }

    @Test
    public void testGetNotExistPackageInfo() throws Exception {
        mockMvc.perform(get("/api/v1/package/1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testGetDeliveryStatus() throws Exception {
        mockMvc.perform(get("/api/v1/package/123/status"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.packageId").value(123));
    }

    @Test
    public void testGetDeliveryStatusNotExistPackage() throws Exception {
        mockMvc.perform(get("/api/v1/package/1/status"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void testGetDeliveryHistory() throws Exception {
        mockMvc.perform(get("/api/v1/package/123/history"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].packageId").value(123));
    }

    @Test
    public void testGetDeliveryHistoryNotExistPackage() throws Exception {
        mockMvc.perform(get("/api/v1/package/1/history"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }
}
