package okon;

public class Host {
    private final String alias;
    private final String ip;
    private final Integer port;
    private final String command;
    private final String description;
    private final String interfaceName;

    public Host(String alias, String ip, Integer port, String command, String description, String interfaceName) {
        this.alias = alias;
        this.ip = ip;
        this.port = port;
        this.command = command;
        this.description = description;
        this.interfaceName = interfaceName;
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

    public String getInterfaceName() {
        return interfaceName;
    }
}
