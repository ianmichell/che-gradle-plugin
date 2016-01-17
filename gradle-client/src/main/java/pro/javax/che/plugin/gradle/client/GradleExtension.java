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
package pro.javax.che.plugin.gradle.client;

import com.google.inject.Singleton;

import org.eclipse.che.ide.api.extension.Extension;

/**
 * Registers client side extension.
 *
 * @author Vlad Zhukovskyi
 */
@Singleton
@Extension(title = "Gradle Support", description = "Create Gradle Projects", version = "1.0")
public class GradleExtension {
    @SuppressWarnings("unused")
    private org.eclipse.che.ide.ext.java.shared.Constants jConstants;

    @SuppressWarnings("unused")
    private pro.javax.che.plugin.gradle.Constants gConstants;
}
