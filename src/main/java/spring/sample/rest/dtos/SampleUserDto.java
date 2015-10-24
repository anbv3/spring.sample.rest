package spring.sample.rest.dtos;

import lombok.Data;

public class SampleUserDto {
    @Data
    public static class Response {
        private String name;
        private String email;
    }
}
