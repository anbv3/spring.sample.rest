package spring.sample.rest.configs;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.ApiSelector;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket swaggerSpringMvcPlugin() {
        ApiSelector apiSelector = new ApiSelector(
                RequestHandlerSelectors.withClassAnnotation(Api.class), // select @Api only.
                PathSelectors.ant("/api/v1"));
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        return docket
                .ignoredParameterTypes(
                        BindingResult.class,
                        HttpServletRequest.class,
                        Locale.class
                )
                .select()
                .apis(apiSelector.getRequestHandlerSelector())
                .build();
    }
}
