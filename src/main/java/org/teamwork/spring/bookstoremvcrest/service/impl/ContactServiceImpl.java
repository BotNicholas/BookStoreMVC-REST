package org.teamwork.spring.bookstoremvcrest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamwork.spring.bookstoremvcrest.mapper.abstraction.AbstractMapper;
import org.teamwork.spring.bookstoremvcrest.mapper.simplicity.DefaultMapper;
import org.teamwork.spring.bookstoremvcrest.model.Contact;
import org.teamwork.spring.bookstoremvcrest.model.RefContactType;
import org.teamwork.spring.bookstoremvcrest.model.dto.ContactDTO;
import org.teamwork.spring.bookstoremvcrest.repository.ContactRepository;
import org.teamwork.spring.bookstoremvcrest.repository.RefContactTypeRepository;
import org.teamwork.spring.bookstoremvcrest.service.DefaultService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements DefaultService<ContactDTO, Contact, Integer> {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private RefContactTypeRepository contactTypeRepository;
    @Autowired
    private AbstractMapper mapper;
    @Override
    public List<ContactDTO> findAll() {
        List<Contact> contacts = contactRepository.findAll();
        List<ContactDTO> contactDTOS = contacts.stream().map(contact -> mapper.toDTO(contact, ContactDTO.class)).collect(Collectors.toList());

        return contactDTOS;
    }

    @Override
    public ContactDTO findByKey(Integer key) {
        return mapper.toDTO(contactRepository.findById(key), ContactDTO.class);
    }

    @Override
    public ContactDTO save(ContactDTO obj) {
        Contact contact = contactRepository.save(mapper.toEntity(obj, Contact.class));

        return mapper.toDTO(contact, ContactDTO.class);
    }

    @Override
    public ContactDTO update(Integer key, ContactDTO obj) {
        Contact contact = contactRepository.findById(key).orElse(new Contact());

        RefContactType contactType = contact.getContactType();
        if (!contact.getContactType().getCode().equals(obj.getContactType().getCode())) {
            contactType = contactTypeRepository.findById(obj.getContactType().getCode()).orElse(null);
        }

        contact.setContactType(contactType);
        contact.setFirstname(obj.getFirstname());
        contact.setLastname(obj.getLastname());
        contact.setWorkPhone(obj.getWorkPhone());
        contact.setCellPhone(obj.getCellPhone());
        contact.setOtherDetails(obj.getOtherDetails());

        contactRepository.save(contact);

        return mapper.toDTO(contact, ContactDTO.class);
    }

    @Override
    public void delete(Integer key) {
        contactRepository.deleteById(key);
    }
}
