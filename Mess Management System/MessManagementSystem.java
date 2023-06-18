import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
public class MessManagementSystem extends JFrame {
    private JLabel nameLabel, rollNumberLabel, branchLabel, phoneNumberLabel, feePaymentLabel, validityLabel;
    private JTextField nameField, rollNumberField, branchField, phoneNumberField, feePaymentField, validityField;
    private JButton addButton, viewButton, searchButton, updateButton, deleteButton;
    private JTextArea outputArea;
    private ArrayList<Student> studentList;

    public MessManagementSystem() {
        setTitle("Student Mess Management System");
        setVisible(true);
        setSize(600, 400);

        setLayout(new FlowLayout());

        nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        rollNumberLabel = new JLabel("Roll Number:");
        rollNumberField = new JTextField(10);
        branchLabel = new JLabel("Branch:");
        branchField = new JTextField(10);
        phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberField = new JTextField(10);
        feePaymentLabel = new JLabel("Fee Payment:");
        feePaymentField = new JTextField(10);
        validityLabel = new JLabel("Validity:");
        validityField = new JTextField(5);

        addButton = new JButton("Add");
        viewButton = new JButton("View");
        searchButton = new JButton("Search");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        outputArea = new JTextArea(15, 50);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(nameLabel);
        add(nameField);
        add(rollNumberLabel);
        add(rollNumberField);
        add(branchLabel);
        add(branchField);
        add(phoneNumberLabel);
        add(phoneNumberField);
        add(feePaymentLabel);
        add(feePaymentField);
        add(validityLabel);
        add(validityField);
        add(addButton);
        add(viewButton);
        add(searchButton);
        add(updateButton);
        add(deleteButton);
        add(scrollPane);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewStudentDetails();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchStudent();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateStudentDetails();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });

        loadDataFromFile();
    }

    private void addStudent() {
        String name = nameField.getText();
        int rollNumber = Integer.parseInt(rollNumberField.getText());
        String branch = branchField.getText();
        long phoneNumber = Long.parseLong(phoneNumberField.getText());
        double feePayment = Double.parseDouble(feePaymentField.getText());
        Date validity = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            validity = dateFormat.parse(validityField.getText());
        } catch (ParseException e) {

            JOptionPane.showMessageDialog(this, "Invalid validity date.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        Student student = new Student(name, rollNumber, branch, phoneNumber, feePayment, validity);
        studentList.add(student);

        nameField.setText("");
        rollNumberField.setText("");
        branchField.setText("");
        phoneNumberField.setText("");
        feePaymentField.setText("");
        validityField.setText("");

        saveDataToFile();
    }

    private void viewStudentDetails() {
        outputArea.setText("");
        for (Student student : studentList) {
            String details = "Name: " + student.getName() + "\n" +
                    "Roll Number: " + student.getRollNumber() + "\n" +
                    "Fee Payment: " + student.getFeePayment() + "\n" +
                    "-----------------------------\n";
            outputArea.append(details);
        }
    }

    private void searchStudent() {
        String searchQuery = JOptionPane.showInputDialog(this, "Enter roll number or name:");
        boolean found = false;

        for (Student student : studentList) {
            if (student.getName().equalsIgnoreCase(searchQuery) || student.getRollNumber() == Integer.parseInt(searchQuery)) {
                String details = "Name: " + student.getName() + "\n" +
                        "Roll Number: " + student.getRollNumber() + "\n" +
                        "Fee Payment: " + student.getFeePayment() + "\n" +
                        "-----------------------------\n";
                outputArea.setText(details);
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Student not found.", "Search", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateStudentDetails() {
        String searchQuery = JOptionPane.showInputDialog(this, "Enter roll number or name:");
        boolean found = false;

        for (Student student : studentList) {
            if (student.getName().equalsIgnoreCase(searchQuery) || student.getRollNumber() == Integer.parseInt(searchQuery)) {
                String newName = JOptionPane.showInputDialog(this, "Enter new name:");
                double newFeePayment = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter new fee payment:"));

                student.setName(newName);
                student.setFeePayment(newFeePayment);

                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Student not found.", "Update", JOptionPane.INFORMATION_MESSAGE);
        }

        saveDataToFile();
    }

    private void deleteStudent() {
        String searchQuery = JOptionPane.showInputDialog(this, "Enter roll number or name:");
        boolean found = false;

        for (int i = 0; i < studentList.size(); i++) {
            Student student = studentList.get(i);
            if (student.getName().equalsIgnoreCase(searchQuery) || student.getRollNumber() == Integer.parseInt(searchQuery)) {
                studentList.remove(i);
                found = true;
                break;
            }
        }

        if (found) {
            JOptionPane.showMessageDialog(this, "Student deleted successfully.", "Delete", JOptionPane.INFORMATION_MESSAGE);
            outputArea.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Student not found.", "Delete", JOptionPane.INFORMATION_MESSAGE);
        }

        saveDataToFile();
    }

    private void loadDataFromFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream("student_data.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            studentList = (ArrayList<Student>) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            studentList = new ArrayList<Student>();
        } catch (IOException e) {
            studentList = new ArrayList<Student>();
        } catch (ClassNotFoundException e) {
            studentList = new ArrayList<Student>();
        }
    }

    private void saveDataToFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("student_data.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(studentList);

            objectOutputStream.close();
            fileOutputStream.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        catch (IOException e) {
            System.err.println("An error occurred during file I/O: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MessManagementSystem().setVisible(true);
            }
        });

    }
}
