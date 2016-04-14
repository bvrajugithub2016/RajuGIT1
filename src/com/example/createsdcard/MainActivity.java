package com.example.createsdcard;

//Android supports also access to an external storage system e.g. the SD card. 
//All files and directories on the external storage system are readable for all applications with the correct permission.

/*
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
			Toast.makeText(this, "sdcard available", Toast.LENGTH_LONG).show();
		else
		{
			Toast.makeText(this, "sdcard not available", Toast.LENGTH_LONG).show();
			finish();
		}
	}
	
	//write file to external storage
	public void clickWriteButton(View v)
	{
		EditText et = (EditText) findViewById(R.id.editText1);
		
		File f = null;
		FileWriter fw = null;
		BufferedWriter bw= null;
		
		try
		{
			f = new File(Environment.getExternalStorageDirectory() + "/bluetooth");
			//getExternalStorageDirectory() returns File object which represents the sdcard directory
			
			if(!f.exists())
			{
				Toast.makeText(this, f.getPath() + " doesn't exist, so creating it", Toast.LENGTH_LONG).show();
				f = new File(Environment.getExternalStorageDirectory() + "/bluetooth");
				boolean success = f.mkdir();
				
				if(success)
					Toast.makeText(this, f.getPath() + " created successfully ", Toast.LENGTH_LONG).show();				
				else
				{
					Toast.makeText(this, f.getPath() + " sorry, can't be created", Toast.LENGTH_LONG).show();
					return;
				}
			}
			
			f = new File(Environment.getExternalStorageDirectory(), "bluetooth/myfile25.txt");
			
			fw = new FileWriter(f);  //for text file
			bw = new BufferedWriter( fw );
			bw.write(et.getText().toString());
			
			Toast.makeText(this, "Saved in the sdcard", Toast.LENGTH_LONG).show();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(bw != null)
			{
				try
				{
					bw.close();
					fw.close();
				}
				catch (IOException e)
				{	e.printStackTrace();
				}
			}
		}
		
	}
	
	//read file from external storage
	public void clickReadButton(View v)
	{
		EditText et = (EditText) findViewById(R.id.editText2);
		
		BufferedReader br= null;
		FileReader fr = null;
		
		try
		{
			File f = new File(Environment.getExternalStorageDirectory(), "bluetooth/myfile25.txt");
			
			if(!f.exists())
			{
				Toast.makeText(this, f.getPath() + " doesn't exist, have to saved the file?", Toast.LENGTH_LONG).show();
				return;
			}
			
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			
			String line;
			StringBuffer sb = new StringBuffer();
			
			while( ( line = br.readLine() ) != null)
				sb.append(line + "\n");
			
			et.setText(sb.toString());
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(br != null)
			{
				try
				{
					br.close();
					fr.close();
				}
				catch (IOException e)
				{	e.printStackTrace();
				}
			}
		}
	}

}
