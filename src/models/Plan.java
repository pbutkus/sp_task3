package models;

import java.util.UUID;

public class Plan {

    private String name;
    private int storageCapacity;
    private int databaseCapacity;
    private Server server;
    private final UUID uuid;

    public Plan(String name, int storageCapacity, int databaseCapacity, Server server) {
        this.name = name;
        this.storageCapacity = storageCapacity;
        this.databaseCapacity = databaseCapacity;
        this.server = server;
        this.uuid = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(int storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    public int getDatabaseCapacity() {
        return databaseCapacity;
    }

    public void setDatabaseCapacity(int databaseCapacity) {
        this.databaseCapacity = databaseCapacity;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
