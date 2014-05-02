import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class PictChatClient {
	private MyCanvas	c;
	private BufferedWriter	bw;
	private BufferedReader	br;

	public static void main( String[] args ) {
		PictChatClient pcc = new PictChatClient();
		pcc.doEvent();
	}

	PictChatClient(){
		Frame f = new Frame( "PictChatClient" );
		f.setSize(640,480);
		f.addWindowListener( new WindowAdapter() {
			@Override public void windowClosing( WindowEvent e ) {
				try {
					bw.write( "quit" );
					bw.flush();
					bw.close();
					br.close();
				} catch( Exception e2 ) {}
				System.exit( 0 );
			}
		});
		c = new MyCanvas();
		c.addMouseListener( new MouseListener() {
			@Override public void mouseClicked( MouseEvent e ) {
				try {
					bw.write( "setOval " + e.getX() + " " + e.getY() + "\n" );
					bw.flush();
					c.setPoint( e.getX(), e.getY() );
				} catch( Exception e2 ) {}
			}
			@Override public void mouseEntered( MouseEvent e ) {}
			@Override public void mouseExited( MouseEvent e ) {}
			@Override public void mousePressed( MouseEvent e ) {}
			@Override public void mouseReleased( MouseEvent e ) {}
		});
		c.addMouseMotionListener( new MouseMotionListener() {
			@Override public void mouseDragged( MouseEvent e ) {
				try {
					bw.write( "setOval " + e.getX() + " " + e.getY() + "\n" );
					bw.flush();
					c.setPoint( e.getX(), e.getY() );
				} catch( Exception e2 ) {}
			}
			@Override public void mouseMoved( MouseEvent e ) {}
		});
		f.add( c );
		f.setVisible( true );
	}

	public void doEvent() {
		try {
			InetSocketAddress socketAddress =
				new InetSocketAddress( "localhost", 8000 );
			Socket socket = new Socket();
			socket.connect( socketAddress, 10000 );
			InetAddress inadr;
			if( ( inadr = socket.getInetAddress() ) != null ){
				System.out.println( "Connect to " + inadr );
			} else {
				System.out.println( "Connection failed" );
				System.exit( 0 );
			}
			bw = new BufferedWriter(
    			new OutputStreamWriter( socket.getOutputStream() ) );
			br = new BufferedReader(
    			new InputStreamReader( socket.getInputStream() ) );
		} catch( Exception e ) {}
	}
}
