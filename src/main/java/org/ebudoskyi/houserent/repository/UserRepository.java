package org.ebudoskyi.houserent.repository;

import org.ebudoskyi.houserent.model.Role;
import org.ebudoskyi.houserent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);
    List<User> findByName(String name); // точний пошук
    List<User> findByNameContainingIgnoreCase(String name); // пошук по частині імені
}
