package pl.stormit.eduquiz.statistic.userstatistic;

public interface UserStatisticFacade {

    long getTotalNumberOfUsers();

    long getNewUsersCountLast30Days();
}
