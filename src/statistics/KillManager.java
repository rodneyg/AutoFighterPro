package statistics;

import java.util.ArrayList;
import java.util.List;

import base.Fighter;
import data.IOManager;

public class KillManager {
	private List<Kill> kills = new ArrayList<Kill>();
	private List<Kill> currentKills = new ArrayList<Kill>();
	private String killLogLocation;

	public KillManager(Fighter f) {
		killLogLocation = f.killLogLocation;
	}

	public List<Kill> getLifetimeKills() {
		return kills;
	}

	public List<Kill> getCurrentKills() {
		return currentKills;
	}

	public void load(IOManager io) {
		String[] killStrs = io.getFileManager().getStringArray(killLogLocation);

		for (String kill : killStrs) {
			if (!kill.contains("@") && kill.contains(":")) {
				String[] split = kill.split(":");
				try {
					kills.add(new Kill(split[0], Integer.parseInt(split[1]),
							Long.parseLong(split[2]), Integer
									.parseInt(split[3]), Long
									.parseLong(split[4]), Long
									.parseLong(split[5])));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void save(Kill kill, IOManager io) {
		List<String> endSession = new ArrayList<String>();
		endSession.add(kill.toString());
		currentKills.add(kill);
		io.getFileManager().appendFile(killLogLocation, false,
				(ArrayList) endSession);
	}

	public void start(IOManager io) {
		List<String> endSession = new ArrayList<String>();
		endSession.add("<Start@[" + System.currentTimeMillis() + "]>");
		io.getFileManager().appendFile(killLogLocation, false,
				(ArrayList) endSession);
	}

	public void end(IOManager io) {
		List<String> endSession = new ArrayList<String>();
		endSession.add("</End@[" + System.currentTimeMillis() + "]>");
		io.getFileManager().appendFile(killLogLocation, false,
				(ArrayList) endSession);
	}

	public Kill[][] getSessions() {
		return null;
	}

	public long getAverageSpecialAttacks() {
		return getAverageCombatTime(null);
	}

	public long getAverageSpecialAttacks(String monster) {
		long monsterMatch = 0;
		for (Kill kill : kills) {
			if (monster == null || kill.name.equalsIgnoreCase(monster)) {
				monsterMatch++;
			}
		}

		return getTotalSpecialAttacks(monster) / monsterMatch;
	}

	public long getTotalSpecialAttacks() {
		return getTotalSpecialAttacks(null);
	}

	public long getTotalSpecialAttacks(String monster) {
		long time = 0;
		for (Kill kill : kills) {
			if (monster == null || kill.name.equalsIgnoreCase(monster)) {
				if (kill.totalCombatTime > 0) {
					time += kill.totalCombatTime;
				}
			}
		}

		return time;
	}

	public long getAverageCombatTime() {
		return getAverageCombatTime(null);
	}

	public long getAverageCombatTime(String monster) {
		long monsterMatch = 0;
		for (Kill kill : kills) {
			if (monster == null || kill.name.equalsIgnoreCase(monster)) {
				monsterMatch++;
			}
		}

		return getTotalCombatTime(monster) / monsterMatch;
	}

	public long getTotalCombatTime() {
		return getTotalCombatTime(null);
	}

	public long getTotalCombatTime(String monster) {
		long time = 0;
		for (Kill kill : kills) {
			if (monster == null || kill.name.equalsIgnoreCase(monster)) {
				if (kill.totalCombatTime > 0) {
					time += kill.totalCombatTime;
				}
			}
		}

		return time;
	}

	public long getTotalKills() {
		return kills.size();
	}

	public long getTotalKills(String monster) {
		long kills2 = 0;
		for (Kill kill : kills) {
			if (monster == null || kill.name.equalsIgnoreCase(monster)) {
				kills2++;
			}
		}

		return kills2;
	}

	public long getTotalXPGained() {
		return getTotalXPGained(null);
	}

	public long getTotalXPGained(String monster) {
		long xp = 0;
		for (Kill kill : kills) {
			if (monster == null || kill.name.equalsIgnoreCase(monster)) {
				if (kill.totalCombatTime > 0) {
					xp += kill.totalCombatTime;
				}
			}
		}

		return xp;
	}
}
