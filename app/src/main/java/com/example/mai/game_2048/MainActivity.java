package com.example.mai.game_2048;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;
import android.support.v4.view.GestureDetectorCompat;

public class MainActivity extends Activity implements GestureDetector.OnGestureListener {

    GestureDetectorCompat myDetector/*To call onTouchEvent()*/;
    final int[] tilesId = {R.id.t0, R.id.t1, R.id.t2, R.id.t3, R.id.t4, R.id.t5, R.id.t6,
            R.id.t7, R.id.t8, R.id.t9, R.id.t10, R.id.t11,
            R.id.t12, R.id.t13, R.id.t14, R.id.t15};
    Board board;
    MenuOptions menuOptions;
    StoreData storeData;
    boolean saved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If android 3.0+ activate hardware acceleration
        if (Build.VERSION.SDK_INT >= 11) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }

        myDetector = new GestureDetectorCompat(this, this);
        objectReferences();
        if (!saved)
            board.generateRandomTile(2);
    }

    public void objectReferences() {
        board = new Board(this);
        storeData = new StoreData(board);
        saved = storeData.readFromFile();
        board.tiles = new Tile[16];
        for (int i = 0; i < 16; ++i) {
            board.tiles[i] = new Tile();
            board.tiles[i].setTv((TextView) findViewById(tilesId[i]));
            if (saved)
                storeData.readFromFile(i);
        }
        board.score = (TextView) findViewById(R.id.score);
        board.score.setText("Score\n" + board.sum);
        menuOptions = new MenuOptions(board);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.undo:
                if (!menuOptions.undo())
                    board.warning("No available steps!");

                return true;
            case R.id.restart:
                menuOptions.restart();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        storeData.writeToFile();
        super.onPause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Detect gesture type
        myDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        menuOptions.copyDataTo(); //if backup is needed
        switch (getSlope(e1.getX(), e1.getY(), e2.getX(), e2.getY())) {
            case 1:
                board.moveUp();
                break;
            case 2:
                board.moveLeft();
                break;
            case 3:
                board.moveDown();
                break;
            case 4:
                board.moveRight();
                break;
        }
        if (board.lose()) {
            EndDialog ed = new EndDialog();
            ed.showLoseDialog(this, board, menuOptions);
        }
        board.generateRandomTile(1);
        return true;
    }

    public int getSlope(float x1, float y1, float x2, float y2) {
        //Detect swipe direction
        Double angle = Math.toDegrees(Math.atan2(y1 - y2, x2 - x1));
        if (angle > 45 && angle <= 135)// top
            return 1;
        if (angle >= 135 && angle < 180 || angle < -135 && angle > -180)// left
            return 2;
        if (angle < -45 && angle >= -135)// down
            return 3;
        if (angle > -45 && angle <= 45)// right
            return 4;
        return 0;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

}