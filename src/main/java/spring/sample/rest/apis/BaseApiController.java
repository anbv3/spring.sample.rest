package spring.sample.rest.apis;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import spring.sample.rest.exceptions.DuplicatedEntityException;
import spring.sample.rest.exceptions.EntityNotFoundException;

import java.util.UUID;

public class BaseApiController {
    private static final Logger LOG = LoggerFactory.getLogger(BaseApiController.class);

    @ExceptionHandler({AccessDeniedException.class, AuthenticationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public VndErrors handleAuthError(Exception e) {
        LOG.error("Internal error", e);
        return new VndErrors(UUID.randomUUID().toString(), e.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ServletRequestBindingException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public VndErrors handleValidationError(Exception e) {
        return new VndErrors(UUID.randomUUID().toString(), e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public VndErrors handleNotFoundException(Exception e) {
        return new VndErrors(UUID.randomUUID().toString(), e.getMessage());
    }

    @ExceptionHandler(DuplicatedEntityException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public VndErrors handleDuplicateError(Exception e) {
        return new VndErrors(UUID.randomUUID().toString(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public VndErrors handleUnexpectedException(Exception e) {
        LOG.error("Internal error", e);

        if (Strings.isNullOrEmpty(e.getMessage())) {
            return new VndErrors(UUID.randomUUID().toString(), e.toString());
        }

        return new VndErrors(UUID.randomUUID().toString(), e.getMessage());
    }
}
