package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.RegistrationPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Registration Form Tests using Page Object Model")
public class RegistrationTest extends BaseTest {

    static RegistrationPage registrationPage;
    static WebDriverWait wait;

    @BeforeAll
    static void initPage() {
        registrationPage = new RegistrationPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @BeforeEach
    void setupTest() {
        // Add a small delay between tests to avoid issues
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    @Order(1)
    @DisplayName("Should register successfully with valid data")
    void testSuccessfulRegistration() {
        registrationPage.navigate();

        String[] hobbies = {"Sports", "Reading"};
        registrationPage.completeRegistration(
                "John", "Doe", "john.doe@email.com", "Male",
                "1234567890", "15", "January", "1990",
                "Math", hobbies, "123 Main Street", "NCR", "Delhi"
        );

        // Wait a bit for modal to appear
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertTrue(registrationPage.isSuccessModalDisplayed(),
                "Success modal should be displayed");
        assertEquals("Thanks for submitting the form",
                registrationPage.getModalTitle(),
                "Modal title should match");

        registrationPage.closeModal();
    }

    @Test
    @Order(2)
    @DisplayName("Should handle missing required fields")
    void testMissingRequiredFields() {
        registrationPage.navigate();

        // Only fill first name and try to submit
        registrationPage.fillBasicInfo("John", "", "", "");
        registrationPage.submitForm();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Form should not submit successfully (no modal appears)
        assertFalse(registrationPage.isSuccessModalDisplayed(),
                "Form should not submit with missing required fields");
    }

    @ParameterizedTest(name = "Registration test: {0} {1}")
    @Order(3)
    @CsvSource({
            "Alice, Wilson, alice.wilson@email.com, Female, 9876543210, 25, March, 1992, Chemistry, Reading, 456 Elm St, NCR, Delhi",
            "Mike, Brown, mike.brown@email.com, Male, 5551234567, 30, April, 1985, Physics, Sports, 789 Oak Dr, Uttar Pradesh, Agra"
    })
    void testRegistrationWithInlineData(String firstName, String lastName, String email,
                                        String gender, String mobile, String day, String month,
                                        String year, String subject, String hobby, String address,
                                        String state, String city) {
        registrationPage.navigate();

        String[] hobbies = {hobby};
        registrationPage.completeRegistration(firstName, lastName, email, gender, mobile,
                day, month, year, subject, hobbies, address, state, city);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertTrue(registrationPage.isSuccessModalDisplayed(),
                "Registration should be successful for " + firstName + " " + lastName);
        registrationPage.closeModal();
    }

    @ParameterizedTest(name = "CSV File test: {0} {1}")
    @Order(4)
    @CsvFileSource(resources = "/registration-data.csv", numLinesToSkip = 1)
    void testRegistrationFromCSV(String firstName, String lastName, String email,
                                 String gender, String mobile, String day, String month,
                                 String year, String subject, String hobbies, String address,
                                 String state, String city) {
        registrationPage.navigate();

        // Handle potential null or empty hobbies
        String[] hobbyArray = (hobbies != null && !hobbies.trim().isEmpty()) ?
                hobbies.split(",") : new String[]{"Sports"};

        // Trim whitespace from hobby names
        for (int i = 0; i < hobbyArray.length; i++) {
            hobbyArray[i] = hobbyArray[i].trim();
        }

        registrationPage.completeRegistration(firstName, lastName, email, gender, mobile,
                day, month, year, subject, hobbyArray, address, state, city);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertTrue(registrationPage.isSuccessModalDisplayed(),
                "Registration should be successful for " + firstName + " " + lastName);
        registrationPage.closeModal();
    }

    @Test
    @Order(5)
    @DisplayName("Should validate email format")
    void testEmailValidation() {
        registrationPage.navigate();

        // Test with invalid email
        registrationPage.fillBasicInfo("Test", "User", "invalid-email", "1234567890");
        registrationPage.selectGender("Male");
        registrationPage.submitForm();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Form should not submit successfully
        assertFalse(registrationPage.isSuccessModalDisplayed(),
                "Form should not submit with invalid email");
    }

    @Test
    @Order(6)
    @DisplayName("Should validate mobile number format")
    void testMobileValidation() {
        registrationPage.navigate();

        // Test with invalid mobile number (less than 10 digits)
        registrationPage.fillBasicInfo("Test", "User", "test@email.com", "123");
        registrationPage.selectGender("Female");
        registrationPage.submitForm();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Form should not submit successfully
        assertFalse(registrationPage.isSuccessModalDisplayed(),
                "Form should not submit with invalid mobile number");
    }

    @Test
    @Order(7)
    @DisplayName("Should fill only required fields and submit successfully")
    void testMinimalRequiredFields() {
        registrationPage.navigate();

        // Fill only the absolutely required fields
        registrationPage.fillBasicInfo("Jane", "Smith", "jane@test.com", "9876543210");
        registrationPage.selectGender("Female");
        registrationPage.submitForm();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Should succeed with minimal required fields
        assertTrue(registrationPage.isSuccessModalDisplayed(),
                "Registration should succeed with minimal required fields");
        registrationPage.closeModal();
    }

    @AfterEach
    void cleanupTest() {
        // Close any open modals
        try {
            registrationPage.closeModal();
        } catch (Exception e) {
            // Ignore if no modal to close
        }
    }
}