package base;

import impsoft.painting.PaintJob;
import impsoft.scripting.ibot.structs.AryanTile;
import impsoft.scripting.ibot.structs.XY;
import impsoft.scripting.types.ColorSkeltonScriptable;
import impsoft.scripting.types.parallel.scriptjobs.ScriptJob;
import impsoft.utils.general.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Walker {

	private final ColorSkeltonScriptable cs;

	public Walker(ColorSkeltonScriptable cs) {
		this.cs = cs;
	}

	private XY getMiniMapLocation(AryanTile tile) throws InterruptedException {
		XY toReturn = null;
		Rectangle myPlayerLoc = null;
		if (cs.getReflectionFields() != null
				&& cs.getReflectionFields().resizeable) {
			myPlayerLoc = new Rectangle(
					cs.getReflectionFields().MINI_MAP_CENTER_X - 1,
					cs.getReflectionFields().MINI_MAP_CENTER_Y + 1, 3, 3);
		} else {
			myPlayerLoc = new Rectangle(625, 83, 3, 3);
		}
		int currentYaw = cs.theParallelCompass.getYaw();
		if (tile != null && tile.distanceTo(cs.getLocation()) <= 30) {
			int x = (int) (myPlayerLoc.getCenterX() + ((tile.x - cs
					.getLocation().x) * 4));
			int y = (int) (myPlayerLoc.getCenterY() + ((tile.y - cs
					.getLocation().y) * -3));
			double angle = (currentYaw / 45.51111111111111);
			boolean rev = false;
			if (tile.x - cs.getLocation().x < 0)
				rev = true;
			if (tile.equals(cs.getLocation())) {
				toReturn = new XY((int) myPlayerLoc.getCenterX(),
						(int) myPlayerLoc.getCenterY());
			} else {
				toReturn = rotate(new XY(x, y),
						new XY((int) myPlayerLoc.getCenterX(),
								(int) myPlayerLoc.getCenterY()), angle, rev);
			}
		}

		return toReturn;
	}

	private XY rotate(XY point, XY center, double degrees, boolean rev) {
		double x = point.x - center.x;
		double y = point.y - center.y;
		double radius = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		double theta = Math.atan(y / x) + Math.toRadians(degrees);
		if (rev) {
			return new XY(-(int) (radius * Math.cos(theta)) + center.x,
					(int) (-(radius * Math.sin(theta)) + center.y));
		}
		return new XY((int) (radius * Math.cos(theta)) + center.x,
				(int) ((radius * Math.sin(theta)) + center.y));
	}

	private float getSlope(AryanTile point1, AryanTile point2) {
		return ((float) (point2.y - point1.y) / (float) (point2.x - point1.x));
	}

	private float getB(float slope, AryanTile point) {
		return ((slope * (-1 * point.x)) - (-1 * point.y));
	}

	private int getY(int x, float slope, float b) {
		return (int) ((slope * x) + b);
	}

	private int random(int min, int max) {
		return (new Random().nextInt((max - min) + 1) + min);
	}

	private AryanTile getNextDestination(AryanTile current,
			AryanTile destination, float slope, float b)
			throws InterruptedException {
		if (cs.theMiniMap.toOnScreenMiniMap(destination) != null) {
			return destination;
		}
		if (destination.x > current.x) {
			for (int i = destination.x; i > current.x; i--) {
				AryanTile next = new AryanTile(i, getY(i, slope, b));
				if (cs.theMiniMap.toOnScreenMiniMap(next) != null) {
					return next;
				}
			}
		} else if (current.x > destination.x) {
			for (int i = destination.x; i < current.x; i++) {
				AryanTile next = new AryanTile(i, getY(i, slope, b));
				if (cs.theMiniMap.toOnScreenMiniMap(next) != null) {
					return next;
				}
			}
		}
		return new AryanTile(current.x + 1, current.y + 1);
	}

	public ScriptJob walkToTile(AryanTile tile) {
		return new walkJob(cs, tile);
	}

	private class walkJob extends ScriptJob {

		private final AryanTile destination;
		private final ColorSkeltonScriptable cs;

		public walkJob(ColorSkeltonScriptable cs, AryanTile destination) {
			super(cs);
			this.destination = destination;
			this.cs = cs;
			ColorSkeltonScriptable.addScriptJob(this);
		}

		@Override
		public void runV() throws InterruptedException {
			setName("Slope Walker");
			while (!isKilled() && destination.distanceTo(cs.getLocation()) > 2) {
				if (destination.distanceTo(cs.getLocation()) > 500) {
					cs.log("Destination too far, breaking");
					break;
				}
				AryanTile current = cs.getLocation();
				float slope = getSlope(current, destination);
				float b = getB(slope, current);
				AryanTile next = getNextDestination(current, destination,
						slope, b);
				if (next == null || current.distanceTo(next) <= 5
						&& !next.equals(destination)) {
					next = new AryanTile(current.x + 5, current.y - 5);
				}
				XY mapLoc = getMiniMapLocation(next);
				if (mapLoc != null) {
					PaintLine paint = new PaintLine(next);
					cs.addPaintJob(paint);
					cs.mouseClickLeft(mapLoc);
					Timer t = new Timer(3000);
					while (!t.isUp() && !cs.isMoving()) {
						cs.sleep(cs.random(400, 600));
					}
					while (cs.isMoving()) {
						if (!next.equals(destination)
								&& (next.distanceTo(cs.getLocation()) <= random(
										9, 11) || next
										.getCenterGameScreenLocation(cs) != null)) {
							break;
						}
						cs.sleep(cs.random(300, 400));
					}
					paint.finish = true;
				}
				cs.sleep(random(200, 300)); // Avoid infinite loop
			}
		}

	}

	private class PaintLine extends PaintJob {

		public boolean finish = false;
		public AryanTile dest;
		private Timer time = null;

		public PaintLine(AryanTile dest) {
			this.dest = dest;
			this.time = new Timer(10000);
		}

		@Override
		public void paint(Graphics g) {
			try {
				AryanTile current = cs.getLocation();
				XY currentXY = getMiniMapLocation(current);
				XY destXY = getMiniMapLocation(dest);
				g.setColor(Color.BLUE);
				g.drawLine(currentXY.x, currentXY.y, destXY.x, destXY.y);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public boolean timeUp() {
			return finish || !cs.active || time.isUp();
		}
	}

}
