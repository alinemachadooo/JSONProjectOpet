package com.example.diego.jsonproject;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Diego on 12/03/2017.
 */

public class Util {


    /**
    *Lê um arquivo da pasta raw (Resources) e converte o mesmo em String.
    *@param inputStream Stream do arquivo local no aplicativo
    *@return O arquivo convertido em String.
    */
    public static String rawToJson(InputStream inputStream) {
        InputStream localStream = inputStream;
        String jsonString = "";
        Writer writer = new StringWriter();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(localStream, "UTF-8"));
            String line = reader.readLine();
            while (line != null) {
                writer.write(line);
                line = reader.readLine();
            }
            jsonString = writer.toString();
            writer.close();
            reader.close();
            localStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    /**
     *Lê um arquivo da web via HTTP e converte o mesmo em String.
     *@param inputStream Stream do arquivo local no aplicativo
     *@return O arquivo convertido em String.
     */
    public static String webToString(InputStream inputStream) {
        InputStream localStream = inputStream;
        String localString = "";
        Writer writer = new StringWriter();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(localStream, "UTF-8"));
            String line = reader.readLine();
            while (line != null) {
                writer.write(line);
                line = reader.readLine();
            }
            localString = writer.toString();
            writer.close();
            reader.close();
            localStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return localString;
    }
    /**
     *Lê um arquivo da pasta raw (Resources) e converte o mesmo em String.
     *@param jsonFile Arquivo JSON com os dados a serem convertidos
     *@return Os dados adicionados na classe Album.
     */
    public static Album convertJSONtoAlbum(String jsonFile){
        Album novoAlbum = null;
        try {
            novoAlbum = new Album();
            List<Faixa> faixas = new ArrayList<>();

            JSONObject mainObject = new JSONObject(jsonFile);
            String banda = mainObject.getString("Banda");
            String album = mainObject.getString("Album");
            int ano = mainObject.getInt("Ano");

            JSONArray faixasJson = mainObject.getJSONArray("Faixas");

            for(int i = 0; i < faixasJson.length(); i++){
                JSONObject localObj = faixasJson.getJSONObject(i);
                String titulo = localObj.getString("Titulo");
                String duracao = localObj.getString("Duracao");
                String vocal = localObj.getString("Vocal");
                faixas.add(new Faixa(titulo,duracao,vocal));
            }
            novoAlbum.setNomeBanda(banda);
            novoAlbum.setNomeAlbum(album);
            novoAlbum.setAnoAlbum(ano);
            novoAlbum.setFaixas(faixas);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return novoAlbum;
    }

    public static Previsao JSONtoPrevisao(String jsonString){
        try{
            Previsao previsao = new Previsao();

            JSONObject mainObj = new JSONObject(jsonString);

            previsao.setCidade(mainObj.getJSONObject("city").getString("name"));

            JSONArray listaTempo = mainObj.getJSONArray("list");
            double temperatura = listaTempo.getJSONObject(0).getJSONObject("temp").getDouble("day");
            previsao.setTemperatura(temperatura);

            return previsao;
        }catch (Exception e){
            Log.e("ERROR", "ERROR", e);
            return null;
        }
    }
}
