package client;

import java.rmi.Naming;
import java.util.Scanner;

import server.StudentManager;

// Lớp chính của máy khách
public class ClientForm {
    public static void main(String[] args) {
        try {
            // Tìm và kết nối với máy chủ
            StudentManager studentManager = (StudentManager) Naming.lookup("rmi://localhost/StudentManager");
            Scanner scanner = new Scanner(System.in);
            
            // Nhập mã học viên cần tìm kiếm
            System.out.print("Nhập mã học viên: ");
            String studentId = scanner.nextLine();
            
            // Gọi phương thức từ xa để tìm kiếm thông tin học viên
            String studentInfo = studentManager.findStudentInfo(studentId);
            System.out.println(studentInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
