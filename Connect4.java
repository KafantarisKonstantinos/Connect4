package ce326.hw3;

import org.json.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.*;
import javax.swing.JList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JScrollPane;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Connect4 {
    // some global variables
    int playersTurn;
    int buttonPush;
    int player;
    static int enablePlay;
    int PvpPlay;
    int AiPlay;
    int HistoryFile;
    static int depth;
    static int winnerFound;
    static int winner;
    static int makeList;
    JSONArray allMoves = new JSONArray();
    List<Timer> timers = new ArrayList<>();
    LocalDateTime now;

    public Connect4(int HistoryFile) {
        this.HistoryFile = HistoryFile;
    }

    public Connect4() {

        buttonPush = 0;
        int buttonClickedCount = 0;
        JTextField keyChoice;
        enablePlay = 0;
        playersTurn = 0;
        player = 0;
        JFrame frame = new JFrame("Connect 4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // creates the background label
        ImageIcon imageIcon = new ImageIcon("./connect4platform.jpg");
        JLabel background = new JLabel(imageIcon);
        background.setPreferredSize(new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight()));
        frame.getContentPane().add(background, BorderLayout.SOUTH);

        // ----------------------------------------------------------------
        // gets image width and height
        int imageWidth = imageIcon.getIconWidth();
        int imageHeight = imageIcon.getIconHeight();

        // creates the sequins for each player
        ImageIcon redIcon = new ImageIcon("./red1.png");
        ImageIcon redIconRecolored = new ImageIcon("./recoloredRed.png");
        ImageIcon yellowIconRecolored = new ImageIcon("./recoloredYellow.png");
        ImageIcon yellowIcon = new ImageIcon("./yellow1.png");

        // creates the buttons and makes their actionlisteners
        JPanel buttonPanel = new JPanel(new GridLayout(6, 7));
        buttonPanel.setOpaque(false);
        buttonPanel.setBounds(0, 0, imageWidth, imageHeight);
        // Create buttons and set their bounds
        // Create buttons and add them to the button panel
        JButton[][] buttons = new JButton[6][7];
        int[][] keepingTrack = new int[6][7];
        int[] occupiedCells = new int[7]; // keeps track of the number of occupied cells in each column

        // ----------------------------------------------------------------
        // creates the buttons and their action listeners

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                buttons[row][col] = new JButton();// "(" + row + ", " + col + ")"
                buttonPanel.add(buttons[row][col]);
                buttons[row][col].setOpaque(false);
                buttons[row][col].setContentAreaFilled(false);
                buttons[row][col].setBorderPainted(false);

                final int columnCount = col; // needed to access column index inside action listener

                buttons[row][col].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2 && enablePlay == 0) {
                            movePlay(columnCount, occupiedCells, buttons, redIcon, yellowIcon, redIconRecolored,
                                    yellowIconRecolored, keepingTrack,
                                    enablePlay);
                            frame.requestFocusInWindow();
                            // Handle double-click event
                        }
                    }
                });
            }

        }

        // creates key listeners for key 0-6 from keyboard
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (enablePlay == 0) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_0:
                            movePlay(0, occupiedCells, buttons, redIcon, yellowIcon, redIconRecolored,
                                    yellowIconRecolored, keepingTrack,
                                    enablePlay);
                            break;
                        case KeyEvent.VK_1:
                            movePlay(1, occupiedCells, buttons, redIcon, yellowIcon, redIconRecolored,
                                    yellowIconRecolored, keepingTrack,
                                    enablePlay);
                            break;
                        case KeyEvent.VK_2:
                            movePlay(2, occupiedCells, buttons, redIcon, yellowIcon, redIconRecolored,
                                    yellowIconRecolored, keepingTrack,
                                    enablePlay);
                            break;
                        case KeyEvent.VK_3:
                            movePlay(3, occupiedCells, buttons, redIcon, yellowIcon, redIconRecolored,
                                    yellowIconRecolored, keepingTrack,
                                    enablePlay);
                            break;
                        case KeyEvent.VK_4:

                            movePlay(4, occupiedCells, buttons, redIcon, yellowIcon, redIconRecolored,
                                    yellowIconRecolored, keepingTrack,
                                    enablePlay);
                            break;
                        case KeyEvent.VK_5:

                            movePlay(5, occupiedCells, buttons, redIcon, yellowIcon, redIconRecolored,
                                    yellowIconRecolored, keepingTrack,
                                    enablePlay);
                            break;
                        case KeyEvent.VK_6:

                            movePlay(6, occupiedCells, buttons, redIcon, yellowIcon, redIconRecolored,
                                    yellowIconRecolored, keepingTrack,
                                    enablePlay);
                            break;
                    }
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        // ----------------------------------------------------------------
        // creates the menu label
        JMenuBar gameMenuBar = new JMenuBar();
        gameMenuBar.setBackground(Color.WHITE);
        frame.getContentPane().add(gameMenuBar, BorderLayout.NORTH);
        JMenu gameMenu = new JMenu(
                "New Game");
        gameMenu.setBackground(Color.RED);
        gameMenu.setMnemonic(KeyEvent.VK_C);
        gameMenuBar.add(gameMenu);
        JMenu gameMenu1 = new JMenu(
                "1st Player");
        gameMenu.setBackground(Color.RED);
        gameMenu.setMnemonic(KeyEvent.VK_C);
        gameMenuBar.add(gameMenu1);
        JMenu gameMenu2 = new JMenu(
                "History");
        gameMenu.setBackground(Color.RED);
        gameMenu.setMnemonic(KeyEvent.VK_C);
        gameMenuBar.add(gameMenu2);
        JMenu gameMenu3 = new JMenu(
                "Help");
        gameMenu.setBackground(Color.RED);
        gameMenu.setMnemonic(KeyEvent.VK_C);
        gameMenuBar.add(gameMenu3);

        // sets the game rule button for player help
        JMenuItem helpFind = new JMenuItem("Game Rules");
        helpFind.setBackground(Color.WHITE);
        helpFind.setMnemonic(KeyEvent.VK_R);
        gameMenu3.add(helpFind);
        helpFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You can enter both 2Player or AI game\n"
                        + "For each one you should open the New Game Button\n"
                        + "Then you can choose 2 Player or the difficulty level of the AI.\n"
                        + "Before you choose the AI's difficulty level you should enter which player will begin the game : You or the AI, from the 1 Player button.\n"
                        + "To place a sequin you have to double click the column or press any key from 0-6 on your keyboard.\n"
                        + "The game will always give winner the red sequing player and loser the yellow sequin player!\n"
                        + "When a winner comes you should restart the game following the same path.");
            }
        });
        // sets a video in button for player help
        JMenuItem helpFound = new JMenuItem("Video with rules");
        helpFound.setBackground(Color.WHITE);
        helpFound.setMnemonic(KeyEvent.VK_R);
        gameMenu3.add(helpFound);
        helpFound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=utXzIFEVPjA"));
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("Opening websites is not supported on this platform.");
                }
            }
        });

        // sets the AI and YOU choices as firstplayer mode
        JRadioButton setPlayer1 = new JRadioButton("AI");
        JRadioButton setPlayer2 = new JRadioButton("YOU");
        // sets the AI as firstplayer mode
        setPlayer1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player = 1;
            }
        });
        setPlayer1.setBackground(Color.WHITE);
        setPlayer1.setMnemonic(KeyEvent.VK_R);
        gameMenu1.add(setPlayer1);
        // sets the player as 1move mode
        setPlayer2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player = 2;
            }
        });
        setPlayer2.setBackground(Color.WHITE);
        setPlayer2.setMnemonic(KeyEvent.VK_R);
        gameMenu1.add(setPlayer2);
        ButtonGroup group = new ButtonGroup();
        group.add(setPlayer1);
        group.add(setPlayer2);

        // set PvP Mode
        JMenuItem PvP = new JMenuItem("2 Player");
        PvP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You Entered PvP Mode\n" + "Press OK to continue");
                resetGame(buttons, keepingTrack, occupiedCells);
                winnerFound = 0;
                PvpPlay = 1;
                enablePlay = 0;
                AiPlay = 0;
            }
        });
        PvP.setBackground(Color.WHITE);
        PvP.setMnemonic(KeyEvent.VK_R);
        gameMenu.add(PvP);
        // sets the easy mode
        JMenuItem EASY = new JMenuItem("Trivial");
        EASY.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You Entered Easy Mode\n" + "Press OK to continue");
                background.setVisible(true);
                frame.requestFocus();
                // update the layout and repaint the container
                resetGame(buttons, keepingTrack, occupiedCells);
                if (player == 1) {
                    playersTurn = 2;
                } else if (player == 2) {
                    playersTurn = 1;
                }
                for (Timer timer : timers) {
                    timer.stop(); // stop the timer
                }
                PvpPlay = 0;
                HistoryFile = 0;
                winnerFound = 0;
                enablePlay = 0;
                AiPlay = 1;
                depth = 1;
                makeHistory();
                now = LocalDateTime.now();
                JSONObject moves = new JSONObject();
                moves.put("Type", "Trivial");
                if (playersTurn == 2) {
                    moves.put("Player", 2);
                    int col = AIMove.MakeMove(occupiedCells, buttons, redIcon, yellowIcon, keepingTrack, 1, 0);
                    moves.put("Move", col);
                    allMoves.put(moves);
                    playersTurn = 1;
                }
            }
        });
        EASY.setBackground(Color.WHITE);
        EASY.setMnemonic(KeyEvent.VK_R);
        gameMenu.add(EASY);

        // sets the medium mode
        JMenuItem Medium = new JMenuItem("Medium");
        Medium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You Entered Medium Mode\n" + "Press OK to continue");
                background.setVisible(true);
                frame.requestFocus();
                resetGame(buttons, keepingTrack, occupiedCells);
                if (player == 1) {
                    playersTurn = 2;
                } else if (player == 2) {
                    playersTurn = 1;
                }
                for (Timer timer : timers) {
                    timer.stop(); // stop the timer
                }
                PvpPlay = 0;
                winnerFound = 0;
                HistoryFile = 0;
                enablePlay = 0;
                AiPlay = 1;
                depth = 3;
                now = LocalDateTime.now();
                JSONObject moves = new JSONObject();
                moves.put("Type", "Medium");
                if (playersTurn == 2) {
                    moves.put("Player", 2);
                    int col = AIMove.MakeMove(occupiedCells, buttons, redIcon, yellowIcon, keepingTrack, 3, 0);
                    moves.put("Move", col);
                    allMoves.put(moves);
                    playersTurn = 1;
                }
            }
        });
        Medium.setBackground(Color.WHITE);
        Medium.setMnemonic(KeyEvent.VK_R);
        gameMenu.add(Medium);

        // sets the hard mode
        JMenuItem Hard = new JMenuItem("Hard");
        Hard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "You Entered Hard Mode\n" + "Press OK to continue");
                background.setVisible(true);
                frame.requestFocus();
                resetGame(buttons, keepingTrack, occupiedCells);
                if (player == 1) {
                    playersTurn = 2;
                } else if (player == 2) {
                    playersTurn = 1;
                }
                for (Timer timer : timers) {
                    timer.stop(); // stop the timer
                }
                timers.clear();
                HistoryFile = 0;
                PvpPlay = 0;
                winnerFound = 0;
                enablePlay = 0;
                AiPlay = 1;
                depth = 5;
                now = LocalDateTime.now();
                JSONObject moves = new JSONObject();
                moves.put("Type", "Hard");
                if (playersTurn == 2) {
                    moves.put("Player", 2);
                    int col = AIMove.MakeMove(occupiedCells, buttons, redIcon, yellowIcon, keepingTrack, 5, 0);
                    moves.put("Move", col);
                    allMoves.put(moves);
                    playersTurn = 1;
                }
            }
        });

        Hard.setBackground(Color.WHITE);
        Hard.setMnemonic(KeyEvent.VK_R);
        gameMenu.add(Hard);

        // sets histry choose
        JMenuItem historyItem = new JMenuItem("Saw history");
        historyItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                background.setVisible(false);
                AiPlay = 0;
                JLabel newBackground = new JLabel();
                newBackground.setPreferredSize(new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight()));
                newBackground.setLayout(new BorderLayout());
                frame.getContentPane().add(newBackground, BorderLayout.CENTER);
                HistoryFile = 1;
                JList<String> list = new JList<>();
                DefaultListModel<String> listModel = new DefaultListModel<>();

                // specify the directory to list
                Path directoryPath = Paths.get(System.getProperty("user.home"), "JsonFilesFolder");

                // get a stream of all the files and directories in the directory
                try (Stream<Path> stream = Files.list(directoryPath)) {
                    // add each file/directory to the model
                    stream.forEach(path -> listModel.addElement(path.toString()));
                } catch (IOException r) {
                    // handle any errors that may occur
                    r.printStackTrace();
                }
                List<Object> reversedList = new ArrayList<>();
                for (int i = listModel.getSize() - 1; i >= 0; i--) {
                    reversedList.add(listModel.getElementAt(i));
                }

                // Clear the existing listModel
                listModel.clear();

                // Add the reversed elements back to the listModel
                for (Object element : reversedList) {
                    listModel.addElement((String) element);
                }
                // set the model for the list
                list.setModel(listModel);
                list.addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        // if (e.getClickCount() == 2) {
                        if (!e.getValueIsAdjusting()) {
                            for (Timer timer : timers) {
                                timer.stop(); // stop the timer
                            }
                            timers.clear();
                            // String selectedItem = list.getSelectedValue();
                            resetGame(buttons, keepingTrack, occupiedCells);
                            winnerFound = 0;
                            enablePlay = 0;
                            int index = list.getSelectedIndex();
                            String selectedFilePath = listModel.getElementAt(index);
                            try {
                                // read the contents of the selected file as a byte array
                                byte[] jsonData = Files.readAllBytes(Paths.get(selectedFilePath));
                                int delay = 3;
                                Timer timer = new Timer(delay, new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        // code to be executed after delay
                                        background.setVisible(true);
                                    }
                                });
                                timer.setRepeats(false);
                                timer.start();
                                // convert the byte array to a JSON string

                                String jsonString = new String(jsonData);
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        JSONObject json = new JSONObject(jsonString);
                                        JSONArray moves = json.getJSONArray("moves");
                                        // creates the 3second delay for each move in the history game playback
                                        for (int i = 0; i < moves.length(); i++) {
                                            int delay1 = 3000;
                                            JSONObject move = moves.getJSONObject(i);
                                            Timer timer1 = new Timer(delay1 * i, new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    int player = move.getInt("Player");
                                                    int moveNum = move.getInt("Move");
                                                    if (player == 1 && winnerFound != 1) {
                                                        playersTurn = 1;
                                                        movePlay(moveNum, occupiedCells, buttons, redIcon,
                                                                yellowIcon,
                                                                redIconRecolored,
                                                                yellowIconRecolored, keepingTrack,
                                                                enablePlay);
                                                    } else if (player == 2 && winnerFound != 1) {
                                                        PvpPlay = 1;
                                                        playersTurn = 2;
                                                        movePlay(moveNum, occupiedCells, buttons, redIcon,
                                                                yellowIcon,
                                                                redIconRecolored,
                                                                yellowIconRecolored, keepingTrack,
                                                                enablePlay);
                                                    }
                                                    String type = move.optString("Type", "unknown");
                                                }
                                            });
                                            timer1.setRepeats(false); // only execute once
                                            timer1.start(); // start the timer
                                            timers.add(timer1);
                                        }
                                    }
                                });
                                thread.start(); // start the thread

                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                        }
                    }

                });

                JScrollPane scrollPane = new JScrollPane(list);
                newBackground.add(scrollPane, BorderLayout.CENTER);

                frame.getContentPane().add(newBackground, BorderLayout.CENTER);
                frame.getContentPane().validate();
                frame.getContentPane().repaint();
                makeHistory();
            }

        });
        historyItem.setBackground(Color.WHITE);
        historyItem.setMnemonic(KeyEvent.VK_R);
        gameMenu2.add(historyItem);

        background.add(buttonPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

    }

    // here is the code for checking if the players move can be completed and places
    // the sequin
    public void movePlay(final int columnCount, int occupiedCells[], JButton buttons[][], ImageIcon redIcon,
            ImageIcon yellowIcon, ImageIcon redIconRecolored, ImageIcon yellowIconRecolored, int keepingTrack[][],
            int enable) {

        if (enable == 0 && winnerFound != 1) {
            if (occupiedCells[columnCount] < 6) { // check if there is space in the column
                int rowCount = 6 - 1 - occupiedCells[columnCount];

                // making the needed changes to icons shape
                Image redImage = redIcon.getImage().getScaledInstance(
                        buttons[rowCount][columnCount].getWidth() - 15,
                        buttons[rowCount][columnCount].getHeight() - 15, java.awt.Image.SCALE_SMOOTH);
                ImageIcon resizedRedIcon = new ImageIcon(redImage);
                Image redImageRecolored = redIconRecolored.getImage().getScaledInstance(
                        buttons[rowCount][columnCount].getWidth() - 15,
                        buttons[rowCount][columnCount].getHeight() - 15, java.awt.Image.SCALE_SMOOTH);
                ImageIcon recoloredRedIcon = new ImageIcon(redImageRecolored);

                Image yellowImage = yellowIcon.getImage().getScaledInstance(
                        buttons[rowCount][columnCount].getWidth() - 15,
                        buttons[rowCount][columnCount].getHeight() - 15, java.awt.Image.SCALE_SMOOTH);
                ImageIcon resizedYellowIcon = new ImageIcon(yellowImage);
                Image yellowImagerecolored = yellowIconRecolored.getImage().getScaledInstance(
                        buttons[rowCount][columnCount].getWidth() - 15,
                        buttons[rowCount][columnCount].getHeight() - 15, java.awt.Image.SCALE_SMOOTH);
                ImageIcon recoloredYellowIcon = new ImageIcon(yellowImagerecolored);

                // checks if the turn is for the first player
                if (playersTurn == 1) {
                    keepingTrack[rowCount][columnCount] = 1;

                    // delays for the sequin
                    int delay = 300; // delay in milliseconds
                    buttons[rowCount][columnCount].setIcon(recoloredRedIcon);
                    buttons[rowCount][columnCount].setDisabledIcon(recoloredRedIcon);
                    buttons[rowCount][columnCount].setEnabled(false);
                    buttons[rowCount][columnCount].setVisible(true);
                    Timer timer = new Timer(delay, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // code to be executed after delay
                            buttons[rowCount][columnCount].setIcon(resizedRedIcon);
                            buttons[rowCount][columnCount].setEnabled(true);

                        }
                    });
                    timer.setRepeats(false); // only execute once
                    timer.start(); // start the timer
                    JSONObject moves = new JSONObject();
                    moves.put("Player", 1);
                    moves.put("Move", columnCount);
                    allMoves.put(moves);
                    occupiedCells[columnCount]++;
                    playersTurn = 2;

                    // checks for winner and if there isone stops the game
                    int check = checkWinner(keepingTrack);

                    if (check == 1) {
                        winnerFound = 1;
                        for (int row = 0; row < 6; row++) {
                            for (int col = 0; col < 7; col++) {
                                if (keepingTrack[row][col] == 1) {
                                    buttons[row][col].setDisabledIcon(resizedRedIcon);
                                } else if (keepingTrack[row][col] == 2) {
                                    buttons[row][col].setDisabledIcon(resizedYellowIcon);
                                }
                                buttons[row][col].setEnabled(false);
                                buttons[row][col].setVisible(true);
                                enablePlay = 1;

                            }
                        }
                        if (HistoryFile != 1) {
                            makeHistory();
                        }

                    } else if (check == 3) {
                        winnerFound = 1;
                        for (int row = 0; row < 6; row++) {
                            for (int col = 0; col < 7; col++) {
                                if (keepingTrack[row][col] == 1) {
                                    buttons[row][col].setDisabledIcon(resizedRedIcon);
                                } else if (keepingTrack[row][col] == 2) {
                                    buttons[row][col].setDisabledIcon(resizedYellowIcon);
                                }
                                buttons[row][col].setEnabled(false);
                                buttons[row][col].setVisible(true);
                                enablePlay = 1;

                            }
                        }
                        if (HistoryFile != 1) {
                            makeHistory();
                        }
                    }
                }
                // checks for second player move if there is the pvp on
                else if (playersTurn == 2 && PvpPlay == 1 && winnerFound != 1) {
                    keepingTrack[rowCount][columnCount] = 2;
                    int delay = 300; // delay in milliseconds
                    buttons[rowCount][columnCount].setIcon(recoloredYellowIcon);
                    buttons[rowCount][columnCount].setDisabledIcon(recoloredYellowIcon);
                    buttons[rowCount][columnCount].setEnabled(false);
                    buttons[rowCount][columnCount].setVisible(true);
                    Timer timer = new Timer(delay, new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // code to be executed after delay
                            buttons[rowCount][columnCount].setIcon(resizedYellowIcon);
                            buttons[rowCount][columnCount].setEnabled(true);

                        }
                    });
                    timer.setRepeats(false); // only execute once
                    timer.start(); // start the timer
                    occupiedCells[columnCount]++;
                    playersTurn = 1;

                    int check = checkWinner(keepingTrack);
                    if (check == 2) {
                        for (int row = 0; row < 6; row++) {
                            for (int col = 0; col < 7; col++) {
                                if (keepingTrack[row][col] == 1) {
                                    buttons[row][col].setDisabledIcon(resizedRedIcon);
                                } else if (keepingTrack[row][col] == 2) {
                                    buttons[row][col].setDisabledIcon(resizedYellowIcon);
                                }
                                buttons[row][col].setEnabled(false);
                                buttons[row][col].setVisible(true);
                                enablePlay = 1;
                            }
                        }
                    } else if (check == 4) {
                        winnerFound = 1;
                        for (int row = 0; row < 6; row++) {
                            for (int col = 0; col < 7; col++) {
                                if (keepingTrack[row][col] == 1) {
                                    buttons[row][col].setDisabledIcon(resizedRedIcon);
                                } else if (keepingTrack[row][col] == 2) {
                                    buttons[row][col].setDisabledIcon(resizedYellowIcon);
                                }
                                buttons[row][col].setEnabled(false);
                                buttons[row][col].setVisible(true);
                                enablePlay = 1;
                            }
                        }
                    }
                }
            }
            if (playersTurn == 2 && AiPlay == 1) {
                JSONObject moves = new JSONObject();
                moves.put("Player", 2);
                int col = AIMove.MakeMove(occupiedCells, buttons, redIcon, yellowIcon, keepingTrack, depth,
                        winnerFound);
                playersTurn = 1;
                moves.put("Move", col);
                allMoves.put(moves);
                if (winnerFound == 1) {
                    makeHistory();
                }

            }
        }
    }

    // ----------------------------------------------------------------
    // checks for winners
    static int checkWinner(int array[][]) {
        // checks for collumn winner
        int reds = 0;
        int yellows = 0;
        for (int col = 0; col < 7; col++) {
            for (int row = 5; row >= 0; row--) {
                if (array[row][col] == 1) {
                    reds++;
                    yellows = 0;
                    if (reds == 4) {
                        JOptionPane.showMessageDialog(null, " You Won!");
                        reds = 0;
                        yellows = 0;
                        winner = 1;
                        return 1;
                    }
                }
                if (array[row][col] == 2) {
                    reds = 0;
                    yellows++;
                    if (yellows == 4) {
                        JOptionPane.showMessageDialog(null, " You Lost!");
                        reds = 0;
                        yellows = 0;
                        winner = 2;
                        return 2;
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
                        JOptionPane.showMessageDialog(null, " You Won!");
                        reds = 0;
                        yellows = 0;
                        winner = 1;
                        return 1;
                    }
                }
                if (array[row][col] == 2) {
                    reds = 0;
                    yellows++;
                    if (yellows == 4) {
                        JOptionPane.showMessageDialog(null, " You Lost!");
                        reds = 0;
                        yellows = 0;
                        winner = 2;
                        return 2;
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
                    winner = 1;
                    JOptionPane.showMessageDialog(null, " You Won!");
                    return 3;

                } else if (row > 2 && col < 4 && array[row][col] == 2 && array[row - 1][col + 1] == 2
                        && array[row - 2][col + 2] == 2
                        && array[row - 3][col + 3] == 2) {
                    winner = 2;
                    JOptionPane.showMessageDialog(null, " You Lost!");
                    return 4;

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
                    winner = 1;
                    JOptionPane.showMessageDialog(null, " You Won!");
                    return 3;

                } else if (row > 2 && col > 2 && array[row][col] == 2 && array[row - 1][col - 1] == 2
                        && array[row - 2][col - 2] == 2
                        && array[row - 3][col - 3] == 2) {
                    winner = 2;
                    JOptionPane.showMessageDialog(null, " You Lost!");
                    return 4;

                }
            }
            reds = 0;
            yellows = 0;
        }

        return 0;
    }

    public void resetGame(JButton[][] buttons, int keepingTrack[][], int occupiedCells[]) {
        // reset game board
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                buttons[i][j].setIcon(null);
                keepingTrack[i][j] = 0;
                buttons[i][j].setEnabled(true);
            }
        }
        // reset game variables
        playersTurn = 1;
        buttonPush = 0;
        Arrays.fill(occupiedCells, 0);
    }

    public void makeHistory() {
        // creates the folder once
        String folderName = "JsonFilesFolder";
        Path path = Paths.get(System.getProperty("user.home"), folderName);

        if (!Files.exists(path)) {
            try {
                Path newDir = Files.createDirectory(path);
                System.out.println("Directory created successfully: " + newDir.toString());
            } catch (IOException e) {
            }
        }
        // creatng each filename of every game
        if (winnerFound == 1) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd - HH.mm.ss");
            String timestamp = now.format(formatter);
            String movesJString = allMoves.toString(2);
            String fileName = timestamp;
            if (depth == 1) {
                if (winner == 2)
                    fileName = timestamp + "  L- Trivial W- AI.json";
                else if (winner == 1)
                    fileName = timestamp + "  L- Trivial W- P.json";

            } else if (depth == 3) {
                if (winner == 2)
                    fileName = timestamp + "  L- Medium  W- AI.json";
                else if (winner == 1)
                    fileName = timestamp + "  L- Medium  W- P.json";
            } else if (depth == 5) {
                if (winner == 2)
                    fileName = timestamp + "  L- Hard    W- AI.json";
                else if (winner == 1)
                    fileName = timestamp + "  L- Hard    W- P.json";
            }

            // create the JSON object with the moves
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("moves", allMoves);

            // write the JSON object to a file
            try {
                Path filePath = Paths.get(path.toString(), fileName);
                Files.write(filePath, jsonObject.toString().getBytes());
            } catch (IOException e) {

            }
        }

    }

    // ----------------------------------------------------------------
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Connect4();
            }
        });
    }
}
