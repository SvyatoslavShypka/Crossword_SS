package Crossword_SS;

import java.util.ArrayList;
import java.util.List;

public class Crossword_SS {
/*
Crossword
*/
        public static void main(String[] args) {
            int[][] crossword = new int[][]{
                    {'f', 'd', 'e', 'r', 'l', 'k'},
                    {'u', 's', 'a', 'm', 'e', 'o'},
                    {'l', 'n', 'g', 'r', 'o', 'v'},
                    {'m', 'l', 'p', 'r', 'r', 'h'},
                    {'p', 'o', 'e', 'e', 'j', 'j'}
            };
// We are looking some words and print result
            detectAllWords(crossword, "home", "same");
/*
Expected result:
home - (5, 3) - (2, 0)
same - (1, 1) - (4, 1)
*/
        }
        public static List<Word> detectAllWords(int[][] crossword, String... words) { //looking for words
            List<Word> l = new ArrayList<>(); // There will be a list of found words
            int matrixXmax = crossword[0].length - 1; // Maximum X index of matrix. Minimum is zero
            int matrixYmax = crossword.length - 1; // Maximum Y index of matrix. Minimum is zero
            for (int i = 0; i < words.length; i++) { //Loop for every searched words
                char[] ch = words[i].toCharArray(); //All characters put in array of chars
                int lenWord = ch.length; //Check quantity of characters
                for (int y = 0; y <= matrixYmax; y++) { //Looking first character by Y positions
                    for (int x = 0; x <= matrixXmax; x++) { //Looking first character by X positions
                        if (crossword[y][x] == ch[0]) { //If found first character
                            for (int offsetY = -1; offsetY <= 1; offsetY++) { //Offset by Y positions (-1, 0, +1). It uses for direction of looking
                                for (int offsetX = -1; offsetX <= 1; offsetX++) { //Offset by X positions (-1, 0, +1). It uses for direction of looking
                                    if (!(offsetX == 0 && offsetY == 0)) { //offsets zeros are skipped
                                        int endX = x + offsetX * (lenWord - 1); //Possible end position by X for this direction
                                        int endY = y + offsetY * (lenWord - 1); //Possible end position by Y for this direction
                                        if (endX >= 0 && endX <= matrixXmax) { //Check if it is within range by X (0-matrixXmax)
                                            if (endY >= 0 && endY <= matrixYmax) { //Check if it is within range by Y (0-matrixYmax)
                                                int countOk = 1; //Count found characters /first is already done/
                                                int tempendX = x; //temporary X position of next character
                                                int tempendY = y; //temporary Y position of next character
                                                for (int m = 1; m < lenWord; m++) { //Loop for all characters started from second one
                                                    tempendX += offsetX; //X position of the character in this direction
                                                    tempendY += offsetY; //Y position of the character in this direction
                                                    if (crossword[tempendY][tempendX] == ch[m]) { //if found next character in this direction
                                                        countOk++; //count quantity of found characters
                                                    }
                                                    if (countOk == lenWord) { //if all characters were found
                                                        l.add(new Word(words[i])); //add to array found word
                                                        l.get(l.size() - 1).setStartPoint(x, y); //set start position of found word (X, Y)
                                                        l.get(l.size() - 1).setEndPoint(tempendX, tempendY); //set finish position of found word (X, Y)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println(l); // Print results
            return l; //return (for this program it isn't needed)
        }
        public static class Word { //class Word: name and positions
            private String text; //name of word
            private int startX; //start X position of word
            private int startY; //start Y position of word
            private int endX; //finish X position of word
            private int endY; //finish Y position of word

            public Word(String text) {
                this.text = text;
            } //constructor by name

            public void setStartPoint(int i, int j) { //setter of start position
                startX = i;
                startY = j;
            }

            public void setEndPoint(int i, int j) { //setter of finish position
                endX = i;
                endY = j;
            }

            @Override
            public String toString() { //override printed form of Word
                return String.format("%s - (%d, %d) - (%d, %d)", text, startX, startY, endX, endY);
            }
        }
}