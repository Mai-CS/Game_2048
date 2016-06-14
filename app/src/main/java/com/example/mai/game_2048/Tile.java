package com.example.mai.game_2048;

import android.graphics.Color;
import android.widget.TextView;

public class Tile {
    private TextView tv;
    private String value;
    private boolean isFilled;

    public TextView getTv() {
        return tv;
    }

    public void setTv(TextView tv) {
        this.tv = tv;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if (value != null) {
            tv.setText(value);
            this.value = value;
        } else {
            tv.setText("");
            this.value = "";
        }
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setIsFilled(boolean isFilled) {
        this.isFilled = isFilled;
    }

    public void paintTile() {
        if (isFilled) {
            switch (value) {
                case "2":
                    tv.setBackgroundResource(R.drawable.filled1);
                    tv.setTextColor(Color.BLACK);
                    break;
                case "4":
                    tv.setBackgroundResource(R.drawable.filled2);
                    tv.setTextColor(Color.BLACK);
                    break;
                case "8":
                    tv.setBackgroundResource(R.drawable.filled3);
                    tv.setTextColor(Color.WHITE);
                    break;
                case "16":
                    tv.setBackgroundResource(R.drawable.filled4);
                    tv.setTextColor(Color.WHITE);
                    break;
                case "32":
                    tv.setBackgroundResource(R.drawable.filled5);
                    tv.setTextColor(Color.WHITE);
                    break;
                case "64":
                    tv.setBackgroundResource(R.drawable.filled6);
                    tv.setTextColor(Color.WHITE);
                    break;
                case "128":
                    tv.setBackgroundResource(R.drawable.filled7);
                    tv.setTextColor(Color.WHITE);
                    break;
                case "256":
                    tv.setBackgroundResource(R.drawable.filled8);
                    tv.setTextColor(Color.WHITE);
                    break;
                case "512":
                    tv.setBackgroundResource(R.drawable.filled9);
                    tv.setTextColor(Color.WHITE);
                    break;
                case "1024":
                    tv.setBackgroundResource(R.drawable.filled10);
                    tv.setTextColor(Color.WHITE);
                    break;
                case "2048":
                    tv.setBackgroundResource(R.drawable.filled11);
                    tv.setTextColor(Color.WHITE);
                    break;
            }
        } else
            tv.setBackgroundResource(R.drawable.tiles);
    }

}