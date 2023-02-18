package pl.stormit.eduquiz.game.dto;

import java.util.List;
import java.util.UUID;

public record GameDto(
        UUID id,
        List<UUID> userAnswers
) {}
