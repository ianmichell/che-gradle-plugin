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
package pro.javax.che.plugin.gradle;

/**
 * Describes shared constants between modules.
 *
 * @author Vlad Zhukovskyi
 */
public interface Constants {
    /**
     * Project Type definitions.
     */
    String  PROJECT_TYPE_ID           = "gradle";
    String  PROJECT_TYPE_DISPLAY_NAME = "Gradle Project Type";
    boolean PROJECT_TYPE_PRIMARY      = true;
    boolean PROJECT_TYPE_MIXABLE      = false;
    boolean PROJECT_TYPE_PERSISTED    = true;
    String  PROJECT_TYPE_PARENT       = "java";
}
