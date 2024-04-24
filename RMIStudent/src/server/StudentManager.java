package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface StudentManager extends Remote {
	
    String findStudentInfo(String MaSV) throws RemoteException;
	String addStudent(String MaSV, String TenSV, String Email, String SDT, String MaKhoa, String MaLop) throws RemoteException;
	String deleteStudent(String MaSV) throws RemoteException;
	String editStudent(String MaSV, String TenSV, String Email, String SDT, String MaKhoa, String MaLop) throws RemoteException;
	String getAllStudentsInfo() throws RemoteException;
	
	String getStudentScores(String MaSV)throws RemoteException;
	String addStudentScore(String MaSV, String MaMH, float DiemTK1, float DiemTK2, float DiemGK, float DiemCK) throws RemoteException;
	String getSubjectIdByName(String TenMH) throws RemoteException;
	String deleteStudentScore(String MaSV, String MaMH) throws RemoteException;
	String updateStudentScore(String MaSV, String MaMH, float DiemTK1, float DiemTK2, float DiemGK, float DiemCK) throws RemoteException;
	String registerCourse(String MaSV, String MaMH)throws RemoteException;
	
}