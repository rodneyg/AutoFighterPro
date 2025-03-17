package statistics;

import java.util.ArrayList;
import java.util.List;

import base.Fighter;

import data.IOManager;

public class LootManager {
	private String lootLogLocation;
	private List<Loot> loots = new ArrayList<Loot>();
	private List<Loot> currentLoot = new ArrayList<Loot>();

	public LootManager(Fighter f) {
		lootLogLocation = f.lootLogLocation;
	}

	public List<Loot> getLifetimeItems() {
		return loots;
	}

	public List<Loot> getCurrentItems() {
		return currentLoot;
	}

	public void load(IOManager io) {
		String[] lootStrs = io.getFileManager().getStringArray(lootLogLocation);

		for (String loot : lootStrs) {
			if (!loot.contains("@") && loot.contains(":")) {
				String[] split = loot.split(":");
				try {
					loots.add(new Loot(split[0], Integer.parseInt(split[1]),
							Integer.parseInt(split[2]), Integer
									.parseInt(split[3]), Long
									.parseLong(split[4])));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void save(Loot loot, IOManager io) {
		List<String> endSession = new ArrayList<String>();
		endSession.add(loot.toString());
		currentLoot.add(loot);
		io.getFileManager().appendFile(lootLogLocation, false,
				(ArrayList) endSession);
	}

	public void start(IOManager io) {
		List<String> endSession = new ArrayList<String>();
		endSession.add("<Start@[" + System.currentTimeMillis() + "]>");
		io.getFileManager().appendFile(lootLogLocation, false,
				(ArrayList) endSession);
	}

	public void end(IOManager io) {
		List<String> endSession = new ArrayList<String>();
		endSession.add("</End@[" + System.currentTimeMillis() + "]>");
		io.getFileManager().appendFile(lootLogLocation, false,
				(ArrayList) endSession);
	}

	public Loot[][] getSessions() {
		return null;
	}

	public long getValueLooted() {
		return getValueLooted(null);
	}

	public long getValueLooted(String itemName) {
		long looted = 0;

		for (Loot loot : loots) {
			if (itemName == null || loot.name.equalsIgnoreCase(itemName)) {
				looted += loot.value * loot.stack;
			}
		}

		return looted;
	}

	public long getItemsLooted() {
		return getItemsLooted(null);
	}

	public long getItemsLooted(String itemName) {
		long looted = 0;

		for (Loot loot : loots) {
			if (itemName == null || loot.name.equalsIgnoreCase(itemName)) {
				looted++;
			}
		}

		return looted;
	}
}
