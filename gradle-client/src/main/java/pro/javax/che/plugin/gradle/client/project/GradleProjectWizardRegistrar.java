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

import pro.javax.che.plugin.gradle.Constants;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import org.eclipse.che.api.workspace.shared.dto.ProjectConfigDto;
import org.eclipse.che.ide.api.project.type.wizard.ProjectWizardRegistrar;
import org.eclipse.che.ide.api.wizard.WizardPage;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.eclipse.che.ide.ext.java.shared.Constants.JAVA_CATEGORY;
import static pro.javax.che.plugin.gradle.Constants.PROJECT_TYPE_ID;

/**
 * Registers new wizard for creating Gradle projects.
 *
 * @author Vlad Zhukovskyi
 */
@Singleton
public class GradleProjectWizardRegistrar implements ProjectWizardRegistrar {

    private final List<Provider<? extends WizardPage<ProjectConfigDto>>> wizardPages;

    @Inject
    public GradleProjectWizardRegistrar(Provider<CreateProjectPresenter> wizardPagesProvider) {
        wizardPages = newArrayList();
        wizardPages.add(wizardPagesProvider);
    }

    @Override
    public String getProjectTypeId() {
        return PROJECT_TYPE_ID;
    }

    @Override
    public String getCategory() {
        return JAVA_CATEGORY;
    }

    @Override
    public List<Provider<? extends WizardPage<ProjectConfigDto>>> getWizardPages() {
        return wizardPages;
    }
}
