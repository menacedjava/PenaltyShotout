package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PenaltyShootout extends JPanel implements ActionListener, KeyListener {
    private static final int WIDTH = 800, HEIGHT = 500;
    private static final int BALL_SIZE = 30, GOAL_WIDTH = 300, GOAL_HEIGHT = 100;
    private static final int KEEPER_WIDTH = 50, KEEPER_HEIGHT = 80;

    private int ballX = WIDTH / 2 - BALL_SIZE / 2, ballY = HEIGHT - 100;
    private int keeperX = WIDTH / 2 - KEEPER_WIDTH / 2, keeperY = 50;
    private int direction = 0; // -1 (chap), 1 (o'ng), 0 (to'g'ri)
    private boolean shotTaken = false, goal = false, saved = false;

    private Timer timer;

    public PenaltyShootout() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.GREEN);
        this.setFocusable(true);
        this.addKeyListener(this);

        timer = new Timer(20, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        g.setColor(Color.WHITE);
        g.fillRect(WIDTH / 2 - GOAL_WIDTH / 2, keeperY, GOAL_WIDTH, GOAL_HEIGHT);


        g.setColor(Color.BLUE);
        g.fillRect(keeperX, keeperY, KEEPER_WIDTH, KEEPER_HEIGHT);


        g.setColor(Color.BLACK);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);


        g.setFont(new Font("Arial", Font.BOLD, 30));
        if (goal) {
            g.setColor(Color.YELLOW);
            g.drawString("GOAL!", WIDTH / 2 - 50, HEIGHT / 2);
        } else if (saved) {
            g.setColor(Color.RED);
            g.drawString("SAVED!", WIDTH / 2 - 60, HEIGHT / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (shotTaken) {
            ballY -= 10;


            if (ballY <= keeperY + KEEPER_HEIGHT && ballX + BALL_SIZE >= keeperX && ballX <= keeperX + KEEPER_WIDTH) {
                saved = true;
                shotTaken = false;
            }


            if (ballY <= keeperY && !saved) {
                goal = true;
                shotTaken = false;
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!shotTaken) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT && ballX > WIDTH / 2 - GOAL_WIDTH / 2) {
                ballX -= 20;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT && ballX < WIDTH / 2 + GOAL_WIDTH / 2 - BALL_SIZE) {
                ballX += 20;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                shotTaken = true;
                goal = false;
                saved = false;


                keeperX = WIDTH / 2 - KEEPER_WIDTH / 2 + (Math.random() > 0.5 ? -50 : 50);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Penalty Shootout");
        PenaltyShootout game = new PenaltyShootout();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}