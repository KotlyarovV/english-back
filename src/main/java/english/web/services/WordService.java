package english.web.services;

import english.web.api.dto.WordCreateUpdateDto;
import english.web.api.dto.WordInfoDto;
import english.web.exceptions.WordIsNotOwnedByUserException;
import english.web.exceptions.WordNotFoundException;
import english.web.models.User;
import english.web.models.Word;
import english.web.repositories.WordRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class WordService {

    private final WordRepository wordRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public WordService(WordRepository wordRepository,
                       ModelMapper modelMapper) {
        this.wordRepository = wordRepository;
        this.modelMapper = modelMapper;
    }

    public List<WordInfoDto> getAll(User user) {
        List<Word> words = wordRepository.findByUserId(user.getId());
        return words.stream()
                .map(w -> modelMapper.map(w, WordInfoDto.class))
                .collect(Collectors.toList());
    }

    public WordInfoDto get(User user, UUID wordId)
            throws WordNotFoundException, WordIsNotOwnedByUserException {
        Optional<Word> wordOptional = wordRepository.findById(wordId);

        if (wordOptional.isEmpty()) {
            throw new WordNotFoundException();
        }

        Word word = wordOptional.get();

        if (!word.getUserId().equals(user.getId())) {
            throw new WordIsNotOwnedByUserException();
        }

        return modelMapper.map(word, WordInfoDto.class);
    }

    public WordInfoDto create(WordCreateUpdateDto wordCreateDto, User user) {
        Word word = modelMapper.map(wordCreateDto, Word.class);
        word.setUserId(user.getId());
        wordRepository.save(word);
        return modelMapper.map(word, WordInfoDto.class);
    }

    public WordInfoDto update(WordCreateUpdateDto wordCreateUpdateDto, UUID wordId, User user)
            throws WordNotFoundException, WordIsNotOwnedByUserException {
        Optional<Word> wordOptional = wordRepository.findById(wordId);

        if (wordOptional.isEmpty()) {
            throw new WordNotFoundException();
        }

        Word word = wordOptional.get();

        if (!word.getUserId().equals(user.getId())) {
            throw new WordIsNotOwnedByUserException();
        }

        modelMapper.map(wordCreateUpdateDto, word);
        wordRepository.save(word);
        return modelMapper.map(word, WordInfoDto.class);
    }

    public void delete(UUID id, User user)
            throws WordNotFoundException, WordIsNotOwnedByUserException {
        Optional<Word> wordOptional = wordRepository.findById(id);

        if (wordOptional.isEmpty()) {
            throw new WordNotFoundException();
        }

        Word word = wordOptional.get();

        if (!word.getUserId().equals(user.getId())) {
            throw new WordIsNotOwnedByUserException();
        }

        wordRepository.delete(word);
    }
}
