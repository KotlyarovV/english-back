package english.web.api.controllers;

import english.web.api.dto.WordCreateUpdateDto;
import english.web.api.dto.WordInfoDto;
import english.web.exceptions.WordIsNotOwnedByUserException;
import english.web.exceptions.WordNotFoundException;
import english.web.models.User;
import english.web.services.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/words")
public class WordController {

    private final WordService wordService;

    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<WordInfoDto>> getAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(wordService.getAll(user));
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public ResponseEntity<WordInfoDto> get(@PathVariable(value = "id") UUID id, @AuthenticationPrincipal User user)
            throws WordIsNotOwnedByUserException, WordNotFoundException {
        return ResponseEntity.ok(wordService.get(user, id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<WordInfoDto> create(@RequestBody WordCreateUpdateDto word, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(wordService.create(word, user));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{id}")
    public ResponseEntity<WordInfoDto> update(@RequestBody WordCreateUpdateDto word,
                                              @PathVariable(value = "id") UUID id, @AuthenticationPrincipal User user)
            throws WordIsNotOwnedByUserException, WordNotFoundException {
        return ResponseEntity.ok(wordService.update(word, id, user));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public void delete(@PathVariable(value = "id") UUID id, @AuthenticationPrincipal User user)
            throws WordIsNotOwnedByUserException, WordNotFoundException {
        wordService.delete(id, user);
    }
}
