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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class StudentManagerImpl extends UnicastRemoteObject implements StudentManager {
	private Map<String, StudentManager> students;
	private Map<String, Map<String, Float[]>> studentScores;
	private Map<String, List<String>> studentCourses;
    protected StudentManagerImpl() throws RemoteException {
    	studentScores = new HashMap<>();
    }

    @Override
    public String findStudentInfo(String MaSV) throws RemoteException {
        String studentInfo = "";
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            // Kết nối với cơ sở dữ liệu
        	Connection conn = DBConnectionFactory.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM sinhvien WHERE MaSV = ?");
            stmt.setString(1, MaSV);
            rs = stmt.executeQuery();
            
            // Lấy thông tin học viên từ kết quả truy vấn
            if (rs.next()) {
                studentInfo = "MaSV: " + rs.getString("MaSV") + "\n" +
                              "TenSV: " + rs.getString("TenSV") + "\n" +
                              "Email: " + rs.getString("Email") + "\n" +
                              "SDT: " + rs.getString("SDT") + "\n" +
                              "MaKhoa: " + rs.getString("MaKhoa") + "\n" +
                              "MaLop: " + rs.getString("MaLop");
            } else {
                studentInfo = "Không tìm thấy thông tin sinh viên với mã " + MaSV;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và giải phóng tài nguyên
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return studentInfo;
    }
    @Override
    public String addStudent(String MaSV, String TenSV, String Email, String SDT, String MaKhoa, String MaLop) throws RemoteException {
        PreparedStatement stmt = null;

        try {
            // Kết nối với cơ sở dữ liệu
        	Connection conn = DBConnectionFactory.getConnection();
            stmt = conn.prepareStatement("INSERT INTO sinhvien (MaSV, TenSV, Email, SDT, MaKhoa, MaLop) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, MaSV);
            stmt.setString(2, TenSV);
            stmt.setString(3, Email);
            stmt.setString(4, SDT);
            stmt.setString(5, MaKhoa);
            stmt.setString(6, MaLop);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return "Thêm sinh viên thành công.";
            } else {
                return "Không thể thêm sinh viên.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi khi thêm sinh viên: " + e.getMessage();
        } finally {
            // Đóng kết nối và giải phóng tài nguyên
            try {
                if (stmt != null) stmt.close();
//                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public String deleteStudent(String MaSV) throws RemoteException {
        PreparedStatement stmt = null;

        try {
            // Kết nối với cơ sở dữ liệu
        	Connection conn = DBConnectionFactory.getConnection();
            stmt = conn.prepareStatement("DELETE FROM sinhvien WHERE MaSV = ?");
            stmt.setString(1, MaSV);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return "Xóa sinh viên thành công.";
            } else {
                return "Không thể xóa sinh viên.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi khi xóa sinh viên: " + e.getMessage();
        } finally {
            // Đóng kết nối và giải phóng tài nguyên
            try {
                if (stmt != null) stmt.close();
//                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public String editStudent(String MaSV, String TenSV, String Email, String SDT, String MaKhoa, String MaLop) throws RemoteException {
        PreparedStatement stmt = null;

        try {
            // Kết nối với cơ sở dữ liệu
        	Connection conn = DBConnectionFactory.getConnection();

            // Kiểm tra xem sinh viên có tồn tại không
            if (!isStudentExists(conn, MaSV)) {
                return "Không tìm thấy sinh viên có mã: " + MaSV;
            }

            // Cập nhật thông tin của sinh viên
            stmt = conn.prepareStatement("UPDATE sinhvien SET TenSV=?, Email=?, SDT=?, MaKhoa=?, MaLop=? WHERE MaSV=?");
            stmt.setString(1, TenSV);
            stmt.setString(2, Email);
            stmt.setString(3, SDT);
            stmt.setString(4, MaKhoa);
            stmt.setString(5, MaLop);
            stmt.setString(6, MaSV);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return "Sửa thông tin sinh viên thành công.";
            } else {
                return "Không thể sửa thông tin sinh viên.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi khi sửa thông tin sinh viên: " + e.getMessage();
        } finally {
            // Đóng kết nối và giải phóng tài nguyên
            try {
                if (stmt != null) stmt.close();
//                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Phương thức kiểm tra xem sinh viên có tồn tại không
    private boolean isStudentExists(Connection conn, String MaSV) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("SELECT * FROM sinhvien WHERE MaSV = ?");
            stmt.setString(1, MaSV);
            rs = stmt.executeQuery();
            return rs.next();
        } finally {
            // Đóng ResultSet và PreparedStatement
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
    }
    //Phương thức xem điềm
    @Override
    public String getStudentScores(String MaSV) throws RemoteException {
        // Tạo chuỗi kết quả ban đầu
        StringBuilder result = new StringBuilder();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Kết nối tới cơ sở dữ liệu
        	Connection conn = DBConnectionFactory.getConnection();

        	// Chuẩn bị câu truy vấn
            String query = "SELECT monhoc.TenMH, bangdiem.DiemTK1, bangdiem.DiemTK2, bangdiem.DiemGK, bangdiem.DiemCK " +
                           "FROM bangdiem " +
                           "JOIN monhoc ON bangdiem.MaMH = monhoc.MaMH " +
                           "WHERE bangdiem.MaSV = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, MaSV);

            // Thực thi câu truy vấn
            resultSet = preparedStatement.executeQuery();

            // Lặp qua kết quả và thêm vào chuỗi kết quả
            while (resultSet.next()) {
                String TenMH = resultSet.getString("TenMH");
                float DiemTK1 = resultSet.getFloat("DiemTK1");
                float DiemTK2 = resultSet.getFloat("DiemTK2");
                float DiemGK = resultSet.getFloat("DiemGK");
                float DiemCK = resultSet.getFloat("DiemCK");
                
             // Thêm thông tin điểm vào chuỗi kết quả
                result.append("Tên môn học: ").append(TenMH)
                        .append(", Điểm TK1: ").append(DiemTK1)
                        .append(", Điểm TK2: ").append(DiemTK2)
                        .append(", Điểm GK: ").append(DiemGK)
                        .append(", Điểm CK: ").append(DiemCK)
                        .append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối và các đối tượng liên quan
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
//                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Trả về chuỗi kết quả
        return result.toString();
    }
    
    @Override
    public String getSubjectIdByName(String TenMH) throws RemoteException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
        	Connection conn = DBConnectionFactory.getConnection();
            String query = "SELECT MaMH FROM monhoc WHERE TenMH = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, TenMH);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("MaMH");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
//                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public String addStudentScore(String MaSV, String MaMH, float DiemTK1, float DiemTK2, float DiemGK, float DiemCK) throws RemoteException {
        PreparedStatement preparedStatement = null;

        try {
        	Connection conn = DBConnectionFactory.getConnection();
            String query = "INSERT INTO bangdiem (MaSV, MaMH, DiemTK1, DiemTK2, DiemGK, DiemCK) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, MaSV);
            preparedStatement.setString(2, MaMH);
            preparedStatement.setFloat(3, DiemTK1);
            preparedStatement.setFloat(4, DiemTK2);
            preparedStatement.setFloat(5, DiemGK);
            preparedStatement.setFloat(6, DiemCK);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return "Đã nhập điểm cho sinh viên thành công.";
            } else {
                return "Lỗi: Không thể nhập điểm cho sinh viên.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi SQL: " + e.getMessage();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
//                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public String deleteStudentScore(String MaSV, String MaMH) {
        PreparedStatement preparedStatement = null;

        try {
        	Connection conn = DBConnectionFactory.getConnection();
            String query = "DELETE FROM bangdiem WHERE MaSV = ? AND MaMH = ?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, MaSV);
            preparedStatement.setString(2, MaMH);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return "Xóa điểm của sinh viên có mã " + MaSV + " thành công.";
            } else {
                return "Không có điểm nào để xóa cho sinh viên có mã " + MaSV ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi SQL: " + e.getMessage();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
//                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public String updateStudentScore(String MaSV, String MaMH, float DiemTK1, float DiemTK2, float DiemGK, float DiemCK) {
        PreparedStatement preparedStatement = null;

        try {
        	Connection conn = DBConnectionFactory.getConnection();
            String query = "UPDATE bangdiem SET DiemTK1=?, DiemTK2=?, DiemGK=?, DiemCK=? WHERE MaSV=? AND MaMH=?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setFloat(1, DiemTK1);
            preparedStatement.setFloat(2, DiemTK2);
            preparedStatement.setFloat(3, DiemGK);
            preparedStatement.setFloat(4, DiemCK);
            

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return "Đã cập nhật điểm cho sinh viên thành công.";
            } else {
                return "Lỗi: Không thể cập nhật điểm cho sinh viên.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi SQL: " + e.getMessage();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
//                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public String registerCourse(String MaSV, String MaMH) throws RemoteException {
        PreparedStatement preparedStatement = null;

        try {
        	Connection conn = DBConnectionFactory.getConnection();
            String query = "INSERT INTO dangkymonhoc (MaSV, MaMH) VALUES (?, ?)";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, MaSV);
            preparedStatement.setString(2, MaMH);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return "Đã đăng ký môn học cho sinh viên thành công.";
            } else {
                return "Lỗi: Không thể đăng ký môn học cho sinh viên.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi SQL: " + e.getMessage();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
//                if (conn != null) conn.close();
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
        try {
            // Truy vấn tất cả học viên từ cơ sở dữ liệu
        	Connection conn = DBConnectionFactory.getConnection();
            String query = "SELECT * FROM sinhvien";
            statement = conn.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Lấy thông tin từ kết quả truy vấn
                String MaSV = resultSet.getString("MaSV");
                String TenSV = resultSet.getString("TenSV");
                String Email = resultSet.getString("Email");
                String SDT = resultSet.getString("SDT");
                String MaKhoa = resultSet.getString("MaKhoa");
                String MaLop = resultSet.getString("MaLop");
                // Thêm thông tin của học viên vào StringBuilder
                result.append("Mã SV: ").append(MaSV).append(", Tên: ").append(TenSV)
                        .append(", Email: ").append(Email).append(", số điện thoại: ").append(SDT)
                        .append(", Khoa: ").append(MaKhoa)
                        .append(", Lớp: ").append(MaLop).append("\n");
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