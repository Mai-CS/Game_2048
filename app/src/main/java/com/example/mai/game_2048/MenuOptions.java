package com.example.mai.game_2048;

public class MenuOptions {

    Board board;

    public MenuOptions() {

    }

    public MenuOptions(Board board) {
        this.board = board;
    }

    public void copyDataTo() {
        try {
            board.sumTemp = board.sum;
            board.arrayTemp = new Tile[16];
            for (int i = 0; i < 16; ++i) {
                board.arrayTemp[i] = new Tile();
                board.arrayTemp[i].setTv(board.tiles[i].getTv());
                board.arrayTemp[i].setValue(board.tiles[i].getValue());
                board.arrayTemp[i].setIsFilled(board.tiles[i].isFilled());
            }
        } catch (Exception e) {
            board.warning("Unexpected Error! Can't Undo");
        }
    }

    public boolean undo() {
        try {
            if (board.arrayTemp == null)
                return false;

            if (!board.canInsertTile)
                //Reset last tile from arrayTemp to not be painted again after copying data back
                if (board.arrayTemp[board.randTile] != null) {
                    board.arrayTemp[board.randTile].setValue("");
                    board.arrayTemp[board.randTile].setIsFilled(false);
                    board.arrayTemp[board.randTile].paintTile();
                }

            board.tiles = new Tile[16];
            for (int i = 0; i < 16; ++i) {
                //Copy data back
                board.tiles[i] = new Tile();
                board.tiles[i].setTv(board.arrayTemp[i].getTv());
                board.tiles[i].setValue(board.arrayTemp[i].getValue());
                board.tiles[i].setIsFilled(board.arrayTemp[i].isFilled());
                board.tiles[i].paintTile();
            }
            board.sum = board.sumTemp;
            board.score.setText("Score\n" + String.valueOf(board.sum));
            board.arrayTemp = null;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

//    public void restart(Context context) {
//        Intent i = new Intent(context, MainActivity.class);
//        ((Activity) context).finish();
//        context.startActivity(i);
//    }

    public void restart() {
        board.sum = 0;
        board.score.setText("Score\n" + String.valueOf(board.sum));
        board.canInsertTile = true;
        for (int i = 0; i < 16; ++i)
            board.resetTile(i);

        board.generateRandomTile(2);
    }

}