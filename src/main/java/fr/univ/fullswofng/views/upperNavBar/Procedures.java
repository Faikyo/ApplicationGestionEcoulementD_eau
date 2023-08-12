

package fr.univ.fullswofng.views.upperNavBar;

import fr.univ.fullswofng.HelloApplication;
import fr.univ.fullswofng.controleur.ControllerImpl;
import javafx.geometry.Dimension2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;



/**
 * @brief
 * This class provides static methods used by the user interface, most notably
 * for opening and saving files, or creating a new project.
 * 
 */
public class Procedures {
	public static Locale currentLocale;
	/**
	 * Resource bundle to load properties file
	 */
	private static ResourceBundle rb;

	/**
	 * The main window currently used by the interface
	 */
	private static Stage workingUI;

	//Pour initialiser le stage
	public Procedures(Stage stage) {
		workingUI=stage;

	}
	//Pour lancer les méthodes
	public Procedures() {
		initRb();
	}

	public static void showMessageDialog(Stage owner, String message, String title, Alert.AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.initOwner(owner);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);

		alert.showAndWait();
	}

	/**
	 * Set currentLocale with the default language
	 * */
	public static void defaultSettings(){
		currentLocale = Locale.getDefault();
	}

	/**
	 * Set another language in the preferences files for the next start of the UI
	 * @param locale The new language which will be set
	 */
	public static void addUILanguage(Locale locale){
		String userHome = System.getProperty("user.home");
		if (userHome == null) {
			return;
		}
		File settingsDirectory = new File(userHome, ".ui_transfer");
		if (!settingsDirectory.exists())
			settingsDirectory.mkdir();
		File settingsFile = new File(settingsDirectory,
				"settings.properties");
		Properties preferences = new Properties();
		FileInputStream fis;
		if(settingsFile.exists()) {
			try{
				fis = new FileInputStream(settingsFile);
				preferences.load(fis);
			}catch(Exception ignored) {
			}
		}
		preferences.setProperty("language", String.valueOf(locale));
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(settingsFile);
			preferences.store(fos,
					"Preferences file for tranfer_ui");
		} catch (IOException ignored) {
		}
	}

	/**
	 * Get the language in the preferences file
	 * @return The language in type Locale
	 */
	public static Locale getUILanguage(){
		String userHome = System.getProperty("user.home");
		File settingsFile = new File(new File(userHome, ".ui_transfer"),
				"settings.properties");
		if (!settingsFile.exists())
			defaultSettings();
		Properties preferences = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream(settingsFile);
			preferences.load(fis);
		} catch (IOException e) {
			defaultSettings();
		}
		String langCode = preferences.getProperty("language", "default");
		currentLocale = new Locale(langCode);
		return currentLocale;
	}

	public void initRb(){
		setRb(ResourceBundle.getBundle("language/TransferParameters", getUILanguage()));
	}
	/**
	 * Setter of resource bundle
	 * @param rb The resource bundle
	 */

	public void setRb(ResourceBundle rb){
		this.rb = rb;
	}

	public static ResourceBundle getRb() {
		return rb;
	}
	private ControllerImpl controller;



	/**
	 * The  directory opened with a file chooser.
	 */
	private static String pathRedirection="";
	/**
	 *  The  directory opened with a file chooser (button Browse)
	 */
	private static String pathRedirectionBrowse="";
	/**
	 * The resource bundle containing the locale-specific strings displayed on
	 * the user interface
	 */
	public static ResourceBundle messages = setUpLocalization();

	/**
	 * The last directory opened with a file chooser. The next time a file
	 * chooser is opened, it should be set to this location
	 */
	private static File lastDirectoryUsed = null;

	/**
	 * The maximum number of files in the recent files menu
	 */
	private static final int RECENT_FILES_LIST_LENGTH = 5;

	/**
	 * The list of recently used project files
	 */
	private static List<File> recentFilesList = loadRecentlyUsedFiles();

	/**
	 * The project file (*.fsp file) currently opened in the application. It is
	 * set when a project is saved or opened, and reset to null when a new
	 * project is created
	 */
	private static File workingProject;



	/**
	 * The directory associated the workingProject. Set in the same manner as
	 * workingProject
	 */
	private static File workingDirectory;





	/**
	 * The output directory used when TransferUserInterfaceJFX is launched. This directory must
	 * be located directly under workingDirectory and be named Outputs*
	 */
	private static File outputDirectory;

	/**
	 * True if the generated parameters.txt must contain descriptive comments
	 * for each parameter, false if it limited to the tags and their values
	 */
	private static boolean verboseParametersFiles;

	/**
	 * True if the parameters have been changed since the last time the project
	 * was saved. FullSWOF_UI does not keep an undo history. This variable is
	 * used only to decide if confirmation messages should be displayed when the
	 * user attempts an action such as opening another project without saving
	 * the current project first
	 */
	static boolean changeSinceLastSave = false;

	public static void setPreferences() {
		List<String> languages = Arrays.asList("Default", "English", "Français");
		ChoiceDialog<String> languageDialog = new ChoiceDialog<>("Default", languages);
		languageDialog.setTitle("Langue");
		languageDialog.setHeaderText("Choisissez une langue :");
		languageDialog.setContentText("Langue :");

		Optional<String> result = languageDialog.showAndWait();

		result.ifPresent(language -> {
			Locale locale;
			switch (language) {
				case "Français":
					locale = new Locale("fr");
					break;
				case "English":
					locale = new Locale("en");
					break;
				default:
					locale = Locale.getDefault();
			}

			// Ajouter la nouvelle langue et redémarrer l'application
			addUILanguage(locale);
			restartApplication();
		});

	}

	private static void restartApplication() {
		// Affichez un message indiquant que l'application doit être redémarrée
		Alert alert = new Alert(Alert.AlertType.INFORMATION, "Veuillez redémarrer l'application pour appliquer les modifications de langue.", ButtonType.OK);
		alert.showAndWait();

		// Fermez l'application actuelle
		workingUI.close();

		// Redémarrez l'application
		// Notez que cette méthode ne fonctionne que si vous exécutez votre application à partir d'un fichier JAR
		// Si vous exécutez votre application depuis un IDE, vous devrez la redémarrer manuellement
		try {
			Runtime.getRuntime().exec("java -jar your-application.jar");  //TODO
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @details
	 * Displays credits and license information.
	 */

	public static void about() {
		AboutHtml help = new AboutHtml(getRb(), HelloApplication.hostServices);
		help.show();
	}

	/**
	 * @details
	 * Terminates the application after a confirmation message.
	 */
	public static void close() {
		// if the user answers yes to a confirmation message
		if (showConfirmDialog(workingUI,messages.getString("exitMessage"),
				messages.getString("exitTitle"))) {
				// end the application
				System.exit(0);
		}
	}
	public static boolean showConfirmDialog(Stage owner, String message, String title) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.initOwner(owner);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);

		Optional<ButtonType> result = alert.showAndWait();
		return result.isPresent() && result.get() == ButtonType.OK;
	}

	/**
	 * @return The  directory opened with a file chooser
	 */
	public static String getPathRedirection() {
		return pathRedirection;
	}

	/**
	 * @return the last directory opened with a file chooser
	 */
	public static File getLastDirectoryUsed() {
		if(lastDirectoryUsed==null){
			File defaultDirectory = new File(pathRedirection);
			if (defaultDirectory.isDirectory()) {
				return defaultDirectory;
			} else {
				// Si le répertoire par défaut n'est pas valide, retournez null
				return null;
			}
		}
		return lastDirectoryUsed;
	}


	/**
	 * @return the resource bundle used by the application to display UI
	 *         messages
	 */
	public ResourceBundle getMessages() {
		return messages;
	}

	/**
	 * @return the output directory used when TransfertUserInterface is launched
	 */
	public static File getOutputDirectory() {
		return outputDirectory;
	}

	/**
	 * @return the list of recently used files
	 */
	public static List<File> getRecentFilesList() {
		return recentFilesList;
	}

	/**
	 * @return the directory associated the working project
	 */
	public static File getWorkingDirectory() {
		return workingDirectory;
	}

	/**
	 * @return the project file (*.fsp file) currently opened in the application
	 */
	public static File getWorkingProject() {
		return workingProject;
	}

	/**
	 * @return the main window currently used by the interface
	 */
	public Stage getWorkingUI() {
		return workingUI;
	}

	/**
	 * @return true if the parameters have been changed since the last time the
	 *         project was saved
	 */
	public static boolean hasChangedSinceLastSave() {
		return changeSinceLastSave;
	}

	/**
	 * @details
	 * Opens a HTML help file in a browser. The file chosen depends on the
	 * current locale. The method will try to get the file corresponding to the
	 * current localization or fall back on a default file. The method to open a
	 * web browser is OS-dependent.
	 */
	public static void help() {
		URL url;
		Locale lang=getUILanguage();
		String langs = lang.toLanguageTag();
		if (langs.equals("fr")){
			url = Procedures.class.getResource("/doc/user_manual_fr.html");

		}
		else if (langs.equals("en")){
			url = Procedures.class.getResource("/doc/user_manual_en.html");
		}
		else{
			url = Procedures.class.getResource("/doc/user_manual_en.html");

		}
		String title = "User manual";
		PrintHtml help = null;
		try {
			help = new PrintHtml(url, title);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		help.setSize(new Dimension2D(700, 500));
		help.setLocationRelativeTo(null);


	}


	/**
	 * @details
	 * Allows the user to choose a file and attempt to read it as a parameters
	 * file to import its parameters in the working project. There is no
	 * restriction on the name or format of the file, but it should follow the
	 * rules of a parameters.txt file or the import will not succeed. The method
	 * does not check the validity of the file, the parameters are simply left
	 * unmodified if the syntax of the file is bad.
	 */
	public static void importParameters() {
		// confirmation message (the current parameters are all lost during the import)
		if (showConfirmDialog(workingUI, messages.getString("importParametersConfirm"),
				messages.getString("importParametersTitle"))){
			// open a file chooser to the last used directory
			FileChooser chooser = new FileChooser();
			chooser.setInitialDirectory(getLastDirectoryUsed());
			File selectedFile = chooser.showOpenDialog(workingUI);
			if (selectedFile != null) {
				File parameters = selectedFile;
				setLastDirectoryUsed(selectedFile.getParentFile());
				/*try {
					// set the parameters to the values in the selected file
					workingModel.fromFile(parameters);
				} catch (IOException e) {
					// an error occurred that prevented the file from being read
					controller.showMessageDialog(workingUI, HTML_OPENING				TODO
							+ messages.getString("importParametersTitle")
							+ HTML_ENDING,
							messages.getString("parametersFileNotRead"), Alert.AlertType.ERROR);
				}*/
			}
		}
	}

	/**
	 * @details
	 * Allows the user to create a new project, using the default configuration
	 * choice. This method is called upon starting the application.
	 */
	public static void newProject() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Création d'un nouveau projet");
		alert.setContentText("Êtes-vous sûr que vous voulez créer un nouveau projet? Vous allez perdre toutes les données actuelles.");

		// Option pour la réponse de l'utilisateur
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			// L'utilisateur a confirmé, effacer toutes les données
//			clearData();
//  		setModel(defaultConfiguration);   TODO
			// set the working project to null (not yet saved)
			workingProject = null;
			// change the title of the main window
			workingUI.setTitle(messages.getString("applicationName"));
			setPathRedirectionBrowse(getPathRedirection());
			if(getPathRedirection()!=null){
				setLastDirectoryUsed(new File(getPathRedirection()));
			}
		}


	}

	/**
	 * @details
	 * Same as newProject(), after a confirmation message displayed if the
	 * current project has been changed and not saved.
	 */
	public static void newProjectAfterConfirmation() {
		if (!changeSinceLastSave
				|| showConfirmDialog(workingUI,messages.getString("newProjectConfirm"),
				messages.getString("newProjectTitle"))) {
			newProject();
		}
	}

	/**
	 * @details
	 * Allows the user to open a project created with FullSWOF_UI (*.fsp) file.
	 * A confirmation message is displayed if the current project has been
	 * changed and not saved.
	 */
	public static void openProject() {
		if (!changeSinceLastSave
				|| showConfirmDialog(workingUI,messages.getString("openProjectConfirm"), messages.getString("openProjectTitle"))) {
			FileChooser chooser = new FileChooser();
			chooser.setInitialDirectory(getLastDirectoryUsed());
			FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("FSP Files", "*.fsp");
			chooser.getExtensionFilters().add(filter);
			File selectedFile =chooser.showOpenDialog(workingUI);
			if ( selectedFile !=null) {
				openProject(selectedFile);
				setLastDirectoryUsed(selectedFile.getParentFile());
			}
		}
	}

	/**
	 * @details
	 * Attempts to open the project save in a file
	 * 
	 * @param f
	 *            the project file
	 * @return true if the project was successfully opened
	 */
	public static boolean openProject(File f) {
		if (Procedures.hasChangedSinceLastSave()
				&& showConfirmDialog(workingUI,messages.getString("openProjectConfirm"), messages.getString("openProjectTitle"))) {
			return false;
		}
		File oldWorkingProject = workingProject;
		File oldWorkingDirectory = workingDirectory;
		workingProject = f;
		String workingDirectoryPath = f.getAbsolutePath();
		int dot = workingDirectoryPath.lastIndexOf('.');
		if (dot != -1)
			workingDirectoryPath = workingDirectoryPath.substring(0, dot);
		workingDirectory = new File(workingDirectoryPath);
		pathRedirectionBrowse=workingDirectoryPath;
		if (projectIsReady()) {
			try {
				Properties project = new Properties();
				FileInputStream fis = new FileInputStream(workingProject);
				project.loadFromXML(fis);
				String projectConfig = project.getProperty("configuration", "");
				fis.close();
/*				for (Node c : AVAILABLE_CONFIGURATIONS) {
					if (projectConfig.equals(c.getName())) {
						setModel(c);
						break;
					}
				}*/
				File parameters = new File(
						new File(workingDirectory, "Inputs"), "parameters.txt");
//				workingModel.fromFile(parameters);  TODO verifie le fichier
				workingUI.setTitle(messages
						.getString("applicationName")
						+ " - "
						+ workingProject.getName());
				changeSinceLastSave = false;
				addRecentlyUsedFile(workingProject);
				return true;
			} catch (Exception e) {
				showMessageDialog(workingUI,messages.getString("parametersFileNotRead"), messages.getString("openProjectTitle"),
						Alert.AlertType.ERROR);
				return false;
			}
		} else {
			workingProject = oldWorkingProject;
			workingDirectory = oldWorkingDirectory;
			showMessageDialog(workingUI,messages.getString("invalidProjectFile"),
					messages.getString("openProjectTitle"),
					Alert.AlertType.ERROR);
			return false;
		}
	}
	/**
	 * @return The  directory opened with a file chooser (button Browse)
	 */
	public static String getPathRedirectionBrowse() {
		return pathRedirectionBrowse;
	}

	/**
	 * @details
	 * Allows the user to open a previously computed result file (*.dat) to get
	 * a 3D view of it.
	 */
	public static void openVisualisation() {
		if (changeSinceLastSave || workingProject == null) {
			if (showConfirmDialog(workingUI,messages.getString("saveProjectFirst"),
					messages.getString("runTitle"))) {
				saveProject();
			} else {
				return;
			}
		}
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(new File(getPathRedirectionBrowse()));
		File selectedFile = chooser.showOpenDialog(workingUI);

		if (selectedFile != null) {
			try {
				setLastDirectoryUsed(selectedFile);
//			VisualizationFile.open(chooser.getSelectedFile());  TODO
//		    } catch (IOException e) {
//			showMessageDialog(
//						      Procedures.getWorkingUI(),
//						      "<html><body width='400'><p>"
//						      + messages.getString("outputFileOpeningError")
//						      + "</p></body></html>",
//						      messages.getString("visualizationTitle"),
//						      Alert.AlertType.ERROR);
//		    }
			} catch (Exception e) {
				throw new RuntimeException(e);
			}


		}
	}


	/**
	 * @details
	 * Called upon saving or opening of a project to check that all the
	 * necessary directories and files are present on the disk
	 * 
	 * @return true if all the necessary directories and files are present on
	 *         the disk, false otherwise
	 */
	public static boolean projectIsReady() {
		// the project file and its associated directory must be set and exist
		if (workingProject == null || workingDirectory == null
				|| !workingProject.exists() || !workingDirectory.exists())
			return false;
		String projectConfig;
		try {
			// read the project file
			Properties project = new Properties();
			FileInputStream fis = new FileInputStream(workingProject);
			project.loadFromXML(fis);
			// the only information in this file is the configuration used
			projectConfig = project.getProperty("configuration", "");
			fis.close();
		} catch (IOException e) {
			// an error occured that prevented the project file from being read
			return false;
		}
		/*
		 * check if the string config is among the configurations available
		 */
		/*for (int i = 0; i < AVAILABLE_CONFIGURATIONS.length; i++) {
			if (AVAILABLE_CONFIGURATIONS[i].toString().equals(projectConfig)) {
				break;														TODO
			} else if (i == AVAILABLE_CONFIGURATIONS.length - 1) {
				// the end of the configuration list was reached
				return false;
			}
		}*/
		/*
		 * Check whether the Inputs directory and Inputs/parameters.txt file
		 * exist
		 */
		File inputsDirectory = new File(workingDirectory, "Inputs");
		if (!inputsDirectory.exists())
			return false;
		File parameters = new File(new File(workingDirectory, "Inputs"),
				"parameters.txt");
		if (!parameters.exists())
			return false;
		return true;
	}

	/**
	 * @details
	 * Allows the user to launch FullSWOF with the parameters of the current
	 * project. The parameters must be valid, otherwise an error message is
	 * displayed. If the project has not been saved, the interface will ask the
	 * user to do it.
	 */
	public static void runProject() {
		String command = "";
		// get the command corresponding to the version of fullSWOF currently
		// used
		/*for (int i = 0; i < AVAILABLE_CONFIGURATIONS.length; i++) {
			if (AVAILABLE_CONFIGURATIONS[i] == workingModel) {
				// display a message if no command was set for this version then
				// exit the procedure
				if (launchingCommands[i] == null					TODO la commande à éxécuter
						|| launchingCommands[i].equals("")) {
					controller.showMessageDialog(workingUI,messages.getString("commandNotFound"), messages.getString("runTitle"),
							Alert.AlertType.ERROR);
					return;
				} else
					command = launchingCommands[i];
			}
		}*/

		// display a warning if the project has changed or was never saved
		if (changeSinceLastSave || workingProject == null) {
			if (showConfirmDialog(workingUI,messages.getString("saveProjectFirst"),
					messages.getString("runTitle"))) {
				saveProject();
			} else {
				return;
			}
		}
		// validate the project (includes copying files...) and check that there
		// are no invalid inputs
/*		if (!workingController.validate(workingDirectory)) {
			controller.showMessageDialog(workingUI,messages.getString("verifyInput"), messages.getString("runTitle"),
					Alert.AlertType.ERROR);
		} else {
			// warn the user if the output directory is not empty
			if (outputDirectory.list().length > 0
					&& (
							showConfirmDialog(							TODO vérifie
									workingUI,messages
													.getString("outputDirectoryContainsFiles"), messages
											.getString("runTitle")))) {
				return;
			}
			// run FullSWOF
			FullswofIO.exec(command, workingDirectory, outputDirectory);
		}*/
	}

	/**
	 * @details
	 * Saves the current project, overwriting the old files if the user allows
	 * it. If the current project is null (first time it is saved), this method
	 * works in the same way as saveProjectAs() .
	 */
	public static void saveProject() {
		if (workingProject == null || workingDirectory == null) {
			saveProjectAs();
		} else {

//			 * If the model saved is not entirely valid, a confirmation message
//			 * is displayed The user can still chose to save the model

/*			if (!workingModel.isValid()) {
				if (showConfirmDialog(
						null,
						HTML_OPENING
								+ messages.getString("wrongValuesUponSave")
								+ HTML_ENDING,
						messages.getString("saveProjectTitle"))) {
					return;
				}
			}*/
/*			if(workingModel.getName().compareTo("FullSWOF_2D")==0){
				Definition_2D.checkFile();
			}
			else{
				Definition_1D.checkFile();
			}*/

//			 * If a parameters file already exists, a confirmation message is
//			 * displayed

			File parameters = new File(new File(workingDirectory, "Inputs"),
					"parameters.txt");
			if (parameters.exists()
					&& showConfirmDialog(workingUI,messages.getString("overwriteConfirm"),
							messages.getString("saveProjectTitle"))) {
				return;
			}

//			 * Preparation of the project folders if needed

			if (!projectIsReady())
				prepareProject();

//			 * Validation (workingUI includes creating the outputs directory and
//			 * copying the annex files where they are needed)

//			workingController.validate(workingDirectory);  TODO verifier les données

//			 * Writing of the parameters file

			try {
				FileWriter fw = new FileWriter(parameters);
//   				fw.write(workingModel.toFile(verboseParametersFiles));  TODO Ecrit les données dans le fichier parametres
				fw.close();
			} catch (IOException e) {
				showMessageDialog(workingUI,messages.getString("saveProjectWritingError"), messages.getString("saveProjectTitle"),
						Alert.AlertType.ERROR);
			}

//			 * Writing of the project file

			Properties project = new Properties();
			project.setProperty("configuration", "");
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(workingProject);
				project.storeToXML(fos, "FullSWOF_TRANSFERT project file");
				fos.close();
			} catch (IOException e) {
				showMessageDialog(workingUI,messages.getString("saveProjectWritingError"), messages.getString("saveProjectTitle"),
						Alert.AlertType.ERROR);
			}
			// project was just saved, no change since last save
			changeSinceLastSave = false;
			// change the title of the main window to include the name of the
			// project
			workingUI.setTitle(messages.getString("applicationName")
					+ " - " + workingProject.getName());
			// message to confirm the success of the operation
			showMessageDialog(workingUI,messages.getString("projectSaved"),
					messages.getString("saveProjectTitle"),
					Alert.AlertType.INFORMATION);
		}
	}

	/**
	 * @details
	 * Allows the user to save the current project as a new file. This new file
	 * is the current project after this operation.
	 */
	public static void saveProjectAs() {
		FileChooser chooser = new FileChooser();
		chooser.setInitialDirectory(getLastDirectoryUsed());
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("FSP Files", "*.fsp");
		chooser.getExtensionFilters().add(filter);

		File selectedFile =chooser.showOpenDialog(workingUI);

		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle(messages.getString("saveProjectTitle"));
		File selectedDirectory = directoryChooser.showDialog(workingUI);


		if (selectedDirectory != null) {
			if (selectedFile!=null) {
				// if the project already exist
				workingProject = selectedFile;
				setLastDirectoryUsed(selectedFile);
				String workingDirectoryPath = selectedFile
						.getAbsolutePath();
				/*
				 * strip the selected pathname of its file extension this is the
				 * name of the directory used for the project
				 */
				int dot = workingDirectoryPath.lastIndexOf('.');
				if (dot != -1)
					workingDirectoryPath = workingDirectoryPath.substring(0,
							dot);
				workingDirectory = new File(workingDirectoryPath);
				pathRedirectionBrowse=workingDirectoryPath;
				saveProject();
			} else {
				// if this is a new file
				String selectedPath = selectedDirectory
						.getAbsolutePath();
				// strip the selected pathname of its file extension
				int ext = selectedPath.lastIndexOf(".fsp");
				if (ext != -1)
					selectedPath = selectedPath.substring(0, ext);
				workingDirectory = new File(selectedPath);
				workingDirectory.mkdir();
				workingProject = new File(selectedPath + ".fsp");
				setPathRedirectionBrowse(workingDirectory.getAbsolutePath());
				try {
					// write the project file
					Properties project = new Properties();
					project.setProperty("configuration","");
					FileOutputStream fos;
					fos = new FileOutputStream(workingProject);
					project.storeToXML(fos, "FullSWOF_UI project file");
					fos.close();
				} catch (Exception e) {
					// error occurred that prevented the file from being written
					showMessageDialog(workingUI,messages.getString("saveProjectWritingError"),
							messages.getString("saveProjectTitle"),
							Alert.AlertType.ERROR);
				}
				// add the file to the list of recently used files
				addRecentlyUsedFile(workingProject);
				saveProject();
			}
		}
	}

	/**
	 * @param pathRedirection
	 * 			The new directory opened with a file chooser
	 */
	public static void setPathRedirection(String pathRedirection) {
		Procedures.pathRedirection = pathRedirection;
	}

	/**
	 * @param pathRedirectionBrowse
	 * 			The new directory opened with a file chooser (button Browse)
	 */
	public static void setPathRedirectionBrowse(String pathRedirectionBrowse) {
		Procedures.pathRedirectionBrowse = pathRedirectionBrowse;
	}

	/**
	 * @details
	 * Sets the last directory used. The next time a file chooser is used, it
	 * will opened at this location.
	 * 
	 * @param lastFileUsed
	 *            the last file used. This can be a directory or a file, in
	 *            which case the parent directory of this file will be
	 *            considered the last directory used.
	 */
	public static void setLastDirectoryUsed(File lastFileUsed) {
		if (!lastFileUsed.exists())
			Procedures.lastDirectoryUsed = null;
		if (!lastFileUsed.isDirectory())
			Procedures.lastDirectoryUsed = lastFileUsed.getParentFile();
		else
			Procedures.lastDirectoryUsed = lastFileUsed;
	}

	/**
	 * @details
	 * Changes the model (FullSWOF configuration) used by the interface. All
	 * parameters are reset and the interface is updated with the new view. This
	 * method is called only upon creation of a new project.
	 *
	 * @param model
	 *            the new model to be set
	 */
	/*public static void setModel(Node model) {
		workingModel = model;
		// instantiate the controller for the new model
		workingController = model.setUpController();
		// create or update the user interface
		if (workingUI == null)
			workingUI = new Stage();
		else
			workingUI.updateContent();
	}*/

	/**
	 * @details
	 * Sets the output directory used when FullSWOF is launched.
	 * 
	 * @param outputDirectory
	 *            the output directory used when FullSWOF is launched
	 */
	public static void setOutputDirectory(File outputDirectory) {
		Procedures.outputDirectory = outputDirectory;
	}


	/**
	 * @details
	 * Sets the list of recently used project files.
	 * 
	 * @param recentFilesList
	 *            the list of recently used project files
	 */
	public static void setRecentFilesList(List<File> recentFilesList) {
		Procedures.recentFilesList = recentFilesList;
	}

	/**
	 * @details
	 * Sets the option for generating verbose parameters file.
	 * 
	 * @param verboseParametersFiles
	 *            true if the generated parameters.txt must contain descriptive
	 *            comments for each parameter, false if it limited to the tags
	 *            and their values
	 */
	public static void setVerboseParametersFiles(boolean verboseParametersFiles) {
		Procedures.verboseParametersFiles = verboseParametersFiles;
	}

	private static void addRecentlyUsedFile(File f) {
		// if the file is already in the list, delete it to move it to the top
		// of the list
		if (recentFilesList.contains(f))
			recentFilesList.remove(f);
		recentFilesList.add(0, f);
		if (recentFilesList.size() > RECENT_FILES_LIST_LENGTH)
			recentFilesList.remove(RECENT_FILES_LIST_LENGTH);
		// update the recent files menu
//		workingUI.updateRecentFilesMenu();		TODO
		// save the list to a properties file
		String userHome = System.getProperty("user.home");
		if (userHome == null) {
			return;
		}
		/*
		 * write the ./fullswof_ui/recent_files.properties for the next time the
		 * application is used
		 */
		File settingsDirectory = new File(userHome, ".fullswof_ui");
		if (!settingsDirectory.exists())
			settingsDirectory.mkdir();
		File recentFiles = new File(settingsDirectory,
				"recent_files.properties");
		Properties recentFilesProperties = new Properties();
		recentFilesProperties.setProperty("length",
				Integer.toString(recentFilesList.size()));
		for (int i = 0; i < recentFilesList.size(); i++) {
			recentFilesProperties.setProperty("file" + i, recentFilesList
					.get(i).getAbsolutePath());
		}
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(recentFiles);
			recentFilesProperties.store(fos,
					"Recently used files for FullSWOF_UI");
		} catch (IOException e) {
		}
	}

	/**
	 * Attempts to load the list of recently used file from a previous use of
	 * the application. This list is located in the recent_files.properties
	 * file, which can be found in the hidden directorie named fullswof_ui, in
	 * the user's home directory
	 * 
	 * @return a list of recently used files. It can be empty but will not be
	 *         longer than RECENT_FILES_LIST_LENGTH
	 */
	private static List<File> loadRecentlyUsedFiles() {
		List<File> list = new LinkedList<File>();
		String userHome = System.getProperty("user.home");
		// if the user directory is not set, return an empty list (the file is
		// supposed to be there)
		if (userHome == null)
			return list;
		File recentFiles = new File(new File(userHome, ".fullswof_ui"),
				"recent_files.properties");
		// if the ./fullswof_ui/recentfiles.properties file does not exist,
		// return an empty list
		if (!recentFiles.exists())
			return list;
		// read the content of the file to create the list
		Properties recentFilesProperties = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream(recentFiles);
			recentFilesProperties.load(fis);
		} catch (IOException e) {
		}
		int length = Math.min(RECENT_FILES_LIST_LENGTH, Integer
				.valueOf(recentFilesProperties.getProperty("length", "0")));
		for (int i = 0; i < length; i++) {
			String fileName = recentFilesProperties.getProperty("file" + i, "");
			File file = new File(fileName);
			list.add(file);
		}
		return list;
	}

	/**
	 * Called upon saving of a previously saved project to create all the
	 * necessary directories and files
	 */
	private static void prepareProject() {
		if (workingProject == null || workingDirectory == null
				|| !workingProject.exists() || !workingDirectory.exists()) {
			showMessageDialog(workingUI,messages.getString("nonExistantProject"), messages.getString("error"),
					Alert.AlertType.ERROR);
			workingProject = null;
		} else {
			File inputsDirectory = new File(workingDirectory, "Inputs");
			if (!inputsDirectory.exists()) {
				inputsDirectory.mkdir();
			}
		}
	}

	/**
	 * Sets the language used by the interface. Must be called only before the
	 * interface is displayed.
	 * 
	 * @return
	 */
	private static ResourceBundle setUpLocalization() {
		Locale l;
		if (currentLocale != null)
			l = currentLocale;
		else
			l = Locale.getDefault();
		return ResourceBundle.getBundle("l10n.ui.UIMessages", l);
	}

	/**
	 * Check if the file exist
	 * @param nameFile
	 * 		name file
	 * @return True : the file exist
	 */
	public static boolean isFile(String nameFile){
		if(workingProject!=null){
		String workingDirectoryPath = workingProject.getAbsolutePath();
		int dot = workingDirectoryPath.lastIndexOf('.');
		if (dot != -1)
			workingDirectoryPath = workingDirectoryPath.substring(0, dot)+"/Inputs/"+nameFile;
			File file =new File(workingDirectoryPath);
			return file.isFile();
		}
		return false;
	}

	/**
	 * the file
	 *
	 * @param nameFile
	 * 			name file
	 * @return the file
	 */
	public static File getFile(String nameFile){
		if(workingProject!=null){
			String workingDirectoryPath = workingProject.getAbsolutePath();
			int dot = workingDirectoryPath.lastIndexOf('.');
			if (dot != -1)
				workingDirectoryPath = workingDirectoryPath.substring(0, dot)+"/Inputs/"+nameFile;
			File file =new File(workingDirectoryPath);
			return file;
		}
		return null;
	}


}
