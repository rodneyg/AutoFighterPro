package gui;

import impsoft.scripting.ibot.enums.Prayer;
import impsoft.utils.general.Timer;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import looting.LootItem;
import teleporting.Jewellery;
import teleporting.TeleportTab;

import combat.Monster;

import base.BankItem;
import base.DreamScript;
import base.DreamUtility;
import base.Fighter;
import breaking.Break;
import breaking.BreakHandler;
import data.FileManager;
import data.IOManager;

public class ProfileHandler extends DreamUtility {
    private static IOManager IO = new IOManager();

    public ProfileHandler(DreamScript cs) {
	super(cs);
    }

    public static void convert() {

    }

    public static void load(String name, Fighter fighter) {
	FileManager files = IO.getFileManager();
	String[] values = files.getStringArray(fighter.name + IOManager.SETTINGS_FOLDER + name + IOManager.PROFILE_EXTENSION);
	ArrayList<String> valueList = new ArrayList<String>();

	for(String value : values) {
	    valueList.add(value);
	}
	
	try {
	    new UserInterfaceThread(this, mainProgressBar, new Timer(5000)).start();

	    Break reccurring = new Break(Integer.parseInt(breakAtField.getText()),
		    Integer.parseInt(breakForField.getText()), fighter.random(10));
	    Break[] breaks = new Break[200];

	    for (int i = 0; i < 200; i++) {
		breaks[i] = reccurring;
	    }

	    fighter.breaker = new BreakHandler(breaks, fighter.getLogoutHandler());
	    fighter.breaking = true;
	} catch (NumberFormatException e) {
	    e.printStackTrace();
	}

	fighter.frame.zoneSpinner.setValue(fighter.distanceFromTile);
	fighter.frame.mouseKeyCheck.setSelected(fighter.mouseKeys);

	foodSlider
	fighter.foodWithdraw = Integer.parseInt(foodWithdrawSpinner.getValue().toString());
	fighter.eatForLoot = eatForLootCheck.isSelected();
	fighter.eatAtBank = eatAtBankCheck.isSelected();
	fighter.guthans = guthansCheck.isSelected();
	fighter.b2p = b2pCheck.isSelected();
	fighter.generalStoreFood = denoteCheck.isSelected();
	fighter.foodName = foodCombo.getSelectedItem().toString();
	fighter.healing = !fighter.foodName.equals("Not eating");

	fighter.equipRangedAmmo = rangedCheck.isSelected();
	fighter.safespot = safeSpotsCheck.isSelected();

	fighter.buryBones = buryBonesCheck.isSelected();
	fighter.scatterAshes = sactterAshesCheck.isSelected();
	fighter.waitForLoot = waitLootCheck.isSelected();
	fighter.telegrabLoot = telegrabCheck.isSelected();
	fighter.rightClickLoot = rightClickCheck.isSelected();
	// TODO: finish loot and alch

	try {
	    fighter.priceMinimum = Integer.parseInt(lootOverField.getText());
	} catch (NumberFormatException e) {
	    e.printStackTrace();
	}

	fighter.cleanHerbs = cleanHerbsCheck.isSelected();
	fighter.cleanHerbsAndDrop = dropHerbsCheck.isSelected();

	fighter.hoverTarget = hoverCheck.isSelected();
	fighter.specialAttack = specialAttackCheck.isSelected();

	if (specialWeaponCombo.getSelectedIndex() != 0) {
	    fighter.specialAttackWeapon = specialWeaponCombo.getSelectedItem().toString();
	}

	if (teleportBankCheck.isSelected()) {
	    String selectedItem = teleportCombo.getSelectedItem().toString();

	    for (Jewellery jewellery : Jewellery.values()) {
		if (selectedItem.equalsIgnoreCase(jewellery.getName())) {
		    fighter.jewellery = jewellery;
		    fighter.jewelleryName = jewellery.getName();
		    fighter.jewelleryLocation = teleportLocationCombo.getSelectedItem().toString();
		    fighter.useJewelleryToBank = true;
		    break;
		}
	    }

	    for (TeleportTab teleportTab : TeleportTab.values()) {
		if (selectedItem.equalsIgnoreCase(teleportTab.getName())) {
		    fighter.teleportTab = teleportTab;
		    fighter.teleportTabName = teleportTab.getName();
		    fighter.useTabToBank = true;
		    break;
		}
	    }

	    fighter.equipJewellery = keepEquippedCheck.isSelected();
	}

	if (teleportMonsterCheck.isSelected()) {
	    String selectedItem = teleportCombo2.getSelectedItem().toString();

	    for (Jewellery jewellery : Jewellery.values()) {
		if (selectedItem.equalsIgnoreCase(jewellery.getName())) {
		    fighter.jewellery = jewellery;
		    fighter.jewelleryName = jewellery.getName();
		    fighter.jewelleryLocation = teleportLocationCombo2.getSelectedItem().toString();
		    fighter.useJewelleryToBank = true;
		    break;
		}
	    }

	    for (TeleportTab teleportTab : TeleportTab.values()) {
		if (selectedItem.equalsIgnoreCase(teleportTab.getName())) {
		    fighter.teleportTab = teleportTab;
		    fighter.teleportTabName = teleportTab.getName();
		    fighter.useTabToBank = true;
		    break;
		}
	    }

	    fighter.equipJewellery = keepEquippedCheck2.isSelected();
	}

	fighter.invisibleOnMiniMap = invisibleCheck.isSelected();
	fighter.unreachable = blockedCheck.isSelected();

	if (prayerCheck.isSelected()) {
	    fighter.prayer = true;
	    fighter.prayAtAltar = prayerAltarButton.isSelected();

	    if (prayerCombo.getSelectedIndex() == 0) {
		fighter.quickPrayer = true;
	    }

	    for (Prayer prayer : Prayer.values()) {
		if (prayer.name.equalsIgnoreCase(prayerCombo.getSelectedItem().toString())) {
		    fighter.prayerSpell = prayer;
		    break;
		}
	    }
	}

	List<Monster> monsters = new ArrayList<Monster>();
	for (int i = 0; i < addMonstersList.getModel().getSize(); i++) {
	    String element = addMonstersList.getModel().getElementAt(i).toString();

	    if (element.contains("id=")) {
		String[] strs = element.split(", ");
		monsters.add(new Monster(strs[0], Integer.parseInt(strs[1].replace("id=", ""))));
	    }
	}

	fighter.monsters = monsters.toArray(new Monster[monsters.size()]);

	if (fighter.centerTile == null) {
	    JOptionPane.showMessageDialog(null, "Set the center tile in the UI",
		    "You need to set center tile", JOptionPane.ERROR_MESSAGE);
	    return;
	}

	fighter.lootItems.clear();
	fighter.alchItems.clear();

	if (bankList.getSize() > 0) {
	    for (int i = 0; i < bankList.getSize(); i++) {
		BankItem element = (BankItem) bankList.getElementAt(i);
	    }
	}

	if (lootList.getSize() > 0) {
	    for (int i = 0; i < lootList.getSize(); i++) {
		fighter.lootItems.add((LootItem) lootList.getElementAt(i));
	    }
	}

	if (alchList.getSize() > 0) {
	    for (int i = 0; i < alchList.getSize(); i++) {
		fighter.alchItems.add((LootItem) alchList.getElementAt(i));
	    }
	}
    }
    
    //		return id + ":" + name + ":(" + Arrays.toString(ignoreLevels) + ")" ;
    public void setValue(String value, DreamFighterUI ui) {
	if(value.contains("Monsters:")) {
	     String[] monsters = value.replace("Monsters:[", "").replace("]", "").split(",");
	     
	     for(String monster : monsters) {
		 
	     }
	}
    }
    

    public static void save(String name, Fighter fighter) {
	ArrayList<String> values = new ArrayList<String>();

	values.add("Monsters:[" + Arrays.toString(fighter.monsters) + "]");
	values.add("Slayer:" + fighter.slayerEnabled);
	values.add("Center tile:" + fighter.centerTile.toString());
	values.add("Zone size:" + fighter.distanceFromTile);
	values.add("Mouse keys:" + true);
	values.add("Heal at:" + fighter.healAt);
	values.add("Food withdraw:"+ fighter.foodWithdraw);
	values.add("Eat for loot:" + fighter.eatForLoot);
	values.add("Eat at bank:" + fighter.eatAtBank);
	values.add("Guthans:" + fighter.guthans);
	values.add("B2P:" + fighter.b2p);
	values.add("General store food:" + fighter.generalStoreFood);
	values.add("Food name:" + fighter.foodName);
	values.add("Screenshots:" + fighter.takeScreenshots);
	values.add("Taskbar notifications:" + fighter.taskbarNotifications);
	values.add("Mouse boost:" + fighter.mouseSpeedBoost);
	values.add("Goals:[" + Arrays.toString(fighter.goals) + "]");
	values.add("Equip range:" + fighter.equipRange);
	values.add("Safespot:" + fighter.safespot);
	values.add("Bury bones:" + fighter.buryBones);
	values.add("Scatter ashes:" + fighter.scatterAshes);
	values.add("Wait for loot:" + fighter.waitForLoot);
	values.add("Lootshare:" + fighter.lootshare);
	values.add("Telegrab:" + fighter.telegrabLoot);
	values.add("Right click loot:" + fighter.rightClickLoot);
	values.add("Price min:" + fighter.priceMinimum);
	values.add("Clean herbs:" + fighter.cleanHerbs);
	values.add("Alch:[" + Arrays.toString(fighter.alchItems.getCloseMatches("")) + "]");
	values.add("Hover target:" + fighter.hoverTarget);
	values.add("Special attack:" + fighter.specialAttack);
	values.add("Special attack weapon:" + fighter.specialAttackWeapon);
	values.add("Jewellery bank:" + fighter.useJewelleryToBank);
	values.add("Jewellery monster:" + fighter.useJewelleryToMonster);
	values.add("Equip Jewellery:" + fighter.equipJewellery);
	values.add("Jewellery name:"+ fighter.jewelleryName);
	values.add("Jewellery location:" + fighter.jewelleryLocation);
	values.add("Tab to bank:" + fighter.useTabToBank);
	values.add("Tab to monster:" + fighter.useTabToMonster);
	values.add("Teleport tab:" + fighter.teleportTabName);
	values.add("Show distance zone:" + fighter.showDistanceZone);
	values.add("Hidden on minimap:" + fighter.invisibleOnMiniMap);
	values.add("Unreachable:" + fighter.unreachable);
	values.add("Multi combat:" + fighter.multiCombat);
	values.add("Prayer name:" + fighter.prayerName);
	values.add("Toggle prayer:" + fighter.togglePrayer);
	values.add("Pray at altar:" + fighter.prayAtAltar);
	values.add("Quick prayer:" + fighter.quickPrayer);
	values.add("Summoning:" + fighter.summoning);
	values.add("Renew at obelisk:" + fighter.renewAtObelisk);
	values.add("Bank:[" + Arrays.toString(fighter.bankItems) + "]");
	values.add("Rest at bank:" + fighter.restAtBank);
	values.add("Shutdown on bank:" + fighter.shutdownOnBank);
	values.add("Breaks:[" + Arrays.toString(fighter.breaks) + "]");
	
	FileManager files = IO.getFileManager();
	files.appendFile(fighter.name + IOManager.SETTINGS_FOLDER + name + IOManager.PROFILE_EXTENSION, false, values);
    }

    @SuppressWarnings("unchecked")
    public static boolean load(String profile, GUI gui) {
	try {
	    File fi = new File(io.getBrandedName() + io.separator + path + io.separator + profile
		    + extension);
	    if (!fi.exists()) {
		return false;
	    }

	    FileInputStream fis = io.getFileManager().getInputStream(
		    io.getBrandedName() + io.separator + path + io.separator + profile + extension);
	    XMLDecoder decoder = new XMLDecoder(fis);
	    HashMap<String, Object> direct = (HashMap<String, Object>) decoder.readObject();
	    HashMap<String, Object[]> modellic = (HashMap<String, Object[]>) decoder.readObject();
	    decoder.close();
	    fis.close();
	    loadSlayer(gui);
	    if (direct.containsKey("centerTileButton")) {
		gui.centerTileButton.setText((String) direct.get("centerTileButton"));
	    }

	    if ((Boolean) direct.get("multiCombatBox") != null) {
		gui.multiCombatBox.setSelected((Boolean) direct.get("multiCombatBox"));
	    }

	    if ((Boolean) direct.get("lootshareBox") != null) {
		gui.lootshareBox.setSelected((Boolean) direct.get("lootshareBox"));
	    }

	    gui.profileField.setText((String) direct.get("profileField"));
	    gui.zoneSizeSpinner.setValue(direct.get("zoneSizeSpinner"));
	    gui.foodOptionsSpinner.setValue(direct.get("foodOptionsSpinner"));
	    gui.healPercentageSlider.setValue((Integer) direct.get("healPercentageSlider"));
	    gui.skipTaskBox.setSelected((Boolean) direct.get("skipTaskBox"));
	    gui.breaksBox.setSelected((Boolean) direct.get("breaksBox"));
	    gui.playSpinner1.setValue(direct.get("playSpinner1"));
	    gui.breakSpinner1.setValue(direct.get("breakSpinner1"));
	    gui.randomizationSpinner.setValue(direct.get("randomizationSpinner"));
	    gui.playSpinner2.setValue(direct.get("playSpinner2"));
	    gui.breakSpinner2.setValue(direct.get("breakSpinner2"));
	    gui.playSpinner3.setValue(direct.get("playSpinner3"));
	    gui.playSpinner4.setValue(direct.get("playSpinner4"));
	    gui.breakSpinner3.setValue(direct.get("breakSpinner3"));
	    gui.breakSpinner4.setValue(direct.get("breakSpinner4"));
	    gui.playSpinner5.setValue(direct.get("playSpinner5"));
	    gui.playSpinner6.setValue(direct.get("playSpinner6"));
	    gui.playSpinner7.setValue(direct.get("playSpinner7"));
	    gui.breakSpinner5.setValue(direct.get("breakSpinner5"));
	    gui.breakSpinner6.setValue(direct.get("breakSpinner6"));
	    gui.breakSpinner7.setValue(direct.get("breakSpinner7"));
	    gui.levelSpinner.setValue(direct.get("levelSpinner"));
	    gui.shutDownBox.setSelected((Boolean) direct.get("shutDownBox"));
	    gui.safeSpotBox.setSelected((Boolean) direct.get("safeSpotBox"));
	    gui.guthanBox.setSelected((Boolean) direct.get("guthanBox"));
	    gui.specBox.setSelected((Boolean) direct.get("specBox"));
	    gui.hoverBox.setSelected((Boolean) direct.get("hoverBox"));
	    gui.behindObstacleBox.setSelected((Boolean) direct.get("behindObstacleBox"));
	    gui.crabBox.setSelected((Boolean) direct.get("crabBox"));
	    gui.minimapInvisibleBox.setSelected((Boolean) direct.get("minimapInvisibleBox"));
	    gui.prayerBox.setSelected((Boolean) direct.get("prayerBox"));
	    gui.deathwalkBox.setSelected((Boolean) direct.get("deathwalkBox"));
	    gui.familiarBox.setSelected((Boolean) direct.get("familiarBox"));
	    gui.familiarSpecBox.setSelected((Boolean) direct.get("familiarSpecBox"));
	    gui.rechargeBox.setSelected((Boolean) direct.get("rechargeBox"));
	    gui.revenantBox.setSelected((Boolean) direct.get("revenantBox"));
	    gui.teleportBox.setSelected((Boolean) direct.get("teleportBox"));
	    gui.rechargeSummoningBox.setSelected((Boolean) direct.get("rechargeSummoningBox"));
	    gui.restBox.setSelected((Boolean) direct.get("restBox"));
	    gui.b2pBox.setSelected((Boolean) direct.get("b2pBox"));
	    gui.buryBox.setSelected((Boolean) direct.get("buryBox"));
	    gui.depositBoxBox.setSelected((Boolean) direct.get("depositBoxBox"));
	    gui.waitForLootBox.setSelected((Boolean) direct.get("waitForLootBox"));
	    gui.pickAmmoBox.setSelected((Boolean) direct.get("pickAmmoBox"));
	    gui.taskbarBox.setSelected((Boolean) direct.get("taskbarBox"));
	    gui.cleanHerbsBox.setSelected((Boolean) direct.get("cleanHerbsBox"));
	    gui.dropCleanHerbsBox.setSelected((Boolean) direct.get("dropCleanHerbsBox"));
	    gui.shutDownBankBox.setSelected((Boolean) direct.get("shutDownBankBox"));
	    gui.useBox.setSelected((Boolean) direct.get("useBox"));
	    gui.keepEquippedBox.setSelected((Boolean) direct.get("keepEquippedBox"));
	    gui.tabBox.setSelected((Boolean) direct.get("tabBox"));
	    gui.denoteBox.setSelected((Boolean) direct.get("denoteBox"));
	    gui.eatBankBox.setSelected((Boolean) direct.get("eatBankBox"));
	    gui.telegrabBox.setSelected((Boolean) direct.get("telegrabBox"));
	    gui.mouseBox.setSelected((Boolean) direct.get("mouseBox"));
	    gui.statBox.setSelected((Boolean) direct.get("statBox"));
	    gui.phoneField.setText((String) direct.get("phoneField"));
	    gui.emailField.setText((String) direct.get("emailField"));
	    gui.usernameField.setText((String) direct.get("usernameField"));
	    gui.dynamicSigBox.setSelected((Boolean) direct.get("dynamicSigBox"));
	    gui.emailBox.setSelected((Boolean) direct.get("emailBox"));
	    gui.smsBox.setSelected((Boolean) direct.get("smsBox"));
	    gui.passwordField.setText((String) direct.get("passwordField"));
	    gui.ignoreLevelsField.setText((String) direct.get("ignoreLevelsField"));
	    gui.nameField.setText((String) direct.get("nameField"));
	    gui.idField.setText((String) direct.get("idField"));
	    gui.bankOptionField.setText((String) direct.get("bankOptionField"));
	    gui.bankOptionSpinner.setValue(direct.get("bankOptionSpinner"));
	    gui.lootOptionField.setText((String) direct.get("lootOptionField"));
	    if (!direct.get("lootValueSpinner").toString().equalsIgnoreCase("-1")) {
		gui.lootValueSpinner.setValue(direct.get("lootValueSpinner"));
	    }
	    gui.eatForLootBox.setSelected((Boolean) direct.get("eatForLootBox"));
	    gui.alchOptionField.setText((String) direct.get("alchOptionField"));
	    // gui.foodCBox.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("foodCBox")[0]));
	    gui.foodCBox.setSelectedItem(modellic.get("foodCBox")[1]);
	    // gui.masterCBox.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("masterCBox")[0]));
	    gui.masterCBox.setSelectedItem(modellic.get("masterCBox")[1]);
	    // gui.monsterCBox.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("monsterCBox")[0]));
	    gui.monsterCBox.setSelectedItem(modellic.get("monsterCBox")[1]);
	    // gui.helmCBox.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("helmCBox")[0]));
	    gui.helmCBox.setSelectedItem(modellic.get("helmCBox")[1]);
	    // gui.train1CBox.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("train1CBox")[0]));
	    gui.train1CBox.setSelectedItem(modellic.get("train1CBox")[1]);
	    // gui.train2CBox.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("train2CBox")[0]));
	    gui.train2CBox.setSelectedItem(modellic.get("train2CBox")[1]);
	    // gui.goalList.setListData(new Object[] {
	    // modellic.get("goalList")[0] });
	    // gui.goalList.setSelectedValue(modellic.get("goalList")[1], true);
	    // gui.specCBox.setModel(new DefaultComboBoxModel(
	    // new Object[] { modellic.get("specCBox")[0] }));
	    gui.specCBox.setSelectedItem(modellic.get("specCBox")[1]);
	    // gui.prayerCBox.setModel(new DefaultComboBoxModel(new Object[] {
	    // modellic
	    // .get("prayerCBox")[0] }));
	    gui.prayerCBox.setSelectedItem(modellic.get("prayerCBox")[1]);
	    // gui.familiarCBox.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("familiarCBox")[0]));
	    gui.familiarCBox.setSelectedItem(modellic.get("familiarCBox")[1]);
	    // gui.useCBox1.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("useCBox1")[0]));
	    gui.useCBox1.setSelectedItem(modellic.get("useCBox1")[1]);
	    // gui.useCBox2.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("useCBox2")[0]));
	    gui.useCBox2.setSelectedItem(modellic.get("useCBox2")[1]);
	    // gui.tabCBox.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("tabCBox")[0]));
	    gui.tabCBox.setSelectedItem(modellic.get("tabCBox")[1]);
	    // gui.nearbyList.setListData((Object[])
	    // modellic.get("nearbyList")[0]);
	    // gui.nearbyList.setSelectedValue(modellic.get("nearbyList")[1],
	    // true);
	    gui.monsterList.setListData(modellic.get("monsterList")/**
	     * new
	     * Object[] { modellic.get("monsterList")[0]}
	     **/
	    );
	    // gui.monsterList.setSelectedValue(modellic.get("monsterList")[1],
	    // true);
	    // gui.bankOptionList.setListData((Object[])
	    // modellic.get("bankOptionList")[0]);
	    // gui.bankOptionList.setSelectedValue(modellic.get("bankOptionList")[1],
	    // true);
	    gui.bankList.setListData(modellic.get("bankList")/**
	     * new Object[] {
	     * modellic.get("bankList")[0]}
	     **/
	    );
	    // gui.bankList.setSelectedValue(modellic.get("bankList")[1], true);
	    gui.lootList.setListData(modellic.get("lootList")/**
	     * new Object[]
	     * {modellic.get("lootList")[0]}
	     **/
	    );
	    // gui.lootList.setSelectedValue(modellic.get("lootList")[1], true);
	    // gui.lootOptionList.setListData((Object[])
	    // modellic.get("lootOptionList")[0]);
	    // gui.lootOptionList.setSelectedValue(modellic.get("lootOptionList")[1],
	    // true);
	    // gui.alchOptionList.setListData((Object[])
	    // modellic.get("alchOptionList")[0]);
	    // gui.alchOptionList.setSelectedValue(modellic.get("alchOptionList")[1],
	    // true);
	    gui.alchList.setListData(modellic.get("alchList")/**
	     * new Object[] {
	     * modellic.get("alchList")[0]}
	     **/
	    );
	    // gui.alchList.setSelectedValue(modellic.get("alchList")[1], true);
	    // gui.helmCBox.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("helmCBox")[0]));
	    gui.helmCBox.setSelectedItem(modellic.get("helmCBox")[1]);
	    // gui.capeCBox.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("capeCBox")[0]));
	    gui.capeCBox.setSelectedItem(modellic.get("capeCBox")[1]);
	    // gui.legsCBox.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("legsCBox")[0]));
	    gui.legsCBox.setSelectedItem(modellic.get("legsCBox")[1]);
	    // gui.feetCBox.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("feetCBox")[0]));
	    gui.feetCBox.setSelectedItem(modellic.get("feetCBox")[1]);
	    // gui.ammoCBox.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("ammoCBox")[0]));
	    gui.ammoCBox.setSelectedItem(modellic.get("ammoCBox")[1]);
	    // gui.neckCBox.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("neckCBox")[0]));
	    gui.neckCBox.setSelectedItem(modellic.get("neckCBox")[1]);
	    // gui.handsCBox.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("handsCBox")[0]));
	    gui.handsCBox.setSelectedItem(modellic.get("handsCBox")[1]);
	    // gui.bodyCBox.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("bodyCBox")[0]));
	    gui.bodyCBox.setSelectedItem(modellic.get("bodyCBox")[1]);
	    // gui.wepCBox.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("wepCBox")[0]));
	    gui.wepCBox.setSelectedItem(modellic.get("wepCBox")[1]);
	    // gui.shieldCBox.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("shieldCBox")[0]));
	    gui.shieldCBox.setSelectedItem(modellic.get("shieldCBox")[1]);
	    // gui.ringCBox.setModel(new DefaultComboBoxModel((Object[])
	    // modellic.get("ringCBox")[0]));
	    gui.ringCBox.setSelectedItem(modellic.get("ringCBox")[1]);
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return true;
    }
}
