package com.gmr.server;

import com.gmr.server.util.HttpSessionFactory;

/**
 * session检查Thread
 * 
 * @author gmr
 *
 */
public class SessionCheckThread extends Thread {

	@Override
	public void run() {
		while (true) {
			try{
				HttpSessionFactory.check();
			}catch(Exception e){
				e.printStackTrace();
			}
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
