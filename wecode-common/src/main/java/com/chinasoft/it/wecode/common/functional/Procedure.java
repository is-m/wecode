package com.chinasoft.it.wecode.common.functional;

@FunctionalInterface
public interface Procedure {

  void run();

  default Procedure andThen(Procedure after) {
    return () -> {
      this.run();
      after.run();
    };
  }

  default Procedure compose(Procedure before) {
    return () -> {
      before.run();
      this.run();
    };
  }

  /*
  public static void main(String[] args) {
    Procedure procedure1 = () -> System.out.print("Hello");
    Procedure procedure2 = () -> System.out.print("World");

    procedure1.andThen(procedure2).run(); // HelloWorld
    System.out.println();
    procedure1.compose(procedure2).run(); // WorldHello

  }
  */
}
