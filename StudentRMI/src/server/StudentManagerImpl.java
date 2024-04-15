package server;

import java.rmi.RemoteException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class StudentManagerImpl extends UnicastRemoteObject implements StudentManager {
	private Map<String, StudentManager> students;
    protected StudentManagerImpl() throws RemoteException {
        super();
    }

    @Override
    public String findStudentInfo(String studentId) throws RemoteException {
        String studentInfo = "";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            // Kết nối với cơ sở dữ liệu
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hocvien", "root", "");
            stmt = conn.prepareStatement("SELECT * FROM thongtinhocvien WHERE MaHV = ?");
            stmt.setString(1, studentId);
            rs = stmt.executeQuery();
            
            // Lấy thông tin học viên từ kết quả truy vấn
            if (rs.next()) {
                studentInfo = "MaHV: " + rs.getString("MaHV") + "\n" +
                              "TenHV: " + rs.getString("TenHV") + "\n" +
                              "Email: " + rs.getString("Email") + "\n" +
                              "SoDienThoai: " + rs.getString("SoDienThoai") + "\n" +
                              "DiaChi: " + rs.getString("DiaChi");
            } else {
                studentInfo = "Không tìm thấy thông tin học viên với mã " + studentId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và giải phóng tài nguyên
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return studentInfo;
    }
    @Override
    public String addStudent(String MaHV, String TenHV, String Email, String SoDienThoai, String DiaChi) throws RemoteException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Kết nối với cơ sở dữ liệu
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hocvien", "root", "");
            stmt = conn.prepareStatement("INSERT INTO thongtinhocvien (MaHV, TenHV, Email, SoDienThoai, DiaChi) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, MaHV);
            stmt.setString(2, TenHV);
            stmt.setString(3, Email);
            stmt.setString(4, SoDienThoai);
            stmt.setString(5, DiaChi);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return "Thêm học viên thành công.";
            } else {
                return "Không thể thêm học viên.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi khi thêm học viên: " + e.getMessage();
        } finally {
            // Đóng kết nối và giải phóng tài nguyên
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public String deleteStudent(String MaHV) throws RemoteException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Kết nối với cơ sở dữ liệu
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hocvien", "root", "");
            stmt = conn.prepareStatement("DELETE FROM thongtinhocvien WHERE MaHV = ?");
            stmt.setString(1, MaHV);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return "Xóa học viên thành công.";
            } else {
                return "Không thể xóa học viên.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi khi xóa học viên: " + e.getMessage();
        } finally {
            // Đóng kết nối và giải phóng tài nguyên
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public String getAllStudentsInfo() throws RemoteException {
        StringBuilder result = new StringBuilder();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            // Truy vấn tất cả học viên từ cơ sở dữ liệu
        	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hocvien", "root", "");
            String query = "SELECT * FROM thongtinhocvien";
            statement = conn.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Lấy thông tin từ kết quả truy vấn
                String MaHV = resultSet.getString("MaHV");
                String TenHV = resultSet.getString("TenHV");
                String Email = resultSet.getString("Email");
                String SoDienThoai = resultSet.getString("SoDienThoai");
                String DiaChi = resultSet.getString("DiaChi");
                // Thêm thông tin của học viên vào StringBuilder
                result.append("Mã học viên: ").append(MaHV).append(", Tên: ").append(TenHV)
                        .append(", Email: ").append(Email).append(", số điện thoại: ").append(SoDienThoai)
                        .append(", địa chỉ: ").append(DiaChi).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng các resource
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }
}