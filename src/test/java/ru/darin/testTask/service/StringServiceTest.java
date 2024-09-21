package ru.darin.testTask.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import ru.darin.testTask.dto.StringDTO;
import ru.darin.testTask.model.StringModel;
import ru.darin.testTask.repository.StringRepository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

class StringServiceTest {

    private StringRepository repository = Mockito.mock(StringRepository.class);

    private ModelMapper mapper = Mockito.mock(ModelMapper.class);

    StringDTO dto = new StringDTO();

    @InjectMocks
    private StringService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveString() {
        StringModel model = new StringModel();
        model.setStr("aabbb");
        service.saveString(model);
        verify(repository, times(1)).save(model);
    }

    @Test
    void allStrings() {
        service.allStrings();
        verify(repository, times(1)).findAll();
    }

    @Test
    void convertToDTOFromModel() {
        StringModel model = new StringModel();
        service.convertToDTOFromModel(model);
        verify(mapper, times(1)).map(model, StringDTO.class);
    }

    @Test
    void convertToModelFromDTO() {
        service.convertToModelFromDTO(dto);
        verify(mapper, times(1)).map(dto, StringModel.class);
    }

    @BeforeEach
    void createDto() {
        dto.setStr("aaabb");
    }

    @Test
    void getFillingMapOfCharactersFromString() {
        Map<Character, Integer> expectedMap = new HashMap<>();
        expectedMap.put('a', 3);
        expectedMap.put('b', 2);

        assertEquals(expectedMap, service.getFillingMapOfCharactersFromString(dto));
    }

    @Test
    void getResultWithCountOfCharacters() {
        Map<Character, Integer> expectedLinkedHashMap = new LinkedHashMap<>();
        expectedLinkedHashMap.put('b', 2);
        expectedLinkedHashMap.put('a', 3);

        assertEquals(expectedLinkedHashMap, service.getResultWithCountOfCharacters(dto));
    }
}