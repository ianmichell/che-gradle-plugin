/*******************************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package pro.javax.che.plugin.gradle.client.project;

import pro.javax.che.plugin.gradle.client.project.CreateProjectView.ActionDelegate;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eclipse.che.api.workspace.shared.dto.ProjectConfigDto;
import org.eclipse.che.ide.api.wizard.AbstractWizardPage;

/**
 * Wizard for creating Gradle projects in Che.
 *
 * @author Vlad Zhukovskyi
 * @see CreateProjectView
 * @see CreateProjectViewImpl
 */
@Singleton
public class CreateProjectPresenter extends AbstractWizardPage<ProjectConfigDto> implements ActionDelegate {

    private CreateProjectView view;

    @Inject
    public CreateProjectPresenter(CreateProjectView view) {
        super();

        this.view = view;
        this.view.setDelegate(this);
    }

    @Override
    public void go(AcceptsOneWidget container) {
        container.setWidget(view);
    }

    @Override
    public boolean isCompleted() {
        return true;
    }

    @Override
    public boolean canSkip() {
        return true;
    }
}
