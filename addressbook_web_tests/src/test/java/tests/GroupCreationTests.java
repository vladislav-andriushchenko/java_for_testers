package tests;

import model.GroupData;
import org.junit.jupiter.api.Test;

public class GroupCreationTests extends TestBase {
    @Test
    public void canCreateGroup() {
        app.groups().createGroup(new GroupData("name", "header", "footer"));
    }

    @Test
    public void canCreateGroupWithEmptyName() {
        app.groups().createGroup(new GroupData());
    }

    @Test
    public void canCreateGroupWithNameOnly() {
        var emptyGroup = new GroupData();
        var groupWithName = emptyGroup.withName("new name");
        app.groups().createGroup(groupWithName);
    }
}
