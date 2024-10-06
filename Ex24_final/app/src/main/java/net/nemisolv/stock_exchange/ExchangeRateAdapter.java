package net.nemisolv.stock_exchange;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ExchangeRateAdapter extends ArrayAdapter<ExchangeRate> {
    private final Context context;
    private final List<ExchangeRate> exchangeRates;

    public ExchangeRateAdapter(Context context, List<ExchangeRate> exchangeRates) {
        super(context, 0, exchangeRates);
        this.context = context;
        this.exchangeRates = exchangeRates;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ExchangeRate exchangeRate = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        ImageView imgThumbnail = convertView.findViewById(R.id.img_thumbnail);
        TextView textCurrencyType = convertView.findViewById(R.id.text_currency_type);
        TextView textBuyCash = convertView.findViewById(R.id.text_buy_cash);
        TextView textSellCash = convertView.findViewById(R.id.text_sell_cash);

        textCurrencyType.setText(exchangeRate.getCurrencyType());
        textBuyCash.setText("Buy Cash: " + exchangeRate.getBuyCash());
        textSellCash.setText("Sell Cash: " + exchangeRate.getSellCash());

        Bitmap thumbnail = exchangeRate.getThumbnail();
        if (thumbnail != null) {
            imgThumbnail.setImageBitmap(thumbnail);
        }

        return convertView;
    }
}