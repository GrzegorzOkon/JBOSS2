package okon;

import okon.config.HostParamsReader;
import okon.config.ServerAuthReader;
import okon.exception.AppException;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class JBoss2App {
    static final Queue<Job> jobs = new LinkedList<>();
    static final List<Message> messages = new ArrayList();

    public static void main(String args[]) {
        initializeQueue();
        startThreadPool(4);
        print();
    }

    static void initializeQueue() {
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

    static void startThreadPool(int threadSum) {
        Thread[] threads = new Thread[threadSum];
        for (int i = 0; i < threadSum; i++) {
            threads[i] = new MessageProducerThread();
        }
        for (int i = 0; i < threadSum; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threadSum; i++) {
            try {
                threads[i].join();
            } catch (Exception e) {
                throw new AppException(e);
            }
        }
    }

    static void print() {
        printToConsole();
        printToFile();
    }

    static void printToConsole() {
        for (Message message : messages) {
            System.out.println(message.getDescription());
            System.out.println(message.getResult());
        }
    }

    static void printToFile() {
        try (Writer out = new FileWriter(new java.io.File(JBoss2App.getJarFileName() + ".txt"))) {
            for (Message message : messages) {
                out.write(message.getDescription());
                out.write(System.getProperty("line.separator"));
                out.write(message.getResult());
                out.write(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    static String getJarFileName() {
        String path = JBoss2App.class.getResource(JBoss2App.class.getSimpleName() + ".class").getFile();
        path = path.substring(0, path.lastIndexOf('!'));
        path = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf('.'));
        return path;
    }
}
