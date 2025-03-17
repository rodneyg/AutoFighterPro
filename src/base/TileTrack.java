package base;

import impsoft.scripting.ibot.structs.AryanTile;
import impsoft.scripting.ibot.structs.XY;
import mouse.MouseTrack;

public class TileTrack extends MouseTrack {

	public TileTrack(AryanTile location) {
		super(new XY(location.x, location.y));
	}

}
