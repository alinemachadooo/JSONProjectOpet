package com.example.diego.jsonproject;

/**
 * Created by Aline on 20/03/2017.
 */

public class Previsao {
    private String cidade;
    private double temperatura;

    public Previsao() {
    }

        public double getTemperatura() {
            return temperatura;
        }

        public void setTemperatura(double temperatura) {
            this.temperatura = temperatura;
        }

        public String getCidade() {
            return cidade;
        }

        public void setCidade(String cidade) {
            this.cidade = cidade;
        }
}
