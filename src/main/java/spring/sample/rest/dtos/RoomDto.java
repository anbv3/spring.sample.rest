package spring.sample.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

public class RoomDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class room {
        @NotBlank
        @Length(min = 4, max = 15)
        private String name;

    }

    @Data
    public static class Response {
        private String name;
        private Date checkIn;
    }

}
