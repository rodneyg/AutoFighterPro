package statistics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import paint.PaintIcon;

public class Logger extends PaintIcon {
    private boolean showLoot = false;
    private boolean showKills = true;

    private int state = 2;
    private int color = 0;

    private String root = "/statistics/logger/images/";
    private static final String[] colors = { "cyan", "green", "magenta",
	    "orange", "pink", "red" };
    private static final String[] imageStates = { "current.png",
	    "lifetime.png", "off.png" };

    private static final Rectangle xButton = new Rectangle(499, 5, 16, 13);
    private static final Rectangle currentButton = new Rectangle(446, 4, 48, 13);
    private static final Rectangle lifetimeButton = new Rectangle(398, 4, 48,
	    13);

    private String text;

    private LootManager lootManager;
    private KillManager killManager;
    private Formatter formatter;

    public Logger(String text, int x, int y, LootManager lootManager,
	    KillManager killManager, Formatter formatter, String name) {
	super(x, y, "", null);
	this.text = text;
	this.lootManager = lootManager;
	this.killManager = killManager;
	this.formatter = formatter;
	root = name + File.separator + "statistics" + File.separator + "logger"
		+ File.separator + "images";
    }

    public void changeLogger() {
	if (showLoot) {
	    showKills = true;
	    showLoot = false;
	} else {
	    showKills = false;
	    showLoot = true;
	}
    }

    public void next() {
	if (color + 1 >= colors.length) {
	    color = 0;
	} else {
	    color++;
	}
    }

    public void init() {
	changeIcon(root + File.separator + colors[color] + File.separator + imageStates[state]);
    }

    @Override
    public void paint(Graphics g) {
	if (icon != null) {
	    g.drawImage(icon, getX(), getY(), null);

	    g.setColor(Color.BLACK);
	    g.setFont(new Font("Arial", Font.BOLD, 9));

	    if (showLoot) {
		g.drawString(text, getX() + 4, getY() + 10);
		g.drawString("Loot logger", getX() + 130, getY() + 10);

		List<Loot> items = null;
		if (state == 0) {
		    items = lootManager.getCurrentItems();
		} else if (state == 1) {
		    items = lootManager.getLifetimeItems();
		} else {
		    return;
		}

		if (items.size() > 0) {
		    List<String> painted = new ArrayList<String>();
		    int j = 0;
		    g.setColor(Color.WHITE);

		    for (Loot loot : items) {
			if (!painted.contains(loot.name)) {
			    painted.add(loot.name);
			    g.drawString(
				    loot.name
					    + "  |  Qty: "
					    + formatter.formatValue(lootManager
						    .getItemsLooted(loot.name))
					    + "  |  Value: $"
					    + formatter.formatValue(lootManager
						    .getValueLooted(loot.name)),
				    getX() + 4, getY() + 33 + (9 * j));
			    j++;
			}
		    }

		    g.drawString(
			    "Total stats: Looted "
				    + formatter.formatValue(lootManager
					    .getItemsLooted())
				    + " items for $"
				    + formatter.formatValue(lootManager
					    .getValueLooted()), getX() + 4,
			    getY() + 22);

		}
	    } else if (showKills) {
		g.drawString(text, getX() + 4, getY() + 10);
		g.drawString("Kill logger", getX() + 130, getY() + 10);

		List<Kill> kills = null;
		if (state == 0) {
		    kills = killManager.getCurrentKills();
		} else if (state == 1) {
		    kills = killManager.getLifetimeKills();
		} else {
		    return;
		}

		if (kills.size() > 0) {
		    List<String> painted = new ArrayList<String>();
		    int j = 0;
		    g.setColor(Color.WHITE);

		    for (Kill kill : kills) {
			if (!painted.contains(kill.name)) {
			    painted.add(kill.name);
			    g.drawString(
				    kill.name
					    + "  |  Qty: "
					    + formatter.formatValue(killManager
						    .getTotalKills(kill.name))
					    + "  |  Time: "
					    + killManager
						    .getTotalCombatTime(kill.name)
					    / 1000
					    + "  |  XP: "
					    + formatter
						    .formatValue(killManager
							    .getTotalXPGained(kill.name)),
				    getX() + 4, getY() + 33 + (9 * j));
			    j++;
			}
		    }

		    g.drawString(

			    "Total stats:"
				    + " Kills: "
				    + formatter.formatValue(killManager
					    .getTotalKills())
				    + "  |  Time: "
				    + killManager.getTotalCombatTime()
				    / 1000
				    + " seconds  |  XP: "
				    + formatter.formatValue(killManager
					    .getTotalXPGained()), getX() + 4,
			    getY() + 22);

		}
	    }
	}
    }

    @Override
    public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
	if (xButton.contains(e.getX(), e.getY())) {
	    state = 2;
	} else if (lifetimeButton.contains(e.getX(), e.getY())) {
	    state = 1;
	} else if (currentButton.contains(e.getX(), e.getY())) {
	    state = 0;
	}

	init();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(MouseEvent e) {
	// TODO Auto-generated method stub

    }
}
