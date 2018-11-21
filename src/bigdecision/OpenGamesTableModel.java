/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigdecision;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Colm
 */
public class OpenGamesTableModel extends AbstractTableModel {

    public static final int GAME_ID_COLUMN = 0;
    private final String[] columns = { "Game ID", "Player", "Start Date" };
    
    private final String[][] rowData;
    
    public OpenGamesTableModel(String[][] data) {
        this.rowData = data;
    } 
   
    public OpenGamesTableModel(String response) {
        String[] rows = response.split("\n");
        rowData = new String[rows.length][columns.length];
        for(int i = 0; i < rows.length; i++) {
            rowData[i] = rows[i].split(",");
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
