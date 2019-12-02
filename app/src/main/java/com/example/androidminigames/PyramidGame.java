package com.example.androidminigames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class PyramidGame extends AppCompatActivity {

    private ArrayList<String> levels = new ArrayList<>();
    private ArrayList<String> levelsSolutions = new ArrayList<>();

    private Context context;
    private Display display;

    private GridLayout gameGrid2;
    private GridLayout pyramidGrid;
    private GridLayout numberGrid;
    private GridLayout cleanGrid;
    private GridLayout finishGrid;

    GridLayout gameGrid;
    GridLayout imagesGrid;
    GridLayout wordGrid;
    GridLayout gameStateGrid;
    GridLayout lettersGrid;

    private int indexPyramidGrid;
    private int indexLevel = 0;
    private int numberOfCells;


    // Constructor
    public PyramidGame(Context context, Display display,
                       GridLayout gameGrid2, GridLayout pyramidGrid, GridLayout numberGrid, GridLayout cleanGrid, GridLayout finishGrid,
                       GridLayout gameGrid, GridLayout imagesGrid, GridLayout wordGrid, GridLayout gameStateGrid,GridLayout lettersGrid){


        Collections.addAll(levels,
                "216", "0", "110", "54", "0", "58", "12", "42", "10", "0",
                "0", "219", "0", "0", "105", "0", "0", "47", "0", "165"/*,
                        "0", "1703", "0", "0", "0", "271", "0", "620", "0", "57",
                        "0", "0", "0", "336", "95", "0", "0", "159", "0", "21",
                        "6", "124", "0", "0", "0", "0", "963", "0", "257", "0",
                        "36", "0", "83", "241", "0", "0", "0", "0", "0", "684",
                        "64", "0", "0", "0", "4", "0", "0", "0", "2", "1",
                        "0", "24", "0", "0", "0", "9", "2", "0", "0", "0",
                        "8232", "0", "294", "2", "0", "21", "0", "2", "0", "0"*/);
        Collections.addAll(levelsSolutions,
                "216", "106", "110", "54", "52", "58", "12", "42", "10", "48",
                "547", "219", "328", "114", "105", "223", "67", "47", "58", "165"/*,
                        "2808", "1703", "1105", "869", "834", "271", "249", "620", "214", "57",
                        "189", "241", "52", "336", "95", "43", "495", "159", "64", "21",
                        "6", "124", "118", "415", "291", "173", "963", "548", "257", "84",
                        "36", "119", "83", "241", "122", "39", "1084", "846", "724", "684",
                        "64", "8", "8", "2", "4", "2", "1", "2", "2", "1",
                        "1296", "24", "54", "4", "6", "9", "2", "2", "3", "3",
                        "8232", "28", "294", "2", "14", "21", "1", "2", "7", "3"*/);

        this.context = context;
        this.gameGrid2 = gameGrid2;
        this.pyramidGrid = pyramidGrid;
        this.cleanGrid = cleanGrid;
        this.numberGrid = numberGrid;
        this.finishGrid = finishGrid;


        this.gameGrid = gameGrid;
        this.imagesGrid = imagesGrid;
        this.wordGrid = wordGrid;
        this.gameStateGrid = gameStateGrid;
        this.lettersGrid = lettersGrid;

        this.display = display;
        numberOfCells = pyramidGrid.getChildCount();

        initPyramid();
    }

    // Pyramid initialization
    public void initPyramid() {
        int startIndexLevel = indexLevel * numberOfCells;
        for(int i = startIndexLevel; i < (startIndexLevel + numberOfCells); i++) {

            String getLevels = levels.get(i);

            if(getLevels.equals("0")) {
                System.out.println("88888888888888888\nSET ALL TO ZERO\n888888888888888888");
                Button pyramidButton =  ((Button) pyramidGrid.getChildAt(i % numberOfCells));
                pyramidButton.setText("");
                pyramidButton.setBackgroundResource(android.R.drawable.btn_default);

            }

            else {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!! INITPYRAMID: " + (i % numberOfCells) + " !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                ((Button) pyramidGrid.getChildAt(i % numberOfCells)).setText(getLevels);
                ((Button) pyramidGrid.getChildAt(i % numberOfCells)).setBackgroundColor(Color.argb(255, 40, 116, 166));
            }
        }

        setPyramidCellsListeners();
        setNumbersListeners();
        cleanListener();
    }

    // Operation of the pyramid buttons
    public void setPyramidCellsListeners() {
        int startIndexLevel = indexLevel * numberOfCells;
        for(int i = startIndexLevel; i < (startIndexLevel + numberOfCells); i++) {

            Button emptyButton = (Button) pyramidGrid.getChildAt(i % numberOfCells);
            String getLevels = levels.get(i);

            System.out.println("index to 0: " + i % numberOfCells + "\ttext: " + emptyButton.getText());

            if(getLevels.equals("0")) {
                final int indexI = i % numberOfCells;
                emptyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        indexPyramidGrid = indexI;

                        System.out.println("!!!!!!!!!!!!!!!!! indexPyramidGrid " + indexPyramidGrid + " !!!!!!!!!!!!!!!!!!!!!!!!!!");
                    }
                });
            }
        }
    }

    // Operation of the number buttons to fill in the select box in the pyramid
    public void setNumbersListeners() {
        for(int i = 0; i < numberGrid.getChildCount(); i++) {
            Button numbers = (Button) numberGrid.getChildAt(i);
            numbers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Button btn = (Button) v;
                    char buttonContent = btn.getText().charAt(0);
                    Button pyramidButton = (Button) pyramidGrid.getChildAt(indexPyramidGrid);
                    String pyramidContent = (String) pyramidButton.getText();

                    pyramidContent += buttonContent;
                    pyramidButton.setText(pyramidContent);
                    levels.set(indexLevel * numberOfCells + indexPyramidGrid, pyramidContent);

                    if(!winLevel()){
                        modifyButtonStyle();
                        System.out.println("!!!!!!!!!!!!!!!! NOT FINISH !!!!!!!!!!!!!!!!");
                    }
                    else {
                        modifyButtonStyle();
                        System.out.println("!!!!!!!!!!!!!!!! FINISH LEVEL !!!!!!!!!!!!!!!!!!!!");
                        finishLevel();
                    }

                    System.out.println("in setNumberListeners:\tlevels[] = " + levels.get(indexPyramidGrid));
                }
            });
        }
    }

    // Operation of the delete button to delete the content of the selected box
    public void cleanListener() {
        for(int i = 0; i < cleanGrid.getChildCount(); i++) {
            Button cleanButton = (Button) cleanGrid.getChildAt(i);
            cleanButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button pyramidButton = (Button) pyramidGrid.getChildAt(indexPyramidGrid);
                    pyramidButton.setText("");
                    levels.set(indexPyramidGrid, "0");
                }
            });
        }
    }

    public void modifyButtonStyle(){
        Button cell = (Button) pyramidGrid.getChildAt(indexPyramidGrid);
        cell.setClickable(false);
        int solutionIndex =  indexLevel * numberOfCells + indexPyramidGrid;
        System.out.println("\n\n\n#####################\nIn modifyStyle()\nlevels.get(i): |" + levels.get(indexPyramidGrid) + "|\tlevelsSolutions.get(i): |" + levelsSolutions.get(solutionIndex) + "|\n#####################\n\n");
        System.out.println("!!!!!!!!! solutionIndex: " + solutionIndex + " !!!!!!!!!!!!!!");
        if(cell.getText().equals(levelsSolutions.get(solutionIndex))){
            System.out.println("TRUE:::::::::::::::::::::::::::");

            cell.setBackgroundColor(Color.argb(255, 40, 116, 166));
        }
        else{
            System.out.println("FALSE:::::::::::::::::::::::::::");
            cell.setBackgroundColor(Color.argb(255, 255, 255, 255));
        }

    }

    public boolean winLevel(){
        System.out.println("levels: " +  levels);
        int startIndexLevel = indexLevel * numberOfCells;
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!! in WINLEVEL !!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("startIndexLevel: " + startIndexLevel + " indexLevel: " + indexLevel);
        System.out.println("indexLevel: " + indexLevel);

        for(int i = startIndexLevel; i < (startIndexLevel + numberOfCells); i++){
            System.out.println("i: " + i + " startIndexLevel + numberOfCells: " + (startIndexLevel + numberOfCells));
            if(!levels.get(i).equals(levelsSolutions.get(i))){
                System.out.println("WinLevel\ti: " + i + "\tlevels.get(i): |" + levels.get(i) + "|\tlevelsSolutions.get(i): |" + levelsSolutions.get(i) + "|");
                System.out.println("!levels.get(i).equals(levelSolutions.get(i): " + !levels.get(i).equals(levelsSolutions.get(i)));
                System.out.println("WINLEVEL:\tFALSE:::::::::::::::::::::::::::");
                return false;
            }
        }
        System.out.println("WinLevel:\tTRUE:::::::::::::::::::::::::::");
        return true;
    }

    public void finishLevel() {
        finishGrid.setColumnCount(1);
        finishGrid.setRowCount(2);

        TextView message = new TextView(context);
        message.setText("BRAVO ! Vous avez complété ce niveau.");
        message.setTextColor(Color.GREEN);
        message.setPadding(200, 0, 0, 0);
        finishGrid.addView(message);

        Button nextButton = new Button(context);
        nextButton.setText("Suivant");
        /*nextButton.setTextColor(Color.BLACK);
        nextButton.setMaxHeight(100);
        nextButton.setMaxWidth(100);
        nextButton.setPadding(500, 30,0, 0 );*/
        finishGrid.addView(nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexLevel += 1;
                if(indexLevel >= (levels.size() / numberOfCells)) {
                    EndGamePopUp endGamePopUp = new EndGamePopUp(context,  display,
                                                                 gameGrid, imagesGrid, wordGrid, gameStateGrid, lettersGrid,
                                                                 gameGrid2, pyramidGrid,  numberGrid, cleanGrid, finishGrid);

                    endGamePopUp.displayPopUp();
                    System.out.println("!!!!!!!!!!!!!!!! YOU HAVE FINISH ALL LEVEL !!!!!!!!!!!!!!!!!!");
                }
                else {
                    finishGrid.removeAllViews();
                    indexPyramidGrid = 0;
                    initPyramid();
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!! YOU CLICK FOR NEXT LEVEL !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                }
            }
        });

        System.out.println("BRAVO-99999999999999999999999999999");
    }
}
