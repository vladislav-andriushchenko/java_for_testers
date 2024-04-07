package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stqa.mantis.common.CommonFunctions;

import java.time.Duration;

public class UserRegistrationTests extends TestBase {

    @Test
    void canRegisterUser() {
        var username = CommonFunctions.randomString(5);
        var password = "password";
        var email = String.format("%s@localhost", username);

        //create a user (JamesHelper)
        app.jamesCli().addUser(email, password);

        //fill the form with user creation and send email (UI)
        app.session().signUp(username, email);

        //wait for email (MailHelper)
        var messages = app.mail().receive(email, password, Duration.ofSeconds(10));

        //get link form email
        var url = app.session().getConfirmationLink(messages);

        //open a link and finish a registration (UI)
        app.session().finishSignUp(url, username, password);

        //check new user login (HttpSessionHelper)
        app.http().login(username, password);
        Assertions.assertTrue(app.http().isLoggedIn());
    }
}
