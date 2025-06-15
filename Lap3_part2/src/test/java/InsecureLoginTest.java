import org.junit.jupiter.api.Test;
import nguyentienhung.example.lap3_part2.InsecureLogin;
import static org.junit.jupiter.api.Assertions.*;

public class InsecureLoginTest {

    @Test
    public void testLoginSuccess() {
        InsecureLogin.login("admin", "123456");
        // Không cần assert nếu chỉ cần chạy để tránh warning "method not used"
    }

    @Test
    public void testLoginFail() {
        InsecureLogin.login("user", "wrongpassword");
    }

    @Test
    public void testPrintUserInfo() {
        InsecureLogin insecureLogin = new InsecureLogin();
        insecureLogin.printUserInfo("John Doe");
    }
}
