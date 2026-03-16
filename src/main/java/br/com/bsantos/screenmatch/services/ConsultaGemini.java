package br.com.bsantos.screenmatch.services;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class ConsultaGemini {

    public static String traduzSinopse(String sinopse) {
        Client client = new Client();

        GenerateContentResponse response =
                client.models.generateContent("gemini-2.5-flash", "Traduza o seguinte texto para português do Brasil: " + sinopse + ". Mande somente a tradução e nada mais", null);
        return response.text().trim();
    }
}
