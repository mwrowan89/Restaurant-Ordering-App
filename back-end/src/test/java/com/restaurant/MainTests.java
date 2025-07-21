package com.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@ActiveProfiles("test")
class MainTests {

    @Test
    void contextLoads() {
    }
    
    @Test
    void testMainMethod() {
        try (MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
            // Arrange: set up the mock to do nothing when run is called
            mocked.when(() -> SpringApplication.run(eq(Main.class), any(String[].class)))
                .thenReturn(null);
            
            // Act: call the main method which will use mocked SpringApplication
            String[] args = new String[]{"arg1", "arg2"};
            Main.main(args);
            
            // Assert: verify that run was called with the expected arguments
            mocked.verify(() -> SpringApplication.run(eq(Main.class), eq(args)), Mockito.times(1));
        }
    }
}
