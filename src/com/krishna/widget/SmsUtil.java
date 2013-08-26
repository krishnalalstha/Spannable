package com.krishna.widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

public class SmsUtil {

	public static HashMap<String, String> selectedContact = new HashMap<String, String>();


	public static ArrayList<Contact> getContacts(Context context,
			boolean addAllConatct) {
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		try {

			Cursor cursor = context.getContentResolver()
					.query(Phone.CONTENT_URI,
							new String[] { Phone._ID, Phone.DISPLAY_NAME,
									Phone.NUMBER }, null, null, null);
			cursor.moveToFirst();
			while (cursor.moveToNext()) {
				Contact contact = new Contact();
				contact.contactName = cursor.getString(cursor
						.getColumnIndex(Phone.DISPLAY_NAME));
				contact.num = cursor.getString(cursor
						.getColumnIndex(Phone.NUMBER));
				if (selectedContact.get(contact.num) == null)
					contacts.add(contact);
			}

			Collections.sort(contacts, new Comparator<Contact>() {

				@Override
				public int compare(Contact object1, Contact object2) {
					// TODO Auto-generated method stub
					return object1.contactName
							.compareToIgnoreCase(object2.contactName);
				}

			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Log.i("contactLength",String.valueOf(contacts.size()));
		return contacts;
	}




	public static Object extractBitmapFromTextView(View view) {

		int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		view.measure(spec, spec);
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		Bitmap b = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
				Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(b);
		c.translate(-view.getScrollX(), -view.getScrollY());
		view.draw(c);
		BitmapDrawable d = new BitmapDrawable(getContext().getResources(), b);
		d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
		return d;

	}

}
