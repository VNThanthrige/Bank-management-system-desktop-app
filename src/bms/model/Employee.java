package bms.model;

public class Employee {
    private int id;
    private String name;
    private String role;
    private double salary;
    private String branch;
    private String userName;
    private String password;
    private int age;
    private String phoneNumber;

    public Employee() {}

    public Employee(int id, String name, String role, double salary, String branch,
                    String userName, String password, int age, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.salary = salary;
        this.branch = branch;
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
