package pl.stormit.eduquiz.quizcreator.domain.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.stormit.eduquiz.quizcreator.domain.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);
    List<Category> findByNameIgnoreCase(String name);
}
