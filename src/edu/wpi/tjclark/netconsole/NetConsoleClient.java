/**
 * Copyright (c) 2015 Thomas Clark
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package edu.wpi.tjclark.netconsole;

import com.intellij.openapi.actionSystem.AnAction;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * Listens for netconsole packets in a separate thread, and updates a {@link Document} when they arrive
 */
public class NetConsoleClient {
    private static final int NETCONSOLE_PORT = 6666;
    private Document document;
    private boolean disabled;
    private AnAction resumeAction;
    private AnAction stopAction;

    /**
     * Initialize an enabled NetConsole controller and start receiving packets
     *
     * @param document The <code>Document</code> to write incoming packets to
     */
    public NetConsoleClient(Document document) {
        this.document = document;
        this.disabled = false;

        new Thread(this.receive).start();
    }

    /**
     * @param stopped if <code>true</code>, ignore any incoming packets
     */
    public void setDisabled(boolean stopped) {
        this.disabled = stopped;
    }

    /**
     * @return <code>true</code> if the console is set to ignore incoming packets
     */
    public boolean isDisabled() {
        return this.disabled;
    }

    /**
     * @return The {@link javax.swing.text.Document} containing the console output
     */
    public Document getDocument() {
        return this.document;
    }

    private Runnable receive = new Runnable() {
        @Override
        public void run() {
            for (; ; ) {
                try {
                    final DatagramSocket socket = new DatagramSocket(null);
                    socket.setReuseAddress(true);
                    socket.setBroadcast(true);
                    socket.bind(new InetSocketAddress(NETCONSOLE_PORT));

                    final DatagramPacket packet = new DatagramPacket(new byte[1500], 1500);

                    for (; ; ) {
                        socket.receive(packet);

                        if (!NetConsoleClient.this.disabled) {
                            final String str = new String(packet.getData(), packet.getOffset(), packet.getLength());

                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Document document = NetConsoleClient.this.document;
                                        synchronized (document) {
                                            document.insertString(document.getLength(), str, null);
                                        }
                                    } catch (BadLocationException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                } catch (IOException ignored) {
                }
            }
        }
    };
}
