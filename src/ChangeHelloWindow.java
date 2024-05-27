import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ChangeHelloWindow extends JDialog {

    public ChangeHelloWindow() {

        setTitle("Зміна вітання");
        setSize(330, 80);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 2));
        setModal(true);

        JLabel helloLabel = new JLabel("Введіть нове вітання:");
        JTextField helloField = new JTextField(10);
        JButton changeButton = new JButton("Застосувати");


        changeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String hello = helloField.getText();

                File newPasswordHelloFile = new File("src/resources/admin/hello.txt");
                try {
                    FileWriter writer = new FileWriter(newPasswordHelloFile);
                    writer.write(hello);
                    writer.close();
                    JOptionPane.showMessageDialog(null, "Вітання успішно змінено");
                    dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Помилка під час зміни вітання", "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(helloLabel);
        add(helloField);
        add(new JLabel(""));
        add(changeButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
