package spring.sample.rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.sample.rest.domains.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findFirstByNameIgnoreCase(String name);
}
