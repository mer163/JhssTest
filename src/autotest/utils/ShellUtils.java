package autotest.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellUtils {
	
	public static Process cmd(String commnand){
		return process(commnand);
	}
	
	public static Process adb(String command) {
		return process("adb " + command);
	}

	public static Process adb(String command,String device) {
		Process ps;
		if(device==null){
			ps = process("adb " + command);
		}else{
			ps = process("adb -s "+device+ " " + command);
		}
		return ps;
	}
	
	public static Process shell(String command,String device) {
		Process ps;
		if(device==null){
			ps =  process("adb shell " + command);
		}else{
			ps = process("adb -s "+device +" shell " + command);
		}
		return ps;
	}
	
	public static Process shell(String command) {
		return process("adb shell " + command);
	}
	
	public static void restartADB(){
		try{
			cmd("kill-server");
			cmd("devices");
		}finally{
		}	
	}
	
	public static BufferedReader shellOut(Process ps) {
		BufferedInputStream in = new BufferedInputStream(ps.getInputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		return br;
	}

	public static String getShellOut(Process ps) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = shellOut(ps);
		String line;

		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
//			sb.append(line + System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	private static Process process(String command) {
		Process ps = null;
		try {
			ps = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ps;
	}
}
