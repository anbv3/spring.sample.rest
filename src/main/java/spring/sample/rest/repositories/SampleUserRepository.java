package spring.sample.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.sample.rest.domains.SampleUser;

public interface SampleUserRepository extends JpaRepository<SampleUser, Long> {}
