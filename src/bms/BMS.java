package bms;

import bms.controller.LoginController;
import bms.model.employeeDAO;
import bms.view.LoginView;
import bms.view.EmpSideView;
import bms.view.AdminDashboardView;

public class BMS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        LoginView loginView = new LoginView();
        employeeDAO dao = new employeeDAO();
        new LoginController(loginView, dao);
        
        
        
        
        
    }
    
}
    