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
package pro.javax.che.plugin.gradle.server;

import com.google.inject.Singleton;

import org.eclipse.che.api.project.server.FolderEntry;
import org.eclipse.che.api.project.server.InvalidValueException;
import org.eclipse.che.api.project.server.ValueProvider;
import org.eclipse.che.api.project.server.ValueProviderFactory;
import org.eclipse.che.api.project.server.ValueStorageException;

import java.util.List;

/**
 * Resolve basic project properties, such as Gradle version, distribution type, project tasks, etc.
 *
 * @author Vlad Zhukovskyi
 */
@Singleton
public class GradleValueProviderFactory implements ValueProviderFactory {
    @Override
    public ValueProvider newInstance(FolderEntry projectFolder) {
        return new GradleValueProvider(projectFolder);
    }

    protected class GradleValueProvider implements ValueProvider {

        protected FolderEntry projectFolder;

        public GradleValueProvider(FolderEntry projectFolder) {
            this.projectFolder = projectFolder;
        }

        @Override
        public List<String> getValues(String attributeName) throws ValueStorageException {
            return null;
        }

        @Override
        public void setValues(String attributeName, List<String> value) throws ValueStorageException, InvalidValueException {

        }
    }
}
