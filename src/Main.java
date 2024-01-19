import controllers.CloudController;
import controllers.UserController;
import models.Cloud;
import ui.UserInterface;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        CloudController cloudController = new CloudController(new Cloud());
        UserController userController = new UserController();
        UserInterface ui = new UserInterface(cloudController, userController, scanner);

        ui.start();

    }
}