package controllers;

import models.Cloud;
import models.Database;
import models.Server;
import models.Storage;

import java.util.List;

public class CloudController {

    private final Cloud cloud;

    public CloudController(Cloud cloud) {
        this.cloud = cloud;
    }

    public void takeSpaceFromStorage(int value) {
        Storage storage = cloud.getStorage();

        if (value <= storage.getCapacity()) {
            storage.setCapacity(storage.getCapacity() - value);
        }

    }

    public void returnSpaceToStorage(int value) {
        Storage storage = cloud.getStorage();

        storage.setCapacity(storage.getCapacity() + value);
    }

    public void takeSpaceFromDatabase(int value) {
        Database storage = cloud.getDatabase();

        if (value <= storage.getCapacity()) {
            storage.setCapacity(storage.getCapacity() - value);
        }

    }

    public void returnSpaceToDatabase(int value) {
        Database storage = cloud.getDatabase();

        storage.setCapacity(storage.getCapacity() + value);
    }

    public void takeServerFromList(Server server) {
        List<Server> serverList = cloud.getServers();

        for (int i = 0; i < serverList.size(); i++) {
            if (serverList.get(i).name().equals(server.name())) {
                serverList.remove(i);
                cloud.setServers(serverList);
                return;
            }
        }

    }

    public void returnServer(Server server) {
        List<Server> serverList = cloud.getServers();

        serverList.add(server);

        cloud.setServers(serverList);
    }

    public int getAvailableStorageCapacity() {
        return cloud.getStorage().getCapacity();
    }

    public int getAvailableDatabaseCapacity() {
        return cloud.getDatabase().getCapacity();
    }

    public List<Server> getAvailableServerList() {
        return cloud.getServers();
    }

}
