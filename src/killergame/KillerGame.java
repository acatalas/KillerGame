/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package killergame;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import killergame.connections.VisualHandler;
import killergame.connections.KillerPad;
import killergame.connections.KillerServer;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Ale
 */
public class KillerGame extends JFrame {

    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 600;
    private static final String TITLE = "KillerGame";

    //Game screen objects
    private Viewer viewer;
    private JPanel gamePanel;
    
    //Config screen objects
    private JFrame configFrame;
    private JPanel configPanel;
    private JTextField previousPortField;
    private JTextField nextPortField;
    private JTextField previousIpField;
    private JTextField nextIpField;
    private JButton startButton;
    
    //Connection objects
    private KillerServer killerServer;
    private KillerPad killerPad;
    private VisualHandler previousKiller;   //left
    private VisualHandler nextKiller;       //right
    private int thisPort;
    
    private ArrayList<KillerPad> killerPads = new ArrayList();
    //Game objects
    private List<VisibleObject> objects;

    public KillerGame() {
        showPortDialog();
        initializeGameScreen();
        initializeConfigurationScreen();
        
        killerPads = new ArrayList<>();
        previousKiller = new VisualHandler(this);
        nextKiller = new VisualHandler(this);
        
        addVisibleObjects();
        startAliveObjects();
        
        killerServer = new KillerServer(this, thisPort);
        new Thread(viewer).start();
        new Thread(killerServer).start();
        new Thread(previousKiller).start();
        new Thread(nextKiller).start();
    }

    private void initializeGameScreen() {
        setTitle(TITLE);
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addGameComponents();
        setVisible(true);
    }

    private void initializeConfigurationScreen() {
        configFrame = new JFrame(KillerGame.TITLE);
        configFrame.setSize(300, 200);
        configPanel = (JPanel)configFrame.getContentPane();
        configPanel.setLayout(new GridBagLayout());
        
        addConfigurationComponents();
        
        configFrame.setVisible(true);
    }
    
    private void showPortDialog(){
        thisPort = Integer.valueOf(JOptionPane.showInputDialog(this, "This device's port"));
    }

    private void addConfigurationComponents() {
        previousPortField = new JTextField("6666");
        nextPortField = new JTextField("6666");
        previousIpField = new JTextField("localhost");
        nextIpField = new JTextField("localhost");
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(previousKiller.getIp() == null && previousKiller.getPort() == 0){
                    previousKiller.setIp(previousIpField.getText());
                    previousKiller.setPort(Integer.valueOf(previousPortField.getText()));
                }
                if(nextKiller.getIp() == null && nextKiller.getPort() == 0){
                    nextKiller.setIp(nextIpField.getText());
                    nextKiller.setPort(Integer.valueOf(nextPortField.getText()));
                }
            }
        });
        configPanel.add(new JLabel("Configure the left computer"),
                new GridBagConstraints(0, 0, 4, 1, 1.0, 0.0, 
                        GridBagConstraints.LINE_START,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(10, 5, 0, 0), 0, 0));
        configPanel.add(new JLabel("Port: "),
                new GridBagConstraints(0, 1, 1, 1, 0.0, 0.2, 
                        GridBagConstraints.LINE_START,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 5, 5), 0, 0));
        configPanel.add(previousPortField,
                new GridBagConstraints(1, 1, 1, 1, 0.0, 0.2, 
                        GridBagConstraints.LINE_START,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(0, 0, 0, 0), 0, 0));
        configPanel.add(new JLabel("IP: "),
                new GridBagConstraints(2, 1, 1, 1, 0.0, 0.2,
                        GridBagConstraints.LINE_START,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 5, 5), 0, 0));
        configPanel.add(previousIpField,
                new GridBagConstraints(3, 1, 1, 1, 0.0, 0.2, 
                        GridBagConstraints.LINE_START,
                        GridBagConstraints.HORIZONTAL, 
                        new Insets(0, 0, 0, 5), 0, 0));
        configPanel.add(new JLabel("Configure the right computer"),
                new GridBagConstraints(0, 2, 4, 1, 0.0, 0.0,
                        GridBagConstraints.LINE_START,
                        GridBagConstraints.HORIZONTAL, 
                        new Insets(0, 5, 0, 0), 0, 0));
        configPanel.add(new JLabel("Port: "),
                new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, 
                        GridBagConstraints.LINE_START,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 5, 5), 0, 0));
        configPanel.add(nextPortField,
                new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, 
                        GridBagConstraints.LINE_START,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(0, 0, 0, 0), 0, 0));
        configPanel.add(new JLabel("IP: "),
                new GridBagConstraints(2, 3, 1, 1, 0.0, 0.2,
                        GridBagConstraints.LINE_START,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 5, 5), 0, 0));
        configPanel.add(nextIpField,
                new GridBagConstraints(3, 3, 1, 1, 0.0, 0.2, 
                        GridBagConstraints.LINE_START,
                        GridBagConstraints.HORIZONTAL, 
                        new Insets(0, 0, 0, 5), 0, 0));
        configPanel.add(startButton,
                new GridBagConstraints(0, 4, 4, 1, 0.0, 0.0,
                        GridBagConstraints.LINE_START, 
                        GridBagConstraints.HORIZONTAL,
                        new Insets(0, 0, 0, 0), 0, 0));
        
    }

    private void addGameComponents() {
        viewer = new Viewer(this);
        add(viewer);
    }

    private void addVisibleObjects() {
        objects = new ArrayList<>();
        //Adds meteors
        /*for (int i = 0; i < 10; i++) {
        objects.add(new KillerMeteor(this, ImageIO.read(new File("meteor.png")),
        (int) Math.floor(Math.random() * 700),
        (int) Math.floor(Math.random() * 600),
        2,
        2));
        }*/
        
        //Adds KillerWall
        objects.add(new KillerWall(this, Color.BLACK, 0, -10.0, SCREEN_WIDTH, 10));
        objects.add(new KillerWall(this, Color.BLACK, 0, SCREEN_HEIGHT, SCREEN_WIDTH, 10));
    }
    
    public void removeVisibleObject(VisibleObject object){
        objects.remove(object);
    }
    
    public void addVisibleObject(VisibleObject object) {
        objects.add(object);
    }
    
    public List<VisibleObject> getVisibleObjects() {
        return objects;
    }
    
    public void addKillerPad(KillerPad killerPad){
        killerPads.add(killerPad);
    }
    
    public void removeKillerPad(KillerPad killerPad){
        killerPads.remove(killerPad);
    }
    
    public List<KillerPad> getKillerPads(){
        return killerPads;
    }
    
    public VisualHandler getPreviousKiller(){
        return previousKiller;
    }
    
    public VisualHandler getNextKiller(){
        return nextKiller;
    }
    
    public int getPort(){
        return thisPort;
    }
    
    public KillerShip getKillerShip(String ip){
        KillerShip killerShip = null;
        for(int i = 0; i < objects.size(); i++){
            if(objects.get(i) instanceof KillerShip){
                if((objects.get(i) instanceof KillerShip) 
                        && ((KillerShip)objects.get(i)).getIp().equalsIgnoreCase(ip))
                    killerShip = (KillerShip)objects.get(i);
                }
            }
        return killerShip;
    }

    private void startAliveObjects() {
        for (VisibleObject object : objects) {
            if (object instanceof Alive) {
                new Thread((Alive) object).start();
            }
        }
    }
    
    public void testCollision(VisibleObject object) {
        if(object instanceof KillerShip){
            if(object.getY() <= 0 | object.getY() >= SCREEN_HEIGHT - (object.getHeight()*2)){
                ((KillerShip) object).bounce();
            }
            if(object.getX() <= 0){
                previousKiller.sendShip((KillerShip) object, 0 , object.getY());
                object.die();
            }
            if(object.getY() >= SCREEN_WIDTH - (object.getWidth() * 2)){
                nextKiller.sendShip((KillerShip) object, 
                        SCREEN_WIDTH - object.getWidth(), object.getY());
                object.die();
            }
        }
        
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i) != object) {
                KillerRules.testCollision(object, objects.get(i));
            }
        }
    }

    public static void main(String[] args) {
        KillerGame killerGame = new KillerGame();
    }

}