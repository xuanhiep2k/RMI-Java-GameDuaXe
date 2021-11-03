/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.PlayerCtr;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Friend;

import model.Player;
import model.Rank;
import model.RequestModel;

/**
 *
 * @author AdamKyle
 */
public class PlayerMainFrm extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    private ArrayList<Player> list;
    private ArrayList<RequestModel> list1;
    private ArrayList<Rank> list2;
    private ArrayList<Friend> list4;
    private Player player;

    private PlayerCtr myRemoteObject;

    public PlayerMainFrm(PlayerCtr ro, Player pl) {
        initComponents();

        list = new ArrayList<Player>();
        list1 = new ArrayList<RequestModel>();
        list2 = new ArrayList<Rank>();
        list4 = new ArrayList<Friend>();

        myRemoteObject = ro;
        this.player = pl;

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        txt_Fullname.setText("Xin chao: " + player.getFullname());

        tbl_listFirend();
        tbl_onlineFriend();
        tbl_rankFriend();
        tbl_waitFriend();

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                myRemoteObject.updatePlayerstatus("0", player.getIdplayer());
                System.exit(0);
            }
        });
    }

    public void tbl_waitFriend() {
//table wait friend
        ImageIcon online = new ImageIcon("C:\\Users\\NONAME.NONAME\\Documents\\NetBeansProjects\\RMIClient\\src\\img\\online.png");
        ImageIcon offline = new ImageIcon("C:\\Users\\NONAME.NONAME\\Documents\\NetBeansProjects\\RMIClient\\src\\img\\offline.png");
        list1 = myRemoteObject.selectRequest(player.getIdplayer());
        String[] columnNames1 = {"", "Danh sách chờ duyệt"};
        Object[][] value1 = new Object[list1.size()][columnNames1.length];
        for (int i = 0; i < list1.size(); i++) {
            if (myRemoteObject.checkOnline("1", list1.get(i).getSenderid())) {
                value1[i][0] = online;
                value1[i][1] = list1.get(i).getRequestname();
            } else {
                value1[i][0] = offline;
                value1[i][1] = list1.get(i).getRequestname();
            }
        }

        DefaultTableModel tableModelWait = new DefaultTableModel(value1, columnNames1) {
            @Override
            public Class getColumnClass(int column1) {
                tbl_wait.getColumnModel().getColumn(0).setPreferredWidth(30);
                tbl_wait.getColumnModel().getColumn(1).setPreferredWidth(500);
                return (column1 == 0) ? Icon.class : Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                //unable to edit cells
                return false;
            }
        };
        tbl_wait.setModel(tableModelWait);
    }

    public void tbl_rankFriend() {
        //table rank friend
        ImageIcon online = new ImageIcon("C:\\Users\\NONAME.NONAME\\Documents\\NetBeansProjects\\RMIClient\\src\\img\\online.png");
        ImageIcon offline = new ImageIcon("C:\\Users\\NONAME.NONAME\\Documents\\NetBeansProjects\\RMIClient\\src\\img\\offline.png");
        list2 = myRemoteObject.orderRank();
        String[] columnNames2 = {"", "Name", "Core", "Rank"};
        Object[][] value2 = new Object[list2.size()][columnNames2.length];
        for (int i = 0; i < list2.size(); i++) {
            if (myRemoteObject.checkOnline("1", list2.get(i).getIdplayer())) {
                value2[i][0] = online;
                value2[i][1] = list2.get(i).getFullname();
                value2[i][2] = list2.get(i).getCore();
                value2[i][3] = list2.get(i).getRank();
            } else {
                value2[i][0] = offline;
                value2[i][1] = list2.get(i).getFullname();
                value2[i][2] = list2.get(i).getCore();
                value2[i][3] = list2.get(i).getRank();
            }
        }

        DefaultTableModel tableModelRank = new DefaultTableModel(value2, columnNames2) {
            @Override
            public Class getColumnClass(int column1) {
                tbl_rank.getColumnModel().getColumn(0).setPreferredWidth(30);
                tbl_rank.getColumnModel().getColumn(1).setPreferredWidth(300);
                tbl_rank.getColumnModel().getColumn(2).setPreferredWidth(100);
                tbl_rank.getColumnModel().getColumn(3).setPreferredWidth(100);
                return (column1 == 0) ? Icon.class : Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                //unable to edit cells
                return false;
            }
        };
        tbl_rank.setModel(tableModelRank);
    }

    public void tbl_listFirend() {
//table list friend
        ImageIcon online = new ImageIcon("C:\\Users\\NONAME.NONAME\\Documents\\NetBeansProjects\\RMIClient\\src\\img\\online.png");
        ImageIcon offline = new ImageIcon("C:\\Users\\NONAME.NONAME\\Documents\\NetBeansProjects\\RMIClient\\src\\img\\offline.png");
        list4 = myRemoteObject.listFiend(player.getIdplayer());
        String[] columnNames3 = {"", "Fullname"};
        Object[][] value3 = new Object[list4.size()][columnNames3.length];
        for (int i = 0; i < list4.size(); i++) {
            if (list4.get(i).getIdPlayer1() == player.getIdplayer()) {
                if (myRemoteObject.checkOnline("1", list4.get(i).getIdPlayer2())) {
                    value3[i][0] = online;
                } else {
                    value3[i][0] = offline;
                }
            } else if (list4.get(i).getIdPlayer2() == player.getIdplayer()) {
                if (myRemoteObject.checkOnline("1", list4.get(i).getIdPlayer1())) {
                    value3[i][0] = online;
                } else {
                    value3[i][0] = offline;
                }
            }
            if (list4.get(i).getIdPlayer1() == player.getIdplayer()) {
                value3[i][1] = list4.get(i).getNamePlayer2();
            } else {
                value3[i][1] = list4.get(i).getNamePlayer1();
            }
        }

        DefaultTableModel tableModelFriend = new DefaultTableModel(value3, columnNames3) {
            @Override
            public Class getColumnClass(int column1) {
                tbl_friends.getColumnModel().getColumn(0).setPreferredWidth(30);
                tbl_friends.getColumnModel().getColumn(1).setPreferredWidth(500);
                return (column1 == 0) ? Icon.class : Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                //unable to edit cells
                return false;
            }
        };
        tbl_friends.setModel(tableModelFriend);
    }

    public void tbl_onlineFriend() {
        ImageIcon online = new ImageIcon("C:\\Users\\NONAME.NONAME\\Documents\\NetBeansProjects\\RMIClient\\src\\img\\online.png");
        //table online friend
        list = myRemoteObject.playersOnline(player);

        String[] columnNames = {"", "Danh sách người chơi online"};
        Object[][] value = new Object[list.size()][columnNames.length];
        for (int i = 0; i < list.size(); i++) {
            value[i][0] = online;
            value[i][1] = list.get(i).getFullname();
        }

        DefaultTableModel tableModelOnline = new DefaultTableModel(value, columnNames) {
            @Override
            public Class getColumnClass(int column
            ) {
                tbl_players_online.getColumnModel().getColumn(0).setPreferredWidth(30);
                tbl_players_online.getColumnModel().getColumn(1).setPreferredWidth(500);
                return (column == 0) ? Icon.class : Object.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                //unable to edit cells
                return false;
            }
        };
        tbl_players_online.setModel(tableModelOnline);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbFoundMatch = new javax.swing.JLabel();
        lbTimerPairMatch = new javax.swing.JLabel();
        btnAcceptPairMatch = new javax.swing.JButton();
        btnDeclinePairMatch = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txt_Fullname = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        lbFindMatch = new javax.swing.JLabel();
        jProgressBar2 = new javax.swing.JProgressBar();
        btnCancelFindMatch = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_players_online = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_friends = new javax.swing.JTable();
        btnDeleteFriend = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_wait = new javax.swing.JTable();
        btnAccept = new javax.swing.JButton();
        btnDeny = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_rank = new javax.swing.JTable();
        btnAddFriend = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        lbFoundMatch1 = new javax.swing.JLabel();
        lbTimerPairMatch1 = new javax.swing.JLabel();
        btnAcceptPairMatch1 = new javax.swing.JButton();
        btnDeclinePairMatch1 = new javax.swing.JButton();

        lbFoundMatch.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbFoundMatch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbFoundMatch.setText("Đã tìm thấy đối thủ ... Vào ngay?");

        lbTimerPairMatch.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        lbTimerPairMatch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTimerPairMatch.setText("15s");

        btnAcceptPairMatch.setText("Chấp nhận");
        btnAcceptPairMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptPairMatchActionPerformed(evt);
            }
        });

        btnDeclinePairMatch.setText("Từ chối");
        btnDeclinePairMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeclinePairMatchActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txt_Fullname.setBackground(new java.awt.Color(204, 204, 204));
        txt_Fullname.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        txt_Fullname.setForeground(new java.awt.Color(51, 51, 255));
        txt_Fullname.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jButton2.setText("Thoát");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jProgressBar1.setIndeterminate(true);

        lbFindMatch.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbFindMatch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbFindMatch.setText("Đang tìm trận...");

        jProgressBar2.setIndeterminate(true);

        btnCancelFindMatch.setText("Hủy");
        btnCancelFindMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelFindMatchActionPerformed(evt);
            }
        });

        tbl_players_online.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Danh sách người chơi online"
            }
        ));
        tbl_players_online.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_players_onlineMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_players_online);
        if (tbl_players_online.getColumnModel().getColumnCount() > 0) {
            tbl_players_online.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Danh sách người chơi", jPanel1);

        tbl_friends.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Fullname"
            }
        ));
        tbl_friends.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_friendsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_friends);

        btnDeleteFriend.setText("Xóa ");
        btnDeleteFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteFriendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnDeleteFriend, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteFriend)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Danh sách bạn bè", jPanel3);

        tbl_wait.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Danh sách chờ duyệt"
            }
        ));
        tbl_wait.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_waitMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_wait);

        btnAccept.setText("Accept");
        btnAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptActionPerformed(evt);
            }
        });

        btnDeny.setText("Deny");
        btnDeny.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDenyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnDeny, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnAccept, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAccept)
                    .addComponent(btnDeny))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Chờ duyệt", jPanel4);

        tbl_rank.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Name", "Rank"
            }
        ));
        tbl_rank.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_rankMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_rank);

        btnAddFriend.setText("Add Friend");
        btnAddFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFriendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnAddFriend))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddFriend)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Bảng xếp hạng", jPanel5);

        btnRefresh.setText("Làm mới");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        lbFoundMatch1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbFoundMatch1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbFoundMatch1.setText("Đã tìm thấy đối thủ ... Vào ngay?");

        lbTimerPairMatch1.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        lbTimerPairMatch1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTimerPairMatch1.setText("15s");

        btnAcceptPairMatch1.setText("Chấp nhận");
        btnAcceptPairMatch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptPairMatch1ActionPerformed(evt);
            }
        });

        btnDeclinePairMatch1.setText("Từ chối");
        btnDeclinePairMatch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeclinePairMatch1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDeclinePairMatch1)
                        .addGap(28, 28, 28)
                        .addComponent(btnAcceptPairMatch1)
                        .addGap(143, 143, 143))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbFoundMatch1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbTimerPairMatch1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(btnRefresh))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(txt_Fullname, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(jButton2)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(7, 7, 7)))))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbFindMatch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelFindMatch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_Fullname, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(lbFindMatch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelFindMatch)
                    .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lbFoundMatch1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTimerPairMatch1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeclinePairMatch1)
                    .addComponent(btnAcceptPairMatch1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefresh)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        myRemoteObject.updatePlayerstatus("0", player.getIdplayer());

        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnCancelFindMatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelFindMatchActionPerformed
        // chỉ gửi yêu cầu lên server chứ ko đổi giao diện ngay
        // socketHandler sẽ đọc kết quả trả về từ server và quyết định có đổi stateDisplay hay không

    }//GEN-LAST:event_btnCancelFindMatchActionPerformed

    private void btnAcceptPairMatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptPairMatchActionPerformed

    }//GEN-LAST:event_btnAcceptPairMatchActionPerformed

    private void btnDeclinePairMatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeclinePairMatchActionPerformed

    }//GEN-LAST:event_btnDeclinePairMatchActionPerformed

    private void btnAcceptPairMatch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptPairMatch1ActionPerformed

    }//GEN-LAST:event_btnAcceptPairMatch1ActionPerformed

    private void btnDeclinePairMatch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeclinePairMatch1ActionPerformed

    }//GEN-LAST:event_btnDeclinePairMatch1ActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        tbl_listFirend();
        tbl_onlineFriend();
        tbl_rankFriend();
        tbl_waitFriend();
    }//GEN-LAST:event_btnRefreshActionPerformed

    Rank temp = new Rank();
    private void tbl_players_onlineMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_players_onlineMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tbl_players_onlineMouseClicked

    private void btnAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptActionPerformed
        // TODO add your handling code here:

        if (!myRemoteObject.checkFriend(request.getSenderid(), request.getRecieverid()) && !myRemoteObject.checkFriend(request.getRecieverid(), request.getSenderid())) {
            if (myRemoteObject.addFriend(request.getSenderid(), request.getRecieverid(), request.getRequestname(), player.getFullname())) {
                JOptionPane.showMessageDialog(this, "Ban va " + request.getRequestname() + " da tro thanh ban be");
                myRemoteObject.deleteRequest(request.getSenderid(), request.getRecieverid());
                myRemoteObject.deleteRequest(request.getRecieverid(), request.getSenderid());
                tbl_waitFriend();
                tbl_listFirend();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ban da ket ban roi");

        }

    }//GEN-LAST:event_btnAcceptActionPerformed

    private void btnAddFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFriendActionPerformed
        // TODO add your handling code here:
        if (!myRemoteObject.checkFriend(player.getIdplayer(), temp.getIdplayer()) && !myRemoteObject.checkFriend(temp.getIdplayer(), player.getIdplayer())) {
            if (player.getIdplayer() != temp.getIdplayer() && !myRemoteObject.checkrequestFriend(player.getIdplayer(), temp.getIdplayer())) {
                if (myRemoteObject.requestFriend(player.getIdplayer(), temp.getIdplayer(), player.getFullname())) {
                    JOptionPane.showMessageDialog(this, "Da gui loi moi ket ban voi " + temp.getFullname());
                    btnAddFriend.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Khong the ket ban");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Da gui loi moi ket ban roi, khong the lap lai");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Hai ban da la ban be");
        }
    }//GEN-LAST:event_btnAddFriendActionPerformed

    private void tbl_rankMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_rankMouseClicked
        // TODO add your handling code here:
        int column = tbl_rank.getColumnModel().getColumnIndexAtX(evt.getX()); // get the coloum of the button
        int row = evt.getY() / tbl_rank.getRowHeight(); // get the row of the button

        // *Checking the row or column is valid or not
        if (row < tbl_rank.getRowCount() && row >= 0 && column < tbl_rank.getColumnCount() && column >= 0) {
            temp = list2.get(row);
            if (myRemoteObject.checkrequestFriend(player.getIdplayer(), temp.getIdplayer())) {
                btnAddFriend.setEnabled(false);
            } else {
                btnAddFriend.setEnabled(true);
            }
            if (myRemoteObject.checkFriend(player.getIdplayer(), temp.getIdplayer()) || myRemoteObject.checkFriend(temp.getIdplayer(), player.getIdplayer())) {
                btnAddFriend.setEnabled(false);
            } else {
                btnAddFriend.setEnabled(true);
            }
            if (player.getIdplayer() == temp.getIdplayer()) {
                btnAddFriend.setEnabled(false);
            }
        }
    }//GEN-LAST:event_tbl_rankMouseClicked
    RequestModel request = new RequestModel();
    private void btnDenyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDenyActionPerformed
        // TODO add your handling code here:
        if (myRemoteObject.deleteRequest(request.getSenderid(), request.getRecieverid()) || myRemoteObject.deleteRequest(request.getRecieverid(), request.getSenderid())) {
            JOptionPane.showMessageDialog(this, "Da huy loi moi ket ban " + request.getRequestname());
            tbl_waitFriend();
        } else {
            JOptionPane.showMessageDialog(this, "Khong the thuc hien ");
        }
    }//GEN-LAST:event_btnDenyActionPerformed

    private void tbl_waitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_waitMouseClicked
        // TODO add your handling code here:

        int column = tbl_wait.getColumnModel().getColumnIndexAtX(evt.getX()); // get the coloum of the button
        int row = evt.getY() / tbl_wait.getRowHeight(); // get the row of the button

        // *Checking the row or column is valid or not
        if (row < tbl_wait.getRowCount() && row >= 0 && column < tbl_wait.getColumnCount() && column >= 0) {
            request = list1.get(row);
        }
    }//GEN-LAST:event_tbl_waitMouseClicked

    Friend delFriend = new Friend();
    private void btnDeleteFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteFriendActionPerformed
        // TODO add your handling code here:
        if (player.getIdplayer() == delFriend.getIdPlayer1()) {
            if (myRemoteObject.deleteFriend(player.getIdplayer(), delFriend.getIdPlayer2())) {
                JOptionPane.showMessageDialog(this, "Da xoa " + delFriend.getNamePlayer2() + " khoi danh sach ban be");
                tbl_listFirend();
            }
        } else if (player.getIdplayer() == delFriend.getIdPlayer2()) {
            myRemoteObject.deleteFriend(delFriend.getIdPlayer1(), player.getIdplayer());
            JOptionPane.showMessageDialog(this, "Da xoa " + delFriend.getNamePlayer1() + " khoi danh sach ban be");
            tbl_listFirend();
        }
    }//GEN-LAST:event_btnDeleteFriendActionPerformed

    private void tbl_friendsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_friendsMouseClicked
        // TODO add your handling code here:
        int column = tbl_friends.getColumnModel().getColumnIndexAtX(evt.getX()); // get the coloum of the button
        int row = evt.getY() / tbl_friends.getRowHeight(); // get the row of the button

        // *Checking the row or column is valid or not
        if (row < tbl_friends.getRowCount() && row >= 0 && column < tbl_friends.getColumnCount() && column >= 0) {
            delFriend = list4.get(row);
        }
    }//GEN-LAST:event_tbl_friendsMouseClicked

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccept;
    private javax.swing.JButton btnAcceptPairMatch;
    private javax.swing.JButton btnAcceptPairMatch1;
    private javax.swing.JButton btnAddFriend;
    private javax.swing.JButton btnCancelFindMatch;
    private javax.swing.JButton btnDeclinePairMatch;
    private javax.swing.JButton btnDeclinePairMatch1;
    private javax.swing.JButton btnDeleteFriend;
    private javax.swing.JButton btnDeny;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbFindMatch;
    private javax.swing.JLabel lbFoundMatch;
    private javax.swing.JLabel lbFoundMatch1;
    private javax.swing.JLabel lbTimerPairMatch;
    private javax.swing.JLabel lbTimerPairMatch1;
    private javax.swing.JTable tbl_friends;
    private javax.swing.JTable tbl_players_online;
    private javax.swing.JTable tbl_rank;
    private javax.swing.JTable tbl_wait;
    private javax.swing.JLabel txt_Fullname;
    // End of variables declaration//GEN-END:variables
}
