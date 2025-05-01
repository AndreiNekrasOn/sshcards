package org.andnekon.server;

import org.andnekon.controller.GameController;
import org.andnekon.controller.GameControllerFactory;
import org.andnekon.utils.MonitoredOutputStream;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.AsyncAuthException;
import org.apache.sshd.server.auth.keyboard.InteractiveChallenge;
import org.apache.sshd.server.auth.keyboard.KeyboardInteractiveAuthenticator;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.password.PasswordChangeRequiredException;
import org.apache.sshd.server.channel.ChannelSession;
import org.apache.sshd.server.command.Command;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.shell.ShellFactory;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SshTuiServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setPort(2222);
        sshd.setHost("0.0.0.0");
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider());
        sshd.setShellFactory(new ShellFactory() {
            @Override
            public Command createShell(ChannelSession channel) throws IOException {
                return new Command() {
                    private InputStream in;
                    private OutputStream out;
                    private OutputStream err;
                    private ExitCallback callback;
                    private ExecutorService executor = Executors.newSingleThreadExecutor();

                    @Override
                    public void setInputStream(InputStream in) {
                        this.in = in;
                    }

                    @Override
                    public void setOutputStream(OutputStream out) {
                        this.out = out;
                    }

                    @Override
                    public void setErrorStream(OutputStream err) {
                        this.err = err;
                    }

                    @Override
                    public void setExitCallback(ExitCallback callback) {
                        this.callback = callback;
                    }

                    @Override
                    public void start(ChannelSession channel, Environment env) throws IOException {
                        executor.submit(() -> {
                            try {
                                int cols = Integer.parseInt(env.getEnv().get(Environment.ENV_COLUMNS));
                                int rows = Integer.parseInt(env.getEnv().get(Environment.ENV_LINES));
                                System.out.println("SSH client terminal size: " + cols + "x" + rows);
                                GameController controller = GameControllerFactory.createController(true, in, out);
                                controller.run();
                            } catch (Exception e) {
                                e.printStackTrace(new PrintStream(err));
                            } finally {
                                callback.onExit(0);
                            }
                        });
                    }

                    @Override
                    public void destroy(ChannelSession channel) throws Exception {
                        executor.shutdownNow();
                    }
                };
            }
        });

        sshd.setPasswordAuthenticator((String username, String password, ServerSession session) -> true);
        sshd.setKeyboardInteractiveAuthenticator(new KeyboardInteractiveAuthenticator() {
            @Override
            public InteractiveChallenge generateChallenge(ServerSession session, String username, String lang,
                    String subMethods) throws Exception {
                return new InteractiveChallenge();
            }
            @Override
            public boolean authenticate(ServerSession session, String username, List<String> responses)
                throws Exception {
                return true;
            }
        });
        sshd.start();
        while (true) {}
    }
}
