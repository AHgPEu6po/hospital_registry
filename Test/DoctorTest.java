import org.junit.Test;
import static org.junit.Assert.*;

public class DoctorTest {

    private final Doctor doctor = new Doctor("Петров", "Іван", "Олексійович", "Чоловіча", "Лікар", "Перша");


    @Test
    public void testGetSurname() {
        assertEquals("Петров", doctor.getSurname());
    }

    @Test
    public void testGetName() {
        assertEquals("Іван", doctor.getName());
    }

    @Test
    public void testGetFathername() {
        assertEquals("Олексійович", doctor.getFathername());
    }

    @Test
    public void testGetGender() {
        assertEquals("Чоловіча", doctor.getGender());
    }

    @Test
    public void testGetJob() {
        assertEquals("Лікар", doctor.getJob());
    }

    @Test
    public void testGetShift() {
        assertEquals("Перша", doctor.getShift());
    }
}
