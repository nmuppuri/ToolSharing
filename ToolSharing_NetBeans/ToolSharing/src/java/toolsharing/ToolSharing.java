/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolsharing;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;


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
