package pl.stormit.eduquiz.result.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "results")
@Getter
@Setter
public class Result {

    @Id
    private UUID id;

    private Integer score;
}
