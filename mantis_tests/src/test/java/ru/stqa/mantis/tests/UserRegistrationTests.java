package ru.stqa.mantis.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunctions;
import ru.stqa.mantis.model.DeveloperMailUser;
import ru.stqa.mantis.model.UserData;

import java.time.Duration;

public class UserRegistrationTests extends TestBase {

    DeveloperMailUser user;

    @Test
    void canRegisterUser() {
        var password = "password";
        user = app.developerMail().addUser();
        var email = String.format("%s@localhost", user.name());
        app.jamesCli().addUser(email, password);

        app.rest().createUser(new UserData()
                .withUsername(user.name())
                .withPassword(password)
                .withRealName(user.name())
                .withEmail(email));

        var messages = app.mail().receive(email, password, Duration.ofSeconds(10));
//        var message = app.developerMail().receive(user, Duration.ofSeconds(10));
        var url = CommonFunctions.extractUrl(messages.get(0).content());
        app.session().finishSignUp(url, user.name(), password);
        app.http().login(user.name(), password);
        Assertions.assertTrue(app.http().isLoggedIn());
    }

    @AfterEach
    void deleteMailUser() {
        app.developerMail().deleteUser(user);
    }
}
