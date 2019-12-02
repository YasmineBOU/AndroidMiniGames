package com.example.androidminigames;

import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.View;
import android.view.animation.GridLayoutAnimationController;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainMenu extends AppCompatActivity {

    private static String description = "Veuillez sélectionner un des jeux \t\tproposés ci-dessous";
    ImageView logicCategory;
    ImageView mathCategory;
    TextView gameDescription;


    GridLayout gameGrid;
    GridLayout imagesGrid;
    GridLayout wordGrid;
    GridLayout gameStateGrid;
    GridLayout lettersGrid;

    GridLayout gameGrid2;
    GridLayout pyramidGrid;
    GridLayout numberGrid;
    GridLayout cleanGrid;
    GridLayout finishGrid;


    Context context;
    Display display;

    public MainMenu(Context context, Display display,
                    GridLayout gameGrid, GridLayout imagesGrid, GridLayout wordGrid, GridLayout gameStateGrid, GridLayout lettersGrid,
                    GridLayout gameGrid2, GridLayout pyramidGrid, GridLayout numberGrid, GridLayout cleanGrid, GridLayout finishGrid){

        this.context = context;
        this.display = display;
        this.gameGrid = gameGrid;
        this.imagesGrid = imagesGrid;
        this.wordGrid = wordGrid;
        this.gameStateGrid = gameStateGrid;
        this.lettersGrid = lettersGrid;

        this.gameGrid2 = gameGrid2;
        this.pyramidGrid = pyramidGrid;
        this.numberGrid = numberGrid;
        this.cleanGrid = cleanGrid;
        this.finishGrid = finishGrid;

        createMenu();
    }

    public void createMenu(){

        int padding = 300;
        gameDescription = new TextView(context);
        gameDescription.setText(description);
        gameDescription.setTextColor(Color.WHITE);
        gameDescription.setTextSize(20.0f);
        gameDescription.setPadding(padding / 3, padding / 2, 0, 0);

        logicCategory = new ImageView(context);
        mathCategory = new ImageView(context);

        logicCategory.setImageResource(R.drawable.four_pics_one_word);
        logicCategory.setAdjustViewBounds(true);
        logicCategory.setMaxWidth(1000);
        logicCategory.setMaxHeight(800);
        logicCategory.setPadding(padding + 150, padding, 0, 0);

        mathCategory.setImageResource(R.drawable.additive_pyramid);
        mathCategory.setAdjustViewBounds(true);
        mathCategory.setMaxWidth(1000);
        mathCategory.setMaxHeight(800);
        mathCategory.setPadding(padding + 80, padding / 2, 0, 0);

        logicButtonListener();
        mathButtonListener();

        gameGrid.removeAllViews();
        gameGrid.addView(gameDescription, 0);
        gameGrid.addView(logicCategory, 1);
        gameGrid.addView(mathCategory, 2);
    }

    public void logicButtonListener(){
        logicCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            for(int i  = 2; i >= 0; --i)
                gameGrid.removeViewAt(i);

            gameGrid.addView(imagesGrid);
            gameGrid.addView(wordGrid);
            gameGrid.addView(gameStateGrid);
            gameGrid.addView(lettersGrid);


            FourPicsOneWord game = new FourPicsOneWord(gameGrid, imagesGrid, wordGrid, gameStateGrid, lettersGrid,
                                                       gameGrid2, pyramidGrid,  numberGrid, cleanGrid, finishGrid,
                                                       context, display);
            game.gamePlay();

            }
        });
    }

    public void mathButtonListener(){
        mathCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("NUMBER OF CHILDREN: " + gameGrid.getChildCount());
                for(int i  = 2; i >+ 0; --i)
                    gameGrid.removeViewAt(i);

                System.out.println("NUMBER OF CHILDREN: " + gameGrid2.getChildCount());
                gameGrid.addView(gameGrid2);
                //gameGrid2.addView(pyramidGrid);

                PyramidGame game = new PyramidGame(context, display,
                                                   gameGrid2, pyramidGrid, numberGrid, cleanGrid, finishGrid,
                                                   gameGrid, imagesGrid, wordGrid, gameStateGrid, lettersGrid);

            }
        });
    }
}
