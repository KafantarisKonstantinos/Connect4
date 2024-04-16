package ce326.hw3;

import java.lang.Math;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class AIMove {
    int something;
    static int colChoice;
    static boolean iAmWinner;

    public void AiMove() {

    }

    public static int MakeMove(int occupiedCells[], JButton buttons[][], ImageIcon redIcon,
            ImageIcon yellowIcon, int keepingTrack[][], int depth, int winnerFound) {
        int[] points = new int[2];
        iAmWinner = false;
        if (winnerFound == 0) {
            points = minMax(keepingTrack, depth, -Integer.MAX_VALUE, Integer.MAX_VALUE, 2);
            ImageIcon yellowIconRecolored = new ImageIcon("C:/JAVA/extra/recoloredyellow.png");
            colChoice = points[0];
            if (occupiedCells[colChoice] < 6) { // check if there is space in the column
                int rowCount = 6 - 1 - occupiedCells[colChoice];

                // Makes the images
                Image yellowImage = yellowIcon.getImage().getScaledInstance(
                        buttons[rowCount][colChoice].getWidth() - 15,
                        buttons[rowCount][colChoice].getHeight() - 15, java.awt.Image.SCALE_SMOOTH);
                ImageIcon resizedYellowIcon = new ImageIcon(yellowImage);
                Image yellowRecoloredImage = yellowIconRecolored.getImage().getScaledInstance(
                        buttons[rowCount][colChoice].getWidth() - 15,
                        buttons[rowCount][colChoice].getHeight() - 15, java.awt.Image.SCALE_SMOOTH);
                ImageIcon recoloredYellowIcon = new ImageIcon(yellowRecoloredImage);
                Image RedImage = redIcon.getImage().getScaledInstance(
                        buttons[rowCount][colChoice].getWidth() - 15,
                        buttons[rowCount][colChoice].getHeight() - 15, java.awt.Image.SCALE_SMOOTH);
                ImageIcon resizedRedIcon = new ImageIcon(RedImage);
                keepingTrack[rowCount][colChoice] = 2;
                int delay = 300; // delay in milliseconds
                buttons[rowCount][colChoice].setIcon(recoloredYellowIcon);
                buttons[rowCount][colChoice].setDisabledIcon(recoloredYellowIcon);
                buttons[rowCount][colChoice].setVisible(true);
                buttons[rowCount][colChoice].setEnabled(false);
                Timer timer = new Timer(delay, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buttons[rowCount][colChoice].setIcon(resizedYellowIcon);
                        buttons[rowCount][colChoice].setEnabled(true);
                    }
                });
                timer.setRepeats(false); // only execute once
                timer.start(); // start the timer
                occupiedCells[colChoice]++;

                int check = Connect4.checkWinner(keepingTrack);
                if (check == 2) {
                    Connect4.winnerFound = 1;
                    for (int row = 0; row < 6; row++) {
                        for (int col = 0; col < 7; col++) {
                            if (keepingTrack[row][col] == 1) {
                                buttons[row][col].setDisabledIcon(resizedRedIcon);
                            } else if (keepingTrack[row][col] == 2) {
                                buttons[row][col].setDisabledIcon(resizedYellowIcon);
                            }
                            // buttonPanel.add(buttons[row][col]);
                            buttons[row][col].setEnabled(false);
                            buttons[row][col].setVisible(true);
                            Connect4.enablePlay = 1;
                        }
                    }
                } else if (check == 4) {
                    Connect4.winnerFound = 1;
                    for (int row = 0; row < 6; row++) {
                        for (int col = 0; col < 7; col++) {
                            if (keepingTrack[row][col] == 1) {
                                buttons[row][col].setDisabledIcon(resizedRedIcon);
                            } else if (keepingTrack[row][col] == 2) {
                                buttons[row][col].setDisabledIcon(resizedYellowIcon);
                            }
                            buttons[row][col].setEnabled(false);
                            buttons[row][col].setVisible(true);
                            Connect4.enablePlay = 1;
                        }
                    }
                }
            }
        }
        return colChoice;
    }

    static int PointsCount(int keepingTrack[][]) {
        int redPoints = 0;
        for (int row = 5; row >= 0; row--) {
            for (int col = 0; col < 7; col++) {
                // checks for diagonal right of each position
                if (keepingTrack[row][col] == 1 || keepingTrack[row][col] == 0) {
                    if (row > 2 && col < 4) {
                        int count = 0;
                        int stop = 0;
                        int countStop = 0;
                        for (int i = row; i >= 0;) {
                            for (int c = col; c < 7; c++) {
                                countStop++;

                                if (keepingTrack[i][c] == 1) {
                                    count++;
                                } else if (keepingTrack[i][c] == 2) {
                                    stop = 1;
                                }
                                if (countStop == 4) {
                                    break;
                                }
                                i--;
                            }
                            if (countStop == 4) {
                                break;
                            }
                        }
                        if (count == 1 && stop == 0 && countStop == 4) {
                            redPoints--;
                        }
                        if (count == 2 && stop == 0 && countStop == 4) {
                            redPoints = redPoints - 4;
                        }
                        if (count == 3 && stop == 0 && countStop == 4) {
                            redPoints = redPoints - 16;
                        }
                        if (count == 4 && stop == 0 && countStop == 4) {
                            redPoints = redPoints - 10000;
                        }
                    }
                    if (row > 2 && col > 2) {
                        int count = 0;
                        int stop = 0;
                        int countStop = 0;
                        for (int i = row; i >= 0;) {
                            for (int c = col; c >= 0; c--) {
                                countStop++;

                                if (keepingTrack[i][c] == 1) {
                                    count++;
                                } else if (keepingTrack[i][c] == 2) {
                                    stop = 1;
                                }
                                if (countStop == 4) {
                                    break;
                                }
                                i--;
                            }
                            if (countStop == 4) {
                                break;
                            }
                        }
                        if (count == 1 && stop == 0 && countStop == 4) {
                            redPoints--;
                        }
                        if (count == 2 && stop == 0 && countStop == 4) {
                            redPoints = redPoints - 4;
                        }
                        if (count == 3 && stop == 0 && countStop == 4) {
                            redPoints = redPoints - 16;
                        }
                        if (count == 4 && stop == 0 && countStop == 4) {
                            redPoints = redPoints - 10000;
                        }

                    }
                }
                if (keepingTrack[row][col] == 1) {
                    // check for row 4rth's left side from the sequin
                    // checks for non continued conection in left , but with a gap
                    if (col > 3) {
                        int column = col - 4;
                        while (column < 7) {
                            int count = 0;
                            int stop = 0;
                            int countStop = 0;
                            column = column + 1;
                            if (column == col + 1 || column == 5) {
                                break;
                            }
                            for (int pos = column; pos < 7; pos++) {
                                countStop++;
                                if (keepingTrack[row][pos] == 1) {
                                    count++;
                                } else if (keepingTrack[row][pos] == 2) {
                                    stop = 1;
                                }
                                if (countStop == 4) {
                                    break;
                                }
                            }
                            if (count == 1 && stop == 0 && countStop == 4) {
                                redPoints--;
                            }
                            if (count == 2 && stop == 0 && countStop == 4) {
                                redPoints = redPoints - 4;
                            }
                            if (count == 3 && stop == 0 && countStop == 4) {
                                redPoints = redPoints - 16;
                            }
                            if (count == 4 && stop == 0 && countStop == 4) {
                                redPoints = redPoints - 10000;
                            }
                        }
                    }
                    // check for row 4rth's right side from the sequin
                    // checks for non continued conection in right , but with a gap
                    else if (col < 3) {
                        int column = col + 4;
                        while (column >= 0) {
                            int count = 0;
                            int stop = 0;
                            int columnCount = 0;
                            column = column - 1;
                            if (column == 2 || column == col - 1) {
                                break;
                            }
                            for (int pos = col; pos >= 0; pos--) {
                                columnCount++;
                                if (keepingTrack[row][pos] == 1) {
                                    count++;
                                } else if (keepingTrack[row][pos] == 2) {
                                    stop = 1;
                                }
                                if (columnCount == 4) {
                                    break;
                                }
                            }
                            if (count == 1 && stop == 0 && columnCount == 4) {
                                redPoints--;
                            }
                            if (count == 2 && stop == 0 && columnCount == 4) {
                                redPoints = redPoints - 4;
                            }
                            if (count == 3 && stop == 0 && columnCount == 4) {
                                redPoints = redPoints - 16;
                            }
                            if (count == 4 && stop == 0 && columnCount == 4) {
                                redPoints = redPoints - 10000;
                            }
                        }
                    } else if (col == 3) {
                        int column = -1;
                        while (column < 4) {
                            int count = 0;
                            int stop = 0;
                            int countStop = 0;
                            column = column + 1;
                            if (column == 4) {
                                break;
                            }
                            for (int pos = column; pos < 7; pos++) {
                                countStop++;
                                if (keepingTrack[row][pos] == 1) {
                                    count++;
                                } else if (keepingTrack[row][pos] == 2) {
                                    stop = 1;
                                }
                                if (countStop == 4) {
                                    break;
                                }
                            }
                            if (count == 1 && stop == 0 && countStop == 4) {
                                redPoints--;
                            }
                            if (count == 2 && stop == 0 && countStop == 4) {
                                redPoints = redPoints - 4;
                            }
                            if (count == 3 && stop == 0 && countStop == 4) {
                                redPoints = redPoints - 16;
                            }
                            if (count == 4 && stop == 0 && countStop == 4) {
                                redPoints = redPoints - 10000;
                            }
                        }

                    }
                    // checks for column 4rth's upside from the sequin
                    if (row >= 3 && keepingTrack[row - 1][col] == 0 && keepingTrack[row - 2][col] == 0
                            && keepingTrack[row - 3][col] == 0) {
                        redPoints--;
                    } else if (row >= 3 && keepingTrack[row - 1][col] == 1 && keepingTrack[row - 2][col] == 0
                            && keepingTrack[row - 3][col] == 0) {
                        redPoints = redPoints - 4;
                    } else if (row >= 3 && keepingTrack[row - 1][col] == 1 && keepingTrack[row - 2][col] == 1
                            && keepingTrack[row - 3][col] == 0) {
                        redPoints = redPoints - 16;
                    } else if (row >= 3 && keepingTrack[row - 1][col] == 1 && keepingTrack[row - 2][col] == 1
                            && keepingTrack[row - 3][col] == 1) {
                        redPoints = redPoints - 10000;
                    }
                }
                // adding the ai's points
                if (keepingTrack[row][col] == 2 || keepingTrack[row][col] == 0) {
                    if (row > 2 && col < 4) {
                        int count = 0;
                        int stop = 0;
                        int countStop = 0;
                        for (int i = row; i >= 0;) {
                            for (int c = col; c < 7; c++) {
                                countStop++;
                                if (keepingTrack[i][c] == 2) {
                                    count++;
                                } else if (keepingTrack[i][c] == 1) {
                                    stop = 1;
                                }
                                if (countStop == 4) {
                                    break;
                                }
                                i--;
                            }
                            if (countStop == 4) {
                                break;
                            }
                        }
                        if (count == 1 && stop == 0 && countStop == 4) {
                            redPoints++;
                        }
                        if (count == 2 && stop == 0 && countStop == 4) {
                            redPoints = redPoints + 4;
                        }
                        if (count == 3 && stop == 0 && countStop == 4) {
                            redPoints = redPoints + 16;
                        }
                        if (count == 4 && stop == 0 && countStop == 4) {
                            redPoints = redPoints + 10000;
                        }
                    }
                    if (row > 2 && col > 2) {
                        int count = 0;
                        int stop = 0;
                        int countStop = 0;
                        for (int i = row; i >= 0;) {
                            for (int c = col; c >= 0; c--) {
                                countStop++;
                                if (keepingTrack[i][c] == 2) {
                                    count++;
                                } else if (keepingTrack[i][c] == 1) {
                                    stop = 1;
                                }
                                if (countStop == 4) {
                                    break;
                                }
                                i--;
                            }
                            if (countStop == 4) {
                                break;
                            }
                        }
                        if (count == 1 && stop == 0 && countStop == 4) {
                            redPoints++;
                        }
                        if (count == 2 && stop == 0 && countStop == 4) {
                            redPoints = redPoints + 4;
                        }
                        if (count == 3 && stop == 0 && countStop == 4) {
                            redPoints = redPoints + 16;
                        }
                        if (count == 4 && stop == 0 && countStop == 4) {
                            redPoints = redPoints + 10000;
                        }

                    }
                }
                if (keepingTrack[row][col] == 2) {
                    // checks for non continued conection in left , but with a gap
                    if (col > 3) {
                        int column = col - 4;
                        while (column < 7) {
                            int count = 0;
                            int countStop = 0;
                            int stop = 0;
                            column = column + 1;
                            if (column == 5 || column == col + 1) {
                                break;
                            }
                            for (int pos = column; pos < 7; pos++) {
                                countStop++;
                                if (keepingTrack[row][pos] == 2) {
                                    count++;
                                } else if (keepingTrack[row][pos] == 1) {
                                    stop = 1;
                                }
                                if (countStop == 4) {
                                    break;
                                }
                            }
                            if (count == 1 && stop == 0 && countStop == 4) {
                                redPoints++;
                            }
                            if (count == 2 && stop == 0 && countStop == 4) {
                                redPoints = redPoints + 4;
                            }
                            if (count == 3 && stop == 0 && countStop == 4) {
                                redPoints = redPoints + 16;
                            }
                            if (count == 3 && stop == 0 && countStop == 4) {
                                redPoints = redPoints + 10000;
                            }
                        }
                    }
                    // check for row 4rth's right side from the sequin
                    // checks for non continued conection in right , but with a gap
                    else if (col < 3) {
                        int column = col + 4;
                        while (column >= 0) {
                            int count = 0;
                            int countStop = 0;
                            int stop = 0;
                            column = column - 1;
                            if (column == 2 || column == col - 1) {
                                break;
                            }
                            for (int pos = column; pos >= 0; pos--) {
                                countStop++;
                                if (keepingTrack[row][pos] == 2) {
                                    count++;
                                } else if (keepingTrack[row][pos] == 1) {
                                    stop = 1;
                                }
                                if (countStop == 4) {
                                    break;
                                }
                            }
                            if (count == 1 && stop == 0) {
                                redPoints++;
                            }
                            if (count == 2 && stop == 0) {
                                redPoints = redPoints + 4;
                            }
                            if (count == 3 && stop == 0) {
                                redPoints = redPoints + 16;
                            }
                            if (count == 4 && stop == 0) {
                                redPoints = redPoints + 10000;
                            }
                        }
                    } else if (col == 3) {
                        int column = -1;
                        while (column < 4) {
                            int count = 0;
                            int countStop = 0;
                            int stop = 0;
                            column = column + 1;
                            if (column == 4) {
                                break;
                            }
                            for (int pos = column; pos < 7; pos++) {
                                countStop++;
                                if (keepingTrack[row][pos] == 2) {
                                    count++;
                                } else if (keepingTrack[row][pos] == 1) {
                                    stop = 1;
                                }
                                if (countStop == 4) {
                                    break;
                                }
                            }
                            if (count == 1 && stop == 0 && countStop == 4) {
                                redPoints++;
                            }
                            if (count == 2 && stop == 0 && countStop == 4) {
                                redPoints = redPoints + 4;
                            }
                            if (count == 3 && stop == 0 && countStop == 4) {
                                redPoints = redPoints + 16;
                            }
                            if (count == 3 && stop == 0 && countStop == 4) {
                                redPoints = redPoints + 10000;
                            }
                        }
                    }
                    // checks for column 4rth's upside from the sequin
                    if (row >= 3 && keepingTrack[row - 1][col] == 0 && keepingTrack[row - 2][col] == 0
                            && keepingTrack[row - 3][col] == 0) {
                        redPoints++;
                    } else if (row >= 3 && keepingTrack[row - 1][col] == 2 && keepingTrack[row - 2][col] == 0
                            && keepingTrack[row - 3][col] == 0) {
                        redPoints = redPoints + 4;
                    } else if (row >= 3 && keepingTrack[row - 1][col] == 2 && keepingTrack[row - 2][col] == 2
                            && keepingTrack[row - 3][col] == 0) {
                        redPoints = redPoints + 16;
                    } else if (row >= 3 && keepingTrack[row - 1][col] == 2 && keepingTrack[row - 2][col] == 2
                            && keepingTrack[row - 3][col] == 2) {
                        redPoints = redPoints + 10000;
                    }

                }
            }
        }
        // System.out.println(redPoints);
        return redPoints;
    }

    static int[] minMax(int keepingTrack[][], int depth, int alpha, int beta, int maxPlayer) {
        if (depth == 0 || checkWinnerMinMax(keepingTrack)) {
            // return PointsCount(keepingTrack);
            return (new int[] { -1, PointsCount(keepingTrack) });
        }
        // for maximizer player --> for AI

        if (maxPlayer == 2) {
            int[] evaAr = new int[2];
            evaAr[0] = -1;
            evaAr[1] = -Integer.MAX_VALUE;
            int row = 5;
            for (int col = 0; col < 7; col++) {
                int eva;
                // checking for the free row
                if ((keepingTrack[row][col] == 1 || keepingTrack[row][col] == 2)) {
                    int roww;
                    for (roww = row; roww >= 0; roww--) {
                        if (roww < 0) {
                            break;
                        }
                        if (keepingTrack[roww][col] == 0) {
                            keepingTrack[roww][col] = 2;
                            break;
                        }
                    }
                    if (roww < 0) {
                        continue;
                    }
                    if (checkWinnerMinMax(keepingTrack)) {
                        evaAr[0] = col;
                        evaAr[1] = 10000;
                        keepingTrack[roww][col] = 0;
                        return evaAr;
                    }
                    int[] eva1 = minMax(keepingTrack, depth - 1, alpha, beta, 1);
                    if (eva1[1] > evaAr[1]) {
                        evaAr[0] = col;
                    }
                    evaAr[1] = Math.max(evaAr[1], eva1[1]);
                    alpha = Math.max(alpha, evaAr[1]);

                    keepingTrack[roww][col] = 0;
                    if (beta <= alpha) {
                        break;
                    }
                }
                // if the position in empty
                else if (keepingTrack[row][col] == 0) {
                    keepingTrack[row][col] = 2;
                    if (checkWinnerMinMax(keepingTrack)) {
                        evaAr[0] = col;
                        evaAr[1] = 10000;
                        keepingTrack[row][col] = 0;
                        return evaAr;
                    }
                    int[] eva1 = minMax(keepingTrack, depth - 1, alpha, beta, 1);
                    if (eva1[1] > evaAr[1]) {
                        evaAr[0] = col;
                    }
                    evaAr[1] = Math.max(evaAr[1], eva1[1]);
                    alpha = Math.max(alpha, evaAr[1]);
                    keepingTrack[row][col] = 0;
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return evaAr;

        }
        // minimizer player --> for YOU
        else if (maxPlayer == 1) {
            int[] evaArMin = new int[2];
            evaArMin[0] = -1;
            evaArMin[1] = Integer.MAX_VALUE;
            iAmWinner = false;
            int row = 5;
            for (int col = 0; col < 7; col++) {
                // finds the empty row and plays the move
                if (keepingTrack[row][col] == 2 || keepingTrack[row][col] == 1) {
                    int roww;
                    for (roww = row; roww >= 0; roww--) {
                        if (roww < 0) {
                            break;
                        }
                        if (keepingTrack[roww][col] == 0) {
                            keepingTrack[roww][col] = 1;
                            break;
                        }
                    }
                    if (roww < 0) {
                        continue;
                    }
                    if (checkWinnerMinMax(keepingTrack)) {
                        evaArMin[0] = col;
                        evaArMin[1] = -10000;
                        keepingTrack[roww][col] = 0;
                        return evaArMin;
                    }
                    int[] eva1 = minMax(keepingTrack, depth - 1, alpha, beta, 2);
                    if (eva1[1] < evaArMin[1]) {
                        evaArMin[0] = col;
                    }
                    evaArMin[1] = Math.min(evaArMin[1], eva1[1]);
                    beta = Math.min(beta, evaArMin[1]);
                    keepingTrack[roww][col] = 0;
                    if (beta <= alpha) {
                        break;
                    }
                }
                // is empty row and plays the move
                else if (keepingTrack[row][col] == 0) {
                    keepingTrack[row][col] = 1;
                    if (checkWinnerMinMax(keepingTrack)) {
                        evaArMin[0] = col;
                        evaArMin[1] = -10000;
                        keepingTrack[row][col] = 0;
                        return evaArMin;
                    }
                    int[] eva1 = minMax(keepingTrack, depth - 1, alpha, beta, 2);
                    if (eva1[1] < evaArMin[1]) {
                        evaArMin[0] = col;
                    }

                    evaArMin[1] = Math.min(evaArMin[1], eva1[1]);
                    beta = Math.min(beta, evaArMin[1]);
                    keepingTrack[row][col] = 0;
                    if (beta <= alpha) {
                        break;
                    }
                }
            }

            return evaArMin;
        }
        int[] result = new int[2];
        return result;
    }

    static boolean checkWinnerMinMax(int array[][]) {
        // checks for collumn winner
        int reds = 0;
        int yellows = 0;
        for (int col = 0; col < 7; col++) {
            for (int row = 5; row >= 0; row--) {
                if (array[row][col] == 1) {
                    reds++;
                    yellows = 0;
                    if (reds == 4) {
                        reds = 0;
                        yellows = 0;
                        return true;
                    }
                }
                if (array[row][col] == 2) {
                    reds = 0;
                    yellows++;
                    if (yellows == 4) {
                        reds = 0;
                        yellows = 0;
                        return true;
                    }
                }
            }
            reds = 0;
            yellows = 0;
        }
        // ---------------------------------------------------------------
        // checks for row winner
        for (int row = 5; row >= 0; row--) {
            for (int col = 0; col < 7; col++) {
                if (array[row][col] == 1) {
                    reds++;
                    yellows = 0;
                    if (reds == 4) {
                        reds = 0;
                        yellows = 0;
                        return true;
                    }
                }
                if (array[row][col] == 2) {
                    reds = 0;
                    yellows++;
                    if (yellows == 4) {
                        reds = 0;
                        yellows = 0;
                        return true;
                    }
                }
                if (array[row][col] == 0) {
                    reds = 0;
                    yellows = 0;
                }
            }
            reds = 0;
            yellows = 0;
        }
        // ---------------------------------------------------------------
        // checks for diagonial right winner
        for (int row = 5; row >= 0; row--) {
            for (int col = 0; col < 7; col++) {
                if (row > 2 && col < 4 && array[row][col] == 1 && array[row - 1][col + 1] == 1
                        && array[row - 2][col + 2] == 1
                        && array[row - 3][col + 3] == 1) {
                    return true;

                } else if (row > 2 && col < 4 && array[row][col] == 2 && array[row - 1][col + 1] == 2
                        && array[row - 2][col + 2] == 2
                        && array[row - 3][col + 3] == 2) {
                    return true;

                }
            }
            reds = 0;
            yellows = 0;
        }
        // ---------------------------------------------------------------
        // checks for diagonial left winner
        for (int row = 5; row >= 0; row--) {
            for (int col = 0; col < 7; col++) {
                if (row > 2 && col > 2 && array[row][col] == 1 && array[row - 1][col - 1] == 1
                        && array[row - 2][col - 2] == 1
                        && array[row - 3][col - 3] == 1) {
                    return true;

                } else if (row > 2 && col > 2 && array[row][col] == 2 && array[row - 1][col - 1] == 2
                        && array[row - 2][col - 2] == 2
                        && array[row - 3][col - 3] == 2) {
                    return true;

                }
            }
            reds = 0;
            yellows = 0;
        }

        return false;
    }
}