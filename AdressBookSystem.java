package ABS;

import java.io.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class AdressBookSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AddressBook addressBook = new AddressBook();
        String filename = "contacts.txt";
        System.out.println("Welcome to the Address Book System!");
        addressBook.loadContactsFromFile(filename);

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Add a new contact");
            System.out.println("2. Remove a contact");
            System.out.println("3. Search for a contact");
            System.out.println("4. Display all contacts");
            System.out.println("5. Save contacts to file");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline after nextInt()

            switch (choice) {
                case 1:
                    System.out.println("Enter name:");
                    String name = scanner.nextLine();
                    System.out.println("Enter phone number:");
                    String phoneNumber = scanner.nextLine();
                    System.out.println("Enter email address:");
                    String emailAddress = scanner.nextLine();

                    if (!name.isEmpty() && !phoneNumber.isEmpty() && !emailAddress.isEmpty()) {
                        Contact newContact = new Contact(name, phoneNumber, emailAddress);
                        addressBook.addContact(newContact);
                        System.out.println("Contact added successfully.");
                    } else {
                        System.out.println("Please fill in all required fields.");
                    }
                    break;
                case 2:
                    System.out.println("Enter the name of the contact to remove:");
                    String nameToRemove = scanner.nextLine();
                    Contact contactToRemove = addressBook.searchContact(nameToRemove);
                    if (contactToRemove != null) {
                        addressBook.removeContact(contactToRemove);
                        System.out.println("Contact removed successfully.");
                    } else {
                        System.out.println("Contact not found.");
                    }
                    break;
                case 3:
                    System.out.println("Enter the name of the contact to search:");
                    String nameToSearch = scanner.nextLine();
                    Contact foundContact = addressBook.searchContact(nameToSearch);
                    if (foundContact != null) {
                        System.out.println("Contact found: " + foundContact);
                    } else {
                        System.out.println("Contact not found.");
                    }
                    break;
                case 4:
                    addressBook.displayAllContacts();
                    break;
                case 5:
                    addressBook.saveContactsToFile(filename);
                    break;
                case 6:
                    // Save contacts before exiting
                    addressBook.saveContactsToFile(filename);
                    System.out.println("Exiting the Address Book System.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

class Contact implements Serializable {
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Contact(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    // Getters and setters for attributes
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + emailAddress;
    }
}

class AddressBook {
    private List<Contact> contacts;

    public AddressBook() {
        contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    public Contact searchContact(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }
    public void displayAllContacts() {
        if (contacts.isEmpty()) {
            System.out.println("Address book is empty.");
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }
    }
    public void saveContactsToFile(String filename) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(contacts);
            System.out.println("Contacts saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving contacts to file: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public void loadContactsFromFile(String filename) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            contacts = (List<Contact>) inputStream.readObject();
            System.out.println("Contacts loaded from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading contacts from file: " + e.getMessage());
        }
    }
}
