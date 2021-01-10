package br.com.nkey.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Autowired
	BuildProperties buildProperties;
	
	@Bean
	public Docket buildApiDocumentation() {

		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.nkey.api"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(buildDataDocumentation());

	}

	private ApiInfo buildDataDocumentation() {

		return new ApiInfoBuilder()
					.title("NKey")
					.description("API de Teste criado para processo de seleção para empresa NKey Soluções Digitais")
					.version(buildProperties.getVersion())
					.contact(new Contact("Gustavo Santos Arruda", "https://github.com/gustavoarruda/", "gustavoanalistabr@gmail.com"))
				.license("Apache License Version 2.0")
				.build();

	}
	
}
