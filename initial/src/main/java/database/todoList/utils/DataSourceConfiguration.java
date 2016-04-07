package database.todoList.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

	private DataSource dataSource;

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        final String driverClassName = "com.mysql.jdbc.Driver";
        final String url = "jdbc:mysql://localhost:3306/todo_list";
        final String username = "root";
        final String password = "password";

		HikariConfig config = new HikariConfig();

		config.setDriverClassName(driverClassName);
		config.setJdbcUrl(url);
		config.setUsername(username);
		config.setPassword(password);
		dataSource = new HikariDataSource(config);
		return dataSource;
    }

	@Bean(name = "jdbcTemplate")
	public JdbcTemplate jdbcTemplate(DataSource dataSource){
		return new JdbcTemplate(this.dataSource);
	}
}

/*DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
dataSourceBuilder.driverClassName(driverClassName);
dataSourceBuilder.url(url);
dataSourceBuilder.username(username);
dataSourceBuilder.password(password);
return dataSourceBuilder.build();*/

/*BasicDataSource basicDataSource = new BasicDataSource();
basicDataSource.setDriverClassName(driverClassName);
basicDataSource.setUrl(url);
basicDataSource.setUsername(username);
basicDataSource.setPassword(password);
return basicDataSource;*/