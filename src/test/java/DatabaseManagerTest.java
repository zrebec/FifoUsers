import org.example.DatabaseManager;
import org.example.SUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class DatabaseManagerTest {

    private DatabaseManager dbManager;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        dbManager = new DatabaseManager();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        dbManager.deleteAll();
        dbManager = null;
        System.setOut(System.out);
    }

    @Test
    void addUserTest() {
        dbManager.addUser(1, "a1", "Robert");
        dbManager.addUser(2, "a2", "Martin");
        SUser martin = dbManager.getUser(2);
        assertEquals("a2", martin.getUserGuid());
        assertEquals("Martin", martin.getUserName());
    }

    @Test
    void printAndDeleteUser() {
        dbManager.addUser(3, "a3", "Alice");
        dbManager.deleteUser(3);
        dbManager.printAll();
        assertFalse(outContent.toString().contains("Alice"));
    }

    @Test
    void printAndDeleteAllTest() {
        dbManager.addUser(4, "a4", "John");
        dbManager.addUser(5, "a5", "Doe");
        dbManager.printAll();
        assertTrue(outContent.toString().contains("John"));
        assertTrue(outContent.toString().contains("Doe"));
        dbManager.deleteAll();
        assertFalse(outContent.toString().contains("Robert"));
        assertFalse(outContent.toString().contains("Martin"));
    }
}
