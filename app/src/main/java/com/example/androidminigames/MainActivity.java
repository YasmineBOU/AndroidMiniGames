package com.example.androidminigames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    // GameUtilities game; // = new GameUtilities(GameGrid, 5, 5);
    //FourPicsOneWord game;
    MainMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout gameGrid = (GridLayout) findViewById(R.id.gameGrid);
        GridLayout imagesGrid = (GridLayout) findViewById(R.id.fourImages);
        GridLayout wordGrid = (GridLayout) findViewById(R.id.word);
        GridLayout gameStateGrid = (GridLayout) findViewById(R.id.gameState);
        GridLayout lettersGrid = (GridLayout) findViewById(R.id.letters);


        LayoutInflater inflater = getLayoutInflater();
        View pyramidView = (View) inflater.inflate(R.layout.pyramid, null);

        GridLayout gameGrid2 = (GridLayout) pyramidView.findViewById(R.id.gameGrid2);
        GridLayout pyramidGrid = (GridLayout) pyramidView.findViewById(R.id.pyramidGrid);
        GridLayout numberGrid = (GridLayout) pyramidView.findViewById(R.id.numberGrid);
        GridLayout cleanGrid = (GridLayout) pyramidView.findViewById(R.id.cleanGrid);
        GridLayout finishGrid = (GridLayout) pyramidView.findViewById(R.id.finishGrid);

       System.out.println("MAIN_ACTIVITY: " + pyramidGrid.getChildCount());

        Context context = getBaseContext();
        Display display = getWindowManager().getDefaultDisplay();
        menu = new MainMenu(context, display,
                            gameGrid, imagesGrid, wordGrid, gameStateGrid, lettersGrid,
                            gameGrid2, pyramidGrid,  numberGrid, cleanGrid, finishGrid);
    }
}

