import javax.swing.*;
import java.io.*;

public class ChangePasswordController {
    private final ChangePasswordWindow view;

    private final Object user;

    public ChangePasswordController(Object user) {
        this.user = user;
        this.view = new ChangePasswordWindow();
        initController();
    }

    private void initController() {
        view.getChangeButton().addActionListener(e -> handleChangePassword());
    }

    private void handleChangePassword() {
        String oldPassword = view.getOldPasswordField().getText();
        String newPassword = view.getNewPasswordField().getText();
        String repitPassword = new String(view.getRepitPasswordField().getPassword());

        if (newPassword.length() < 8) {
            JOptionPane.showMessageDialog(null, "Пароль повинен містити не менше 8 символів", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!newPassword.equals(repitPassword)) {
            JOptionPane.showMessageDialog(null, "Паролі не співпадають", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String filePath = "src/resources/";
        if (user instanceof Patient){
            filePath += "patients/" + ((Patient) user).getSurname() + ((Patient) user).getName() + "/password.txt";
        } else if (user instanceof Doctor){
            filePath += "doctors/" + ((Doctor) user).getSurname() + ((Doctor) user).getName() + "/password.txt";
        } else {
            filePath += "admin/password.txt";
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String password = reader.readLine();
            reader.close();
            if (!password.equals(oldPassword)) {
                JOptionPane.showMessageDialog(null, "Неправильний пароль", "Помилка", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Помилка під час перевірки пароля", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }


        File newPasswordFile = new File(filePath);
        try {
            FileWriter writer = new FileWriter(newPasswordFile);
            writer.write(newPassword);
            writer.close();
            JOptionPane.showMessageDialog(null, "Пароль успішно змінено");
            view.dispose();
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Помилка під час зміни паролю", "Помилка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
