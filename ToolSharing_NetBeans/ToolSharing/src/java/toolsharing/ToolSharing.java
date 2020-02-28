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
import static org.apache.commons.lang.StringUtils.trim;


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
    
    /********** ADMIN REGISTRATION *************
    @GET
    @Path("NewAdmin&{id}&{passwd}&{fname}&{lname}&{email}")//&{phone}&{address}")
    @Produces("application/json")
    public String adminRegis(@PathParam("id") int id, @PathParam("passwd") String pwd, 
            @PathParam("fname") String fname, @PathParam("lname") String lname,
            @PathParam("email") String email/*, @PathParam("phone") long phone,
            @PathParam("address") String addr*) {
        //TODO return proper representation object
           
        System.out.println("/********** ADMIN REGISTRATION *************");
        
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
    }*/
    
    
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
            String sql1 = "INSERT INTO student_registration(student_id, decision, delete_request) VALUES (?, 'Pending', 0)";

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
    
    
    /********** STUDENT DELETE REQUEST *************/
    @GET
    @Path("DeleteStudent&{id}")
    @Produces("application/json")
    public String studentDelete(@PathParam("id") int id) {
        //TODO return proper representation object
           
        System.out.println("/********** STUDENT DELETE REQUEST *************/");
        
        try {
            createConnection();
            String sql = "INSERT INTO student_registration(student_id, decision, delete_request) VALUES (?, 'Pending', 1)";

            stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
            stm.close();

            mainObj.accumulate("Status", "OK");
            mainObj.accumulate("Timestamp", epoc);
            mainObj.accumulate("Message", "Delete request sent");

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

            String sql = "SELECT student_id, concat(first_name, ' ', last_name) as name, email, passwd, delete_request FROM student_registration inner join person where student_id = id"
                    + " and decision = 'Pending'";
            
            stm = con.prepareStatement(sql);            
            ResultSet rs = stm.executeQuery();
            
            int id, del_req;
            String name, email, passwd;
            if(rs.next() == true){
                do{
                    id = rs.getInt(1);
                    del_req = rs.getInt(5);
                    name = rs.getString(2);
                    email = rs.getString(3);
                    passwd = rs.getString(4);
                    
                    childObj.accumulate("StudentId", id);
                    childObj.accumulate("StudentDelete", del_req);
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
    
    
    /**************** STUDENT DELETE ACCEPT ****************/
    @GET
    @Path("StudentDeleteAccept&{student_id}")
    @Produces("application/json")
    public String studentDelAccept(@PathParam("student_id") int student_id) {
        //TODO return proper representation object
           
        System.out.println("/********** STUDENT DELETE ACCEPT*************/");
        
        try {
            createConnection();

            String sql = "delete from message_recepient where to_student_id = ?";
            String sql1 = "delete from message where from_student_id = ?";
            String sql2 = "delete from order_details "
                    + "where posted_student_id = ? or borrowed_student_id = ?";
            String sql3 = "delete from favorite_tools "
                    + "where posted_student_id = ? or logged_student_id = ?";
            String sql4 = "delete from student_tools where posted_student_id = ?";
            String sql5 = "delete from student_registration "
                    + "where student_id = ?";
            String sql6 = "delete from student where student_id = ?";
            String sql7 = "delete from person where id = ?";

            stm = con.prepareStatement(sql);
            stm.setInt(1, student_id);
            stm.executeUpdate();
            stm.close();
            
            stm = con.prepareStatement(sql1);
            stm.setInt(1, student_id);
            stm.executeUpdate();
            stm.close();
            
            stm = con.prepareStatement(sql2);
            stm.setInt(1, student_id);
            stm.setInt(2, student_id);
            stm.executeUpdate();
            stm.close();
            
            stm = con.prepareStatement(sql3);
            stm.setInt(1, student_id);
            stm.setInt(2, student_id);
            stm.executeUpdate();
            stm.close();
            
            stm = con.prepareStatement(sql4);
            stm.setInt(1, student_id);
            stm.executeUpdate();
            stm.close();
            
            stm = con.prepareStatement(sql5);
            stm.setInt(1, student_id);
            stm.executeUpdate();
            stm.close();
            
            stm = con.prepareStatement(sql6);
            stm.setInt(1, student_id);
            stm.executeUpdate();
            stm.close();
            
            stm = con.prepareStatement(sql7);
            stm.setInt(1, student_id);
            stm.executeUpdate();
            stm.close();

            mainObj.accumulate("Status", "OK");
            mainObj.accumulate("Timestamp", epoc);
            mainObj.accumulate("Message", "Account Deleted");

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

            /*String sql = "select st.posted_student_id, t.*, coalesce(od.to_date, ''), coalesce(od.from_date, ''), st.rating,\n" +
                "DATEDIFF(coalesce(od.from_date, current_date), current_date) availableTill,\n" +
                "DATEDIFF(coalesce(od.to_date, current_date), current_date) availableIn,\n" +
                "st.availability, f.favorite, f.logged_student_id\n" +
                "from student_tools st left outer join order_details od on od.posted_student_id = st.posted_student_id\n" +
                "and st.posted_tool_id = od.posted_tool_id\n" +
                "right outer join tools t\n" +
                "on st.posted_tool_id = t.tool_id\n" +
                "left outer join favorite_tools f\n" +
                "on f.posted_tool_id = t.tool_id\n" +
                "and f.posted_student_id = st.posted_student_id\n" +
                "order by availability desc, tool_name";*/
            String sql = "select distinct st.posted_student_id, t.*, coalesce(od.to_date, ''), coalesce(od.from_date, ''), st.rating avg_rating,  \n" +
"                DATEDIFF(coalesce(od.from_date, current_date), current_date) availableTill,  \n" +
"                DATEDIFF(coalesce(od.to_date, current_date), current_date) availableIn,  \n" +
"                st.availability, f.favorite, f.logged_student_id\n" +
"                from student_tools st left outer join (select posted_tool_id, posted_student_id, borrowed_student_id, max(from_date) from_date, max(to_date) to_date, max(order_id) from order_details\n" +
"				group by 1,2,3) od on od.posted_student_id = st.posted_student_id  \n" +
"                and st.posted_tool_id = od.posted_tool_id  \n" +
"                right outer join tools t  \n" +
"                on st.posted_tool_id = t.tool_id  \n" +
"                left outer join favorite_tools f  \n" +
"                on f.posted_tool_id = t.tool_id  \n" +
"                and f.posted_student_id = st.posted_student_id \n" +
"             --  where DATEDIFF(coalesce(od.to_date, current_date), current_date) >= 0 \n" +
"                order by availability desc, tool_name";
            
            stm = con.prepareStatement(sql);           
            ResultSet rs = stm.executeQuery();
            
            int psid, ptid, lsid, availabletill, availablefrom, availability, fav;
            float trating, student_rating;
            String fd, td, name, desc, img, student_comments;
            if(rs.next() == true){
                do{
                    psid = rs.getInt("posted_student_id");
                    ptid = rs.getInt("tool_id");
                    lsid = rs.getInt("logged_student_id");
                    name = rs.getString("tool_name");
                    desc = rs.getString("tool_desc");
                    img = rs.getString("tool_img");
                    td = rs.getString(6);                        
                    //System.out.println("return date: " + rd);
                    fd = rs.getString(7);
                    trating = rs.getFloat("avg_rating");
                    availabletill = rs.getInt(9);
                    availablefrom = rs.getInt(10);
                    availability = rs.getInt(11);
                    fav = rs.getInt(12);
                    //student_rating = rs.getFloat("sid_rating");
                    //student_comments = rs.getString("sid_comments");
                    
                    childObj.accumulate("PostedStudentId", psid);
                    childObj.accumulate("LoggedStudentId", lsid);
                    childObj.accumulate("PostedToolId", ptid);
                    childObj.accumulate("ToolName", name);                    
                    childObj.accumulate("ToolDesc", desc);
                    childObj.accumulate("ToolImg", img);
                    childObj.accumulate("FromDate", fd);
                    childObj.accumulate("ReturnDate", td);
                    childObj.accumulate("ToolRating", trating);
                    childObj.accumulate("ToolAvailableTillInDays", availabletill);
                    childObj.accumulate("ToolAvailableFromInDays", availablefrom);
                    childObj.accumulate("ToolAvailability", availability);
                    childObj.accumulate("ToolFavorite", fav);
                    //childObj.accumulate("StudentToolRating", student_rating);
                    //childObj.accumulate("StudentToolComment", student_comments);
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
                    + " borrowed_student_id, from_date, to_date, returned, penalty) "
                    + "VALUES (?, ?, ?, ?, ?, 0, 0)";
            
            stm = con.prepareStatement(sql);
            stm.setInt(1, ptid);
            stm.setInt(2, psid);
            stm.setInt(3, bsid);
            stm.setString(4, fd);
            stm.setString(5, td);
            //stm.setString(6, td);
            
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

            String sql = "INSERT INTO student_tools(posted_student_id, posted_tool_id, availability) VALUES (?, ?, 1)";

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
           
        System.out.println("/********** MY TOOLS LIST *************/");
        
        try {
            createConnection();

            String sql = "select * from tools t inner join student_tools st"
                    + " on t.tool_id = st.posted_tool_id\n" +
                        "where st.posted_student_id = ? order by 2";
            
            stm = con.prepareStatement(sql);  
            stm.setInt(1,psid);
            ResultSet rs = stm.executeQuery();
            
            int id, avail;
            String name, desc, img;
            if(rs.next() == true){
                do{
                    id = rs.getInt(1);
                    name = rs.getString(2);
                    desc = rs.getString(3);
                    img = rs.getString(4);
                    avail = rs.getInt(7);
                    
                    childObj.accumulate("ToolId", id);
                    childObj.accumulate("ToolName", name);                    
                    childObj.accumulate("ToolDesc", desc);
                    childObj.accumulate("ToolImg", img);
                    childObj.accumulate("ToolAvailability", avail);
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
    
    
    /********** UPDATE MY TOOLS AVAILABILITY *************/
    @GET
    @Path("UpdateMyToolAvail&{psid}&{ptid}&{avail}")
    @Produces("application/json")
    public String getUpdateMyToolAvail(@PathParam("psid") int psid,
            @PathParam("ptid") int ptid, @PathParam("avail") int avail) {
        //TODO return proper representation object
           
        System.out.println("/********** UPDATE MY TOOLS AVAILABILITY *************/");
        
        try {
            createConnection();

            String sql = "update student_tools\n" +
                        "set availability = ?\n" +
                        "where posted_student_id = ?\n" +
                        "and posted_tool_id = ?";
            
            stm = con.prepareStatement(sql);  
            stm.setInt(3,ptid);
            stm.setInt(2,psid);
            stm.setInt(1,avail);
            stm.executeUpdate();
                
            mainObj.accumulate("Status", "Ok");
            mainObj.accumulate("Timestamp", epoc);
            mainObj.accumulate("Message", "Updated Successfully!");

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
    
    
    /********** BORROWED TOOLS LIST *************/
    @GET
    @Path("BorrowedToolsList&{bsid}")
    @Produces("application/json")
    public String getBorrowedToolsList(@PathParam("bsid") int bsid) {
        //TODO return proper representation object
           
        System.out.println("/********** BORROWED TOOLS LIST *************/");
        
        try {
            createConnection();

            /*String sql = "select * from tools t inner join order_details od\n" +
                        "on od.posted_tool_id = t.tool_id\n" +
                        "where borrowed_student_id = ?";*/
            String sql = "select t.*, od.*, st.rating from tools t inner join order_details od\n" +
                "on od.posted_tool_id = t.tool_id\n" +
                "left outer join student_tools st\n" +
                "on st.posted_tool_id = t.tool_id\n" +
                "and od.posted_student_id = st.posted_student_id\n" +
                "where borrowed_student_id = ?"
                    + " and returned = 0";
            
            stm = con.prepareStatement(sql);  
            stm.setInt(1,bsid);
            ResultSet rs = stm.executeQuery();
            
            int id, order_id, psid, returned;
            float rating, penalty;
            String name, desc, img, return_date;
            if(rs.next() == true){
                do{
                    id = rs.getInt("tool_id");
                    name = rs.getString("tool_name");
                    desc = rs.getString("tool_desc");
                    img = rs.getString("tool_img");
                    order_id = rs.getInt("order_id");
                    psid = rs.getInt("posted_student_id");
                    return_date = rs.getString("to_date");
                    returned = rs.getInt("returned");
                    penalty = rs.getFloat("penalty");
                    rating = rs.getFloat("rating");
                    
                    childObj.accumulate("ToolId", id);
                    childObj.accumulate("ToolName", name);                    
                    childObj.accumulate("ToolDesc", desc);
                    childObj.accumulate("ToolImg", img);
                    childObj.accumulate("ReturnDate", return_date);
                    childObj.accumulate("ToolRating", rating);
                    childObj.accumulate("ToolPenalty", penalty);
                    childObj.accumulate("ToolReturned", returned);
                    childObj.accumulate("PostedStudentId", psid);
                    childObj.accumulate("ToolOrderId", order_id);
                    jSONArray.add(childObj);  
                    childObj.clear();
                }while(rs.next());
                
                mainObj.accumulate("Status", "Ok");
                mainObj.accumulate("Timestamp", epoc);
                mainObj.accumulate("ToolsList", jSONArray);
            }  else{
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
    
    
    /********** BORROWED TOOLS LIST HISTORY *************/
    @GET
    @Path("BorrowedToolsListHistory&{bsid}")
    @Produces("application/json")
    public String getBorrowedToolsListHistory(@PathParam("bsid") int bsid) {
        //TODO return proper representation object
           
        System.out.println("/********** BORROWED TOOLS LIST HISTORY *************/");
        
        try {
            createConnection();

            /*String sql = "select * from tools t inner join order_details od\n" +
                        "on od.posted_tool_id = t.tool_id\n" +
                        "where borrowed_student_id = ?";*/
            String sql = "select t.*, od.*, st.rating from tools t inner join order_details od\n" +
                "on od.posted_tool_id = t.tool_id\n" +
                "left outer join student_tools st\n" +
                "on st.posted_tool_id = t.tool_id\n" +
                "and od.posted_student_id = st.posted_student_id\n" +
                "where borrowed_student_id = ?"
                    + " and returned = 2 "
                    + "and penalty = 0";
            
            stm = con.prepareStatement(sql);  
            stm.setInt(1,bsid);
            ResultSet rs = stm.executeQuery();
            
            int id, psid, returned, order_id;
            float rating, penalty;
            String name, desc, img, return_date;
            if(rs.next() == true){
                do{                    
                    id = rs.getInt("tool_id");
                    name = rs.getString("tool_name");
                    desc = rs.getString("tool_desc");
                    img = rs.getString("tool_img");
                    order_id = rs.getInt("order_id");
                    psid = rs.getInt("posted_student_id");
                    return_date = rs.getString("to_date");
                    returned = rs.getInt("returned");
                    penalty = rs.getFloat("penalty");
                    rating = rs.getFloat("rating");
                    
                    childObj.accumulate("ToolId", id);
                    childObj.accumulate("ToolName", name);
                    childObj.accumulate("ToolOrderId", order_id);
                    childObj.accumulate("ToolDesc", desc);
                    childObj.accumulate("ToolImg", img);
                    childObj.accumulate("ReturnDate", return_date);
                    childObj.accumulate("ToolRating", rating);
                    childObj.accumulate("ToolPenalty", penalty);
                    childObj.accumulate("ToolReturned", returned);
                    childObj.accumulate("PostedStudentId", psid);
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
    
    
    /********** BORROWED TOOLS LIST REVIEW *************/
    @GET
    @Path("BorrowedToolsListReview&{psid}")
    @Produces("application/json")
    public String getBorrowedToolsListReview(@PathParam("psid") int psid) {
        //TODO return proper representation object
           
        System.out.println("/********** BORROWED TOOLS LIST REVIEW *************/");
        
        try {
            createConnection();

            /*String sql = "select * from tools t inner join order_details od\n" +
                        "on od.posted_tool_id = t.tool_id\n" +
                        "where borrowed_student_id = ?";*/
            String sql = "select t.*, od.*, st.rating from tools t inner join order_details od\n" +
                "on od.posted_tool_id = t.tool_id\n" +
                "left outer join student_tools st\n" +
                "on st.posted_tool_id = t.tool_id\n" +
                "and od.posted_student_id = st.posted_student_id\n" +
                "where st.posted_student_id = ?"
                    + " and returned = 1";
            
            stm = con.prepareStatement(sql);  
            stm.setInt(1,psid);
            ResultSet rs = stm.executeQuery();
            
            int id, bsid, returned, order_id;
            float rating, penalty;
            String name, desc, img, return_date;
            if(rs.next() == true){
                do{
                    id = rs.getInt("tool_id");
                    name = rs.getString("tool_name");
                    desc = rs.getString("tool_desc");
                    img = rs.getString("tool_img");
                    order_id = rs.getInt("order_id");
                    bsid = rs.getInt("borrowed_student_id");
                    return_date = rs.getString("to_date");
                    returned = rs.getInt("returned");
                    penalty = rs.getFloat("penalty");
                    rating = rs.getFloat("rating");
                    
                    childObj.accumulate("ToolId", id);
                    childObj.accumulate("ToolOrderId", order_id);
                    childObj.accumulate("ToolName", name);                    
                    childObj.accumulate("ToolDesc", desc);
                    childObj.accumulate("ToolImg", img);
                    childObj.accumulate("ReturnDate", return_date);
                    childObj.accumulate("ToolRating", rating);
                    childObj.accumulate("ToolPenalty", penalty);
                    childObj.accumulate("ToolReturned", returned);
                    childObj.accumulate("PostedStudentId", psid);
                    childObj.accumulate("BorrowStudentId", bsid);
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
    
    
    /********** BORROWED TOOLS PENALTY *************/
    @GET
    @Path("BorrowedToolsPenalty&{bsid}")
    @Produces("application/json")
    public String getBorrowedToolsPenalty(@PathParam("bsid") int bsid) {
        //TODO return proper representation object
           
        System.out.println("/********** BORROWED TOOLS PENALTY *************/");
        
        try {
            createConnection();

            /*String sql = "select * from tools t inner join order_details od\n" +
                        "on od.posted_tool_id = t.tool_id\n" +
                        "where borrowed_student_id = ?";*/
            String sql = "select t.*, od.*, st.rating from tools t inner join order_details od\n" +
                "on od.posted_tool_id = t.tool_id\n" +
                "left outer join student_tools st\n" +
                "on st.posted_tool_id = t.tool_id\n" +
                "and od.posted_student_id = st.posted_student_id\n" +
                "where od.borrowed_student_id = ?"
                    + " and penalty > 0";
            
            stm = con.prepareStatement(sql);  
            stm.setInt(1,bsid);
            ResultSet rs = stm.executeQuery();
            
            int id, psid, returned, order_id;
            float rating, penalty;
            String name, desc, img, return_date;
            if(rs.next() == true){
                do{
                    id = rs.getInt("tool_id");
                    name = rs.getString("tool_name");
                    desc = rs.getString("tool_desc");
                    img = rs.getString("tool_img");
                    psid = rs.getInt("posted_student_id");
                    return_date = rs.getString("to_date");
                    returned = rs.getInt("returned");
                    penalty = rs.getFloat("penalty");
                    rating = rs.getFloat("rating");
                    order_id = rs.getInt("order_id");
                    
                    childObj.accumulate("ToolId", id);
                    childObj.accumulate("ToolName", name);                    
                    childObj.accumulate("ToolDesc", desc);
                    childObj.accumulate("ToolImg", img);
                    childObj.accumulate("ReturnDate", return_date);
                    childObj.accumulate("ToolRating", rating);
                    childObj.accumulate("ToolPenalty", penalty);
                    childObj.accumulate("ToolReturned", returned);
                    childObj.accumulate("ToolOrderId", order_id);
                    childObj.accumulate("PostedStudentId", psid);
                    childObj.accumulate("BorrowStudentId", bsid);
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
    
    
    /********** BORROW TOOL REVIEW *************/
    @GET
    @Path("BorrowToolReview&{order_id}")
    @Produces("application/json")
    public String getBorrowToolReview(@PathParam("order_id") int order_id) {
        //TODO return proper representation object
           
        System.out.println("/********** BORROW TOOL REVIEW *************/");
        
        try {
            createConnection();

            /*String sql = "delete from order_details "
                    + "where posted_tool_id = ? and borrowed_student_id = ?";*/
            
            String sql = "update order_details "
                    + "set returned = 1 "
                    + "where order_id = ?";
            
            stm = con.prepareStatement(sql);
            stm.setInt(1,order_id);
            stm.executeUpdate();
                
            mainObj.accumulate("Status", "Ok");
            mainObj.accumulate("Timestamp", epoc);
            mainObj.accumulate("Message", "Tool under review for damage!");

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
    
    
    /********** BORROW TOOL RETURN *************/
    @GET
    @Path("BorrowToolReturn&{order_id}&{penalty}")
    @Produces("application/json")
    public String getBorrowToolReturn(@PathParam("order_id") int order_id,
            @PathParam("penalty") String penalty) {
        //TODO return proper representation object
           
        System.out.println("/********** BORROW TOOL RETURN *************/");
        
        try {
            createConnection();

            /*String sql = "delete from order_details "
                    + "where posted_tool_id = ? and borrowed_student_id = ?";*/
            
            String sql = "update order_details "
                    + "set returned = 2, "
                    + "to_date = current_date, "
                    + "from_date = current_date, "
                    + "penalty = ? "
                    + "where order_id = ? ";
            
            stm = con.prepareStatement(sql);
            stm.setInt(2, order_id);
            stm.setFloat(1, Float.parseFloat(penalty));
            int n = stm.executeUpdate();
            
            if( n != 0){
            mainObj.accumulate("Status", "Ok");
            mainObj.accumulate("Timestamp", epoc);
            mainObj.accumulate("Message", "Return Accepted!");
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
    
    
    /********** UPDATE RATING *************/
    @GET
    @Path("UpdateRating&{psid}&{ptid}&{rating}&{ord_id}&{s_rat}&{s_com}")
    @Produces("application/json")
    public String getUpdateRating(@PathParam("psid") int psid,
            @PathParam("ptid") int ptid, @PathParam("rating") String rating,
            @PathParam("ord_id") int ord_id, @PathParam("s_rat") String s_rat,
            @PathParam("s_com") String s_com) {
        //TODO return proper representation object
           
        System.out.println("/********** UPDATE RATING *************/");
        
        try {
            createConnection();

            String sql = "update student_tools\n" +
                "set rating = ?\n" +
                "where posted_student_id = ?\n" +
                "and posted_tool_id = ?";
            
            String sql1 = "INSERT INTO tools_comment(order_id, rating, comments) VALUES (?, ?, ?)";

            stm = con.prepareStatement(sql);
            stm.setInt(2, psid);
            stm.setInt(3, ptid);
            stm.setFloat(1, Float.parseFloat(rating));
            stm.executeUpdate();
            stm.close();
            
            stm = con.prepareStatement(sql1);
            stm.setInt(1, ord_id);
            stm.setFloat(2, Float.parseFloat(s_rat));
            stm.setString(3, s_com);
            stm.executeUpdate();
            stm.close();
                
            mainObj.accumulate("Status", "Ok");
            mainObj.accumulate("Timestamp", epoc);
            mainObj.accumulate("Message", "Thanks for rating!");                          
            

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
    
    
    /********** ADD FAVORITE TOOLS *************/
    @GET
    @Path("AddFavoriteTools&{psid}&{ptid}&{lsid}")
    @Produces("application/json")
    public String getAddFavoritesToolsList(@PathParam("psid") int psid
            , @PathParam("ptid") int ptid, @PathParam("lsid") int lsid) {
        //TODO return proper representation object
           
        System.out.println("/********** ADD FAVORITE TOOLS *************/");
        
        try {
            createConnection();

            String sql = "INSERT INTO favorite_tools(posted_student_id, posted_tool_id, logged_student_id, favorite) VALUES (?, ?, ?, 1)";

            stm = con.prepareStatement(sql);
            stm.setInt(1, psid);
            stm.setInt(2, ptid);
            stm.setInt(3, lsid);
            stm.executeUpdate();
                
            mainObj.accumulate("Status", "Ok");
            mainObj.accumulate("Timestamp", epoc);
            mainObj.accumulate("Message", "Tool Added to Favorites!");                          
            

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
    
    /********** FAVORITE LIST *************/
    @GET
    @Path("FavoriteList&{lsid}")
    @Produces("application/json")
    public String getFavoriteList(@PathParam("lsid") int lsid) {
        //TODO return proper representation object
           
        System.out.println("/********** FAVORITE LIST *************/");
        
        try {
            createConnection();

            String sql = "select distinct st.posted_student_id, t.*, coalesce(od.to_date, ''), coalesce(od.from_date, ''), st.rating,\n" +
                "DATEDIFF(coalesce(od.from_date, current_date), current_date) availableTill,\n" +
                "DATEDIFF(coalesce(od.to_date, current_date), current_date) availableIn,\n" +
                "st.availability, f.favorite\n" +
                "from student_tools st left outer join (select posted_tool_id, "
                    + "posted_student_id, borrowed_student_id, max(from_date) from_date, "
                    + "max(to_date) to_date, max(order_id) from order_details\n" +
"				group by 1,2,3) od "
                    + "on od.posted_student_id = st.posted_student_id\n" +
                "and st.posted_tool_id = od.posted_tool_id\n" +
                "right outer join tools t\n" +
                "on st.posted_tool_id = t.tool_id\n" +
                "left outer join favorite_tools f\n" +
                "on f.posted_tool_id = t.tool_id\n" +
                "and f.posted_student_id = st.posted_student_id\n" +
                "where f.favorite = 1 and f.logged_student_id = ? "
                    + "order by availability desc, tool_name ";
            
            stm = con.prepareStatement(sql); 
            stm.setInt(1, lsid);
            ResultSet rs = stm.executeQuery();
            
            int psid, ptid, availabletill, availablefrom, availability, fav;
            float trating;
            String fd, rd, name, desc, img;
            if(rs.next() == true){
                do{
                    psid = rs.getInt(1);
                    ptid = rs.getInt(2);
                    name = rs.getString(3);
                    desc = rs.getString(4);
                    img = rs.getString(5);
                    rd = rs.getString(6);                        
                    System.out.println("return date: " + rd);
                    fd = rs.getString(7);
                    trating = rs.getFloat(8);
                    availabletill = rs.getInt(9);
                    availablefrom = rs.getInt(10);
                    availability = rs.getInt(11);
                    fav = rs.getInt(12);
                    
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
                    childObj.accumulate("ToolAvailability", availability);
                    childObj.accumulate("ToolFavorite", fav);
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
    
    /********** REMOVE FAVORITE *************/
    @GET
    @Path("RemoveFavorite&{psid}&{ptid}&{lsid}")
    @Produces("application/json")
    public String getRemoveFavorite(@PathParam("psid") int psid,
            @PathParam("ptid") int ptid, @PathParam("lsid") int lsid) {
        //TODO return proper representation object
           
        System.out.println("/********** REMOVE FAVORITE *************/");
        
        try {
            createConnection();

            String sql = "delete from favorite_tools where "
                    + "posted_student_id = ? and "
                    + "posted_tool_id = ? and "
                    + "logged_student_id = ?";

            stm = con.prepareStatement(sql);
            
            stm.setInt(1, psid);
            stm.setInt(2, ptid);
            stm.setInt(3, lsid);
            stm.executeUpdate();
                
            mainObj.accumulate("Status", "Ok");
            mainObj.accumulate("Timestamp", epoc);
            mainObj.accumulate("Message", "Tool Removed from Favorites!"); 
            

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
    
    
    /********** NEW MESSAGE *************/
    @GET
    @Path("NewMessage&{fsid}&{tsid}&{message}")
    @Produces("application/json")
    public String getNewMessage(@PathParam("fsid") int fsid, @PathParam("tsid") int tsid, 
            @PathParam("message") String message) {
        //TODO return proper representation object
           
        System.out.println("/********** NEW MESSAGE *************/");
        
        try {
            createConnection();

            String sql = "INSERT INTO message(message, sent_date, from_student_id) "
                    + "VALUES (?, CURRENT_TIMESTAMP, ?)";
            String sql1 = "INSERT INTO message_recepient (message_id, to_student_id)"
                    + " VALUES ((select message_id from message "
                    + "where sent_date in (select max(sent_date) from message "
                    + "where from_student_id = ?)), ?)";

            stm = con.prepareStatement(sql);
            stm.setString(1, message);
            stm.setInt(2, fsid);
            stm.executeUpdate();
            stm.close();

            stm = con.prepareStatement(sql1);
            stm.setInt(1, fsid);
            stm.setInt(2, tsid);
            stm.executeUpdate();
            stm.close();

            mainObj.accumulate("Status", "OK");
            mainObj.accumulate("Timestamp", epoc);
            mainObj.accumulate("Message", "Message sent!");

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
    
    
    /********** MY Messages *************/
    @GET
    @Path("MyMessages&{sid}")
    @Produces("application/json")
    public String getMyMessages(@PathParam("sid") int sid) {
        //TODO return proper representation object
           
        System.out.println("/********** MY Messages *************/");
        
        try {
            createConnection();

            String sql = "select m.message_id, m.message, "
                    + "m.from_student_id, mr.to_student_id, m.sent_date "
                    + "from message m inner join message_recepient mr "
                    + "on m.message_id = mr.message_id "
                    + "where sent_date in (select max(sent_date) "
                    + "from message m inner join message_recepient mr "
                    + "on m.message_id = mr.message_id "
                    + "group by m.from_student_id, mr.to_student_id "
                    + "having to_student_id = ? "
                    + ") order by sent_date desc";
            
            stm = con.prepareStatement(sql);  
            stm.setInt(1,sid);
            //stm.setInt(2,sid);
            ResultSet rs = stm.executeQuery();
            
            int message_id, from_student_id, to_student_id;
            String message, sent_date;
            if(rs.next() == true){
                do{
                    message_id = rs.getInt("message_id");
                    from_student_id = rs.getInt("from_student_id");
                    to_student_id = rs.getInt("to_student_id");
                    message = rs.getString("message");
                    sent_date = rs.getString("sent_date");
                    
                    childObj.accumulate("MessageId", message_id);
                    childObj.accumulate("FromStudentId", from_student_id);                    
                    childObj.accumulate("Message", message);
                    childObj.accumulate("SentDate", sent_date);
                    childObj.accumulate("ToStudentId", to_student_id);
                    jSONArray.add(childObj);  
                    childObj.clear();
                }while(rs.next());
                
                mainObj.accumulate("Status", "Ok");
                mainObj.accumulate("Timestamp", epoc);
                mainObj.accumulate("MyMessages", jSONArray);
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
    
    
    /********** MessageDetails *************/
    @GET
    @Path("MessageDetails&{tsid}&{fsid}")
    @Produces("application/json")
    public String getMessageDetails(@PathParam("tsid") int tsid, @PathParam("fsid") int fsid) {
        //TODO return proper representation object
           
        System.out.println("/********** MessageDetails *************/");
        
        try {
            createConnection();

            String sql = "select m.message_id, m.message, "
                    + "m.from_student_id, mr.to_student_id "
                    + "from message m inner join message_recepient mr "
                    + "on m.message_id = mr.message_id "
                    + "where mr.to_student_id = ? "
                    + "and m.from_student_id = ? "
                    + "or mr.to_student_id = ? "
                    + "and m.from_student_id = ? "
                    + "order by sent_date";
            
            stm = con.prepareStatement(sql);  
            stm.setInt(1,tsid);
            stm.setInt(2,fsid);
            stm.setInt(3,fsid);
            stm.setInt(4,tsid);
            ResultSet rs = stm.executeQuery();
            
            int message_id, from_student_id, to_student_id;
            String message, id;
            if(rs.next() == true){
                do{
                    message_id = rs.getInt("message_id");
                    from_student_id = rs.getInt("from_student_id");
                    to_student_id = rs.getInt("to_student_id");
                    message = rs.getString("message");
                    //id = rs.getString("id");
                    
                    childObj.accumulate("MDMessageId", message_id);
                    childObj.accumulate("MDFromStudentId", from_student_id);                    
                    childObj.accumulate("MDMessage", message);
                    //childObj.accumulate("MDId", id);
                    childObj.accumulate("MDToStudentId", to_student_id);
                    jSONArray.add(childObj);  
                    childObj.clear();
                }while(rs.next());
                
                mainObj.accumulate("Status", "Ok");
                mainObj.accumulate("Timestamp", epoc);
                mainObj.accumulate("MessageDetails", jSONArray);
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
    
    
    /********** COMMENTS LIST *************/
    @GET
    @Path("CommentsList&{ptid}")
    @Produces("application/json")
    public String getCommentsList(@PathParam("ptid") int ptid) {
        //TODO return proper representation object
           
        System.out.println("/********** COMMENTS LIST *************/");
        
        try {
            createConnection();

            String sql = "select * from tools_comment tc inner join order_details od\n" +
                    "on tc.order_id = od.order_id\n" +
                    "where posted_tool_id = ? "
                    + "and trim(comments) <> 'No Comments'";
            
            stm = con.prepareStatement(sql);  
            stm.setInt(1,ptid);
            ResultSet rs = stm.executeQuery();
            
            int bsid;
            String comment;
            float rating;
            if(rs.next() == true){
                do{
                    bsid = rs.getInt("borrowed_student_id");
                    comment = rs.getString("comments");
                    rating = rs.getFloat("rating");
                    
                    childObj.accumulate("CommentedStudentId", bsid);
                    childObj.accumulate("Comment", comment);                    
                    childObj.accumulate("GivenRating", rating);
                    jSONArray.add(childObj);  
                    childObj.clear();
                }while(rs.next());
                
                mainObj.accumulate("Status", "Ok");
                mainObj.accumulate("Timestamp", epoc);
                mainObj.accumulate("CommentsList", jSONArray);
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
    
    
    /****************** CREATE CONNECTION ************************/
    
    private static void createConnection() throws ClassNotFoundException, SQLException {
        //Class.forName("com.mysql.jdbc.Driver");
        Class.forName("com.mysql.cj.jdbc.Driver");
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
