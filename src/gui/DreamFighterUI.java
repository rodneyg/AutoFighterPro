package gui;

import impsoft.bots.reflection.NPC;
import impsoft.bots.reflection.NPCIterator;
import impsoft.scripting.ibot.enums.Prayer;
import impsoft.scripting.ibot.enums.Skill;
import impsoft.utils.general.Timer;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import looting.LootItem;
import teleporting.Jewellery;
import teleporting.TeleportTab;
import base.BankItem;
import base.Fighter;
import breaking.Break;
import breaking.BreakHandler;
import breaking.RandomBreak;

import combat.Monster;
import combat.SpecialAttackWeapon;

import data.IOManager;
import food.FoodItem;
import food.FoodManager;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DreamFighterUI.java
 *
 * Created on Jun 21, 2011, 11:45:27 AM
 */
/**
 * 
 * @author Rodney
 */
public class DreamFighterUI extends javax.swing.JFrame implements Serializable {
    /**
     * 
     */
    public static final long serialVersionUID = 1L;

    public Fighter fighter = null;
    public DefaultListModel lootList = new DefaultListModel();
    public DefaultListModel bankList = new DefaultListModel();
    public DefaultListModel alchList = new DefaultListModel();

    public DreamFighterUI(Fighter fighter) {
	this.fighter = fighter;

	initComponents();
	loadValues();

	// Get the size of the screen
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	// Determine the new location of the window
	int w = getSize().width;
	int h = getSize().height;
	int x = (dim.width - w) / 2;
	int y = (dim.height - h) / 2;

	// Move the window
	setLocation(x, y);

	if (!IOManager.isMac()) {
	    String[] li = { "Licensee=Gainous Jr Rodney",
		    "LicenseRegistrationNumber=382641515",
		    "Product=Synthetica", "LicenseType=Small Business License",
		    "ExpireDate=--.--.----", "MaxVersion=2.999.999" };
	    UIManager.put("Synthetica.license.info", li);
	    UIManager.put("Synthetica.license.key",
		    "C6D823A5-84315E27-0432C51D-8AD06630-533E40AE");

	    try {
		Thread.currentThread().setContextClassLoader(
			new URLClassLoader(new URL[] {
				new URL(IOManager.SYNTHETICA_URL),
				new URL(IOManager.SYNTHETICA_BATIK_URL),
				new URL(IOManager.SYNTHETICA2D_URL) }));

		// UIManager.setLookAndFeel(new
		// SyntheticaBlackEyeLookAndFeel());

		UIManager
			.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel");
		SwingUtilities.updateComponentTreeUI(this);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	} else {
	    IOManager.setNativeLookAndFeel();
	}
    }

    public void loadValues() {
	DefaultComboBoxModel foodModel = new DefaultComboBoxModel();
	foodModel.addElement("Not eating");
	for (FoodItem fi : FoodManager.getAllFoodTypes()) {
	    foodModel.addElement(fi.getPieces()[0].getName());
	}

	foodCombo.setModel(foodModel);

	DefaultComboBoxModel teleportModel = new DefaultComboBoxModel();
	DefaultComboBoxModel teleportModel2 = new DefaultComboBoxModel();
	for (Jewellery jewellery : Jewellery.values()) {
	    teleportModel.addElement(jewellery.getName());
	    teleportModel2.addElement(jewellery.getName());
	}

	for (TeleportTab teleportTab : TeleportTab.values()) {
	    teleportModel.addElement(teleportTab.getName());
	    teleportModel2.addElement(teleportTab.getName());
	}

	teleportCombo.setModel(teleportModel);
	teleportCombo2.setModel(teleportModel2);

	DefaultComboBoxModel prayerModel = new DefaultComboBoxModel();
	prayerModel.addElement("Quick prayer");

	for (Prayer prayer : Prayer.values()) {
	    prayerModel.addElement(prayer.name);
	}

	prayerCombo.setModel(prayerModel);

	DefaultComboBoxModel specialAttackModel = new DefaultComboBoxModel();
	for (SpecialAttackWeapon weapon : SpecialAttackWeapon.values()) {
	    specialAttackModel.addElement(weapon.getName());
	}

	specialWeaponCombo.setModel(specialAttackModel);
    }

    public Font getEpoxyFont(int size) {
	try {
	    /* Returned font is of pt size 1 */
	    Font font = Font.createFont(Font.TRUETYPE_FONT, new File(
		    fighter.name + IOManager.DOWNLOADS_FOLDER
			    + "EpoXY_histoRy.ttf"));

	    /*
	     * derive and return a 12 pt version : need to use float otherwise
	     * it would be interpreted as style
	     */
	    return font.deriveFont((float) size);

	} catch (IOException ioe) {
	} catch (FontFormatException ffe) {
	}

	return null;
    }

    @Override
    public Dimension getPreferredSize() {
	return new Dimension(644, 496);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    public void initComponents() {

	scriptLabel = new javax.swing.JLabel();
	jTabbedPane2 = new javax.swing.JTabbedPane();
	jPanel3 = new javax.swing.JPanel();
	jPanel8 = new javax.swing.JPanel();
	saveSettingsButton = new javax.swing.JButton();
	profileNameField = new javax.swing.JTextField();
	jPanel9 = new javax.swing.JPanel();
	zoneSpinner = new javax.swing.JSpinner();
	jLabel16 = new javax.swing.JLabel();
	setTileButton = new javax.swing.JButton();
	paintZoneButton = new javax.swing.JToggleButton();
	jPanel13 = new javax.swing.JPanel();
	foodLabel = new javax.swing.JLabel();
	foodSlider = new javax.swing.JSlider();
	foodCombo = new javax.swing.JComboBox();
	foodWithdrawSpinner = new javax.swing.JSpinner();
	jPanel14 = new javax.swing.JPanel();
	breakForField = new javax.swing.JTextField();
	jLabel22 = new javax.swing.JLabel();
	breaksCheck = new javax.swing.JCheckBox();
	jLabel21 = new javax.swing.JLabel();
	breakAtField = new javax.swing.JTextField();
	jLabel18 = new javax.swing.JLabel();
	jLabel19 = new javax.swing.JLabel();
	randomBreaksButton = new javax.swing.JButton();
	jPanel16 = new javax.swing.JPanel();
	mouseSpeedLabel = new javax.swing.JLabel();
	mouseSlider = new javax.swing.JSlider();
	jPanel1 = new javax.swing.JPanel();
	refreshMonstersButton = new javax.swing.JButton();
	jPanel4 = new javax.swing.JPanel();
	jScrollPane4 = new javax.swing.JScrollPane();
	addMonstersList = new javax.swing.JList();
	jLabel10 = new javax.swing.JLabel();
	jScrollPane3 = new javax.swing.JScrollPane();
	foundMonstersList = new javax.swing.JList();
	jLabel11 = new javax.swing.JLabel();
	ignoreLevelsField = new javax.swing.JTextField();
	jLabel12 = new javax.swing.JLabel();
	addCustomButton = new javax.swing.JButton();
	jPanel2 = new javax.swing.JPanel();
	lootRadio = new javax.swing.JRadioButton();
	alchemyRadio = new javax.swing.JRadioButton();
	bankRadio = new javax.swing.JRadioButton();
	itemSearchField = new javax.swing.JTextField();
	jLabel8 = new javax.swing.JLabel();
	jLabel9 = new javax.swing.JLabel();
	bankSpinner = new javax.swing.JSpinner();
	jPanel12 = new javax.swing.JPanel();
	jLabel6 = new javax.swing.JLabel();
	jScrollPane2 = new javax.swing.JScrollPane();
	selectedItemsList = new javax.swing.JList();
	jScrollPane1 = new javax.swing.JScrollPane();
	foundItemsList = new javax.swing.JList();
	jLabel7 = new javax.swing.JLabel();
	waitLootCheck = new javax.swing.JCheckBox();
	eatForLootCheck = new javax.swing.JCheckBox();
	buryBonesCheck = new javax.swing.JCheckBox();
	sactterAshesCheck = new javax.swing.JCheckBox();
	rightClickCheck = new javax.swing.JCheckBox();
	telegrabCheck = new javax.swing.JCheckBox();
	lootOverField = new javax.swing.JTextField();
	jPanel6 = new javax.swing.JPanel();
	jPanel5 = new javax.swing.JPanel();
	shutdownRadio = new javax.swing.JRadioButton();
	jLabel15 = new javax.swing.JLabel();
	jLabel13 = new javax.swing.JLabel();
	levelCombo = new javax.swing.JComboBox();
	jLabel14 = new javax.swing.JLabel();
	goalSkillCombo = new javax.swing.JComboBox();
	attackModeCombo = new javax.swing.JComboBox();
	addGoalButton = new javax.swing.JButton();
	jScrollPane5 = new javax.swing.JScrollPane();
	addedGoalsList = new javax.swing.JList();
	jPanel7 = new javax.swing.JPanel();
	b2pCheck = new javax.swing.JCheckBox();
	eatAtBankCheck = new javax.swing.JCheckBox();
	denoteCheck = new javax.swing.JCheckBox();
	guthansCheck = new javax.swing.JCheckBox();
	rangedCheck = new javax.swing.JCheckBox();
	safeSpotsCheck = new javax.swing.JCheckBox();
	cleanHerbsCheck = new javax.swing.JCheckBox();
	dropHerbsCheck = new javax.swing.JCheckBox();
	specialAttackCheck = new javax.swing.JCheckBox();
	switchCheck = new javax.swing.JCheckBox();
	specialWeaponCombo = new javax.swing.JComboBox();
	teleportBankCheck = new javax.swing.JCheckBox();
	teleportCombo = new javax.swing.JComboBox();
	teleportLocationCombo = new javax.swing.JComboBox();
	teleportMonsterCheck = new javax.swing.JCheckBox();
	teleportCombo2 = new javax.swing.JComboBox();
	teleportLocationCombo2 = new javax.swing.JComboBox();
	prayerCheck = new javax.swing.JCheckBox();
	prayerCombo = new javax.swing.JComboBox();
	prayerAltarButton = new javax.swing.JToggleButton();
	restAtBankCheck = new javax.swing.JCheckBox();
	shutdownBankCheck = new javax.swing.JCheckBox();
	hoverCheck = new javax.swing.JCheckBox();
	keepEquippedCheck = new javax.swing.JCheckBox();
	keepEquippedCheck2 = new javax.swing.JCheckBox();
	prayerFlickingCheck = new javax.swing.JCheckBox();
	jPanel17 = new javax.swing.JPanel();
	invisibleCheck = new javax.swing.JCheckBox();
	blockedCheck = new javax.swing.JCheckBox();
	dontAttackCheck = new javax.swing.JCheckBox();
	screenshotCheck = new javax.swing.JCheckBox();
	taskbarCheck = new javax.swing.JCheckBox();
	mouseKeyCheck = new javax.swing.JCheckBox();
	jPanel10 = new javax.swing.JPanel();
	jPanel15 = new javax.swing.JPanel();
	jPanel11 = new javax.swing.JPanel();
	mainProgressBar = new javax.swing.JProgressBar();
	startButton = new javax.swing.JButton();
	closeButton = new javax.swing.JButton();
	recentUpdatesButton = new javax.swing.JButton();
	websiteLabel = new javax.swing.JLabel();
	jLabel3 = new javax.swing.JLabel();
	jLabel4 = new javax.swing.JLabel();
	updatedLabel = new javax.swing.JLabel();
	downloaderButton = new javax.swing.JButton();
	loadedProfilesCombo = new javax.swing.JComboBox();

	setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	setTitle("AutoFighter Pro");

	scriptLabel.setFont(getEpoxyFont(34));
	scriptLabel.setText("AutoFighter Pro");

	jTabbedPane2.setFont(new java.awt.Font("Segoe Print", 0, 10));

	jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

	saveSettingsButton.setText("Save current settings");
	saveSettingsButton
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			saveSettingsButtonActionPerformed(evt);
		    }
		});

	profileNameField.setText("Default setting");

	javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(
		jPanel8);
	jPanel8.setLayout(jPanel8Layout);
	jPanel8Layout
		.setHorizontalGroup(jPanel8Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel8Layout
					.createSequentialGroup()
					.addContainerGap()
					.addComponent(
						profileNameField,
						javax.swing.GroupLayout.PREFERRED_SIZE,
						95,
						javax.swing.GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(saveSettingsButton)
					.addContainerGap(42, Short.MAX_VALUE)));
	jPanel8Layout
		.setVerticalGroup(jPanel8Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel8Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel8Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(
								profileNameField,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(
								saveSettingsButton))
					.addContainerGap()));

	jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

	zoneSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer
		.valueOf(10), Integer.valueOf(0), null, Integer.valueOf(1)));
	zoneSpinner.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseExited(java.awt.event.MouseEvent evt) {
		zoneSpinnerMouseExited(evt);
	    }
	});

	jLabel16.setText("Fighting zone size:");

	setTileButton.setText("Set center tile");
	setTileButton.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		setTileButtonActionPerformed(evt);
	    }
	});

	paintZoneButton.setText("Paint zone onscreen");
	paintZoneButton.setSelected(true);
	paintZoneButton.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		paintZoneButtonActionPerformed(evt);
	    }
	});

	javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(
		jPanel9);
	jPanel9.setLayout(jPanel9Layout);
	jPanel9Layout
		.setHorizontalGroup(jPanel9Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel9Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel9Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel9Layout
									.createSequentialGroup()
									.addComponent(
										jLabel16)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										zoneSpinner,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										40,
										javax.swing.GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										setTileButton))
							.addComponent(
								paintZoneButton))
					.addContainerGap(76, Short.MAX_VALUE)));
	jPanel9Layout
		.setVerticalGroup(jPanel9Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel9Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel9Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel16)
							.addComponent(
								zoneSpinner,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(
								setTileButton))
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(paintZoneButton)
					.addContainerGap(
						javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)));

	jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

	foodLabel.setText("Heal at: 60%   The bot will heal at: 6hp");

	foodSlider.setMajorTickSpacing(25);
	foodSlider.setMinimum(10);
	foodSlider.setMinorTickSpacing(5);
	foodSlider.setPaintLabels(true);
	foodSlider.setPaintTicks(true);
	foodSlider
		.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
		    public void mouseDragged(java.awt.event.MouseEvent evt) {
			foodSliderMouseDragged(evt);
		    }

		    public void mouseMoved(java.awt.event.MouseEvent evt) {
			foodSliderMouseMoved(evt);
		    }
		});

	foodCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
		"Select food", "Monkfish" }));

	foodWithdrawSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer
		.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

	javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(
		jPanel13);
	jPanel13.setLayout(jPanel13Layout);
	jPanel13Layout
		.setHorizontalGroup(jPanel13Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel13Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel13Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(foodLabel)
							.addGroup(
								jPanel13Layout
									.createSequentialGroup()
									.addComponent(
										foodCombo,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(
										foodWithdrawSpinner,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										40,
										javax.swing.GroupLayout.PREFERRED_SIZE))
							.addComponent(
								foodSlider,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE))
					.addContainerGap(
						javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)));
	jPanel13Layout
		.setVerticalGroup(jPanel13Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel13Layout
					.createSequentialGroup()
					.addComponent(foodLabel)
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						jPanel13Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(
								foodCombo,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(
								foodWithdrawSpinner,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(
						foodSlider,
						javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.PREFERRED_SIZE)
					.addContainerGap(
						javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)));

	jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

	breakForField.setEnabled(false);

	jLabel22.setText("minutes");
	jLabel22.setEnabled(false);

	breaksCheck.setText("Use breaks");
	breaksCheck.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		breaksCheckActionPerformed(evt);
	    }
	});

	jLabel21.setText("minutes");
	jLabel21.setEnabled(false);

	breakAtField.setEnabled(false);

	jLabel18.setText("Break at:");
	jLabel18.setEnabled(false);

	jLabel19.setText("Break for:");
	jLabel19.setEnabled(false);

	randomBreaksButton.setText("Random");
	randomBreaksButton
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			randomBreaksButtonActionPerformed(evt);
		    }
		});

	javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(
		jPanel14);
	jPanel14.setLayout(jPanel14Layout);
	jPanel14Layout
		.setHorizontalGroup(jPanel14Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel14Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel14Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel14Layout
									.createSequentialGroup()
									.addGap(21,
										21,
										21)
									.addGroup(
										jPanel14Layout
											.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
											.addComponent(
												jLabel18)
											.addComponent(
												jLabel19))
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(
										jPanel14Layout
											.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING,
												false)
											.addComponent(
												breakAtField)
											.addComponent(
												breakForField,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												26,
												javax.swing.GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addGroup(
										jPanel14Layout
											.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
											.addComponent(
												jLabel22)
											.addComponent(
												jLabel21)))
							.addGroup(
								jPanel14Layout
									.createSequentialGroup()
									.addComponent(
										breaksCheck)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										49,
										Short.MAX_VALUE)
									.addComponent(
										randomBreaksButton)))
					.addContainerGap()));
	jPanel14Layout
		.setVerticalGroup(jPanel14Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel14Layout
					.createSequentialGroup()
					.addGroup(
						jPanel14Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(
								breaksCheck)
							.addComponent(
								randomBreaksButton))
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						jPanel14Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel18)
							.addComponent(
								breakAtField,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel21))
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						jPanel14Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel19)
							.addComponent(
								breakForField,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel22))));

	jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

	mouseSpeedLabel.setText("Mouse speed boost: 0%");

	mouseSlider.setMajorTickSpacing(25);
	mouseSlider.setMinorTickSpacing(5);
	mouseSlider.setPaintLabels(true);
	mouseSlider.setPaintTicks(true);
	mouseSlider.setValue(0);
	mouseSlider
		.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
		    public void mouseDragged(java.awt.event.MouseEvent evt) {
			mouseSliderMouseDragged(evt);
		    }

		    public void mouseMoved(java.awt.event.MouseEvent evt) {
			mouseSliderMouseMoved(evt);
		    }
		});

	javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(
		jPanel16);
	jPanel16.setLayout(jPanel16Layout);
	jPanel16Layout
		.setHorizontalGroup(jPanel16Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel16Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel16Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(
								mouseSpeedLabel)
							.addComponent(
								mouseSlider,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE))
					.addContainerGap(
						javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)));
	jPanel16Layout
		.setVerticalGroup(jPanel16Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel16Layout
					.createSequentialGroup()
					.addContainerGap()
					.addComponent(mouseSpeedLabel)
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(
						mouseSlider,
						javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.PREFERRED_SIZE)
					.addContainerGap(17, Short.MAX_VALUE)));

	javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(
		jPanel3);
	jPanel3.setLayout(jPanel3Layout);
	jPanel3Layout
		.setHorizontalGroup(jPanel3Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel3Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel3Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel3Layout
									.createParallelGroup(
										javax.swing.GroupLayout.Alignment.LEADING)
									.addComponent(
										jPanel9,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
									.addComponent(
										jPanel8,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
							.addGroup(
								jPanel3Layout
									.createSequentialGroup()
									.addComponent(
										jPanel14,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
									.addGap(107,
										107,
										107)))
					.addGap(18, 18, 18)
					.addGroup(
						jPanel3Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(
								jPanel16,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
							.addComponent(
								jPanel13,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								220,
								javax.swing.GroupLayout.PREFERRED_SIZE))
					.addContainerGap(15, Short.MAX_VALUE)));
	jPanel3Layout
		.setVerticalGroup(jPanel3Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel3Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel3Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel3Layout
									.createSequentialGroup()
									.addComponent(
										jPanel8,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
									.addGap(18,
										18,
										18)
									.addComponent(
										jPanel9,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										jPanel14,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
							.addGroup(
								jPanel3Layout
									.createSequentialGroup()
									.addComponent(
										jPanel13,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										116,
										javax.swing.GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										jPanel16,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(
						javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)));

	jTabbedPane2.addTab("Main options", jPanel3);

	refreshMonstersButton.setText("Refresh nearby monsters");
	refreshMonstersButton
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			refreshMonstersButtonActionPerformed(evt);
		    }
		});

	jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

	addMonstersList.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		addMonstersListMouseReleased(evt);
	    }
	});
	jScrollPane4.setViewportView(addMonstersList);

	jLabel10.setText("Nearby monsters:");

	foundMonstersList.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		foundMonstersListMouseReleased(evt);
	    }
	});
	jScrollPane3.setViewportView(foundMonstersList);

	jLabel11.setText("Selected monsters:");

	javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(
		jPanel4);
	jPanel4.setLayout(jPanel4Layout);
	jPanel4Layout
		.setHorizontalGroup(jPanel4Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel4Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel4Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(jLabel10)
							.addComponent(
								jScrollPane3,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								180,
								javax.swing.GroupLayout.PREFERRED_SIZE))
					.addGap(18, 18, 18)
					.addGroup(
						jPanel4Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(
								jScrollPane4,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								180,
								javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel11))
					.addContainerGap()));
	jPanel4Layout
		.setVerticalGroup(jPanel4Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel4Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel4Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel10)
							.addComponent(jLabel11))
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						jPanel4Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(
								jScrollPane4,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								164,
								Short.MAX_VALUE)
							.addComponent(
								jScrollPane3,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								164,
								Short.MAX_VALUE))
					.addContainerGap()));

	ignoreLevelsField.setText("2012, 2011, 2010");

	jLabel12.setText("Ignore levels:");

	addCustomButton.setText("Add custom monsters");
	addCustomButton.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		addCustomButtonActionPerformed(evt);
	    }
	});

	javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
		jPanel1);
	jPanel1.setLayout(jPanel1Layout);
	jPanel1Layout
		.setHorizontalGroup(jPanel1Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel1Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING,
								false)
							.addGroup(
								jPanel1Layout
									.createSequentialGroup()
									.addComponent(
										refreshMonstersButton)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
									.addComponent(
										jLabel12)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(
										ignoreLevelsField,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										118,
										javax.swing.GroupLayout.PREFERRED_SIZE))
							.addComponent(
								jPanel4,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(addCustomButton)
					.addContainerGap(50, Short.MAX_VALUE)));
	jPanel1Layout
		.setVerticalGroup(jPanel1Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel1Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(
								refreshMonstersButton)
							.addComponent(
								ignoreLevelsField,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel12))
					.addGap(18, 18, 18)
					.addGroup(
						jPanel1Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel1Layout
									.createSequentialGroup()
									.addComponent(
										addCustomButton)
									.addContainerGap())
							.addGroup(
								jPanel1Layout
									.createSequentialGroup()
									.addComponent(
										jPanel4,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
									.addGap(42,
										42,
										42)))));

	jTabbedPane2.addTab("Monsters", jPanel1);

	lootRadio.setSelected(true);
	lootRadio.setText("Looting");
	lootRadio.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		lootRadioActionPerformed(evt);
	    }
	});

	alchemyRadio.setText("Alchemy");
	alchemyRadio.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		alchemyRadioActionPerformed(evt);
	    }
	});

	bankRadio.setText("Banking");
	bankRadio.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		bankRadioActionPerformed(evt);
	    }
	});

	itemSearchField.setText("Enter search text");
	itemSearchField.addKeyListener(new java.awt.event.KeyAdapter() {
	    public void keyTyped(java.awt.event.KeyEvent evt) {
		itemSearchFieldKeyTyped(evt);
	    }
	});

	jLabel8.setText("Search items:");

	jLabel9.setText("x");
	jLabel9.setEnabled(false);

	bankSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer
		.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
	bankSpinner.setEnabled(false);
	bankSpinner.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		bankSpinnerMouseReleased(evt);
	    }
	});

	jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

	jLabel6.setText("Results:");

	selectedItemsList.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		selectedItemsListMouseReleased(evt);
	    }
	});
	jScrollPane2.setViewportView(selectedItemsList);

	jScrollPane1.setViewportView(foundItemsList);
	foundItemsList.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		foundItemsListMouseReleased(evt);
	    }
	});

	jLabel7.setText("Selected items:");

	javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(
		jPanel12);
	jPanel12.setLayout(jPanel12Layout);
	jPanel12Layout
		.setHorizontalGroup(jPanel12Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel12Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel12Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(jLabel6)
							.addComponent(
								jScrollPane1,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								179,
								javax.swing.GroupLayout.PREFERRED_SIZE))
					.addGap(84, 84, 84)
					.addGroup(
						jPanel12Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(jLabel7)
							.addComponent(
								jScrollPane2,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								179,
								javax.swing.GroupLayout.PREFERRED_SIZE))
					.addContainerGap()));
	jPanel12Layout
		.setVerticalGroup(jPanel12Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel12Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel12Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel6)
							.addComponent(jLabel7))
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						jPanel12Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(
								jScrollPane2,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								149,
								Short.MAX_VALUE)
							.addComponent(
								jScrollPane1,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								149,
								Short.MAX_VALUE))
					.addContainerGap()));

	waitLootCheck.setText("Wait for loot");

	eatForLootCheck.setText("Eat for loot space");

	buryBonesCheck.setText("Bury bones");

	sactterAshesCheck.setText("Scatter ashes");
	sactterAshesCheck.setEnabled(false);

	rightClickCheck.setText("Right click loot");

	telegrabCheck.setText("Telegrab loot");

	lootOverField.setText("Loot over X");

	javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
		jPanel2);
	jPanel2.setLayout(jPanel2Layout);
	jPanel2Layout
		.setHorizontalGroup(jPanel2Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel2Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel2Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel2Layout
									.createSequentialGroup()
									.addComponent(
										jPanel12,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
									.addGroup(
										jPanel2Layout
											.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
											.addComponent(
												rightClickCheck)
											.addComponent(
												sactterAshesCheck)
											.addComponent(
												buryBonesCheck)
											.addComponent(
												waitLootCheck)
											.addComponent(
												eatForLootCheck)
											.addGroup(
												jPanel2Layout
													.createParallelGroup(
														javax.swing.GroupLayout.Alignment.TRAILING,
														false)
													.addComponent(
														lootOverField,
														javax.swing.GroupLayout.Alignment.LEADING)
													.addComponent(
														telegrabCheck,
														javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))))
							.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel2Layout
									.createSequentialGroup()
									.addComponent(
										jLabel8)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										341,
										Short.MAX_VALUE)
									.addComponent(
										lootRadio)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										alchemyRadio)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										bankRadio))
							.addGroup(
								jPanel2Layout
									.createSequentialGroup()
									.addComponent(
										itemSearchField,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										139,
										javax.swing.GroupLayout.PREFERRED_SIZE)
									.addGap(12,
										12,
										12)
									.addComponent(
										jLabel9)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(
										bankSpinner,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										39,
										javax.swing.GroupLayout.PREFERRED_SIZE)))
					.addContainerGap()));
	jPanel2Layout
		.setVerticalGroup(jPanel2Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel2Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel2Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(bankRadio)
							.addComponent(
								alchemyRadio)
							.addComponent(lootRadio)
							.addComponent(jLabel8))
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(
						jPanel2Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(
								itemSearchField,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(
								bankSpinner,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(jLabel9))
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						jPanel2Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(
								jPanel12,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
							.addGroup(
								jPanel2Layout
									.createSequentialGroup()
									.addComponent(
										waitLootCheck)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										eatForLootCheck)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										buryBonesCheck)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										sactterAshesCheck)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										rightClickCheck)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										telegrabCheck)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										lootOverField,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(
						javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)));

	jTabbedPane2.addTab("Items", jPanel2);

	jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

	shutdownRadio.setText("Shutdown");

	jLabel15.setText("or switch to:");

	jLabel13.setText("Train:");

	levelCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
		"2", "3", "4", "5", "6", "7" }));

	jLabel14.setText("To level:");

	goalSkillCombo.setModel(new javax.swing.DefaultComboBoxModel(
		new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

	attackModeCombo.setModel(new javax.swing.DefaultComboBoxModel(
		new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

	javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(
		jPanel5);
	jPanel5.setLayout(jPanel5Layout);
	jPanel5Layout
		.setHorizontalGroup(jPanel5Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel5Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel5Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel5Layout
									.createSequentialGroup()
									.addComponent(
										jLabel13)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(
										goalSkillCombo,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										91,
										javax.swing.GroupLayout.PREFERRED_SIZE))
							.addGroup(
								jPanel5Layout
									.createSequentialGroup()
									.addComponent(
										jLabel14)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										levelCombo,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
							.addGroup(
								jPanel5Layout
									.createSequentialGroup()
									.addComponent(
										shutdownRadio)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										jLabel15)
									.addGap(4,
										4,
										4)
									.addComponent(
										attackModeCombo,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										72,
										javax.swing.GroupLayout.PREFERRED_SIZE)))
					.addContainerGap()));
	jPanel5Layout
		.setVerticalGroup(jPanel5Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel5Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel5Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel13)
							.addComponent(
								goalSkillCombo,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(
						jPanel5Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(jLabel14)
							.addComponent(
								levelCombo,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(
						jPanel5Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(
								shutdownRadio)
							.addComponent(jLabel15)
							.addComponent(
								attackModeCombo,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE))
					.addContainerGap()));

	addGoalButton.setText("Add goal");
	addGoalButton.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		addGoalButtonActionPerformed(evt);
	    }
	});

	addedGoalsList.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mouseReleased(java.awt.event.MouseEvent evt) {
		addedGoalsListMouseReleased(evt);
	    }
	});
	jScrollPane5.setViewportView(addedGoalsList);

	javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(
		jPanel6);
	jPanel6.setLayout(jPanel6Layout);
	jPanel6Layout
		.setHorizontalGroup(jPanel6Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel6Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel6Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								false)
							.addComponent(
								jScrollPane5,
								javax.swing.GroupLayout.Alignment.LEADING)
							.addGroup(
								javax.swing.GroupLayout.Alignment.LEADING,
								jPanel6Layout
									.createSequentialGroup()
									.addComponent(
										jPanel5,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										addGoalButton)))
					.addContainerGap(279, Short.MAX_VALUE)));
	jPanel6Layout
		.setVerticalGroup(jPanel6Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				jPanel6Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel6Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.TRAILING)
							.addComponent(
								addGoalButton)
							.addComponent(
								jPanel5,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE))
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(
						jScrollPane5,
						javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.PREFERRED_SIZE)
					.addGap(14, 14, 14)));

	jTabbedPane2.addTab("Goals", jPanel6);

	b2pCheck.setText("Bones to peaches");

	eatAtBankCheck.setText("Eat at bank");

	denoteCheck.setText("Denote food");

	guthansCheck.setText("Guthans");

	rangedCheck.setText("Pick up and equip ammo");

	safeSpotsCheck.setText("Safe spots");
	safeSpotsCheck.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		safeSpotsCheckActionPerformed(evt);
	    }
	});

	cleanHerbsCheck.setText("Clean herbs");
	cleanHerbsCheck.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		cleanHerbsCheckActionPerformed(evt);
	    }
	});

	dropHerbsCheck.setText("Drop herbs");
	dropHerbsCheck.setEnabled(false);

	specialAttackCheck.setText("Special attack");
	specialAttackCheck
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			specialAttackCheckActionPerformed(evt);
		    }
		});

	switchCheck.setText("Switch to:");
	switchCheck.setEnabled(false);

	specialWeaponCombo.setModel(new javax.swing.DefaultComboBoxModel(
		new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
	specialWeaponCombo.setEnabled(false);

	teleportBankCheck.setText("Teleport to bank using");
	teleportBankCheck
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			teleportBankCheckActionPerformed(evt);
		    }
		});

	teleportCombo.setModel(new javax.swing.DefaultComboBoxModel(
		new String[] { "Amulet of glory" }));
	teleportCombo.setEnabled(false);
	teleportCombo.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		teleportComboActionPerformed(evt);
	    }
	});

	teleportLocationCombo.setModel(new javax.swing.DefaultComboBoxModel(
		new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
	teleportLocationCombo.setEnabled(false);

	teleportMonsterCheck.setText("Teleport to monster using");
	teleportMonsterCheck
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			teleportMonsterCheckActionPerformed(evt);
		    }
		});

	teleportCombo2.setModel(new javax.swing.DefaultComboBoxModel(
		new String[] { "Amulet of glory" }));
	teleportCombo2.setEnabled(false);
	teleportCombo2.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		teleportCombo2ActionPerformed(evt);
	    }
	});

	teleportLocationCombo2.setModel(new javax.swing.DefaultComboBoxModel(
		new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
	teleportLocationCombo2.setEnabled(false);

	prayerCheck.setText("Prayer");
	prayerCheck.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		prayerCheckActionPerformed(evt);
	    }
	});

	prayerCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
		"Item 1", "Item 2", "Item 3", "Item 4" }));
	prayerCombo.setEnabled(false);

	prayerAltarButton.setText("Recharge at altar");
	prayerAltarButton.setEnabled(false);

	restAtBankCheck.setText("Rest at bank");

	shutdownBankCheck.setText("Shutdown on first bank trip");

	hoverCheck.setText("Hover target monster");

	keepEquippedCheck.setText("Keep equipped");
	keepEquippedCheck.setEnabled(false);

	keepEquippedCheck2.setText("Keep equipped");
	keepEquippedCheck2.setEnabled(false);

	prayerFlickingCheck.setText("Prayer flicking");
	prayerFlickingCheck.setEnabled(false);

	javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(
		jPanel7);
	jPanel7.setLayout(jPanel7Layout);
	jPanel7Layout
		.setHorizontalGroup(jPanel7Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel7Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel7Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel7Layout
									.createSequentialGroup()
									.addComponent(
										guthansCheck)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										b2pCheck)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										denoteCheck))
							.addGroup(
								jPanel7Layout
									.createSequentialGroup()
									.addComponent(
										rangedCheck)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										safeSpotsCheck))
							.addGroup(
								jPanel7Layout
									.createSequentialGroup()
									.addComponent(
										cleanHerbsCheck)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										dropHerbsCheck))
							.addGroup(
								jPanel7Layout
									.createSequentialGroup()
									.addComponent(
										specialAttackCheck)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										switchCheck)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										specialWeaponCombo,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										97,
										javax.swing.GroupLayout.PREFERRED_SIZE))
							.addGroup(
								jPanel7Layout
									.createSequentialGroup()
									.addComponent(
										teleportBankCheck)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										teleportCombo,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										99,
										javax.swing.GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										teleportLocationCombo,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										73,
										javax.swing.GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										keepEquippedCheck))
							.addGroup(
								jPanel7Layout
									.createSequentialGroup()
									.addComponent(
										teleportMonsterCheck)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										teleportCombo2,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										99,
										javax.swing.GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										teleportLocationCombo2,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										73,
										javax.swing.GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										keepEquippedCheck2))
							.addGroup(
								jPanel7Layout
									.createSequentialGroup()
									.addComponent(
										prayerCheck)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										prayerCombo,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										prayerAltarButton)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										prayerFlickingCheck))
							.addGroup(
								jPanel7Layout
									.createSequentialGroup()
									.addComponent(
										restAtBankCheck)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										eatAtBankCheck)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										shutdownBankCheck))
							.addComponent(
								hoverCheck))
					.addContainerGap(177, Short.MAX_VALUE)));
	jPanel7Layout
		.setVerticalGroup(jPanel7Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel7Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel7Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(
								restAtBankCheck)
							.addComponent(
								eatAtBankCheck)
							.addComponent(
								shutdownBankCheck))
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						jPanel7Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(
								rangedCheck)
							.addComponent(
								safeSpotsCheck))
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(
						jPanel7Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(
								guthansCheck)
							.addComponent(b2pCheck)
							.addComponent(
								denoteCheck))
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(
						jPanel7Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(
								cleanHerbsCheck)
							.addComponent(
								dropHerbsCheck))
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(
						jPanel7Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(
								specialAttackCheck)
							.addComponent(
								switchCheck)
							.addComponent(
								specialWeaponCombo,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE))
					.addGap(8, 8, 8)
					.addGroup(
						jPanel7Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(
								teleportBankCheck)
							.addComponent(
								teleportCombo,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(
								teleportLocationCombo,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(
								keepEquippedCheck))
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						jPanel7Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(
								teleportMonsterCheck)
							.addComponent(
								teleportCombo2,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(
								teleportLocationCombo2,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(
								keepEquippedCheck2))
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
						jPanel7Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(
								prayerCheck)
							.addComponent(
								prayerCombo,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
							.addComponent(
								prayerAltarButton)
							.addComponent(
								prayerFlickingCheck))
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(hoverCheck)
					.addContainerGap(35, Short.MAX_VALUE)));

	jTabbedPane2.addTab("General options", jPanel7);

	invisibleCheck.setText("Invisible on Minimap");

	blockedCheck.setText("Blocked by obstacle");

	dontAttackCheck.setText("Don't attack others monsters");

	screenshotCheck.setText("Take a screenshot every hour");

	taskbarCheck.setText("Taskbar notifications");

	mouseKeyCheck.setText("Use mouse keys when applicable");

	javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(
		jPanel17);
	jPanel17.setLayout(jPanel17Layout);
	jPanel17Layout
		.setHorizontalGroup(jPanel17Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel17Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel17Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
							.addGroup(
								jPanel17Layout
									.createSequentialGroup()
									.addComponent(
										invisibleCheck)
									.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(
										blockedCheck))
							.addComponent(
								taskbarCheck)
							.addComponent(
								mouseKeyCheck)
							.addComponent(
								dontAttackCheck)
							.addComponent(
								screenshotCheck))
					.addContainerGap(365, Short.MAX_VALUE)));
	jPanel17Layout
		.setVerticalGroup(jPanel17Layout
			.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(
				jPanel17Layout
					.createSequentialGroup()
					.addContainerGap()
					.addGroup(
						jPanel17Layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
							.addComponent(
								invisibleCheck)
							.addComponent(
								blockedCheck))
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(mouseKeyCheck)
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(dontAttackCheck)
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(taskbarCheck)
					.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(screenshotCheck)
					.addContainerGap(156, Short.MAX_VALUE)));

	jTabbedPane2.addTab("Options (cont.)", jPanel17);

	javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(
		jPanel15);
	jPanel15.setLayout(jPanel15Layout);
	jPanel15Layout.setHorizontalGroup(jPanel15Layout.createParallelGroup(
		javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 610,
		Short.MAX_VALUE));
	jPanel15Layout.setVerticalGroup(jPanel15Layout.createParallelGroup(
		javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 284,
		Short.MAX_VALUE));

	javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(
		jPanel10);
	jPanel10.setLayout(jPanel10Layout);
	jPanel10Layout.setHorizontalGroup(jPanel10Layout.createParallelGroup(
		javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		jPanel10Layout
			.createSequentialGroup()
			.addGap(0, 0, Short.MAX_VALUE)
			.addComponent(jPanel15,
				javax.swing.GroupLayout.PREFERRED_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE)
			.addGap(0, 1, Short.MAX_VALUE)));
	jPanel10Layout.setVerticalGroup(jPanel10Layout.createParallelGroup(
		javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		jPanel10Layout
			.createSequentialGroup()
			.addGap(0, 0, Short.MAX_VALUE)
			.addComponent(jPanel15,
				javax.swing.GroupLayout.PREFERRED_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE)
			.addGap(0, 0, Short.MAX_VALUE)));

	// TODO:jTabbedPane2.addTab("Online", jPanel10);

	javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(
		jPanel11);
	jPanel11.setLayout(jPanel11Layout);
	jPanel11Layout.setHorizontalGroup(jPanel11Layout.createParallelGroup(
		javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 611,
		Short.MAX_VALUE));
	jPanel11Layout.setVerticalGroup(jPanel11Layout.createParallelGroup(
		javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 278,
		Short.MAX_VALUE));

	// TODO:sjTabbedPane2.addTab("Statistics", jPanel11);

	startButton.setFont(new java.awt.Font("Rockwell Condensed", 0, 13));
	startButton.setText("PUSH TO START");
	startButton.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		startButtonActionPerformed(evt);
	    }
	});

	closeButton.setFont(new java.awt.Font("Rockwell Condensed", 0, 13));
	closeButton.setText("CLOSE INTERFACE");
	closeButton.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		closeButtonActionPerformed(evt);
	    }
	});

	recentUpdatesButton.setFont(new java.awt.Font("Rockwell Condensed", 0,
		14));
	recentUpdatesButton.setText("Recent Updates");
	recentUpdatesButton
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			recentUpdatesButtonActionPerformed(evt);
		    }
		});

	websiteLabel.setFont(getEpoxyFont(11));
	websiteLabel.setText("RSBots.net");

	jLabel3.setFont(new java.awt.Font("Segoe Script", 1, 12));
	jLabel3.setText("Brought to you by:");

	jLabel4.setFont(new java.awt.Font("Segoe Print", 0, 11));
	jLabel4.setText("Last Updated:");

	updatedLabel.setFont(new java.awt.Font("Segoe Print", 1, 12));
	updatedLabel.setText("6/21/11");

	downloaderButton
		.setFont(new java.awt.Font("Rockwell Condensed", 0, 13));
	downloaderButton.setText("PROFILE DOWNLOADER");
	downloaderButton.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(java.awt.event.ActionEvent evt) {
		downloaderButtonActionPerformed(evt);
	    }
	});

	loadedProfilesCombo.setFont(new java.awt.Font("Rockwell Condensed", 0,
		12));
	loadedProfilesCombo.setModel(new javax.swing.DefaultComboBoxModel(
		new String[] { "Default setting" }));
	loadedProfilesCombo
		.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
			loadedProfilesComboActionPerformed(evt);
		    }
		});

	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
		getContentPane());
	getContentPane().setLayout(layout);
	layout.setHorizontalGroup(layout
		.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(
			layout.createSequentialGroup()
				.addGroup(
					layout.createParallelGroup(
						javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
							layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
									layout.createParallelGroup(
										javax.swing.GroupLayout.Alignment.TRAILING)
										.addGroup(
											javax.swing.GroupLayout.Alignment.LEADING,
											layout.createSequentialGroup()
												.addComponent(
													startButton)
												.addPreferredGap(
													javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(
													closeButton)
												.addPreferredGap(
													javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(
													downloaderButton)
												.addPreferredGap(
													javax.swing.LayoutStyle.ComponentPlacement.RELATED,
													127,
													Short.MAX_VALUE)
												.addComponent(
													mainProgressBar,
													javax.swing.GroupLayout.PREFERRED_SIZE,
													136,
													javax.swing.GroupLayout.PREFERRED_SIZE))
										.addComponent(
											jTabbedPane2,
											javax.swing.GroupLayout.Alignment.LEADING,
											javax.swing.GroupLayout.PREFERRED_SIZE,
											616,
											javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addGroup(
							layout.createParallelGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								false)
								.addGroup(
									javax.swing.GroupLayout.Alignment.LEADING,
									layout.createSequentialGroup()
										.addGap(61,
											61,
											61)
										.addComponent(
											jLabel3)
										.addPreferredGap(
											javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
											websiteLabel)
										.addPreferredGap(
											javax.swing.LayoutStyle.ComponentPlacement.RELATED,
											javax.swing.GroupLayout.DEFAULT_SIZE,
											Short.MAX_VALUE)
										.addComponent(
											loadedProfilesCombo,
											javax.swing.GroupLayout.PREFERRED_SIZE,
											137,
											javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(
									javax.swing.GroupLayout.Alignment.LEADING,
									layout.createSequentialGroup()
										.addContainerGap()
										.addComponent(
											scriptLabel)
										.addGap(163,
											163,
											163)
										.addGroup(
											layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addGroup(
													layout.createSequentialGroup()
														.addComponent(
															jLabel4)
														.addPreferredGap(
															javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(
															updatedLabel))
												.addComponent(
													recentUpdatesButton)))))
				.addContainerGap(
					javax.swing.GroupLayout.DEFAULT_SIZE,
					Short.MAX_VALUE)));
	layout.setVerticalGroup(layout
		.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(
			layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(
					layout.createParallelGroup(
						javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
							layout.createSequentialGroup()
								.addComponent(
									scriptLabel)
								.addPreferredGap(
									javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
									layout.createParallelGroup(
										javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(
											jLabel3)
										.addComponent(
											websiteLabel)))
						.addGroup(
							layout.createSequentialGroup()
								.addGroup(
									layout.createParallelGroup(
										javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(
											updatedLabel)
										.addComponent(
											jLabel4))
								.addPreferredGap(
									javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(
									recentUpdatesButton)
								.addPreferredGap(
									javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(
									loadedProfilesCombo,
									javax.swing.GroupLayout.PREFERRED_SIZE,
									javax.swing.GroupLayout.DEFAULT_SIZE,
									javax.swing.GroupLayout.PREFERRED_SIZE)))
				.addPreferredGap(
					javax.swing.LayoutStyle.ComponentPlacement.RELATED,
					18, Short.MAX_VALUE)
				.addComponent(jTabbedPane2,
					javax.swing.GroupLayout.PREFERRED_SIZE,
					311,
					javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(
					javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(
					layout.createParallelGroup(
						javax.swing.GroupLayout.Alignment.TRAILING)
						.addGroup(
							layout.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(
									startButton)
								.addComponent(
									closeButton)
								.addComponent(
									downloaderButton))
						.addComponent(
							mainProgressBar,
							javax.swing.GroupLayout.PREFERRED_SIZE,
							javax.swing.GroupLayout.DEFAULT_SIZE,
							javax.swing.GroupLayout.PREFERRED_SIZE))
				.addContainerGap()));

	pack();
    }// </editor-fold>

    public void recentUpdatesButtonActionPerformed(
	    java.awt.event.ActionEvent evt) {
	new ChangelogUI(this, false).setVisible(true);
    }

    public void alchemyRadioActionPerformed(java.awt.event.ActionEvent evt) {
	lootRadio.setSelected(!alchemyRadio.isSelected()
		&& !bankRadio.isSelected());
	bankRadio.setSelected(!alchemyRadio.isSelected()
		&& !lootRadio.isSelected());
	bankSpinner.setEnabled(bankRadio.isSelected());

	if (alchemyRadio.isSelected()) {
	    selectedItemsList.setModel(alchList);
	}

	itemSearchFieldKeyTyped(null);
    }

    public void refreshMonstersButtonActionPerformed(
	    java.awt.event.ActionEvent evt) {
	try {
	    if (fighter.isLoggedIn()) {
		DefaultListModel mod = new DefaultListModel();

		for (NPCIterator iterator = fighter.getNPCIterator(); iterator
			.hasNext();) {
		    NPC npc = iterator.next();

		    String field = npc.getName() + ", id=" + npc.getType()
			    + ", level=" + npc.getLevel();
		    if (npc != null && npc.getLevel() > 0
			    && !mod.contains(field)) {
			mod.addElement(field);
		    }
		}

		foundMonstersList.setModel(mod);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public void safeSpotsCheckActionPerformed(java.awt.event.ActionEvent evt) {
	// TODO add your handling code here:
    }

    public void addCustomButtonActionPerformed(java.awt.event.ActionEvent evt) {
	new CustomMonsterUI(this, false).setVisible(true);
    }

    public void teleportBankCheckActionPerformed(java.awt.event.ActionEvent evt) {
	teleportCombo.setEnabled(teleportBankCheck.isSelected());
	teleportComboActionPerformed(null);

	if (!teleportBankCheck.isSelected()) {
	    teleportLocationCombo.setEnabled(false);
	    keepEquippedCheck.setEnabled(false);
	}
    }

    public void teleportMonsterCheckActionPerformed(
	    java.awt.event.ActionEvent evt) {
	teleportCombo2.setEnabled(teleportMonsterCheck.isSelected());
	teleportCombo2ActionPerformed(null);

	if (!teleportMonsterCheck.isSelected()) {
	    teleportLocationCombo2.setEnabled(false);
	    keepEquippedCheck2.setEnabled(false);
	}
    }

    public void loadedProfilesComboActionPerformed(
	    java.awt.event.ActionEvent evt) {
	// TODO add your handling code here:
    }

    public void saveSettingsButtonActionPerformed(java.awt.event.ActionEvent evt) {
	// TODO add your handling code here:
    }

    public void foodSliderMouseMoved(java.awt.event.MouseEvent evt) {

    }

    public void foodSliderMouseDragged(java.awt.event.MouseEvent evt) {
	int detectedHeal = 6;
	try {
	    if (fighter.isLoggedIn()) {
		detectedHeal = (int) (fighter.theTabs.Statistics
			.getStatBottom(Skill.HITPOINTS) * (foodSlider
			.getValue() * .1));
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

	foodLabel.setText("Heal at: " + foodSlider.getValue()
		+ "%   The bot will heal at: " + detectedHeal + "hp");
    }

    public void setTileButtonActionPerformed(java.awt.event.ActionEvent evt) {
	if (fighter != null) {
	    fighter.centerTile = fighter.getLocation();
	    setTileButton.setText(fighter.centerTile.toString());
	}
    }

    public void paintZoneButtonActionPerformed(java.awt.event.ActionEvent evt) {
	if (fighter != null) {
	    fighter.showDistanceZone = paintZoneButton.isSelected();
	}
    }

    public void zoneSpinnerMouseExited(java.awt.event.MouseEvent evt) {
	// TODO add your handling code here:
    }

    public void breaksCheckActionPerformed(java.awt.event.ActionEvent evt) {
	breakAtField.setEnabled(breaksCheck.isSelected());
	breakForField.setEnabled(breaksCheck.isSelected());
    }

    public void randomBreaksButtonActionPerformed(java.awt.event.ActionEvent evt) {
	Break randomBreak = new RandomBreak();

	breakAtField.setText("" + randomBreak.getStartTime());
	breakForField.setText("" + randomBreak.getDuration());
    }

    public void mouseSliderMouseMoved(java.awt.event.MouseEvent evt) {
	// TODO add your handling code here:
    }

    public void mouseSliderMouseDragged(java.awt.event.MouseEvent evt) {
	mouseSpeedLabel.setText("Mouse speed boost: " + mouseSlider.getValue()
		+ "%");
    }

    public void foundMonstersListMouseReleased(java.awt.event.MouseEvent evt) {
	if (foundMonstersList.getSelectedValue() != null) {
	    DefaultListModel lm = new DefaultListModel();
	    for (int i = 0; i < addMonstersList.getModel().getSize(); i++) {
		lm.addElement(addMonstersList.getModel().getElementAt(i));
	    }

	    if (!lm.contains(foundMonstersList.getSelectedValue())) {
		lm.addElement(foundMonstersList.getSelectedValue());
	    }

	    addMonstersList.setModel(lm);

	    DefaultListModel lm2 = new DefaultListModel();
	    for (int i = 0; i < foundMonstersList.getModel().getSize(); i++) {
		if (i == foundMonstersList.getSelectedIndex()) {
		    continue;
		}

		lm2.addElement(foundMonstersList.getModel().getElementAt(i));
	    }

	    foundMonstersList.setModel(lm2);
	}
    }

    public DefaultListModel getModel(JList list) {
	DefaultListModel mod = new DefaultListModel();

	for (int i = 0; i < list.getModel().getSize();) {
	    mod.addElement(list.getModel().getElementAt(i));
	}

	return mod;
    }

    public void addMonstersListMouseReleased(java.awt.event.MouseEvent evt) {
	DefaultListModel lm = (DefaultListModel) addMonstersList.getModel();
	lm.remove(addMonstersList.getSelectedIndex());
	addMonstersList.setModel(lm);
    }

    public DefaultListModel getMonsterModel() {
	return (DefaultListModel) addMonstersList.getModel();
    }

    public void itemSearchFieldKeyTyped(java.awt.event.KeyEvent evt) {
	if (fighter.masterLootTable.getIDs().length > 0) {
	    DefaultListModel list = new DefaultListModel();

	    for (LootItem li : fighter.masterLootTable
		    .getCloseMatches(itemSearchField.getText())) {
		if (bankRadio.isSelected()) {
		    list.addElement(li.toString() + " -"
			    + bankSpinner.getValue().toString());
		} else {
		    list.addElement(li);
		}
	    }

	    foundItemsList.setModel(list);
	}
    }

    public void lootRadioActionPerformed(java.awt.event.ActionEvent evt) {
	alchemyRadio.setSelected(!lootRadio.isSelected()
		&& !bankRadio.isSelected());
	bankRadio.setSelected(!lootRadio.isSelected()
		&& !alchemyRadio.isSelected());
	bankSpinner.setEnabled(bankRadio.isSelected());

	if (lootRadio.isSelected()) {
	    selectedItemsList.setModel(lootList);
	}

	itemSearchFieldKeyTyped(null);
    }

    public void bankRadioActionPerformed(java.awt.event.ActionEvent evt) {
	lootRadio.setSelected(!bankRadio.isSelected()
		&& !alchemyRadio.isSelected());
	alchemyRadio.setSelected(!bankRadio.isSelected()
		&& !lootRadio.isSelected());
	bankSpinner.setEnabled(bankRadio.isSelected());

	if (bankRadio.isSelected()) {
	    selectedItemsList.setModel(bankList);
	}

	itemSearchFieldKeyTyped(null);
    }

    public void addGoalButtonActionPerformed(java.awt.event.ActionEvent evt) {
	// TODO add your handling code here:
    }

    public void addedGoalsListMouseReleased(java.awt.event.MouseEvent evt) {
	// TODO add your handling code here:
    }

    public void specialAttackCheckActionPerformed(java.awt.event.ActionEvent evt) {
	switchCheck.setEnabled(specialAttackCheck.isSelected());
	specialWeaponCombo.setEnabled(specialAttackCheck.isSelected());
    }

    public void prayerCheckActionPerformed(java.awt.event.ActionEvent evt) {
	prayerCombo.setEnabled(prayerCheck.isSelected());
	prayerFlickingCheck.setEnabled(prayerCheck.isSelected());
	prayerAltarButton.setEnabled(prayerCheck.isSelected());
    }

    public void startButtonActionPerformed(java.awt.event.ActionEvent evt) {
	try {
	    new UserInterfaceThread(this, mainProgressBar, new Timer(5000))
		    .start();

	    Break reccurring = new Break(Integer.parseInt(breakAtField
		    .getText()), Integer.parseInt(breakForField.getText()),
		    fighter.random(10));
	    Break[] breaks = new Break[200];

	    for (int i = 0; i < 200; i++) {
		breaks[i] = reccurring;
	    }

	    fighter.breaker = new BreakHandler(breaks,
		    fighter.getLogoutHandler());
	    fighter.breaking = true;
	} catch (NumberFormatException e) {
	    e.printStackTrace();
	}

	fighter.distanceFromTile = Integer.parseInt(zoneSpinner.getValue()
		.toString());
	fighter.mouseKeys = mouseKeyCheck.isSelected();

	fighter.healAt = foodSlider.getValue();
	fighter.foodWithdraw = Integer.parseInt(foodWithdrawSpinner.getValue()
		.toString());
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
	    fighter.specialAttackWeapon = specialWeaponCombo.getSelectedItem()
		    .toString();
	}

	if (teleportBankCheck.isSelected()) {
	    String selectedItem = teleportCombo.getSelectedItem().toString();

	    for (Jewellery jewellery : Jewellery.values()) {
		if (selectedItem.equalsIgnoreCase(jewellery.getName())) {
		    fighter.jewellery = jewellery;
		    fighter.jewelleryName = jewellery.getName();
		    fighter.jewelleryLocation = teleportLocationCombo
			    .getSelectedItem().toString();
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
		    fighter.jewelleryLocation = teleportLocationCombo2
			    .getSelectedItem().toString();
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
		if (prayer.name.equalsIgnoreCase(prayerCombo.getSelectedItem()
			.toString())) {
		    fighter.prayerSpell = prayer;
		    break;
		}
	    }
	}

	List<Monster> monsters = new ArrayList<Monster>();
	for (int i = 0; i < addMonstersList.getModel().getSize(); i++) {
	    String element = addMonstersList.getModel().getElementAt(i)
		    .toString();

	    if (element.contains("id=")) {
		String[] strs = element.split(", ");
		monsters.add(new Monster(strs[0], Integer.parseInt(strs[1]
			.replace("id=", ""))));
	    }
	}

	fighter.monsters = monsters.toArray(new Monster[monsters.size()]);

	if (fighter.centerTile == null) {
	    JOptionPane.showMessageDialog(null,
		    "Set the center tile in the UI",
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

	fighter.startScript = true;
	dispose();
    }

    public void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {
	dispose();
    }

    public void downloaderButtonActionPerformed(java.awt.event.ActionEvent evt) {
	// TODO add your handling code here:
    }

    public void cleanHerbsCheckActionPerformed(java.awt.event.ActionEvent evt) {
	dropHerbsCheck.setEnabled(cleanHerbsCheck.isSelected());
    }

    public void teleportComboActionPerformed(java.awt.event.ActionEvent evt) {
	String selectedItem = teleportCombo.getSelectedItem().toString();
	DefaultComboBoxModel teleportModel = new DefaultComboBoxModel();

	for (Jewellery jewellery : Jewellery.values()) {
	    if (selectedItem.equalsIgnoreCase(jewellery.getName())) {
		keepEquippedCheck.setEnabled(true);
		teleportLocationCombo.setEnabled(true);

		for (String location : jewellery.getLocations()) {
		    teleportModel.addElement(location);
		}

		teleportLocationCombo.setModel(teleportModel);
		break;
	    }
	}

	for (TeleportTab teleportTab : TeleportTab.values()) {
	    if (selectedItem.equalsIgnoreCase(teleportTab.getName())) {
		keepEquippedCheck.setEnabled(false);
		teleportLocationCombo.setEnabled(false);
	    }
	}
    }

    public void teleportCombo2ActionPerformed(java.awt.event.ActionEvent evt) {
	String selectedItem = teleportCombo2.getSelectedItem().toString();
	DefaultComboBoxModel teleportModel = new DefaultComboBoxModel();

	for (Jewellery jewellery : Jewellery.values()) {
	    if (selectedItem.equalsIgnoreCase(jewellery.getName())) {
		keepEquippedCheck2.setEnabled(true);
		teleportLocationCombo2.setEnabled(true);

		for (String location : jewellery.getLocations()) {
		    teleportModel.addElement(location);
		}

		teleportLocationCombo2.setModel(teleportModel);
		break;
	    }
	}

	for (TeleportTab teleportTab : TeleportTab.values()) {
	    if (selectedItem.equalsIgnoreCase(teleportTab.getName())) {
		keepEquippedCheck2.setEnabled(false);
		teleportLocationCombo2.setEnabled(false);
	    }
	}
    }

    public void foundItemsListMouseReleased(java.awt.event.MouseEvent evt) {
	if (foundItemsList.getSelectedValue() != null) {
	    DefaultListModel lm = new DefaultListModel();
	    for (int i = 0; i < selectedItemsList.getModel().getSize(); i++) {
		lm.addElement(selectedItemsList.getModel().getElementAt(i));
	    }

	    if (!lm.contains(foundItemsList.getSelectedValue())) {
		if (!bankRadio.isSelected()) {
		    lm.addElement(foundItemsList.getSelectedValue());
		} else {
		    String bank = foundItemsList.getSelectedValue().toString();

		    if (bank.contains("stackable")) {
			bank.replace("(stackable)", "");
		    }

		    String[] strs = bank.split(" -");
		    lm.addElement(new BankItem(strs[0], Integer
			    .parseInt(strs[1].replace("-", ""))));
		}
	    }

	    selectedItemsList.setModel(lm);

	    DefaultListModel lm2 = new DefaultListModel();
	    for (int i = 0; i < foundItemsList.getModel().getSize(); i++) {
		if (i == foundItemsList.getSelectedIndex()) {
		    continue;
		}

		lm2.addElement(foundItemsList.getModel().getElementAt(i));
	    }

	    foundItemsList.setModel(lm2);
	    updateItemLists();
	}
    }

    public void updateItemLists() {
	if (bankRadio.isSelected()) {
	    bankList = (DefaultListModel) selectedItemsList.getModel();
	} else if (alchemyRadio.isSelected()) {
	    alchList = (DefaultListModel) selectedItemsList.getModel();
	} else if (lootRadio.isSelected()) {
	    lootList = (DefaultListModel) selectedItemsList.getModel();
	}
    }

    public void selectedItemsListMouseReleased(java.awt.event.MouseEvent evt) {
	DefaultListModel lm = (DefaultListModel) selectedItemsList.getModel();
	lm.remove(selectedItemsList.getSelectedIndex());
	selectedItemsList.setModel(lm);

	updateItemLists();
    }

    public void bankSpinnerMouseReleased(java.awt.event.MouseEvent evt) {
	if (bankRadio.isSelected()) {
	    itemSearchFieldKeyTyped(null);
	}
    }

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String args[]) {
	java.awt.EventQueue.invokeLater(new Runnable() {

	    public void run() {
		DreamFighterUI ui = new DreamFighterUI(null);
		ui.setVisible(true);
		// new UserInterfaceThread(ui, ui.mainProgressBar).start();
	    }
	});
    }

    // Variables declaration - do not modify
    public javax.swing.JButton addCustomButton;
    public javax.swing.JButton addGoalButton;
    public javax.swing.JList addMonstersList;
    public javax.swing.JList addedGoalsList;
    public javax.swing.JRadioButton alchemyRadio;
    public javax.swing.JComboBox attackModeCombo;
    public javax.swing.JCheckBox b2pCheck;
    public javax.swing.JRadioButton bankRadio;
    public javax.swing.JSpinner bankSpinner;
    public javax.swing.JCheckBox blockedCheck;
    public javax.swing.JTextField breakAtField;
    public javax.swing.JTextField breakForField;
    public javax.swing.JCheckBox breaksCheck;
    public javax.swing.JCheckBox buryBonesCheck;
    public javax.swing.JCheckBox cleanHerbsCheck;
    public javax.swing.JButton closeButton;
    public javax.swing.JCheckBox denoteCheck;
    public javax.swing.JCheckBox dontAttackCheck;
    public javax.swing.JButton downloaderButton;
    public javax.swing.JCheckBox dropHerbsCheck;
    public javax.swing.JCheckBox eatAtBankCheck;
    public javax.swing.JCheckBox eatForLootCheck;
    public javax.swing.JComboBox foodCombo;
    public javax.swing.JLabel foodLabel;
    public javax.swing.JSlider foodSlider;
    public javax.swing.JSpinner foodWithdrawSpinner;
    public javax.swing.JList foundItemsList;
    public javax.swing.JList foundMonstersList;
    public javax.swing.JComboBox goalSkillCombo;
    public javax.swing.JCheckBox guthansCheck;
    public javax.swing.JCheckBox hoverCheck;
    public javax.swing.JTextField ignoreLevelsField;
    public javax.swing.JCheckBox invisibleCheck;
    public javax.swing.JTextField itemSearchField;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel11;
    public javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel13;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel16;
    public javax.swing.JLabel jLabel18;
    public javax.swing.JLabel jLabel19;
    public javax.swing.JLabel jLabel21;
    public javax.swing.JLabel jLabel22;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel10;
    public javax.swing.JPanel jPanel11;
    public javax.swing.JPanel jPanel12;
    public javax.swing.JPanel jPanel13;
    public javax.swing.JPanel jPanel14;
    public javax.swing.JPanel jPanel15;
    public javax.swing.JPanel jPanel16;
    public javax.swing.JPanel jPanel17;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JPanel jPanel6;
    public javax.swing.JPanel jPanel7;
    public javax.swing.JPanel jPanel8;
    public javax.swing.JPanel jPanel9;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JScrollPane jScrollPane5;
    public javax.swing.JTabbedPane jTabbedPane2;
    public javax.swing.JCheckBox keepEquippedCheck;
    public javax.swing.JCheckBox keepEquippedCheck2;
    public javax.swing.JComboBox levelCombo;
    public javax.swing.JComboBox loadedProfilesCombo;
    public javax.swing.JTextField lootOverField;
    public javax.swing.JRadioButton lootRadio;
    public javax.swing.JProgressBar mainProgressBar;
    public javax.swing.JCheckBox mouseKeyCheck;
    public javax.swing.JSlider mouseSlider;
    public javax.swing.JLabel mouseSpeedLabel;
    public javax.swing.JToggleButton paintZoneButton;
    public javax.swing.JToggleButton prayerAltarButton;
    public javax.swing.JCheckBox prayerCheck;
    public javax.swing.JComboBox prayerCombo;
    public javax.swing.JCheckBox prayerFlickingCheck;
    public javax.swing.JTextField profileNameField;
    public javax.swing.JButton randomBreaksButton;
    public javax.swing.JCheckBox rangedCheck;
    public javax.swing.JButton recentUpdatesButton;
    public javax.swing.JButton refreshMonstersButton;
    public javax.swing.JCheckBox restAtBankCheck;
    public javax.swing.JCheckBox rightClickCheck;
    public javax.swing.JCheckBox sactterAshesCheck;
    public javax.swing.JCheckBox safeSpotsCheck;
    public javax.swing.JButton saveSettingsButton;
    public javax.swing.JCheckBox screenshotCheck;
    public javax.swing.JLabel scriptLabel;
    public javax.swing.JList selectedItemsList;
    public javax.swing.JButton setTileButton;
    public javax.swing.JCheckBox shutdownBankCheck;
    public javax.swing.JRadioButton shutdownRadio;
    public javax.swing.JCheckBox specialAttackCheck;
    public javax.swing.JComboBox specialWeaponCombo;
    public javax.swing.JButton startButton;
    public javax.swing.JCheckBox switchCheck;
    public javax.swing.JCheckBox taskbarCheck;
    public javax.swing.JCheckBox telegrabCheck;
    public javax.swing.JCheckBox teleportBankCheck;
    public javax.swing.JComboBox teleportCombo;
    public javax.swing.JComboBox teleportCombo2;
    public javax.swing.JComboBox teleportLocationCombo;
    public javax.swing.JComboBox teleportLocationCombo2;
    public javax.swing.JCheckBox teleportMonsterCheck;
    public javax.swing.JLabel updatedLabel;
    public javax.swing.JCheckBox waitLootCheck;
    public javax.swing.JLabel websiteLabel;
    public javax.swing.JSpinner zoneSpinner;
    // End of variables declaration
}
