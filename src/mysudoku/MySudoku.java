/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysudoku;

import java.util.Arrays;
import java.io.*;

/**
 *
 * @author Rani
 */
public class MySudoku {

    /**
     * @param args the command line arguments
     */
    static int PlaceNumber;
    static int InitialArray[][] = {
        {0, 4, 0, 0, 5, 2, 0, 7, 0},
        {5, 6, 0, 0, 9, 3, 0, 0, 0},
        {1, 0, 2, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 1, 0},
        {0, 0, 8, 9, 4, 7, 5, 0, 0},
        {0, 5, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 1, 0, 8},
        {0, 0, 0, 5, 7, 0, 0, 3, 4},
        {0, 3, 0, 8, 6, 0, 0, 9, 0}};

    static int FilledArray[][] = new int[9][9];
    static int counter = 0, flag = 0, NumberOfBlankSpace = -1, bb = 0;
    static boolean vertical_horizontal = true;
    static int newRow, newColumn, j, horizontal, vertical;
    static boolean box = true;
    static int LocationArray[][] = new int[81][3];
    static int BackTrackMonitor = 0, ProgramEnd = 0, i;
    static int ImNotSure;
    static int FromBack[] = new int[2];

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        for (int y = 0; y <= 8; y++) {
            for (j = 0; j <= 8; j++) {
                FilledArray[y][j] = InitialArray[y][j];
            }
        }

        //  IsPossible(2,1);
        CallingIsPossible();
        //   FindEmpty();
        Print();
        // System.out.println(" "+BackTrackMonitor);
    }

    static void IsPossible(int row, int column) {

        /**
         * **********checking Vertical And horizontal***
         */
        for (PlaceNumber = 1; PlaceNumber <= 9; PlaceNumber++) {
            System.out.println("In IsPossible or location return by backtrack, row#  " + row + "  Column " + column + "  place number  " + PlaceNumber);
            //  System.out.println(PlaceNumber);
            box = true;
            vertical_horizontal = true;
            flag = 0;
            counter = 0;
            for (i = 0; i <= 8; i++) {

                if ((FilledArray[i][column] == PlaceNumber || FilledArray[row][i] == PlaceNumber)) {
                    counter = 0;
                    //    System.out.println("place"+PlaceNumber);
                    break;
                } else if (FilledArray[i][column] != PlaceNumber && FilledArray[row][i] != PlaceNumber) {
                    counter++;
                    //  System.out.println(" not found"+PlaceNumber);
                }
            }
            if (counter == 9) {
                vertical_horizontal = false;   //means that number can be placed if 3*3 is false

                newRow = (row / 3) * 3;

                newColumn = (column / 3) * 3;
                for (int k = 0; k <= 2; k++) {
                    for (j = 0; j <= 2; j++) {
                        if (FilledArray[newRow][newColumn + j] == PlaceNumber) {
                            flag = 0;
                            //  System.out.println("flag 0 breaking out box  "+PlaceNumber);
                            break;
                        } else if (FilledArray[newRow][newColumn + j] != PlaceNumber) {
                            flag++;
                        }

                    }
                    newRow++;

                }
                if (flag == 9) {
                    box = false;
                    FilledArray[row][column] = PlaceNumber;
                    System.out.println("Just filled one position [" + row + "][" + column + "] with number    " + PlaceNumber);  //added by Achachan
                    BackTrackMonitor++;
                    System.out.println("BackTrackMonitor = " + BackTrackMonitor);
                    break;
                }
            }
            if ((vertical_horizontal == true || box == true) && PlaceNumber == 9) {
                //   FromBack[]=BackTrack(row,column);
                column = BackTrack(row, column);
                vertical = column;
                horizontal = ImNotSure;
                row = horizontal;
                System.out.println("Just came out of backtrack Horizontal= " + horizontal + " Vertical=  " + vertical + " Place Number=  " + PlaceNumber);
            }

            /**
             * *******Checking 3*3 BOX*********************
             */
       //***********************************************
        }

    }

    //*********************Printing array*********
    static void Print() {
        for (i = 0; i <= 8; i++) {
            for (j = 0; j <= 8; j++) {
                System.out.print(" " + FilledArray[i][j]);
                if (j == 2 || j == 5) {
                    System.out.print("  ");
                }
                if (j == 8) {
                    System.out.println("");
                }
                if (j == 8 && (i == 2 || i == 5)) {
                    System.out.println("");
                }
            }
        }
    }
    //*********************************************

    //****************BACKTRACK******************************
    static int BackTrack(int Horizontal, int Vertical) {
        //int FromBackTrack[]=new int[2];
        System.out.println("came into backtrack  horizontal= " + Horizontal + " Vertical " + Vertical);
        int JustForFun = 0;//added by achahan
        if (Vertical == 0 && Horizontal == 0) {
            PlaceNumber = FilledArray[0][0];
        } else if (Vertical == 0) {
            Vertical = 8;
            Horizontal--;

            System.out.println("Oops wrong place");
            System.out.println(" horizontal= " + Horizontal + " Vertical " + Vertical);
        }
        int y = Vertical;
        for (i = y; i >= 0; i--) {
            if (Vertical == 0 && Horizontal != 0) {
                Vertical = 8;
                Horizontal--;
            } else {
                Vertical--;
            }
            JustForFun++;//added by achahan
            if (InitialArray[Horizontal][Vertical] == 0) {
                PlaceNumber = FilledArray[Horizontal][Vertical];
                // FilledArray[Horizontal][Vertical]=0;//7/26
                if (Vertical == 0 && Horizontal == 0) {
                    break;
              //Vertical=9;
                    // Horizontal--;

                }

                if (Vertical == 0 && PlaceNumber == 9) {
                    i = 9;
                    Vertical = 8;
                    Horizontal--;
                    BackTrack(Horizontal, Vertical);

                }
                if (PlaceNumber == 9) {
                    //  Vertical--;
                    System.out.println(" In back track when place number=9" + " horizontal= " + Horizontal + " vertical= " + Vertical);
                    FilledArray[Horizontal][Vertical] = 0;
                    BackTrack(Horizontal, Vertical);

                }
                FilledArray[Horizontal][Vertical] = 0;
             //  System.out.println(" horizontal=  "+Horizontal+" Vertical =  "+Vertical+" i="+i );
                //  System.out.println("just for fun loop number=  "+JustForFun);
                break;
            }

        }
       // FromBackTrack[0]=Horizontal;
        // FromBackTrack[1]=Vertical;
        System.out.println(" from BackTrack   b4 goes back to Is posible..Horizontal " + Horizontal + " Vertical= " + Vertical + " PlaceNumber= " + PlaceNumber);
        ImNotSure = Horizontal;
      //  return (FromBackTrack);

        return (Vertical);
      //  System.out.println("NOT OUT OFbACKtRACK");

    }
    //******************************************************

    //***********************FINDING EMPTY SPACES******************
    static void FindEmpty() {

        int p = 0;
        for (int Row = 0; Row <= 8; Row++) {
            for (int Column = 0; Column <= 8; Column++) {

                if (InitialArray[Row][Column] == 0) {
                    NumberOfBlankSpace++;

                    for (j = 0; j <= 2; j++) {
                        LocationArray[p][1] = Row;
                        LocationArray[p][2] = Column;
                        LocationArray[p][0] = NumberOfBlankSpace;

                    }
                    p++;
                }
            }
        }

        for (int Row = 0; Row <= 80; Row++) {
            for (int Column = 0; Column <= 2; Column++) {
                System.out.print(" " + LocationArray[Row][Column]);
                if (Column == 2) {
                    System.out.println("");
                }
            }
        }
    }
    //************************************************************//

    static void CallingIsPossible() throws IOException {
        for (horizontal = 0; horizontal <= 8; horizontal++) {
            for (vertical = 0; vertical <= 8; vertical++) {
                if (FilledArray[horizontal][vertical] == 0) {

                    IsPossible(horizontal, vertical);
                    Print();
                    System.out.println(" Enter a number ");
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    int n = Integer.parseInt(br.readLine());

                }
            }
        }
    }

    static int BackTrackNew(int Horizontal, int Vertical) {
        Vertical--;
        if (Vertical < 0 && Horizontal == 0) {
            return (10);
        }
        if (Vertical < 0 && Horizontal != 0) {
            Vertical = 8;
            Horizontal--;
        }
        while (InitialArray[Horizontal][Vertical] != 0) {
            Vertical--;
            if (Vertical < 0 && Horizontal > 0) {
                Vertical = 8;
                Horizontal--;
            } else if (Vertical < 0 && Horizontal == 0) {
                break;
            }
        }
        /*       if(FilledArray[Horizontal][Vertical]==9)
         {
         FilledArray[Horizontal][Vertical]=0;
         BackTrack(Horizontal,Vertical);

         }*/
        PlaceNumber = FilledArray[Horizontal][Vertical];
        FilledArray[Horizontal][Vertical] = 0;
        return (Vertical);
    }

}
