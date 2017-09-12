/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processing;

import java.io.*;
import java.util.List;

/**
 *
 * @author makafui
 */
public class SystemCommandExecutor {

    private List<String> commandInformation;
    private ThreadedStreamHandler inputStreamHandler;
    private ThreadedStreamHandler errorStreamHandler;

    public SystemCommandExecutor(final List<String> commandInformation) {
        if (commandInformation == null) {
            throw new NullPointerException("The commandInformation is required.");
        }
        this.commandInformation = commandInformation;
    }

    public int executeCommand()
            throws IOException, InterruptedException {
        int exitValue = -99;

        try {
            ProcessBuilder pb = new ProcessBuilder(commandInformation);
            Process process = pb.start();

            
            OutputStream stdOutput = process.getOutputStream();

            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();

            inputStreamHandler = new ThreadedStreamHandler(inputStream);
            errorStreamHandler = new ThreadedStreamHandler(errorStream);

            inputStreamHandler.start();
            errorStreamHandler.start();

            // TODO a better way to do this?
            exitValue = process.waitFor();

            // TODO a better way to do this?
            inputStreamHandler.interrupt();
            errorStreamHandler.interrupt();
            inputStreamHandler.join();
            errorStreamHandler.join();
        
        } catch (IOException e) {
            // TODO deal with this here, or just throw it?
            throw e;
        } catch (InterruptedException e) {
            // generated by process.waitFor() call
            // TODO deal with this here, or just throw it?
            throw e;
        } finally {
            return exitValue;
        }
    }

    /**
     * Get the standard output (stdout) from the command you just exec'd.
     */
    public StringBuilder getStandardOutputFromCommand() {
        return inputStreamHandler.getOutputBuffer();
    }

    /**
     * Get the standard error (stderr) from the command you just exec'd.
     */
    public StringBuilder getStandardErrorFromCommand() {
        return errorStreamHandler.getOutputBuffer();
    }

}
