/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processing;

/**
 *
 * @author makafui
 */
public class Loop extends Thread {
    
    private long loopCount;

    public Loop(String loopCount) {
        try {
            this.loopCount = Long.parseLong(loopCount);
            
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormatException: " + nfe.getMessage());
        }
    }
    
    @Override
    public void run() {
        loop();
    }
    
    private void loop() {
        for (int i = 0; i < loopCount; i++) {
            // Empty Iteration
            System.out.println("Thread running at number" + i);
        }
    }

}
