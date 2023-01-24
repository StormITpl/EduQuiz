package pl.stormit.eduquiz.createquiz.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.stormit.eduquiz.createquiz.user.domain.model.User;
import pl.stormit.eduquiz.createquiz.user.domain.repository.UserRepository;

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
    public User createQuiz(User userRequest) {
        User user = new User();
        user.setNickname(userRequest.getNickname());
        user.setCreateQuiz(user.getCreateQuiz());
        return userRepository.save(user);
    }

    @Transactional
    public User updateQuiz(UUID id, User userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow();
        user.setNickname(userRequest.getNickname());
        user.setCreateQuiz(userRequest.getCreateQuiz());
        return userRepository.save(user);
    }

    @Transactional
    public void deleteQuiz(UUID id) {
        userRepository.deleteById(id);
    }
}
