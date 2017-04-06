package model.io;

/*
    Copyright (C) 31-3-17  Hanze Hogeschool ITV2D

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

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class InputServer {

    private OutputStream outputStream;
    private PrintWriter printWriter;

    private Connection connection;

    public InputServer(Connection connection) {
        this.connection = connection;

    }

    public void start() throws IOException {
        this.outputStream = connection.getSocket().getOutputStream();
        this.printWriter = new PrintWriter(new OutputStreamWriter(outputStream), true);
    }

    public void submit(String command) {
        printWriter.println(command);
        printWriter.flush();
    }

}
