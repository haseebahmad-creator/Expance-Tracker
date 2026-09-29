import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame=new JFrame("Expanse Tracker");
        frame.setSize(1200,700);
        frame.getContentPane().setBackground(Color.white);

        JPanel lpnal=new JPanel();
        lpnal.setBounds(0,0,240,700);
        String[] COLM={"NAME","CATEGORY","DATE","AMOUNT"};
        DefaultTableModel modle=new DefaultTableModel(COLM,0);
        JTable TABLE=new JTable(modle);
        JScrollPane sp=new JScrollPane(TABLE);
        sp.setBounds(250,50,900,500);

        JLabel budget=new JLabel("Budget : Rs.0");
        budget.setBounds(20,10,150,25);
        JLabel expanse=new JLabel("Expanse name");
        expanse.setBounds(20,50,120,25);
        JTextField expancename=new JTextField();
        expancename.setBounds(20,75,200,30);
        JLabel amount=new JLabel("price :");
        amount.setBounds(20,110,120,25);
        JTextField price=new JTextField();
        price.setBounds(20,135,200,30);

        JLabel category=new JLabel("Category :");
        category.setBounds(20,170,120,25);
        JComboBox<String> categorys=new JComboBox<>(new String[]{
                "FOOD","Transportation"," Family care","Health care","Groceries ",
                "Entertainment ","Dating ","Education","OTHERS"," STATIONARY","RENT"
        });
        categorys.setBounds(20,195,200,30);

        JLabel dateLabel=new JLabel("Date :");
        dateLabel.setBounds(20,230,120,25);

        JTextField dateField=new JTextField("DD-MM-YYYY");
        dateField.setBounds(20,255,200,30);

        JLabel monbug=new JLabel("Monthly budget");
        monbug.setBounds(20,295,120,25);

        JTextField setBudget=new JTextField();
        setBudget.setBounds(20,320,200,30);



        JButton Add_expanse=new JButton("Add Expanse");
        Add_expanse.setBounds(20,360,200,30);
        JButton set_budget=new JButton("Set Budget");
        set_budget.setBounds(20,400,200,35);
        JLabel TOTAL=new JLabel("Total : Rs.0");
        TOTAL.setBounds(200,10,150,25);
        JLabel REMAIN=new JLabel("remaining : Rs.0");
        REMAIN.setBounds(300,10,150,25);
        JButton SAVEDATA=new JButton("save");
        SAVEDATA.setBounds(20,440,95,30);
        JButton load=new JButton("load");
        load.setBounds(125,440,95,30);
        JButton del=new JButton("Delete selected ");
        del.setBounds(20,480,200,30);
        SAVEDATA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {


                    FileWriter FW =new FileWriter("expenses.txt");

                    FW.write(budget.getText());
                    FW.write("\n");

                    for(int i=0;i<modle.getRowCount();i++) {

                        FW.write(
                                modle.getValueAt(i,0).toString()+","+
                                        modle.getValueAt(i,1).toString()+","+
                                        modle.getValueAt(i,2).toString()+","+
                                        modle.getValueAt(i,3).toString()
                        );
                        FW.write("\n");



                    }

                    FW.close();

                    JOptionPane.showMessageDialog(null,
                            "Data saved successfully to file EXPANCE.TXT");

                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage());
                }
            }
        });

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedReader br =
                            new BufferedReader(
                                    new FileReader("expenses.txt"));
                    modle.setRowCount(0);
                    String line = br.readLine();
                    if(line!=null) {
                        budget.setText(line);
                    }
                    while((line=br.readLine())!=null) {

                        String[] data = line.split(",");

                        if(data.length==4) {
                            modle.addRow(data);
                        }
                    }

                    br.close();

                    double total = 0;

                    for(int i=0;i<modle.getRowCount();i++) {
                        total += Double.parseDouble(
                                modle.getValueAt(i,3).toString());
                    }

                    TOTAL.setText("Total : Rs." + total);

                    try {
                        String b = budget.getText()
                                .replace("Budget : Rs.","");

                        double budgetValue =
                                Double.parseDouble(b);

                        REMAIN.setText(
                                "remaining : Rs." +
                                        (budgetValue-total));
                    }
                    catch(Exception ex) {
                    }
                    JOptionPane.showMessageDialog(null,
                            "Data loaded successfully");
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage());
                }
            }
        });










        frame.add(sp);
        frame.add(Add_expanse);
        frame.add(expanse);
        frame.add(expancename);
        frame.add(amount);
        frame.add(price);
        frame.add(category);
        frame.add(categorys);
        frame.add(dateLabel);
        frame.add(dateField);
        frame.add(monbug);
        frame.add(setBudget);
        frame.add(set_budget);
        frame.add(SAVEDATA);
        frame.add(load);
        frame.add(del);
        frame.add(budget);
        frame.add(TOTAL);
        frame.add(REMAIN);
        frame.add(lpnal);

        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Add_expanse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(expancename.getText().isEmpty() ||
                        price.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null,"Enter expense details");
                    return;
                }

                modle.addRow(new Object[]{
                        expancename.getText(),
                        categorys.getSelectedItem(),
                        dateField.getText(),
                        price.getText()
                });

                double total = 0;

                for(int i=0;i<modle.getRowCount();i++) {
                    total += Double.parseDouble(
                            modle.getValueAt(i,3).toString());
                }

                TOTAL.setText("Total : Rs." + total);

                try {
                    String b = budget.getText()
                            .replace("Budget : Rs.","");
                    double budgetValue = Double.parseDouble(b);

                    REMAIN.setText(
                            "remaining : Rs." + (budgetValue-total));
                }
                catch(Exception ex) {
                }

                expancename.setText("");
                price.setText("");
            }
        });

        set_budget.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(setBudget.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Enter budget");
                    return;
                }

                budget.setText("Budget : Rs." +
                        setBudget.getText());

                double total = 0;

                for(int i=0;i<modle.getRowCount();i++) {
                    total += Double.parseDouble(
                            modle.getValueAt(i,3).toString());
                }

                TOTAL.setText("Total : Rs." + total);

                try {
                    double budgetValue =
                            Double.parseDouble(setBudget.getText());

                    REMAIN.setText(
                            "remaining : Rs." + (budgetValue-total));
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid Budget");
                }
            }
        });

        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = TABLE.getSelectedRow();
                if(row==-1) {
                    JOptionPane.showMessageDialog(null,
                            "Select a row first");
                    return;
                }
                modle.removeRow(row);
                double total = 0;
                for(int i=0;i<modle.getRowCount();i++) {
                    total += Double.parseDouble(
                            modle.getValueAt(i,3).toString());
                }
                TOTAL.setText("Total : Rs." + total);

                try {
                    String b = budget.getText()
                            .replace("Budget : Rs.","");

                    double budgetValue =
                            Double.parseDouble(b);

                    REMAIN.setText(
                            "remaining : Rs." + (budgetValue-total));
                }
                catch(Exception ex) {
                }
            }
        });
    }
}