package ru.darin.testTask.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.darin.testTask.dto.StringDTO;
import ru.darin.testTask.model.StringModel;
import ru.darin.testTask.repository.StringRepository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StringService {

    private final StringRepository repository;

    private final ModelMapper modelMapper;

    public StringService(StringRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void saveString(StringModel model) {
        repository.save(model);
    }

    public StringDTO convertToDTOFromModel(StringModel model) {
        return modelMapper.map(model, StringDTO.class);
    }

    public StringModel convertToModelFromDTO(StringDTO dto) {
        return modelMapper.map(dto, StringModel.class);
    }

    public Map<Character, Integer> getFillingMapOfCharactersFromString(StringDTO dto) {

        Map<Character, Integer> map = new HashMap<>();

        char[] chars = dto.getStr().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int count = 1;
            for (int j = (i + 1); j < chars.length; j++) {
                if (chars[i] == chars[j]) {
                    count++;
                }
            }

            if (!map.containsKey(chars[i])) {
                map.put(chars[i], count);
            }
        }
        return map;
    }

    public Map<Character,Integer> getResultWithCountOfCharacters(StringDTO dto) {

        return getFillingMapOfCharactersFromString(dto).entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new // Храним в LinkedHashMap для сохранения порядка сортировки
                ));
    }


}
