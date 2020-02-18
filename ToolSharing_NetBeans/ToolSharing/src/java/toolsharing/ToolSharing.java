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
            
            String sql1 = "Insert into student(student_id) values (?)";

            stm = con.prepareStatement(sql);
            stm.setInt(2, student_id);
            stm.setString(1, decision);
            stm.executeUpdate();
            stm.close();
            
            stm = con.prepareStatement(sql1);
            stm.setInt(1, student_id);
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
    
    
    /********** SEARCH TOOLS LIST *************/
    @GET
    @Path("SearchToolsList")
    @Produces("application/json")
    public String getSearchToolsList() {
        //TODO return proper representation object
           
        System.out.println("/********** SEARCH TOOLS LIST *************/");
        
        try {
            createConnection();

            String sql = "select st.posted_student_id, t.*, coalesce(od.return_date, ''), "
                    + "coalesce(od.from_date, ''), od.rating, "
                    + "DATEDIFF(coalesce(od.from_date, current_date), current_date) availableTill, "
                    + "DATEDIFF(coalesce(od.return_date, current_date), current_date) availableIn "
                    + "from tools t left outer join student_tools st on t.tool_id = st.posted_tool_id  "
                    + "left outer join order_details od on st.posted_tool_id = od.posted_tool_id "
                    + "order by 1 desc, 3";
            
            stm = con.prepareStatement(sql);            
            ResultSet rs = stm.executeQuery();
            
            int psid, ptid, trating, availabletill, availablefrom;
            String fd, rd, name, desc, img;
            if(rs.next() == true){
                do{
                    psid = rs.getInt(1);
                    ptid = rs.getInt(2);
                    name = rs.getString(3);
                    desc = rs.getString(4);
                    img = rs.getString(5);
                    rd = rs.getString(6);
                    fd = rs.getString(7);
                    trating = rs.getInt(8);
                    availabletill = rs.getInt(9);
                    availablefrom = rs.getInt(10);
                    
                    childObj.accumulate("PostedStudentId", psid);
                    childObj.accumulate("PostedToolId", ptid);
                    childObj.accumulate("ToolName", name);                    
                    childObj.accumulate("ToolDesc", desc);
                    childObj.accumulate("ToolImg", img);
                    childObj.accumulate("FromDate", fd);
                    childObj.accumulate("ReturnDate", rd);
                    childObj.accumulate("ToolRating", trating);
                    childObj.accumulate("ToolAvailableTillInDays", availabletill);
                    childObj.accumulate("ToolAvailableFromInDays", availablefrom);
                    jSONArray.add(childObj);  
                    childObj.clear();
                }while(rs.next());
                
                mainObj.accumulate("Status", "Ok");
                mainObj.accumulate("Timestamp", epoc);
                mainObj.accumulate("SearchToolsList", jSONArray);
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
    
    
    /********** TOOL ORDER DETAILS *************/
    @GET
    @Path("ToolOrder&{ptid}&{psid}&{bsid}&{fd}&{td}")
    @Produces("application/json")
    public String getToolOrder(@PathParam("ptid") int ptid, @PathParam("psid") int psid,
            @PathParam("bsid") int bsid, @PathParam("fd") String fd,
            @PathParam("td") String td) {
        //TODO return proper representation object
           
        System.out.println("/********** TOOL ORDER DETAILS *************/");
        
        try {
            createConnection();

            String sql = "INSERT INTO order_details(posted_tool_id, posted_student_id,"
                    + " borrowed_student_id, from_date, to_date, return_date) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            
            stm = con.prepareStatement(sql);
            stm.setInt(1, ptid);
            stm.setInt(2, psid);
            stm.setInt(3, bsid);
            stm.setString(4, fd);
            stm.setString(5, td);
            stm.setString(6, td);
            
            stm.executeUpdate();
            stm.close();

            mainObj.accumulate("Status", "OK");
            mainObj.accumulate("Timestamp", epoc);
            mainObj.accumulate("Message", "Borrowed Successfully!");

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
    
    
    /********** TOOLS LIST *************/
    @GET
    @Path("ToolsList")
    @Produces("application/json")
    public String getToolsList() {
        //TODO return proper representation object
           
        System.out.println("/********** TOOLS LIST *************/");
        
        try {
            createConnection();

            String sql = "SELECT * from tools order by 2";
            
            stm = con.prepareStatement(sql);            
            ResultSet rs = stm.executeQuery();
            
            int id;
            String name, desc, img;
            if(rs.next() == true){
                do{
                    id = rs.getInt(1);
                    name = rs.getString(2);
                    desc = rs.getString(3);
                    img = rs.getString(4);
                    
                    childObj.accumulate("ToolId", id);
                    childObj.accumulate("ToolName", name);                    
                    childObj.accumulate("ToolDesc", desc);
                    childObj.accumulate("ToolImg", img);
                    jSONArray.add(childObj);  
                    childObj.clear();
                }while(rs.next());
                
                mainObj.accumulate("Status", "Ok");
                mainObj.accumulate("Timestamp", epoc);
                mainObj.accumulate("ToolsList", jSONArray);
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
    
    /********** ADD MY TOOLS LIST *************/
    @GET
    @Path("AddTools&{psid}&{ptid}")
    @Produces("application/json")
    public String getaddToolsList(@PathParam("psid") int psid, @PathParam("ptid") int ptid) {
        //TODO return proper representation object
           
        System.out.println("/********** ADD MY TOOLS LIST *************/");
        
        try {
            createConnection();

            String sql = "INSERT INTO student_tools(posted_student_id, posted_tool_id) VALUES (?, ?)";

            stm = con.prepareStatement(sql);
            stm.setInt(1, psid);
            stm.setInt(2, ptid);
            stm.executeUpdate();
                
            mainObj.accumulate("Status", "Ok");
            mainObj.accumulate("Timestamp", epoc);
            mainObj.accumulate("Message", "Tool Added!");                          
            

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
    
    
    /********** MY TOOLS LIST *************/
    @GET
    @Path("MyToolsList&{psid}")
    @Produces("application/json")
    public String getMyToolsList(@PathParam("psid") int psid) {
        //TODO return proper representation object
           
        System.out.println("/********** TOOLS LIST *************/");
        
        try {
            createConnection();

            String sql = "select * from tools "
                    + "where tool_id in (select posted_tool_id from student_tools "
                    + "where posted_student_id = ?) order by 2";
            
            stm = con.prepareStatement(sql);  
            stm.setInt(1,psid);
            ResultSet rs = stm.executeQuery();
            
            int id;
            String name, desc, img;
            if(rs.next() == true){
                do{
                    id = rs.getInt(1);
                    name = rs.getString(2);
                    desc = rs.getString(3);
                    img = rs.getString(4);
                    
                    childObj.accumulate("ToolId", id);
                    childObj.accumulate("ToolName", name);                    
                    childObj.accumulate("ToolDesc", desc);
                    childObj.accumulate("ToolImg", img);
                    jSONArray.add(childObj);  
                    childObj.clear();
                }while(rs.next());
                
                mainObj.accumulate("Status", "Ok");
                mainObj.accumulate("Timestamp", epoc);
                mainObj.accumulate("ToolsList", jSONArray);
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
    
    
    /********** PROFILE INFO *************/
    @GET
    @Path("ProfileInfo&{id}")
    @Produces("application/json")
    public String getProfileInfo(@PathParam("id") int id) {
        //TODO return proper representation object
           
        System.out.println("/********** PROFILE INFO *************/");
        
        try {
            createConnection();

            String sql = "select p.*, coalesce(p.address, '') addr from person p where id = ?";
            
            stm = con.prepareStatement(sql);  
            stm.setInt(1,id);
            ResultSet rs = stm.executeQuery();
            
            Long phone;
            String pwd, fn, ln, addr;
            if(rs.next() == true){
                do{
                    phone = rs.getLong(6);
                    pwd = rs.getString(2);
                    fn = rs.getString(3);
                    ln = rs.getString(4);
                    addr = rs.getString(9);
                }while(rs.next());
                
                mainObj.accumulate("Status", "Ok");
                mainObj.accumulate("Timestamp", epoc);
                mainObj.accumulate("Message", "Success");
                mainObj.accumulate("SchoolId", id);
                mainObj.accumulate("Passwd", pwd);
                mainObj.accumulate("FirstName", fn);
                mainObj.accumulate("LastName", ln);
                mainObj.accumulate("Address", addr);
                mainObj.accumulate("Phone", phone);
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
    
    
    /********** UPDATE PROFILE *************/
    @GET
    @Path("UpdateProfile&{id}&{passwd}&{fname}&{lname}&{phone}&{addr}")
    @Produces("application/json")
    public String updateProfile(@PathParam("id") int id, @PathParam("passwd") String pwd, 
            @PathParam("fname") String fname, @PathParam("lname") String lname,
            @PathParam("phone") long phone,
            @PathParam("addr") String addr) {
        //TODO return proper representation object
           
        System.out.println("/********** UPDATE PROFILE *************/");
        
        try {
            createConnection();

            String sql = "update person\n" +
                        "set passwd = ?,\n" +
                        "first_name = ?,\n" +
                        "last_name = ?,\n" +
                        "phone = ?,\n" +
                        "address = ?\n" +
                        "where id = ?";
            
            stm = con.prepareStatement(sql);
            stm.setInt(6, id);
            stm.setString(1, pwd);
            stm.setString(2, fname);
            stm.setString(3, lname);
            stm.setLong(4, phone);
            stm.setString(5, addr);
            stm.executeUpdate();
            stm.close();

            mainObj.accumulate("Status", "OK");
            mainObj.accumulate("Timestamp", epoc);
            mainObj.accumulate("Message", "Changes Saved Successfully!");

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
