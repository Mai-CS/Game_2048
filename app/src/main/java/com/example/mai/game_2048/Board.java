package com.example.mai.game_2048;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Board {

    Context context;
    Tile[] tiles;
    TextView score;
    int sum; /*Calculate score*/
    int randTile;
    Tile[] arrayTemp;
    int sumTemp;
    boolean canInsertTile;

    public Board(Context context) {
        this.context = context;
        canInsertTile = true;
    }

    public void generateRandomTile(int numberOfTiles) {
        if (canInsertTile) {
            Random rand = new Random();
            for (int i = 1; i <= numberOfTiles; ++i) {
                do {
                    //avoid already filled tiles
                    randTile = rand.nextInt(16);
                } while (tiles[randTile].isFilled());
                addTile(randTile, "2");
                tiles[randTile].getTv().startAnimation(
                        AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
            }
        }
    }

    public void moveUp() {
        for (int i = 0; i <= 3; ++i) // for each column
        {
            for (int j = i; j <= i + 8; j += 4) //finish all available matches
            {
                if (tiles[j].isFilled())
                    //see if there is similar tile in same column
                    for (int k = j + 4; k <= i + 12; k += 4) {
                        if (!tiles[k].isFilled())
                            continue;

                        matchTile(j, k);
                        break;
                    }
            }

            int countSteps = 0; //move tile up with number of steps
            for (int j = i; j <= i + 12; j += 4) //move tiles up
            {
                if (!tiles[j].isFilled())
                    countSteps++;
                else if (tiles[j].isFilled()) {
                    String text = tiles[j].getValue();
                    resetTile(j);
                    addTile(Math.abs((j - countSteps * 4)), text);
                    j = Math.abs((j - countSteps * 4));
                    countSteps = 0;
                }
            }
        }
    }

    public void moveLeft() {
        for (int i = 0; i <= 12; i += 4) {
            for (int j = i; j <= i + 2; ++j) {
                if (tiles[j].isFilled())
                    for (int k = j + 1; k <= i + 3; ++k) {
                        if (!tiles[k].isFilled())
                            continue;

                        matchTile(j, k);
                        break;
                    }
            }

            int countSteps = 0;
            for (int j = i; j <= i + 3; ++j) {
                if (!tiles[j].isFilled())
                    countSteps++;
                else if (tiles[j].isFilled()) {
                    String text = tiles[j].getValue();
                    resetTile(j);
                    addTile(Math.abs((j - countSteps)), text);
                    j = Math.abs((j - countSteps));
                    countSteps = 0;
                }
            }
        }
    }

    public void moveDown() {
        for (int i = 12; i <= 15; ++i) {
            for (int j = i; j >= i - 8; j -= 4) {
                if (tiles[j].isFilled())
                    for (int k = j - 4; k >= i - 12; k -= 4) {
                        if (!tiles[k].isFilled())
                            continue;

                        matchTile(j, k);
                        break;
                    }
            }

            int countSteps = 0;
            for (int j = i; j >= i - 12; j -= 4) {
                if (!tiles[j].isFilled())
                    countSteps++;

                else if (tiles[j].isFilled()) {
                    String text = tiles[j].getValue();
                    resetTile(j);
                    addTile((j + countSteps * 4), text);
                    j = (j + countSteps * 4);
                    countSteps = 0;
                }
            }
        }
    }

    public void moveRight() {
        for (int i = 3; i <= 15; i += 4) {
            for (int j = i; j >= i - 2; --j) {
                if (tiles[j].isFilled())
                    for (int k = j - 1; k >= i - 3; --k) {
                        if (!tiles[k].isFilled())
                            continue;

                        matchTile(j, k);
                        break;
                    }
            }

            int countSteps = 0;
            for (int j = i; j >= i - 3; --j) {
                if (!tiles[j].isFilled())
                    countSteps++;

                else if (tiles[j].isFilled()) {
                    String text = tiles[j].getValue();
                    resetTile(j);
                    addTile(j + countSteps, text);
                    j = j + countSteps;
                    countSteps = 0;
                }
            }
        }
    }

    public void resetTile(int index) {
        if (tiles[index] != null) {
            tiles[index].setValue("");
            tiles[index].setIsFilled(false);
            tiles[index].paintTile();
        }
    }

    public void addTile(int index, String text) {
        tiles[index].setValue(text);
        tiles[index].setIsFilled(true);
        tiles[index].paintTile();
    }

    public void matchTile(int j, int k) {
        if (isIdentical(j, k)) {
            int num = 2 * Integer.parseInt(tiles[j].getTv().getText().toString());
            tiles[j].setValue(String.valueOf(num));
            tiles[j].paintTile();
            sum += num;
            score.setText("Score\n" + String.valueOf(sum));
            resetTile(k);
            if (win(num)) {
                EndDialog ed = new EndDialog();
                ed.showWinDialog(context, new MenuOptions());
            }
        }
    }

    public boolean lose() {
        setCanInsertTile();
        return !canInsertTile && !canMoveColumn() && !canMoveRow();
    }

    public boolean win(int num) {
        return num == 2048;
    }

    public void setCanInsertTile() {
        for (int i = 0; i < 16; ++i) {
            if (!tiles[i].isFilled()) {
                canInsertTile = true;
                return;
            }
        }
        canInsertTile = false;
    }

    public boolean canMoveColumn() {
        for (int i = 0; i <= 3; ++i) {
            for (int j = i; j <= i + 8; j += 4) {
                if (tiles[j].isFilled()) {
                    if (!tiles[j + 4].isFilled() || isIdentical(j, j + 4))
                        return true;
                }
            }
        }
        return false;
    }

    public boolean canMoveRow() {
        for (int i = 0; i <= 12; i += 4) {
            for (int j = i; j <= i + 2; ++j) {
                if (tiles[j].isFilled()) {
                    if (!tiles[j + 1].isFilled() || isIdentical(j, j + 1))
                        return true;
                }
            }
        }
        return false;
    }

    public boolean isIdentical(int j, int k) {
        return tiles[k].isFilled() && (tiles[k].getValue().equals(tiles[j].getValue()));
    }

    public void warning(String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }

}