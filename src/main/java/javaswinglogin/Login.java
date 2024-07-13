package javaswinglogin;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.beritamedia.app.controller.NewsController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login extends JPanel {

    private JTextField email;
    private JPasswordField password;
    private JButton LoginBtn;
    private JButton SignUpBtn;

    public Login() {
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new java.awt.Color(51, 102, 255));
        rightPanel.setPreferredSize(new java.awt.Dimension(400, 500));
        rightPanel.setLayout(new GridBagLayout());

        JLabel jLabel9 = new JLabel(new ImageIcon("src/main/resources/com/beritamedia/app/Black_White_Elegant_Monogram_Initial_Name_Logo-removebg-preview.png"));
        JLabel jLabel7 = new JLabel("copyright © Kelompok 6 BeritaKita");
        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 0, 14));
        jLabel7.setForeground(new java.awt.Color(204, 204, 204));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(jLabel9, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 0, 0);
        rightPanel.add(jLabel7, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.VERTICAL;
        this.add(rightPanel, gbc);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(java.awt.Color.WHITE);
        leftPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(51, 102, 255)));
        leftPanel.setPreferredSize(new java.awt.Dimension(400, 500));
        leftPanel.setLayout(new GridBagLayout());

        JLabel jLabel1 = new JLabel("Login");
        jLabel1.setFont(new java.awt.Font("Fira Code", 1, 48));
        jLabel1.setForeground(new java.awt.Color(51, 102, 255));

        JLabel jLabel2 = new JLabel("Email");
        jLabel2.setFont(new java.awt.Font("Impact", 0, 24));
        jLabel2.setForeground(new java.awt.Color(51, 102, 255));

        email = new JTextField();
        email.setFont(new java.awt.Font("Segoe UI", 0, 14));
        email.setForeground(new java.awt.Color(102, 102, 102));
        email.setPreferredSize(new java.awt.Dimension(340, 40));

        JLabel jLabel3 = new JLabel("Password");
        jLabel3.setFont(new java.awt.Font("Impact", 0, 24));
        jLabel3.setForeground(new java.awt.Color(51, 102, 255));

        password = new JPasswordField();
        password.setFont(new java.awt.Font("Segoe UI", 0, 14));
        password.setForeground(new java.awt.Color(102, 102, 102));
        password.setPreferredSize(new java.awt.Dimension(340, 40));

        LoginBtn = new JButton("Login");
        LoginBtn.setFont(new java.awt.Font("Verdana", 0, 14));
        LoginBtn.setForeground(java.awt.Color.WHITE);
        LoginBtn.setBackground(new java.awt.Color(51, 102, 255));
        LoginBtn.setPreferredSize(new java.awt.Dimension(100, 40));
        LoginBtn.addActionListener(evt -> LoginBtnActionPerformed(evt));

        JLabel jLabel4 = new JLabel("I don't have an account");
        jLabel4.setFont(new java.awt.Font("Cambria", 1, 12));
        jLabel4.setForeground(new java.awt.Color(255, 0, 0));

        SignUpBtn = new JButton("Sign Up");
        SignUpBtn.setFont(new java.awt.Font("Verdana", 0, 14));
        SignUpBtn.setForeground(java.awt.Color.WHITE);
        SignUpBtn.setBackground(new java.awt.Color(51, 153, 255));
        SignUpBtn.setPreferredSize(new java.awt.Dimension(100, 40));
        SignUpBtn.addActionListener(evt -> SignUpBtnActionPerformed(evt));

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 50, 20, 0); // Adjusted left margin for title
        gbc.anchor = GridBagConstraints.CENTER;
        leftPanel.add(jLabel1, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 50, 5, 0); // Adjusted left margin for labels and fields
        gbc.anchor = GridBagConstraints.LINE_START;
        leftPanel.add(jLabel2, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 50, 5, 0); // Adjusted left margin for labels and fields
        leftPanel.add(email, gbc);

        gbc.gridy++;
        leftPanel.add(jLabel3, gbc);

        gbc.gridy++;
        leftPanel.add(password, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(20, 50, 5, 0); // Adjusted left margin for buttons
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        leftPanel.add(LoginBtn, gbc);

        gbc.gridx = 0;
        gbc.insets = new Insets(20, 50, 5, 0); // Adjusted left margin for buttons
        gbc.anchor = GridBagConstraints.LINE_END;
        leftPanel.add(SignUpBtn, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 50, 0, 0); // Adjusted left margin for labels and fields
        gbc.anchor = GridBagConstraints.CENTER;
        leftPanel.add(jLabel4, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        this.add(leftPanel, gbc);
    }

    private void SignUpBtnActionPerformed(java.awt.event.ActionEvent evt) {
        SignUp.display();
        SwingUtilities.getWindowAncestor(this).dispose();
    }

    private void LoginBtnActionPerformed(java.awt.event.ActionEvent evt) {
        String Email = email.getText();
        String Password = new String(password.getPassword());

        if (Email.isEmpty() && Password.equals("root")) {
            // Login sebagai admin
            System.out.println("Login sebagai admin");
            showTampilan("admin", null);
        } else if (Email.isEmpty() && Password.isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Email Address and Password are required", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (Email.isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Email Address is required", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (Password.isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Password is required", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            // Login sebagai user
            String SUrl = "jdbc:MySQL://localhost:3306/java_user_database";
            String SUser = "root";
            String SPass = "";
            int notFound = 0;
            String fname = null;
            String passDb = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(SUrl, SUser, SPass);
                Statement st = con.createStatement();
                String query = "SELECT * FROM users WHERE email= '" + Email + "'";
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    passDb = rs.getString("password");
                    fname = rs.getString("full_name");
                    notFound = 1;
                }

                if (notFound == 1 && Password.equals(passDb)) {
                    JOptionPane.showMessageDialog(new JFrame(), "Login Successful", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    showTampilan("user", fname);
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Incorrect email or password", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                password.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showTampilan(String userType, String userName) {
        Platform.runLater(() -> {
            try {
                // Dapatkan jendela utama yang sudah ada
                for (javafx.stage.Window window : javafx.stage.Window.getWindows()) {
                    if (window instanceof Stage) {
                        Stage utamaStage = (Stage) window;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/beritamedia/app/TampilanUtama.fxml"));
                        Parent root = loader.load();

                        NewsController newsController = loader.getController();
                        newsController.updateUIAfterLogin(userType, userName);

                        utamaStage.getScene().setRoot(root);
                        break;
                    }
                }

                // Tutup jendela login setelah login berhasil
                SwingUtilities.invokeLater(() -> {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                    if (frame != null) {
                        frame.dispose();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void display() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setContentPane(new Login());
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
