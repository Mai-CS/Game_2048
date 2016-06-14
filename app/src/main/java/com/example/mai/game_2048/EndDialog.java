package com.example.mai.game_2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class EndDialog {

    public void showLoseDialog(final Context c, final Board b, final MenuOptions m) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setMessage(R.string.gameOver);
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.undo,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (!m.undo())
                            b.warning("No available steps!");
                    }
                });
        builder.setNegativeButton(R.string.tryAgain,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        m.restart();
                    }
                });

        builder.setCancelable(false); //prevent user from touching screen outside the dialog
        builder.create().show();
    }

    public void showWinDialog(final Context c, final MenuOptions m) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setMessage(R.string.youWin);
        builder.setCancelable(true);
        builder.setPositiveButton(R.string.restart,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        m.restart();
                    }
                });


        builder.setCancelable(false);
        builder.create().show();
    }

}