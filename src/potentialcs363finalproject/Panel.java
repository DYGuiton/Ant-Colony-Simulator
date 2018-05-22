package potentialcs363finalproject;

import java.awt.*;
import java.util.Arrays;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Admin Mar 24, 2018 2:51:55 PM
 */
public class Panel extends javax.swing.JPanel {

    AnimationController theAC;

    public Panel() {
        initComponents();
        speedSlider.setBounds(80, 350, 200, 50);
        setLayout(null);
        setSize(700,800);
        ParameterDialog pd = new ParameterDialog();
        setAllSliders();
        reset(pd.getColonySize(), pd.getFood());
        //setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        theAC.paint(g);
        foodTF.setText("" + theAC.theWorld.theColony.getStoredFood());
    }

    public void reset(int colonySize, int food) {
        theAC = new AnimationController(this, colonySize, food);
        theAC.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton8 = new javax.swing.JButton();
        toggleStartStopButton = new javax.swing.JButton();
        foodTF = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        PheromoneViewButton = new javax.swing.JButton();
        speedTF = new javax.swing.JTextField();
        speedSlider = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        resetButton = new javax.swing.JButton();

        setLayout(null);

        jButton8.setText("Print GridWorld");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        add(jButton8);
        jButton8.setBounds(40, 400, 140, 23);

        toggleStartStopButton.setText("Toggle Running");
        toggleStartStopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleStartStopButtonActionPerformed(evt);
            }
        });
        add(toggleStartStopButton);
        toggleStartStopButton.setBounds(0, 260, 140, 23);

        foodTF.setEditable(false);
        foodTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                foodTFActionPerformed(evt);
            }
        });
        add(foodTF);
        foodTF.setBounds(80, 310, 190, 20);

        jLabel1.setText("Stored Food:");
        add(jLabel1);
        jLabel1.setBounds(0, 310, 80, 14);

        PheromoneViewButton.setText("Pheromone View Switch");
        PheromoneViewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PheromoneViewButtonActionPerformed(evt);
            }
        });
        add(PheromoneViewButton);
        PheromoneViewButton.setBounds(280, 260, 200, 23);

        speedTF.setText("24");
        add(speedTF);
        speedTF.setBounds(290, 350, 80, 20);
        add(speedSlider);
        speedSlider.setBounds(80, 350, 200, 26);

        jLabel2.setText("FPS Delay:");
        add(jLabel2);
        jLabel2.setBounds(0, 360, 80, 14);

        resetButton.setText("Reset Simulation");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });
        add(resetButton);
        resetButton.setBounds(340, 400, 140, 23);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        System.out.println("The Grid World:\n" + Arrays.deepToString(theAC.theWorld.GridWorld).replace("], ", "]\n"));
    }//GEN-LAST:event_jButton8ActionPerformed

    private void toggleStartStopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleStartStopButtonActionPerformed
        theAC.running = !theAC.running;
    }//GEN-LAST:event_toggleStartStopButtonActionPerformed

    private void foodTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_foodTFActionPerformed
        
    }//GEN-LAST:event_foodTFActionPerformed

    private void PheromoneViewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PheromoneViewButtonActionPerformed
        theAC.PheromoneViewSwitch();
    }//GEN-LAST:event_PheromoneViewButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        ParameterDialog pd = new ParameterDialog();
        setAllSliders();
        reset(pd.getColonySize(), pd.getFood());
    }//GEN-LAST:event_resetButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton PheromoneViewButton;
    private javax.swing.JTextField foodTF;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton resetButton;
    private javax.swing.JSlider speedSlider;
    private javax.swing.JTextField speedTF;
    private javax.swing.JButton toggleStartStopButton;
    // End of variables declaration//GEN-END:variables

        private void setAllSliders() {
        setSliderValue(speedSlider, speedTF, 24);
    }
    
    
    private void setSliderValue(JSlider theSlider, JTextField theTF, int max) {
        theTF.setText("1");
        theSlider.setMaximum(max);
        theSlider.setMinimum(0);

        theSlider.setMajorTickSpacing(max / 10);
        theSlider.setMinorTickSpacing(max / 1000);
        theSlider.setPaintTicks(true);
        theSlider.setPaintLabels(true);
        theSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                if (theSlider.getValueIsAdjusting()) {
                    theTF.setText("" + theSlider.getValue());
                    theAC.setFPS(Integer.parseInt(theTF.getText()));
                }
            }

        });
    }



}
