package pl.stormit.eduquiz.statistic.userstatistic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.stormit.eduquiz.quizcreator.domain.user.UserService;

@Service
@RequiredArgsConstructor
public class UserStatisticFacadeImpl implements UserStatisticFacade {
    private final UserService userService;

    @Override
    public long getTotalNumberOfUsers() {
        return userService.getTotalNumberOfUsers();
    }

    @Override
    public long getNewUsersCountLast30Days() {
        return userService.getNewUsersCountLast30Days();
    }
}
