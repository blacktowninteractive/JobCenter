/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jobcenter;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author angelacaicedo
 */
public class JobCenterMainController implements Initializable, ScreenController {

    //database connection info
    public static String url = "jdbc:mysql://localhost/jobcenter";
    public static String userdb = "vangfc";//Username of database  
    public static String passdb = "password";//Password of database
    Statement st = null;
    ResultSet rs = null;
    public static Connection conn;
    public ScreenPane myScreenPane;
    public static ListView adminList, taskList, proList, employeeSelect, employeeSelected,
            vehicleEquipSelect, vehicleEquipSelected, custListing, taskTypeList;
    public static Label changeMe;
    public static TitledPane ManagerStatus, PeopleBox, VehiclesBox, ReportsBox, createJob;
    public static Pane CreateJobBox, settingsPane, displayJobs, employeePane;
    public static ToolBar AdminToolBar, FunctionsToolBar, ReportsToolBar, employeeToolbar;
    public static TableView usersTable;
    public static TableView<employee> employeeTable = new TableView<employee>();
    public static TableColumn emp_fname, emp_lname, emp_phone, emp_email;
    public static Button chgPasswd, addEmp, addVehBut, deleteVehBut, clearJob,
            saveJob, confirmJob, cancelJob, addCustBut, addTask, delTask;
    //All stuff on job creation form
    public static Text setCustPhone, setCustName, setCustCity, setCustState, setCustPOC, setCustCompPhone,
            setCustFax, setCustAddr, setCustZip;
    public static TextField jobTitle, jobName, custJobNum, custJobName, startDate, startTime,
            streetAddr, city, state, zip, diamStr, feetStr;
    public static String jobTitleStr, jobNameStr, custJobNumStr, custJobNameStr, startDateStr, startTimeStr,
            streetAddrStr, cityStr, stateStr, zipStr, custAdd, phone, fax, pocName, pocPhone;
    public static ComboBox screenList, taskComboBox;
    public static RadioButton prodChk, hourChk;
    public static TextArea sInstr, tInstr, dInstr, wInstr;
    public static ObservableList<String> admin = FXCollections.observableArrayList(
            "Manager status", "People", "Vehicles", "Create/Delete a JobCenter User", "Settings");
    //ObservableList<String> functions = FXCollections.observableArrayList(
    //      "Show job board", "Summary report");
    public static ObservableList<String> tasks = FXCollections.observableArrayList(
            "Create new job", "Display jobs");
    public static ObservableList<String> proposals = FXCollections.observableArrayList(
            "New proposal");
    public static List<String> list = new ArrayList<String>();
    public static ObservableList<String> options = FXCollections.observableList(list),
            taskListBox = FXCollections.observableList(list),
            taskTypeListStr = FXCollections.observableList(list);
    List<String> empListSel = new ArrayList<String>(),
            vehList = new ArrayList<String>(),
            custList = new ArrayList<String>(),
            jobTypePicked = new ArrayList<String>();
    ObservableList<String> vehList11 = FXCollections.observableArrayList(vehList),
            empSelect = FXCollections.observableArrayList(empListSel),
            custListingObs = FXCollections.observableArrayList(custList);
    public static String billing, cid, jobtypecompiled, empCompiled, equipCompiled, sI, dI, tI, wI;
    
    @FXML
    TreeView<String> currentJobsDisplay;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO     
        emp_fname.setCellValueFactory(new PropertyValueFactory<employee, String>("firstName"));
        emp_lname.setCellValueFactory(new PropertyValueFactory<employee, String>("lastName"));
        emp_email.setCellValueFactory(new PropertyValueFactory<employee, String>("email"));
        emp_phone.setCellValueFactory(new PropertyValueFactory<employee, String>("phone"));

        employeeTable.setItems(populateDB());

        //if you wanna add an icon in front
        //   Node rootIcon = new ImageView(new Image(getClass().getResourceAsStream("exchange.png")));
          
        
        TreeItem<String> root = new TreeItem<String>("Active Jobs");        
        TreeItem<String> empLeaf = new TreeItem<String>("test");           
        
        for (TreeItem<String> depNode : root.getChildren())
        {
            depNode.getChildren().add(empLeaf);
            break;
        }
        TreeItem<String> depNode = new TreeItem<String>("ALLCON");
            
        root.setExpanded(true);
        root.getChildren().add(depNode);
          
        this.currentJobsDisplay.setRoot(root);
        //.properties.setRoot(root);

    }
    

    public ObservableList<employee> populateDB() {
        /*
         ObservableList<employee> tester2 = FXCollections.observableArrayList(
         new employee("Jacob", "Smith", "jacob.smith@example.com", "12234"),
         new employee("Isabella", "Johnson", "isabella.johnson@example.com", "12234"),
         new employee("Ethan", "Williams", "ethan.williams@example.com", "12234"),
         new employee("Emma", "Jones", "emma.jones@example.com", "12234"),
         new employee("Michael", "Brown", "michael.brown@example.com", "12234"));
         */
        ObservableList<employee> tester2 = FXCollections.observableArrayList();

        //make the connection
        try {
            conn = DriverManager.getConnection(url, userdb, passdb);
            st = conn.createStatement();
            rs = st.executeQuery("select * from employees;");

            while (rs.next()) {
                tester2.add(new employee(rs.getString(1), rs.getString(2), rs.getString(5), rs.getString(4)));
                //System.out.println(rs.getString(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(JobCenterController.class.getName()).log(Level.SEVERE, null, ex);
        }




        return tester2;
    }

    private String getMyIp() throws UnknownHostException {
        //unique identifier for different computers
        InetAddress IP = InetAddress.getLocalHost();
        String ipAddr = IP.getHostAddress();
        return ipAddr;
    }
    //this is the signout button listener, signs out and clears database session entry

    @FXML
    private void signoutTransition(ActionEvent event) throws UnknownHostException, SQLException {
        String myIp = getMyIp();
        System.out.println("my ip: " + myIp);

        //make the connection
        try {
            conn = DriverManager.getConnection(url, userdb, passdb);
        } catch (SQLException ex) {
            Logger.getLogger(JobCenterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (conn != null && !conn.isClosed()) {
            System.out.println("Connection Established...");
        }


        //delete all entries associated with IP before exiting to the login screen
        Statement updateDb = null;
        updateDb = conn.createStatement();

        String insertSess = "delete from session where ipAddr = '" + myIp + "';";
        System.out.println(insertSess);

        //set our session id and ip address in order to identify user.
        int executeUpdate = updateDb.executeUpdate(insertSess);

        myScreenPane.setScreen("login");

    }

    public void clearPane() {

        VehiclesBox.setVisible(false);
        ManagerStatus.setVisible(false);
        PeopleBox.setVisible(false);
        AdminToolBar.setVisible(false);
        employeeToolbar.setVisible(false);
        employeePane.setVisible(false);

        //FunctionsToolBar.setVisible(false);

        ReportsBox.setVisible(false);
        ReportsToolBar.setVisible(false);

        CreateJobBox.setVisible(false);
        settingsPane.setVisible(false);

        displayJobs.setVisible(false);

    }
    private final Node rootIcon = new ImageView(
            new Image(getClass().getResourceAsStream("exchange.png")));

    @Override
    public void setScreenPane(ScreenPane screenPage) {
        adminList.setItems(admin);
        // funcList.setItems(functions);
        taskList.setItems(tasks);
        proList.setItems(proposals);


        List<String> empList = new ArrayList<String>();
        List<String> vehList = new ArrayList<String>();

        //make the connection
        try {
            conn = DriverManager.getConnection(url, userdb, passdb);
            st = conn.createStatement();

            rs = st.executeQuery("select fname from employees;");
            while (rs.next()) {
                System.out.println(rs.getString(1));
                empList.add(rs.getString(1));
            }
            rs = st.executeQuery("select VehicleName from vehicles;");
            while (rs.next()) {
                System.out.println(rs.getString(1));
                vehList.add(rs.getString(1));
            }
            rs = st.executeQuery("select CompanyName from customer;");
            while (rs.next()) {
                System.out.println(rs.getString(1));
                custList.add(rs.getString(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(JobCenterController.class.getName()).log(Level.SEVERE, null, ex);
        }



        ObservableList<String> emp = FXCollections.observableArrayList(empList);
        ObservableList<String> vehEquip = FXCollections.observableArrayList(vehList);
        ObservableList<String> custListingObs = FXCollections.observableArrayList(custList);

        employeeSelect.setItems(emp);
        vehicleEquipSelect.setItems(vehEquip);
        custListing.setItems(custListingObs);

        myScreenPane = screenPage;
        adminList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> ov,
                    String old_val, String new_val) {
                //clear pane first
                clearPane();

                if (new_val == "Manager status") {
                    ManagerStatus.setVisible(true);
                    AdminToolBar.setVisible(true);
                }
                if (new_val == "People") {
                    employeePane.setVisible(true);
                    //PeopleBox.setVisible(true);
                    //employeeToolbar.setVisible(true);
                }
                if (new_val == "Vehicles") {
                    VehiclesBox.setVisible(true);
                    AdminToolBar.setVisible(true);
                }
                if (new_val == "Settings") {
                    ObservableList<Screen> getAll = Screen.getScreens();

                    for (int i = 0; i < getAll.size(); i++) {
                        String format = (Screen.getScreens().get(i)).toString();
                        int locSemicolon = format.indexOf(":"),
                                locScreen = format.indexOf("Screen");
                        format = format.substring(locScreen, locSemicolon);

                        System.out.println(format);
                        options.add(format);
                    }
                    screenList.setItems(options);
                    settingsPane.setVisible(true);
                }
            }
        });

        myScreenPane = screenPage;

        /*funcList.getSelectionModel()
         .selectedItemProperty().addListener(
         new ChangeListener<String>() {
         public void changed(ObservableValue<? extends String> ov,
         String old_val, String new_val) {

         //clear pane first
         clearPane();

         if (new_val == "Show job board") {
         FunctionsToolBar.setVisible(true);
         }

         if (new_val == "Summary report") {
         ReportsBox.setVisible(true);
         ReportsToolBar.setVisible(true);
         }

         }
         });*/
        myScreenPane = screenPage;
        taskList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> ov,
                    String old_val, String new_val) {

                clearPane();
                if (new_val == "Create new job") {
                    taskListBox = FXCollections.observableList(new ArrayList<String>());

                    List<String> getJobTypes = new ArrayList<String>();
                    //make the connection
                    try {
                        conn = DriverManager.getConnection(url, userdb, passdb);
                        st = conn.createStatement();
                        rs = st.executeQuery("select jobName from jobType;");
                        while (rs.next()) {
                            System.out.println(rs.getString(1));
                            getJobTypes.add(rs.getString(1));

                        }

                        rs = st.executeQuery("select jobName from jobtype;");
                        while (rs.next()) {
                            System.out.println(rs.getString(1));
                            taskListBox.add(rs.getString(1));
                        }



                    } catch (SQLException ex) {
                        Logger.getLogger(JobCenterController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    taskComboBox.setItems(taskListBox);
                    CreateJobBox.setVisible(true);


                }
                if (new_val == "Display jobs") {
                    clearPane();

                    displayJobs.setVisible(true);

                }

            }
        });
        myScreenPane = screenPage;
        proList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> ov,
                    String old_val, String new_val) {
                clearPane();

            }
        });

    }
    //*********************************************************************************
    //*********************************************************************************
    //BUTTON ACTION LISTNERS HERE!!!! this coordinates to the JavaFX Scene Builder
    static Stage stageJob;

    @FXML
    private void delTaskList(ActionEvent event) {
        String del = taskTypeList.getSelectionModel().selectedItemProperty().getValue().toString();
        System.out.println("delete: " + del);
        for (int i = 0; i < taskTypeListStr.size(); i++) {
            if (taskTypeListStr.get(i).toString().equals(del)) {
                taskTypeListStr.remove(i);
            }
        }
        taskTypeList.setItems(taskTypeListStr);
    }

    @FXML
    private void addTaskList(ActionEvent event) {
        String itemChosen = taskComboBox.getValue().toString();
        itemChosen += "," + diamStr.getText() + ",";
        itemChosen += feetStr.getText();

        System.out.println(itemChosen);

        taskTypeListStr.add(itemChosen);
        taskTypeList.setItems(taskTypeListStr);

    }

    @FXML
    private void addCustButAction(ActionEvent event) throws SQLException {
        custAdd = custListing.getSelectionModel().selectedItemProperty().getValue().toString();

        conn = DriverManager.getConnection(url, userdb, passdb);
        st = conn.createStatement();
        String qry = "select * from customer where CompanyName ='" + custAdd.trim() + "';";
        System.out.println("qry: " + qry);

        rs = st.executeQuery(qry);
        while (rs.next()) {
            cid = rs.getString(1);
            streetAddrStr = rs.getString(4);
            cityStr = rs.getString(5);
            stateStr = rs.getString(6);
            zipStr = rs.getString(7);
            phone = rs.getString(8);
            fax = rs.getString(9);
            pocName = rs.getString(14);
            pocPhone = rs.getString(15);

            //custList.add(rs.getString(1));
        }
        setCustPhone.setText(phone);
        setCustName.setText(custAdd.trim());
        setCustCity.setText(cityStr);
        setCustState.setText(stateStr);
        setCustPOC.setText(pocPhone);
        setCustCompPhone.setText(phone);
        setCustFax.setText(fax);
        setCustAddr.setText(streetAddrStr);
        setCustZip.setText(zipStr);

    }

    @FXML
    private void saveJobDb(ActionEvent event) throws SQLException, IOException {

        jobTitleStr = jobTitle.getText();
        jobNameStr = jobName.getText();
        custJobNumStr = custJobNum.getText();
        custJobNameStr = custJobName.getText();
        startDateStr = jobName.getText();
        startTimeStr = custJobNum.getText();

        jobtypecompiled = "";
        empCompiled = "";
        equipCompiled = "";

        //compile job types 
        for (int i = 0; i < taskTypeListStr.size(); i++) {
            jobtypecompiled += "/" + taskTypeListStr.get(i);
        }

        //compile employees  
        for (int j = 0; j < empListSel.size(); j++) {
            empCompiled += "/" + empListSel.get(j);
        }

        //compile equipment  
        for (int k = 0; k < vehList.size(); k++) {
            equipCompiled += "/" + vehList.get(k);
        }

        System.out.println("CID: " + cid);
        System.out.println("Job name: " + jobNameStr);
        System.out.println("Cust job #: " + custJobNumStr);
        System.out.println("Cust job name: " + custJobNameStr);
        System.out.println("start date: " + startDateStr);
        System.out.println("start time: " + startTimeStr);
        System.out.println("street: " + streetAddr.getText());
        System.out.println("city: " + city.getText());
        System.out.println("state: " + state.getText());
        System.out.println("zip: " + zip.getText());

        if (prodChk.isSelected()) {
            billing = "Production Payment";
        }
        if (hourChk.isSelected()) {
            billing = "Hourly Payment";
        }

        sI = sInstr.getText();
        dI = dInstr.getText();
        tI = tInstr.getText();
        wI = wInstr.getText();

        System.out.println(sI);
        System.out.println(dI);
        System.out.println(tI);
        System.out.println(wI);



        Parent root = FXMLLoader.load(getClass().getResource("confirm_job.fxml"));

        Scene scene2 = new Scene(root);
        stageJob = new Stage();


        stageJob.setScene(scene2);
        stageJob.setResizable(false);

        stageJob.show();
    }

    @FXML
    private void clearJobEntries(ActionEvent event) throws SQLException {
        vehList.clear();
        vehList11 = FXCollections.observableArrayList(vehList);
        vehicleEquipSelected.setItems(vehList11);

        empListSel.clear();
        empSelect = FXCollections.observableArrayList(empListSel);
        employeeSelected.setItems(empSelect);

        jobTitle.setText("");
        jobName.setText("");
        custJobNum.setText("");
        custJobName.setText("");
        jobName.setText("");
        custJobNum.setText("");

        streetAddr.setText("");
        city.setText("");
        state.setText("");
        zip.setText("");
        startDate.setText("");
        startTime.setText("");

        setCustPhone.setText("");
        setCustName.setText("");
        setCustCity.setText("");
        setCustState.setText("");
        setCustPOC.setText("");
        setCustCompPhone.setText("");
        setCustFax.setText("");
        setCustAddr.setText("");
        setCustZip.setText("");

        feetStr.setText("");
        diamStr.setText("");

        sInstr.setText("");
        dInstr.setText("");
        tInstr.setText("");
        wInstr.setText("");


        taskTypeListStr.clear();
        taskTypeList.setItems(taskTypeListStr);


        CreateJobBox.setVisible(true);
    }

    @FXML
    private void deleteVeh(ActionEvent event) throws SQLException {
        String del = vehicleEquipSelected.getSelectionModel().selectedItemProperty().getValue().toString();
        System.out.println("delete: " + del);
        for (int i = 0; i < vehList.size(); i++) {
            if (vehList.get(i).toString().equals(del)) {
                vehList.remove(i);
            }
        }
        vehList11 = FXCollections.observableArrayList(vehList);
        vehicleEquipSelected.setItems(vehList11);
    }

    @FXML
    private void deleteEmp(ActionEvent event) throws SQLException {
        String del = employeeSelected.getSelectionModel().selectedItemProperty().getValue().toString();
        System.out.println("delete: " + del);
        for (int i = 0; i < empListSel.size(); i++) {
            if (empListSel.get(i).toString().equals(del)) {
                empListSel.remove(i);
            }
        }
        empSelect = FXCollections.observableArrayList(empListSel);
        employeeSelected.setItems(empSelect);

    }

    @FXML
    private void addEmpJob(ActionEvent event) throws SQLException {
        String val = employeeSelect.getSelectionModel().selectedItemProperty().getValue().toString();
        System.out.println(val);


        if (!empListSel.contains(val)) {
            empListSel.add(val);
        }
        empSelect = FXCollections.observableArrayList(empListSel);
        employeeSelected.setItems(empSelect);
    }

    @FXML
    private void addVehEquip(ActionEvent event) throws SQLException {
        String val = vehicleEquipSelect.getSelectionModel().selectedItemProperty().getValue().toString();
        System.out.println(val);

        if (!vehList.contains(val)) {
            vehList.add(val);
        }
        vehList11 = FXCollections.observableArrayList(vehList);
        vehicleEquipSelected.setItems(vehList11);
    }

    @FXML
    private void setJobBoard(ActionEvent event) throws SQLException {
        Group root = new Group();
        Button closeWindow = new Button("Close");

        String itemChosen = screenList.getValue().toString();

        //make connection to db 
        try {
            conn = DriverManager.getConnection(url, userdb, passdb);
        } catch (SQLException ex) {
            Logger.getLogger(JobCenterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (conn != null && !conn.isClosed()) {
            System.out.println("Connection Established...");
        }

        //insert into database
        Statement updateDb = null;
        updateDb = conn.createStatement();

        String updateJobBoard = "update screensettings set screen = '" + itemChosen + "' where appView = 'JobBoard'";
        System.out.println(updateJobBoard);

        //set our session id and ip address in order to identify user.
        int executeUpdate = updateDb.executeUpdate(updateJobBoard);

        //show the complete box dialog
        Label label2;
        label2 = new Label("Screen set: " + itemChosen);
        HBox hb2 = new HBox();

        hb2.getChildren().addAll(label2, closeWindow);
        hb2.setSpacing(10);
        hb2.setLayoutX(25);
        hb2.setLayoutY(48);
        root.getChildren().add(hb2);

        final Scene scene2 = new Scene(root);
        final Stage stage2 = new Stage();

        stage2.close();
        stage2.setScene(scene2);
        stage2.setHeight(150);
        stage2.setWidth(310);
        stage2.setResizable(false);
        stage2.show();

        closeWindow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                stage2.close();
            }
        });

    }

    @FXML
    private void handlePasswdAction(ActionEvent event) {
        //Creating a GridPane container
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        //title of pane
        Label title = new Label("Reset Password");
        title.getText();
        GridPane.setConstraints(title, 0, 0);
        grid.getChildren().add(title);

        //Defining the Name text field
        final PasswordField oldPass = new PasswordField();
        oldPass.setPromptText("Enter old password");
        oldPass.setPrefColumnCount(10);
        oldPass.getText();
        GridPane.setConstraints(oldPass, 0, 1);
        grid.getChildren().add(oldPass);

        //Defining the Name text field
        final PasswordField newPw = new PasswordField();
        newPw.setPromptText("Enter new password");
        newPw.setPrefColumnCount(10);
        newPw.getText();
        GridPane.setConstraints(newPw, 0, 2);
        grid.getChildren().add(newPw);
        //Defining the Last Name text field
        final PasswordField verHash = new PasswordField();
        verHash.setPromptText("Re-enter password");
        GridPane.setConstraints(verHash, 0, 3);
        grid.getChildren().add(verHash);
        //Defining the Submit button
        Button submit = new Button("Submit");


        GridPane.setConstraints(submit, 1, 3);
        grid.getChildren().add(submit);

        Group root = new Group(), root2 = new Group(), root3 = new Group();
        final Stage stage2 = new Stage();

        Scene scene = new Scene(root);
        root.getChildren().addAll(grid);

        stage2.setScene(scene);
        stage2.setHeight(150);
        stage2.setWidth(250);
        stage2.setResizable(false);
        stage2.show();

        Label label1 = new Label("Password Changed!");
        Button closeWindow = new Button("Close");
        HBox hb = new HBox();

        hb.getChildren().addAll(label1, closeWindow);
        hb.setSpacing(10);
        hb.setLayoutX(48);
        hb.setLayoutY(48);

        final Scene scene2 = new Scene(root2);
        root2.getChildren().addAll(hb, closeWindow);

        Label label2 = new Label("Password not changed!");
        HBox hb2 = new HBox();

        hb2.getChildren().addAll(label2, closeWindow);
        hb2.setSpacing(10);
        hb2.setLayoutX(48);
        hb2.setLayoutY(48);

        final Scene scene3 = new Scene(root3);
        root3.getChildren().addAll(hb2);

        //***************************************************************
        //here we define what all our buttons do within this transaction :)
        //***************************************************************


        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                try {
                    String user = new Scanner(new File("usrinfo.txt")).useDelimiter("\\Z").next(),
                            pass = oldPass.getText();
                    boolean authorized = false;

                    //System.out.println(content);

                    System.out.println("Button click!");
                    System.out.println("saved user name: " + user);
                    String driver = "com.mysql.jdbc.Driver";
                    try {
                        Class.forName(driver).newInstance();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(JobCenterController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(JobCenterMainController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(JobCenterMainController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    //get md5 of old password (for verification); then get md5 of new password to store
                    String oldPwHash = getMD5(oldPass.getText()),
                            newPwHash = getMD5(newPw.getText()),
                            verifyHash = getMD5(verHash.getText());

                    if (verifyHash.equals(newPwHash)) {


                        //make the connection
                        try {
                            conn = DriverManager.getConnection(url, userdb, passdb);
                        } catch (SQLException ex) {
                            Logger.getLogger(JobCenterController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (conn != null && !conn.isClosed()) {
                            System.out.println("Connection Established...");
                        }

                        st = conn.createStatement();
                        rs = st.executeQuery("select userName from users;");

                        while (rs.next()) {
                            System.out.println(rs.getString(1));

                            //check to see if username matches
                            if (rs.getString(1).equals(user)) {
                                String qry = "select password from users where userName = '" + user + "';";
                                System.out.println(qry);
                                rs = st.executeQuery(qry);

                                while (rs.next()) {

                                    //check to see if password matches
                                    if (rs.getString(1).equals(oldPwHash)) {
                                        System.out.println("matched!" + rs.getString(1));
                                        authorized = true;
                                    }
                                }
                            }
                        }

                        if (authorized == true) {
                            String qry3 = "update users set password = '" + newPwHash + "' where userName = '" + user + "';";
                            System.out.println(qry3);
                            Statement updateDb = null;
                            updateDb = conn.createStatement();
                            int executeUpdate = updateDb.executeUpdate(qry3);

                            stage2.close();
                            stage2.setScene(scene2);
                            stage2.setHeight(150);
                            stage2.setWidth(250);
                            stage2.setResizable(false);
                            stage2.show();

                        } else {
                            stage2.close();
                            stage2.setScene(scene3);
                            stage2.setHeight(150);
                            stage2.setWidth(250);
                            stage2.setResizable(false);
                            stage2.show();
                        }
                    } //if the hashes for new password do not match then exit immediately.....
                    else {
                        stage2.close();
                        stage2.setScene(scene3);
                        stage2.setHeight(150);
                        stage2.setWidth(250);
                        stage2.setResizable(false);
                        stage2.show();
                    }


                } catch (SQLException ex) {
                    Logger.getLogger(JobCenterMainController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(JobCenterMainController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(JobCenterMainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private String getMD5(String val) throws NoSuchAlgorithmException {
                //for secure password
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.reset();
                md.update(val.getBytes());

                byte[] digest = md.digest();
                BigInteger bigInt = new BigInteger(1, digest);
                String hashtextpw = bigInt.toString(16);

                // Now we need to zero pad it if you actually want the full 32 chars.
                while (hashtextpw.length() < 32) {
                    hashtextpw = "0" + hashtextpw;
                }
                return hashtextpw;

            }
        });

        closeWindow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                stage2.close();
            }
        });


    }
}
