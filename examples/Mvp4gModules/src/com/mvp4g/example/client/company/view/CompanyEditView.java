package com.mvp4g.example.client.company.view;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CompanyEditView extends AbstractCompanyView {

	private TextBox name = null;

	@Override
	protected String getLeftButtonName() {
		return "Update";
	}

	@Override
	protected Widget createAndGetNameWidget() {
		name = new TextBox();
		return name;
	}

	@Override
	protected String getRightButtonName() {
		return "Clear";
	}

	public HasValue<String> getName() {
		return name;
	}

}
