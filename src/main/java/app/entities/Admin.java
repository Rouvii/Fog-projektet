package app.entities;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou
 */
public class Admin {

    private int adminId;

    private boolean isAdmin;

    public Admin(int adminId, boolean isAdmin) {
        this.adminId = adminId;
        this.isAdmin = isAdmin;
    }


    public int getAdminId() {
        return adminId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
