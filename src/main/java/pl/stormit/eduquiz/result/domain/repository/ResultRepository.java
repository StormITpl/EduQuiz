package pl.stormit.eduquiz.result.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.stormit.eduquiz.result.domain.model.Result;

import java.util.UUID;

public interface ResultRepository extends JpaRepository<Result, UUID> {
}
