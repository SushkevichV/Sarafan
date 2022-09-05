package by.sva.Sarafan2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import by.sva.Sarafan2.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
