package gui;

import versioncontrol.Changelog;

public class Changelogs {
	public static Changelog intialRelease = new Changelog(
			1.0,
			"<html><h2>Intial Release 1.0 ",
			"(Tuesday, July 12, 2011)</h1><br><li>New paint</li><li>New user interface</li><li>Improved attacking</li><li>Updraded inventory methods</li><li>Added change log feature</li>" +
			"<li>More loot options</li><li>Loot/Kill tracking</li><li>Better bot customization</li><li>New mouse tracker</li></html>");

	public static Changelog[] releases = { intialRelease };
}
