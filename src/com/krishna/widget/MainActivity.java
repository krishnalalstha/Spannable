package com.krishna.widget;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	private CustomMultiAutoCompleteTextView phoneNum;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		phoneNum = (CustomMultiAutoCompleteTextView)
				findViewById(R.id.editText);
		ContactPickerAdapter contactPickerAdapter = new ContactPickerAdapter(this,
				android.R.layout.simple_list_item_1, SmsUtil.getContacts(
					this, false));
		phoneNum.setAdapter(contactPickerAdapter);
	}

}
