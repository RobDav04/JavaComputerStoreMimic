import java.util.ArrayList;

public class StaffType {
    private String username;
    private String password;
    private boolean isManager;
    protected static ArrayList<StaffType> userList = new ArrayList<>();

    public StaffType(String username, String password, boolean isManager) {
        this.username = username;
        this.password = password;
        this.isManager = isManager;
    }

    public String getPassword() { return password; }
    public String getUsername() { return username; }
    public Boolean getIsManager() { return isManager; }

    public static void addUsers() {
        userList.add(new StaffType("m1", "m1", true));
        userList.add(new StaffType("m2", "m2", true));
        userList.add(new StaffType("p1", "p1", false));
        userList.add(new StaffType("p2", "p2", false));
        userList.add(new StaffType("p3", "p3", false));
    }

    public static ArrayList<StaffType> getStaff() { return userList; }

}
