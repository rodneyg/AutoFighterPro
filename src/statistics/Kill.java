package statistics;

public class Kill {
	public String name;
	public int id;
	public long totalCombatTime;
	public int specialAttacks;
	public long timestamp;
	public long xp;

	public Kill(String name, int id, long totalCombatTime, int specialAttacks,
			long timestamp, long xp) {
		super();
		this.name = name;
		this.id = id;
		this.totalCombatTime = totalCombatTime;
		this.specialAttacks = specialAttacks;
		this.timestamp = timestamp;
		this.xp = xp;
	}

	@Override
	public String toString() {
		return name + ":" + id + ":" + totalCombatTime + ":" + specialAttacks
				+ ":" + timestamp + ":" + xp;
	}
}
