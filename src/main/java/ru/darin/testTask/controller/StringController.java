package ru.darin.testTask.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.darin.testTask.dto.StringDTO;
import ru.darin.testTask.service.StringService;
import ru.darin.testTask.util.ExceptionBuilder;
import ru.darin.testTask.util.StringErrorResponse;
import ru.darin.testTask.util.StringException;

import java.util.List;

@RestController
@AllArgsConstructor
public class StringController {

    private StringService service;

    @PostMapping("/input")
    public ResponseEntity inputStr(@RequestBody @Valid StringDTO dto, BindingResult bindingResult) {
        ExceptionBuilder.buildErrorMessageForClient(bindingResult);
        service.saveString(service.convertToModelFromDTO(dto));
        return ResponseEntity.ok(service.getResultWithCountOfCharacters(dto));
    }

    @GetMapping("/allStrings")
    public List<StringDTO> allStrings() {
        return service.allStrings();
    }

    @ExceptionHandler
    private ResponseEntity<StringErrorResponse> libraryHandlerException(StringException e) {
        StringErrorResponse response = new StringErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
