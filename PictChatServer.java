import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class PictChatServer {
	private MyCanvas c;
	public static void main( String[] args ) {
		PictChatServer pcs = new PictChatServer();
		pcs.doEvent();
	}

	PictChatServer() {
		Frame f = new Frame( "PictChatServer" );
		f.setSize( 640, 480 );
		f.addWindowListener( new WindowAdapter() {
			@Override public void windowClosing( WindowEvent e ) {
				System.exit( 0 );
			}
		} );
		c = new MyCanvas();
		f.add( c );
		f.setVisible( true );
	}

	public void doEvent(){
		try{
			ServerSocket ss = new ServerSocket( 8000 );
			while( true ) {
				Socket socket = ss.accept();
				BufferedReader br = new BufferedReader(
    				new InputStreamReader( socket.getInputStream() ) );
				BufferedWriter bw = new BufferedWriter(
    				new OutputStreamWriter( socket.getOutputStream() ) );
				while( true ) {
					String command = br.readLine();
					String[] words = command.split( " " );
					System.out.println( command );
					if( words[0].equalsIgnoreCase( "setOval" ) ) {
						try {
							c.setPoint(
    							 Integer.parseInt( words[1] ),
    							 Integer.parseInt( words[2] ) );
						} catch( Exception e ) {}
					} else if( words[0].equalsIgnoreCase( "changeMode" ) ) {
						//描画方式を変更する処理
					}
					else if( words[0].equalsIgnoreCase( "quit" ) ) {
						break;
					}
				}
				bw.close();
				br.close();
				socket.close();
			}
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
}
