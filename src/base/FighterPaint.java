package base;

import impsoft.bots.reflection.Entity;
import impsoft.bots.reflection.NPC;
import impsoft.scripting.ibot.structs.AryanTile;
import impsoft.scripting.ibot.structs.AryanTileZone;
import impsoft.scripting.ibot.structs.XY;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import paint.PaintManager;

public class FighterPaint extends PaintManager {
	Fighter f;

	public FighterPaint(Fighter cs) {
		super(cs);
		this.f = cs;
	}

	@Override
	public void paint(Graphics g) {
		drawMiniMapZone(g);
	}

	public void drawMiniMapZone(Graphics g) {
		try {
			if (cs.isLoggedIn()) {
				if (f.centerTile != null) {
					XY cent = cs.theMiniMap
							.toVirtualMiniMapUsingReflection(f.centerTile);
					if (cent != null) {
						int radius = f.distanceFromTile;
						XY radiusEdge = cs.theMiniMap
								.toMiniMapReflection(new AryanTile(
										f.centerTile.x + radius, f.centerTile.y));
						int r = (int) Math.sqrt(Math.pow(cent.x - radiusEdge.x,
								2D) + Math.pow(cent.y - radiusEdge.y, 2D));
						g.setColor(new Color(255, 255, 255, 50));
						g.fillOval(cent.x - r, cent.y - r, 2 * r, 2 * r);
						g.setColor(Color.BLACK);
						g.drawOval(cent.x - r, cent.y - r, 2 * r, 2 * r);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// TODO: move this to suitable location, if possible
	public AryanTile[] getBox(AryanTile corner, int sides) {
		List<AryanTile> tiles = new ArrayList<AryanTile>();
		for (int i = 0; i < sides; i++) {
			for (int j = 0; j < sides; j++) {
				tiles.add(new AryanTile(corner.x + i, corner.y + j));
			}
		}

		return null;
	}

	public Shape createDiamond(int x, int y, final float s) {
		final GeneralPath p0 = new GeneralPath();
		p0.moveTo(x, y + -s);
		p0.lineTo(x + s, y);
		p0.lineTo(x, y + s);
		p0.lineTo(x + -s, y);
		p0.closePath();
		return p0;
	}

	public void paintZoneToMiniMap(Graphics g, Color border, Color fill,
			AryanTileZone z) {
		AryanTile t1 = new AryanTile(z.planelessXStart, z.planelessYStart);
		AryanTile t2 = new AryanTile(z.planelessXStart, z.planelessYStop);
		AryanTile t3 = new AryanTile(z.planelessXStop, z.planelessYStart);
		AryanTile t4 = new AryanTile(z.planelessXStop, z.planelessYStop);

		try {
			XY TL = cs.theMiniMap.toMiniMapReflection(t1);
			XY BL = cs.theMiniMap.toMiniMapReflection(t2);
			XY TR = cs.theMiniMap.toMiniMapReflection(t3);
			XY BR = cs.theMiniMap.toMiniMapReflection(t4);

			int[] x = { TL.x, BL.x, BR.x, TR.x };
			int[] y = { TL.y, BL.y, BR.y, TR.y };

			g.setColor(fill);
			g.fillPolygon(x, y, 4);

			g.setColor(border);
			g.drawPolygon(x, y, 4);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void paintMovementPlan(NPC npc, Graphics g) {
		if (npc != null) {
			AryanTile[] movementPlan = npc.getMovementPlan();

			if (movementPlan != null && movementPlan.length > 0) {
				for (int i = 0; i < movementPlan.length; i++) {
					Polygon tilePolygon = movementPlan[i]
							.getGameScreenLocation(cs, -40, 40, -40, 40, 25, 30);

					if (tilePolygon != null) {
						cs.paintRectangle(tilePolygon.getBounds(), new Color(
								255, 0, 0, 180), 500 * i);
					}
				}
			}
		}
	}

	public void paintInteractingLines(NPC npc, Graphics g) {
		if (npc != null) {
			Polygon p = npc.getGameScreenLocation();
			if (p != null) {
				Entity e = npc.getInteractingEntity();
				if (e != null) {
					Polygon p2 = e.getGameScreenLocation();
					if (p2 != null) {
						g.setColor(Color.ORANGE);
						g.drawLine((int) p.getBounds().getCenterX(), (int) p
								.getBounds().getCenterY(), (int) p2.getBounds()
								.getCenterX(), (int) p2.getBounds()
								.getCenterY());
					}
				}
			}
		}
	}
}
