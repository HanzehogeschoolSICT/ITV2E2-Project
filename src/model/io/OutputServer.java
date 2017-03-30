package model.io;/*
    Copyright (C) 30-3-17  Hanze Hogeschool ITV2D

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.*;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class OutputServer {

    private static final String LOGIN_MESSAGE = "Strategic Game Server Fixed [Version 1.1.0](C) Copyright 2015 Hanzehogeschool Groningen";

    private boolean connected;

    private Integer port;
    private String address;
    private Socket socket;

    private LinkedBlockingQueue<String> writeQueue;
    private LinkedBlockingQueue<String> outputQueue;

    private BufferedReader inputReader;
    private InputStream inputStream;
    private PrintWriter printWriter;

    private LoopObserver observer;
    private StartObserver startObserver;

    public OutputServer(String address, Integer port) {
        this.port = port;
        this.address = address;
        this.writeQueue = new LinkedBlockingQueue<>();
        this.outputQueue = new LinkedBlockingQueue<>();
        this.connected = false;
    }

    public void start(StartObserver startObserver) throws IOException {
        this.startObserver = startObserver;
        this.socket = new Socket(this.address, this.port);

        InputStream inputStream = socket.getInputStream();
        this.inputStream = inputStream;
        this.inputReader = new BufferedReader(new InputStreamReader(inputStream));

        OutputStream outputStream = socket.getOutputStream();
        this.printWriter = new PrintWriter(new OutputStreamWriter(outputStream), true);

        startLoop();
    }

    private void startLoop() {
        new Thread(() -> {
            try {
                runReadWriteLoop();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }).start();
    }

    private void runReadWriteLoop() throws IOException {
        while (true) {
            if(writeQueue.size() >= 1) {
                printWriter.println(writeQueue.poll());
            } else if (inputStream.available() != 0) {
                String line;
                while ((line = inputReader.readLine()) != null) {
                    System.out.println(line);
                    outputQueue.add(line);
                }
                checkConnection();
                Queue<String> result = new LinkedBlockingQueue<>(outputQueue);
                if (observer != null) observer.onEndOfLine(result);
                outputQueue = new LinkedBlockingQueue<>();
            }
        }
    }

    private void checkConnection() {
        if (!this.connected) {
            StringBuilder builder = new StringBuilder();
            for (String line : outputQueue) builder.append(line);
            this.connected = builder.toString().equals(LOGIN_MESSAGE);
            this.startObserver.onConnect(builder.toString());
            this.outputQueue = new LinkedBlockingQueue<>();
        }
    }

    public void submit(String command, LoopObserver observer) {
        this.observer = observer;
        printWriter.print(command);
    }

    public interface LoopObserver {
        void onEndOfLine(Queue<String> outputQueue);
    }

    public interface StartObserver {
        void onConnect(String message);
    }

    public void setObserver(LoopObserver observer) {
        this.observer = observer;
    }
}
