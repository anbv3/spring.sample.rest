package spring.sample.rest.apis;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import spring.sample.rest.dtos.RoomDto;
import spring.sample.rest.dtos.SampleUserDto;
import spring.sample.rest.exceptions.DuplicatedEntityException;
import spring.sample.rest.exceptions.EntityNotFoundException;
import spring.sample.rest.services.RoomService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = {"/api/v1"}, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Room")
public class RoomController extends BaseApiController {
    private static final Logger LOG = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoomService roomService;

    @RequestMapping(value = "/rooms", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal Server Error")
    })
    public List<RoomDto.Response> findAll() throws EntityNotFoundException {
        return roomService.findAll()
                          .stream()
                          .map(r -> modelMapper.map(r, RoomDto.Response.class))
                          .collect(Collectors.toList());
    }

    @RequestMapping(value = "/rooms/{roomId}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Not Found"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal Server Error")
    })
    public RoomDto.Response findById(@PathVariable("roomId") Long roomId) throws EntityNotFoundException {
        LOG.debug("roomId: {}", roomId);
        return modelMapper.map(roomService.findById(roomId), RoomDto.Response.class);
    }

    @RequestMapping(value = "/rooms", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Created"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad Request"),
            @ApiResponse(code = HttpServletResponse.SC_CONFLICT, message = "Conflict"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal Server Error")
    })
    public RoomDto.Response create(@RequestBody @Valid RoomDto.room request) throws DuplicatedEntityException {
        LOG.debug("name: {}", request.getName());
        return modelMapper.map(roomService.create(request), RoomDto.Response.class);
    }

    @RequestMapping(value = "/rooms/{roomId}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "No Content"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad Request"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Not Found"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal Server Error")
    })
    public void update(@PathVariable Long roomId,
                       @RequestBody @Valid RoomDto.room request) throws EntityNotFoundException {
        LOG.debug("name: {}", request.getName());
        roomService.update(roomId, request);
    }

    @RequestMapping(value = "/rooms/{roomId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "No Content"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Not Found"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal Server Error")
    })
    public void delete(@PathVariable Long roomId) {
        LOG.debug("roomId: {}", roomId);
        roomService.delete(roomId);
    }

    @RequestMapping(value = "/rooms/{roomId}/users", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Not Found"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal Server Error")
    })
    public List<SampleUserDto.Response> findUsersById(@PathVariable("roomId") Long roomId) throws EntityNotFoundException {
        LOG.debug("roomId: {}", roomId);
        return roomService.findUsersById(roomId)
                          .stream()
                          .map(u -> modelMapper.map(u, SampleUserDto.Response.class))
                          .collect(Collectors.toList());
    }
}
