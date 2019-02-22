package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.datamodel.Contact;

public class DialogController {
    @FXML
    private TextField dialogTextFieldFName;
    @FXML
    private TextField dialogTextFieldLName;
    @FXML
    private TextField dialogTextFieldPNumber;
    @FXML
    private TextField dialogTextFieldNotes;

    private Contact newContact ;

    public Contact addNewContact(){
            newContact = new Contact(dialogTextFieldFName.getText(),
                    dialogTextFieldLName.getText(),
                    dialogTextFieldPNumber.getText(),
                    dialogTextFieldNotes.getText());
       return newContact;
    }

    public void editContact(Contact contact) {
        setDialogTextFieldFName(contact.getFirstName());
        setDialogTextFieldLName(contact.getLastName());
        setDialogTextFieldPNumber(contact.getPhoneNumber());
        setDialogTextFieldNotes(contact.getNotes());
    }

    public void updateContact(Contact contact){
            contact.setFirstName(dialogTextFieldFName.getText());
            contact.setLastName(dialogTextFieldLName.getText());
            contact.setPhoneNumber(dialogTextFieldPNumber.getText());
            contact.setNotes(dialogTextFieldNotes.getText());
    }


    public void setDialogTextFieldFName(String fName) {
        this.dialogTextFieldFName.setText(fName);
    }

    public void setDialogTextFieldLName(String lName) {
        this.dialogTextFieldLName.setText(lName);
    }

    public void setDialogTextFieldPNumber(String phoneNumber) {
        this.dialogTextFieldPNumber.setText(phoneNumber);
    }
    public void setDialogTextFieldNotes(String notes) {
        this.dialogTextFieldNotes.setText(notes);
    }
}
