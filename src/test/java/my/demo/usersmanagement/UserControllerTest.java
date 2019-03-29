package my.demo.usersmanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.demo.usersmanagement.dto.UserRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@Sql("classpath:test_data.sql")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findUserByLoginAndPassword() throws Exception {
        mockMvc.perform(get("/user")
                .param("login", "larisa")
                .param("password", "qwerty"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.login").value("larisa"))
                .andExpect(jsonPath("$.blocked").value(false))
                .andReturn();;
    }

    @Test
    public void addUser() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setLogin("ivan");
        userRequestDto.setPassword("qwerty12");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userRequestDto);

        mockMvc.perform(post("/user")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.login").value("ivan"))
                .andExpect(jsonPath("$.blocked").value(false))
                .andReturn();;
    }

    @Test
    public void addUserNegative() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setLogin("");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userRequestDto);

        mockMvc.perform(post("/user")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();;
    }

    @Test
    public void updateUser() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setLogin("ivan");
        userRequestDto.setPassword("qwerty12");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userRequestDto);

        mockMvc.perform(put("/user/update")
                .param("userId", "2")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.login").value("ivan"))
                .andExpect(jsonPath("$.blocked").value(true))
                .andReturn();;
    }

    @Test
    public void blockUser() throws Exception {


        mockMvc.perform(put("/user/block")
                .param("userId", "3")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.login").value("admin"))
                .andExpect(jsonPath("$.blocked").value(true))
                .andReturn();;
    }
}
