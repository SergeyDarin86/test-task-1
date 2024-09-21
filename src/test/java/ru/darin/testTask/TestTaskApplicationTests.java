package ru.darin.testTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ru.darin.testTask.controller.StringController;
import ru.darin.testTask.dto.StringDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(value = {"/create_string_model_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/delete_string_model_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties") // указываем файл с пропертями для тестового окружения
class TestTaskApplicationTests {

    @Autowired
    private StringController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void inputStr() throws Exception {
        StringDTO dto = new StringDTO();
        dto.setStr("dddaaf");

        mockMvc.perform(post("/input")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(dto))
                )
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    void inputStrWithThrowException() throws Exception {
        StringDTO dto = new StringDTO();
        dto.setStr("aaa?");

        mockMvc.perform(post("/input")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(dto))
                )
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    //проверка на то, что sql-скрипты работают и в базе есть тестовые данные
    @Test
    void getAllStrings() throws Exception {
        this.mockMvc
                .perform(get("/allStrings").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

}
