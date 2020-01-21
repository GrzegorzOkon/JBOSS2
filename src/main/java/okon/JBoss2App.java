package okon;

import okon.config.HostParamsReader;
import okon.config.ServerAuthReader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class JBoss2App {
    static final Queue<Job> jobs = new LinkedList<>();

    public static void main (String args[]) {
        initializeQueue();
    }

    static void initializeQueue () {
        List<Host> hosts = HostParamsReader.readHostParams(new File("./config/hosts.xml"));
        List<Authorization> authorizations = ServerAuthReader.readServerAuthorizationParams(new File("./config/server-auth.xml"));
        createJobs(hosts, authorizations);
    }

    static void createJobs(List<Host> hosts, List<Authorization> authorizations) {
        for (Host host : hosts) {
            for (Authorization authorization : authorizations) {
                if (host.getInterfaceName().equals(authorization.getInterfaceName())) {
                    Job job = new Job(host.getAlias(), host.getIp(), host.getPort(), host.getCommand(), host.getDescription(),
                            authorization.getUser(), authorization.getPassword(), authorization.getDomain());
                    jobs.add(job);
                    break;
                }
            }
        }
    }
}
