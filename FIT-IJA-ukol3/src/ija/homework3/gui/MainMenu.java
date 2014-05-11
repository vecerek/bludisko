    package ija.homework3.gui;

    import javax.swing.*;
    import javax.swing.event.*;
    import java.awt.*;
    import java.awt.event.*;
    import ija.homework3.gui.GamePlay;

    /**
     * Object responsible for the menu of the game.
     */
    public class MainMenu extends javax.swing.JFrame {

        /**  Varibale declaration */
        private JPanel      panelCont;
        private JPanel      panelMainMenu;
        private JPanel      panelPlayMenu;
        private JPanel      panelGameScreen;
        private JButton     buttonPlay;
        private JButton     buttonOptions;
        private JButton     buttonExit;
        private JButton     buttonBack;
        private JButton     buttonNewGame;
        private JButton     buttonConnect;
        private JLabel      labelMenuBackground;
        private JLabel      labelPlayBackground;
        private JLabel      labelSliderValues;
        private JLabel      labelMaps;
        private JLabel      labelGames;
        private CardLayout  layout;
        private GroupLayout listLayout;
        private JScrollPane scrollPanelMapList;
        private JScrollPane scrollPanelGameList;
        private JList       listMap;
        private JList       listGame;
        private JSlider     sliderSpeed;
        private JTextArea  textFieldCurrentSpeed;
        private String[] strListMap;
        private String[] strListGame;
        private double gameSpeed;

        /**  Constructor  */
        public MainMenu() {
            super("The Labyrinth");

            setResizable(false);
            setSize(922,542);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            initComponents();

            //  Sets the main panel to CardLayout and adds it to the Content Panel
            panelCont.setLayout(layout);
            getContentPane().add(panelCont);

            //  Creates the panel of the MainMenu
            createMainMenu();

            //  Creates the panel of PlayMenu
            createPlayMenu();

            //  Creates the panel of Game
            createGameScreen();

            //  Sets the MainMenu panel as starting panel
            layout.show(panelCont, "panelMainMenu");
            repaint();
        }

        /**  Methods  */
        //  Initialization
        private void initComponents() {
            panelCont             = new JPanel();
            panelMainMenu         = new JPanel();
            panelPlayMenu         = new JPanel();
            panelGameScreen       = new JPanel();
            buttonPlay            = new JButton();
            buttonOptions         = new JButton();
            buttonExit            = new JButton();
            buttonBack            = new JButton();
            buttonNewGame         = new JButton();
            buttonConnect         = new JButton();
            labelMenuBackground   = new JLabel();
            labelPlayBackground   = new JLabel();
            labelSliderValues     = new JLabel();
            labelMaps             = new JLabel();
            labelGames            = new JLabel();
            layout                = new CardLayout();
            listLayout            = new GroupLayout(panelPlayMenu);
            scrollPanelMapList    = new JScrollPane();
            scrollPanelGameList   = new JScrollPane();
            listMap               = new JList();
            listGame              = new JList();
            sliderSpeed           = new JSlider(JSlider.HORIZONTAL, 5, 50, 25);
            textFieldCurrentSpeed = new JTextArea();
        }

        //  MainMenu
        private void createMainMenu(){

            panelMainMenu.setLayout(null);
            panelCont.add(panelMainMenu, "panelMainMenu");

            //  Play button
            buttonPlay.setFont(new Font("URW Chancery L", 0, 32));
            buttonPlay.setText("Play");
            buttonPlay.setBounds(30, 150, 200, 55);
            buttonPlay.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    buttonActionPlay(evt);
                }
            });
            panelMainMenu.add(buttonPlay);

            //  Options button
            buttonOptions.setFont(new Font("URW Chancery L", 0, 32));
            buttonOptions.setText("Options");
            buttonOptions.setBounds(30, 275, 200, 55);
            panelMainMenu.add(buttonOptions);

            //  Exit button
            buttonExit.setFont(new Font("URW Chancery L", 0, 32));
            buttonExit.setText("Exit");
            buttonExit.setBounds(30, 400, 200, 55);
            buttonExit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    buttonActionExit(evt);
                }
            });
            panelMainMenu.add(buttonExit);

            //  Sets the background
            labelMenuBackground.setIcon(new ImageIcon(getClass().getResource("/pic/menu_background1.png")));
            labelMenuBackground.setBounds(0, -10, 922, 542);
            panelMainMenu.add(labelMenuBackground);
        }

        //  PlayMenu
        private void createPlayMenu(){
            panelPlayMenu.setLayout(null);
            panelCont.add(panelPlayMenu, "panelPlayMenu");

            //  List maps
            labelMaps.setBackground(new Color(-1,true));
            labelMaps.setOpaque(true);
            labelMaps.setBounds(30, 30, 381, 25);
            labelMaps.setText(" Maps:");
            panelPlayMenu.add(labelMaps);
            scrollPanelMapList.setViewportView(listMap);

            //  List current games
            labelGames.setBackground(new Color(-1,true));
            labelGames.setOpaque(true);
            labelGames.setBounds(503, 30, 381, 25);
            labelGames.setText(" Games:");
            panelPlayMenu.add(labelGames);
            scrollPanelGameList.setViewportView(listGame);

            //  Sets the Lists
            panelPlayMenu.setLayout(listLayout);
            listLayout.setHorizontalGroup(
                listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(listLayout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addGroup(listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(scrollPanelMapList, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(92, 92, 92)
                    .addComponent(scrollPanelGameList, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(160, Short.MAX_VALUE))
            );
            listLayout.setVerticalGroup(
                listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(listLayout.createSequentialGroup()
                    .addGap(55, 55, 55)
                    .addGroup(listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(scrollPanelGameList, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(scrollPanelMapList, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    )
                    .addContainerGap(0, Short.MAX_VALUE))
            );

            //  Slider
            labelSliderValues.setBackground(new Color(-1,true));
            labelSliderValues.setOpaque(true);
            labelSliderValues.setText("0.5                          2.5                                   5");
            labelSliderValues.setBounds(30, 305, 300, 10);
            panelPlayMenu.add(labelSliderValues);
            sliderSpeed.setBounds(30, 315,300,30);
            sliderSpeed.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    textFieldCurrentSpeed.setText("\n Speed: "+(sliderSpeed.getValue()/10)+"."+(sliderSpeed.getValue()%10));
                    gameSpeed = Double.parseDouble((sliderSpeed.getValue()/10)+"."+(sliderSpeed.getValue()%10));
                    System.out.println(gameSpeed);
                }
            });
            panelPlayMenu.add(sliderSpeed);

            //sliderSpeed.getValue()/10)+"."+(sliderSpeed.getValue()%10)  Text area with the current speed
            textFieldCurrentSpeed.setText("\n Speed: "+(sliderSpeed.getValue()/10)+"."+(sliderSpeed.getValue()%10));
            textFieldCurrentSpeed.setBounds(330, 305, 81, 40);
            textFieldCurrentSpeed.setEditable(false);
            panelPlayMenu.add(textFieldCurrentSpeed);

            //  Back button
            buttonBack.setFont(new Font("URW Chancery L", 0, 32));
            buttonBack.setText("Main menu");
            buttonBack.setBounds(361, 425, 200, 55);
            buttonBack.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    buttonActionMainMenu(evt);
                }
            });
            panelPlayMenu.add(buttonBack);

            //  Start new game button
            buttonNewGame.setFont(new Font("URW Chancery L", 0, 32));
            buttonNewGame.setText("New Game");
            buttonNewGame.setBounds(125, 350, 200, 55);
            buttonNewGame.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    buttonActionNewGame(evt);
                }
            });
            panelPlayMenu.add(buttonNewGame);

            //  Connect to running game button
            buttonConnect.setFont(new Font("URW Chancery L", 0, 32));
            buttonConnect.setText("Connect");
            buttonConnect.setBounds(600, 350, 200, 55);
            buttonConnect.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    buttonActionConnect(evt);
                }
            });
            panelPlayMenu.add(buttonConnect);

            //  Sets the background
            labelPlayBackground.setIcon(new ImageIcon(getClass().getResource("/pic/menu_background.gif")));
            labelPlayBackground.setBounds(0, -10, 922, 542);
            panelPlayMenu.add(labelPlayBackground);
        }

        //  GameScree
        private void createGameScreen(){
            panelGameScreen.setLayout(null);
            panelCont.add(panelGameScreen, "panelGameScreen");
        }
        @SuppressWarnings("unchecked")
        private void buttonActionPlay(java.awt.event.ActionEvent evt) {
            /** Ati */
            //establish connection, expected = list of maps, list of running games

            //strListMap = new String[strings.length]; -- Change "strings" to the string array containig the maps returned by the server after connection
            //System.arraycopy(strings,0,strListMap,0,strings.length-1); -||-
            //strListGame = new String[strings.length]; -- Change "strings" to the string array containig the running games returned by the server after connection
            //System.arraycopy(strings,0,strListGame,0,strings.length-1); -||-

            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };// delete
            strListMap = new String[strings.length];// delete
            System.arraycopy(strings,0,strListMap,0,strings.length); // delete
            strListGame = new String[strings.length];// delete
            System.arraycopy(strings,0,strListGame,0,strings.length); // delete
            /***/

             // Don't touch or change location of the rest of the code in this method !!!
            listMap.setModel(new javax.swing.AbstractListModel() {
                public int getSize() { return strListMap.length; }
                public Object getElementAt(int i) { return strListMap[i]; }
            });
            listGame.setModel(new javax.swing.AbstractListModel() {
                public int getSize() { return strListGame.length; }
                public Object getElementAt(int i) { return strListGame[i]; }
            });
            layout.show(panelCont, "panelPlayMenu");
        }

        private void buttonActionExit(java.awt.event.ActionEvent evt) {
            System.exit(0);
        }

        private void buttonActionMainMenu(java.awt.event.ActionEvent evt) {
            layout.show(panelCont, "panelMainMenu");
        }

         private void buttonActionNewGame(java.awt.event.ActionEvent evt) {
            if(listMap.getSelectedIndex() != -1 && strListMap[listMap.getSelectedIndex()] != null){
                setExtendedState(JFrame.ICONIFIED);
                /** Ati */
                //strListMap[listMap.getSelectedIndex()] -- The chosen map from the list (string)
                //start a new game Ati magic
                //new GamePlay(x,y).setVisible(true); -- Change x and y (map size) + add connection information if need (also change class constructor)
                new GamePlay(25,25).setVisible(true);// delete
                /***/
            }
            setVisible(true);
            //layout.show(panelCont, "panelMainMenu");
        }

        private void buttonActionConnect(java.awt.event.ActionEvent evt) {
            if(listGame.getSelectedIndex() != -1 && strListGame[listMap.getSelectedIndex()] != null){
                setExtendedState(JFrame.ICONIFIED);
                /** Ati */
                //strListGame[listGame.getSelectedIndex()] -- The chosen game from the list (string)
                //connect to a running game Ati magic
                //new GamePlay(x,y).setVisible(true); -- Change x and y (map size) + add connection information if need (also change class constructor)
                new GamePlay(25, 25).setVisible(true);// delete
            }
        }
    }
