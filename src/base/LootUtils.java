package base;

import impsoft.bots.reflection.GroundItem;
import impsoft.scripting.ibot.enums.Feedback;
import impsoft.scripting.ibot.enums.Skill;
import impsoft.scripting.ibot.enums.Spell;
import impsoft.scripting.ibot.structs.AryanTile;
import impsoft.utils.general.Timer;
import impsoft.utils.ruler.RulerClickOptions;
import impsoft.utils.ruler.RulerScriptUtils;
import inventory.InventoryUtils;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.regex.Pattern;

import locating.ObjectFinder;
import looting.LootItem;
import looting.LootTable;
import statistics.Loot;
import statistics.LootManager;
import bergCoder.ClosestTile;
import data.IOManager;

public class LootUtils extends DreamUtility {
	public Fighter ds = null;
	public ObjectFinder objectFinder = null;
	public LootManager lootManager;

	private long looted = 0;

	public static final EnumSet RIGHT_CLICK_ONLY = EnumSet.of(
			RulerClickOptions.PEND_FOR_MOVEMENT_TO_STOP,
			RulerClickOptions.WALK_USING_ANY,
			RulerClickOptions.GOAL_MENU_CLICK,
			RulerClickOptions.WALK_GAME_AVOID_ALL);
	public static final EnumSet DEFAULT_CLICK = EnumSet.of(
			RulerClickOptions.PEND_FOR_MOVEMENT_TO_STOP,
			RulerClickOptions.WALK_USING_ANY,
			RulerClickOptions.GOAL_NORMAL_CLICK,
			RulerClickOptions.WALK_GAME_AVOID_ALL);

	public LootUtils(DreamScript cs, LootManager lootManager) {
		super(cs);
		ds = (Fighter) cs;
		objectFinder = cs.getObjectFinder();
		this.lootManager = lootManager;
	}

	public long getLooted() {
		return looted;
	}

	public void scatterAshes() throws InterruptedException {
		ds.getInventoryUtils().doInventory(InventoryUtils.BONES, "scatter",
				true);
	}

	public void buryAllBones() throws InterruptedException {
		ds.getInventoryUtils().doActionOnAll(InventoryUtils.BONES, "bury");
	}

	public boolean hasAlchRunes() throws InterruptedException {
		return cs.theTabs.Inventory.countStackOf("Fire rune") >= 5
				&& cs.theTabs.Inventory.count("Nature rune") > 0;

	}

	public void alch(LootTable alchTable) throws InterruptedException {
		for (LootItem lootItem : alchTable) {
			if (!hasAlchRunes()) {
				break;
			}

			if (cs.theTabs.Inventory.count(lootItem.getName()) > 0) {
				while (hasAlchRunes()
						&& cs.theTabs.Inventory.count(lootItem.getName()) > 0) {
					if (!cs.theTabs.Magic.isSpellSelected()) {
						cs.theTabs.Magic.clickSpell(Spell.HIGH_LEVEL_ALCHEMY);
					}

					for (Timer t = new Timer(2000); t.isNotUp()
							&& !cs.theTabs.Inventory.isSelected();) {
						cs.sleep(100);
					}

					if (cs.theTabs.Magic.isSpellSelected()) {
						if (!cs.theTabs.Inventory.isSelected()) {
							cs.theTabs.Inventory.key();
						}

						cs.getInventoryUtils().doInventory(lootItem.getName(),
								null, false);
					}
				}
			}
		}
	}

	public boolean collectLoot(LootTable lootTable, LootTable alchTable,
			int lootDistance, int priceMinimum, boolean buryBones,
			boolean scatterAshes, boolean dumpFood, boolean b2p,
			boolean telegrab, boolean rightClickAlways, AryanTile center)
			throws InterruptedException {
		if (!worthLooting(lootTable, lootDistance)) {
			return false;
		}

		for (AryanTile tile : getStacks(lootTable, lootDistance)) {
			if (!cs.theMiniMap.canReachReflection(tile)) {
				continue;
			}

			if (tile.distanceTo(center) > lootDistance) {
				continue;
			}

			for (GroundItem item : cs.getAllGroundItemsAt(tile)) {
				for (LootItem lootItem : lootTable) {
					int id = item.getID();
					ds.getTabHandler().setTab(cs.theTabs.Inventory);
					int totalCount = cs.theTabs.Inventory.count(lootItem
							.getName());
					int stack = item.getStackSize();
					lootItem.setValue(item.getGrandExchangeValue() / stack);
					boolean cantStack = !lootItem.isStackable()
							|| totalCount < 1;

					if (!lootItem.idMatches(id)
							|| (lootItem.getValue() < priceMinimum)) {
						continue;
					}

					if (buryBones && cs.theTabs.Inventory.isFull() && cantStack) {
						ds.getInventoryUtils().doInventory(
								InventoryUtils.BONES, "bury", true);
					}

					if (scatterAshes && cs.theTabs.Inventory.isFull()
							&& cantStack) {
						// TODO: todo it
					}

					if (dumpFood && cantStack && cs.theTabs.Inventory.isFull()) {
						if (!b2p
								|| (!lootItem.nameMatches("Bones") && !lootItem
										.nameMatches("Big bones"))) {
							ds.getFoodManager().heal();
						}
					}

					if (b2p && !lootItem.nameMatches("Bones")
							&& !lootItem.nameMatches("Big bones")
							&& cs.theTabs.Inventory.isFull() && cantStack) {
						if (ds.getFoodManager().getHealthPoints() + 80 > cs.theTabs.Statistics
								.getStatBottom(Skill.HITPOINTS)
								&& cs.theTabs.Inventory.count("") > 0) {
							ds.getInventoryUtils().doInventory(
									InventoryUtils.BONES, "drop", false);
						}
					}

					if (cantStack && cs.theTabs.Inventory.isFull()) {
						continue;
					}

					if (lootItem.getId() == item.getID()
							&& item.getLocation().equals(tile)) {
						if (telegrab) {
							cs.theTabs.Magic
									.selectSpell(Spell.TELEKINETIC_GRAB);

							if (!cs.theTabs.Magic
									.isSpellSelected(Spell.TELEKINETIC_GRAB)) {
								continue;
							}
						}

						String[] args = lootItem.getName().split(" ");
						String patt = "";
						for (String s : args) {
							patt += s + ".*";
						}

						if (new Clicker(cs).clickWorldObject(item, Pattern.compile(".*Take " + lootItem.getName() + ".*", Pattern.CASE_INSENSITIVE), true)) {
							ds.sleepUntilNotMoving();
							cs.sleep(500);

							looted += lootItem.getValue() * stack;

							cs.log("Looted " + lootItem.getName() + " $"
									+ lootItem.getValue() + " (qty: " + stack
									+ ")");
							
							lootManager.save(new Loot(lootItem.getName(), lootItem.getId(), stack, lootItem.getValue(), System.currentTimeMillis()), new IOManager());
						}
					}
				}
			}
		}

		return true;
	}

	public AryanTile[] getStacks(LootTable lootTable, int lootDistance)
			throws InterruptedException {
		HashSet<AryanTile> stackLocations = new HashSet<AryanTile>();
		for (GroundItem item : cs.getAllGroundItems()) {
			if (lootTable.get(item.getID()) != null
					&& item.getLocation().distanceTo(cs.getLocation()) <= lootDistance) {
				stackLocations.add(item.getLocation());
			}
		}

		AryanTile[] stackArray = new AryanTile[stackLocations.size()];
		stackArray = stackLocations.toArray(stackArray);
		Arrays.sort(stackArray, new ClosestTile(cs));
		return stackArray;
	}

	public boolean worthLooting(LootTable lootTable, int lootDistance)
			throws InterruptedException {
		if (cs.theTabs.Inventory.isFull()) {
			return false;
		}

		for (GroundItem item : cs.getAllGroundItems()) {
			if (lootTable.get(item.getID()) != null
					&& item.getLocation().distanceTo(cs.getLocation()) <= lootDistance) {
				return true;
			}
		}
		return false;
	}
}
