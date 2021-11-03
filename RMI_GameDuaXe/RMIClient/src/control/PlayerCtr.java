package control;

import general.FriendInterface;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import model.IPAddress;
import view.PlayerConnectToServer;
import general.PlayerInterface;
import general.RankInterface;
import general.RequestModelInterface;
import java.util.ArrayList;
import model.Friend;
import model.Player;
import model.Rank;
import model.RequestModel;
import server.RMILoginInterface;

public class PlayerCtr {

    private PlayerConnectToServer view;
    private RMILoginInterface loginRO;
    private PlayerInterface PlayerRO;
    private RequestModelInterface RequestRO;
    private FriendInterface FriendRO;
    private RankInterface RankRO;
    private IPAddress serverAddress = new IPAddress("localhost", 9999); //default server address
    private String rmiService = "rmiServer";                            //default server service key

    public PlayerCtr(PlayerConnectToServer view) {
        this.view = view;
    }

    public PlayerCtr(PlayerConnectToServer view, String serverHost, int serverPort, String service) {
        this.view = view;
        serverAddress.setHost(serverHost);
        serverAddress.setPort(serverPort);
        rmiService = service;
    }

    public boolean init() {
        try {
            // get the registry
            Registry registry = LocateRegistry.getRegistry(serverAddress.getHost(), serverAddress.getPort());
            // lookup the remote objects
            loginRO = (RMILoginInterface) (registry.lookup(rmiService));
            PlayerRO = (PlayerInterface) (registry.lookup(rmiService));
            RequestRO = (RequestModelInterface) (registry.lookup(rmiService));
            RankRO = (RankInterface) (registry.lookup(rmiService));
            FriendRO = (FriendInterface) (registry.lookup(rmiService));
            view.setServerHost(serverAddress.getHost(), serverAddress.getPort() + "", rmiService);
            view.showMessage("Found the remote objects at the host: " + serverAddress.getHost() + ", port: " + serverAddress.getPort());
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Error to lookup the remote objects!");
            return false;
        }
        return true;
    }

    public String remoteRegisterUser(Player player) {
        try {
            return loginRO.registerUser(player);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Player remoteLogin(Player player) {
        try {
            return loginRO.checkLogin(player);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePlayerstatus(String value, int id) {
        try {
            PlayerRO.updatePlayerstatus(value, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Player> playersOnline(Player player) {
        try {
            return PlayerRO.playersOnline(player);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean requestFriend(int senderId, int recieverId, String requestname) {
        try {
            return PlayerRO.requestFriend(senderId, recieverId, requestname);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addFriend(int id, int playerid, String name1, String name2) {
        try {
            return PlayerRO.addFriend(id, playerid, name1, name2);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkrequestFriend(int senderId, int recieverId) {
        try {
            return PlayerRO.checkrequestFriend(senderId, recieverId);
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<RequestModel> selectRequest(int recieverid) {
        try {
            return RequestRO.selectRequest(recieverid);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean waiterOnline(int recieverid) {
        try {
            return RequestRO.waiterOnline(recieverid);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkOnline(String status, int id) {
        try {
            return RequestRO.checkOnline(status, id);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean insertRank(int idplayer, String fullname) {
        try {
            return RankRO.insertRank(idplayer, fullname);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkRank(int idplayer, String fullname) {
        try {
            return RankRO.checkRank(idplayer, fullname);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean rank(int rank) {
        try {
            return RankRO.rank(rank);
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Rank> orderRank() {
        try {
            return RankRO.orderRank();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteRequest(int senderId, int recieverId) {
        try {
            return RequestRO.deleteRequest(senderId, recieverId);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkFriend(int id_player1, int id_player2) {
        try {
            return PlayerRO.checkFriend(id_player1, id_player2);
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Friend> listFiend(int id) {
        try {
            return FriendRO.listFiend(id);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteFriend(int senderId, int recieverId) {
        try {
            return FriendRO.deleteFriend(senderId, recieverId);
        } catch (Exception e) {
            return false;
        }
    }
}
