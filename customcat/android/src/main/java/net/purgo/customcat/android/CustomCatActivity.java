package net.purgo.customcat.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import net.purgo.customcat.core.CustomCat;

public class CustomCatActivity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new CustomCat());
  }
}
