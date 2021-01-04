package com.example.helloworld;

import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.DialogInterface;
import android.content.OperationApplicationException;
import android.graphics.Color;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import static com.example.helloworld.MainActivity.contactItems;
import static com.example.helloworld.MainActivity.iconuser;

//import static com.example.helloworld.MainActivity.contactList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Page1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Page1Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static ArrayList<ContentProviderOperation> newcontact = new ArrayList<>();
    static ListView listview;
    static ListViewAdapter adapter = new ListViewAdapter();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Page1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Page1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Page1Fragment newInstance(String param1, String param2) {
        Page1Fragment fragment = new Page1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page1, null) ;
        contactItems.clear();
        ((MainActivity) getContext()).getContactList();

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, LIST_MENU) ;

        //ListView listview = (ListView) view.findViewById(R.id.listview1);
        //listview.setAdapter(adapter) ;

        listview = (ListView) view.findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        ImageButton btn = (ImageButton) view.findViewById(R.id.button2);
        btn.setScaleType(ImageButton.ScaleType.FIT_CENTER);
        ImageButton btn2 = (ImageButton) view.findViewById(R.id.button3);
        btn2.setScaleType(ImageButton.ScaleType.FIT_CENTER);
        ImageButton btn3 = (ImageButton) view.findViewById(R.id.button4);
        btn3.setScaleType(ImageButton.ScaleType.FIT_CENTER);

        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                //Toast.makeText(getContext(), "helloworld", Toast.LENGTH_LONG).show();
                final LinearLayout linear = (LinearLayout) View.inflate(getActivity(), R.layout.contactdialog, null);
                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                adb.setView(linear)
                        .setTitle("New Contact")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                EditText edt = (EditText) linear.findViewById(R.id.et1);
                                String name = edt.getText().toString();
                                if(name.length()>0) {
                                    EditText edt2 = (EditText) linear.findViewById(R.id.et2);
                                    String number = edt2.getText().toString();
                                    EditText edt3 = (EditText) linear.findViewById(R.id.et3);
                                    String email = edt3.getText().toString();
                                    EditText edt4 = (EditText) linear.findViewById(R.id.et4);
                                    String address = edt4.getText().toString();

                                    ContactItem con = new ContactItem();
                                    con.setUser_name(name);
                                    con.setUser_phNumber(number);
                                    con.setMail(email);
                                    con.setAddress(address);

                                    contactItems.add(con);

                                    adapter.addItem(iconuser, con.getUser_name(), con.getUser_phNumber(),
                                            con.getMail(), con.getAddress());
                                    adapter.notifyDataSetChanged();
                                    listview.setAdapter(adapter);

                                    newcontact = new ArrayList<>();
                                    ContentProviderOperation.Builder op =
                                            ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                                                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                                                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null);
                                    newcontact.add(op.build());

                                    op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                                            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, con.getUser_name());
                                    newcontact.add(op.build());

                                    op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, con.getUser_phNumber())
                                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, "TYPE_MOBILE");
                                    newcontact.add(op.build());

                                    op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                                            .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, con.getMail())
                                            .withValue(ContactsContract.CommonDataKinds.Email.TYPE, "TYPE_WORK");

                                    newcontact.add(op.build());

                                    op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
                                            .withValue(ContactsContract.CommonDataKinds.StructuredPostal.CITY, con.getAddress())
                                            .withValue(ContactsContract.CommonDataKinds.StructuredPostal.TYPE, "TYPE_HOME");

                                    op.withYieldAllowed(true);
                                    newcontact.add(op.build());

                                    try {
                                        getContext().getContentResolver().applyBatch(ContactsContract.AUTHORITY, newcontact);
                                    } catch (OperationApplicationException e) {
                                        e.printStackTrace();
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog finalDialog = adb.create();
                finalDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        finalDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#6E6557"));
                        finalDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#6E6557"));
                        finalDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.parseColor("#6E6557"));
                    }
                });
                finalDialog.show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                //Toast.makeText(getContext(), "helloworld", Toast.LENGTH_LONG).show();

                final LinearLayout linear = (LinearLayout) View.inflate(getContext(), R.layout.searchdialog, null);
                AlertDialog.Builder adb = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme);
                EditText edt = linear.findViewById(R.id.et5);
                adb.setView(linear);

                adb.setTitle("Search")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String name = edt.getText().toString();
                                int[] index = new int[1000];
                                int count=0;
                                //edt.setText("Helloworld");

                                for (int i = 0; i < contactItems.size(); i++) {
                                    ContactItem tmp = contactItems.get(i);
                                    String tmp_name = tmp.getUser_name();

                                    if (tmp_name.toLowerCase().contains(name.toLowerCase())) {
                                        index[count++] = i;
                                    }
                                }

                                if (count > 0) {
                                    adapter.clearItem();
                                    for(int i=0;i<count; i++) {
                                        ContactItem con = contactItems.get(index[i]);
                                        //contactItems = new ArrayList<>();
                                        //contactItems.add(con);
                                        adapter.addItem(iconuser, con.getUser_name(), con.getUser_phNumber(),
                                                con.getMail(), con.getAddress());
                                    }
                                }
                                else {
                                    adapter.clearItem();
                                    //contactItems = new ArrayList<>();
                                }

                                adapter.notifyDataSetChanged();
                                listview.setAdapter(adapter);

                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog finalDialog = adb.create();
                finalDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        finalDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#6E6557"));
                        finalDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#6E6557"));
                        finalDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.parseColor("#6E6557"));
                    }
                });
                finalDialog.show();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                adapter.clearItem();
                for(int i = 0; i< contactItems.size() ; i++){

                    ContactItem ci = contactItems.get(i);
                    adapter.addItem(iconuser, ci.getUser_name(), ci.getUser_phNumber(),
                            ci.getMail(), ci.getAddress());

                }
                adapter.notifyDataSetChanged();
                listview.setAdapter(adapter);
            }
        });

        adapter.clearItem();
        for(int i = 0; i< contactItems.size() ; i++){

            ContactItem ci = contactItems.get(i);
            adapter.addItem(iconuser, ci.getUser_name(), ci.getUser_phNumber(),
                    ci.getMail(), ci.getAddress());

        }
        return view ;
    }

}