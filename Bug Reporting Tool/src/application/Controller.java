package application;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
	@FXML
	public Button loginButton;
	@FXML
	public Button completeQuit;
	@FXML
	public Button loginClose;
	@FXML
	public Button projectsButton;
	@FXML
	public Button backButton;
	@FXML
	public Button usersButton;
	@FXML
	public Button bugReportsButton;
	@FXML
	public Button searchButton;
	@FXML
	public Button callQuitButton;
	@FXML
	public Button quitCancelButton;
	@FXML
	public Button createBugReportButton;
	@FXML
	public Button logoutButton;
	@FXML
	public Button loadUsersButton;
	@FXML
	public Button loadProjectsButton;
	@FXML
	public Button loadOrojectIdsButton;
	@FXML
	public Button archiveProjectButton;
	@FXML
	public Button loadDataButton;
	@FXML
	public Button searchBySeverityButton;
	@FXML
	public Button searchByReportIdButton;
	@FXML
	public Button archiveReportButton;
	@FXML
	public ToggleGroup severityGroup,reportTypeGroup;
	@FXML
	public TextField reporterUsername,projectName,searchByUsernameInput,managerUsername,projectID,userNameInput,usernameInput,userPasswordInput,loginUsername;
	@FXML
	public PasswordField loginPassword;
	@FXML
	public TextArea bugSummaryInput,reproductionDescriptionInput;
	@FXML
	public ComboBox<String> canReproduceInput;
	@FXML
	public ComboBox<String> projectIDInput,projectIdBoxInput,searchBySeverityInput;
	@FXML
	public ComboBox<String> userTypeInput;
	@FXML
	public DatePicker bugFoundDate;
	@FXML
	public ListView<Projects> projectList;
	@FXML
	public TableView<User> userTable;
	@FXML
	TableColumn<User,String> userNameColumn,userTypeColumn,usernameColumn,userPasswordColumn;
	@FXML
	public TableView<BugReport> bugTable;
	@FXML
	TableColumn<BugReport,String> bugTypeColumn,bugSeverityColumn,bugSummaryColumn,bugReproduceColumn, bugStepsColumn,projectIdColumnSearch,reportColumnSearch;
	@FXML
	TableColumn<BugReport, LocalDate> dateFoundColumnSearch;

	private static int accessLevel;
	private static String username;

	static BugList reports=new BugList();
	static ArrayList<Projects> pList =new ArrayList<Projects>();
	static ArrayList<Projects> archivedProjectsList =new ArrayList<Projects>();
	static ArrayList<BugReport> archivedReportsList =new ArrayList<BugReport>();
	static ArrayList<User> uList =new ArrayList<User>();


	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	public void addBugReport()
	{
		if((RadioButton)severityGroup.getSelectedToggle()==null || projectIDInput.getSelectionModel().getSelectedItem().isEmpty() || (RadioButton)reportTypeGroup.getSelectedToggle()==null || bugSummaryInput.getText().isEmpty() || reporterUsername.getText().isEmpty())
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Warning");
			alert.setContentText("All appropriate fields must be filled");
			alert.showAndWait();
		}
		else if(bugSummaryInput.getText().length() > 50)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Too big");
			alert.setHeaderText("Summary can be only 50 characters long");
			alert.showAndWait();
		}
		else if(reporterUsername.getText().length() > 32)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Too big");
			alert.setHeaderText("reporter can be only 32 characters long");
			alert.showAndWait();
		}
		else
		{
			BugReport b = new BugReport();
			b.setSeverity(((RadioButton)severityGroup.getSelectedToggle()).getText());
			b.setProjectId(projectIDInput.getSelectionModel().getSelectedItem());
			b.setReportType(((RadioButton)reportTypeGroup.getSelectedToggle()).getText());
			b.setSummary(bugSummaryInput.getText());
			b.setCanReproduce(canReproduceInput.getSelectionModel().getSelectedItem());
			b.setReDescription(reproductionDescriptionInput.getText());
			b.setFoundDate(bugFoundDate.getValue());
			b.setUsername(reporterUsername.getText());


			reports.add(b);
			saveReportsToFile();
		}

	}
	public void archiveReport()
	{
		archivedReportsList.add(bugTable.getSelectionModel().getSelectedItem());
		reports.archive(bugTable.getSelectionModel().getSelectedItem());
		saveReportsToFile();
		saveArchivedReportsToFile();
		ObservableList<BugReport> reportSelected, allReports;
		allReports = bugTable.getItems();
		reportSelected = bugTable.getSelectionModel().getSelectedItems();
		reportSelected.forEach(allReports::remove);
	}

	public void addProject()
	{
		if(projectName.getText().isEmpty() || managerUsername.getText().isEmpty() || projectID.getText().isEmpty())
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Warning");
			alert.setContentText("All fields must be filled");
			alert.showAndWait();
		}
		else if(projectName.getText().length() > 15)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Too big");
			alert.setHeaderText("Project name can be only 15 characters long");
			alert.showAndWait();
		}
		else if(projectID.getText().length() > 5)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Too big");
			alert.setHeaderText("Project ID can be only 5 characters long");
			alert.showAndWait();
		}
		else if(managerUsername.getText().length() > 32)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Too big");
			alert.setHeaderText("Project Manager can be only 32 characters long");
			alert.showAndWait();
		}
		else if(accessLevel < 3)
		{

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Nice Try");
			alert.setHeaderText("You aint got permission fo dat");
			alert.showAndWait();
			projectName.clear();
			managerUsername.clear();
			projectID.clear();
		}
		else
		{
			if(pList.isEmpty())
			{
				Projects p = new Projects();
				p.setProjectName(projectName.getText());
				p.setManagerUsername(managerUsername.getText());
				p.setProjectId(Integer.parseInt(projectID.getText()));
				p.setDate(LocalDate.now());
				projectName.clear();
				managerUsername.clear();
				projectID.clear();
				projectList.getItems().add(p);
				pList.add(p);
				saveProjectsToFile();
			}
			else
			{
				boolean b = false;
				for(int i = 0; i < pList.size(); i++)
				{
					if(pList.get(i).getProjectId()==Integer.parseInt(projectID.getText()))
					{
						b = true;
					}
				}
				if(b)
				{
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Nice Try");
					alert.setHeaderText("You aint got permission fo dat");
					alert.showAndWait();
				}
				else
				{
					try{
						Projects p = new Projects();
						p.setProjectName(projectName.getText());
						p.setManagerUsername(managerUsername.getText());
						p.setProjectId(Integer.parseInt(projectID.getText()));
						p.setDate(LocalDate.now());
						projectName.clear();
						managerUsername.clear();
						projectID.clear();
						projectList.getItems().add(p);
						pList.add(p);
						saveProjectsToFile();
						}catch(NumberFormatException n)
						{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Incompatible Types");
							alert.setHeaderText("Porject ID must be a number");
							alert.showAndWait();
						}
				}
			}			
		}	
	}

	public void loadProjectInputs()
	{
		try{
			projectName.setText(projectList.getSelectionModel().getSelectedItem().getProjectName());
			managerUsername.setText(projectList.getSelectionModel().getSelectedItem().getManagerUsername());
		}catch(NullPointerException n)
		{

		}
	}

	public void updateProject()
	{
		if(accessLevel < 3)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Nice Try");
			alert.setHeaderText("You aint got permission fo dat");
			alert.showAndWait();
		}
		else if(managerUsername.getText().isEmpty() || projectName.getText().isEmpty())
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Not Possible");
			alert.setHeaderText("Project name and project manager must not be blank");
			alert.showAndWait();
		}
		else
		{
			try{
				pList.get(projectList.getSelectionModel().getSelectedIndex()).setManagerUsername(managerUsername.getText());
				pList.get(projectList.getSelectionModel().getSelectedIndex()).setProjectName(projectName.getText());
				managerUsername.clear();
				projectName.clear();
				saveProjectsToFile();
				loadProjects();
			}catch(ArrayIndexOutOfBoundsException a)
			{

			}
		}
	}

	public void deleteProject()
	{
		try
		{loadReportsFromFile();
		if(accessLevel < 3)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Nice Try");
			alert.setHeaderText("You aint got permission fo dat");
			alert.showAndWait();

		}
		else if(!(reports.searchById("" + projectList.getSelectionModel().getSelectedItem().getProjectId()).isEmpty()))
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Nope");
			alert.setHeaderText("This project has active bugs, and cannot be archived");
			alert.showAndWait();
		}
		else
		{
			loadProjectsFromFile();
			archivedProjectsList.add(searchProject(projectList.getSelectionModel().getSelectedItem().getProjectId()));
			pList.remove(searchProject(projectList.getSelectionModel().getSelectedItem().getProjectId()));
			saveProjectsToFile();
			saveArchivedProjectsToFile();
			projectList.getItems().remove(projectList.getSelectionModel().getSelectedItem());
		}
		}catch(NullPointerException n)
		{
			
		}
	}

	public void addUser()
	{
		if(userNameInput.getText().isEmpty() || usernameInput.getText().isEmpty() || userPasswordInput.getText().isEmpty() || userTypeInput.getSelectionModel().getSelectedItem().isEmpty())
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Warning");
			alert.setContentText("All fields must be full");
			alert.showAndWait();
		}
		else if(usernameInput.getText().length() > 32)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Too big");
			alert.setHeaderText("Username can be only 32 characters long");
			alert.showAndWait();
		}
		else if(userNameInput.getText().length() > 32)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Too big");
			alert.setHeaderText("User name can be only 32 characters long");
			alert.showAndWait();
		}
		else if(userPasswordInput.getText().length() > 16)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Too big");
			alert.setHeaderText("User name can be only 16 characters long");
			alert.showAndWait();
		}
		else if(userTypeInput.getSelectionModel().getSelectedItem().equals("Project Manager") && accessLevel != 4)
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Cannot Add");
			alert.setHeaderText("Your access level is not high enough");
			alert.showAndWait();
		}
		else
		{
			User u = new User(userNameInput.getText(),usernameInput.getText(),userPasswordInput.getText(),userTypeInput.getSelectionModel().getSelectedItem().toString());
			userTable.getItems().add(u);
			uList.add(u);
			saveUsersToFile();
		}
	}


	public void deleteUser()
	{
		uList.remove(userTable.getSelectionModel().getSelectedItem());
		saveUsersToFile();
		ObservableList<User> userSelected, allUsers;
		allUsers = userTable.getItems();
		userSelected = userTable.getSelectionModel().getSelectedItems();
		userSelected.forEach(allUsers::remove);
	}
	public void loadSearchData()
	{
		bugTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		bugTypeColumn.setOnEditCommit(
				new EventHandler<CellEditEvent<BugReport, String>>() {
					@Override
					public void handle(CellEditEvent<BugReport, String> t) {
						if(!(username.equals(bugTable.getSelectionModel().getSelectedItem().getUsername())) && accessLevel < 4)
						{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Cannot edit");
							alert.setHeaderText("You did not create this bug");
							alert.showAndWait();
						}
						else if(!(t.getNewValue().equals("Coding Error") || t.getNewValue().equals("Design Issue")))
						{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Invalid Input");
							alert.setHeaderText("Please enter a valid bug type");
							alert.showAndWait();
						}
						else
						{
							((BugReport) t.getTableView().getItems().get(t.getTablePosition().getRow())).setReportType((t.getNewValue()));
							reports.getIndex(bugTable.getSelectionModel().getSelectedIndex()).setReportType(t.getNewValue());
							saveReportsToFile();
						}
						bugTable.refresh();
					}
				});
		bugSeverityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		bugSeverityColumn.setOnEditCommit(
				new EventHandler<CellEditEvent<BugReport, String>>() {
					@Override
					public void handle(CellEditEvent<BugReport, String> t) {
						if(!(username.equals(bugTable.getSelectionModel().getSelectedItem().getUsername())) && accessLevel < 4)
						{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Cannot edit");
							alert.setHeaderText("You did not create this bug");
							alert.showAndWait();
						}
						else if(!(t.getNewValue().equals("Minor") || t.getNewValue().equals("Serious") || t.getNewValue().equals("Fatal")))
						{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Invalid Input");
							alert.setHeaderText("Please enter a valid bug severity");
							alert.showAndWait();
						}
						else
						{
							((BugReport) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSeverity((t.getNewValue()));
							reports.getIndex(bugTable.getSelectionModel().getSelectedIndex()).setSeverity(t.getNewValue());
							saveReportsToFile();
						}
						bugTable.refresh();
					}
				});
		bugSummaryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		bugSummaryColumn.setOnEditCommit(
				new EventHandler<CellEditEvent<BugReport, String>>() {
					@Override
					public void handle(CellEditEvent<BugReport, String> t) {
						if(!(username.equals(bugTable.getSelectionModel().getSelectedItem().getUsername())) && accessLevel < 4)
						{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Cannot edit");
							alert.setHeaderText("You did not create this bug");
							alert.showAndWait();
						}
						else if(t.getNewValue().length() > 50)
						{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Too big");
							alert.setHeaderText("Summary can be only 50 characters long");
							alert.showAndWait();
						}
						else if(t.getNewValue().isEmpty())
						{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Not possible");
							alert.setHeaderText("Summary cannot be blank");
							alert.showAndWait();
						}
						else
						{
							((BugReport) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSummary((t.getNewValue()));
							reports.getIndex(bugTable.getSelectionModel().getSelectedIndex()).setSummary(t.getNewValue());
							saveReportsToFile();
						}
						bugTable.refresh();
					}
				});
		bugStepsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		bugStepsColumn.setOnEditCommit(
				new EventHandler<CellEditEvent<BugReport, String>>() {
					@Override
					public void handle(CellEditEvent<BugReport, String> t) {
						if(!(username.equals(bugTable.getSelectionModel().getSelectedItem().getUsername())) && accessLevel < 4)
						{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Cannot edit");
							alert.setHeaderText("You did not create this bug");
							alert.showAndWait();
						}
						else if(t.getNewValue().isEmpty())
						{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Not possible");
							alert.setHeaderText("Reporduce steps cannot be blank");
							alert.showAndWait();
						}
						else
						{
							((BugReport) t.getTableView().getItems().get(t.getTablePosition().getRow())).setReDescription((t.getNewValue()));
							reports.getIndex(bugTable.getSelectionModel().getSelectedIndex()).setReDescription(t.getNewValue());
							saveReportsToFile();
						}
						bugTable.refresh();
					}
				});
		bugReproduceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		bugReproduceColumn.setOnEditCommit(
				new EventHandler<CellEditEvent<BugReport, String>>() {
					@Override
					public void handle(CellEditEvent<BugReport, String> t) {
						if(!(username.equals(bugTable.getSelectionModel().getSelectedItem().getUsername())) && accessLevel < 4)
						{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Cannot edit");
							alert.setHeaderText("You did not create this bug");
							alert.showAndWait();
						}
						else if(!(t.getNewValue().equals("Yes") || t.getNewValue().equals("No")))
						{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Invalid Input");
							alert.setHeaderText("Please enter Yes or No");
							alert.showAndWait();
						}
						else
						{
							((BugReport) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCanReproduce((t.getNewValue()));
							reports.getIndex(bugTable.getSelectionModel().getSelectedIndex()).setCanReproduce(t.getNewValue());
							saveReportsToFile();
						}
						bugTable.refresh();
					}
				});
		loadProjectsFromFile();
		projectIdBoxInput.getItems().clear();
		for(int i=0; i<pList.size();i++)
		{
			projectIdBoxInput.getItems().add(""+pList.get(i).getProjectId());
		}
		projectIdBoxInput.getItems().add("Empty");
	}
	public void searchByReportID()
	{
		loadReportsFromFile();
		bugTable.getItems().clear();	

		if(!(projectIdBoxInput.getSelectionModel().getSelectedItem().toString().equals("")) && searchByUsernameInput.getText().isEmpty() && searchBySeverityInput.getSelectionModel().getSelectedItem().equals("Empty"))
		{
			bugTable.getItems().addAll(reports.searchById(projectIdBoxInput.getSelectionModel().getSelectedItem().toString()));
		}
		else if(!(searchByUsernameInput.getText().toString().equals("")) && searchBySeverityInput.getSelectionModel().getSelectedItem().equals("Empty") && projectIdBoxInput.getSelectionModel().getSelectedItem().equals("Empty"))
		{
			bugTable.getItems().addAll(reports.searchByUsername(searchByUsernameInput.getText().toString()));
		}
		else if(!(searchBySeverityInput.getSelectionModel().getSelectedItem().toString().equals("")) && searchByUsernameInput.getText().isEmpty() && projectIdBoxInput.getSelectionModel().getSelectedItem().equals("Empty"))
		{
			bugTable.getItems().addAll(reports.searchBySeverity(searchBySeverityInput.getSelectionModel().getSelectedItem()));
		}
		else if(!(projectIdBoxInput.getSelectionModel().getSelectedItem().toString().equals("")) && !(searchByUsernameInput.getText().toString().equals("")) && searchBySeverityInput.getSelectionModel().getSelectedItem().toString().equals("Empty"))
		{
			bugTable.getItems().addAll(reports.searchByPC(projectIdBoxInput.getSelectionModel().getSelectedItem().toString(), searchByUsernameInput.getText()));
		}
		else if(!(projectIdBoxInput.getSelectionModel().getSelectedItem().toString().equals(""))  && !(searchBySeverityInput.getSelectionModel().getSelectedItem().toString().equals("")) && searchByUsernameInput.getText().toString().equals(""))
		{
			bugTable.getItems().addAll(reports.searchByPS(projectIdBoxInput.getSelectionModel().getSelectedItem().toString(),searchBySeverityInput.getSelectionModel().getSelectedItem().toString()));
		}
		else if(!(searchByUsernameInput.getText().toString().equals("")) && !(searchBySeverityInput.getSelectionModel().getSelectedItem().toString().equals("")) && projectIdBoxInput.getSelectionModel().getSelectedItem().toString().equals("Empty"))
		{
			bugTable.getItems().addAll(reports.searchByCS(searchByUsernameInput.getText(), searchBySeverityInput.getSelectionModel().getSelectedItem().toString()));
		}
		else if(!(projectIdBoxInput.getSelectionModel().getSelectedItem().toString().equals("")) && !(searchByUsernameInput.getText().toString().equals("")) && !(searchBySeverityInput.getSelectionModel().getSelectedItem().toString().equals("")))
		{
			bugTable.getItems().addAll(reports.searchByPCS(projectIdBoxInput.getSelectionModel().getSelectedItem().toString(), searchByUsernameInput.getText(), searchBySeverityInput.getSelectionModel().getSelectedItem().toString()));
		}
		else
		{
			bugTable.getItems().clear();
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Warning");
			alert.setContentText("Search criteria Incorrect");
			alert.showAndWait();
		}
	}



	public void login() throws IOException
	{
		loadUsersFromFile();
		if(loginUsername.getText().equals("") || loginPassword.getText().equals(""))
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Cannot Login");
			alert.setHeaderText("Username or/and password fields are blank");
			alert.showAndWait();
		}
		else
		{
			boolean b = false;
			for(int i=0;i<uList.size();i++)
			{
				if(loginUsername.getText().equals(uList.get(i).getUsername()) && loginPassword.getText().equals(uList.get(i).getPassword()))
				{
					b = true;
					accessLevel = uList.get(i).getAccessLevel();
					username = uList.get(i).getUsername();
				}
			}
			if(b)
			{
				Stage stage; 
				Parent root;        
				stage= (Stage) loginButton.getScene().getWindow();
				root = FXMLLoader.load(getClass().getClassLoader().getResource("Main.fxml"));
				Scene scene = new Scene(root, 600, 400);
				stage.setScene(scene);
				stage.show();
			}
			else
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Cannot Login");
				alert.setHeaderText("Username or/and password fields is incorrect");
				alert.showAndWait();
			}
		}

	}

	public void logout() throws IOException
	{
		Stage stage; 
		Parent root;         
		stage= (Stage) logoutButton.getScene().getWindow();
		root = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));
		Scene scene = new Scene(root, 372, 227);
		stage.setScene(scene);
		stage.show();
	}

	public void projects() throws IOException
	{

		Stage stage; 
		Parent root;         
		stage= (Stage) projectsButton.getScene().getWindow();
		root = FXMLLoader.load(getClass().getClassLoader().getResource("Projects.fxml"));
		Scene scene = new Scene(root, 594, 405);
		stage.setScene(scene);
		stage.show();

	}
	public void loadProjects()
	{
		projectList.getItems().clear();
		loadProjectsFromFile();
		for(int i=0; i<pList.size();i++)
		{
			projectList.getItems().add(pList.get(i));
		}
	}
	public void loadProjectIds()
	{
		loadProjectsFromFile();
		projectIDInput.getItems().clear();
		for(int i=0; i<pList.size();i++)
		{
			projectIDInput.getItems().add(""+pList.get(i).getProjectId());
		}
	}

	public void users() throws IOException
	{	
		if(accessLevel == 3 || accessLevel == 4)
		{
			Stage stage; 
			Parent root;         
			stage= (Stage) usersButton.getScene().getWindow();
			root = FXMLLoader.load(getClass().getClassLoader().getResource("Users.fxml"));
			Scene scene = new Scene(root, 511, 522);
			stage.setScene(scene);
			stage.show();
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Unauthorised Access");
			alert.setHeaderText("Your access level is not high enough for this section");
			alert.showAndWait();
		}

	}
	public void loadUsers()
	{
		userTable.getItems().clear();
		loadUsersFromFile();
		for(int i=0; i<uList.size();i++)
		{
			userTable.getItems().add(uList.get(i));
		}
		userNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		userNameColumn.setOnEditCommit(
				new EventHandler<CellEditEvent<User, String>>() {
					@Override
					public void handle(CellEditEvent<User, String> t) {
						if(accessLevel < 4 && (userTable.getSelectionModel().getSelectedItem().getUserType().equals("Project Manager") || userTable.getSelectionModel().getSelectedItem().getUserType().equals("Super User") ))
						{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Cannot edit");
							alert.setHeaderText("You do not have the access level to edit this");
							alert.showAndWait();
							userTable.refresh();
						}
						else if(t.getNewValue().length() > 32)
						{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Too big");
							alert.setHeaderText("User name can be only 32 characters long");
							alert.showAndWait();
							userTable.refresh();
						}
						else if(t.getNewValue().isEmpty())
						{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Not possible");
							alert.setHeaderText("User name must not be blank");
							alert.showAndWait();
							userTable.refresh();
						}
						else
						{
							((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
							uList.get(userTable.getSelectionModel().getSelectedIndex()).setName(t.getNewValue());
							saveUsersToFile();
						}
					}
				});
		userTypeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		userTypeColumn.setOnEditCommit(
				new EventHandler<CellEditEvent<User, String>>() {
					@Override
					public void handle(CellEditEvent<User, String> t) {

						if(accessLevel < 4 && (userTable.getSelectionModel().getSelectedItem().getUserType().equals("Project Manager") || userTable.getSelectionModel().getSelectedItem().getUserType().equals("Super User") ))
						{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Cannot edit");
							alert.setHeaderText("You do not have the access level to edit this");
							alert.showAndWait();
							userTable.refresh();
						}
						else if((accessLevel == 3 && (!(t.getNewValue().equals("Tester") || t.getNewValue().equals("Developer")))) || (accessLevel == 4 && (!(t.getNewValue().equals("Tester") || t.getNewValue().equals("Developer") || t.getNewValue().equals("Project Manager")))))
						{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Invalid Input");
							alert.setHeaderText("Please enter a valid user type.");
							alert.showAndWait();
							userTable.refresh();
						}
						else
						{
							((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setUserType(t.getNewValue());
							uList.get(userTable.getSelectionModel().getSelectedIndex()).setUserType(t.getNewValue());
							saveUsersToFile();
						}
					}
				});
		userPasswordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		userPasswordColumn.setOnEditCommit(
				new EventHandler<CellEditEvent<User, String>>() {
					@Override
					public void handle(CellEditEvent<User, String> t) {
						if(accessLevel < 4 && (userTable.getSelectionModel().getSelectedItem().getUserType().equals("Project Manager") || userTable.getSelectionModel().getSelectedItem().getUserType().equals("Super User") ))
						{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Cannot edit");
							alert.setHeaderText("You do not have the access level to edit this");
							alert.showAndWait();
							userTable.refresh();
						}
						else if(t.getNewValue().length() > 16)
						{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Too big");
							alert.setHeaderText("Password can be only 16 characters long");
							alert.showAndWait();
							userTable.refresh();
						}
						else if(t.getNewValue().isEmpty())
						{
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("Not possible");
							alert.setHeaderText("User password must not be blank");
							alert.showAndWait();
							userTable.refresh();
						}
						else
						{
							((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPassword((t.getNewValue()));
							uList.get(userTable.getSelectionModel().getSelectedIndex()).setPassword(t.getNewValue());
							saveUsersToFile();
						}
					}
				});
	}

	public void bugReports() throws IOException
	{
		Stage stage; 
		Parent root;         
		stage= (Stage) bugReportsButton.getScene().getWindow();
		root = FXMLLoader.load(getClass().getClassLoader().getResource("BugReports.fxml"));	
		Scene scene = new Scene(root, 700, 500);
		stage.setScene(scene);
		stage.show();

	}

	public void search() throws IOException
	{
		Stage stage; 
		Parent root;         
		stage= (Stage) searchButton.getScene().getWindow();
		root = FXMLLoader.load(getClass().getClassLoader().getResource("Search.fxml"));
		Scene scene = new Scene(root, 920, 500);
		stage.setScene(scene);
		stage.show();
	}

	public void callQuit() throws IOException
	{
		Stage stage = new Stage(); 
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Quiter.fxml"));
		Scene scene = new Scene(root, 300, 150);
		stage.setScene(scene);
		stage.show();
	}

	public void regularBack() throws IOException
	{
		Stage stage; 
		Parent root;         
		stage= (Stage) backButton.getScene().getWindow();
		root = FXMLLoader.load(getClass().getClassLoader().getResource("Main.fxml"));
		Scene scene = new Scene(root, 600, 400);
		stage.setScene(scene);
		stage.show();
	}

	public void quitCancel()
	{
		quitCancelButton.getScene().getWindow().hide();
	}

	public void close()
	{
		System.exit(0);
	}
	public Projects searchProject(int ID)
	{
		int place=0;
		for(int i=0; i<pList.size(); i++)//for loop to go through every object
		{
			if(pList.get(i).getProjectId()==ID) 
			{
				place=i;//assign the object found to place variable
			}
		}
		return pList.get(place);
	}
	public User searchUser(String username)
	{
		int place=0;
		for(int i=0; i<uList.size(); i++)//for loop to go through every object
		{
			if(uList.get(i).getUsername().equals(username)) 
			{
				place=i;//assign the object found to place variable
			}
		}
		return uList.get(place);
	}

	public static void saveReportsToFile() 
	{
		try 
		{
			FileOutputStream fos = new FileOutputStream("Bug Reports.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(reports);

			oos.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public static void loadReportsFromFile() 
	{
		try 
		{
			File file = new File("Bug Reports.ser");
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			reports=  (BugList) ois.readObject();
			ois.close();
			fis.close();

		} catch (EOFException e) 
		{
			// just means it's a new file
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		BugReport.bugCount = (reports.getIndexOfReport(reports.list.size()-1).getBugID()+1);
	}
	public static void saveArchivedReportsToFile() 
	{
		try 
		{
			FileOutputStream fos = new FileOutputStream("Archived Reports.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(archivedReportsList);

			oos.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public static void loadArchivedReportsFromFile() 
	{
		try 
		{
			File file = new File("Archived Reports.ser");
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			archivedReportsList=  (ArrayList<BugReport>) ois.readObject();
			ois.close();
			fis.close();

		} catch (EOFException e) 
		{
			// just means it's a new file
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public static void saveArchivedProjectsToFile() 
	{
		try 
		{
			FileOutputStream fos = new FileOutputStream("Archived Projects.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(archivedProjectsList);
			oos.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public static void loadArchivedProjectsFromFile() 
	{
		try 
		{
			File file = new File("Archived Projects.ser");
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			archivedProjectsList=  (ArrayList<Projects>) ois.readObject();
			ois.close();
			fis.close();

		} catch (EOFException e) 
		{
			// just means it's a new file
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public static void saveProjectsToFile() 
	{
		try 
		{
			FileOutputStream fos = new FileOutputStream("Projects.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(pList);

			oos.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public static void loadProjectsFromFile() 
	{
		try 
		{
			File file = new File("Projects.ser");
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			pList=  (ArrayList<Projects>) ois.readObject();
			ois.close();
			fis.close();

		} catch (EOFException e) 
		{
			// just means it's a new file
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public static void saveUsersToFile() 
	{
		try 
		{
			FileOutputStream fos = new FileOutputStream("Users.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(uList);

			oos.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public static void loadUsersFromFile() 
	{
		try 
		{
			File file = new File("Users.ser");
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			uList=  (ArrayList<User>) ois.readObject();
			ois.close();
			fis.close();

		} catch (EOFException e) 
		{
			// just means it's a new file
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}


