package base;

import impsoft.bots.input.MouseCommand;
import impsoft.bots.reflection.WorldObject;
import impsoft.scripting.ibot.structs.RGB;
import impsoft.scripting.ibot.structs.XY;
import impsoft.scripting.types.ColorSkeltonScriptable;
import impsoft.scripting.types.parallel.scriptjobs.ScriptJob;
import impsoft.utils.general.Timer;
import impsoft.utils.ibot.ColorMapAnaylsis;
import impsoft.values.constant.Areas;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.regex.Pattern;

/**
 * 
 * @author Bradsta
 * 
 *         Credits: Smitteh, Vector
 * 
 *         Clicker V1.0
 * 
 *         Clicker click = new Clicker(this);
 * 
 */
public class Clicker extends DreamUtility {

    public Clicker(DreamScript cs) {
	super(cs);
    }

    /**
     * PLEASE! Set object dimensions before attempting to use this method! You
     * may do this by using this code sample -
     * WorldObject.setObjectDimensions(Dimensions here);
     * 
     * @param wo
     *            - WorldObject to be clicked
     * @param actionRegex
     *            - the action we will be looking for
     * @param walkIfNeeded
     *            - set true if you want the method to walk if wo is not on
     *            screen
     * @throws InterruptedException
     *             - because that's how it is
     */
    public boolean clickWorldObject(WorldObject wo, Pattern actionRegex,
	    boolean walkIfNeeded) throws InterruptedException {
	if (wo != null) {
	    if (wo.getGameScreenLocation() == null) {
		cs.theCamera.setCameraToTile(wo.getLocation(), 3);
	    }
	    if (wo.getLocation().getCenterGameScreenLocation(cs) == null
		    && walkIfNeeded) {
		ScriptJob sj = cs.theParallelWalking.walkToTile(wo
			.getLocation());
		Timer t = new Timer(10000);
		while (!sj.isDone()
			&& wo.getLocation().getCenterGameScreenLocation(cs) == null
			&& wo.getLocation().distanceTo(cs.getLocation()) < 20
			&& !t.isUp()) {
		    cs.sleep(cs.random(200, 300));
		}
		sj.forceKill();
		cs.sleep(cs.random(400, 600));
	    }
	    XY move = wo.getRecommendPointToClick();
	    if (move == null)
		return false;
	    MouseCommand mc = MouseCommand.Move(Thread.MAX_PRIORITY,
		    Thread.currentThread(), move.x, move.y);
	    cs.enterCommand(mc, false);
	    for (int i = 0; i < 5 && wo != null
		    && wo.getRecommendPointToClick() != null
		    && !isInPolygon(wo.getGameScreenLocation()); i++) {
		int x = move.x;
		int y = move.y;
		if ((move = wo.getRecommendPointToClick()) != null
			&& !move.equals(new XY(x, y))) {
		    x = move.x;
		    y = move.y;
		    mc.changeDestination(move.x, move.y);
		}
		cs.sleep(cs.random(80, 100));
	    }
	    if (cs.theTopText.isTopTextContaining(actionRegex)) {
		cs.mouseClickLeft(cs.getCurrentMouseXY());
		return waitForRed();
	    } else if (wo.getGameScreenLocation() != null) {
		cs.mouseClickRight(cs.getCurrentMouseXY());
		for (int w = 0; w < 5 && !cs.theMenuFinder.isMenuUp(); w++)
		    cs.sleep(100);
		if (cs.theMenuFinder.doMenuContains(actionRegex)) {
		    return waitForRed();
		}
	    }
	}
	return false;
    }

    /**
     * EVERYTHING BEYOND THIS POINT IS PRIVATE
     */

    // CREDITS THATSUPERFLY
    private boolean waitForRed() throws InterruptedException {
	Timer xT = new Timer(200);
	try {
	    while (xT.isNotUp()) {
		XY clickedAt = cs.getCurrentMouseXY();
		if (Areas.GAME.contains(clickedAt.x, clickedAt.y)) {
		    int x = clickedAt.x - 5;
		    int y = clickedAt.y - 5;
		    Rectangle areaClicked = new Rectangle(x, y, 20, 20);
		    if (cs.getColorMap() == null || cs.getColorMap().length < 1
			    || areaClicked == null) {
			return false;
		    }
		    boolean red = (ColorMapAnaylsis.findRGB(cs.getColorMap(),
			    new RGB(254, 0, 0), areaClicked) != null);
		    if (red)
			return red;
		}
		cs.waitTillNextFrame();
	    }
	} catch (Exception e) {
	    return false;
	}
	return false;
    }

    private boolean isInPolygon(Polygon gameLoc) {
	return gameLoc.contains(new Point(cs.getCurrentMouseX(), cs
		.getCurrentMouseY()));
    }
}
