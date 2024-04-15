package client;

import javax.swing.*;
import server.StudentManager;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame {
    private JTextField idTextField, nameTextField, emailTextField, phoneTextField, addressTextField;
    private JTextArea textArea;
    private StudentManager studentManager;

    public ClientGUI() {
        super("Quản lý thông tin học viên");

        try {
            studentManager = (StudentManager) java.rmi.Naming.lookup("rmi://localhost/StudentManager");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Tạo các thành phần giao diện
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        JLabel idLabel = new JLabel("Mã học viên:");
        idTextField = new JTextField(20);
        JLabel nameLabel = new JLabel("Tên học viên:");
        nameTextField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        emailTextField = new JTextField(20);
        JLabel phoneLabel = new JLabel("Số điện thoại:");
        phoneTextField = new JTextField(20);
        JLabel addressLabel = new JLabel("Địa chỉ:");
        addressTextField = new JTextField(20);
        inputPanel.add(idLabel);
        inputPanel.add(idTextField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameTextField);
        inputPanel.add(emailLabel);
        inputPanel.add(emailTextField);
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneTextField);
        inputPanel.add(addressLabel);
        inputPanel.add(addressTextField);

        JPanel buttonPanel = new JPanel();
        JButton searchButton = new JButton("Tìm kiếm");
        JButton addButton = new JButton("Thêm");
        JButton deleteButton = new JButton("Xóa");
        JButton viewAllButton = new JButton("Xem tất cả");
        JButton resetButton = new JButton("Reset");
        buttonPanel.add(searchButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewAllButton);
        buttonPanel.add(resetButton);

        textArea = new JTextArea(15, 30);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Thiết lập layout cho frame
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Xử lý sự kiện khi click nút Xem tất cả
        viewAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Gọi phương thức từ xa để lấy danh sách toàn bộ học viên
                    String allStudentsInfo = studentManager.getAllStudentsInfo();
                    textArea.setText(allStudentsInfo);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Xử lý sự kiện khi click nút Tìm kiếm
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentId = idTextField.getText();
                try {
                    String studentInfo = studentManager.findStudentInfo(studentId);
                    textArea.setText(studentInfo);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Xử lý sự kiện khi click nút Thêm
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String MaHV = idTextField.getText();
                String TenHV = nameTextField.getText();
                String Email = emailTextField.getText();
                String SoDienThoai = phoneTextField.getText();
                String DiaChi = addressTextField.getText();
                try {
                    // Gọi phương thức từ xa để thêm học viên
                    String result = studentManager.addStudent(MaHV, TenHV, Email, SoDienThoai, DiaChi);
                    textArea.setText(result);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Xử lý sự kiện khi click nút Xóa
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String MaHV = idTextField.getText();
                try {
                    // Gọi phương thức từ xa để xóa học viên
                    String result = studentManager.deleteStudent(MaHV);
                    textArea.setText(result);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Xử lý sự kiện khi nhấn nút reset
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xóa nội dung của các trường nhập liệu
                idTextField.setText("");
                nameTextField.setText("");
                emailTextField.setText("");
                phoneTextField.setText("");
                addressTextField.setText("");
                // Xóa nội dung trong textArea
                textArea.setText("");
            }
        });


        // Thiết lập kích thước và hiển thị frame
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI();
            }
        });
    }
}
