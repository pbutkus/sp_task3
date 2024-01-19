package ui;

import controllers.CloudController;
import controllers.UserController;
import models.Plan;
import models.Server;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private final CloudController cloudController;
    private final UserController userController;
    private final Scanner scanner;

    public UserInterface(CloudController cloudController, UserController userController, Scanner scanner) {
        this.cloudController = cloudController;
        this.userController = userController;
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            System.out.println("Select an option:");
            System.out.println("1. My plans");
            System.out.println("2. Order a new plan");
            System.out.println("0. Exit");
            String userInput = scanner.nextLine();

            switch (userInput) {
                case "1":
                    listPlans();
                    break;
                case "2":
                    orderNewPlan();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid input. Try again:");
                    System.out.println();
            }
        }
    }

    private void listPlans() {
        List<Plan> userPlanList = userController.getUserPlanList();

        System.out.println();

        if (userPlanList.isEmpty()) {
            System.out.println("You are currently not subscribed to any plans.");
            System.out.println();
            return;
        }

        System.out.println("Select plan:");

        for (int i = 0; i < userPlanList.size(); i ++) {
            System.out.println((i + 1) + ". " + userPlanList.get(i));
        }

        myPlansMenu(userPlanList);
    }

    private void myPlansMenu(List<Plan> planList) {
        System.out.println("0. Back");

        while (true) {
            String userInput = scanner.nextLine();

            int userInputAsInt = 0;

            try {
                userInputAsInt = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number representing an option.");
            }

            if (userInputAsInt == 0) {
                System.out.println();
                return;
            } else if (userInputAsInt < 0 || userInputAsInt > planList.size()) {
                System.out.println("Invalid input. Try again:");
            } else {
                planMenu(userController.getUserPlanList().get(userInputAsInt - 1));
                return;
            }
        }
    }

    private void planMenu(Plan plan) {
        while (true) {
            System.out.println();
            System.out.println(plan);
            System.out.println("1. Customize plan");
            System.out.println("2. Cancel plan");
            System.out.println("0. Back");
            String userInput = scanner.nextLine();

            switch (userInput) {
                case "1":
                    customizePlanMenu(plan);
                    break;
                case "2":
                    cancelPlan(plan);
                    return;
                case "0":
                    System.out.println();
                    return;
                default:
                    System.out.println("Invalid option. Try again:");
            }
        }
    }

    private int userInputStringToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void customizePlanMenu(Plan plan) {
        while (true) {
            System.out.println();
            System.out.println("'" + plan + "' customization");
            System.out.println("1. Rename");
            System.out.println("2. Change storage capacity");
            System.out.println("3. Change server");
            System.out.println("4. Change database capacity");
            System.out.println("0. Cancel");
            String userInput = scanner.nextLine();

            switch (userInput) {
                case "1":
                    System.out.println();
                    System.out.println("Enter new plan name:");
                    String newName = scanner.nextLine();
                    plan.setName(newName);
                    userController.updatePlan(plan);
                    System.out.println("Name updated");
                    break;
                case "2":
                    System.out.println();
                    int availableStorageCapacity = cloudController.getAvailableStorageCapacity();
                    System.out.println("Available storage capacity: " + availableStorageCapacity);
                    System.out.println("Current assigned storage capacity: " + plan.getStorageCapacity());
                    System.out.println("Enter new storage capacity:");

                    while (true) {
                        String newCapacity = scanner.nextLine();
                        int newCapacityInt = userInputStringToInt(newCapacity);

                        if (newCapacityInt < 0) {
                            System.out.println("Invalid input. Try again:");
                        } else if (newCapacityInt > availableStorageCapacity) {
                            System.out.println("Requested capacity is larger than available capacity. Enter a new capacity:");
                        } else {
                            plan.setStorageCapacity(newCapacityInt);
                            cloudController.takeSpaceFromStorage(newCapacityInt);
                            userController.updatePlan(plan);
                            System.out.println("Storage capacity updated");
                            break;
                        }
                    }

                    break;
                case "3":
                    System.out.println();
                    System.out.println("Current server: " + plan.getServer().name());
                    System.out.println();
                    System.out.println("Choose new server:");

                    cloudController.returnServer(plan.getServer());

                    List<Server> serverList = cloudController.getAvailableServerList();

                    for (int i = 0; i < serverList.size(); i++) {
                        System.out.println((i + 1) + ". " + serverList.get(i).name());
                    }

                    while (true) {
                        String selectedServer = scanner.nextLine();
                        int convertedInput = userInputStringToInt(selectedServer);

                        if (convertedInput < 0) {
                            System.out.println("Invalid input. Try again:");
                        } else {
                            Server server = serverList.get(convertedInput - 1);
                            cloudController.takeServerFromList(server);
                            plan.setServer(server);
                            userController.updatePlan(plan);
                            break;
                        }
                    }
                    break;
                case "4":
                    System.out.println();
                    int availableDatabaseCapacity = cloudController.getAvailableDatabaseCapacity();
                    System.out.println("Available database capacity: " + availableDatabaseCapacity);
                    System.out.println("Current assigned database capacity: " + plan.getDatabaseCapacity());
                    System.out.println("Enter new database capacity:");

                    while (true) {
                        String newDatabaseCapacity = scanner.nextLine();
                        int newDatabaseCapacityInt = userInputStringToInt(newDatabaseCapacity);

                        if (newDatabaseCapacityInt < 0) {
                            System.out.println("Invalid input. Try again:");
                        } else if (newDatabaseCapacityInt > availableDatabaseCapacity) {
                            System.out.println("Requested capacity is larger than available capacity. Enter a new capacity:");
                        } else {
                            plan.setDatabaseCapacity(newDatabaseCapacityInt);
                            cloudController.takeSpaceFromDatabase(newDatabaseCapacityInt);
                            userController.updatePlan(plan);
                            System.out.println("Storage capacity updated");
                            break;
                        }
                    }
                    break;
                case "0":
                    System.out.println();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void cancelPlan(Plan plan) {
        System.out.println();

        cloudController.returnServer(plan.getServer());
        cloudController.returnSpaceToDatabase(plan.getDatabaseCapacity());
        cloudController.returnSpaceToStorage(plan.getStorageCapacity());

        userController.removePlan(plan);

        System.out.println("The plan has been canceled");
        System.out.println();
    }

    private void orderNewPlan() {
        int availableStorageCapacity = cloudController.getAvailableStorageCapacity();
        int availableDatabaseCapacity = cloudController.getAvailableDatabaseCapacity();
        List<Server> serverList = cloudController.getAvailableServerList();

        if (availableStorageCapacity <= 0 || availableDatabaseCapacity <= 0 || serverList.isEmpty()) {
            System.out.println();
            System.out.println("We currently do not have enough resources remaining for a new plan. Try again later.");
            System.out.println();
            return;
        }

        Server server = null;

        System.out.println();
        System.out.println("Choose a name for the plan");
        String name = scanner.nextLine();

        System.out.println();
        System.out.println("Enter storage capacity (available: " + availableStorageCapacity + ")");
        int storageCapacity = handleCapacity(availableStorageCapacity);

        System.out.println();
        System.out.println("Select server from the list:");
        for (int i = 0; i < serverList.size(); i++) {
            System.out.println((i + 1) + ". " + serverList.get(i).name());
        }
        while (true) {
            String serverIndex = scanner.nextLine();
            int convertedServerIndex = userInputStringToInt(serverIndex);

            if (convertedServerIndex < 0) {
                System.out.println("Invalid input. Try again:");
            } else {
                server = serverList.get(convertedServerIndex - 1);
                break;
            }
        }

        System.out.println();
        System.out.println("Enter database capacity (available: " + availableDatabaseCapacity + ")");
        int databaseCapacity = handleCapacity(availableDatabaseCapacity);

        cloudController.takeSpaceFromStorage(storageCapacity);
        cloudController.takeSpaceFromDatabase(databaseCapacity);
        cloudController.takeServerFromList(server);

        Plan newPlan = new Plan(name, storageCapacity, databaseCapacity, server);

        userController.addPlan(newPlan);

        System.out.println();
        System.out.println("Plan ordered successfully");
        System.out.println();
    }

    private int handleCapacity(int availableCapacity) {
        while (true) {
            String capacity = scanner.nextLine();
            int convertedCapacity = userInputStringToInt(capacity);

            if (convertedCapacity < 0) {
                System.out.println("Invalid input. Try again:");
            } else if (convertedCapacity > availableCapacity) {
                System.out.println("Requested capacity is larger than available capacity. Enter a new capacity:");
            } else {
                return convertedCapacity;
            }
        }
    }

}
