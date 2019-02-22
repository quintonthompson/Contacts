package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.datamodel.Contact;
import sample.datamodel.ContactData;

import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;


public class Controller {

    @FXML
    private TableView<Contact> tableView;
    @FXML
    private BorderPane mainBorderPane;

    private FilteredList<Contact> filteredList;

    private Predicate<Contact> wantAllContacts;

    private ContactData data;

    public void initialize() {
        data = new ContactData();
        data.loadContacts();

        wantAllContacts = new Predicate<Contact>() {
            @Override
            public boolean test(Contact contact) {
                return true;
            }
        };

        filteredList = new FilteredList<Contact>(data.getContacts(), wantAllContacts);

        SortedList<Contact> sortedList = new SortedList<Contact>(filteredList, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getFirstName().compareTo(o2.getFirstName());//use date comparator method
            }
        });

        tableView.setItems(data.getContacts());


    }

    //for testing table view
    public ObservableList<Contact> getContacts() {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        contacts.add(new Contact("Quinton", "Thompson", "1234", "test1"));
        contacts.add(new Contact("Bob", "Martin", "5678", "test2"));
        return contacts;
    }


    @SuppressWarnings("Duplicates")
    @FXML
    public void showNewContactDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add Contact");
        dialog.setHeaderText("Use this dialog to create a contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactdialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load dialog");
            e.printStackTrace();
            return;
        }


        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            Contact newContact = controller.addNewContact();
            data.addContact(newContact);
            tableView.getSelectionModel().select(newContact);
            data.saveContacts();
        }
    }

    @SuppressWarnings("Duplicates")
    @FXML
    public void editContact() {
        Contact selectedContact = tableView.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Contact Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the contact you want to edit.");
            alert.showAndWait();
            return;
        }
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Edit Contact");
        dialog.setHeaderText("Use this dialog to edit a contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactdialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {
            System.out.println("Couldnt load dialog");
            e.printStackTrace();
            return;
        }


        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        DialogController controller = fxmlLoader.getController();
        controller.editContact(selectedContact);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            controller.updateContact(selectedContact);
            tableView.getSelectionModel().select(selectedContact);
            data.saveContacts();
        }


    }

    @FXML
    public void deleteContact() {
        Contact contact = tableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Contact");
        alert.setHeaderText("Delete Contact: " + contact.getFirstName());
        alert.setContentText("Are you sure? Press OK to confirm or cancel.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            data.deleteContact(contact);
            data.saveContacts();
            System.out.println("Contact: " + contact.getFirstName() + " was deleted");
        }
    }

}

