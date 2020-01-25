package okon;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.Properties;

public class SshConnection implements AutoCloseable {
    private Session session;

    public SshConnection (String hostname, Integer port, String username, String password) {
        try {
            connect(hostname, port, username, password);
        } catch (JSchException e) {}
    }

    private void connect(String hostname, Integer port,  String username, String password) throws JSchException {
        JSch jSch = new JSch();
        session = jSch.getSession(username, hostname, port);
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
        session.setConfig(config);
        session.setPassword(password);
        session.connect();
    }

    public String runCommand(String command) throws JSchException, IOException {
        String result = "";
        if (!session.isConnected())
            throw new RuntimeException("Not connected to an open session.  Call open() first!");
        ChannelExec channel = null;
        channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand(command);
        PrintStream out = new PrintStream(channel.getOutputStream());
        InputStream in = channel.getInputStream();
        channel.connect();
        result = getChannelOutput(channel, in);
        channel.disconnect();
        return result;
    }

    private String getChannelOutput(Channel channel, InputStream in) throws IOException {
        StringBuilder result = new StringBuilder();
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;

            while ((line = reader.readLine()) != null)
            {
                result.append(line);
            }
            channel.disconnect();
        }
        catch(Exception e)
        {
            System.err.println("Error: " + e);
        }
        return result.toString();
    }

    public void close(){
        session.disconnect();
    }
}