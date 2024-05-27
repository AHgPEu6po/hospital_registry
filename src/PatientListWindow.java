import javax.swing.*;
import java.awt.*;

public class PatientListWindow extends JFrame {

    private JTable table;
    private final JTextField searchField;
    private final JButton searchButton;

    public PatientListWindow(JTable table) {

        setTitle("Список пацієнтів");
        setSize(850, 400);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        this.searchField = new JTextField();
        this.searchButton = new JButton("Пошук за ФІО");
        this.table = table;


        JPanel searchPanel = new JPanel(new GridLayout(1, 4));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(this.table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JTextField getSearchField(){
        return searchField;
    }

    public JButton getSearchButton(){
        return searchButton;
    }

    public void updateTable(JTable table){
        this.table = table;
    }
}