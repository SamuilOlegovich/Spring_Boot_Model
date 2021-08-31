package spring.boot.users.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.boot.users.db.Contact;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    // возвращает количество контактов у пользователя с заданным идентификатором
    public long countByUserId(final Integer userId);



    // возвращает все контакты заданного пользователя
    public List<Contact> findByUserId(final Integer userId, final Sort sort);



    // активирует/деактивирует заданный контакт
    @Modifying
    @Query("update Contact c set c.isActive = ?1 where c.id = ?2")
    public void activate(final Boolean isActive, final Integer id);
}
