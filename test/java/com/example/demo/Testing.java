package com.example.demo;

import com.example.demo.model.Csv;
import com.example.demo.service.MyException;
import com.google.gson.Gson;
import net.minidev.json.JSONUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Fail.fail;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class Testing {

    @Autowired
    private MockMvc mvc;

    @Value("${worker.environment}")
    private String environment;

    @Test
    public void getWelcome() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/output"))
                .andExpect(status().isOk());
    }

       @Test
       public void updateTest() throws Exception {
           Csv csv = new Csv();
           csv.setDescription("this is for update testing");
           csv.setAccountNo(12);
           Gson gson = new Gson();
           String requestBody = gson.toJson(csv);
           mvc.perform(MockMvcRequestBuilders.put("/stud/106").
                   contentType(MediaType.APPLICATION_JSON).content(requestBody))

                   .andExpect(status().isOk());

       }
    @Test(expected = Exception.class)
    public void myTestMethod() throws Exception {

        Csv csv = new Csv();
        csv.setDescription("this is for testing");
        csv.setAccountNo(12);
        Gson gson = new Gson();
        String requestBody = gson.toJson(csv);
        mvc.perform(MockMvcRequestBuilders.put("/stud/125").
                contentType(MediaType.APPLICATION_JSON).content(requestBody))

                .andExpect(status().isOk());

    }

    @Test
    public void deleteEnv() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/students/103"))
                .andExpect(status().isOk());
    }
    @Test(expected = Exception.class)
    public void myDeleteTesting() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/students/126"))
                        .andExpect(status().isOk());

    }

}
