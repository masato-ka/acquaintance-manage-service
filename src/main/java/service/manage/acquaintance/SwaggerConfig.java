package service.manage.acquaintance;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket document() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
//				.paths(PathSelectors.any())
				.paths(PathSelectors.ant("/api/v1/**"))
				
				.build()
	            .apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("顔認証・知人管理API")
	        .version("1.0")
	        .build();
	}
	
}
