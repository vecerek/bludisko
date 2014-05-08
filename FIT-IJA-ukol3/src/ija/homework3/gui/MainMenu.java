    package ija.homework3.gui;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.*;

    public class MainMenu extends javax.swing.JFrame {

        /**  Varibale declaration */
        private JPanel      panelCont;
        private JPanel      panelMainMenu;
        private JPanel      panelPlayMenu;
        private JButton     buttonPlay;
        private JButton     buttonOptions;
        private JButton     buttonExit;
        private JButton     buttonBack;
        private JButton     buttonNewGame;
        private JButton     buttonConnect;
        private JLabel      labelMenuBackground;
        private JLabel      labelPlayBackground;
        private CardLayout  layout;
        private GroupLayout listLayout;
        private JScrollPane scrollPanelMapList;
        private JScrollPane scrollPanelGameList;
        private JList       listMap;
        private JList       listGame;
        private JSlider     sliderSpeed;

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

            //  Sets the MainMenu panel as starting panel
            layout.show(panelCont, "panelMainMenu");

        }

        /**  Methods  */
        //  Initialization
        private void initComponents() {
            panelCont           = new JPanel();
            panelMainMenu       = new JPanel();
            panelPlayMenu       = new JPanel();
            buttonPlay          = new JButton();
            buttonOptions       = new JButton();
            buttonExit          = new JButton();
            buttonBack          = new JButton();
            buttonNewGame       = new JButton();
            buttonConnect       = new JButton();
            labelMenuBackground = new JLabel();
            labelPlayBackground = new JLabel();
            layout              = new CardLayout();
            listLayout          = new GroupLayout(panelPlayMenu);
            scrollPanelMapList  = new JScrollPane();
            scrollPanelGameList = new JScrollPane();
            listMap             = new JList();
            listGame            = new JList();
            sliderSpeed         = new JSlider(JSlider.HORIZONTAL, 5, 50, 25);
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
        @SuppressWarnings("unchecked")
        private void createPlayMenu(){

            panelPlayMenu.setLayout(null);
            panelCont.add(panelPlayMenu, "panelPlayMenu");

            //  List maps
            listMap.setModel(new javax.swing.AbstractListModel() {
                String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
                public int getSize() { return strings.length; }
                public Object getElementAt(int i) { return strings[i]; }
            });
            scrollPanelMapList.setViewportView(listMap);

            //  List current games
            listGame.setModel(new javax.swing.AbstractListModel() {
                String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
                public int getSize() { return strings.length; }
                public Object getElementAt(int i) { return strings[i]; }
            });
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
                    .addGap(30, 30, 30)
                    .addGroup(listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(scrollPanelGameList, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(scrollPanelMapList, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    )
                    .addContainerGap(0, Short.MAX_VALUE))
            );

            //  Slider
            sliderSpeed.setBounds(30, 305,381,30);
            panelPlayMenu.add(sliderSpeed);

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

        private void buttonActionPlay(java.awt.event.ActionEvent evt) {
            layout.show(panelCont, "panelPlayMenu");
        }

        private void buttonActionExit(java.awt.event.ActionEvent evt) {
            System.exit(0);
        }

        private void buttonActionMainMenu(java.awt.event.ActionEvent evt) {
            layout.show(panelCont, "panelMainMenu");
        }

         private void buttonActionNewGame(java.awt.event.ActionEvent evt) {
            System.out.println("NewGame");
            //layout.show(panelCont, "panelMainMenu");
        }

        private void buttonActionConnect(java.awt.event.ActionEvent evt) {
            System.out.println("Connect");
            //layout.show(panelCont, "panelMainMenu");
        }
    }
