package fr.acinq.eclair.wallet.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

import java.util.Observable;

public class SummaryPurchaseViewModel extends Observable {
  private Context context;
  public final ObservableField<String > summary = new ObservableField<>("");
  public final ObservableField<String > day = new ObservableField<>("");


  public SummaryPurchaseViewModel(Context context)
  {
    this.context = context;
  }

  public SummaryPurchaseViewModel(String text)
  {
      this.summary.set(text);
  }


}
