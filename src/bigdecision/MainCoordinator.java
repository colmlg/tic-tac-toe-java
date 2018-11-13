/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bigdecision;

import javax.swing.JFrame;

/**
 *
 * @author colmlegear
 */
public class MainCoordinator {
    private JFrame currentFrame;
    
    public void goToLogin() {
        LoginJFrame login = new LoginJFrame(this);
        setCurrentFrame(login);
    }
    
    public void goToRegister() {
        RegisterJFrame register = new RegisterJFrame(this);
        setCurrentFrame(register);
    }
    
    public void goToMain(int userId) {
        System.out.println("TODO: Implement main screen. userId:" + userId);
    }
    
    private void setCurrentFrame(JFrame frame) {
        if (frame != null) {
            frame.setVisible(true);
        }
        
        if (currentFrame != null) {
            currentFrame.setVisible(false);
            currentFrame.dispose();
        }
        
        currentFrame = frame;
    }
}
