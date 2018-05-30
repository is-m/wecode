package com.chinasoft.it.wecode.sign.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).useDefaultResponseMessages(false)
				.globalOperationParameters(globalParameters())
				// .globalResponseMessage(requestMethod, responseMessages)
				.select().apis(RequestHandlerSelectors.basePackage("com.chinasoft.it.wecode"))
				.paths(PathSelectors.any()).build();
	}

	private Parameter parameter(String paramType, String name, String description, String valueType,
			String defaultValue) {
		return new ParameterBuilder().name(name).defaultValue(defaultValue).description(description)
				.modelRef(new ModelRef(valueType)).parameterType(paramType).required(false).build();
	}

	private List<Parameter> globalParameters() {
		return Arrays.asList(parameter("header", "X-Authentication", "token", "string", "ZXlKaGJHY2lPaUpJVXpJMU5pSjkuZXlKemRXSWlPaUlrWjNWbGMzUWtJaXdpYVdGMElqb3hOVEkzTmprek1ERXdMQ0psZUhBaU9qRTFNamMyT1RNd056QjkuTDcydS1xR24wZXZsblM2dGtVZGdzdnFnbGRfZzNNa3A1b2Z1MzZCRy1ubw=="));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Spring Boot中使用Swagger2构建RESTful APIs")
				.description("更多Spring Boot相关文章请关注：http://blog.didispace.com/")
				.termsOfServiceUrl("http://blog.didispace.com/").version("1.0").build();
	}
}
