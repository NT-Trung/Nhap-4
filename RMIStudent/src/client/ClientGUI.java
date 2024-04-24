package client;

import javax.swing.*;
import server.StudentManager;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;

public class ClientGUI extends JFrame {
    private JTextField MaSVTextField, TenSVTextField, EmailTextField, SDTTextField, MaKhoaTextField, MaLopTextField;
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
    	JPanel inputPanel = new JPanel(new GridLayout(3, 3));
    	JLabel MaSVLabel = new JLabel("Mã sinh viên:");
    	MaSVTextField = new JTextField(20);
    	JLabel TenSVLabel = new JLabel("Tên sinh viên:");
    	TenSVTextField = new JTextField(20);
    	JLabel EmailLabel = new JLabel("Email:");
    	EmailTextField = new JTextField(20);
    	JLabel SDTLabel = new JLabel("Số điện thoại:");
    	SDTTextField = new JTextField(20);
    	JLabel MaKhoaLabel = new JLabel("Khoa:");
    	MaKhoaTextField = new JTextField(20);
    	JLabel MaLopLabel = new JLabel("Lớp:");
    	MaLopTextField = new JTextField(20);
    	inputPanel.add(MaSVLabel);
    	inputPanel.add(MaSVTextField);
    	inputPanel.add(TenSVLabel);
    	inputPanel.add(TenSVTextField);
    	inputPanel.add(EmailLabel);
    	inputPanel.add(EmailTextField);
    	inputPanel.add(SDTLabel);
    	inputPanel.add(SDTTextField);
    	inputPanel.add(MaKhoaLabel);
    	inputPanel.add(MaKhoaTextField);
    	inputPanel.add(MaLopLabel);
    	inputPanel.add(MaLopTextField);

    	// Khởi tạo cửa sổ chính
    	setTitle("Quản lý sinh viên");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setSize(600, 400);
    	// Tạo panel chứa các nút chức năng
    	JPanel buttonPanel = new JPanel();
    	buttonPanel.setLayout(new GridLayout(3, 4, 1, 5)); // 3 hàng, 4 cột, khoảng cách 10 pixel 
    	// Tạo các nút chức năng
    	JButton searchButton = new JButton("Tìm kiếm");
    	JButton addButton = new JButton("Thêm SV");
    	JButton deleteButton = new JButton("Xóa SV");
    	JButton editButton = new JButton("Sửa TT SV\n");


    	JButton viewScoreButton = new JButton("Xem điểm");
    	JButton enterScoreButton = new JButton("Nhập điểm");
    	JButton deleteScoreButton = new JButton("Xóa điểm");
    	JButton editScoreButton = new JButton("Sửa điểm");
    	JButton viewAllButton = new JButton("Xem tất cả");
    	JButton registerCourseButton = new JButton("ĐK Môn học");
    	JButton resetButton = new JButton("Reset");
    	// Thêm các nút vào panel
    	buttonPanel.add(searchButton);
    	buttonPanel.add(addButton);
    	buttonPanel.add(deleteButton);
    	buttonPanel.add(editButton);

    	buttonPanel.add(viewScoreButton);
    	buttonPanel.add(enterScoreButton);
    	buttonPanel.add(deleteScoreButton);
    	buttonPanel.add(editScoreButton);
    	buttonPanel.add(viewAllButton);
    	buttonPanel.add(registerCourseButton);
    	buttonPanel.add(resetButton);
    	// Thiết lập layout cho frame
    	setLayout(new BorderLayout());
    	// Thiết lập kích thước cho inputPanel
    	inputPanel.setPreferredSize(new Dimension(600, 100));
    	add(inputPanel, BorderLayout.NORTH);
    	add(buttonPanel, BorderLayout.CENTER);

    	textArea = new JTextArea(20, 10);
    	JScrollPane scrollPane = new JScrollPane(textArea);
    	// Thiết lập kích thước cho scrollPane
    	scrollPane.setPreferredSize(new Dimension(600, 300));
    	add(scrollPane, BorderLayout.SOUTH);
    	// Hiển thị cửa sổ
    	setVisible(true);
    	
    	
    	
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
                String MaSV = MaSVTextField.getText();
                try {
                    String studentInfo = studentManager.findStudentInfo(MaSV);
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
                String MaSV = MaSVTextField.getText();
                String TenSV = TenSVTextField.getText();
                String Email = EmailTextField.getText();
                String SDT = SDTTextField.getText();
                String MaKhoa = MaKhoaTextField.getText();
                String MaLop = MaLopTextField.getText();
                try {
                    // Gọi phương thức từ xa để thêm học viên
                    String result = studentManager.addStudent(MaSV, TenSV, Email, SDT, MaKhoa, MaLop);
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
                String MaSV = MaSVTextField.getText();
                try {
                    // Gọi phương thức từ xa để xóa học viên
                    String result = studentManager.deleteStudent(MaSV);
                    textArea.setText(result);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
     // Xử lý sự kiện khi click nút "Sửa"
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String MaSV = MaSVTextField.getText();
                String TenSV = TenSVTextField.getText();
                String Email = EmailTextField.getText();
                String SDT = SDTTextField.getText();
                String MaKhoa = MaKhoaTextField.getText();
                String MaLop = MaLopTextField.getText();
                try {
                    // Gọi phương thức từ xa để sửa thông tin sinh viên
                    String result = studentManager.editStudent(MaSV, TenSV, Email, SDT, MaKhoa, MaLop);
                    textArea.setText(result);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Xử lý sự kiện khi click nút "Xem điểm"
        viewScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String MaSV = MaSVTextField.getText();
                try {
                    String scoreInfo = studentManager.getStudentScores(MaSV);
                    textArea.setText(scoreInfo);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Xử lý sự kiện khi click nút "Nhập điểm"
        enterScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String MaSV = MaSVTextField.getText();
                String TenMH = JOptionPane.showInputDialog("Nhập tên môn học:");

                try {
                    String MaMH = studentManager.getSubjectIdByName(TenMH);

                    if (MaMH != null) {
                        // Tạo cửa sổ dialog để nhập điểm
                        JPanel panel = new JPanel(new GridLayout(4, 2));
                        JTextField tk1Field = new JTextField();
                        JTextField tk2Field = new JTextField();
                        JTextField gkField = new JTextField();
                        JTextField ckField = new JTextField();

                        panel.add(new JLabel("Nhập điểm TK1:"));
                        panel.add(tk1Field);
                        panel.add(new JLabel("Nhập điểm TK2:"));
                        panel.add(tk2Field);
                        panel.add(new JLabel("Nhập điểm GK:"));
                        panel.add(gkField);
                        panel.add(new JLabel("Nhập điểm CK:"));
                        panel.add(ckField);

                        int result = JOptionPane.showConfirmDialog(null, panel, "Nhập điểm", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            try {
                                float DiemTK1 = Float.parseFloat(tk1Field.getText());
                                float DiemTK2 = Float.parseFloat(tk2Field.getText());
                                float DiemGK = Float.parseFloat(gkField.getText());
                                float DiemCK = Float.parseFloat(ckField.getText());

                                // Thêm dữ liệu vào bảng hoặc hiển thị kết quả trong textArea
                                String resultMessage = studentManager.addStudentScore(MaSV, MaMH, DiemTK1, DiemTK2, DiemGK, DiemCK);
                                textArea.setText(resultMessage);
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Nhập điểm không hợp lệ!");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Môn học không tồn tại!");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        // Xử lý sự kiện khi click nút "Xóa điểm"
        deleteScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String MaSV = MaSVTextField.getText();
                String TenMH = JOptionPane.showInputDialog("Nhập tên môn học cần xóa điểm:");

                try {
                    String MaMH = studentManager.getSubjectIdByName(TenMH);

                    if (MaMH != null) {
                        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa điểm của môn " + TenMH + " cho sinh viên có mã " + MaSV + "?");
                        if (confirm == JOptionPane.YES_OPTION) {
                            // Xóa điểm và hiển thị kết quả trong textArea
                            String resultMessage = studentManager.deleteStudentScore(MaSV, MaMH);
                            textArea.setText(resultMessage);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Môn học không tồn tại!");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Xử lý sự kiện khi click nút "Sửa điểm"
        editScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String MaSV = MaSVTextField.getText();
                String TenMH = JOptionPane.showInputDialog("Nhập tên môn học cần sửa điểm:");

                try {
                    String MaMH = studentManager.getSubjectIdByName(TenMH);

                    if (MaMH != null) {
                        // Tạo cửa sổ dialog để nhập điểm mới
                        JPanel panel = new JPanel(new GridLayout(4, 2));
                        JTextField tk1Field = new JTextField();
                        JTextField tk2Field = new JTextField();
                        JTextField gkField = new JTextField();
                        JTextField ckField = new JTextField();

                        panel.add(new JLabel("Nhập điểm TK1 mới:"));
                        panel.add(tk1Field);
                        panel.add(new JLabel("Nhập điểm TK2 mới:"));
                        panel.add(tk2Field);
                        panel.add(new JLabel("Nhập điểm GK mới:"));
                        panel.add(gkField);
                        panel.add(new JLabel("Nhập điểm CK mới:"));
                        panel.add(ckField);

                        int result = JOptionPane.showConfirmDialog(null, panel, "Sửa điểm", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            try {
                                float DiemTK1 = Float.parseFloat(tk1Field.getText());
                                float DiemTK2 = Float.parseFloat(tk2Field.getText());
                                float DiemGK = Float.parseFloat(gkField.getText());
                                float DiemCK = Float.parseFloat(ckField.getText());

                                // Sửa điểm và hiển thị kết quả trong textArea
                                String resultMessage = studentManager.updateStudentScore(MaSV, MaMH, DiemTK1, DiemTK2, DiemGK, DiemCK);
                                textArea.setText(resultMessage);
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Nhập điểm không hợp lệ!");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Môn học không tồn tại!");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        // Xử lý sự kiện khi click nút "Đăng ký môn học"
        registerCourseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String MaSV = MaSVTextField.getText();
                String TenMH = JOptionPane.showInputDialog("Nhập tên môn học:");

                try {
                    String MaMH = studentManager.getSubjectIdByName(TenMH);

                    if (MaMH != null) {
                        int result = JOptionPane.showConfirmDialog(null, null, "Đăng ký môn học thành công", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            try {
                                // Thêm dữ liệu vào bảng hoặc hiển thị kết quả trong textArea
                                String resultMessage = studentManager.registerCourse(MaSV, MaMH);
                                textArea.setText(resultMessage);
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Nhập môn không hợp lệ!");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Môn học không tồn tại!");
                    }
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
                MaSVTextField.setText("");
                TenSVTextField.setText("");
                EmailTextField.setText("");
                SDTTextField.setText("");
                MaKhoaTextField.setText("");
                MaLopTextField.setText("");
                // Xóa nội dung trong textArea
                textArea.setText("");
            }
        });


        // Thiết lập kích thước và hiển thị frame
        setSize(600, 600);
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
