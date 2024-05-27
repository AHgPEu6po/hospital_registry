import javax.swing.*;
import java.awt.*;

public class ChangePasswordWindow extends JDialog {

    private final JButton changeButton;
    private final JTextField oldPasswordField;
    private final JTextField newPasswordField;
    private final JPasswordField repitPasswordField;

    public ChangePasswordWindow() {

        setTitle("Зміна паролю");
        setSize(330, 150);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(4, 2));
        setModal(true);

        JLabel oldPasswordLabel = new JLabel("Введіть старий пароль:");
        this.oldPasswordField = new JTextField(10);
        JLabel newPasswordLabel = new JLabel("Введіть новий пароль:");
        this.newPasswordField = new JTextField(10);
        JLabel repitPasswordLabel = new JLabel("Повторіть пароль:");
        this.repitPasswordField = new JPasswordField(10);
        this.changeButton = new JButton("Застосувати");

        add(oldPasswordLabel);
        add(oldPasswordField);
        add(newPasswordLabel);
        add(newPasswordField);
        add(repitPasswordLabel);
        add(repitPasswordField);
        add(new JLabel(""));
        add(changeButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JButton getChangeButton() {
        return changeButton;
    }

    public JTextField getOldPasswordField() {
        return oldPasswordField;
    }

    public JTextField getNewPasswordField() {
        return newPasswordField;
    }

    public JPasswordField getRepitPasswordField() {
        return repitPasswordField;
    }
}
