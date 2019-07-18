// Generated code from Butter Knife. Do not modify!
package com.bw.home;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeActivity_ViewBinding implements Unbinder {
  private HomeActivity target;

  private View view7f0c007a;

  private View view7f0c0101;

  private View view7f0c0045;

  @UiThread
  public HomeActivity_ViewBinding(HomeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public HomeActivity_ViewBinding(final HomeActivity target, View source) {
    this.target = target;

    View view;
    target.frame = Utils.findRequiredViewAsType(source, R.id.frame, "field 'frame'", FrameLayout.class);
    view = Utils.findRequiredView(source, R.id.home, "field 'home' and method 'onClick'");
    target.home = Utils.castView(view, R.id.home, "field 'home'", RadioButton.class);
    view7f0c007a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.video, "field 'video' and method 'onClick'");
    target.video = Utils.castView(view, R.id.video, "field 'video'", RadioButton.class);
    view7f0c0101 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.circle, "field 'circle' and method 'onClick'");
    target.circle = Utils.castView(view, R.id.circle, "field 'circle'", RadioButton.class);
    view7f0c0045 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.frame = null;
    target.home = null;
    target.video = null;
    target.circle = null;

    view7f0c007a.setOnClickListener(null);
    view7f0c007a = null;
    view7f0c0101.setOnClickListener(null);
    view7f0c0101 = null;
    view7f0c0045.setOnClickListener(null);
    view7f0c0045 = null;
  }
}
