package gui;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rodney
 */
import impsoft.utils.general.Timer;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class UserInterfaceThread extends Thread implements Runnable {
	private JFrame mainFrame;
	private JProgressBar mainBar;
	private boolean pause;
	private Timer finish;

	public UserInterfaceThread(JFrame frame, JProgressBar bar, Timer finish) {
		this.mainFrame = frame;
		this.mainBar = bar;
		this.finish = finish;
	}

	@Override
	public void run() {
		mainBar.setValue(1);

		while (mainFrame != null && mainFrame.isVisible()) {
			try {
				if(finish.isUp()) {
					break;
				}
				
				if (!pause) {
					if (mainBar.getValue() == 100) {
						mainBar.setValue(1);
					}

					mainBar.setValue(mainBar.getValue() + 2);
				}

				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void setPaused(boolean pause) {
		this.pause = pause;
	}
}
