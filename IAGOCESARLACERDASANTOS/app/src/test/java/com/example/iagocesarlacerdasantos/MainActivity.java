package com.example.iagocesarlacerdasantos;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private TextView nameText, surnameText, enrollmentText, finalMessage;
    private GridLayout keyGrid;
    private ArrayList<Button> keyButtons = new ArrayList<>();
    private String namePlaceholder = "IAGO CESAR";
    private String surnamePlaceholder = "LACERDA SANTOS";
    private String enrollmentPlaceholder = "200028764";
    private String name = "**** *****"; // Correspondendo ao tamanho de "IAGO CESAR"
    private String surname = "******* ******"; // Correspondendo ao tamanho de "LACERDA SANTOS"
    private String enrollment = "*********"; // Correspondendo ao tamanho de "200028764"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa os componentes de interface
        nameText = findViewById(R.id.nameText);
        surnameText = findViewById(R.id.surnameText);
        enrollmentText = findViewById(R.id.enrollmentText);
        finalMessage = findViewById(R.id.finalMessage);
        keyGrid = findViewById(R.id.keyGrid);

        // Inicializa os botões e configurações
        initializeKeyButtons();
        setupButtons();

        // Exibe os asteriscos iniciais nos campos de texto
        updateTextFields();
    }

    // Método para atualizar os campos de texto com asteriscos
    private void updateTextFields() {
        nameText.setText("Nome: " + name);
        surnameText.setText("Sobrenome: " + surname);
        enrollmentText.setText("Matrícula: " + enrollment);
    }

    // Método para inicializar os botões de teclas no GridLayout (4x8)
    private void initializeKeyButtons() {
        keyGrid.setColumnCount(4); // Configura a grade para ter 4 colunas
        keyGrid.setRowCount(8); // Configura a grade para ter 8 linhas

        // Teclas correspondendo apenas aos caracteres presentes nos campos
        String[] keys = {"I", "A", "G", "O", "C", "E", "S", "R",
                "L", "D", "N", "T", "6", "2", "0", "8",
                "7", "4", "", ""}; // Inclui espaço como caractere

        for (String key : keys) {
            Button button = new Button(this);
            button.setText(key);
            button.setTextColor(Color.BLACK); // Ajuste de cor do texto para ficar visível
            button.setBackgroundColor(Color.WHITE); // Cor de fundo padrão do botão

            // Desabilita botões vazios
            if (key.isEmpty()) {
                button.setEnabled(false); // Desabilita o botão
                button.setBackgroundColor(Color.LTGRAY); // Cor para indicar que o botão está desabilitado
            } else {
                button.setOnClickListener(this::onKeyClick); // Apenas habilita os botões com letras e números
            }

            keyButtons.add(button);
            keyGrid.addView(button);
        }
    }

    // Método para configurar os botões de embaralhar e resetar
    private void setupButtons() {
        Button shuffleButton = findViewById(R.id.shuffleButton);
        Button resetButton = findViewById(R.id.resetButton);

        shuffleButton.setBackgroundColor(Color.WHITE); // Cor branca para o botão de embaralhar
        resetButton.setBackgroundColor(Color.RED); // Cor vermelha para o botão de reset

        shuffleButton.setOnClickListener(v -> shuffleKeys());
        resetButton.setOnClickListener(v -> resetFields());
    }

    // Método chamado quando um botão de tecla é clicado
    private void onKeyClick(View v) {
        Button button = (Button) v;
        String value = button.getText().toString();

        // Preencher todas as ocorrências do valor clicado nos três campos
        name = preencherCampo(name, namePlaceholder, value);
        surname = preencherCampo(surname, surnamePlaceholder, value);
        enrollment = preencherCampo(enrollment, enrollmentPlaceholder, value);

        // Atualizar os campos de texto
        updateTextFields();

        // Alterar a cor do botão após ser utilizado e desabilitá-lo
        button.setBackgroundColor(Color.YELLOW);
        button.setEnabled(false);

        // Verificação se todos os campos foram preenchidos corretamente
        if (!name.contains("*") && !surname.contains("*") && !enrollment.contains("*")) {
            finalMessage.setText("PARABÉNS! VOCÊ COMPLETOU");
            finalMessage.setVisibility(View.VISIBLE);

            // Atualiza para exibir apenas os caracteres completados sem asteriscos
            nameText.setText("Nome: " + namePlaceholder);
            surnameText.setText("Sobrenome: " + surnamePlaceholder);
            enrollmentText.setText("Matrícula: " + enrollmentPlaceholder);
        }
    }

    // Método para preencher os asteriscos correspondentes ao caractere inserido
    private String preencherCampo(String campoAtual, String campoCompleto, String valor) {
        char[] campoAtualArray = campoAtual.toCharArray();
        char[] campoCompletoArray = campoCompleto.toCharArray();

        for (int i = 0; i < campoAtualArray.length; i++) {
            if (campoAtualArray[i] == '*' && campoCompletoArray[i] == valor.charAt(0)) {
                campoAtualArray[i] = valor.charAt(0);
            }
        }

        // Verifica se o caractere é 'S' e preenche todos os 'S' no campo
        if (valor.equals("S")) {
            for (int i = 0; i < campoAtualArray.length; i++) {
                if (campoAtualArray[i] == '*' && campoCompletoArray[i] == 'S') {
                    campoAtualArray[i] = 'S';
                }
            }
        }

        return new String(campoAtualArray);
    }

    // Método para embaralhar os botões no GridLayout
    private void shuffleKeys() {
        Collections.shuffle(keyButtons);
        keyGrid.removeAllViews();
        for (Button button : keyButtons) {
            keyGrid.addView(button);
        }
    }

    // Método para resetar os campos para o estado original
    private void resetFields() {
        name = "**** *****"; // Resetar para a quantidade de asteriscos original
        surname = "******* ******";
        enrollment = "*********";
        updateTextFields(); // Atualiza os campos para mostrar asteriscos
        finalMessage.setVisibility(View.GONE);

        // Resetar a cor e o estado dos botões
        for (Button button : keyButtons) {
            button.setBackgroundColor(Color.WHITE);
            button.setTextColor(Color.BLACK); // Reverter a cor do texto para preto
            button.setEnabled(true);
            if (button.getText().toString().isEmpty()) {
                button.setEnabled(false); // Manter botões vazios desabilitados
                button.setBackgroundColor(Color.LTGRAY); // Manter cor de desabilitado
            }
        }
    }
}