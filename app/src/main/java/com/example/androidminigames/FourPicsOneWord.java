package com.example.androidminigames;


import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static android.graphics.Color.*;

public class FourPicsOneWord  extends AppCompatActivity {
    private GridLayout gameGrid;
    private GridLayout imagesGrid;
    private GridLayout wordGrid;
    private GridLayout gameStateGrid;
    private GridLayout lettersGrid;

    private GridLayout gameGrid2;
    GridLayout pyramidGrid;
    GridLayout numberGrid;
    GridLayout cleanGrid;
    GridLayout finishGrid;

    private int gridWidth;
    private int gridHeight;
    private int screenWidth;
    private int screenHeight;
    private ArrayList<String> wordsList = new ArrayList<>();
    private ArrayList<String> imagesList = new ArrayList<>();
    Context context;
    Display display;
    private int currentWordIndex;
    private int nbImages;
    private ArrayList<Character> userWord = new ArrayList<>();
    private ArrayList<Character> lettersList = new ArrayList<>(12);
    private int nbMaxLetters;
    private boolean makeImageBigger = true;
    private boolean endGame = false;

    public FourPicsOneWord(GridLayout gameGrid, GridLayout imagesGrid, GridLayout wordGrid, GridLayout gameStateGrid,GridLayout lettersGrid,
                           GridLayout gameGrid2, GridLayout pyramidGrid, GridLayout numberGrid, GridLayout cleanGrid, GridLayout finishGrid,
                           Context context, Display display){
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

        this.currentWordIndex = 0;
        this.nbImages = 4;
        this.nbMaxLetters = 12;

        Collections.addAll(this.wordsList, "matin"/*, "transport", "monument", "felin", "jouer", "friandises", "menage", "mediterranee"*/);

        generateImageList();
        getScreenDimensions();

    }

    void generateImageList(){
        for(int i = 0; i < wordsList.size(); ++i){
            for(int j = 0; j < nbImages; ++j){
                imagesList.add(wordsList.get(i) + j);
            }
        }
        System.out.println("\nimageList:\n" + imagesList);
    }

    public void getScreenDimensions(){
        Point dimensions = new Point();
        display.getSize(dimensions);
        this.screenWidth = dimensions.x;
        this.screenHeight = dimensions.y;
    }


    public void createWordGrid(){
        wordGrid.removeAllViews();
        int size = wordsList.get(currentWordIndex).length();
        int buttonSize = 200;
        for(int i  = 0; i < size; ++i){
            Button letterButton = new Button(context);
            letterButton.setLayoutParams(new LinearLayout.LayoutParams(buttonSize , buttonSize));
            letterButton.setText(" ");
            letterButton.setTag("word" + i);

            letterButton.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
            letterButton.setTextSize(20.0f);


            final int index = i;
            letterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;
                    CharSequence  buttonContent = b.getText();
                    char letter = buttonContent.charAt(0);
                    if(letter != ' ') {
                        int letterIndex = putLetterInEmptyPlace(letter);
                        ((Button) lettersGrid.getChildAt(letterIndex)).setText(buttonContent);
                        b.setText(" ");
                        userWord.set(index, ' ');

                        System.out.println("After putting back the letter: buttonContent: |" + letter + "| to the index " + letterIndex +  " of lettersGrid");
                    }
                }
            });

            wordGrid.addView(letterButton, i);
        }
    }


    public void createLettersGrid(){
        lettersGrid.removeAllViews();
        int buttonSize = 225;

        for(int i = 0; i < nbMaxLetters; ++i){
            Button letterButton = new Button(context);
            letterButton.setTag("letter" + i);
            letterButton.setText(lettersList.get(i).toString());
            letterButton.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
            letterButton.setTextSize(30.0f);
            letterButton.setElevation(50.0f);
            letterButton.setShadowLayer(90, 10, 10, GRAY);
            final int index = i;
            letterButton.setLayoutParams(new LinearLayout.LayoutParams(buttonSize , buttonSize));
            letterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;
                    CharSequence buttonContent = b.getText();
                    char letter = buttonContent.charAt(0);
                    if(letter != ' ') {
                        int wordIndex = getIndexFirstBlankUserWord();
                        if (wordIndex > -1) {
                            userWord.set(wordIndex, letter);
                            ((Button) wordGrid.getChildAt(wordIndex)).setText(buttonContent);
                            lettersList.set(index, ' ');
                            b.setText(" ");
                            System.out.println("clicked on " + buttonContent + " letter\tuserWord: " + userWord);
                            if(wordComplete()) {
                                System.out.println("222222222222222222222222222222222222222222 WORD COMPLETE 2222222222222222222222222222222222");
                                boolean winGame = checkGameStatus();
                                if(winGame)
                                    switchLevel();
                            }
                        }
                    }
                }
            });

            lettersGrid.addView(letterButton, i);
        }
    }


    public void updateImageGrid(int width, int height){
        imagesGrid.removeAllViews();
        imagesGrid.setRowCount(width);
        imagesGrid.setColumnCount(height);
        imagesGrid.getPaddingLeft();
        int paddingSize = 25;
        for(int i = 0; i < nbImages; ++i){
            ImageView image = new ImageView(context);
            int drawableId = context.getResources().getIdentifier(imagesList.get(currentWordIndex * 4 + i), "drawable", context.getPackageName());
            //image.setImageResource(drawableId);
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
            roundedBitmapDrawable.setCornerRadius(50.0f);
            image.setImageDrawable(roundedBitmapDrawable);
            image.setAdjustViewBounds(true);
            image.setMaxWidth(500 + paddingSize);
            image.setMaxHeight(400 + paddingSize);
            image.setPadding(paddingSize, paddingSize, paddingSize, paddingSize);

            final int w = width, h = height;
            image.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v){
                    if(!endGame) {
                        if (makeImageBigger) {
                            ImageView img = (ImageView) v;
                            gridWidth = imagesGrid.getWidth();
                            gridHeight = imagesGrid.getHeight();

                            img.setAdjustViewBounds(true);
                            img.setMaxWidth(gridWidth);
                            img.setMaxHeight(gridHeight);
                            imagesGrid.removeAllViews();
                            imagesGrid.addView(img, 0);
                            makeImageBigger = false;
                        } else {
                            makeImageBigger = true;
                            updateImageGrid(w, h);
                        }
                    }
                }

            });
            imagesGrid.addView(image, i);
            System.out.println(imagesList.get(currentWordIndex * 4 + i) + " added at the position: " + i);
        }
    }

    public void switchLevel(){
        System.out.println("Switch level");
        gameStateGrid.removeAllViews();

        TextView message = new TextView(context);
        message.setText("CORRECT");
        message.setTextSize(30.0f);
        message.setTextColor(GREEN);
        Button continueButton = new Button(context);
        continueButton.setText("Continuer");

        final ObjectAnimator colorAnim = ObjectAnimator.ofInt(continueButton, "backgroundColor", GREEN, WHITE);
        colorAnim.setDuration(300); //duration of flash
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.start();

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                ++currentWordIndex;
                if(currentWordIndex >= wordsList.size()) {
                    endGame = true;
                    endGame();
                }
                else
                    gamePlay();
            }
        });


        gameStateGrid.addView(message);
        gameStateGrid.addView(continueButton);
    }

    public void endGame(){
        EndGamePopUp endGamePopUp = new EndGamePopUp(context, display,
                                                     gameGrid, imagesGrid, wordGrid, gameStateGrid, lettersGrid,
                                                     gameGrid2, pyramidGrid,  numberGrid, cleanGrid, finishGrid);
        endGamePopUp.displayPopUp();
    }


    public void gamePlay(){
        if(currentWordIndex < wordsList.size()) {
            fillUserWordWithBlank();
            fillLettersList();
            updateImageGrid(2, 2);
            createWordGrid();
            displayLevel();
            createLettersGrid();
        }
    }

    public void displayLevel(){
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@\nDISPLAY THE LEVEL\n@@@@@@@@@@@@@@@@@@@@@@@\n");
        gameStateGrid.removeAllViews();
        TextView numLevel = new TextView(context);
        numLevel.setText("Niveau "  + (currentWordIndex + 1));
        numLevel.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        numLevel.setTextSize(50.0f);
        numLevel.setTextColor(Color.RED);
        numLevel.setShadowLayer(100, 1.0f, 1.0f, Color.WHITE);
        gameStateGrid.addView(numLevel);
    }

    public boolean wordComplete(){
        for(int i = 0; i < userWord.size(); ++i){
            if(userWord.get(i) == ' ')
                return false;
        }
        return true;
    }

    public void fillLettersList(){
        lettersList.clear();
        String currentWord = wordsList.get(currentWordIndex);
        int currentWordLength = currentWord.length();
        int i = 0;
        for(; i < currentWordLength; ++i){
            lettersList.add(i, Character.toUpperCase(currentWord.charAt(i)));
        }
        for(; i < nbMaxLetters; ++i){
            int numLetter = new Random().nextInt(26);
            lettersList.add(i, (char)Character.toUpperCase('A' + numLetter));
        }
        Collections.shuffle(lettersList);
        System.out.println("LettersList: \n" + lettersList);
    }

    public void fillUserWordWithBlank(){
        userWord.clear();
        if(currentWordIndex < wordsList.size()) {
            String currentWord = wordsList.get(currentWordIndex);
            int currentWordLength = currentWord.length();
            for (int i = 0; i < currentWordLength; ++i) {
                userWord.add(' ');
            }
        }
    }

    public int getIndexFirstBlankUserWord(){
        int index = 0;
        for(; index < userWord.size() && userWord.get(index) != ' '; ++index);
        System.out.println("FIRST BLANK AT " + index);
        return index == userWord.size() ? -1 : index;
    }

    public int putLetterInEmptyPlace(Character letter){
        int i  = 0;
        System.out.println("lettersList:\n " +  lettersList);
        for(; i < lettersList.size(); ++i){
            if(lettersList.get(i) == ' ') {
                lettersList.set(i, letter);
                break;
            }
        }
        return i;
    }

    public boolean checkGameStatus(){
        boolean wordFilled = true;
        String userWholeWord = "";
        for(int i = 0; i < userWord.size(); ++i){
            Character currentLetter = userWord.get(i) ;
            userWholeWord += currentLetter;
            if(currentLetter == ' ') {
                wordFilled = false;
                break;
            }
        }

        for(int i = 0; i < wordGrid.getChildCount(); ++i){
            Button word = (Button) wordGrid.getChildAt(i);
            word.setTextColor(BLACK);
        }
        System.out.println("in checkGameStatus:\t wordFilled: " + wordFilled);
        System.out.println("wholeWord: |"+ userWholeWord + "|\tuserWord: |" + wordsList.get(currentWordIndex) + "|");
        if(wordFilled){
            if(userWholeWord.equals(wordsList.get(currentWordIndex).toUpperCase())) {
                System.out.println("Bravo vous avez deviné le bon mot !!!");
                for(int i = 0; i < wordGrid.getChildCount(); ++i){
                    Button word = (Button) wordGrid.getChildAt(i);
                    word.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
                }
                for (int i = 0; i < wordGrid.getChildCount(); ++i) {
                    Button word = (Button) wordGrid.getChildAt(i);
                    final ObjectAnimator colorAnim = ObjectAnimator.ofInt(word, "textColor", BLACK, GREEN); //you can change colors
                    colorAnim.setDuration(75); //duration of flash
                    colorAnim.setEvaluator(new ArgbEvaluator());
                    colorAnim.setRepeatMode(ValueAnimator.REVERSE);
                    colorAnim.setRepeatCount(15);
                    colorAnim.start();
                }
                return true;
            }
            else {
                for (int i = 0; i < wordGrid.getChildCount(); ++i) {
                    Button word = (Button) wordGrid.getChildAt(i);
                    final ObjectAnimator colorAnim = ObjectAnimator.ofInt(word, "textColor", RED, BLACK); //you can change colors
                    colorAnim.setDuration(60); //duration of flash
                    colorAnim.setEvaluator(new ArgbEvaluator());
                    colorAnim.setRepeatMode(ValueAnimator.REVERSE);
                    colorAnim.setRepeatCount(10);
                    colorAnim.start();
                }
            }
        }
        return false;
    }

}
