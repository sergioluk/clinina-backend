package clinina.vet.api.controller;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        // Carrega o arquivo secrets.env do diretório /etc/secrets/
        Dotenv dotenv = Dotenv.configure()
                .directory("/etc/secrets/")
                .filename("secrets.env")
                .load();
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dotenv.get("DATABASE_URL"));
        config.setUsername(dotenv.get("DATABASE_USERNAME"));
        config.setPassword(dotenv.get("DATABASE_PASSWORD"));
        return new HikariDataSource(config);
    }
}
