package test;/*
    Copyright (C) 28-3-17  Hanze Hogeschool ITV2D

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
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class Test {

    private SocketChannel channel;

    public Test() {
        try {
            SocketChannel socketChannel = this.channel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 7789));
            while (!socketChannel.finishConnect()){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read() {
        Charset charset = Charset.forName("ISO-8859-1");
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        CharsetDecoder decoder = charset.newDecoder();

        try {
            int bytesRead = channel.read(byteBuffer);
            if (bytesRead == -1) {
                this.wait();
            }
            byteBuffer.flip();
            String request = decoder.decode(byteBuffer).toString();
            System.out.println(request);
            byteBuffer.clear();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void write() {
        try {
            String newData = "login Harjan";

            ByteBuffer buf = ByteBuffer.allocate(256);
            buf.clear();
            buf.put(newData.getBytes());

            buf.flip();

            while(buf.hasRemaining()) {
                channel.write(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
