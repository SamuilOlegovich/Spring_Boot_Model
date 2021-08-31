package spring.boot.users.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import spring.boot.users.enums.ContactOrder;
import spring.boot.users.db.Contact;
import spring.boot.users.repository.ContactRepository;

@Service
public final class ContactService {
    @Autowired
    private ContactRepository contactRepo;



    // возвращает количество контактов у пользователя с заданным идентификатором
    public long count(final Integer userId) {
        return this.contactRepo.count();
    }




    // возвращает список контактов заданного пользователя
    public List<Contact> list(final Integer userId, final ContactOrder order) {
        Sort sort;

        switch (order) {
            case TYPE_ASC:
                sort = Sort.by("type").ascending();
                break;
            case TYPE_DESC:
                sort = Sort.by("type").descending();
                break;
            case CONTACT_ASC:
                sort = Sort.by("contact").ascending();
                break;
            case CONTACT_DESC:
                sort = Sort.by("contact").descending();
                break;
            case DEFAULT:
            default:
                sort = Sort.by("type").ascending();
        }
        return this.contactRepo.findByUserId(userId, sort);
    }



    // возвращает контакт по заданному идентификатору
    public Contact find(final Integer id) {
        final Optional<Contact> optContact = this.contactRepo.findById(id);
        return optContact.isPresent() ? optContact.get() : null;
    }



    // сохраняет информацию о заданном контакте
    public void store(final Contact contact) {
        this.contactRepo.saveAndFlush(contact);
    }



    // удаляет контакт по заданному идентификатору
    public void kill(final Integer id) {
        this.contactRepo.deleteById(id);
    }



    // активирует/деактивирует заданный контакт
    public void activate(final Boolean isActive, final Integer id) {
        this.contactRepo.activate(isActive, id);
    }
}
