package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initialize() throws Exception {
        // load H2 db driver
        Class.forName("org.h2.Driver");

        // create connection
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:projectdb", "sa", "")) {
            // Create table SUSERS
            try (Statement stmt = conn.createStatement()) {
                String sql = """
                        CREATE TABLE SUSERS (
                        USER_ID INT PRIMARY KEY,
                        USER_GUID VARCHAR(255),
                        USER_NAME VARCHAR(255));
                        """;
                stmt.execute(sql);
            }
        }
    }
}
