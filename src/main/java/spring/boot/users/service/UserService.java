package spring.boot.users.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import spring.boot.users.enums.UserOrder;
import spring.boot.users.db.User;
import spring.boot.users.repository.UserRepository;

@Service
public final class UserService {
    public static int DEFAULT_PAGE  = 0;  // номер страницы по умолчанию
    public static int DEFAULT_COUNT = 20; // количество пользователей на страницу по умолчанию

    @Autowired
    private UserRepository userRepo;



    // возвращает общее количество всех пользователей
    public long count() {
        return this.userRepo.count();
    }



    // возвращает список пользователей по заданным параметрам
    public List<User> list(final int page, final int count, final UserOrder userOrder) {
        Sort sort;

        switch (userOrder) {
            case LOGIN_ASC:
                sort = Sort.by("login").ascending();
                break;
            case LOGIN_DESC:
                sort = Sort.by("login").descending();
                break;
            case EMAIL_ASC:
                sort = Sort.by("email").ascending();
                break;
            case EMAIL_DESC:
                sort = Sort.by("email").descending();
                break;
            case LAST_NAME_ASC:
                sort = Sort.by("last_name").ascending();
                break;
            case LAST_NAME_DESC:
                sort = Sort.by("last_name").descending();
                break;
            case DEFAULT:
            default:
                sort = Sort.by("login").ascending();
        }

        return this.userRepo.findAll(PageRequest.of(page, count, sort)).getContent();
    }



    // возвращает список пользователей на первой странице с заданной сортировкой
    public List<User> list(final UserOrder userOrder) throws Exception {
        return this.list(DEFAULT_PAGE, DEFAULT_COUNT, userOrder);
    }



    // возвращает пользователя по заданному идентификатору
    public User find(final Integer id) {
        final Optional<User> optUser = this.userRepo.findById(id);
        return optUser.isPresent() ? optUser.get() : null;
    }



    // возвращает пользователя по заданному логину
    public User find(final String login) {
        return this.userRepo.findFirstByLogin(login);
    }



    // сохраняет информацию о заданном пользователе
    public void store(final User user) {
        this.userRepo.saveAndFlush(user);
    }



    // удаляет пользователя по заданному идентификатору
    public void kill(final Integer id) {
        this.userRepo.deleteById(id);
    }



    // активирует/деактивирует заданного пользователя
    public void activate(final Boolean isActive, final Integer id) {
        this.userRepo.activate(isActive, id);
    }
}
