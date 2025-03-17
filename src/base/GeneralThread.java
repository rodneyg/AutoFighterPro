package base;

import impsoft.scripting.ibot.structs.XY;
import impsoft.utils.general.Timer;
import mouse.MouseTrack;

public class GeneralThread extends Thread implements Runnable {
	private DreamScript cs;
	private boolean run = true;

	public GeneralThread(String name, DreamScript cs) {
		setName(name);
		this.cs = cs;
	}

	@Override
	public void run() {
		Timer updateVariables = new Timer(1000);
		while (run) {
			if (updateVariables.isUp()) {
				updateVariables.reset();
			}

			if (cs.paintMouseTrail) {
				MouseTrack currentMouse = new MouseTrack(new XY(
						cs.getCurrentMouseX(), cs.getCurrentMouseY()));

				if (!cs.mouseTrackList.contains(currentMouse)) {
					cs.mouseTrackList.add(currentMouse);
					currentMouse = null;
				}
			}

			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stopRunning() {
		run = false;
	}
}
