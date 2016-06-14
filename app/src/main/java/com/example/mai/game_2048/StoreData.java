package com.example.mai.game_2048;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StoreData {
    Board board;
    File f;
    BufferedWriter bw;
    BufferedReader br;

    public StoreData(Board board) {
        this.board = board;
        String path = board.context.getFilesDir().getAbsolutePath() + "score2048.txt";
        f = new File(path);
    }

    public void writeToFile() {
        try {
            bw = new BufferedWriter(new FileWriter(f));
            bw.write(String.valueOf(board.sum) + "\n");
            bw.write(String.valueOf(board.canInsertTile) + "\n");
            for (int i = 0; i < 16; ++i) {
                if (board.tiles[i].getValue() != null)
                    bw.write(board.tiles[i].getValue() + "\n");
                else
                    bw.write("\n");
                bw.write(String.valueOf(board.tiles[i].isFilled()) + "\n");
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            board.warning(e.toString());
        }
    }

    public boolean readFromFile() {
        try {
            if (f.exists()) {
                br = new BufferedReader(new FileReader(f));
                String line;
                if ((line = br.readLine()) != null) {
                    board.sum = Integer.parseInt(line);
                    board.canInsertTile = Boolean.parseBoolean(br.readLine());
                }
                return true;
            } else
                return false;
        } catch (IOException e) {
            board.warning(e.toString());
            return false;
        }
    }

    public void readFromFile(int index) {
        try {
            board.tiles[index].setValue(br.readLine());
            board.tiles[index].setIsFilled(Boolean.parseBoolean(br.readLine()));
            board.tiles[index].paintTile();
        } catch (IOException e) {
            board.warning(e.toString());
        }
    }

}