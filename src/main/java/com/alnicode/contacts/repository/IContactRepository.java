package com.alnicode.contacts.repository;

import com.alnicode.contacts.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IContactRepository extends JpaRepository<Contact, Long> { }
