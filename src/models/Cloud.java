package models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Cloud {

    private Storage storage;
    private Database database;
    private List<Server> servers;

    public Cloud() {
        this.storage = new Storage((int)(Math.random() * 10000) + 1);
        this.database = new Database((int)(Math.random() * 1000) + 1);
        this.servers = buildServerList((int)(Math.random() * 2) + 1);
    }

    private List<Server> buildServerList(int number) {
        List<Server> serverList = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            serverList.add(new Server((i + 100) + "_BASIC"));
            serverList.add(new Server((i + 200) + "_MIDDLE"));
            serverList.add(new Server((i + 300) + "_MAX"));
        }

        serverList.sort(Comparator.comparing(Server::name));

        return serverList;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        servers.sort(Comparator.comparing(Server::name));

        this.servers = servers;
    }

}
