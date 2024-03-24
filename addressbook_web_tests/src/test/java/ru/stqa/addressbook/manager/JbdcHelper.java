package ru.stqa.addressbook.manager;

import ru.stqa.addressbook.model.ContactData;
import ru.stqa.addressbook.model.GroupData;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JbdcHelper extends HelperBase {
    public JbdcHelper(ApplicationManager manager) {
        super(manager);
    }

    public List<GroupData> getGroupList() {
        var groups = new ArrayList<GroupData>();
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root", "");
             var statement = conn.createStatement();
             var result = statement.executeQuery("SELECT group_id, group_name, group_header, group_footer FROM group_list")) {

            while (result.next()) {
                groups.add(new GroupData()
                        .withId(result.getString("group_id"))
                        .withName(result.getString("group_name"))
                        .withHeader(result.getString("group_header"))
                        .withFooter(result.getString("group_footer")));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groups;
    }

    public List<ContactData> getContactList() {
        var contacts = new ArrayList<ContactData>();
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root", "");
             var statement = conn.createStatement();
             var result = statement.executeQuery("SELECT addressbook_id, addressbook_lastname, addressbook_firstname, addressbook_address addressbook_mobile FROM addressbook")) {
            while (result.next()) {
                contacts.add(new ContactData()
                        .withId(result.getString("addressbook_id"))
                        .withFirstName(result.getString("addressbook_firstname"))
                        .withLastName(result.getString("addressbook_lastname"))
                        .withAddress(result.getString("addressbook_address")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return contacts;

    }

    public void checkConsistency() {
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root", "");
             var statement = conn.createStatement();
             var result = statement.executeQuery(
                     "SELECT * FROM address_in_groups ag LEFT JOIN addressbook ab ON ab.id = ag.id WHERE ab.id IS NULL")) {

            if (result.next()) {
                throw new IllegalStateException("DB is corrupted");

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
