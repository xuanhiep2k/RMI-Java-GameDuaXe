package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Friend;

import model.Player;
import model.Rank;
import model.RequestModel;

public class PlayerDAO extends DAO {

    public PlayerDAO() {
        super();
    }

    public Player checkLogin(Player player) {
        String sql = "SELECT id_player, fullname, position FROM tblplayer WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, player.getUsername());
            ps.setString(2, player.getPassword());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                player.setIdplayer(rs.getInt("id_player"));
                player.setFullname(rs.getString("fullname"));
                player.setPosition(rs.getString("position"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return player;
    }

    public String registerUser(Player player) {
        String sql = "INSERT INTO tblplayer ( username, password, fullname, gender, email, position) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, player.getUsername());
            ps.setString(2, player.getPassword());
            ps.setString(3, player.getFullname());
            ps.setString(4, player.getGender());
            ps.setString(5, player.getEmail());
            ps.setString(6, player.getPosition());

            if (ps.executeUpdate() > 0) {
                return "ok";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePlayerstatus(String value, int id) {
        String sql = "UPDATE tblplayer SET status = ? WHERE id_player = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, value);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkOnline(String status, int id) {
        boolean result = false;
        String sql = "SELECT * FROM tblplayer WHERE status = ? AND id_player = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

    public ArrayList<Player> playersOnline(Player player) {
        ArrayList<Player> list = new ArrayList<>();
        String sql = "SELECT id_player,fullname FROM tblplayer WHERE status = 1";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                player = new Player();
                player.setIdplayer(rs.getInt("id_player"));
                player.setFullname(rs.getString("fullname"));
                list.add(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public boolean waiterOnline(int recieverid) {
        String sql = "SELECT status FROM tblrequestfriend WHERE recieverid = ?";
        boolean result = false;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, recieverid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RequestModel request = new RequestModel();
                request.setRecieverid(rs.getInt("recieverid"));
                request.setRequestname(rs.getString("requestname"));
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

    public boolean requestFriend(int senderId, int recieverId, String requestname) {
        String sql = "INSERT IGNORE INTO tblfriendrequest (senderid, recieverid, requestname) VALUES (?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, senderId);
            ps.setInt(2, recieverId);
            ps.setString(3, requestname);
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        return false;
    }

    public boolean checkrequestFriend(int senderId, int recieverId) {
        String sql = "SELECT * FROM tblfriendrequest WHERE senderid = ? AND recieverid = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, senderId);
            ps.setInt(2, recieverId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public ArrayList<RequestModel> selectRequest(int recieverid) {
        ArrayList<RequestModel> list = new ArrayList<>();
        String sql = "SELECT * FROM tblfriendrequest WHERE recieverid = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, recieverid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RequestModel model = new RequestModel();
                model.setSenderid(rs.getInt("senderid"));
                model.setRecieverid(rs.getInt("recieverid"));
                model.setRequestname(rs.getString("requestname"));
                list.add(model);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return list;
    }

    public boolean deleteRequest(int senderId, int recieverId) {
        boolean result = false;
        String sql = "DELETE FROM tblfriendrequest WHERE senderid = ? AND recieverid =?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, senderId);
            ps.setInt(2, recieverId);
            if (ps.executeUpdate() > 0) {
                result = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return result;
    }

    public boolean insertRank(int idplayer, String fullname) {
        String sql = "INSERT IGNORE INTO tblrank (idplayer, fullname) VALUES (?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idplayer);
            ps.setString(2, fullname);
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean checkRank(int idplayer, String fullname) {
        String sql = "SELECT * FROM tblrank WHERE idplayer = ? AND fullname = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idplayer);
            ps.setString(2, fullname);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean rank(int rank) {

        String sql = "INSERT IGNORE INTO tblrank (rank) VALUES (?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, rank);
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public ArrayList<Rank> orderRank() {
        ArrayList<Rank> list = new ArrayList<>();
        String order = "SELECT * FROM tblrank ORDER BY core DESC";
        String sql = "SELECT RANK() OVER (ORDER BY core DESC) AS rank, idplayer, fullname, core FROM tblrank";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            PreparedStatement ps1 = con.prepareStatement(order);
            ResultSet rs = ps.executeQuery();
            ResultSet rs1 = ps1.executeQuery();
            while (rs.next() && rs1.next()) {
                Rank rank = new Rank();
                rank.setIdplayer(rs.getInt("idplayer"));
                rank.setFullname(rs.getString("fullname"));
                rank.setCore(rs.getFloat("core"));
                rank.setRank(rs.getInt("rank"));
                list.add(rank);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public boolean addFriend(int senderId, int recieverId, String name1, String name2) {
        boolean result = false;
        String sql = "INSERT INTO tblfriendlist (id_player1, id_player2, namePlayer1, namePlayer2) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, senderId);
            ps.setInt(2, recieverId);
            ps.setString(3, name1);
            ps.setString(4, name2);
            if (ps.executeUpdate() > 0) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

    public boolean checkFriend(int id_player1, int id_player2) {
        String sql = "SELECT * FROM tblfriendlist WHERE id_player1 = ? AND id_player2 = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, id_player1);
            ps.setInt(2, id_player2);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public ArrayList<Friend> listFiend(int id) {
        ArrayList<Friend> list = new ArrayList<>();
        String sql = "SELECT * FROM tblfriendlist WHERE id_player1 = ? OR id_player2 = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Friend fr = new Friend();
                fr.setIdPlayer1(rs.getInt("id_player1"));
                fr.setIdPlayer2(rs.getInt("id_player2"));
                fr.setNamePlayer1(rs.getString("namePlayer1"));
                fr.setNamePlayer2(rs.getString("namePlayer2"));
                list.add(fr);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public boolean deleteFriend(int senderId, int recieverId) {
        boolean result = false;
        String sql = "DELETE FROM tblfriendlist WHERE id_player1 = ? AND id_player2 =?;";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, senderId);
            ps.setInt(2, recieverId);
            if (ps.executeUpdate() > 0) {
                result = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return result;
    }
}
