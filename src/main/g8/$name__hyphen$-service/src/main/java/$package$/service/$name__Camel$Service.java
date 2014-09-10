package $package$.service;

/**
 * The $name$ service.
 */
public class $name;format="Camel"$Service {

  public void run(String[] args) throws Exception {
    while(true) {
      System.out.println("still running...");
      Thread.sleep(5000);
    }
  }

  public boolean test() {
    return true;
  }

  public static void main(String[] args) throws Exception {
    new $name;format="Camel"$Service().run(args);
  }
}
