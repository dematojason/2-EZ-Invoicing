package GUI;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

import GUI.GUI_Frame;

public class App implements Runnable{

	private boolean running = false;
	private Thread thread;
	protected FileChannel channel;
	
	
	public synchronized void start() {
		if(running == true) 
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
		}
	
	@Override
	public void run() {
		long start_time = System.currentTimeMillis();
		long minute = 60000;
		while(running) {
			long end_time = System.currentTimeMillis();
			if(end_time-start_time > minute) {
				start_time = System.currentTimeMillis();
				tick();
			}
		}
	}
	
	private void tick() {
		try {
			check_file_status("somefile");
			// can add more of these ^ for more files
		} catch (IOException e) {
			//shits fuckin closed dawgggggg
		}
		
	}
	
	private void check_file_status(String file_path) throws IOException {
		File file = new File(file_path);
		@SuppressWarnings("resource")
		FileChannel file_channel = new RandomAccessFile(file, "rw").getChannel();

		FileLock lock = file_channel.lock();
		try {
		    lock = file_channel.tryLock(); // passes without exception, we got it on lock
		    out_to_file(file);
		} catch (OverlappingFileLockException e) {
		    // File is open by someone else
		} finally {
			lock.release();
		}
	}
	private void out_to_file(File file) {
		// Open middle file first
		// take data for the second file
		// close middle file
		// open excel wb 
		// add all entries for that file
	}

	public static void main(String[] args) {
		new GUI_Frame(new App());
	}
}
