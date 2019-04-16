package me.kabi404.mikana.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;

import me.kabi404.mikana.R;
import me.kabi404.mikana.adapter.KanaRowsAdapter;
import me.kabi404.mikana.model.KanaManager;
import me.kabi404.mikana.model.Syllabary;

public final class SelectActivity extends AppCompatActivity {

    private KanaManager kanaManager;

    private ListView kanaRowsView;
    private RadioButton hiraganaRadio, katakanaRadio;
    private KanaRowsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        kanaManager = KanaManager.getInstance();

        setKanaRowsView((ListView) findViewById(R.id.kanaRows));
        setAdapter(new KanaRowsAdapter(this));

        setHiraganaRadio((RadioButton) findViewById(R.id.hiraganaRadio));
        setKatakanaRadio((RadioButton) findViewById(R.id.katakanaRadio));

    }

    public void onUpdatePressed(View view) {

        Syllabary currentSyllabary = getHiraganaRadio().isChecked() ? Syllabary.HIRAGANA : Syllabary.KATAKANA;
        kanaManager.setCurrentSyllabary(currentSyllabary);
        kanaManager.unselectKanas();

        for(int rowIndex = 0; rowIndex < getKanaRowsView().getChildCount(); rowIndex++) {
            View rowView = getKanaRowsView().getChildAt(rowIndex);

            CheckBox[] checks = new CheckBox[5];

            checks[0] = rowView.findViewById(R.id.check1);
            checks[1] = rowView.findViewById(R.id.check2);
            checks[2] = rowView.findViewById(R.id.check3);
            checks[3] = rowView.findViewById(R.id.check4);
            checks[4] = rowView.findViewById(R.id.check5);

            for(int columnIndex = 0; columnIndex < checks.length; columnIndex++) {
                if(checks[columnIndex].isChecked()) {
                    kanaManager.selectKana(rowIndex, columnIndex);
                }
            }
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void setToHiragana(View view) {
        kanaManager.setCurrentSyllabary(Syllabary.HIRAGANA);
        adapter.notifyDataSetChanged();
    }

    public void setToKatakana(View view) {
        kanaManager.setCurrentSyllabary(Syllabary.KATAKANA);
        adapter.notifyDataSetChanged();
    }

    // Getters & Setters

    public ListView getKanaRowsView() {
        return kanaRowsView;
    }

    public void setKanaRowsView(ListView kanaRowsView) {
        this.kanaRowsView = kanaRowsView;
    }

    public RadioButton getHiraganaRadio() {
        return hiraganaRadio;
    }

    public void setHiraganaRadio(RadioButton hiraganaRadio) {
        this.hiraganaRadio = hiraganaRadio;
    }

    public RadioButton getKatakanaRadio() {
        return katakanaRadio;
    }

    public void setKatakanaRadio(RadioButton katakanaRadio) {
        this.katakanaRadio = katakanaRadio;
    }

    public KanaRowsAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(KanaRowsAdapter adapter) {
        this.adapter = adapter;
        getKanaRowsView().setAdapter(adapter);
    }
}
