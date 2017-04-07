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

public class OutputServer {

    private InputStream inputStream;
    private BufferedReader inputReader;

    private Connection connection;

    private Observer observer;

    public OutputServer(Connection connection) {
        this.connection = connection;
    }

    /**
     * Start the output server on the given connections socket.
     * @throws IOException Thrown when the connection to the socket cannot be established.
     */
    public void start() throws IOException {
        this.connection.setSocket(new Socket(connection.getAddress(), connection.getPort()));

        InputStream inputStream = connection.getSocket().getInputStream();
        this.inputStream = inputStream;
        this.inputReader = new BufferedReader(new InputStreamReader(inputStream));

        startLoop();
    }

    private void startLoop() {
        new Thread(() -> {
            try {
                startReadLoop();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }).start();
    }

    private void startReadLoop() throws IOException {
        while (connection.getSocket().isConnected()) {
            String line;
            while ((line = inputReader.readLine()) != null) {
                if (observer != null) observer.onNewLine(line);
                if (!inputReader.ready() && observer != null) observer.onEndOfLine();
            }
        }
    }

    public interface Observer {
        void onNewLine(String line);
        void onEndOfLine();
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }

}
