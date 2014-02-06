package net.purgo.customcat.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import net.purgo.customcat.core.CustomCat;

public class CustomCatJava {

  public static void main(String[] args) {
    JavaPlatform.Config config = new JavaPlatform.Config();
    // use config to customize the Java platform, if needed
    JavaPlatform.register(config);
    PlayN.run(new CustomCat());
  }
}
