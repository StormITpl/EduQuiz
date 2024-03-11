package pl.stormit.eduquiz.statistic.userstatistic;

import java.time.Instant;
import java.util.UUID;

public interface UserStatisticFacade {

    long getTotalNumberOfUsers();

    Instant lastLoginByUser(UUID userId);}
