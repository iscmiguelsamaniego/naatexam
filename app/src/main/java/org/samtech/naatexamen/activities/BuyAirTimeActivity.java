package org.samtech.naatexamen.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.samtech.naatexamen.R;
import org.samtech.naatexamen.callbacks.CallbackUtilAlert;
import org.samtech.naatexamen.model.database.DatabaseManager;
import org.samtech.naatexamen.model.database.Sales;
import org.samtech.naatexamen.utils.AlertUtils;
import org.samtech.naatexamen.utils.ImageUtils;
import org.samtech.naatexamen.utils.MessagesUtils;

import static org.samtech.naatexamen.utils.Keys.SALESID;

public class BuyAirTimeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private EditText username, password;
    private Button continueBtn;
    private int drawableSrc;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_buy_airtime);

        imageView = findViewById(R.id.act_buy_airtime_image_header);
        username = findViewById(R.id.act_buy_airtime_username);
        password = findViewById(R.id.act_buy_airtime_password);
        continueBtn = findViewById(R.id.act_buy_airtime_continue_btn);

        continueBtn.setOnClickListener(this);
        dbManager = new DatabaseManager(this);
        Intent intent = getIntent();
        int idsales = intent.getIntExtra(SALESID, 0);
        getAndSetValuesFromDB(idsales);

    }

    private void getAndSetValuesFromDB(int paramId) {
        Sales sales = dbManager.getSale(paramId);

        if (sales != null) {
            drawableSrc = ImageUtils.getDrawable(sales.getBcname());
            ImageUtils.setImage(this, imageView, drawableSrc);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_buy_airtime_continue_btn:
                showAlert();
                break;
        }
    }

    private void showAlert() {
        AlertUtils.showConfirmAlert(this,
                R.string.do_reload,
                R.string.accept,
                R.string.cancel,
                drawableSrc,
                false,
                new CallbackUtilAlert() {
                    @Override
                    public void onClickAlert(boolean isClicked, int actionType) {
                        MessagesUtils.showToast(BuyAirTimeActivity.this, "listo");
                    }
                });
    }
}
