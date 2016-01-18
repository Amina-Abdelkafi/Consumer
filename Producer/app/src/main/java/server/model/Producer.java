package server.model;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Producer<T> implements Runnable {

  private String sendToAddress = "0.0.0.0";
  private int    sendToPort    = 0;
  private T      objectToSend;

  public Producer(String sendToAddress, int sendToPort, T objectToSend) {
    this.sendToAddress = sendToAddress;
    this.sendToPort = sendToPort;
    this.objectToSend = objectToSend;
    Log.e("111", sendToAddress + "  " + sendToPort);

  }

  @Override
  public void run() {
    try {
      Log.e("", sendToAddress + "  " + sendToPort);

      Socket socket = new Socket(sendToAddress, sendToPort);
      Log.e("", sendToAddress + "  " + sendToPort);
      ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(objectToSend);
      oos.flush();
      oos.close();
      socket.close();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

}
