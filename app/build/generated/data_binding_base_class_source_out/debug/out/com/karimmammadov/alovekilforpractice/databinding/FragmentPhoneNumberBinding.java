// Generated by view binder compiler. Do not edit!
package com.karimmammadov.alovekilforpractice.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.karimmammadov.alovekilforpractice.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentPhoneNumberBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final AppCompatButton btnEnterNumber;

  @NonNull
  public final EditText phoneEdit;

  @NonNull
  public final ProgressBar progressBar;

  private FragmentPhoneNumberBinding(@NonNull LinearLayout rootView,
      @NonNull AppCompatButton btnEnterNumber, @NonNull EditText phoneEdit,
      @NonNull ProgressBar progressBar) {
    this.rootView = rootView;
    this.btnEnterNumber = btnEnterNumber;
    this.phoneEdit = phoneEdit;
    this.progressBar = progressBar;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentPhoneNumberBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentPhoneNumberBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_phone_number, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentPhoneNumberBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_enterNumber;
      AppCompatButton btnEnterNumber = ViewBindings.findChildViewById(rootView, id);
      if (btnEnterNumber == null) {
        break missingId;
      }

      id = R.id.phoneEdit;
      EditText phoneEdit = ViewBindings.findChildViewById(rootView, id);
      if (phoneEdit == null) {
        break missingId;
      }

      id = R.id.progressBar;
      ProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      return new FragmentPhoneNumberBinding((LinearLayout) rootView, btnEnterNumber, phoneEdit,
          progressBar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
