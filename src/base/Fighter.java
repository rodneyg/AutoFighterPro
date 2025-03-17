package base;

import food.FoodManager;
import gui.DreamFighterUI;
import impsoft.bots.ColorBot;
import impsoft.bots.reflection.NPC;
import impsoft.scripting.ibot.enums.Prayer;
import impsoft.scripting.ibot.enums.Skill;
import impsoft.scripting.ibot.enums.Spell;
import impsoft.scripting.ibot.interfaces.Icon;
import impsoft.scripting.ibot.interfaces.ScriptToolbar;
import impsoft.scripting.ibot.itemrec.ItemCondition;
import impsoft.scripting.ibot.itemrec.ItemContains;
import impsoft.scripting.ibot.itemrec.ItemContainsIgnore;
import impsoft.scripting.ibot.structs.AryanTile;
import impsoft.scripting.ibot.structs.XY;
import impsoft.threads.SWTThread;
import impsoft.utils.general.Timer;
import impsoft.utils.ruler.RulerScriptUtils;
import impsoft.values.variable.SettingsManager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;

import kevin.kCachedFileLoader;
import looting.LootItemLoader;
import looting.LootTable;
import mouse.MouseSpeedHandler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import paint.DefaultPaintButton;
import paint.DefaultPaintText;
import paint.FighterProgressBar;
import paint.PaintComponent;
import paint.StripedPaintContainer;
import potions.Potion;
import statistics.Experience;
import statistics.Formatter;
import statistics.KillManager;
import statistics.LevelGoal;
import statistics.Logger;
import statistics.LootManager;
import systemtray.ScriptTrayManager;
import teleporting.Jewellery;
import teleporting.TeleportTab;
import bergCoder.Object3D;
import branding.Brand;
import breaking.Break;
import breaking.BreakHandler;

import combat.Monster;

import data.Download;
import data.FileManager;
import data.IOManager;
import data.ImageManager;

public class Fighter extends DreamScript implements Icon, ScriptToolbar {
    public Monster[] monsters;
    public boolean specializedFighter = false;
    public boolean differentFileSystem = false;
    public boolean slayerEnabled = false;
    public AryanTile centerTile;
    public int distanceFromTile = 0;

    public String lootLogLocation;
    public String killLogLocation;
    private LootManager lootManager;
    private KillManager killManager;
    private Formatter formatter = new Formatter(this);

    public String selectedMonsterDimension = "Human";
    public Object3D monsterDimension = null;

    public String status = "";

    public long totalKills = 0;
    public long totalCash = 0;
    public long totalDeaths = 0;
    public long totalSpecialAttacks = 0;

    public boolean mouseKeys = false; // credits: david

    public int healAt = 0;
    public int foodWithdraw = 0;
    public boolean healing = false;
    public boolean eatForLoot = false;
    public boolean eatAtBank = false;
    public boolean guthans = false;
    public boolean b2p = false;
    public boolean generalStoreFood = false;
    public String foodName = null;

    public boolean takeScreenshots = false;
    public boolean taskbarNotifications = false;
    public int mouseSpeedBoost = 0;
    public Timer screenshotTimer = new Timer(1000 * 60 * 60);
    public boolean usingGoals = false;
    public String[] defaultGear = null;
    public LevelGoal[] goals = null;

    public boolean equipRangedAmmo = false;
    public Timer equipRange = new Timer(60000);
    public String rangedName = null;
    public boolean safespot = false;

    public boolean buryBones = false;
    public boolean scatterAshes = false;
    public boolean loot = false;
    public boolean waitForLoot = false;
    public boolean lootshare = false; // TODO: maybe
    public boolean telegrabLoot = false;
    public boolean rightClickLoot = false;
    public int priceMinimum = -1;
    public LootTable lootItems = new LootTable();
    public LootTable masterLootTable = new LootTable();

    public boolean cleanHerbs = false;
    public boolean cleanHerbsAndDrop = false;
    public boolean alch = false;
    public LootTable alchItems = new LootTable();

    public boolean hoverTarget = false;
    public boolean specialAttack = false;
    public String specialAttackWeapon = null;

    public boolean useJewelleryToBank = false;
    public boolean useJewelleryToMonster = false;
    public boolean needJewellery = false;
    public boolean equipJewellery = false;
    public String jewelleryName = null;
    public Jewellery jewellery = null;
    public String jewelleryLocation = null;
    public boolean useTabToBank = false;
    public boolean useTabToMonster = false;
    public String teleportTabName = null;
    public TeleportTab teleportTab = null;

    public boolean showDistanceZone = true;
    public boolean invisibleOnMiniMap = false;
    public boolean unreachable = false;
    public boolean multiCombat = false;
    public boolean aggressive = false;
    public boolean underAttack = false;

    public String prayerName = null;
    public Prayer prayerSpell = null;
    public boolean prayer = false;
    public boolean togglePrayer = false;
    public boolean prayAtAltar = false;
    public boolean quickPrayer = false;

    public boolean summoning = false;
    public boolean renewAtObelisk = false;
    public boolean potions = false;

    public BankItem[] bankItems = new BankItem[0];
    public boolean restAtBank = false;
    public boolean shutdownOnBank = false;

    public boolean breaking = false;
    public Break[] breaks = null;
    public BreakHandler breaker = null;

    private Clicker clicker = new Clicker(this);

    public Image icon = null;
    public ScriptTrayManager systemTray = new ScriptTrayManager();
    public LootUtils lootUtils = new LootUtils(this, lootManager);
    public IOManager io = new IOManager();
    public FileManager fileManager = IOManager.getFileManager();
    public ImageManager imageManager = IOManager.getImageManager();
    public Download itemDataDownload = null;
    public Download fontDownload = null;

    public DreamFighterUI frame = null;
    public boolean startScript = false;
    public boolean paint = true;
    public LookAndFeel defaultLF = null;
    private static final Rectangle loggerLocation = new Rectangle(212, 3, 304,
	    14);

    private int colorIndex = 0;
    private static final Color[] colors = { Color.CYAN, Color.GREEN,
	    Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED };

    private Experience attack;
    private Experience strength;
    private Experience defense;
    private Experience range;
    private Experience magic;

    public BotState theBotState = BotState.WALK;

    public enum BotState {
	WALK, FIGHT, BANK
    }

    public List<PaintComponent> paintComponents = new ArrayList<PaintComponent>();

    GeneralThread generalThread = null;

    public Fighter(ColorBot d) {
	super(d);

	version = 2.0;
    }

    @Override
    public void script() throws InterruptedException, Exception {
	RulerScriptUtils.useModels = true;
	ClassLoader prevCl = Thread.currentThread().getContextClassLoader();

	if (!hasAuth(71) && !hasAuth(657)) {
	    throw new DreamError("You do not have a Fighter auth");
	}

	try {
	    startUp();

	    generalThread.start();
	    // addScriptJob(npcWatcher);

	    log("Intializing logger files");
	    lootManager = new LootManager(this);
	    lootManager.start(io);
	    lootUtils = new LootUtils(this, lootManager);

	    killManager = new KillManager(this);
	    killManager.start(io);

	    getExperienceHandler().startAllExperiences(this);
	    attack = getExperienceHandler().getExperience(Skill.ATTACK);
	    strength = getExperienceHandler().getExperience(Skill.STRENGTH);
	    defense = getExperienceHandler().getExperience(Skill.DEFENSE);
	    range = getExperienceHandler().getExperience(Skill.RANGED);
	    magic = getExperienceHandler().getExperience(Skill.MAGIC);

	    while (startScript) {
		if (!isLoggedIn()) {
		    sleep(3000);
		}

		if (breaker != null) {
		    if (breaker.hasToBreak()) {
			theBotState = BotState.BANK;
		    } else {
			breaker.nextBreak();
		    }

		}

		switch (theBotState) {
		case WALK:
		    walk();
		    break;
		case FIGHT:
		    beforeFight();
		    fight2();
		    break;
		case BANK:
		    bank();
		    break;
		}

		getToolbarValues();
		sleep(10);
	    }
	} catch (Exception e) {
	    String trace = "\n" + e.getMessage() + "\n";
	    for (StackTraceElement element : e.getStackTrace()) {
		trace += element.toString() + "\n";
	    }

	    log(trace);

	    if (e instanceof DreamError) {
		getLogoutHandler().logout(true);
	    }
	} finally {
	    Thread.currentThread().setContextClassLoader(prevCl);
	}
    }

    @Override
    public void turnOff() {
	try {
	    lootManager.end(io);
	    killManager.end(io);

	    startScript = false;
	    paint = false;

	    generalThread.stopRunning();

	    frame.dispose();
	    systemTray.dispose();

	    masterLootTable.clear();
	    lootItems.clear();
	    mouseTrackList.clear();
	    getCombatUtils().npcs.clear();

	    breaks = null;
	    bankItems = null;

	    log("Successful clean up");
	} catch (Exception e) {
	    e.printStackTrace();
	    log(e);
	} finally {
	    super.turnOff();
	}
    }

    public void checkChat() throws InterruptedException {

    }

    public void walk() throws InterruptedException {
	teleport(false);

	setFastRun(true);
	theWorldMap.walkTo(centerTile);

	theBotState = BotState.FIGHT;
    }

    public void food() throws InterruptedException {
	FoodManager foodManager = getFoodManager();
	if (guthans) {
	    if (foodManager.getHpPercentage() < 60) {
		log("Putting on guthans armor");
		getEquipmentUtils().putOnGearReflection(FoodManager.GUTHANS);
	    } else if (foodManager.getHpPercentage() >= 90) {
		log("Removing guthans armor");

		if (rangedName != null) {
		    getEquipmentUtils().putOnGear(defaultGear);
		    checkRangeAmmo();
		} else {
		    getEquipmentUtils().putOnGearReflection(defaultGear);
		}
	    }
	}

	if (foodManager.getHpPercentage() < healAt) {
	    if (foodManager.countFood() < 1 && generalStoreFood) {
		if (foodManager.countNotedFood() > 0) {
		    ItemContains ourFood = new ItemContains(foodName);

		    log("Selling noted food");
		    theGeneralStore.sellX(ourFood, foodWithdraw, false);

		    log("Buying back food");
		    theGeneralStore.buyX(ourFood, foodWithdraw, true);
		}
	    } else if (!foodManager.hasFood() && b2p) {
		if (theTabs.Inventory.count(FoodManager.BONES) > 0) {
		    if (hasB2PRunes()) {
			log("Casting B2P spell");
			theTabs.Magic.clickSpell(Spell.BONES_TO_PEACHES);

			for (Timer peachTimer = new Timer(6000); !peachTimer
				.isUp()
				&& theTabs.Inventory.count(FoodManager.PEACH) < 1;) {
			    sleep(100);
			}
		    } else {
			log("Breaking B2P tab");

			if (getInventoryUtils().doInventory(
				FoodManager.BONES_TO_PEACHES, "break", true)
				.isSuccessful()) {
			    log("Waiting for peaches to appear");
			    for (Timer peachTimer = new Timer(6000); !peachTimer
				    .isUp()
				    && theTabs.Inventory
					    .count(FoodManager.PEACH) < 1;) {
				sleep(100);
			    }
			}
		    }
		}
	    }

	    if (foodManager.hasFood()) {
		if (foodManager.getHpPercentage() < healAt) {
		    log("Eating food");
		    foodManager.heal(100);
		}
	    } else {
		if (!guthans
			&& (!b2p || theTabs.Inventory.count(FoodManager.BONES) < 1)) {
		    log("No food, going to the bank");
		    theBotState = BotState.BANK;
		}
	    }
	}
    }

    public boolean hasB2PRunes() throws InterruptedException {
	return theTabs.Inventory.countStackOf("Earth rune") >= 4
		&& theTabs.Inventory.count("Nature rune") >= 2
		&& theTabs.Inventory.count("Water rune") >= 4;

    }

    public boolean checkPoison() throws InterruptedException {
	if (theMiniMapIndicators.isPoisioned()) {
	    if (theTabs.Inventory.count("antipoision") > 0) {
		getInventoryUtils().doInventory("antipoison", null, true);
	    } else {
		theBotState = BotState.BANK;
		return false;
	    }
	}

	return true;
    }

    public void checkPotions() throws InterruptedException {
	for (Potion potion : Potion.values()) {
	    if (getPotionUtils().hasPotion(potion)
		    && getPotionUtils().needPotion(potion)) {
		log("Using a dose of an " + potion.getName());
		getPotionUtils().usePotion(potion);
	    }
	}
    }

    public boolean prayer(boolean to) throws InterruptedException {
	if (theMiniMapIndicators.getPrayerPoints() < 5) {
	    getPotionUtils().usePotion(Potion.PRAYER);
	}

	if (!togglePrayer && !to && theBotState.equals(BotState.FIGHT)) {
	    return true;
	}

	if (theMiniMapIndicators.getPrayerPoints() > 0) {
	    logStatus("Setting prayer " + (to ? "on" : "off"));

	    if (theTabs.Prayer.getQuickSelectPrayers().size() == 1
		    && theTabs.Prayer.getQuickSelectPrayers().contains(
			    prayerSpell)) {
		return theTabs.Prayer.setPrayer(
			theTabs.Prayer.getQuickSelectPrayers(), to);
	    } else if (quickPrayer) {
		return theTabs.Prayer.setPrayer(
			theTabs.Prayer.getQuickSelectPrayers(), to);
	    } else if (prayerSpell != null) {
		return theTabs.Prayer.setPrayer(prayerSpell, to);
	    }
	}

	return false;
    }

    public void summoning() throws InterruptedException {

    }

    public void beforeFight() throws InterruptedException {
	if (healing) {
	    food();
	}

	if (loot
		&& lootUtils.worthLooting(lootItems, 10)) {
	    status = "Looting";
	    lootUtils.collectLoot(lootItems, alchItems, 10, priceMinimum,
		    buryBones, scatterAshes, healing, b2p, telegrabLoot,
		    rightClickLoot, centerTile);

	    totalCash = lootUtils.getLooted();
	}

	status = "Looking for monsters";

	if (equipRangedAmmo) {
	    checkRangeAmmo();
	}

	setFastRun(true);

	if (safespot) {
	    logStatus("Walking to safespot");
	    int defaultAccuracy = getColorBot().walkingAccuracyOverride;
	    getColorBot().walkingAccuracyOverride = 0;

	    theWorldMap.walkTo(centerTile, false);
	    getColorBot().walkingAccuracyOverride = defaultAccuracy;
	} else if (getLocation().distanceTo(centerTile) > distanceFromTile
		|| !theMiniMap.canReachReflection(centerTile)) {
	    logStatus("Walking back to center");
	    theWorldMap.walkTo(centerTile, true);
	}

	if (prayer) {
	    prayer(inCombat());
	}

	checkPotions();

	if (!theBotState.equals(BotState.FIGHT)) {
	    return;
	}
    }

    public NPC getMonster() throws InterruptedException {
	return getCombatUtils().getNPC(centerTile, null, distanceFromTile,
		unreachable, !invisibleOnMiniMap, true, monsters);
    }

    public long startCombat = 0;

    public void fight2() throws InterruptedException {
	NPC mine = null;
	if ((mine = getMyPlayer().getInteractingNPC()) == null
		|| !isValidMonster(mine)
		|| !getCombatUtils().isAttackable(mine)) {
	    NPC found = getMonster();
	    if (found != null) {
		logStatus("Found monster, attacking");

		if (clicker
			.clickWorldObject(found, Pattern.compile(".*Attack.*",
				Pattern.CASE_INSENSITIVE), true)) {
		    log("Attack successful");
		    Timer wait = new Timer(2000);
		    while (wait.isNotUp()
			    && (mine = getMyPlayer().getInteractingNPC()) == null) {
			if (isMoving()) {
			    wait.reset();
			}

			sleep(100);
		    }

		    sleepUntilNotMoving();
		}
	    }
	}

	if ((mine = getMyPlayer().getInteractingNPC()) != null
		&& (isValidMonster(mine) || underAttack)
		&& getCombatUtils().isAttackable(mine)) {
	    logStatus("Interacting with NPC");

	    startCombat = System.currentTimeMillis();

	    if (waitForDeath(mine)) {
		totalKills++;
		log("Killed monster! Total kills: " + totalKills);
		underAttack = false;

		if (loot) {
		    logStatus("Looting");

		    if (waitForLoot) {
			log("Activating loot wait");
			Timer lootWait = new Timer(4000);
			while (!lootWait.isUp()) {
			    if (lootUtils.worthLooting(lootItems, 2)) {
				break;
			    }

			    sleep(100);
			}
		    }

		    loot();
		}

		if (usingGoals) {
		    for (LevelGoal goal : goals) {
			if (getGoalHandler().hasFinishedGoal(goal)) {
			    getGoalHandler().doGoalAction(goal);
			}
		    }
		}
	    }
	}
    }

    public boolean waitForDeath(NPC npc) throws InterruptedException {
	logStatus("Fighting with monster");
	theBotState = BotState.FIGHT;
	while (npc != null && npc.valid() && theBotState.equals(BotState.FIGHT)) {
	    if (!getCombatUtils().isNPCAttacking(getMyPlayer())) {
		if (!underAttack) {
		    NPC mine = null;
		    if ((mine = getMyPlayer().getInteractingNPC()) != null
			    && !mine.equals(npc)) {
			return false;
		    }
		}

		sleep(100);
	    }

	    if (healing) {
		food();
		sleep(100);
	    }

	    if (prayer) {
		prayer(true);
		sleep(100);
	    }

	    if (potions) {
		checkPotions();

		if (checkPoison()) {
		    theBotState = BotState.BANK;
		    return false;
		}

		sleep(100);
	    }

	    if (specialAttack) {
		getCombatUtils().doSpecialAttack(specialAttackWeapon);
		sleep(100);
	    }

	    sleep(100);
	}

	return true;
    }

    public boolean isValidMonster(NPC npc) throws InterruptedException {
	if (npc == null) {
	    return false;
	}

	for (Monster monster : monsters) {
	    if (monster.matches(npc)) {
		return getCombatUtils().isAttackable(npc);
	    }
	}

	return false;
    }

    public void loot() throws InterruptedException {
	lootUtils.collectLoot(lootItems, alchItems, 10, priceMinimum,
		buryBones, scatterAshes, eatForLoot, b2p, telegrabLoot,
		rightClickLoot, centerTile);
    }

    public void checkRangeAmmo() throws InterruptedException {
	if (equipRangedAmmo && equipRange.isUp()) {
	    log("Equipping " + rangedName);
	    getInventoryUtils().doInventory(new ItemContains(rangedName), null,
		    true);
	    equipRange.reset();
	}
    }

    public void bank() throws InterruptedException {
	teleport(true);
	setFastRun(true);

	if (breaking && breaker.hasToBreak()) {
	    log("Taking a break");
	    breaker.doBreak();
	    breaker.nextBreak();
	}

	if (equipRangedAmmo) {
	    equipRange.endTime = System.currentTimeMillis();
	    checkRangeAmmo();
	}

	if (buryBones && theTabs.Inventory.count("bones") > 0) {
	    lootUtils.buryAllBones();
	}

	if (scatterAshes && theTabs.Inventory.count("ashes") > 0) {
	    lootUtils.scatterAshes();
	}

	theBank.doDepositAllBut(getDepositIgnoredItems().toArray(
		new String[getDepositIgnoredItems().size()]));

	if (healing && foodWithdraw > 0) {
	    if (!generalStoreFood) {
		log("Withdrawing " + foodWithdraw + " " + foodName);
		doWithdrawX(foodName, foodWithdraw);
	    }
	}

	if (needJewellery) {
	    log("Withdrawing 1 " + jewellery.getName());
	    doWithdrawX(jewellery.getName(), 1);
	}

	if (teleportTab != null) {
	    log("Withdrawing 1 " + teleportTab.getName());
	    doWithdrawX(teleportTab.getName(), 1);
	}

	Collections.shuffle(Arrays.asList(bankItems));
	for (BankItem bankItem : bankItems) {
	    doWithdrawX(bankItem.getName(), bankItem.getWithdrawAmount());
	    log("Withdrawing " + bankItem.getName() + " "
		    + bankItem.getWithdrawAmount());
	}
	
	if(theMiniMapIndicators.isPoisioned()) {
	    theBank.doWithDraw1("Antipoison", true);
	}

	theBank.exit();

	if (theMiniMapIndicators.isPoisioned()
		&& theTabs.Inventory.count("antipoison") < 1) {
	    throw new DreamError("Poisoned and no anti poisons");
	}

	if (equipJewellery) {
	    if (theTabs.Inventory.count(jewellery.getItemCondition()) < 1) {
		error("There is no " + jewellery.getName()
			+ " in the inventory, shutting down");
		getLogoutHandler().logout(true);
	    }

	    log("Equipping " + jewellery.getName());
	    getInventoryUtils().doInventory(jewellery.getItemCondition(), null,
		    true);
	}

	if (eatAtBank) {
	    log("Restoring health to 100%");
	    getFoodManager().heal(100);
	    sleep(1500);

	    log("Withdrawing more food");
	    doWithdrawX(foodName,
		    foodWithdraw - theTabs.Inventory.count(foodName));
	    theBank.exit();
	}

	if (shutdownOnBank) {
	    log("Banked, now shutting down");
	    getLogoutHandler().logout();
	}

	setFastRun(true);
	theBotState = BotState.WALK;
    }

    public void doWithdrawX(String item, int amount)
	    throws InterruptedException {
	if (theTabs.Inventory.count(item) < amount) {
	    theBank.doWithDrawX(item, amount);
	}
    }

    public boolean hasBankItems() throws InterruptedException {
	for (BankItem bankItem : bankItems) {
	    if (theTabs.Inventory.count(bankItem.getName()) < bankItem
		    .getWithdrawAmount()) {
		return false;
	    }
	}

	if (needJewellery
		&& theTabs.Inventory.count(jewellery.getItemCondition()) < 1) {
	    return false;
	}

	if (teleportTab != null
		&& theTabs.Inventory.count(teleportTab.getName()) < 1) {
	    return false;
	}

	if (healing && foodWithdraw > 0
		&& theTabs.Inventory.count(foodName) < 1) {
	    return false;
	}

	return true;
    }

    public List<String> getDepositIgnoredItems() {
	List<String> items = new ArrayList<String>();

	for (String gear : defaultGear) {
	    items.add(gear);
	}

	if (guthans) {
	    for (String guthanArmor : FoodManager.GUTHANS) {
		items.add(guthanArmor);
	    }
	}

	if (healing && foodName != null) {
	    items.add(foodName);
	}

	if (jewellery != null) {
	    items.add(jewellery.getName());
	}

	if (teleportTab != null) {
	    items.add(teleportTab.getName());
	}

	return items;
    }

    public void teleport(boolean banking) throws InterruptedException {
	boolean teleported = false;

	if ((useJewelleryToBank && banking)
		|| (useJewelleryToMonster && !banking)) {
	    if (equipJewellery) {
		getTabHandler().setTab(theTabs.Equipment);
		needJewellery = theTabs.Equipment.isWearing(jewellery.getName()
			+ " (1)");
	    } else {
		needJewellery = theTabs.Inventory.count(jewellery.getName()
			+ " (1)") > 0;
	    }

	    teleported = getTeleportUtils().doJewelry(jewellery,
		    jewelleryLocation);
	    log(teleported ? "Successfully teleported" : "Failed to teleport");
	} else if ((useTabToBank && banking) || (useTabToMonster && !banking)) {
	    teleported = getTeleportUtils().doTeleportTab(teleportTab);
	    log(teleported ? "Successfully teleported" : "Failed to teleport");
	}
    }

    public boolean validate() throws InterruptedException {
	log("Starting validation checks");

	List<String> errors = new ArrayList<String>();
	List<String> warnings = new ArrayList<String>();
	boolean openUI = false;

	if (monsters == null || monsters.length < 1) {
	    errors.add("You need to add monsters to your list");
	    openUI = true;
	}

	if (multiCombat != getReflectionFields().multiCombat) {
	    multiCombat = getReflectionFields().multiCombat;
	    warnings.add("Multi Combat checkbox should be " + multiCombat
		    + ", corrected");
	}

	getCombatUtils().multi_combat = multiCombat;

	if (distanceFromTile < 1) {
	    errors.add("Invalid input for distance, distance needs to be greater than 0");
	}

	if (healing) {
	    if (healAt < 1) {
		errors.add("Invalid input for healing percentage, needs to be greater than 0");
	    }

	    if (!guthans) {
		if (foodName == null) {
		    errors.add("No selected food name");
		}
	    }

	    if (foodWithdraw < 1) {
		warnings.add("Food withdraw set at 0, open GUI to change this setting");
	    }
	}

	if (b2p || buryBones) {
	    if (lootItems.getCloseMatches("bone").length < 1) {
		warnings.add("Did not add bones to loot list, corrected");
		lootItems.add(masterLootTable.getCloseMatches("bone"));
	    }
	}

	if (scatterAshes) {
	    if (lootItems.getCloseMatches("ash").length < 1) {
		warnings.add("Did not add ashes to loot list, corrected");
		lootItems.add(masterLootTable.getCloseMatches("ash"));
	    }
	}

	if (cleanHerbs || cleanHerbsAndDrop) {
	    if (lootItems.getCloseMatches("grimy").length < 1) {
		warnings.add("Did not add grimy herbs to loot list, corrected");
		lootItems.add(masterLootTable.getCloseMatches("grimy"));
	    }
	}

	if (potions) {
	    /**
	     * boolean bankItemPotions = false; bankItem: for (BankItem bi :
	     * bankItems) { for (Potion potion : Potion.values()) { if
	     * (bi.getName().contains(potion.getName())) { bankItemPotions =
	     * true; break bankItem; } } }
	     * 
	     * if (!bankItemPotions) { warnings.add(
	     * "Did not add potions to bank items list, disabling potions feature"
	     * ); }
	     **/
	}

	if (prayer && prayerName != null) {
	    quickPrayer = prayerName.contains("uick");

	    if (quickPrayer) {
		for (Prayer prayerSpell2 : Prayer.values()) {
		    if (prayerSpell2.name.equalsIgnoreCase(prayerName)) {
			prayerSpell = prayerSpell2;
			break;
		    }
		}
	    }
	}

	if ((useTabToBank || useTabToMonster) && teleportTabName != null) {
	    for (TeleportTab teleportTab2 : TeleportTab.values()) {
		if (teleportTab2.getName().equalsIgnoreCase(teleportTabName)) {
		    teleportTab = teleportTab2;
		    break;
		}
	    }
	}

	if ((useJewelleryToBank || useJewelleryToMonster)
		&& jewelleryName != null) {
	    for (Jewellery jewellery2 : Jewellery.values()) {
		if (jewellery2.getName().equalsIgnoreCase(jewelleryName)) {
		    jewellery = jewellery2;
		    break;
		}
	    }
	}

	StringBuilder sb = new StringBuilder();
	if (errors.size() > 0) {
	    for (String error : errors) {
		sb.append(error + "\n");
	    }

	    error(sb.toString());

	    JOptionPane.showMessageDialog(null, sb.toString(), "Found "
		    + errors.size() + " errors in your setup",
		    JOptionPane.ERROR_MESSAGE);
	}

	if (warnings.size() > 0) {
	    sb = new StringBuilder();
	    for (String warning : warnings) {
		sb.append(warning + "\n");
	    }

	    warn(sb.toString());

	    JOptionPane.showMessageDialog(null, sb.toString(), "Found "
		    + warnings.size() + " warnings in your setup",
		    JOptionPane.WARNING_MESSAGE);
	}

	if (openUI) {
	    sleep(1000);
	    frame.setVisible(true);
	}

	return errors.size() == 0;
    }

    public void takeScreenshot(boolean buttonClicked) {
	if (buttonClicked
		|| (takeScreenshots && screenshotTimer.isUpThenReset())) {

	}
    }

    public void checkEquipment() throws InterruptedException {
	String weaponName = theTabs.Equipment.getWeapon();
	String arrowsName = theTabs.Equipment.getArrows();

	if (weaponName == null) {
	    getTabHandler().setTab(theTabs.Equipment);

	    weaponName = theTabs.Equipment.getWeapon();
	    arrowsName = theTabs.Equipment.getArrows();
	}

	if (weaponName != null
		&& (weaponName.contains("bow") || weaponName.equals("seercull"))) {
	    log("Using a ranged weapon " + weaponName);
	    if (equipRangedAmmo && arrowsName != null) {
		lootItems.add(masterLootTable.getItems(arrowsName
			+ "(stackable)"));
		rangedName = arrowsName;
		log("Arrows/Bolts will be picked up: " + arrowsName);
	    }
	} else if (weaponName != null
		&& (weaponName.contains("knife") || weaponName.contains("dart") || weaponName
			.contains("chompa"))) {
	    log("Using thrown weapon " + weaponName);
	    if (equipRangedAmmo && arrowsName != null) {
		lootItems.add(masterLootTable.getItems(weaponName
			+ "(stackable)"));
		rangedName = arrowsName;
		log("Arrows/Bolts will be picked up: " + weaponName);
	    }
	} else if (weaponName != null) {
	    log("Using melee weapon: " + weaponName);
	}

	defaultGear = getEquipmentUtils().getAllGear();
	getTabHandler().setTab(theTabs.Inventory);
    }

    public boolean hasAuth(int pid) {
	for (int product : SettingsManager.getProductIds()) {
	    if (product == pid) {
		return true;
	    }
	}

	return false;
    }

    public void startUp() throws InterruptedException, Exception {
	brandValues = new Brand[] {
		new Brand(1, "www.modernvirtualtechnologies.com",
			"AutoFighterPro", "ak4322", 193),
		new Brand(703, "www.rsbots.net", "AutoFighterPro", "ak4322",
			101),
		new Brand(71, "www.rsbots.net", "AutoFighterPro", "ak4322", 101),
		new Brand(712, "www.rs2botting.com", "Fighting Bot", "ak4322",
			101),
		new Brand(657, "www.oembots.com", "DreamFighter", "ak4322", 101) };

	rebrand();
	titleText = new DefaultPaintText(10, 444, name + " V" + version,
		new String[] { "Pro" }, colors[colorIndex], Color.WHITE,
		new Color(0, 0, 0, 160), Color.BLACK, true, false, false);

	setUpFiles();
	initLogger();

	container.setWidthOffset(25);

	logStatus("Finished loading " + masterLootTable.getIDs().length
		+ " items");
	frame = new DreamFighterUI(this);

	logStatus("Opening user interface");
	frame.setVisible(true);
	frame.repaint();

	generalThread = new GeneralThread(name + " Watcher", this);

	paintComponents.add(loggerButton);
	paintComponents.add(guiButton);
	paintComponents.add(screenshotButton);
	paintComponents.add(themeButton);
	paintComponents.add(resetButton);

	while (!startScript || !isLoggedIn()) {
	    sleep(2000);

	    if (!startScript) {
		log("Waiting for user to start the bot");
	    } else if (!isLoggedIn()) {
		log("Waiting for user to login");
	    }
	}

	validate();

	loot = lootItems.getSize() > 0;

	if (mouseSpeedBoost > 0) {
	    log("Boosting mouse speed by " + mouseSpeedBoost + "%");
	    getColorBot().currentSpeed = MouseSpeedHandler
		    .boostMouse(mouseSpeedBoost);
	}

	getEquipmentUtils().masterLootTable = masterLootTable;
	getExperienceHandler().startAllExperiences(this);
	getPotionUtils().randomFrequency();

	if (usingGoals) {
	    for (LevelGoal goal : goals) {
		if (getGoalHandler().hasFinishedGoal(goal)) {
		    error("Remember to remove your old level goals on next run");
		    getGoalHandler().finished(goal);
		}
	    }
	}

	checkEquipment();
    }

    public void logStatus(String log) {
	status = log;
	log(status);
    }

    @Override
    public ItemCondition getItemsToDeposit() {
	return new ItemCondition() {
	    @Override
	    public boolean validItem(String item) {
		return new ItemContainsIgnore("", getDepositIgnoredItems()
			.toArray(new String[100])).validItem(item);
	    }
	};
    }

    private ChatString previousChat = null;

    @Override
    public void chat(String message) throws InterruptedException {
	ChatString chat = new ChatString(message);
	if (previousChat != null
		&& previousChat.getMessage()
			.equalsIgnoreCase(chat.getMessage())) {
	    return;
	}

	if (chat.isGameChat()) {
	    if (chat.getMessage().equalsIgnoreCase("I'm already under attack.")) {
		underAttack = true;
	    } else if (chat.getMessage().equalsIgnoreCase(
		    "There is no ammo left in your quiver.")) {
		throw new DreamError("Out of ranged ammo");
	    } else if (chat.getMessage().contains("Oh dear, you are dead!")) {
		log("We died, walking back to center location");
		totalDeaths++;
		theBotState = BotState.WALK;
	    }
	}
    }

    StripedPaintContainer container = new StripedPaintContainer(Color.BLACK,
	    Color.BLACK, new Rectangle(6, 344, 245, 113));
    DefaultPaintText takescreenText = new DefaultPaintText(5, 16,
	    "Take screenshot");
    Logger logger = null;

    private DefaultPaintButton screenshotButton = new DefaultPaintButton(276,
	    441, "Screenshot", Color.WHITE, colors[colorIndex], Color.BLACK) {
	@Override
	public void mouseReleased(MouseEvent e) {

	}
    };
    private DefaultPaintButton themeButton = new DefaultPaintButton(276, 441,
	    ">Theme", Color.WHITE, colors[colorIndex], Color.BLACK) {
	@Override
	public void mouseReleased(MouseEvent e) {
	    logger.next();
	    logger.init();

	    if (colorIndex > 4) {
		colorIndex = 0;
	    } else {
		colorIndex++;
	    }

	    for (PaintComponent component : paintComponents) {
		if (component instanceof DefaultPaintButton) {
		    ((DefaultPaintButton) component)
			    .setColor(colors[colorIndex]);
		}
	    }
	}
    };
    private DefaultPaintButton loggerButton = new DefaultPaintButton(276, 441,
	    "Swap Logger", Color.WHITE, colors[colorIndex], Color.BLACK) {
	@Override
	public void mouseReleased(MouseEvent e) {
	    logger.changeLogger();
	}
    };
    private DefaultPaintButton guiButton = new DefaultPaintButton(276, 441,
	    "UI", Color.WHITE, colors[colorIndex], Color.BLACK) {
	@Override
	public void mouseReleased(MouseEvent e) {
	    frame.setVisible(true);
	}
    };
    private DefaultPaintButton resetButton = new DefaultPaintButton(276, 441,
	    "   Reset    ", Color.WHITE, colors[colorIndex], Color.BLACK) {
	@Override
	public void mouseReleased(MouseEvent e) {
	    totalCash = 0;
	    totalKills = 0;
	    totalDeaths = 0;
	}
    };
    private DefaultPaintText statusText = new DefaultPaintText(6, 331, status,
	    null, colors[colorIndex], Color.WHITE, new Color(0, 0, 0, 160),
	    Color.BLACK, true, false, true);
    private DefaultPaintText killsText = new DefaultPaintText(6, 331, status,
	    null, colors[colorIndex], Color.WHITE, new Color(0, 0, 0, 160),
	    Color.BLACK, true, false, true);
    private DefaultPaintText lootText = new DefaultPaintText(6, 331, status,
	    null, colors[colorIndex], Color.WHITE, new Color(0, 0, 0, 160),
	    Color.BLACK, true, false, true);
    private DefaultPaintText deathsText = new DefaultPaintText(6, 331, status,
	    null, colors[colorIndex], Color.WHITE, new Color(0, 0, 0, 160),
	    Color.BLACK, true, false, true);
    private DefaultPaintText titleText = new DefaultPaintText(182, 355, name
	    + " V" + version, new String[] { "Pro" }, colors[colorIndex],
	    Color.WHITE, new Color(0, 0, 0, 160), Color.BLACK, true, false,
	    false);
    private DefaultPaintText timerText = new DefaultPaintText(182, 355, name
	    + " V" + version, new String[] { "Pro" }, colors[colorIndex],
	    Color.WHITE, new Color(0, 0, 0, 160), Color.BLACK, true, false,
	    false);

    public void initLogger() {
	lootLogLocation = name + File.separator + "statistics" + File.separator
		+ "loot.log";
	killLogLocation = name + File.separator + "statistics" + File.separator
		+ "kills.log";

	lootManager = new LootManager(this);
	killManager = new KillManager(this);

	lootManager.load(io);
	killManager.load(io);

	logger = new Logger(name + " V" + version + " ", 212, 4, lootManager,
		killManager, formatter, name);
	logger.init();
    }

    public void setUpFiles() throws InterruptedException {
	fileManager.createDirectory(name);
	fileManager.createDirectory(name + IOManager.SEPARATOR
		+ IOManager.DOWNLOADS_FOLDER);
	fileManager.createDirectory(name + IOManager.SEPARATOR
		+ IOManager.STATISTICS_FOLDER);
	fileManager.createDirectory(name + IOManager.SEPARATOR
		+ IOManager.SETTINGS_FOLDER);
	fileManager.createDirectory(name + IOManager.SEPARATOR
		+ IOManager.SCREENSHOT_FOLDER);

	itemDataDownload = new Download(IOManager.ITEM_DATA_URL, name
		+ IOManager.SEPARATOR + IOManager.DOWNLOADS_FOLDER);
	itemDataDownload.download();

	fontDownload = new Download(IOManager.EPOXY_URL, name
		+ IOManager.SEPARATOR + IOManager.DOWNLOADS_FOLDER);
	fontDownload.download();

	logStatus("Downloading item data");
	try {
	    IOManager.getLoggerImages(name);

	    int downloadStatus = itemDataDownload.getStatus();
	    while ((downloadStatus = itemDataDownload.getStatus()) != Download.CANCELLED
		    && downloadStatus != Download.COMPLETE
		    && downloadStatus != Download.ERROR) {
		sleep(1000);
	    }

	    masterLootTable.add(LootItemLoader
		    .getLootItemsFromStream(fileManager.getInputStream(name
			    + IOManager.ITEM_DATA_FILE)));
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private Color attackColor = new Color(66, 11, 6, 220);
    private Color rangedColor = new Color(73, 97, 20, 220);
    private Color magicColor = new Color(9, 12, 94, 220);
    private Color hitpointColor = new Color(236, 113, 25, 220);
    private Color defenseColor = new Color(89, 109, 175, 220);
    private Color strengthColor = new Color(5, 60, 30, 220);
    private Color slayerColor = new Color(8, 8, 8, 220);

    public void paint(Graphics g) {
	if (paint) {
	    Graphics2D g2 = (Graphics2D) g;

	    g2.setRenderingHint(RenderingHints.KEY_RENDERING,
		    RenderingHints.VALUE_RENDER_QUALITY);
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		    RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
		    RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
	    g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
		    RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	    g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
		    RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		    RenderingHints.VALUE_INTERPOLATION_BICUBIC);

	    try {
		if (showDistanceZone && isLoggedIn()) {
		    if (centerTile != null) {
			XY cent = theMiniMap.toMiniMapReflection(centerTile);
			if (cent != null) {
			    int radius = distanceFromTile;
			    XY radiusEdge = theMiniMap
				    .toMiniMapReflection(new AryanTile(
					    centerTile.x + radius, centerTile.y));
			    int r = (int) Math.sqrt(Math.pow(cent.x
				    - radiusEdge.x, 2D)
				    + Math.pow(cent.y - radiusEdge.y, 2D));
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

	    container.paint(g);

	    if (logger != null) {
		logger.paint(g);
	    }

	    screenshotButton.paint(g);
	    themeButton.setLocation(screenshotButton.getBounds().x
		    + screenshotButton.getBounds().width,
		    screenshotButton.getY());
	    loggerButton.setLocation(
		    themeButton.getBounds().x + themeButton.getBounds().width,
		    themeButton.getY());
	    guiButton
		    .setLocation(
			    loggerButton.getBounds().x
				    + loggerButton.getBounds().width,
			    loggerButton.getY());
	    resetButton.setLocation(
		    guiButton.getBounds().x + guiButton.getBounds().width,
		    guiButton.getY());

	    themeButton.paint(g);
	    loggerButton.paint(g);
	    guiButton.paint(g);
	    resetButton.paint(g);
	    statusText = new DefaultPaintText(15, 362, status, null,
		    colors[colorIndex], Color.WHITE, new Color(0, 0, 0, 160),
		    Color.BLACK, false, false, false);
	    statusText.paint(g);

	    killsText = new DefaultPaintText(15, 383, "Kills: "
		    + formatter.formatValue(totalKills) + " ("
		    + formatter.getHourlyValueFormatted(totalKills, timeRan)
		    + "/h)" + " kills", null, colors[colorIndex], Color.WHITE,
		    new Color(0, 0, 0, 160), Color.BLACK, true, false, false);
	    killsText.paint(g);

	    lootText = new DefaultPaintText(15, 404, "Looted: $"
		    + formatter.formatValue(totalCash) + " ("
		    + formatter.getHourlyValueFormatted(totalCash, timeRan)
		    + "/h)", null, colors[colorIndex], Color.WHITE, new Color(
		    0, 0, 0, 160), Color.BLACK, true, false, false);
	    lootText.paint(g);

	    deathsText = new DefaultPaintText(15, 425, "Deaths: "
		    + formatter.formatValue(totalDeaths) + " ("
		    + formatter.getHourlyValueFormatted(totalDeaths, timeRan)
		    + "/h)" + " deaths", null, colors[colorIndex], Color.WHITE,
		    new Color(0, 0, 0, 160), Color.BLACK, true, false, false);
	    deathsText.paint(g);

	    timerText = new DefaultPaintText(15, 446, "Time ran: "
		    + timeRan.toStringTimeElapsed(), null, colors[colorIndex],
		    Color.WHITE, new Color(0, 0, 0, 160), Color.BLACK, true,
		    false, false);
	    timerText.paint(g);

	    titleText = new DefaultPaintText(6, 331, name + " V" + version,
		    new String[] { "Pro" }, colors[colorIndex], Color.WHITE,
		    new Color(0, 0, 0, 160), Color.BLACK, true, false, false);
	    titleText.paint(g);
	    // testBar.paint(g);
	    // drawProgressBar(double percent, Experience experience,
	    // Formatter format, Timer timeRan, Graphics g)

	    try {
		if (attack != null) {
		    int add = 1;

		    if (attack.getTotalXPGained() > 0) {
			new FighterProgressBar(277, 329 + (15 * add), 50,
				false, Color.BLACK, new Color(
					attackColor.getRed(),
					attackColor.getGreen(),
					attackColor.getBlue(), 180),
				Color.WHITE, "").drawProgressBar(
				attack.getPercentToLevel(), attack, formatter,
				timeRan, g2);
			add++;
		    }

		    if (strength.getTotalXPGained() > 0) {
			new FighterProgressBar(277, 329 + (15 * add), 50,
				false, Color.BLACK, new Color(
					strengthColor.getRed(),
					strengthColor.getGreen(),
					strengthColor.getBlue(), 180),
				Color.WHITE, "").drawProgressBar(
				strength.getPercentToLevel(), strength,
				formatter, timeRan, g2);

			add++;
		    }

		    if (defense.getTotalXPGained() > 0) {
			new FighterProgressBar(277, 329 + (15 * add), 50,
				false, Color.BLACK, new Color(
					defenseColor.getRed(),
					defenseColor.getGreen(),
					defenseColor.getBlue(), 180),
				Color.WHITE, "").drawProgressBar(
				defense.getPercentToLevel(), defense,
				formatter, timeRan, g2);
		    }

		    if (magic.getTotalXPGained() > 0) {
			new FighterProgressBar(277, 329 + (15 * add), 50,
				false, Color.BLACK, new Color(
					magicColor.getRed(),
					magicColor.getGreen(),
					magicColor.getBlue(), 180),
				Color.WHITE, "").drawProgressBar(
				magic.getPercentToLevel(), magic, formatter,
				timeRan, g2);
		    }

		    if (range.getTotalXPGained() > 0) {
			new FighterProgressBar(277, 329 + (15 * add), 50,
				false, Color.BLACK, new Color(
					rangedColor.getRed(),
					rangedColor.getGreen(),
					rangedColor.getBlue(), 180),
				Color.WHITE, "").drawProgressBar(
				range.getPercentToLevel(), range, formatter,
				timeRan, g2);
		    }
		}

	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

	g.dispose();
    }

    @Override
    public boolean mouse(MouseEvent mouse) {
	Point point = mouse.getPoint();
	boolean ignoreEvent = false;

	if (loggerLocation.contains(mouse.getX(), mouse.getY())) {
	    logger.mouseEvent(mouse);
	    ignoreEvent = true;
	}

	if (paintComponents.size() > 0) {
	    for (PaintComponent component : paintComponents) {
		if (component.getBounds().contains(point)) {
		    component.mouseEvent(mouse);
		    ignoreEvent = true;
		}
	    }
	}

	return ignoreEvent;
    }

    @Override
    public Image getIconImage() {
	try {
	    return new Image(SWTThread.theDisplay, new kCachedFileLoader(
		    "http://www.autofighter.ak4322.info/images/afpicon.png",
		    "afpicon.png").getURL().openConnection().getInputStream());
	} catch (IOException e) {
	    e.printStackTrace();
	}

	return null;
    }

    public void getToolbarValues() {
	toolbar.getDisplay().syncExec(new Runnable() {
	    @Override
	    public void run() {
		eatAtBank = eatBank.getSelection();
		restAtBank = restBank.getSelection();
		shutdownOnBank = shutdownBank.getSelection();

		buryBones = lootBury.getSelection();
		scatterAshes = lootScatter.getSelection();
		rightClickLoot = lootRightClick.getSelection();
		telegrabLoot = lootTelegrab.getSelection();
		eatForLoot = lootEat.getSelection();

		multiCombat = npcMultiCombat.getSelection();
		invisibleOnMiniMap = npcInvisible.getSelection();
		unreachable = npcBlocked.getSelection();
	    }
	});
    }

    public void setToolbarValues() {
	toolbar.getDisplay().syncExec(new Runnable() {
	    @Override
	    public void run() {
		paintNPCs.setSelection(true);
		paintGround.setSelection(true);
		paintMain.setSelection(true);
		paintLogger.setSelection(true);

		eatBank.setSelection(eatAtBank);
		restBank.setSelection(restAtBank);
		shutdownBank.setSelection(shutdownOnBank);

		lootBury.setSelection(buryBones);
		lootScatter.setSelection(scatterAshes);
		lootRightClick.setSelection(rightClickLoot);
		lootTelegrab.setSelection(telegrabLoot);
		lootEat.setSelection(eatForLoot);

		npcMultiCombat.setSelection(multiCombat);
		npcInvisible.setSelection(invisibleOnMiniMap);
		npcBlocked.setSelection(unreachable);

		screenshot.setSelection(false);
	    }
	});
    }

    private org.eclipse.swt.widgets.ToolBar toolbar;
    private ToolItem itemDropDown;

    org.eclipse.swt.widgets.Menu menu = null;
    MenuItem paintOptions;
    org.eclipse.swt.widgets.Menu paintMenu;
    private MenuItem paintNPCs;
    private MenuItem paintGround;
    private MenuItem paintMain;
    private MenuItem paintLogger;

    private MenuItem bankOptions;
    private org.eclipse.swt.widgets.Menu bankMenu;
    private MenuItem eatBank;
    private MenuItem restBank;
    private MenuItem shutdownBank;
    private MenuItem forceBank;

    private MenuItem lootOptions;
    private org.eclipse.swt.widgets.Menu lootMenu;
    private MenuItem lootBury;
    private MenuItem lootScatter;
    private MenuItem lootRightClick;
    private MenuItem lootTelegrab;
    private MenuItem lootEat;

    private MenuItem npcOptions;
    private org.eclipse.swt.widgets.Menu npcMenu;
    private MenuItem npcMultiCombat;
    private MenuItem npcInvisible;
    private MenuItem npcBlocked;

    private MenuItem miscOptions;
    private org.eclipse.swt.widgets.Menu miscMenu;
    private MenuItem screenshot;
    private MenuItem ui;
    private MenuItem changeTheme;

    @Override
    public void addToolItem(final ToolBar toolbar) {
	this.toolbar = toolbar;

	menu = new org.eclipse.swt.widgets.Menu(toolbar.getShell(), SWT.POP_UP);

	bankOptions = new MenuItem(menu, SWT.CASCADE);
	npcOptions = new MenuItem(menu, SWT.CASCADE);
	paintOptions = new MenuItem(menu, SWT.CASCADE);
	miscOptions = new MenuItem(menu, SWT.CASCADE);
	lootOptions = new MenuItem(menu, SWT.CASCADE);

	bankMenu = new org.eclipse.swt.widgets.Menu(bankOptions);
	lootMenu = new org.eclipse.swt.widgets.Menu(lootOptions);
	npcMenu = new org.eclipse.swt.widgets.Menu(npcOptions);
	miscMenu = new org.eclipse.swt.widgets.Menu(miscOptions);
	paintMenu = new org.eclipse.swt.widgets.Menu(paintOptions);

	paintNPCs = new MenuItem(paintMenu, SWT.CHECK);
	paintGround = new MenuItem(paintMenu, SWT.CHECK);
	paintMain = new MenuItem(paintMenu, SWT.CHECK);
	paintLogger = new MenuItem(paintMenu, SWT.CHECK);

	eatBank = new MenuItem(bankMenu, SWT.CHECK);
	restBank = new MenuItem(bankMenu, SWT.CHECK);
	shutdownBank = new MenuItem(bankMenu, SWT.CHECK);
	forceBank = new MenuItem(bankMenu, SWT.BUTTON1);
	forceBank.addSelectionListener(new SelectionListener() {
	    @Override
	    public void widgetSelected(SelectionEvent arg0) {
		theBotState = BotState.BANK;
		menu.setVisible(true);
	    }

	    @Override
	    public void widgetDefaultSelected(SelectionEvent arg0) {
		theBotState = BotState.BANK;
		menu.setVisible(true);
	    }
	});

	lootBury = new MenuItem(lootMenu, SWT.CHECK);
	lootScatter = new MenuItem(lootMenu, SWT.CHECK);
	lootRightClick = new MenuItem(lootMenu, SWT.CHECK);
	lootTelegrab = new MenuItem(lootMenu, SWT.CHECK);
	lootEat = new MenuItem(lootMenu, SWT.CHECK);

	npcMultiCombat = new MenuItem(npcMenu, SWT.CHECK);
	npcInvisible = new MenuItem(npcMenu, SWT.CHECK);
	npcBlocked = new MenuItem(npcMenu, SWT.CHECK);

	screenshot = new MenuItem(miscMenu, SWT.CHECK);
	ui = new MenuItem(miscMenu, SWT.BUTTON1);
	ui.addSelectionListener(new SelectionListener() {
	    @Override
	    public void widgetSelected(SelectionEvent arg0) {
		frame.setVisible(true);
		menu.setVisible(true);
	    }

	    @Override
	    public void widgetDefaultSelected(SelectionEvent arg0) {
		frame.setVisible(true);
		menu.setVisible(true);
	    }
	});

	changeTheme = new MenuItem(miscMenu, SWT.BUTTON1);
	changeTheme.addSelectionListener(new SelectionListener() {
	    @Override
	    public void widgetSelected(SelectionEvent arg0) {
		logger.next();
		logger.init();
		menu.setVisible(true);
	    }

	    @Override
	    public void widgetDefaultSelected(SelectionEvent arg0) {
		logger.next();
		logger.init();
		menu.setVisible(true);
	    }
	});

	itemDropDown = new ToolItem(toolbar, SWT.PUSH);
	itemDropDown.setToolTipText("AutoFighter Pro Settings");
	itemDropDown.setImage(new org.eclipse.swt.graphics.Image(toolbar
		.getDisplay(), new kCachedFileLoader(
		"http://www.autofighter.ak4322.info/images/afpicon.png")
		.getURL().getPath()));

	paintOptions.setText("Paint Options");
	paintOptions.setMenu(paintMenu);
	paintNPCs.setText("Paint NPCs");
	paintGround.setText("Paint Ground Items");
	paintMain.setText("Paint main");
	paintLogger.setText("Paint logger");

	bankOptions.setText("Bank Options");
	bankOptions.setMenu(bankMenu);
	eatBank.setText("Eat at bank");
	restBank.setText("Rest at bank");
	shutdownBank.setText("Shutdown on bank");
	forceBank.setText("Bank now");

	lootOptions.setText("Loot Options");
	lootOptions.setMenu(lootMenu);
	lootBury.setText("Bury bones");
	lootScatter.setText("Scatter ashes");
	lootRightClick.setText("Right click only");
	lootTelegrab.setText("Telegrab loot");
	lootEat.setText("Eat food for loot");

	npcOptions.setText("NPC Options");
	npcOptions.setMenu(npcMenu);
	npcMultiCombat.setText("Multi combat");
	npcInvisible.setText("Invisible on minimap");
	npcBlocked.setText("Blocked by obstacle");

	miscOptions.setText("Misc Options");
	miscOptions.setMenu(miscMenu);
	screenshot.setText("Take screenshot");
	ui.setText("Open UI");
	changeTheme.setText("Change theme");

	itemDropDown.addListener(SWT.Selection,
		new org.eclipse.swt.widgets.Listener() {
		    public void handleEvent(Event event) {
			org.eclipse.swt.graphics.Rectangle bounds = itemDropDown
				.getBounds();
			org.eclipse.swt.graphics.Point point = toolbar
				.toDisplay(bounds.x, bounds.y + bounds.height);
			menu.setLocation(point);
			menu.setVisible(true);
		    }
		});

	org.eclipse.swt.graphics.Point oldSize = toolbar.getShell().getSize();
	toolbar.getShell().pack(false);
	toolbar.getShell().setSize(oldSize);
    }

    @Override
    public void removeToolItem() {
	itemDropDown.dispose();
	toolbar.update();

	org.eclipse.swt.graphics.Point oldSize = toolbar.getShell().getSize();
	toolbar.getShell().pack(false);
	toolbar.getShell().setSize(oldSize);
    }
}
