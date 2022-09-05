package by.sva.Sarafan2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import by.sva.Sarafan2.entity.User;

public interface UserDetailRepository extends JpaRepository<User, String> {

}
