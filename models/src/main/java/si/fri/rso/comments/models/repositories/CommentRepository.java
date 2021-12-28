package si.fri.rso.comments.models.repositories;

import org.springframework.data.repository.CrudRepository;
import si.fri.rso.comments.models.entities.Comment;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface CommentRepository extends CrudRepository<Comment, Integer> {

    Iterable<Comment> findAllByStationID(Integer id);
}