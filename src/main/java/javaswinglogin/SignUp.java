package javaswinglogin;

import javafx.application.Platform;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUp extends JPanel {

    private JTextField fname;
    private JTextField emailAddress;
    private JPasswordField pass;


    public SignUp() {
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(51, 102, 255));
        rightPanel.setPreferredSize(new Dimension(400, 500));
        rightPanel.setLayout(new GridBagLayout());

        JLabel jLabel9 = new JLabel(new ImageIcon("src/main/resources/com/beritamedia/app/Black_White_Elegant_Monogram_Initial_Name_Logo-removebg-preview.png"));
        JLabel jLabel3 = new JLabel("copyright © Kelompok 6 BeritaKita");
        jLabel3.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
        jLabel3.setForeground(new Color(204, 204, 204));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(jLabel9, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 0, 0);
        rightPanel.add(jLabel3, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.VERTICAL;
        this.add(rightPanel, gbc);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(51, 102, 255)));
        leftPanel.setPreferredSize(new Dimension(400, 500));
        leftPanel.setLayout(new GridBagLayout());

        JLabel jLabel2 = new JLabel("Sign Up");
        jLabel2.setFont(new Font("Fira Code", Font.BOLD, 46));
        jLabel2.setForeground(new Color(51, 102, 255));

        JLabel jLabel4 = new JLabel("Full name");
        jLabel4.setFont(new Font("Impact", Font.PLAIN, 24));
        jLabel4.setForeground(new Color(51, 102, 255));

        fname = new JTextField();
        fname.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fname.setForeground(new Color(102, 102, 102));
        fname.setPreferredSize(new Dimension(340, 40));

        JLabel jLabel5 = new JLabel("Email");
        jLabel5.setFont(new Font("Impact", Font.PLAIN, 24));
        jLabel5.setForeground(new Color(51, 102, 255));

        emailAddress = new JTextField();
        emailAddress.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailAddress.setForeground(new Color(102, 102, 102));
        emailAddress.setPreferredSize(new Dimension(340, 40));

        JLabel jLabel6 = new JLabel("Password");
        jLabel6.setFont(new Font("Impact", Font.PLAIN, 24));
        jLabel6.setForeground(new Color(51, 102, 255));

        pass = new JPasswordField();
        pass.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pass.setForeground(new Color(102, 102, 102));
        pass.setPreferredSize(new Dimension(340, 40));

        JButton signUpBtn = new JButton("Sign Up");
        signUpBtn.setFont(new Font("Verdana", Font.PLAIN, 14));
        signUpBtn.setForeground(Color.WHITE);
        signUpBtn.setBackground(new Color(51, 102, 255));
        signUpBtn.setPreferredSize(new Dimension(100, 40));
        signUpBtn.addActionListener(evt -> signUpBtnActionPerformed(evt));

        JLabel jLabel7 = new JLabel("I have an account");
        jLabel7.setFont(new Font("Cambria", Font.BOLD, 12));
        jLabel7.setForeground(new Color(255, 0, 0));

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Verdana", Font.PLAIN, 14));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setBackground(new Color(51, 153, 255));
        loginBtn.setPreferredSize(new Dimension(100, 40));
        loginBtn.addActionListener(evt -> loginBtnActionPerformed(evt));

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 50, 20, 0); // Adjusted left margin for title
        gbc.anchor = GridBagConstraints.CENTER;
        leftPanel.add(jLabel2, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 50, 5, 0); // Adjusted left margin for labels and fields
        gbc.anchor = GridBagConstraints.LINE_START;
        leftPanel.add(jLabel4, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 50, 5, 0); // Adjusted left margin for labels and fields
        leftPanel.add(fname, gbc);

        gbc.gridy++;
        leftPanel.add(jLabel5, gbc);

        gbc.gridy++;
        leftPanel.add(emailAddress, gbc);

        gbc.gridy++;
        leftPanel.add(jLabel6, gbc);

        gbc.gridy++;
        leftPanel.add(pass, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 50, 5, 0); // Adjusted left margin for buttons
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        leftPanel.add(signUpBtn, gbc);

        gbc.gridx = 0;
        gbc.insets = new Insets(20, 50, 5, 0); // Adjusted left margin for buttons
        gbc.anchor = GridBagConstraints.LINE_END;
        leftPanel.add(loginBtn, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 50, 0, 0); // Adjusted left margin for labels and fields
        gbc.anchor = GridBagConstraints.CENTER;
        leftPanel.add(jLabel7, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        this.add(leftPanel, gbc);
    }

    private void signUpBtnActionPerformed(java.awt.event.ActionEvent evt) {
        String fullNameText = fname.getText();
        String emailText = emailAddress.getText();
        String passwordText = new String(pass.getPassword());

        String url = "jdbc:mysql://localhost:3306/java_user_database";
        String user = "root";
        String pass = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            String query = "INSERT INTO users (full_name, email, password) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, fullNameText);
            pst.setString(2, emailText);
            pst.setString(3, passwordText); // Pastikan untuk mengenkripsi password sebelum menyimpannya

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "User registered successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        Login.display();
        SwingUtilities.getWindowAncestor(this).dispose();
    }

    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {
        Login.display();
        SwingUtilities.getWindowAncestor(this).dispose();
    }

    public static void display() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sign Up");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setContentPane(new SignUp());
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);

            // Nonaktifkan tombol-tombol di TampilanUtama.fxml
            Platform.runLater(() -> {
                for (javafx.stage.Window window : javafx.stage.Window.getWindows()) {
                    if (window instanceof Stage) {
                        Stage utamaStage = (Stage) window;
                        utamaStage.getScene().getRoot().setDisable(true);
                        utamaStage.getScene().getRoot().setOpacity(0.5);
                        break;
                    }
                }
            });

            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent windowEvent) {
                    // Aktifkan kembali tombol-tombol di TampilanUtama.fxml
                    Platform.runLater(() -> {
                        for (javafx.stage.Window window : javafx.stage.Window.getWindows()) {
                            if (window instanceof Stage) {
                                Stage utamaStage = (Stage) window;
                                utamaStage.getScene().getRoot().setDisable(false);
                                utamaStage.getScene().getRoot().setOpacity(1.0);
                                break;
                            }
                        }
                    });
                }
            });
        });
    }
}
