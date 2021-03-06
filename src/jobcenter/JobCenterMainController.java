/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jobcenter;

import javafx.scene.paint.*;
import javafx.scene.shape.*;
import java.awt.Font;
import javafx.print.PrinterJob;
import java.awt.print.PrinterException;
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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.jnlp.PrintService;

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
    private static String printerName = "";
    Statement st = null;
    ResultSet rs = null;
    public static Connection conn;
    public ScreenPane myScreenPane;
    public static ListView adminList, taskList, proList, employeeSelect, employeeSelected,
            vehicleEquipSelect, vehicleEquipSelected, custListing, taskTypeList, empAddJobView, vehAddJobView;
    public static Label changeMe;
    public static TitledPane createJob;
    public static Pane CreateJobBox, settingsPane, displayJobs, employeePane, equipVehPane,
            startPaneNothing, managerPane;
    public static ToolBar FunctionsToolBar, ReportsToolBar, employeeToolbar, createJobToolbar,
            editJobToolbar;
    public static TableView usersTable;
    public static TableView<employee> employeeTable = new TableView<employee>();
    public static TableView<equipment> equipmentTable = new TableView<equipment>();
    public static TableColumn emp_fname, emp_lname, emp_phone, emp_email,
            vehNameIns, typeIns, statusIns;
    public static Button chgPasswd, addEmp, addVehBut, deleteVehBut, clearJob,
            saveJob, confirmJob, cancelJob, addCustBut, addTask, delTask, displayJobBut,
            deleteEmpBut, deleteEquipBut, addEquipBut, editJobBut, saveChangesBut, addEmpToTreeBut,
            addVehEqToTreeBut;
    //All stuff on job creation form
    public static Text setCustPhone, setCustName, setCustCity, setCustState, setCustPOC, setCustCompPhone,
            setCustFax, setCustAddr, setCustZip;
    public static TextField jobTitle, jobName, custJobNum, custJobName, startDate, startTime,
            streetAddr, city, state, zip, diamStr, feetStr, fNameStrIns, lNameStrIns, phoneStrIns, emailStrIns,
            vehNameNew, typeNew, statusNew;
    public static String jobTitleStr, jobNameStr, custJobNumStr, custJobNameStr, startDateStr, startTimeStr,
            streetAddrStr, cityStr, stateStr, zipStr, custAdd, phone, fax, pocName, pocPhone, status, custUniqueID;
    public static ComboBox screenList, taskComboBox, jobStatus;
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
    public static ObservableList<String> jStatus = FXCollections.observableArrayList(
            "IN PROGRESS", "COMPLETE", "HOLD-CUSTOMER", "HOLD-WEATHER", "HOLD-OTHER", "PROJECTED", "CANCELLED");
    public static List<String> list = new ArrayList<String>();
    public static ObservableList<String> options = FXCollections.observableList(list),
            taskListBox = FXCollections.observableList(list),
            taskTypeListStr = FXCollections.observableList(list);
    List<String> empListSel = new ArrayList<String>(),
            vehList = new ArrayList<String>(),
            custList = new ArrayList<String>(),
            jobTypePicked = new ArrayList<String>(),
            jobList = new ArrayList<String>();
    ObservableList<String> vehList11 = FXCollections.observableArrayList(vehList),
            empSelect = FXCollections.observableArrayList(empListSel),
            custListingObs = FXCollections.observableArrayList(custList),
            displayListUpdater = FXCollections.observableArrayList(custList);
    public static String billing, cid, jobtypecompiled, empCompiled, equipCompiled, sI, dI, tI, wI;
    TreeItem<String> root559;
    @FXML
    TreeView<String> currentJobsDisplay;
    private String custName;

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

        //System.out.println(employeeTable.getColumns());

        vehNameIns.setCellValueFactory(new PropertyValueFactory<equipment, String>("veh"));
        typeIns.setCellValueFactory(new PropertyValueFactory<equipment, String>("type"));
        statusIns.setCellValueFactory(new PropertyValueFactory<equipment, String>("stat"));

        equipmentTable.setItems(populateEquip());

        //if you wanna add an icon in front
        //   Node rootIcon = new ImageView(new Image(getClass().getResourceAsStream("exchange.png")));

    }

    //returns true or false if an employee exists in a list
    public boolean empExist(String listEmp, String empToAdd) {
        if (listEmp.contains(empToAdd)) {
            return true;
        } else {
            return false;
        }
    }

    public ObservableList<equipment> populateEquip() {
        ObservableList<equipment> tester2 = FXCollections.observableArrayList();

        //make the connection
        try {
            conn = DriverManager.getConnection(url, userdb, passdb);
            st = conn.createStatement();
            rs = st.executeQuery("select VehicleName, VehicleType, VehicleStatus from vehicles;");

            while (rs.next()) {
                tester2.add(new equipment(rs.getString(1), rs.getString(2), rs.getString(3)));
                //System.out.println(rs.getString(1));
                //System.out.println(rs.getString(2));
                //System.out.println(rs.getString(3));

            }

        } catch (SQLException ex) {
            Logger.getLogger(JobCenterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tester2;
    }

    public void refreshList() {
        root559 = new TreeItem<String>("Active Jobs");
        jobList = getJobListTitles();
        root559.setExpanded(true);
        for (int i = 0; i < jobList.size(); i++) {

            //adds each job child node to the treeview
            TreeItem<String> depNode = new TreeItem<String>(jobList.get(i));

            //once job added, grab all cust data needed and add info to that child node
            List<String> jobListInfo = getJobListInfo(jobList.get(i));

            depNode.setExpanded(false);
            for (int j = 0; j < jobListInfo.size(); j++) {
                TreeItem<String> var = new TreeItem<String>(jobListInfo.get(j));
                depNode.getChildren().add(var);
            }

            //add the node and its info into the list
            root559.getChildren().add(depNode);
        }
        currentJobsDisplay.setRoot(root559);
        /*
         currentJobsDisplay.addEventHandler(EventType.ROOT, new EventHandler<Event>() {
         @Override
         public void handle(Event event) {
         //System.out.println("event " + event);
         root559 = new TreeItem<String>("Active Jobs");
         jobList = getJobListTitles();
         root559.setExpanded(true);

         for (int i = 0; i < jobList.size(); i++) {
         TreeItem<String> depNode = new TreeItem<String>(jobList.get(i));

         //grab all cust data needed
         List<String> jobListInfo = getJobListInfo(jobList.get(i));

         depNode.setExpanded(false);
         for (int j = 0; j < jobListInfo.size(); j++) {
         TreeItem<String> var = new TreeItem<String>(jobListInfo.get(j));
         depNode.getChildren().add(var);
         }

         //add the node and its info into the list
         root559.getChildren().add(depNode);
         }
         currentJobsDisplay.setRoot(root559);
         }
         });*/
    }

    //adds infor to child node of root node in treeview... yeah thats confusing kind of... but think hard its easy!
    public List<String> getJobListInfo(String jobtitle) {
        List<String> jobListInfo = new ArrayList<String>();
        //make the connection
        try {
            conn = DriverManager.getConnection(url, userdb, passdb);
            st = conn.createStatement();
            String qry = "SELECT (select CompanyName from customer where CID = Customer_CID), status, JobName, JobWorkDate, JobEmployees, JobEandV from currentjobs where JobTitle='" + jobtitle + "'";
            rs = st.executeQuery(qry);

            while (rs.next()) {
                jobListInfo.add(rs.getString(1));
                jobListInfo.add(rs.getString(2));
                jobListInfo.add(rs.getString(3));
                jobListInfo.add(rs.getString(4));

                //employees list, parse it out and add to the list one by one...

                jobListInfo.add(rs.getString(5));


                //veh/equip
                jobListInfo.add(rs.getString(6));
                //System.out.println(rs.getString(1));
                //System.out.println(rs.getString(2));
                //System.out.println(rs.getString(3));
                //System.out.println(rs.getString(4));
                //System.out.println(rs.getString(5));
                //System.out.println(rs.getString(6));
            }

        } catch (SQLException ex) {
            Logger.getLogger(JobCenterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jobListInfo;
    }

    //returns the title of all jobs for the root node
    public List<String> getJobListTitles() {
        List<String> jobList = new ArrayList<String>();

        //make the connection
        try {
            conn = DriverManager.getConnection(url, userdb, passdb);
            st = conn.createStatement();
            rs = st.executeQuery("select JobTitle from currentjobs;");
            while (rs.next()) {
                jobList.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(JobCenterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jobList;
    }

    public ObservableList<employee> populateDB() {
        ObservableList<employee> tester2 = FXCollections.observableArrayList();

        //make the connection
        try {
            conn = DriverManager.getConnection(url, userdb, passdb);
            st = conn.createStatement();
            rs = st.executeQuery("select fname, lname, phone, email from employees;");

            while (rs.next()) {
                tester2.add(new employee(rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(3)));
                //System.out.println(rs.getString(1));
                //System.out.println(rs.getString(2));
                //System.out.println(rs.getString(3));
                //System.out.println(rs.getString(4));

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
        //System.out.println("my ip: " + myIp);

        //make the connection
        try {
            conn = DriverManager.getConnection(url, userdb, passdb);
        } catch (SQLException ex) {
            Logger.getLogger(JobCenterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (conn != null && !conn.isClosed()) {
            //System.out.println("Connection Established...");
        }


        //delete all entries associated with IP before exiting to the login screen
        Statement updateDb = null;
        updateDb = conn.createStatement();

        String insertSess = "delete from session where ipAddr = '" + myIp + "';";
        //System.out.println(insertSess);

        //set our session id and ip address in order to identify user.
        int executeUpdate = updateDb.executeUpdate(insertSess);

        myScreenPane.setScreen("login");

    }

    public void clearPane() {

        employeePane.setVisible(false);
        equipVehPane.setVisible(false);
        CreateJobBox.setVisible(false);
        settingsPane.setVisible(false);
        displayJobs.setVisible(false);
        managerPane.setVisible(false);
        startPaneNothing.setVisible(true);

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

            rs = st.executeQuery("select fname,lname from employees;");
            while (rs.next()) {
                //System.out.println(rs.getString(1));
                empList.add(rs.getString(1) + " " + rs.getString(2));
            }
            rs = st.executeQuery("select VehicleName from vehicles;");
            while (rs.next()) {
                //System.out.println(rs.getString(1));
                vehList.add(rs.getString(1));
            }
            rs = st.executeQuery("select CompanyName from customer;");
            while (rs.next()) {
                //System.out.println(rs.getString(1));
                custList.add(rs.getString(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(JobCenterController.class.getName()).log(Level.SEVERE, null, ex);
        }



        ObservableList<String> emp = FXCollections.observableArrayList(empList);
        ObservableList<String> vehEquip = FXCollections.observableArrayList(vehList);
        ObservableList<String> custListingObs = FXCollections.observableArrayList(custList);

        //set items on the job form
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
                    startPaneNothing.setVisible(false);
                    managerPane.setVisible(true);
                }
                if (new_val == "People") {
                    startPaneNothing.setVisible(false);
                    employeePane.setVisible(true);
                }
                if (new_val == "Vehicles") {
                    startPaneNothing.setVisible(false);
                    equipVehPane.setVisible(true);


                }
                if (new_val == "Settings") {
                    ObservableList<Screen> getAll = Screen.getScreens();

                    for (int i = 0; i < getAll.size(); i++) {
                        String format = (Screen.getScreens().get(i)).toString();
                        int locSemicolon = format.indexOf(":"),
                                locScreen = format.indexOf("Screen");
                        format = format.substring(locScreen, locSemicolon);

                        //System.out.println(format);
                        options.add(format);
                    }
                    screenList.setItems(options);
                    startPaneNothing.setVisible(false);
                    settingsPane.setVisible(true);
                }
            }
        });

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
                            //System.out.println(rs.getString(1));
                            getJobTypes.add(rs.getString(1));

                        }

                        rs = st.executeQuery("select jobName from jobtype;");
                        while (rs.next()) {
                            //System.out.println(rs.getString(1));
                            taskListBox.add(rs.getString(1));
                        }



                    } catch (SQLException ex) {
                        Logger.getLogger(JobCenterController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    taskComboBox.setItems(taskListBox);
                    startPaneNothing.setVisible(false);
                    CreateJobBox.setVisible(true);
                    editJobToolbar.setVisible(false);
                    createJobToolbar.setVisible(true);
                    jobStatus.setItems(jStatus);

                }
                if (new_val == "Display jobs") {
                    clearPane();
                    startPaneNothing.setVisible(false);
                    displayJobs.setVisible(true);

                    List<String> empList = new ArrayList<String>();
                    List<String> vehList = new ArrayList<String>();

                    //make the connection
                    try {
                        conn = DriverManager.getConnection(url, userdb, passdb);
                        st = conn.createStatement();

                        rs = st.executeQuery("select fname,lname from employees;");
                        while (rs.next()) {
                            //System.out.println(rs.getString(1));
                            empList.add(rs.getString(1) + " " + rs.getString(2));
                        }
                        rs = st.executeQuery("select VehicleName from vehicles;");
                        while (rs.next()) {
                            System.out.println(rs.getString(1));
                            vehList.add(rs.getString(1));
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(JobCenterController.class.getName()).log(Level.SEVERE, null, ex);
                    }



                    ObservableList<String> emp2 = FXCollections.observableArrayList(empList);
                    ObservableList<String> vehEquip2 = FXCollections.observableArrayList(vehList);



                    //also set items in the job view
                    empAddJobView.setItems(emp2);
                    vehAddJobView.setItems(vehEquip2);


                    //adds the treeview here!
                    refreshList();
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
    private void saveChangesAction(ActionEvent event) throws IOException, SQLException {

        jobTitleStr = jobTitle.getText();
        jobNameStr = jobName.getText();
        custJobNumStr = custJobNum.getText();
        custJobNameStr = custJobName.getText();
        startDateStr = startDate.getText();
        startTimeStr = startTime.getText();

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

        //System.out.println("CID: " + cid);
        //System.out.println("Job name: " + jobNameStr);
        //System.out.println("Cust job #: " + custJobNumStr);
        //System.out.println("Cust job name: " + custJobNameStr);
        //System.out.println("start date: " + startDateStr);
        //System.out.println("start time: " + startTimeStr);
        //System.out.println("street: " + streetAddr.getText());
        //System.out.println("city: " + city.getText());
        //System.out.println("state: " + state.getText());
        //System.out.println("zip: " + zip.getText());

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
        status = jobStatus.getSelectionModel().selectedItemProperty().getValue().toString();

        streetAddrStr = streetAddr.getText();
        cityStr = city.getText();
        stateStr = state.getText();
        zipStr = zip.getText();


        System.out.println("job type");
        for (int i = 0; i < jobTypePicked.size(); i++) {
            System.out.print(jobTypePicked.get(i));
            System.out.print(",");
        }

        System.out.println("equipment");
        for (int i = 0; i < vehList.size(); i++) {
            System.out.print(vehList.get(i));
            System.out.print(",");
        }

        System.out.println("employees");
        for (int i = 0; i < empListSel.size(); i++) {
            System.out.println(empListSel.get(i));
            System.out.print(",");
        }
        System.out.println("CID: " + cid);

        String qry = "update currentjobs set "
                + "CustJobNum='" + custJobNumStr
                + "', CustJobName='" + custJobNameStr
                + "', JobTitle='" + jobTitleStr
                + "', JobName='" + jobNameStr
                + "', JobWorkDate='" + startDateStr
                + "', JobStartTime='" + startTimeStr
                + "', JobType='" + jobtypecompiled
                + "', JobEmployees='" + empCompiled
                + "', JobEandV='" + equipCompiled
                + "', S_Instr='" + sI
                + "', D_Instr='" + dI
                + "', T_Instr='" + tI
                + "', W_Instr='" + wI
                + "', billing='" + billing
                + "', status='" + status
                + "', jobSiteAddr='" + streetAddr.getText()
                + "', jobCitySite='" + city.getText()
                + "', jobStateLoc='" + state.getText()
                + "', jobZipLoc='" + zip.getText()
                + "' where CurJobID = " + custUniqueID;

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

        //set our session id and ip address in order to identify user.
        int executeUpdate = updateDb.executeUpdate(qry);

        //show popup that changes are made
        Label label2;
        label2 = new Label("Changes saved.");
        HBox hb2 = new HBox();
        Group root = new Group();


        Button closeWindow = new Button("Close");
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
    private void addEmpToTree(ActionEvent event) throws IOException, SQLException {
        //get job name selected
        String getJobTitle = currentJobsDisplay.getSelectionModel().selectedItemProperty().getValue().toString();
        getJobTitle = getJobTitle.substring(getJobTitle.indexOf(":") + 1, getJobTitle.indexOf("]"));
        getJobTitle = getJobTitle.trim();

        //get employee name selected
        String empStrConv = empAddJobView.getSelectionModel().selectedItemProperty().getValue().toString();

        System.out.println("Root: " + currentJobsDisplay.getSelectionModel().selectedItemProperty().getBean());
        System.out.println("Index: " + currentJobsDisplay.getSelectionModel().getSelectedIndex());
        //System.out.println("RootStr: " + currentJobsDisplay.getChildrenUnmodifiable().get(currentJobsDisplay.getSelectionModel().getSelectedIndex()));
        System.out.println("Adding: " + empStrConv + " to job: " + getJobTitle);

        //check make sure job exists
        //make the connection
        try {
            conn = DriverManager.getConnection(url, userdb, passdb);
            st = conn.createStatement();
            String qryCurJob = "select CurJobID, JobEmployees from currentJobs where JobTitle = '" + getJobTitle + "';";

            rs = st.executeQuery(qryCurJob);
            if (rs.next()) {
                String idMod = rs.getString(1);
                String empList = rs.getString(2);
                System.out.println("JobID: " + idMod);
                System.out.println("employess: " + empList);

                if (empExist(empList, empStrConv)) {
                    System.out.println("Name Exists!!");
                    //popup explain to user that this selection is invalid
                    //show the complete box dialog
                    Label label2;
                    label2 = new Label("Employee already exists.");
                    HBox hb2 = new HBox();
                    Group root = new Group();


                    Button closeWindow = new Button("Close");
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
                } else {
                    System.out.println("Name not found, adding to job!!");
                    empList += "/" + empStrConv;

                    String addEmpStrQry = "update currentjobs set "
                            + "JobEmployees='" + empList
                            + "' where CurJobID = " + idMod;

                    Statement updateDb = null;
                    //set our session id and ip address in order to identify user
                    updateDb = conn.createStatement();

                    int executeUpdate = updateDb.executeUpdate(addEmpStrQry);
                    refreshList();
                }
            } else {
                //popup explain to user that this selection is invalid
                //show the complete box dialog
                Label label2;
                label2 = new Label("Invalid Selection");
                HBox hb2 = new HBox();
                Group root = new Group();


                Button closeWindow = new Button("Close");
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

        } catch (SQLException ex) {
            Logger.getLogger(JobCenterController.class.getName()).log(Level.SEVERE, null, ex);
        }


        //if not exist message to user that what they selected is not a job
        //else add the employee/vehicle in 
        String qryRun = "";
    }

    @FXML
    private void addVehEqToTree(ActionEvent event) throws IOException, SQLException {
        //get job name selected
        String getJobTitle = currentJobsDisplay.getSelectionModel().selectedItemProperty().getValue().toString();
        getJobTitle = getJobTitle.substring(getJobTitle.indexOf(":") + 1, getJobTitle.indexOf("]"));
        getJobTitle = getJobTitle.trim();

        //get veh/equip name selected
        String vehStrConv = vehAddJobView.getSelectionModel().selectedItemProperty().getValue().toString();

        System.out.println("Root: " + currentJobsDisplay.getSelectionModel().selectedItemProperty().getBean());
        System.out.println("Index: " + currentJobsDisplay.getSelectionModel().getSelectedIndex());
        //System.out.println("RootStr: " + currentJobsDisplay.getChildrenUnmodifiable().get(currentJobsDisplay.getSelectionModel().getSelectedIndex()));
        System.out.println("Adding: " + vehStrConv + " to job: " + getJobTitle);

        //check make sure job exists
        //make the connection
        try {
            conn = DriverManager.getConnection(url, userdb, passdb);
            st = conn.createStatement();
            String qryCurJob = "select CurJobID, JobEandV from currentJobs where JobTitle = '" + getJobTitle + "';";

            rs = st.executeQuery(qryCurJob);
            if (rs.next()) {
                String idMod = rs.getString(1);
                String vehList = rs.getString(2);
                System.out.println("JobID: " + idMod);
                System.out.println("employess: " + vehList);

                if (empExist(vehList, vehStrConv)) {
                    System.out.println("Name Exists!!");
                    //popup explain to user that this selection is invalid
                    //show the complete box dialog
                    Label label2;
                    label2 = new Label("veh/equip already exists.");
                    HBox hb2 = new HBox();
                    Group root = new Group();


                    Button closeWindow = new Button("Close");
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
                } else {
                    System.out.println("Name not found, adding to job!!");
                    vehList += "/" + vehStrConv;

                    String addEmpStrQry = "update currentjobs set "
                            + "JobEandV='" + vehList
                            + "' where CurJobID = " + idMod;

                    Statement updateDb = null;
                    //set our session id and ip address in order to identify user
                    updateDb = conn.createStatement();

                    int executeUpdate = updateDb.executeUpdate(addEmpStrQry);
                    refreshList();
                }
            } else {
                //popup explain to user that this selection is invalid
                //show the complete box dialog
                Label label2;
                label2 = new Label("Invalid Selection");
                HBox hb2 = new HBox();
                Group root = new Group();


                Button closeWindow = new Button("Close");
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

        } catch (SQLException ex) {
            Logger.getLogger(JobCenterController.class.getName()).log(Level.SEVERE, null, ex);
        }


        //if not exist message to user that what they selected is not a job
        //else add the employee/vehicle in 
        String qryRun = "";
    }

    @FXML
    private void editJobAction(ActionEvent event) throws IOException, SQLException {
        String getJobTitle = currentJobsDisplay.getSelectionModel().selectedItemProperty().getValue().toString();
        getJobTitle = getJobTitle.substring(getJobTitle.indexOf(":") + 1, getJobTitle.indexOf("]"));
        getJobTitle = getJobTitle.trim();

        //clear the entries on job form first
        clearJobEntriesNow();

        conn = DriverManager.getConnection(url, userdb, passdb);
        st = conn.createStatement();

        String listOfTasks;
        String empLister, vehLister;

        rs = st.executeQuery("select * from currentjobs where JobTitle = '" + getJobTitle + "';");
        while (rs.next()) {
            jobTitle.setText(rs.getString(5));
            cid = rs.getString(2);
            jobName.setText(rs.getString(6));
            custJobNum.setText(rs.getString(3));
            custJobName.setText(rs.getString(4));
            startDate.setText(rs.getString(7));
            startTime.setText(rs.getString(8));
            status = rs.getString(17);
            listOfTasks = rs.getString(9);

            custUniqueID = rs.getString(1);

            sI = rs.getString(12);
            dI = rs.getString(13);
            tI = rs.getString(14);
            wI = rs.getString(15);

            if (rs.getString(15) == "Production Payment") {
                prodChk.setSelected(true);
            } else {
                hourChk.setSelected(true);
            }

            streetAddr.setText(rs.getString(18));
            city.setText(rs.getString(19));
            state.setText(rs.getString(20));
            zip.setText(rs.getString(21));

            sInstr.setText(rs.getString(12));
            dInstr.setText(rs.getString(13));
            tInstr.setText(rs.getString(14));
            wInstr.setText(rs.getString(15));

            empLister = rs.getString(10);
            vehLister = rs.getString(11);



            taskListBox = FXCollections.observableList(new ArrayList<String>());

            List<String> getJobTypes = new ArrayList<String>();
            //make the connection
            try {
                conn = DriverManager.getConnection(url, userdb, passdb);
                st = conn.createStatement();
                rs = st.executeQuery("select jobName from jobType;");
                while (rs.next()) {
                    //System.out.println(rs.getString(1));
                    getJobTypes.add(rs.getString(1));

                }
                rs = st.executeQuery("select jobName from jobtype;");
                while (rs.next()) {
                    //System.out.println(rs.getString(1));
                    taskListBox.add(rs.getString(1));


                }
            } catch (SQLException ex) {
                Logger.getLogger(JobCenterController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

            String tmpStr = "", holder = "", tmpStr2 = "", tmpStr3;

            tmpStr = listOfTasks.substring(1, listOfTasks.length());
            tmpStr2 = empLister.substring(1, empLister.length());
            tmpStr3 = vehLister.substring(1, vehLister.length());

            //3 while statements
            //1. add equipment list
            while (true) {
                if (tmpStr3.indexOf("/") < 0) {
                    vehList.add(tmpStr3.substring(0, tmpStr3.length()));
                    break;
                }
                if (tmpStr3.indexOf("/") == 0) {
                    tmpStr3 = tmpStr3.substring(1, tmpStr3.length());
                    if (tmpStr3.indexOf("/") < 0) {
                        vehList.add(tmpStr3.substring(0, tmpStr3.length()));
                        break;
                    } else {
                        vehList.add(tmpStr3.substring(0, tmpStr3.indexOf("/")));
                        tmpStr3 = tmpStr3.substring(tmpStr3.indexOf("/"), tmpStr3.length());
                    }

                } else {

                    holder = tmpStr3;
                    tmpStr3 = tmpStr3.substring(0, tmpStr3.indexOf("/"));
                    vehList.add(tmpStr3);

                    tmpStr3 = holder;

                    tmpStr3 = tmpStr3.substring(tmpStr3.indexOf("/"), tmpStr3.length());
                }
            }
            vehList11 = FXCollections.observableArrayList(vehList);
            vehicleEquipSelected.setItems(vehList11);

            //2. add employee list
            while (true) {
                if (tmpStr2.indexOf("/") < 0) {
                    empListSel.add(tmpStr2.substring(0, tmpStr2.length()));
                    break;
                }
                if (tmpStr2.indexOf("/") == 0) {
                    tmpStr2 = tmpStr2.substring(1, tmpStr2.length());
                    if (tmpStr2.indexOf("/") < 0) {
                        empListSel.add(tmpStr2.substring(0, tmpStr2.length()));
                        break;
                    } else {
                        empListSel.add(tmpStr2.substring(0, tmpStr2.indexOf("/")));
                        tmpStr2 = tmpStr2.substring(tmpStr2.indexOf("/"), tmpStr2.length());
                    }

                } else {

                    holder = tmpStr2;
                    tmpStr2 = tmpStr2.substring(0, tmpStr2.indexOf("/"));
                    empListSel.add(tmpStr2);

                    tmpStr2 = holder;

                    tmpStr2 = tmpStr2.substring(tmpStr2.indexOf("/"), tmpStr2.length());
                }
            }
            empSelect = FXCollections.observableArrayList(empListSel);
            employeeSelected.setItems(empSelect);


            //3. add task type list
            while (true) {

                if (tmpStr.indexOf("/") < 0) {
                    taskTypeListStr.add(tmpStr.substring(0, tmpStr.length()));
                    break;
                }


                if (tmpStr.indexOf("/") == 0) {
                    taskTypeListStr.add(tmpStr.substring(1, tmpStr.length()));
                    break;
                }


                holder = tmpStr;
                tmpStr = tmpStr.substring(0, tmpStr.indexOf("/"));
                taskTypeListStr.add(tmpStr);

                tmpStr = holder;

                tmpStr = tmpStr.substring(tmpStr.indexOf("/"), tmpStr.length());

            }


            taskTypeList.setItems(taskTypeListStr);


            taskComboBox.setItems(taskListBox);
            taskComboBox.setValue(st);
            jobStatus.setItems(jStatus);
            jobStatus.setValue(status);

            /*
             System.out.println(rs.getString(1));
             System.out.println(rs.getString(2));
             System.out.println(rs.getString(3));
             System.out.println(rs.getString(4));
             System.out.println(rs.getString(5));
             System.out.println(rs.getString(6));
             System.out.println(rs.getString(7));
             System.out.println(rs.getString(8));
             System.out.println(rs.getString(9));
             System.out.println(rs.getString(10));
             System.out.println(rs.getString(11));
             System.out.println(rs.getString(12));
             System.out.println(rs.getString(13));
             System.out.println(rs.getString(14));
             System.out.println(rs.getString(15));
             System.out.println(rs.getString(16));
             System.out.println(rs.getString(17));*/

        }
        String qry = "select * from customer where CID ='" + cid + "';";
        //System.out.println("qry: " + qry);

        rs = st.executeQuery(qry);
        while (rs.next()) {
            cid = rs.getString(1);
            custName = rs.getString(3);
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
        setCustName.setText(custName);
        setCustCity.setText(cityStr);
        setCustState.setText(stateStr);
        setCustPOC.setText(pocPhone);
        setCustCompPhone.setText(phone);
        setCustFax.setText(fax);
        setCustAddr.setText(streetAddrStr);
        setCustZip.setText(zipStr);


        displayJobs.setVisible(false);
        CreateJobBox.setVisible(true);
        createJobToolbar.setVisible(false);
        editJobToolbar.setVisible(true);

    }

    @FXML
    private void deleteEquipAction(ActionEvent event) throws IOException, SQLException {
        String vnamestr = equipmentTable.getSelectionModel().selectedItemProperty().getValue().getVeh();
        String queryDelete = "DELETE FROM vehicles WHERE VehicleName = '" + vnamestr + "'";
        //System.out.println(queryDelete);

        //insert into database
        Statement updateDb = null;

        //make the connection
        try {
            conn = DriverManager.getConnection(url, userdb, passdb);

            //set our session id and ip address in order to identify user.
            updateDb = conn.createStatement();

            int executeUpdate = updateDb.executeUpdate(queryDelete);
            equipmentTable.setItems(populateEquip());



        } catch (SQLException ex) {
            Logger.getLogger(JobCenterController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addEquipAction(ActionEvent event) throws IOException, SQLException {

        String queryRunNow = "insert into vehicles (VehicleName, VehicleType,VehicleStatus) "
                + " values('" + vehNameNew.getText() + "','" + typeNew.getText() + "','"
                + statusNew.getText() + "');";

        //insert into database
        Statement updateDb = null;
        //System.out.println("Add EQUIP: " + queryRunNow);

        //make the connection
        try {
            conn = DriverManager.getConnection(url, userdb, passdb);

            //set our session id and ip address in order to identify user
            updateDb = conn.createStatement();

            int executeUpdate = updateDb.executeUpdate(queryRunNow);
            equipmentTable.setItems(populateEquip());


            //show the complete box dialog
            Label label2;
            label2 = new Label("Equipment Added");
            HBox hb2 = new HBox();
            Group root = new Group();


            Button closeWindow = new Button("Close");
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



        } catch (SQLException ex) {
            Logger.getLogger(JobCenterController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void deleteEmpAction(ActionEvent event) throws IOException, SQLException {
        String emailToDelete = employeeTable.getSelectionModel().selectedItemProperty().getValue().getEmail();
        String queryDelete = "DELETE FROM employees WHERE email = '" + emailToDelete + "'";
        //System.out.println(queryDelete);

        //insert into database
        Statement updateDb = null;

        //make the connection
        try {
            conn = DriverManager.getConnection(url, userdb, passdb);

            //set our session id and ip address in order to identify user.
            updateDb = conn.createStatement();

            int executeUpdate = updateDb.executeUpdate(queryDelete);
            employeeTable.setItems(populateDB());



        } catch (SQLException ex) {
            Logger.getLogger(JobCenterController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addEmployeeAction(ActionEvent event) throws IOException, SQLException {
        String queryRun = "insert into employees (fname, lname,address,phone,email,birthdate) "
                + "values('" + fNameStrIns.getText() + "','" + lNameStrIns.getText() + "','"
                + "null','" + phoneStrIns.getText() + "','" + emailStrIns.getText() + "','2013-11-21" + "')";

        //insert into database
        Statement updateDb = null;
        //System.out.println(queryRun);

        //make the connection
        try {
            conn = DriverManager.getConnection(url, userdb, passdb);

            //set our session id and ip address in order to identify user
            updateDb = conn.createStatement();

            int executeUpdate = updateDb.executeUpdate(queryRun);
            employeeTable.setItems(populateDB());

            //show the complete box dialog
            Label label2;
            label2 = new Label("Employee Added");
            HBox hb2 = new HBox();
            Group root = new Group();


            Button closeWindow = new Button("Close");
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



        } catch (SQLException ex) {
            Logger.getLogger(JobCenterController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void displayJobAction(ActionEvent event) throws IOException, SQLException {
        //Parent root = FXMLLoader.load(getClass().getResource("DisplayJobBoard.fxml"));
        conn = DriverManager.getConnection(url, userdb, passdb);
        st = conn.createStatement();
        String qry = "select * from currentjobs where status ='IN PROGRESS';";
        //System.out.println("qry: " + qry);
        String jobTxtStr = "", jobTypeStr = "", jobDateTxtStr = "", jobStatusStr = "", empListStr = "", equipListStr = "";
        int countAmt = 1, area1 = 3, area2 = 4, area3 = 5, area4 = 6;
        Group root = new Group();
        GridPane grid = new GridPane();

        List<String> empListSort = new ArrayList<String>();
        List<String> equipListSort = new ArrayList<String>();

        rs = st.executeQuery(qry);
        while (rs.next()) {
            jobTxtStr = rs.getString(5);
            jobTypeStr = rs.getString(6);
            jobDateTxtStr = rs.getString(7);
            jobStatusStr = rs.getString(17);

            empListStr = rs.getString(10);
            equipListStr = rs.getString(11);

            //System.out.println(jobTxtStr);
            //System.out.println("Count: " + Integer.toString(countAmt));

            ToolBar addme = new ToolBar();
            Button test = new Button("Print");

            addme.getItems().add(test);
            addme.setMinWidth(1255);
            grid.setHgap(10);
            grid.setVgap(3);

            //grid.setBlendMode(BlendMode.DIFFERENCE);

            TextField jobTxt = new TextField(),
                    jobTypeTxt = new TextField(),
                    jobDateTxt = new TextField(),
                    jobStatusBox = new TextField();

            jobTxt.setStyle("-fx-background-color: lightblue;"
                    + "-fx-font-size: 15;");
            jobTxt.setMaxWidth(150);
            jobTxt.setEditable(false);
            jobTxt.setText(jobTxtStr);
            jobTxt.setAlignment(Pos.CENTER);

            jobTypeTxt.setStyle("-fx-background-color: lightblue;"
                    + "-fx-font-size: 15;");
            jobTypeTxt.setMaxWidth(150);
            jobTypeTxt.setEditable(false);
            jobTypeTxt.setText(jobTypeStr);
            jobTypeTxt.setAlignment(Pos.CENTER);

            jobDateTxt.setStyle("-fx-background-color: white;"
                    + "-fx-font-size: 15;");
            jobDateTxt.setMaxWidth(150);
            jobDateTxt.setEditable(false);
            jobDateTxt.setText(jobDateTxtStr);
            jobDateTxt.setAlignment(Pos.CENTER);

            jobStatusBox.setStyle("-fx-background-color: white;"
                    + "-fx-font-size: 15;");
            jobStatusBox.setMaxWidth(150);
            jobStatusBox.setEditable(false);
            jobStatusBox.setText(jobStatusStr);
            jobStatusBox.setAlignment(Pos.CENTER);

            if (countAmt == 10) {
                countAmt = 1;
                area1 += 50;
                area2 += 50;
                area3 += 50;
                area4 += 50;
            }
            if (countAmt == 20) {
                countAmt = 1;
                area1 += 100;
                area2 += 100;
                area3 += 100;
                area4 += 100;
            }
            if (countAmt == 30) {
                countAmt = 1;
                area1 += 30;
                area2 += 30;
                area3 += 30;
                area4 += 30;
            }
            grid.add(jobTxt, countAmt, area1);
            grid.add(jobTypeTxt, countAmt, area2);
            grid.add(jobDateTxt, countAmt, area3);
            grid.add(jobStatusBox, countAmt, area4);

            //System.out.println("Index: " + empListStr.indexOf("/"));
            //System.out.println("at row: " + countAmt);

            String nameOfPerson;
            int counterArea = area4 + 1;

            //displays the employees who are set for the job
            while (true) {

                if (empListStr.indexOf("/") < 0) {
                    break;
                }


                if (empListStr.indexOf("/") >= 0) {
                    //sort out employees for display
                    empListStr = empListStr.substring(0, empListStr.length());

                    //System.out.println("Unprocessed string: " + empListStr);
                    //System.out.println("Before: " + empListStr);
                    //System.out.println(empListStr.indexOf("/"));

                    if (empListStr.indexOf("/") == 0) {
                        empListStr = empListStr.substring(1, empListStr.length());

                        if (empListStr.indexOf("/") > 0) {
                            nameOfPerson = empListStr.substring(0, empListStr.indexOf("/"));
                            empListStr = empListStr.substring(empListStr.indexOf("/"), empListStr.length());
                        } else {
                            nameOfPerson = empListStr.substring(0, empListStr.length());
                        }

                        //empListStr = empListStr.substring(empListStr.indexOf("/") + 1, empListStr.length());
                        //System.out.println("After: " + empListStr);
                        //System.out.println("Adding: " + nameOfPerson);
                        TextField nameTxt = new TextField();
                        nameTxt.setMaxWidth(150);
                        nameTxt.setStyle("-fx-background-color: green;"
                                + "-fx-font-size: 15;");

                        nameTxt.setMaxHeight(100);
                        nameTxt.setEditable(false);
                        nameTxt.setText(nameOfPerson);
                        nameTxt.setAlignment(Pos.CENTER);

                        grid.add(nameTxt, countAmt, counterArea);
                        //System.out.println("at row: " + countAmt);
                        counterArea++;

                    }
                } else {
                    //System.out.println("Adding2: " + empListStr);
                    nameOfPerson = empListStr;
                    TextField nameTxt = new TextField();
                    nameTxt.setStyle("-fx-background-color: green;"
                            + "-fx-font-size: 15;");
                    nameTxt.setMaxWidth(150);
                    nameTxt.setEditable(false);
                    nameTxt.setText(nameOfPerson);
                    nameTxt.setAlignment(Pos.CENTER);

                    grid.add(nameTxt, countAmt, counterArea);
                    //System.out.println("at row2: " + countAmt);
                    empListStr = "";
                    counterArea++;

                    break;
                }



            }
            String equipNameStr;
            //displays the equipment set for the job
            while (true) {
                if (equipListStr.indexOf("/") >= 0) {
                    //sort out employees for display
                    equipListStr = equipListStr.substring(1, equipListStr.length() - 1);
                    //System.out.println("\n\nUnprocessed string: " + equipListStr);
                    equipNameStr = "";
                    //System.out.println("Before: " + equipListStr);
                    //System.out.println(equipListStr.indexOf("/"));
                    if (equipListStr.indexOf("/") > 0) {
                        equipNameStr = equipListStr.substring(0, equipListStr.indexOf("/"));
                        equipListStr = equipListStr.substring(equipListStr.indexOf("/"), equipListStr.length());
                        //System.out.println("After: " + equipListStr);


                        //System.out.println("Adding: " + equipNameStr);
                        TextField nameTxt = new TextField();

                        nameTxt.setStyle("-fx-background-color: yellow;"
                                + "-fx-font-size: 15;");
                        nameTxt.setMaxWidth(150);
                        nameTxt.setMaxHeight(70);
                        nameTxt.setEditable(false);
                        nameTxt.setText(equipNameStr);
                        nameTxt.setAlignment(Pos.CENTER);

                        grid.add(nameTxt, countAmt, counterArea);
                        //System.out.println("at row: " + countAmt);
                        counterArea++;

                    }
                } else {
                    //System.out.println("Adding2: " + equipListStr);
                    equipNameStr = equipListStr;
                    TextField nameTxt = new TextField();

                    nameTxt.setStyle("-fx-background-color: yellow;"
                            + "-fx-font-size: 15;");
                    nameTxt.setMaxWidth(150);
                    nameTxt.setEditable(false);
                    nameTxt.setText(equipNameStr);
                    nameTxt.setAlignment(Pos.CENTER);

                    grid.add(nameTxt, countAmt, counterArea);
                    //System.out.println("at row2: " + countAmt);
                    empListStr = "";
                    counterArea++;

                    break;
                }



            }







            countAmt++;
        }






        root.getChildren().add(grid);

        Scene scene2 = new Scene(root, Color.BLACK);
        stageJob = new Stage();
        stageJob.setHeight(662);
        stageJob.setWidth(1224);
        stageJob.setResizable(false);
        stageJob.setFullScreen(true);


        stageJob.setScene(scene2);
        stageJob.setResizable(false);
        javafx.geometry.Rectangle2D primaryScreenBounds = Screen.getScreens().get(1).getVisualBounds();
        stageJob.setX(primaryScreenBounds.getMinX());
        stageJob.setY(primaryScreenBounds.getMinY());


        stageJob.show();

    }

    @FXML
    private void delTaskList(ActionEvent event) {
        String del = taskTypeList.getSelectionModel().selectedItemProperty().getValue().toString();
        //System.out.println("delete: " + del);
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

        //System.out.println(itemChosen);

        taskTypeListStr.add(itemChosen);
        taskTypeList.setItems(taskTypeListStr);

    }

    @FXML
    private void addCustButAction(ActionEvent event) throws SQLException {
        custAdd = custListing.getSelectionModel().selectedItemProperty().getValue().toString();

        conn = DriverManager.getConnection(url, userdb, passdb);
        st = conn.createStatement();
        String qry = "select * from customer where CompanyName ='" + custAdd.trim() + "';";
        //System.out.println("qry: " + qry);

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
        startDateStr = startDate.getText();
        startTimeStr = startTime.getText();

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

        //System.out.println("CID: " + cid);
        //System.out.println("Job name: " + jobNameStr);
        //System.out.println("Cust job #: " + custJobNumStr);
        //System.out.println("Cust job name: " + custJobNameStr);
        //System.out.println("start date: " + startDateStr);
        //System.out.println("start time: " + startTimeStr);
        //System.out.println("street: " + streetAddr.getText());
        //System.out.println("city: " + city.getText());
        //System.out.println("state: " + state.getText());
        //System.out.println("zip: " + zip.getText());

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
        status = jobStatus.getSelectionModel().selectedItemProperty().getValue().toString();
        //System.out.println(sI);
        //System.out.println(dI);
        //System.out.println(tI);
        //System.out.println(wI);

        Parent root5 = FXMLLoader.load(getClass().getResource("confirm_job.fxml"));

        Scene scene2 = new Scene(root5);
        stageJob = new Stage();

        stageJob.setScene(scene2);
        stageJob.setResizable(false);


        currentJobsDisplay.addEventHandler(EventType.ROOT, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                //System.out.println("event " + event);
            }
        });
        stageJob.show();



    }

    @FXML
    private void summaryAction(ActionEvent event) throws PrinterException {

        System.out.println(Printer.get);
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, 
                PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);

        double scaleX = pageLayout.getPrintableWidth() / myScreenPane.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / myScreenPane.getBoundsInParent().getHeight();
        myScreenPane.getTransforms().add(new Scale(scaleX, scaleY));

        PrinterJob job = PrinterJob.createPrinterJob();

        if (job != null) {
            boolean success = job.printPage(myScreenPane);
            if (success) {
                job.endJob();
            }

        }


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
        createJobToolbar.setVisible(true);
        startPaneNothing.setVisible(false);
    }

    @FXML
    private void deleteVeh(ActionEvent event) throws SQLException {
        String del = vehicleEquipSelected.getSelectionModel().selectedItemProperty().getValue().toString();
        //System.out.println("delete: " + del);
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
        //System.out.println("delete: " + del);
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
        //System.out.println(val);


        if (!empListSel.contains(val)) {
            empListSel.add(val);
        }
        empSelect = FXCollections.observableArrayList(empListSel);
        employeeSelected.setItems(empSelect);
    }

    @FXML
    private void addVehEquip(ActionEvent event) throws SQLException {
        String val = vehicleEquipSelect.getSelectionModel().selectedItemProperty().getValue().toString();
        //System.out.println(val);

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
            Logger.getLogger(JobCenterController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        if (conn != null && !conn.isClosed()) {
            //System.out.println("Connection Established...");
        }

        //insert into database
        Statement updateDb = null;
        updateDb = conn.createStatement();

        String updateJobBoard = "update screensettings set screen = '" + itemChosen + "' where appView = 'JobBoard'";
        //System.out.println(updateJobBoard);

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

                    ////System.out.println(content);

                    //System.out.println("Button click!");
                    //System.out.println("saved user name: " + user);
                    String driver = "com.mysql.jdbc.Driver";
                    try {
                        Class.forName(driver).newInstance();


                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(JobCenterController.class
                                .getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(JobCenterMainController.class
                                .getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(JobCenterMainController.class
                                .getName()).log(Level.SEVERE, null, ex);
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
                            Logger.getLogger(JobCenterController.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        }
                        if (conn != null && !conn.isClosed()) {
                            //System.out.println("Connection Established...");
                        }

                        st = conn.createStatement();
                        rs = st.executeQuery("select userName from users;");

                        while (rs.next()) {
                            //System.out.println(rs.getString(1));

                            //check to see if username matches
                            if (rs.getString(1).equals(user)) {
                                String qry = "select password from users where userName = '" + user + "';";
                                //System.out.println(qry);
                                rs = st.executeQuery(qry);

                                while (rs.next()) {

                                    //check to see if password matches
                                    if (rs.getString(1).equals(oldPwHash)) {
                                        //System.out.println("matched!" + rs.getString(1));
                                        authorized = true;
                                    }
                                }
                            }
                        }

                        if (authorized == true) {
                            String qry3 = "update users set password = '" + newPwHash + "' where userName = '" + user + "';";
                            //System.out.println(qry3);
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
                    Logger.getLogger(JobCenterMainController.class
                            .getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(JobCenterMainController.class
                            .getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(JobCenterMainController.class
                            .getName()).log(Level.SEVERE, null, ex);
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

    // a local accessible method
    private void clearJobEntriesNow() {
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
        createJobToolbar.setVisible(true);
        startPaneNothing.setVisible(false);
    }
}
