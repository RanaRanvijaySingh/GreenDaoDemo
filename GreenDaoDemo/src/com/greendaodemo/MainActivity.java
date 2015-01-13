package com.greendaodemo;

import java.util.List;

import com.greendaodemo.DaoMaster.DevOpenHelper;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener{

	private TextView textView;
	private Button buttonInsert,buttonDelete;
	private DevOpenHelper databaseHelper;
	private SQLiteDatabase sqDatabase;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initializeComponents();
		initializeDatabase();
	}

	private void initializeComponents() {
		textView 	= (TextView)findViewById(R.id.textView);
		buttonInsert= (Button)findViewById(R.id.buttonInsert);
		buttonDelete= (Button)findViewById(R.id.buttonDelete);
		buttonInsert.setOnClickListener(this);
		buttonDelete.setOnClickListener(this);
	}

	private void initializeDatabase() {
		databaseHelper = new DevOpenHelper(getApplicationContext(), "green_dao_demo.db", null);
		sqDatabase = databaseHelper.getWritableDatabase();
		DaoMaster.createAllTables(sqDatabase, true);
		textView.setText(getAllTableData());
	}

	private User getUserModel() {
		User mUser = new User();
		mUser.setName("Ranvijay");
		mUser.setEmail("rana@gmail.com");
		mUser.setPhone_number("9876543212");
		return mUser;
	}

	@Override
	public void onClick(View view) {
		
		switch (view.getId()) {
		case R.id.buttonInsert:
			onClickInsertButton();
			break;
		case R.id.buttonDelete:
			onClickDeleteButton();
			break;

		default:
			break;
		}
		textView.setText(getAllTableData());
	}

	private void onClickInsertButton() {
		DaoMaster daoMaster = new DaoMaster(sqDatabase);
		DaoSession daoSession = daoMaster.newSession();
		UserDao userDao = daoSession.getUserDao();
		userDao.insert(getUserModel());
	}

	private void onClickDeleteButton() {
		DaoMaster daoMaster = new DaoMaster(sqDatabase);
		DaoSession daoSession = daoMaster.newSession();
		UserDao userDao = daoSession.getUserDao();
		User mUser = getLastDataBaseEntry();
		if (mUser != null) {
			userDao.delete(mUser);
		}else {
			Toast.makeText(getApplicationContext(), "No more entry to delete", Toast.LENGTH_SHORT).show();
		}
	}
	
	private User getLastDataBaseEntry() {
		DaoMaster daoMaster = new DaoMaster(sqDatabase);
		DaoSession daoSession = daoMaster.newSession();
		UserDao userDao = daoSession.getUserDao();
		List<User> listUser = userDao.loadAll();
		if (listUser != null && listUser.size() > 0) {
			return listUser.get(listUser.size() -1);
		}
		return null;
	}

	private String getAllTableData(){
		String tableData = "Table Data";
		DaoMaster daoMaster = new DaoMaster(sqDatabase);
		DaoSession daoSession = daoMaster.newSession();
		UserDao userDao = daoSession.getUserDao();
		List<User> listUser = userDao.loadAll();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < listUser.size(); i++) {
			stringBuffer.append(listUser.get(i).getName());
			stringBuffer.append("\t\t");
			stringBuffer.append(listUser.get(i).getPhone_number());
			stringBuffer.append("\t");
			stringBuffer.append(listUser.get(i).getEmail());
			stringBuffer.append("\n");
		}
		tableData = stringBuffer.toString();
		if (tableData.trim().equalsIgnoreCase("")) {
			tableData = "Table Data";
		}
		return tableData;
	}
	
	@Override
	protected void onDestroy() {
		sqDatabase.close();
		databaseHelper.close();
		super.onDestroy();
	}
}
