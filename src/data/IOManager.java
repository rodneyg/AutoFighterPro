package data;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.UIManager;

public class IOManager {
    public static final String SEPARATOR = File.separator;

    public static final String SCREENSHOT_FOLDER = SEPARATOR + "screenshots"
	    + SEPARATOR;
    public static final String SETTINGS_FOLDER = SEPARATOR + "settings"
	    + SEPARATOR;
    public static final String DOWNLOADS_FOLDER = SEPARATOR + "downloads"
	    + SEPARATOR;
    public static final String STATISTICS_FOLDER = SEPARATOR + "statistics"
	    + SEPARATOR;

    public static final String LOOT_LOG_FILE = STATISTICS_FOLDER + "loot.log";
    public static final String KILL_LOG_FILE = STATISTICS_FOLDER + "kill.log";
    public static final String ITEM_DATA_FILE = DOWNLOADS_FOLDER
	    + "itemData.csv";

    public static final String OLD_PROFILE_EXTENSION = ".PRO_SETTINGS";
    public static final String PROFILE_EXTENSION = ".settings";

    public static final String ITEM_DATA_URL = "https://s3.amazonaws.com/iBot/itemlist.csv";
    public static final String SYNTHETICA_URL = "http://www.modernvirtualtechnologies.com/IMPSoft%20Resources/Synthetica/synthetica.jar";
    public static final String SYNTHETICA2D_URL = "http://www.modernvirtualtechnologies.com/IMPSoft%20Resources/Synthetica/syntheticaSimple2D.jar";
    public static final String SYNTHETICA_BATIK_URL = "http://www.modernvirtualtechnologies.com/IMPSoft%20Resources/Synthetica/syntheticaBatik.jar";
    public static final String SYNTHETICA_BLACK_URL = "http://www.modernvirtualtechnologies.com/IMPSoft%20Resources/Synthetica/syntheticaBlackEye.jar";
    public static final String EPOXY_URL = "http://www.modernvirtualtechnologies.com/IMPSoft%20Resources/Fighter/EpoXY_histoRy.ttf";
    public static final String LOGGER_ZIP_URL = "http://www.modernvirtualtechnologies.com/IMPSoft%20Graphics/paints/Fighter/logger.zip";

    public IOManager() {
	super();
    }

    public static void getLoggerImages(String fighterName) throws IOException {
	Download loggerZip = new Download(IOManager.LOGGER_ZIP_URL, fighterName
		+ IOManager.SEPARATOR + IOManager.STATISTICS_FOLDER);

	loggerZip.download();

	int downloadStatus = loggerZip.getStatus();
	while ((downloadStatus = loggerZip.getStatus()) != Download.CANCELLED
		&& downloadStatus != Download.COMPLETE
		&& downloadStatus != Download.ERROR) {
	    try {
		Thread.sleep(1000);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }

    public String getCurrentDate() {
	Date today = Calendar.getInstance().getTime();
	SimpleDateFormat formatter = new SimpleDateFormat(
		"[EEE] [MMM-dd] [hh.mm]");
	return formatter.format(today).replace(" ", "");
    }

    public static ImageManager getImageManager() {
	return new ImageManager();
    }

    public static FileManager getFileManager() {
	return new FileManager();
    }

    public static void setNativeLookAndFeel() {
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (Exception e) {
	    System.out.println("Error setting native LAF: " + e);
	}
    }

    public static boolean isWindows() {
	String os = System.getProperty("os.name").toLowerCase();// windows
	return (os.indexOf("win") >= 0);
    }

    public static boolean isMac() {
	String os = System.getProperty("os.name").toLowerCase();// Mac
	return (os.indexOf("mac") >= 0);

    }

    public static boolean isUnix() {
	String os = System.getProperty("os.name").toLowerCase();// linux or unix
	return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);

    }
}