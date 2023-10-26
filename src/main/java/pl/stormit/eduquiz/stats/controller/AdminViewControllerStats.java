package pl.stormit.eduquiz.stats.controller;

import org.springframework.stereotype.Service;
import pl.stormit.eduquiz.stats.UserStatisticsService;

@Service
public class AdminViewControllerStats {

    private final UserStatisticsService userStatisticsService;

    public AdminViewControllerStats(UserStatisticsService userStatisticsService) {
        this.userStatisticsService = userStatisticsService;
    }

    public long getTotalUsers() {
        return userStatisticsService.getTotalNumberOfUsers();
    }

    public long getUsersRegisteredLastWeek() {
        return userStatisticsService.getUsersRegisteredLastWeek();
    }
}

