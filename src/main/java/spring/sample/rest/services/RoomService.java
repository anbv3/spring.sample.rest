package spring.sample.rest.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.sample.rest.domains.Room;
import spring.sample.rest.domains.SampleUser;
import spring.sample.rest.dtos.RoomDto;
import spring.sample.rest.exceptions.DuplicatedEntityException;
import spring.sample.rest.exceptions.EntityNotFoundException;
import spring.sample.rest.repositories.RoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomService {
    private final Logger LOG = LoggerFactory.getLogger(RoomService.class);

    @Autowired
    private RoomRepository roomRepository;


    public List<Room> findAll() throws EntityNotFoundException {
        return Optional.ofNullable(roomRepository.findAll()).orElse(new ArrayList<>());
    }

    public Room findById(Long roomId) throws EntityNotFoundException {
        return Optional.ofNullable(roomRepository.findOne(roomId))
                       .orElseThrow(() -> new EntityNotFoundException("Room: Not Found"));
    }

    public Room create(RoomDto.room request) throws DuplicatedEntityException {
        Room existRoom = roomRepository.findFirstByNameIgnoreCase(request.getName());
        if (existRoom != null) {
            throw new DuplicatedEntityException("Room Duplication: " + request.getName());
        }

        Room room = Room.create(request.getName());
        return roomRepository.save(room);
    }

    public void update(Long roomId, RoomDto.room request) throws EntityNotFoundException {
        Room room = Optional.ofNullable(roomRepository.findOne(roomId))
                            .orElseThrow(() -> new EntityNotFoundException("Room: Not Found"));

        room.setName(request.getName());
        roomRepository.save(room);
    }

    public void delete(Long roomId) {
        Room room;
        try {
            room = Optional.ofNullable(roomRepository.findOne(roomId))
                           .orElseThrow(() -> new EntityNotFoundException("Room: Not Found"));

            roomRepository.delete(room);
        } catch (EntityNotFoundException e) {
            LOG.error("", e.getMessage());
        }

    }

    public List<SampleUser> findUsersById(Long roomId) throws EntityNotFoundException {
        Room room = Optional.ofNullable(roomRepository.findOne(roomId))
                            .orElseThrow(() -> new EntityNotFoundException("Room: Not Found"));

        return room.getSampleUsers();
    }
}
