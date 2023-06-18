import java.io.Serializable;
import java.util.Date;
public class Student implements Serializable {
    private String name;
    private int rollNumber;
    private String branch;
    private long phoneNumber;
    private double feePayment;
    private Date validity;

    public Student(String name, int rollNumber, String branch, long phoneNumber, double feePayment, Date validity) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.branch = branch;
        this.phoneNumber = phoneNumber;
        this.feePayment = feePayment;
        this.validity = validity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getFeePayment() {
        return feePayment;
    }

    public void setFeePayment(double feePayment) {
        this.feePayment = feePayment;
    }

    public Date isValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }


}
