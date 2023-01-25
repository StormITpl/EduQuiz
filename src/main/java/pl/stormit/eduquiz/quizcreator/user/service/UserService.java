package pl.stormit.eduquiz.quizcreator.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.eduquiz.quizcreator.user.domain.model.User;
import pl.stormit.eduquiz.quizcreator.user.domain.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow();
    }

    @Transactional
    public User createUser(User userRequest) {
        User user = new User();
        user.setNickname(userRequest.getNickname());
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(UUID id, User userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow();
        user.setNickname(userRequest.getNickname());
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
