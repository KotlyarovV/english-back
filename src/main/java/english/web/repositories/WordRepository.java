package english.web.repositories;

import english.web.models.Word;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WordRepository extends CrudRepository<Word, UUID> {
    List<Word> findByUserId(Long userId);
}
