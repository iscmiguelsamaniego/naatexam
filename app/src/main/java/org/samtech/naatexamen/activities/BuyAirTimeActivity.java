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
import org.samtech.naatexamen.utils.RegexUtils;
import org.samtech.naatexamen.utils.StringUtils;

import java.util.Objects;

import static org.samtech.naatexamen.utils.Keys.POSITIVE;
import static org.samtech.naatexamen.utils.Keys.SALESID;

public class BuyAirTimeActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText phonenumberEditText, amountEditText;
    private int drawableSrc;
    DatabaseManager dbManager;
    int idsales;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_buy_airtime);

        ImageView imageView = findViewById(R.id.act_buy_airtime_image_header);
        phonenumberEditText = findViewById(R.id.act_buy_airtime_phonenumber);
        amountEditText = findViewById(R.id.act_buy_airtime_amount);
        Button continueBtn = findViewById(R.id.act_buy_airtime_continue_btn);

        continueBtn.setOnClickListener(this);
        dbManager = new DatabaseManager(this);
        Intent intent = getIntent();
        idsales = intent.getIntExtra(SALESID, 0);

        if (queryValues(idsales) != null) {
            drawableSrc = ImageUtils.getDrawable(Objects.requireNonNull
                    (queryValues(idsales)).getBcname());
            ImageUtils.setImage(this, imageView, drawableSrc);
        }
    }

    private Sales queryValues(int paramId) {
        Sales sales = dbManager.getSale(paramId);
        if (sales != null) {
            return sales;
        }
        return null;
    }

    private Sales updateSales() {
        String phoneAux = StringUtils.getEText(phonenumberEditText);
        String amountAux = StringUtils.getEText(amountEditText);

        if (queryValues(idsales) != null) {
            Sales saleValues = queryValues(idsales);
            assert saleValues != null;
            Sales sale = new Sales(saleValues.getId(), saleValues.getBcname(), phoneAux, amountAux,
                    saleValues.getConcept());
            dbManager.updateSale(sale);

            if (queryValues(saleValues.getId()) != null) {
                return queryValues(saleValues.getId());
            }
        }
        return null;
    }

    private boolean isValidForm() {
        String phoneAux = StringUtils.getEText(phonenumberEditText);
        String amountAux = StringUtils.getEText(amountEditText);
        int amountAuxMin = Integer.parseInt(!amountAux.isEmpty() ? amountAux : "0");

        if (RegexUtils.isNotemptyValidate(phoneAux)) {
            if (RegexUtils.isNotemptyValidate(amountAux)) {
                if(phoneAux.length() >= 10) {
                    if (RegexUtils.isNotemptyValidate(amountAux)) {
                        if(amountAuxMin >= 20) {
                            return true;
                        }else{
                            MessagesUtils.showToast(this,
                                    "El monto minimo es de 20");
                        }
                    } else {
                        MessagesUtils.showToast(this,
                                "Por favor registra una cantidad");
                    }
                }else{
                    MessagesUtils.showToast(this,
                            "Longitud del número telefónico invalida");
                }
            } else {
                MessagesUtils.showToast(this,
                        "Ingrese una cantidad");
            }
        } else {
            MessagesUtils.showToast(this,
                    "Por favor registra un némero telefónico");
        }
        return false;
    }

    private void showConfirmAlert() {

        String details = "Con el número: \n" + StringUtils.getEText(phonenumberEditText)
                + " \n y el monto: \n $" + StringUtils.getEText(amountEditText);

        AlertUtils.showConfirmAlert(this,
                R.string.do_reload,
                details,
                R.string.accept,
                R.string.cancel,
                drawableSrc,
                false,
                new CallbackUtilAlert() {
                    @Override
                    public void onClickAlert(boolean isClicked, int actionType) {
                        if (updateSales() != null) {
                            if(actionType == POSITIVE) {
                                updateSales();
                                showReadyAlert();
                            }
                        }
                    }
                });
    }

    private void showReadyAlert() {
        AlertUtils.showReadyAlert(this,
                R.string.ready_reload,
                R.string.accept,
                R.drawable.ic_done,
                false,
                new CallbackUtilAlert() {
                    @Override
                    public void onClickAlert(boolean isClicked, int actionType) {
                        clearForm();
                        finish();
                    }
                });
    }
    private void clearForm(){
        phonenumberEditText.setText("");
        amountEditText.setText("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.act_buy_airtime_continue_btn:
                if (isValidForm()) {
                    showConfirmAlert();
                }
                break;
        }
    }

}
