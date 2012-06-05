package com.krishna.widget;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class ContactPickerAdapter extends ArrayAdapter<Contact> implements
		Filterable {

	private ArrayList<Contact> contactList, cloneContactList;
	private LayoutInflater layoutInflater;

	@SuppressWarnings("unchecked")
	public ContactPickerAdapter(Context context, int textViewResourceId,
			ArrayList<Contact> contactList) {
		super(context, textViewResourceId);
		this.contactList = contactList;
		this.cloneContactList = (ArrayList<Contact>) this.contactList.clone();
		layoutInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {

		return contactList.size();
	}

	@Override
	public Contact getItem(int position) {

		return contactList.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Holder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.contact_list_item,
					null);
			holder = new Holder();
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.phone = (TextView) convertView.findViewById(R.id.phone);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		Contact contact = getItem(position);
		holder.name.setText(contact.contactName);
		holder.phone.setText(contact.num);
		return convertView;
	}

	@Override
	public Filter getFilter() {
		Filter contactFilter = new Filter() {

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				if (results.values != null) {
					contactList = (ArrayList<Contact>) results.values;
					notifyDataSetChanged();
				}

			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {

				String sortValue = constraint == null ? "" : constraint
						.toString().toLowerCase();
				FilterResults filterResults = new FilterResults();
				if (!TextUtils.isEmpty(sortValue.trim())) {
					ArrayList<Contact> sortedContactList = new ArrayList<Contact>();
					for (Contact contact : cloneContactList) {
						if (contact.contactName.toLowerCase().contains(
								sortValue)
								|| contact.num.toLowerCase()
										.contains(sortValue))
							sortedContactList.add(contact);
					}

					filterResults.values = sortedContactList;
					filterResults.count = sortedContactList.size();

				}
				return filterResults;
			}

			@Override
			public CharSequence convertResultToString(Object resultValue) {
			// need to save this to saved contact
				return ((Contact) resultValue).contactName;
			}
		};

		return contactFilter;
	}

	@SuppressWarnings("unchecked")
	public void setContactList(ArrayList<Contact> contactList) {
		// this isn't the efficient method
		// need to improvise on this
		this.contactList = contactList;
		this.cloneContactList = (ArrayList<Contact>) this.contactList.clone();
		notifyDataSetChanged();
	}

	public static class Holder {
		public TextView phone, name;
	}

}
