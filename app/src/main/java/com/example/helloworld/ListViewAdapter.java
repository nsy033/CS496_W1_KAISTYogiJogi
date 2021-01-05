package com.example.helloworld;

import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.helloworld.MainActivity.contactItems;
import static com.example.helloworld.Page1Fragment.adapter;
import static com.example.helloworld.Page1Fragment.listview;
import static com.example.helloworld.Page1Fragment.newcontact;

//import static com.example.helloworld.MainActivity.contactList;

public class ListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    public static ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    // ListViewAdapter의 생성자
    public ListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.textView2) ;
/*
        ImageButton btn = (ImageButton) convertView.findViewById(R.id.button);
        btn.setScaleType(ImageButton.ScaleType.FIT_CENTER);

        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                ListViewItem con = listViewItemList.get(position);
                Intent tt = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+con.getTitle()));
                context.startActivity(tt);
            }
        });
        btn.setOnLongClickListener(new View.OnLongClickListener(){
            public boolean onLongClick(View view) {
                ListViewItem con = listViewItemList.get(position);
                Intent tt = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+con.getDesc()));
                context.startActivity(tt);
                return true;
            }
        });
*/

        convertView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListViewItem con = listViewItemList.get(position);
                AlertDialog.Builder adb = new AlertDialog.Builder(context, R.style.MyDialogTheme);
                adb.setTitle(con.getTitle())
                        .setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("DIAL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dial(context, position);
                            }
                        })
                        .setNegativeButton("CALL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                call(context, position);
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

        convertView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                ListViewItem con = listViewItemList.get(position);

                final LinearLayout linear = (LinearLayout) View.inflate(context, R.layout.contactdialog, null);
                AlertDialog.Builder adb = new AlertDialog.Builder(context, R.style.MyDialogTheme);

                EditText edt = linear.findViewById(R.id.et1);
                adb.setView(linear);
                edt.setText(con.getTitle());

                EditText edt2 = linear.findViewById(R.id.et2);
                adb.setView(linear);
                edt2.setText(con.getDesc());

                EditText edt3 = linear.findViewById(R.id.et3);
                adb.setView(linear);
                edt3.setText(con.getMail());

                EditText edt4 = linear.findViewById(R.id.et4);
                adb.setView(linear);
                edt4.setText(con.getAddress());

                adb.setTitle("Edit Contact")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String name = edt.getText().toString();
                                String number = edt2.getText().toString();
                                String mail = edt3.getText().toString();
                                String add = edt4.getText().toString();

                                ListViewItem tmp = new ListViewItem();
                                tmp.setIcon(MainActivity.sized[0]);
                                tmp.setTitle(name);
                                tmp.setDesc(number);
                                tmp.setMail(mail);
                                tmp.setAddress(add);
                                listViewItemList.set(position, tmp);

                                ContactItem temp = new ContactItem();
                                temp.setUser_name(name);
                                temp.setUser_phNumber(number);
                                temp.setMail(mail);
                                temp.setAddress(add);
                                contactItems.set(position, temp);

                                adapter.notifyDataSetChanged();
                                listview.setAdapter(adapter);

                                ContentResolver cr = context.getContentResolver();
                                deleteContact(cr, con.getDesc());

                                ContactItem contactItem = new ContactItem();
                                contactItem.setUser_name(name);
                                contactItem.setUser_phNumber(number);
                                contactItem.setMail(mail);
                                contactItem.setAddress(add);

                                newcontact = new ArrayList<>();
                                ContentProviderOperation.Builder op =
                                        ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                                                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                                                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null);
                                newcontact.add(op.build());

                                op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contactItem.getUser_name());
                                newcontact.add(op.build());

                                op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contactItem.getUser_phNumber())
                                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, "TYPE_MOBILE");
                                newcontact.add(op.build());

                                op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                                        .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, contactItem.getMail())
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
                                    context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, newcontact);
                                } catch (OperationApplicationException e) {
                                    e.printStackTrace();
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int index = 0;
                                for (int i = 0; i < contactItems.size(); i++) {
                                    ContactItem tmp = contactItems.get(i);
                                    String tmp_name = tmp.getUser_name();

                                    if (tmp_name.equals(con.getTitle())) {
                                        index = i;
                                        break;
                                    }
                                }
                                contactItems.remove(index);

                                for (int i = 0; i < listViewItemList.size(); i++) {
                                    ListViewItem tmp = listViewItemList.get(i);
                                    String tmp_name = tmp.getTitle();

                                    if (tmp_name.equals(con.getTitle())) {
                                        index = i;
                                        break;
                                    }
                                }
                                listViewItemList.remove(index);

                                adapter.notifyDataSetChanged();
                                listview.setAdapter(adapter);

                                ContentResolver cr = context.getContentResolver();
                                deleteContact(cr, con.getDesc());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

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
                return true;
            }
        });

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageBitmap(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText(listViewItem.getDesc());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Bitmap icon, String title, String desc, String mail, String address) {
        ListViewItem item = new ListViewItem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);
        item.setMail(mail);
        item.setAddress(address);

        listViewItemList.add(item);
    }

    public void clearItem() {
        listViewItemList = new ArrayList<ListViewItem>();
    }


    public void dial(Context context, int position) {
        ListViewItem con = listViewItemList.get(position);
        Intent tt = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+con.getDesc()));
        context.startActivity(tt);
    }

    public void call(Context context, int position) {
        ListViewItem con = listViewItemList.get(position);
        Intent tt = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+con.getDesc()));
        context.startActivity(tt);
    }

    public static void deleteContact(ContentResolver contactHelper, String number) {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        String[] args = new String[] { String.valueOf(getContactID(contactHelper,
                number))};
        ops.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
                .withSelection(ContactsContract.RawContacts.CONTACT_ID + "=?", args).build());
        try {
            contactHelper.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }

    private static long getContactID(ContentResolver contactHelper, String number) {
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        String[] projection = { ContactsContract.PhoneLookup._ID };
        Cursor cursor = null;
        try {
            cursor = contactHelper.query(contactUri, projection, null, null,null);
            if (cursor.moveToFirst()) {
                int personID = cursor.getColumnIndex(ContactsContract.PhoneLookup._ID);
                return cursor.getLong(personID);
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        return -1;
    }
}