import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import javax.swing.*;
import java.io.*;

import static org.junit.Assert.assertEquals;

public class PasswordDoctorTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testReadPasswordFromFile() throws IOException {
        String expectedPassword = "testPassword";
        File tempFile = temporaryFolder.newFile("password.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(expectedPassword);
        }
        PasswordDoctor passwordDoctor = new PasswordDoctor(new JFrame(), false);
        PasswordDoctor.ADRESSA = tempFile.getAbsolutePath();

        passwordDoctor.readPasswordFromFile();
        String actualPassword = PasswordDoctor.PASSWORD;

        assertEquals(expectedPassword, actualPassword);
    }
}
