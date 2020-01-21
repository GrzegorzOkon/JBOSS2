package okon;

public class Authorization {
    private final String interfaceName;
    private final String user;
    private final String password;
    private final String domain;

    public Authorization(String interfaceName, String user, String password, String domain) {
        this.interfaceName = interfaceName;
        this.user = user;
        this.password = password;
        this.domain = domain;
    }

    public String getInterfaceName() {
        return interfaceName;
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
