package tienhung.service.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTest {

    private final AccountService service = new AccountService();

    @ParameterizedTest(name = "Register Test {index}: username={0}, password={1}, email={2} => expected={3}")
    @CsvFileSource(resources = "/test-data.csv", numLinesToSkip = 1)
    void testRegisterAccount(String username, String password, String email, boolean expected, String expectedType) {
        boolean result = service.registerAccount(username, password, email);
        assertEquals(expected, result,
                () -> "Register failed for: username=" + username + ", password=" + password + ", email=" + email);
    }

    @ParameterizedTest(name = "EmailType Test {index}: email={2} => expectedType={4}")
    @CsvFileSource(resources = "/test-data.csv", numLinesToSkip = 1)
    void testAccountTypeByEmailDomain(String username, String password, String email, boolean expected, String expectedType) {
        String actualType = service.getAccountTypeByEmailDomain(email);
        assertEquals(expectedType, actualType,
                () -> "Expected type: " + expectedType + " but got: " + actualType + " for email: " + email);
    }
}
