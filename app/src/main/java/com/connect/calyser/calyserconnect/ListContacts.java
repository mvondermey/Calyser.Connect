package com.connect.calyser.calyserconnect;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.UpdateAppearance;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.security.UnresolvedPermission;
import java.util.ArrayList;


public class ListContacts extends AppCompatActivity {
//
    ArrayList<String> ContactsEmail = new ArrayList<String>();
    ArrayAdapter<String> mAdapter = null;
    ListView mListView = null;
//
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    //this.optionsMenu = menu;
    //MenuInflater inflater = getMenuInflater(); //ERROR<-----------
    getMenuInflater().inflate(R.menu.menu, menu);
    //return super.onCreateOptionsMenu(menu); // in Fragment cannot be applied <------------
    return true;
}
    //
    @Override
    public void onResume() {
        super.onResume();
        //
        UpdateUI();
        //
    }
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        //String[] values = new String[]{"TEst"};
        //
        //ContactsEmail.add("Martin");
        //
        UpdateUI();
        //
        // Item Click Listener for the listview
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
//
                System.out.print("Button pressed");
//
                String selectedItem = (String) parent.getItemAtPosition(position);
//
                Toast.makeText(getApplicationContext(), " Pressed " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        };
        //
        // Setting the item click listener for the listview
        mListView.setOnItemClickListener(itemClickListener);
        //
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    //
    private void UpdateUI() {
        //
        GetContacts();
        String[] values = new String[ContactsEmail.size()];
        ContactsEmail.toArray(values);
        //
        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        mListView = (ListView) findViewById(R.id.contactlist);
        mListView.setAdapter(mAdapter);
        //

    }
    //
    private ArrayList<String> GetContactsEmail(){
        //
        return ContactsEmail;
        //
    }
    //
    private void GetContacts() {
    //
        ContactsEmail.clear();
        //
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        String id = null, phone = null, email = null, note = null, orgName = null, title = null;
        String Phone1 = "unknown", Phone2 = "unknown", Phone3 = "unknown", type1 = "unknown", type2 = "unknown", type3 = "unknown";
        int size = cur.getCount();
        if (cur.getCount() > 0) {
            int cnt = 1;
            while (cur.moveToNext()) {
                email = "";
                cnt++;
                id = cur.getString(cur
                        .getColumnIndex(ContactsContract.Contacts._ID));
                email = cur
                        .getString(cur
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));


                if (Integer
                        .parseInt(cur.getString(cur
                                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)

                {
                    System.out.println("name : " + email);
                    Cursor pCur = cr
                            .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                            + " = ?", new String[]{id},
                                    null);

                    Phone1 = " ";
                    Phone2 = " ";
                    Phone3 = " ";
                    while (pCur.moveToNext()) {
                        String phonetype = pCur
                                .getString(pCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                        String MainNumber = pCur
                                .getString(pCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        if (phonetype.equalsIgnoreCase("1")) {
                            Phone1 = MainNumber;
                            type1 = "home";
                        } else if (phonetype.equalsIgnoreCase("2")) {
                            Phone2 = MainNumber;
                            type2 = "mobile";
                        } else {
                            Phone3 = MainNumber;
                            type3 = "work";
                        }
                    }
                    pCur.close();

                }
                Cursor addrCur = cr
                        .query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID
                                        + " = ?", new String[]{id},
                                null);
                if (addrCur.getCount() == 0) {
//                    addbuffer.append("unknown");
                } else {
                    int cntr = 0;
                    while (addrCur.moveToNext()) {

                        cntr++;
                        String poBox = addrCur
                                .getString(addrCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                        if (poBox == null) {
                            poBox = " ";
                        }
                        String street = addrCur
                                .getString(addrCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                        if (street == null) {
                            street = " ";
                        }
                        String neb = addrCur
                                .getString(addrCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.NEIGHBORHOOD));
                        if (neb == null) {
                            neb = " ";
                        }
                        String city = addrCur
                                .getString(addrCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                        if (city == null) {
                            city = " ";
                        }
                        String state = addrCur
                                .getString(addrCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                        if (state == null) {
                            state = " ";
                        }
                        String postalCode = addrCur
                                .getString(addrCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                        if (postalCode == null) {
                            postalCode = " ";
                        }
                        String country = addrCur
                                .getString(addrCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                        if (country == null) {
                            country = " ";
                        }

                        String type = addrCur
                                .getString(addrCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
                        if (type == null) {
                            type = " ";
                        }
                    }

                }

                addrCur.close();

                String noteWhere = ContactsContract.Data.CONTACT_ID
                        + " = ? AND " + ContactsContract.Data.MIMETYPE
                        + " = ?";
                String[] noteWhereParams = new String[]{
                        id,
                        ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};
                Cursor noteCur = cr.query(
                        ContactsContract.Data.CONTENT_URI, null,
                        noteWhere, noteWhereParams, null);

                note = " ";

                if (noteCur.moveToFirst()) {
                    note = noteCur
                            .getString(noteCur
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));

                    if (note == null) {
                        note = " ";
                    }
                }
                noteCur.close();
                String orgWhere = ContactsContract.Data.CONTACT_ID
                        + " = ? AND " + ContactsContract.Data.MIMETYPE
                        + " = ?";
                String[] orgWhereParams = new String[]{
                        id,
                        ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
                Cursor orgCur = cr.query(
                        ContactsContract.Data.CONTENT_URI, null,
                        orgWhere, orgWhereParams, null);
                orgName = " ";
                if (orgCur.moveToFirst()) {
                    orgName = orgCur
                            .getString(orgCur
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY));

                }
                if (orgName == null) {
                    orgName = " ";
                }
                orgCur.close();

                Cursor emailCur = cr
                        .query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID
                                        + " = ?", new String[]{id},
                                null);
                email = "unknown";
                while (emailCur.moveToNext()) {

                    email = emailCur
                            .getString(emailCur
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    String emailType = emailCur
                            .getString(emailCur
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

                    if (email == null) {
                        email = "unknown";
                    }
                    if (emailType.equalsIgnoreCase("1")) {
                    } else {
                    }
                }

                // add
                emailCur.close();
                //
                System.out.println("listcontact.ID =   "+id);
                System.out.println("listcontact.Email = "+email);
                ContactsEmail.add(email);
                //
            }
        }
        System.out.println("listcontact.Contact list empty");
    }
}
