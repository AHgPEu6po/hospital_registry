import org.junit.Test;
import static org.junit.Assert.*;

public class PatientTest {

    private final Patient patient = new Patient("Іванов", "Петро", "Олександрович", "Чоловіча", "1990-01-01", "1234567890");

    @Test
    public void testGetSurname() {
        assertEquals("Іванов", patient.getSurname());
    }

    @Test
    public void testGetName() {
        assertEquals("Петро", patient.getName());
    }

    @Test
    public void testGetFathername() {
        assertEquals("Олександрович", patient.getFathername());
    }

    @Test
    public void testGetGender() {
        assertEquals("Чоловіча", patient.getGender());
    }

    @Test
    public void testGetBirthday() {
        assertEquals("1990-01-01", patient.getBirthday());
    }

    @Test
    public void testGetId() {
        assertEquals("1234567890", patient.getId());
    }
}
