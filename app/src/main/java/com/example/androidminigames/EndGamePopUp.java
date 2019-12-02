package com.example.androidminigames;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.concurrent.ThreadPoolExecutor;

import static android.graphics.Color.argb;

public class EndGamePopUp {
    private GridLayout gameGrid;
    private GridLayout imagesGrid;
    private GridLayout wordGrid;
    private GridLayout gameStateGrid;
    private GridLayout lettersGrid;

    private GridLayout gameGrid2;
    private GridLayout pyramidGrid;
    private GridLayout numberGrid;
    private GridLayout cleanGrid;
    private GridLayout finishGrid;

    private Context context;
    private Display display;

    public EndGamePopUp(Context context, Display display,
                        GridLayout gameGrid, GridLayout imagesGrid, GridLayout wordGrid, GridLayout gameStateGrid, GridLayout lettersGrid,
                        GridLayout gameGrid2, GridLayout pyramidGrid, GridLayout numberGrid, GridLayout cleanGrid, GridLayout finishGrid) {
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

        this.context = context;
        this.display = display;

    }

    public void displayPopUp(){
        Point size = new Point();
        display.getSize(size);
        System.out.println("@@@@@@@@@@@@\nEND GAME\n@@@@@@@@@@@@\nchilds of gameGrid: " /*+ gameGrid.getChildCount()*/);
        //gameGrid.removeAllViews();
        int padding = 300;
        TextView endingMessage = new TextView(context);
        Button goToMainMenu = new Button(context);
        int goToMainMenuW = 225, goToMainMenuH = 225;
        GridLayout contentEndGame = new GridLayout(context);
        final PopupWindow endGamePopUp = new PopupWindow(context);

        endingMessage.setText("Bravo\nvous avez réussi tous les niveaux avec succès");
        endingMessage.setTextSize(30.0f);

        goToMainMenu.setBackgroundResource(R.drawable.main_menu);
        goToMainMenu.setLayoutParams(new LinearLayout.LayoutParams(goToMainMenuW, goToMainMenuH));
        goToMainMenu.setPadding(padding * 2, 10, 0, 0);
        goToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                endGamePopUp.dismiss();
                MainMenu mainMenu = new MainMenu(context, display,
                                                 gameGrid, imagesGrid, wordGrid, gameStateGrid, lettersGrid,
                                                 gameGrid2, pyramidGrid,  numberGrid, cleanGrid, finishGrid);
            }
        });

        contentEndGame.setRowCount(2);
        contentEndGame.setColumnCount(1);
        contentEndGame.setBackgroundColor(argb(100, 51, 67, 251));
        contentEndGame.addView(endingMessage);
        contentEndGame.addView(goToMainMenu);

        endGamePopUp.setContentView(contentEndGame);
        endGamePopUp.showAtLocation((View)gameGrid, Gravity.CENTER, 0, size.y / 2);
        endGamePopUp.update();

    }
}
