import org.junit.Test;
import javax.swing.*;
import java.io.*;

import static org.junit.Assert.*;

public class PasswordAdminTest {

    @Test
    public void testReadPasswordFromFile() {
        String tempDir = System.getProperty("java.io.tmpdir");
        String tempFileName = tempDir + "/testPassword.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFileName))) {
            writer.write("correctPassword");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Не вдалося створити тимчасовий файл з паролем");
        }

        PasswordAdmin passwordAdmin = new PasswordAdmin(new JFrame(), false) {
            @Override
            void readPasswordFromFile() {
                try (BufferedReader reader = new BufferedReader(new FileReader(tempFileName))) {
                    PASSWORD = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        passwordAdmin.readPasswordFromFile();

        assertEquals("correctPassword", PasswordAdmin.PASSWORD);

        new java.io.File(tempFileName).delete();
    }

    @Test
    public void testReadHelloFromFile() {
        String tempDir = System.getProperty("java.io.tmpdir");
        String tempFileName = tempDir + "/testHello.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFileName))) {
            writer.write("Hello, Admin!");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Не вдалося створити тимчасовий файл з текстом привітання");
        }

        PasswordAdmin passwordAdmin = new PasswordAdmin(new JFrame(), false) {
            @Override
            String readHelloFromFile() {
                try (BufferedReader reader = new BufferedReader(new FileReader(tempFileName))) {
                    return reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        String helloMessage = passwordAdmin.readHelloFromFile();

        assertEquals("Hello, Admin!", helloMessage);

        new java.io.File(tempFileName).delete();
    }
}
