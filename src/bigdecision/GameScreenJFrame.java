/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigdecision;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import ttt.james.server.TTTWebService;
import ttt.james.server.TTTWebService_Service;

/**
 *
 * @author Colm
 */
public class GameScreenJFrame extends javax.swing.JFrame {
    private final TTTWebService service = new TTTWebService_Service().getTTTWebServicePort();
    private final MainCoordinator coordinator;
    private final int userId;
    private final int gameId;
    private final int ROWS = 3;
    private final int COLUMNS = 3;

    private JButton[][] gameButtons = new JButton[ROWS][COLUMNS];
    /**
     * Creates new form GameScreenJFrame
     * @param coordinator
     * @param userId
     * @param gameId
     */
    public GameScreenJFrame(MainCoordinator coordinator, int userId, int gameId) {
        this.coordinator = coordinator;
        this.userId = userId;
        this.gameId = gameId;
        initComponents();
        initGameBoard();
        setLocationRelativeTo(null);
        updateGameBoard();
    }
    
    private void initGameBoard() {
           GridLayout layout = new GridLayout(ROWS, COLUMNS);
           boardPanel.setLayout(layout);
           for(int i = 0; i < ROWS; i++) {
               for (int j = 0; j < COLUMNS; j++) {
                   final int row = i;
                   final int column = j;
                   JButton button = new JButton();
                   button.addActionListener(e -> onButtonPressed(row, column));
                   boardPanel.add(button);
                   gameButtons[row][column] = button;
               }
           }
           boardPanel.setPreferredSize(new Dimension(240, 240));
    }
    
    private void updateGameBoard() {
        String response = service.getBoard(gameId);
        if (response.equals("ERROR-NOMOVES") || response.equals("ERROR-DB")) {
            return;
        }
        
        String[] moves = response.split("\n");
        for(String move : moves) {
            String[] components = move.split(",");
            int playerId = Integer.parseInt(components[0]);
            int column =  Integer.parseInt(components[1]);
            int row =  Integer.parseInt(components[2]);
            
            String marker = playerId == userId ? "X" : "O";
            gameButtons[row][column].setText(marker);
        }
    }
    
    private void onButtonPressed(int row, int column) {
       String response = service.takeSquare(column, row, gameId, userId);
       switch (response) {
           case "ERROR-TAKEN":
               updateGameBoard();
               return;
           case "ERROR-DB":
               break;
           case "ERROR":
               break;
           case "0":
               updateGameBoard();
               break;
           case "1":
               updateGameBoard();
               break;
       }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        boardPanel = new javax.swing.JPanel();
        backButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout boardPanelLayout = new javax.swing.GroupLayout(boardPanel);
        boardPanel.setLayout(boardPanelLayout);
        boardPanelLayout.setHorizontalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        boardPanelLayout.setVerticalGroup(
            boardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );

        backButton.setLabel("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backButton)
                        .addGap(0, 325, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(boardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(backButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        coordinator.goToMainMenu(userId);
    }//GEN-LAST:event_backButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JPanel boardPanel;
    // End of variables declaration//GEN-END:variables
}
