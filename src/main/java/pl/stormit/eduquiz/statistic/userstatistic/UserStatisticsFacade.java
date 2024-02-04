package pl.stormit.eduquiz.statistic.userstatistic;

import java.time.Instant;
import java.util.UUID;

public interface UserStatisticsFacade {

    Instant lastLoginByUser(UUID userId);
}
