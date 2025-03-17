package paint;

import impsoft.scripting.ibot.structs.XY;
import impsoft.utils.general.Timer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import statistics.Experience;
import statistics.Formatter;

public class FighterProgressBar extends ProgressBar {

    public FighterProgressBar(int x, int y, int percentage, boolean striped,
	    Color outlineColor, Color fillColor, Color textColor, String text) {
	super(x, y, percentage, striped, outlineColor, fillColor, textColor,
		text);
    }

    public void drawProgressBar(double percent, Experience experience,
	    Formatter format, Timer timeRan, Graphics g) {
	try {
	    if (experience.getTotalXPGained() < 1) {
		return;
	    }
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

	Font f = g.getFont();

	int buttonX = x;
	int buttonY = y;

	int barWidth = 218;
	int barHeight = 15;

	double per = (percent * .01);
	if (percent > 99) {
	    per = 1;
	}

	g.setColor(new Color(0, 0, 0, 225));
	g.drawRect(buttonX, buttonY, barWidth + 1, barHeight + 2);
	g.fillRect(buttonX, buttonY, barWidth + 1, barHeight + 2);

	g.setColor(fillColor);
	g.drawRect(buttonX + 2, buttonY + 2, (int) (barWidth * per) - 3,
		barHeight - 2);
	g.fillRect(buttonX + 2, buttonY + 2, (int) (barWidth * per) - 3,
		barHeight - 2);

	drawBarText(experience, format, timeRan, g);

	g.setFont(f);
    }

    public void drawShadowString(int x, int y, String text, Color color,
	    Graphics g) {
	for (int i = 1; i > -1; i--) {
	    g.setColor(i != 1 ? color : Color.BLACK);

	    g.drawString(text, x + i, y + i);
	}
    }

    Font serif = new Font("Serif", Font.PLAIN, 9);

    public void drawBarText(Experience experience, Formatter format,
	    Timer timeRan, Graphics g) {
	try {
	    XY textXY = paintUtils.centerText(text, g, 218, 15);
	    g.setFont(serif);

	    drawShadowString(
		    x + 3,
		    textXY.y + y + 1,
		    experience.getSkill().name.toUpperCase()
			    + "  |  "
			    + experience.getLevel()
			    + "/"
			    + experience.getStartLevel()
			    + "  |  "
			    + experience.getTTL()
			    + "  |  "
			    + format.getHourlyValueFormatted(
				    experience.getTotalXPGained(), timeRan)
			    + " XP/H", Color.WHITE, g);
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
