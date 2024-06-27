package pl.stormit.eduquiz.authenticator;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import pl.stormit.eduquiz.statistic.userstatistic.UserStatisticFacade;

public class AuthenticationEventsListener {

    private final UserStatisticFacade userStatisticFacade;

    public AuthenticationEventsListener(final UserStatisticFacade userStatisticFacade) {
        this.userStatisticFacade = userStatisticFacade;
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        String username = success.getAuthentication().getName();
        userStatisticFacade.incrementLoginCount(username);
    }
}
