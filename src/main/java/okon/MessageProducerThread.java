package okon;

import okon.exception.AppException;

import static okon.JBoss2App.jobs;
import static okon.JBoss2App.messages;

public class MessageProducerThread extends Thread {
    @Override
    public void run() {
        while (!jobs.isEmpty()) {
            Job job = null;
            synchronized (jobs) {
                if (!jobs.isEmpty()) {
                    job = jobs.poll();
                }
            }
            if (job != null) {
                Message message = null;
                try (SshConnection connection = new SshConnection(job.getIp(), job.getPort(), job.getUser(), job.getPassword())) {
                    try {
                        String result = connection.runCommand(job.getCommand());
                        message = new Message(job.getDescription(), result);
                    } catch (Exception e) {
                        throw new AppException(e);
                    }
                }
                synchronized (messages) {
                    messages.add(message);
                }
            }
        }
    }
}