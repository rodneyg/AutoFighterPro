package statistics;

public class Loot {
	public String name;
	public int id;
	public int stack;
	public int value;
	public long timestamp;

	public Loot(String name, int id, int stack, int value, long timestamp) {
		super();
		this.name = name;
		this.id = id;
		this.stack = stack;
		this.value = value;
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return name + ":" + id + ":" + stack + ":" + value + ":" + timestamp;
	}
}
