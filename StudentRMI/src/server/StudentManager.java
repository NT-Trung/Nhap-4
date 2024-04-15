package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface StudentManager extends Remote {
    String findStudentInfo(String studentId) throws RemoteException;
	String addStudent(String MaHV, String TenHV, String Email, String SoDienThoai, String DiaChi) throws RemoteException;
	String deleteStudent(String MaHV) throws RemoteException;
	String getAllStudentsInfo() throws RemoteException;
}