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

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import edu.wpi.tjclark.netconsole.actions.AutoScrollAction;
import edu.wpi.tjclark.netconsole.actions.ClearAction;
import edu.wpi.tjclark.netconsole.actions.DisableAction;
import org.jdesktop.swingx.JXTextArea;

import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * Initializes a toolwindow that shows a netconsole
 *
 * @author Thomas Clark
 */
public class NetConsoleToolWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        final Document document = new PlainDocument();
        final NetConsoleClient netConsole = new NetConsoleClient(document);

        final JXTextArea textArea = new JXTextArea();
        textArea.setDocument(document);
        textArea.setEditable(false);

        final DefaultActionGroup actionGroup = new DefaultActionGroup();
        actionGroup.addAction(new AutoScrollAction(netConsole, textArea));
        actionGroup.addAction(new ClearAction(netConsole));
        actionGroup.addAction(new DisableAction(netConsole, textArea));

        final SimpleToolWindowPanel netConsolePanel = new SimpleToolWindowPanel(false, false);
        final ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar(ActionPlaces.UNKNOWN,
                actionGroup, false);
        netConsolePanel.setToolbar(toolbar.getComponent());
        netConsolePanel.setContent(new JBScrollPane(textArea));

        final ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        final Content content = contentFactory.createContent(netConsolePanel, "", true);
        toolWindow.getContentManager().addContent(content);
    }
}
