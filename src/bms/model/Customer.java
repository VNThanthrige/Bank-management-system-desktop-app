package bms.model;

public class Customer {
    private int id;
    private String name;
    private double saving;       // new field
    private String cardNo;
    private String userName;
    private String password;
    private int age;
    private String phoneNumber;
    private String loanStatus;  // e.g., "Pending", "Approved", "Rejected"

    public Customer() {}

    public Customer(int id, String name, double saving, String cardNo, String userName,
                    String password, int age, String phoneNumber, String loanStatus) {
        this.id = id;
        this.name = name;
        this.saving = saving;
        this.cardNo = cardNo;
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.loanStatus = loanStatus;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getSaving() { return saving; }
    public void setSaving(double saving) { this.saving = saving; }

    public String getCardNo() { return cardNo; }
    public void setCardNo(String cardNo) { this.cardNo = cardNo; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getLoanStatus() { return loanStatus; }
    public void setLoanStatus(String loanStatus) { this.loanStatus = loanStatus; }
}
