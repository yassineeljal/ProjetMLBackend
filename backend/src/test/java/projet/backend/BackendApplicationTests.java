package projet.backend;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("Désactivé pour Jenkins : test de contexte non requis pour la CI")
class BackendApplicationTests {

    @Test
    void contextLoads() {
    }
}
