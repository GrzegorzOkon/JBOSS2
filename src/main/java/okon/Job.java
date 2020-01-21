package okon;

public class Job {
    private final String alias;
    private final String ip;
    private final Integer port;
    private final String command;
    private final String description;
    private final String user;
    private final String password;
    private final String domain;

    public Job(String alias, String ip, Integer port, String command, String description, String user, String password, String domain) {
        this.alias = alias;
        this.ip = ip;
        this.port = port;
        this.command = command;
        this.description = description;
        this.user = user;
        this.password = password;
        this.domain = domain;
    }

    public String getAlias() {
        return alias;
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDomain() {
        return domain;
    }
}
