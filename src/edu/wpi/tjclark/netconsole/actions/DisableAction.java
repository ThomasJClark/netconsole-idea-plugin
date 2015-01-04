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

import javax.swing.*;

/**
 * Toggles the console between being enabled and disabled.  When the console is disabled, all incoming packets are
 * ignored.
 */
public class DisableAction extends ToggleAction {
    private NetConsoleClient netConsole;
    private JComponent consoleComponent;

    public DisableAction(NetConsoleClient netConsole, JComponent consoleComponent) {
        super("Disable", "Ignore incoming packets", AllIcons.Actions.Suspend);

        this.netConsole = netConsole;
        this.consoleComponent = consoleComponent;
    }

    /**
     * Returns the selected (checked, pressed) state of the action.
     *
     * @param e the action event representing the place and context in which the selected state is queried.
     * @return true if the action is selected, false otherwise
     */
    @Override
    public boolean isSelected(AnActionEvent e) {
        return this.netConsole.isDisabled();
    }

    /**
     * Sets the selected state of the action to the specified value.
     *
     * @param e     the action event which caused the state change.
     * @param state the new selected state of the action.
     */
    @Override
    public void setSelected(AnActionEvent e, boolean state) {
        this.netConsole.setDisabled(state);
        this.consoleComponent.setEnabled(!state);
    }
}
