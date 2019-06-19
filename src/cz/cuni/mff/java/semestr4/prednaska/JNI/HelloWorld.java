package cz.cuni.mff.java.semestr4.prednaska.JNI;

class HelloWorld {
  
  public native void displayHelloWorld();

  static {
    System.loadLibrary("hello");
  }

  public static void main(String[] args) {
    new HelloWorld().displayHelloWorld();
  }
  
}
