package com.example.myandycrush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.widget.GridLayout;

public class MainActivity extends AppCompatActivity {
    GameUtilities game; // = new GameUtilities(GameGrid, 5, 5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridLayout GameGrid = (GridLayout) findViewById(R.id.gameGrid);
        Context context = getBaseContext();
        Display display = getWindowManager().getDefaultDisplay();
        game = new GameUtilities(GameGrid, 5, 5, context, display);

    }

}













/*******************************/
/*     Just for Debug          */
/*******************************/


/*Display display = getWindowManager().getDefaultDisplay();
        game.getScreenDimensions(display);
        System.out.println("***********\nWidth: " + game.getScreenWidth() + "\tHeight: "
                            + game.getScreenHeight() + "\n***********");*/
