import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SchemaCheckRunner implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("SchemaCheckRunner is running...");
        String currentSchema = jdbcTemplate.queryForObject("SHOW search_path", String.class);
        System.out.println("Current search_path: " + currentSchema);
    }
}
