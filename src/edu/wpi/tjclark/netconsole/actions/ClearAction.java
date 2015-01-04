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

package edu.wpi.tjclark.netconsole.actions;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import edu.wpi.tjclark.netconsole.NetConsoleClient;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Clear the console
 */
public class ClearAction extends AnAction {
    private NetConsoleClient netConsole;

    public ClearAction(NetConsoleClient netConsole) {
        super("Clear", "Clear the console", AllIcons.Actions.Delete);

        this.netConsole = netConsole;
    }

    /**
     * Implement this method to provide your action handler.
     *
     * @param e Carries information on the invocation place
     */
    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            Document document = this.netConsole.getDocument();

            synchronized (document) {
                /* Remove the entire document */
                document.remove(0, document.getLength());
            }
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }
}
