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

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;

/**
 * Implementation for the {@code CreateProjectView}.
 *
 * @author Vlad Zhukovskyi
 * @see CreateProjectView
 * @see CreateProjectPresenter
 */
@Singleton
public class CreateProjectViewImpl implements CreateProjectView {

    private static CreateProjectViewImplUiBinder uiBinder = GWT.create(CreateProjectViewImplUiBinder.class);

    private ActionDelegate delegate;
    private FlowPanel      widget;

    public CreateProjectViewImpl() {
        widget = uiBinder.createAndBindUi(this);
    }

    @Override
    public void setDelegate(ActionDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    interface CreateProjectViewImplUiBinder extends UiBinder<FlowPanel, CreateProjectViewImpl> {
    }
}
