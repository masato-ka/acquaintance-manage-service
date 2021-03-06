package service.manage.acquaintance;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Profile("production")
@Configuration
public class MySQLConfiguration {

	private final DataSourceProperties dataSourceProperties;
	
	
	public MySQLConfiguration(DataSourceProperties dataSourceProperties){
		this.dataSourceProperties = dataSourceProperties; 
	}
	
	@ConfigurationProperties(prefix="spring.datasource.tomcat")
	@Bean(destroyMethod="close")
	DataSource realDataSource() throws URISyntaxException{
		
		DataSource dataSource = null;
		String url = null;
		String username = null;;
		String password = null;;
		
		
		String databaseUrl = System.getenv("CLEARDB_DATABASE_URL");
		if(databaseUrl != null){
			
			URI dbUri = new URI(databaseUrl);
			url = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();
			username = dbUri.getUserInfo().split(":")[0];
			password = dbUri.getUserInfo().split(":")[1];
			
		}else{
			
			url = this.dataSourceProperties.getUrl();
			username = this.dataSourceProperties.getUsername();
			password = this.dataSourceProperties.getPassword();
			
		}
		
		DataSourceBuilder factory = DataSourceBuilder
				.create(this.dataSourceProperties.getClassLoader())
				
				.url(url)
				.username(username)
				.password(password);
		dataSource = factory.build();
		return dataSource;
		
	}
	
}
