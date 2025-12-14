package kr.ac.jbnu.ksm.assignment2.user.repository;

import kr.ac.jbnu.ksm.assignment2.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>
{
    Optional<User> findByLoginId(String loginId);
}