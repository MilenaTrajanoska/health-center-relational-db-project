package lekarskaordinacija.app.service;

import lekarskaordinacija.app.model.Person;
import lekarskaordinacija.app.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    public List<Person> getAllUsers() {
        return personRepository.findAll();
    }
}
