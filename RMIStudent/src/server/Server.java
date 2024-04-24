package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@SuppressWarnings("unused")
public class Server {
    public static void main(String[] args) {
    	try {
            StudentManager studentManager = new StudentManagerImpl();
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            java.rmi.Naming.rebind("StudentManager", studentManager);
            System.out.println("Server is ready...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//jdbc sql server driver jar download