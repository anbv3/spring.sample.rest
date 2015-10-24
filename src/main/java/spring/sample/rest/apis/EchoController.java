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
import spring.sample.rest.dtos.EchoDto;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = {"/api/v1"}, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Echo")
public class EchoController extends BaseApiController {
    private static final Logger LOG = LoggerFactory.getLogger(EchoController.class);

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = "/echos/{msg}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal Server Error")
    })
    public EchoDto.Response echoBack(@PathVariable("msg") String msg) {
        LOG.debug("Message: {}", msg);
        EchoDto.Response response = new EchoDto.Response();
        response.setMessage(msg);

        return response;
    }

    @RequestMapping(value = "/echos", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad Request"),
            @ApiResponse(code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "Internal Server Error")
    })
    public EchoDto.Response echoCreate(@RequestBody @Valid EchoDto.Create request) {
        LOG.debug("Message: {}", request.getMessage());
        EchoDto.Response response = new EchoDto.Response();
        response.setMessage(request.getMessage());

        return modelMapper.map(response, EchoDto.Response.class);
    }

}
