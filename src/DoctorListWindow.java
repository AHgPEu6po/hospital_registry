import javax.swing.*;
import java.awt.*;


public class DoctorListWindow extends JFrame {

    private JTable table;
    private final JTextField searchField;
    private final JButton searchButton;
    private final JComboBox<String>  searchTypeComboBox;

    public DoctorListWindow(JTable table) {
        setTitle("Список лікарів");
        setSize(600, 400);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        this.searchField = new JTextField();
        this.searchTypeComboBox = new JComboBox<>(new String[]{"ФІО", "Спеціальність"});
        this.searchButton = new JButton("Пошук");
        this.table = table;

        JPanel searchPanel = new JPanel(new GridLayout(1, 4));
        searchPanel.add(searchTypeComboBox);
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

    public String getSearchType(){
        return (String) searchTypeComboBox.getSelectedItem();
    }

    public void updateTable(JTable table){
        this.table = table;
    }

}