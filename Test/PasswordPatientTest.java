import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import javax.swing.*;
import java.io.*;

import static org.junit.Assert.assertEquals;

public class PasswordPatientTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testReadPasswordFromFile() throws IOException {
        String expectedPassword = "testPassword";
        File tempFile = temporaryFolder.newFile("password.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write(expectedPassword);
        }
        PasswordPatient passwordPatient = new PasswordPatient(new JFrame(), false);
        PasswordPatient.ADRESSA = tempFile.getAbsolutePath();

        passwordPatient.readPasswordFromFile();
        String actualPassword = PasswordPatient.PASSWORD;

        assertEquals(expectedPassword, actualPassword);
    }
}
