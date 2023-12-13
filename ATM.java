import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATM {
    private BankAccount userAccount;
    private static ATMGUI atmGUI;

    public ATM(BankAccount account) {
        this.userAccount = account;
    }

    public void withdraw(double amount) {
        if (userAccount.getBalance() >= amount) {
            userAccount.withdraw(amount);
            atmGUI.displayMessage("Withdrawn: " + amount);
        } else {
            atmGUI.displayMessage("Insufficient balance.");
        }
    }

    public void deposit(double amount) {
        userAccount.deposit(amount);
        atmGUI.displayMessage("Deposited: " + amount);
    }

    public double checkBalance() {
        return userAccount.getBalance();
    }

    public void setGUI(ATMGUI gui) {
        atmGUI = gui;
    }

    public static void main(String[] args) {
        BankAccount userAccount = new BankAccount(1000); // Initialize with initial balance
        ATM atm = new ATM(userAccount);
        ATMGUI atmGUI = new ATMGUI(atm);
        atm.setGUI(atmGUI);
    }
}

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public double getBalance() {
        return balance;
    }
}

class ATMGUI {
    private static JTextArea displayArea;

    public ATMGUI(ATM atm) {
        JFrame frame = new JFrame("ATM Machine");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel amountLabel = new JLabel("Amount: ");
        JTextField amountField = new JTextField(10);
        amountField.setHorizontalAlignment(JTextField.CENTER);

        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton checkBalanceButton = new JButton("Check Balance");

        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);
        displayArea.setFocusable(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(amountLabel, gbc);

        gbc.gridx = 1;
        panel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(withdrawButton, gbc);

        gbc.gridx = 1;
        panel.add(depositButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(checkBalanceButton, gbc);

        JPanel displayPanel = new JPanel(new BorderLayout());
        displayPanel.add(displayArea, BorderLayout.CENTER);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(displayPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(amountField.getText());
                atm.withdraw(amount);
            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(amountField.getText());
                atm.deposit(amount);
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double balance = atm.checkBalance();
                displayMessage("Current Balance: " + balance);
            }
        });
    }

    public void displayMessage(String message) {
        displayArea.setText(message + "\n");
    }
}
