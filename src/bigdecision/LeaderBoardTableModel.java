/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigdecision;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.table.AbstractTableModel;
import ttt.james.server.TTTWebService;
import ttt.james.server.TTTWebService_Service;

/**
 *
 * @author Manus
 */
public class LeaderBoardTableModel extends AbstractTableModel {

    private final TTTWebService service = new TTTWebService_Service().getTTTWebServicePort();

    public static final int GAME_ID_COLUMN = 0;
    private final String[] columns = {"Player", "Wins", "Losses", "Draws"}; //headings for leaderboard
    private String[][] rowData; //location for leaderboard entries to be stored
    private int userId;
    int[] wins;
    int[] losses;
    int[] draws;
    ArrayList<String> playerSet;

    public LeaderBoardTableModel(String[][] data, int userId) {
        this.rowData = data;
        this.userId = userId;
    }

    //define how to store the response from the leagueTable() webservice method
    //split each game info by the "\n"
    //split each element of each game by ","
    public LeaderBoardTableModel(String response) {
        findPlayers(response);
        tableLayout();
    }
    
    public void tableLayout(){
        rowData = new String[playerSet.size()][columns.length];
        for(int i = 0; i < playerSet.size(); i++) {
            rowData[i][0] = playerSet.get(i);
            rowData[i][1] = "" + wins[i];
            rowData[i][2] = "" + losses[i];
            rowData[i][3] = "" + draws[i];
        }
    }

    private void findPlayers(String response) {
        String player;
        playerSet = new ArrayList<String>();
        String games[] = response.split("\n");
        for (int i = 0; i < games.length; i++) {
          String[] components = games[i].split(",");
            if (!playerSet.contains(components[1])) {
                playerSet.add(components[1]);
            }

            if (!playerSet.contains(components[2])) {
                playerSet.add(components[2]);
            }
        }

        wins = new int[playerSet.size()];
        losses = new int[playerSet.size()];
        draws = new int[playerSet.size()];
        for (String game : games) {
            calculateResults(game);
        }
    }
    
    public void calculateResults(String game) {
        int index;
        String[] components = game.split(",");
            int gameId = Integer.parseInt(components[0]);
            String result = service.getGameState(gameId);
            switch (result) {
                case "1":
                    index = playerSet.indexOf(components[1]);
                    wins[index] += 1;
                    index = playerSet.indexOf(components[2]);
                    losses[index] += 1;
                    break;
                case "2":
                    index = playerSet.indexOf(components[2]);
                    wins[index] += 1;
                    index = playerSet.indexOf(components[1]);
                    losses[index] += 1;
                    break;
                case "3":
                    index = playerSet.indexOf(components[2]);
                    draws[index] += 1;
                    index = playerSet.indexOf(components[1]);
                    draws[index] += 1;
                default:
            }

    }

    @Override
    public int getRowCount() {
        return rowData.length;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rowData[rowIndex][columnIndex];
    }
    
        @Override
    public String getColumnName(int column) {
        return columns[column];
    }

}
