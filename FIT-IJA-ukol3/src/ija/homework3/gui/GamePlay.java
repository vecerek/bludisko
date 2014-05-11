    package ija.homework3.gui;

    import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import ija.homework3.client.Client;

    /**
     * Object responsible for the gameplay.
     */
    public class GamePlay extends javax.swing.JFrame {

        /**  Varibale declaration */
        private JLabel[][] labelField;
        private JLabel[][] labelFieldArrow;
        private JLabel labelTexture;
        private JLabel labelBackground;
        private JLabel labelCommandLine;
        private JLabel labelHistory;
        private JLabel labelCommandList;
        private JPanel panelCont;
        private JTextArea textAreaHistory;
        private JTextArea textAreaCommandList;
        private JTextField textFieldCommandLine;
        private String strFormat;
        private String strInputCommand;
        private String[] strHistory;
        private String strMap;
        private int fieldX;
        private int fieldY;
        
        private Client client;

        /**  Constructor  */
        public GamePlay(Client client, int x, int y) {

            super("The Labyrinth");
            this.client = client;
            
            setResizable(false);
            setSize(1100,730);

            initComponents(x, y);

            // Left side of the panel with the map
            Game();

            //  Right side of the panel with the command hints, history, and command line
            Command();
        }

        /**  Methods  */
        //  Initialization
        private void initComponents(int x, int y) {
            fieldX = x;
            fieldY = y;
            labelField = new JLabel[fieldX][fieldY];
            for (int i = 0; i < fieldX; i++) {
                for(int j = 0; j < fieldY; j++){
                    labelField[i][j] = new JLabel();
                }
            }
            labelFieldArrow = new JLabel[fieldX][fieldY];
            for (int i = 0; i < fieldX; i++) {
                for(int j = 0; j < fieldY; j++){
                    labelFieldArrow[i][j] = new JLabel();
                }
            }
            labelTexture = new JLabel();
            labelBackground = new JLabel();
            labelCommandLine = new JLabel();
            labelHistory = new JLabel();
            labelCommandList = new JLabel();
            panelCont = new JPanel();
            textAreaHistory = new JTextArea();
            textAreaCommandList = new JTextArea();
            textFieldCommandLine = new JTextField();
            strFormat = new String();
            strInputCommand = new String();
            strHistory = new String[10];
            strMap = new String();
            for (int i = 0; i < 10; i++) {
                strHistory[i] = new String();
            }
            strHistory[0] = "----------------------------------------------------------------------------------\n";
            strHistory[1] =  "\n";
            strHistory[2] =  "\n";
            strHistory[3] =  "\n";
            strHistory[4] =  "\n";
            strHistory[5] =  "\n";
            strHistory[6] =  "\n";
            strHistory[7] =  "\n";
            strHistory[8] =  "\n";
            strHistory[9] =  "\n";

            int tmp;
            if (fieldX > fieldY) tmp = fieldX;
            else tmp = fieldY;
            if(tmp == 20)                   strFormat = "35";
            else if(tmp > 20 && tmp <= 30)  strFormat = "23";
            else if(tmp > 30 && tmp <= 40)  strFormat = "17";
            else if(tmp > 40 && tmp <= 50)  strFormat = "14";
        }

        //  Game - responsible of getting the latest game state from server
        private void Game() {
        /** Ati */
        //delete both Strmaps
        // strMap = "wwwwwwwwwwwwwwwwwwwwwwwwwppwp_pppppppppppppwpppppww>3w_wwwwwwwwwpwwwpwpwpwpwwpwppppppkwpwpppwkwpwkpwwpwwwwwwwwwpwgwwwwwpwwwpwwpppppppwpppppppwpppppwppwwwwwwwpwpwwwpwwwwwpwwwwwwkpppkwpwpwkppwpppwpppppwwwwpwwwpwpwwwpwwwpwwwwwpwwpppwpppppwpwpppwpppppwpwwwwpwwwwwwwwwwwpwpwwwpwpwwpppppppwpppppwpppwpppppwwwwwwgwwwwwpwwwwwwwwwwwgwwpppppppppwpppwpppwpppppwwpwwwwwgwwwpwgwpwpwpwwwwwwpwpwkwpwkwpwpppwpwpppppwwpwpwpwpwpwpwwwwwpwwwpwwwwpwpppwpwpppppwpppppwpwkwwpwpwwwpwwwwwpwpwwwwwpwpwwpwpwppppppkwpwpppppwpppwwpwpwwwwwwwwwpwwwwwgwpwwwwpppppppwpppppwpgpwpppppwwwwwwwwpwpwwwwwpwpwwwwwwwwpppppppgpgpppppwpppppppfwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwppwp_pppppppppppppwpppppww>3w_wwwwwwwwwpwwwpwpwpwpwwpwppppppkwpwpppwkwpwkpwwpwwwwwwwwwpwgwwwwwpwwwpwwpppppppwpppppppwpppppwppwwwwwwwpwpwwwpwwwwwpwwwwwwkpppkwpwpwkppwpppwpppppwwwwpwwwpwpwwwpwwwpwwwwwpwwpppwpppppwpwpppwpppppwpwwwwpwwwwwwwwwwwpwpwwwpwpwwpppppppwpppppwpppwpppppwwwwwwgwwwwwpwwwwwwwwwwwgwwpppppppppwpppwpppwpppppwwpwwwwwgwwwpwgwpwpwpwwwwwwpwpwkwpwkwpwpppwpwpppppwwpwpwpwpwpwpwwwwwpwwwpwwwwpwpppwpwpppppwpppppwpwkwwpwpwwwpwwwwwpwpwwwwwpwpwwpwpwppppppkwpwpppppwpppwwpwpwwwwwwwwwpwwwwwgwpwwwwpppppppwpppppwpgpwpppppwwwwwwwwpwpwwwwwpwpwwwwwwwwpppppppgpgpppppwpppppppfwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwppwp_pppppppppppppwpppppww>3w_wwwwwwwwwpwwwpwpwpwpwwpwppppppkwpwpppwkwpwkpwwpwwwwwwwwwpwgwwwwwpwwwpwwpppppppwpppppppwpppppwppwwwwwwwpwpwwwpwwwwwpwwwwwwkpppkwpwpwkppwpppwpppppwwwwpwwwpwpwwwpwwwpwwwwwpwwpppwpppppwpwpppwpppppwpwwwwpwwwwwwwwwwwpwpwwwpwpwwpppppppwpppppwpppwpppppwwwwwwgwwwwwpwwwwwwwwwwwgwwpppppppppwpppwpppwpppppwwpwwwwwgwwwpwgwpwpwpwwwwwwpwpwkwpwkwpwpppwpwpppppwwpwpwpwpwpwpwwwwwpwwwpwwwwpwpppwpwpppppwpppppwpwkwwpwpwwwpwwwwwpwpwwwwwpwpwwpwpwppppppkwpwpppppwpppwwpwpwwwwwwwwwpwwwwwgwpwwwwpppppppwpppppwpgpwpppppwwwwwwwwpwpwwwwwpwpwwwwwwwwpppppppgpgpppppwpppppppfwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwppwp_pppppppppppppwpppppww>3w_wwwwwwwwwpwwwpwpwpwpwwpwppppppkwpwpppwkwpwkpwwpwwwwwwwwwpwgwwwwwpwwwpwwpppppppwpppppppwpppppwppwwwwwwwpwpwwwpwwwwwpwwwwwwkpppkwpwpwkppwpppwpppppwwwwpwwwpwpwwwpwwwpwwwwwpwwpppwpppppwpwpppwpppppwpwwwwpwwwwwwwwwwwpwpwwwpwpwwpppppppwpppppwpppwpppppwwwwwwgwwwwwpwwwwwwwwwwwgwwpppppppppwpppwpppwpppppwwpwwwwwgwwwpwgwpwpwpwwwwwwpwpwkwpwkwpwpppwpwpppppwwpwpwpwpwpwpwwwwwpwwwpwwwwpwpppwpwpppppwpppppwpwkwwpwpwwwpwwwwwpwpwwwwwpwpwwpwpwppppppkwpwpppppwpppwwpwpwwwwwwwwwpwwwwwgwpwwwwpppppppwpppppwpgpwpppppwwwwwwwwpwpwwwwwpwpwwwwwwwwpppppppgpgpppppwpppppppfwwwwwwwwwwwwwwwwwwwwwwwwww";
        // strMap = "wwwwwwwwwwwwwwwwwwwwwppkwpppppwppppppppwwpwwwpwpwwwwwwpwwwpwppppppwphpwppppwpppwwwwwwwwwwpwpwwwwwwgwwpv1pppppwpppwp^4ppppwwpwwwwwpwwwwwpwwwwpww>3ppwpwpppwppppppwpwwpwpwkwwwgwpwwwwpwpwwpwpwpppwppppppppwkwwwwpwwwpwwwwwwwwwwwwwpppwpppgppppppwppkwwpwpwpwwwpwpwwwwwpwwwpwpwpwpppwppppppppwwpwwwpwwwwwwwwwwwwpwwpppppwppppppppwpppwwwwgwwwpwwwwgwwwwwpwwkkpwpppwppppppppppwwwwwwwwpwwwwwwwwwwwwwpppppppgppppppppppf";
        /***/
            panelCont.setLayout(null);
            getContentPane().setLayout(null);
            getContentPane().add(panelCont);

            labelTexture.setIcon(new ImageIcon(getClass().getResource("/pic/ground.jpg")));
            labelTexture.setBounds(0,0,700,700);
            panelCont.setBounds(0,0,1100,700);

            for (int i = 0; i < fieldX; i++) {
                for(int j = 0; j < fieldY; j++) {
                    panelCont.add(labelField[i][j]);
                    panelCont.add(labelFieldArrow[i][j]);
                }
            }
            /** Ati */
            // Use a While loop until finish message, server is expected to send back the current map or feedback.
            // 1) Set "strMap" to this new map. Use "paintField" to refresh the gui.
            // 2) Use "historyAddLine" to put the message to the history area
            
            paintField();
            /***/
            panelCont.add(labelTexture);

        }
        
        private void Command() {
            //  List of commands
            labelCommandList.setBackground(new Color(-1,true));
            labelCommandList.setOpaque(true);
            labelCommandList.setBounds(730, 30, 330, 25);
            labelCommandList.setText(" Command list:");
            panelCont.add(labelCommandList);
            textAreaCommandList.setText("----------------------------------------------------------------------------------\n"+"    step    - player makes one step"+"\n"+"    go       - player makes several steps, until stopped"+"\n"+"    stop    - stops the player"+"\n"+"    left      - player turns left"+"\n"+"    right    - player turns right"+"\n"+"    take    - player takes the key in front of him"+"\n"+"    open   - player opens the gate in front of him"+"\n"+"    keys    - number of keys owned by player");
            textAreaCommandList.setBounds(730, 55, 330, 150);
            textAreaCommandList.setEditable(false);
            panelCont.add(textAreaCommandList);

            //  History
            labelHistory.setBackground(new Color(-1,true));
            labelHistory.setOpaque(true);
            labelHistory.setBounds(730, 325, 330, 25);
            labelHistory.setText("History:");
            panelCont.add(labelHistory);
            textAreaHistory.setText(strHistory[0]+strHistory[1]+strHistory[2]+strHistory[3]+strHistory[4]+strHistory[5]+strHistory[6]+strHistory[7]+strHistory[8]+strHistory[9]);
            textAreaHistory.setBounds(730, 350, 330, 150);
            textAreaHistory.setEditable(false);
            panelCont.add(textAreaHistory);

            //  Command line
            labelCommandLine.setBackground(new Color(-1,true));
            labelCommandLine.setOpaque(true);
            labelCommandLine.setBounds(730, 620, 330, 25);
            labelCommandLine.setText("Write your commands here:");
            panelCont.add(labelCommandLine);
            textFieldCommandLine.setBounds(730, 645, 330, 25);
            textFieldCommandLine.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    textActionEntered(evt);
                }
            });
            panelCont.add(textFieldCommandLine);

            //  Background
            labelBackground.setIcon(new ImageIcon(getClass().getResource("/pic/gamePlay_background.png")));
            labelBackground.setBounds(0,0,1100,700);
            panelCont.add(labelBackground);

        }

        //  User entered a command
        private void textActionEntered(ActionEvent evt){
            this.strInputCommand = textFieldCommandLine.getText();
            this.textFieldCommandLine.setText("");
            
            /** Ati */
            // "strInputCommand" stores the command given by the user, do your stuff (Ati magic)
            this.client.send(this.strInputCommand);
            try {
            	String success = this.client.readResponse();
                if(success == "OK")
                	this.historyAddLine(this.strInputCommand + " - OK.");
                else
                	this.historyAddLine(this.strInputCommand + " - NOT OK.");
                
            } catch(IOException e) {
            	System.err.println(e.getMessage());
            	this.historyAddLine(this.strInputCommand + " - NOT OK.");
            };
            /***/
        }

        //  Add one line to the bottom of the history
        private void historyAddLine(String line){
            strHistory[1] = strHistory[2];
            strHistory[2] = strHistory[3];
            strHistory[3] = strHistory[4];
            strHistory[4] = strHistory[5];
            strHistory[5] = strHistory[6];
            strHistory[6] = strHistory[7];
            strHistory[7] = strHistory[8];
            strHistory[8] = strHistory[9];
            strHistory[9] = line + "\n";
            textAreaHistory.setText(strHistory[0]+strHistory[1]+strHistory[2]+strHistory[3]+strHistory[4]+strHistory[5]+strHistory[6]+strHistory[7]+strHistory[8]+strHistory[9]);
        }

        //  Puts the map stored in strMap to GUI
        private void paintField() {
            String tmpStrOrientation = new String();
            String tmpActChar = new String();
            for (int i = 0; i < fieldX; i++) {
                for(int j = 0; j < fieldY; j++) {
                    labelField[i][j].setBounds(j*Integer.parseInt(strFormat),i*Integer.parseInt(strFormat),Integer.parseInt(strFormat),Integer.parseInt(strFormat));
                    labelFieldArrow[i][j].setBounds(j*Integer.parseInt(strFormat),i*Integer.parseInt(strFormat),Integer.parseInt(strFormat),Integer.parseInt(strFormat));
                    tmpActChar = strMap.split("")[i*fieldX+j+1];

                    //  strMap parsing
                    if(tmpActChar.equals("w"))
                        labelField[i][j].setIcon(new ImageIcon(getClass().getResource("/pic/bush_"+strFormat+".png")));
                    else if(tmpActChar.equals("g")){
                        if(strMap.split("")[i*fieldX+j+2].equals("w")) tmpStrOrientation = "H";
                        else tmpStrOrientation = "V";
                        labelField[i][j].setIcon(new ImageIcon(getClass().getResource("/pic/gate_"+tmpStrOrientation+"C"+strFormat+".png")));
                    }
                    else if(tmpActChar.equals("_")){
                        if(strMap.split("")[i*fieldX+j+2].equals("w")) tmpStrOrientation = "H";
                        else tmpStrOrientation = "V";
                        labelField[i][j].setIcon(new ImageIcon(getClass().getResource("/pic/gate_"+tmpStrOrientation+"O"+strFormat+".png")));
                    }
                    else if(tmpActChar.equals("k"))
                        labelField[i][j].setIcon(new ImageIcon(getClass().getResource("/pic/key_"+strFormat+".png")));
                    else if(tmpActChar.equals("h"))
                        /** Ati */
                        // Change thor in the image path to the picture of Loki
                        // 4x Loki pics, res.: 35x35, 23x23, 17x17, 14x14
                        // strFormat is only one number (not 14x14, but 14)
                        labelField[i][j].setIcon(new ImageIcon(getClass().getResource("/pic/loki_"+strFormat+".png")));
                        /***/
                    else if(tmpActChar.equals("f"))
                        labelField[i][j].setIcon(new ImageIcon(getClass().getResource("/pic/fin_"+strFormat+".png")));
                    else if(tmpActChar.matches(">|<|\\^|v")){
                        switch(tmpActChar) {
                            case ">": tmpStrOrientation = "east";break;
                            case "<": tmpStrOrientation = "west";break;
                            case "v": tmpStrOrientation = "south";break;
                            case "^": tmpStrOrientation = "north";break;
                        }
                        labelFieldArrow[i][j].setIcon(new ImageIcon(getClass().getResource("/pic/arrow_"+tmpStrOrientation+strFormat+".png")));
                        tmpActChar = strMap.split("")[i*fieldX+j+2];
                        switch(tmpActChar) {
                            case "1": labelField[i][j].setIcon(new ImageIcon(getClass().getResource("/pic/ironman_"+strFormat+".png")));break;
                            case "2": labelField[i][j].setIcon(new ImageIcon(getClass().getResource("/pic/captain_america_"+strFormat+".png")));break;
                            case "3": labelField[i][j].setIcon(new ImageIcon(getClass().getResource("/pic/hulk_"+strFormat+".png")));break;
                            case "4": labelField[i][j].setIcon(new ImageIcon(getClass().getResource("/pic/thor_"+strFormat+".png")));break;
                        }
                        strMap = strMap.substring(0,i*fieldX+j+2)+strMap.substring(i*fieldX+j+3);
                    }
                }
            }
        }
        
        public void rePaintField(String Map)
        {
        	this.strMap = Map;
        	this.paintField();
        }
}
