package ru.stqa.addressbook.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.Random;

public class GroupRemovalTests extends TestBase {
    @Test
    public void canRemoveGroup() {
        if (app.hmb().getGroupCount() == 0) {
            app.hmb().createGroup(new GroupData("", "name", "header", "footer"));
        }
        var oldGroups = app.hmb().getGroupList();
        var rnd = new Random();
        var index = rnd.nextInt(oldGroups.size());
        app.groups().removeGroup(oldGroups.get(index));
        var newGroups = app.hmb().getGroupList();
        var expectedList = new ArrayList<>(oldGroups);
        expectedList.remove(index);
        Assertions.assertEquals(newGroups, expectedList);
    }

    @Test
    public void canRemoveAllGroupsAtOnce() {
        if (app.hmb().getGroupCount() == 0) {
            app.hmb().createGroup(new GroupData("", "name", "header", "footer"));
        }
        app.groups().removeAllGroups();
        Assertions.assertEquals(0, app.hmb().getGroupCount());
    }
}
