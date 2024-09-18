package ru.darin.testTask.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class StringDTO {
    @NotEmpty(message = "Строка не должна быть пустой")
    @Size(min = 2, message = "Строка должна содержать минимум 2 символа")
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z0-9]+$", message = "Неверный формат ввода данных: Вы должны ввести набор из цифр, букв (латиница, кириллица)")
    private String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "StringDTO{" +
                "str='" + str + '\'' +
                '}';
    }
}
