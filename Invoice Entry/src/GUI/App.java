package GUI;

public class App implements Runnable{

	private boolean running = false;
	private Thread thread;
	
	public synchronized void start() {
		if(running == true) 
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
		}
	
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
		// if IsOpened == true; return;
		//else export();
	}

	public static void main(String[] args) {
		new GUI_Frame(new App());
	}
}
