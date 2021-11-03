package general;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import model.RequestModel;

public interface RequestModelInterface extends Remote {

    public ArrayList<RequestModel> selectRequest(int recieverid) throws RemoteException;

    public boolean deleteRequest(int senderId, int recieverId) throws RemoteException;

    public boolean checkOnline(String status, int id) throws RemoteException;

    public boolean waiterOnline(int recieverid) throws RemoteException;

}
