package com.example.myandycrush;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class GameUtilities  extends AppCompatActivity {
    private GridLayout gameGrid;
    private int gridWidth;
    private int gridHeight;
    private int screenWidth;
    private int screenHeight;
    private ArrayList<String> candyNames = new ArrayList<String>();
    private ArrayList<String> bonusNames = new ArrayList<String>();

    public GameUtilities(GridLayout gameGrid, int width, int height, Context context, Display display){
        this.gameGrid = gameGrid; //(GridLayout)findViewById(R.id.gameGrid);
        this.gridWidth = width;
        this.gridHeight = height;
        getScreenDimensions(display);
        this.createGrid(context);
        Collections.addAll(this.candyNames, "green_candy", "orange_candy",
                           "purple_candy", "red_candy", "yellow_candy");
        Collections.addAll(this.bonusNames, "red_bonus_candy");
    }

    public void getScreenDimensions(Display disp){
        Display display = disp; //getWindowManager().getDefaultDisplay();
        Point dimensions = new Point();
        display.getSize(dimensions);
        this.screenWidth = dimensions.x;
        this.screenHeight = dimensions.y;
    }

    public int getScreenHeight(){
        return screenHeight;
    }

    public int getScreenWidth(){
        return screenWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public GridLayout getGameGrid() {
        return gameGrid;
    }

    public int getGridSize(){
        return gridWidth * gridHeight;
    }

    public int getEuclidPGCD(int a, int b){
        int max = a > b ? a : b;
        int min = a < b ? a : b;
        a = max; b = min;
        while(b != 0){
            int temp = b;
            b = a % b;
            a = temp;
        }
        System.out.println("PGCD: " + a);
        return a;
    }

    public void createGrid(Context context){

        gameGrid.setRowCount(this.gridHeight);
        gameGrid.setColumnCount(this.gridWidth);

        int cellWidth = getEuclidPGCD(screenWidth, gridWidth);
        int cellHeight = getEuclidPGCD(screenHeight, gridHeight);
        int size = gridWidth * gridHeight;

        int nbCandies = 3 + new Random().nextInt(candyNames.size());   //Math.random() * (candyNames.size() - )

        System.out.println("cellWidth: " + cellWidth + "\tcellHeight: " + cellHeight + "\tsize: " + size);
        for(int i  = 0; i < size; ++i){
            //System.out.println("In the loop: i:  " + i);
            ImageView image = new ImageView(context);
            int drawableId = context.getResources().getIdentifier("red_candy", "drawable", context.getPackageName());
            image.setImageResource(drawableId);
            image.setAdjustViewBounds(true);
            image.setMaxHeight(cellWidth * size);
            image.setMaxWidth(cellHeight * size);

            gameGrid.addView(image, i);
        }
    }

}
