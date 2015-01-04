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
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import edu.wpi.tjclark.netconsole.NetConsoleClient;
import org.jdesktop.swingx.JXTextArea;

import javax.swing.text.DefaultCaret;

/**
 * Toggles autoscroll on and off.  When autoscroll is on, the console automatically scrolls to the bottom of the
 * text when a new packet arrives.
 */
public class AutoScrollAction extends ToggleAction {
    private NetConsoleClient netConsole;
    private JXTextArea consoleComponent;

    public AutoScrollAction(NetConsoleClient netConsole, JXTextArea console) {
        super("AutoScroll", "Automatically scroll on input", AllIcons.RunConfigurations.Scroll_down);

        this.netConsole = netConsole;
        this.consoleComponent = console;
    }

    /**
     * Returns the selected (checked, pressed) state of the action.
     *
     * @param e the action event representing the place and context in which the selected state is queried.
     * @return true if the textarea is set to automatically scroll
     */
    @Override
    public boolean isSelected(AnActionEvent e) {
        DefaultCaret caret = (DefaultCaret) this.consoleComponent.getCaret();
        return caret.getUpdatePolicy() != DefaultCaret.NEVER_UPDATE;
    }

    /**
     * Sets weather the textarea automatically scrolls on output.
     *
     * @param e     the action event which caused the state change.
     * @param state the new selected state of the action.
     */
    @Override
    public void setSelected(AnActionEvent e, boolean state) {
        DefaultCaret caret = (DefaultCaret) this.consoleComponent.getCaret();

        if (state) {
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            this.consoleComponent.setCaretPosition(this.consoleComponent.getDocument().getLength());
        } else {
            caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        }
    }
}
