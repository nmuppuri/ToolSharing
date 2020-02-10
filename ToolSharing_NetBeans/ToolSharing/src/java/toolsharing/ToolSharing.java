/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolsharing;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * REST Web Service
 *
 * @author naren
 */
@Path("final")
public class ToolSharing {

    static Connection con = null;
    static PreparedStatement stm = null;
    static ResultSet rs = null;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ToolSharing
     */
    public ToolSharing() {
    }    
    
    JSONObject mainObj = new JSONObject();
    long epoc = Instant.now().getEpochSecond();
    
    JSONObject childObj = new JSONObject();
    JSONArray jSONArray = new JSONArray();

    /**
     * Retrieves representation of an instance of toolsharing.ToolSharing
     * @return an instance of java.lang.String
     */
    
    /********** ADMIN REGISTRATION *************/
    @GET
    @Path("NewAdmin&{id}&{passwd}&{fname}&{lname}&{email}")//&{phone}&{address}")
    @Produces("application/json")
    public String adminRegis(@PathParam("id") int id, @PathParam("passwd") String pwd, 
            @PathParam("fname") String fname, @PathParam("lname") String lname,
            @PathParam("email") String email/*, @PathParam("phone") long phone,
            @PathParam("address") String addr*/) {
        //TODO return proper representation object
           
        System.out.println("/********** ADMIN REGISTRATION *************/");
        
        try {
            createConnection();

            String sql = "INSERT INTO person(id, passwd, first_name, last_name, email,"
                    + "admin_access)"
                    + "VALUES (?, ?, ?, ?, ?, 'Y')";
            String sql1 = "INSERT INTO admins(admin_id) VALUES (?)";

            stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            stm.setString(2, pwd);
            stm.setString(3, fname);
            stm.setString(4, lname);
            stm.setString(5, email);
            //stm.setLong(6, phone);
            //stm.setString(7, addr);
            stm.executeUpdate();
            stm.close();

            stm = con.prepareStatement(sql1);
            stm.setInt(1, id);
            stm.executeUpdate();
            stm.close();

            mainObj.accumulate("Status", "OK");
            mainObj.accumulate("Timestamp", epoc);
            mainObj.accumulate("Message", "Account Created Successfully!");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ToolSharing.class.getName()).log(Level.SEVERE,
                    null, ex);
            return getError(ex.toString(), 0, 0);
        } catch (SQLException ex) {
            Logger.getLogger(ToolSharing.class.getName()).log(Level.SEVERE,
                    null, ex);
            return getError(ex.toString(), 0, 0);
        } finally {
            closeConnection();
        }

        return mainObj.toString();
    }
    
    
    /********** STUDENT REGISTRATION *************/
    @GET
    @Path("NewStudent&{id}&{fname}&{lname}&{email}")//&{phone}&{address}")
    @Produces("application/json")
    public String studentRegis(@PathParam("id") int id, //@PathParam("passwd") String pwd, 
            @PathParam("fname") String fname, @PathParam("lname") String lname,
            @PathParam("email") String email/*, @PathParam("phone") long phone,
            @PathParam("address") String addr*/) {
        //TODO return proper representation object
           
        System.out.println("/********** STUDENT REGISTRATION *************/");
        
        try {
            Random num = new Random();
            int pwd = num.nextInt(9999) + 987321;
            createConnection();

            String sql = "INSERT INTO person(id, first_name, last_name, email,"
                    + "passwd, admin_access)"
                    + "VALUES (?, ?, ?, ?, ?, 'N')";
            String sql1 = "INSERT INTO student_registration(student_id, decision) VALUES (?, 'Pending')";

            stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            //stm.setString(2, pwd);
            stm.setString(2, fname);
            stm.setString(3, lname);
            stm.setString(4, email);
            stm.setString(5, pwd+"");
            //stm.setString(7, addr);
            stm.executeUpdate();
            stm.close();

            stm = con.prepareStatement(sql1);
            stm.setInt(1, id);
            stm.executeUpdate();
            stm.close();

            mainObj.accumulate("Status", "OK");
            mainObj.accumulate("Timestamp", epoc);
            mainObj.accumulate("Message", "Account Created Successfully!");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ToolSharing.class.getName()).log(Level.SEVERE,
                    null, ex);
            return getError(ex.toString(), 0, 0);
        } catch (SQLException ex) {
            Logger.getLogger(ToolSharing.class.getName()).log(Level.SEVERE,
                    null, ex);
            return getError(ex.toString(), 0, 0);
        } finally {
            closeConnection();
        }

        return mainObj.toString();
    }
    
    /********** ADMIN LOGIN *************/
    @GET
    @Path("Login&{id}&{passwd}")
    @Produces("application/json")
    public String getUserInfo(@PathParam("id") int id, @PathParam("passwd") String passwd) {
        
        System.out.println("/********** ADMIN LOGIN *************/");
        
        try {
            createConnection();
            
            String sql = "select admin_access from person where id = ? and passwd = ?";
            stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            stm.setString(2, passwd);
            
            ResultSet rs = stm.executeQuery();
            String admin_access;
            
            
            if(rs.next() == true){
                do{
                    admin_access = rs.getString(1);
                mainObj.accumulate("Status", "Ok");
                mainObj.accumulate("Timestamp", epoc);
                mainObj.accumulate("AdminAccess", admin_access);
                mainObj.accumulate("Message", "Login Successfully!!");
                }while(rs.next());
            } else{
                getError("UserId/Password doesn't match. Please try again!", 0, 0);                
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ToolSharing.class.getName()).log(Level.SEVERE, null, ex);
            return getError(ex.toString(), 0, 0);
        } catch (SQLException ex) {
            Logger.getLogger(ToolSharing.class.getName()).log(Level.SEVERE, null, ex);
            return getError(ex.toString(), 0, 0);
        } finally {
            closeConnection();
        }

        return mainObj.toString();
    }
    
    
    /********** FORGOT PASSWORD *************/
    @GET
        @Path("ForgotPassword&{email}")
    @Produces("application/json")
    public String forgotPassword(@PathParam("email") String email) {
        //TODO return proper representation object
           
        System.out.println("/********** FORGOT PASSWORD *************/");
        
        try {
            createConnection();

            String sql = "select passwd from person "
                    + "where email = trim(?)";
            
            stm = con.prepareStatement(sql); 
            stm.setString(1, email);
            
            ResultSet rs = stm.executeQuery();            
            String pwd;
            if(rs.next() == true){
                do{
                    pwd = rs.getString(1);
                }while(rs.next());
                
                mainObj.accumulate("Status", "Ok");
                mainObj.accumulate("Timestamp", epoc);
                mainObj.accumulate("Password", pwd);
            } else{
                getError("None", 0, 0);                
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ToolSharing.class.getName()).log(Level.SEVERE, null, ex);
            return getError(ex.toString(), 0, 0);
        } catch (SQLException ex) {
            Logger.getLogger(ToolSharing.class.getName()).log(Level.SEVERE, null, ex);
            return getError(ex.toString(), 0, 0);
        } finally {
            closeConnection();
        }

        return mainObj.toString();
    }
    
    
    /********** STUDENT REGISTRATION ACCEPT *************/
    @GET
    @Path("StudentRegis")
    @Produces("application/json")
    public String studentRegisDetails() {
        //TODO return proper representation object
           
        System.out.println("/********** STUDENT REGISTRATION ACCEPT *************/");
        
        try {
            createConnection();

            String sql = "SELECT student_id, concat(first_name, ' ', last_name) as name, email, passwd FROM student_registration inner join person where student_id = id"
                    + " and decision = 'Pending'";
            
            stm = con.prepareStatement(sql);            
            ResultSet rs = stm.executeQuery();
            
            int id;
            String name, email, passwd;
            if(rs.next() == true){
                do{
                    id = rs.getInt(1);
                    name = rs.getString(2);
                    email = rs.getString(3);
                    passwd = rs.getString(4);
                    
                    childObj.accumulate("StudentId", id);
                    childObj.accumulate("StudentName", name);                    
                    childObj.accumulate("StudentEmail", email);                    
                    childObj.accumulate("StudentPwd", passwd);                    
                    jSONArray.add(childObj);  
                    childObj.clear();
                }while(rs.next());
                
                mainObj.accumulate("Status", "Ok");
                mainObj.accumulate("Timestamp", epoc);
                mainObj.accumulate("StudentRegis", jSONArray);
            } else{
                getError("None", 0, 0);                
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ToolSharing.class.getName()).log(Level.SEVERE, null, ex);
            return getError(ex.toString(), 0, 0);
        } catch (SQLException ex) {
            Logger.getLogger(ToolSharing.class.getName()).log(Level.SEVERE, null, ex);
            return getError(ex.toString(), 0, 0);
        } finally {
            closeConnection();
        }

        return mainObj.toString();
    }
    
    @GET
    @Path("StudentRegisAccept&{student_id}&{status}")
    @Produces("application/json")
    public String studentRegisAccept(@PathParam("student_id") int student_id,
            @PathParam("admin_id") int admin_id,
            @PathParam("status") String decision) {
        //TODO return proper representation object
           
        System.out.println("/********** STUDENT REGISTRATION ACCEPT*************/");
        
        try {
            createConnection();

            String sql = "update student_registration\n" +
                            "set decision = ?,\n" +
                            "decision_date = current_date\n" +
                            "where student_id = ?";

            stm = con.prepareStatement(sql);
            stm.setInt(2, student_id);
            stm.setString(1, decision);
            stm.executeUpdate();
            stm.close();

            mainObj.accumulate("Status", "OK");
            mainObj.accumulate("Timestamp", epoc);
            mainObj.accumulate("Message", "Data Updated");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ToolSharing.class.getName()).log(Level.SEVERE,
                    null, ex);
            return getError(ex.toString(), 0, 0);
        } catch (SQLException ex) {
            Logger.getLogger(ToolSharing.class.getName()).log(Level.SEVERE,
                    null, ex);
            return getError(ex.toString(), 0, 0);
        } finally {
            closeConnection();
        }

        return mainObj.toString();
    }
    
    
    
    private static void createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/toolsharing?autoReconnect=true&useSSL=false",
                "root", "naren");
    }

    private static void closeConnection() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                /* ignored */
            }
        }
        if (stm != null) {
            try {
                stm.close();
            } catch (SQLException e) {
                /* ignored */
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                /* ignored */
            }
        }
    }

    public String getError(String name, Object key, Object value) {
        mainObj.accumulate("Status", "Error");
        mainObj.accumulate("Timestamp", epoc);
        if (!value.toString().equals("0")) {
            mainObj.accumulate(key.toString(), value);
        }
        mainObj.accumulate("Message", name);
        return mainObj.toString();
    }
    
    
}
