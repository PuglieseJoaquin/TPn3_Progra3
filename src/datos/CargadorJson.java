package datos;

import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.io.Reader;
import modelo.Instancia;
import estructuraDatosAux.GrafoJson;
import estructuraDatosAux.AristaJson;

public class CargadorJson {

    public static Instancia cargarInstanciaDesdeJson(String ruta) {
        GrafoJson grafoJson = leerGrafoDesdeJson(ruta);
        
        Instancia instancia = new Instancia();
        cargarVertices(instancia, grafoJson);
        cargarAristas(instancia, grafoJson);

        return instancia;
    }

    private static GrafoJson leerGrafoDesdeJson(String ruta) {
        Gson gson = new Gson();
        try (Reader lector = new InputStreamReader(CargadorJson.class.getResourceAsStream(ruta))) {
        
            GrafoJson grafoJson = gson.fromJson(lector, GrafoJson.class);

            if (grafoJson == null) {
                throw new RuntimeException("El archivo JSON está vacío o mal formateado.");
            }

            return grafoJson;
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el JSON: " + e.getMessage(), e);
        }
    }

    private static void cargarVertices(Instancia instancia, GrafoJson grafoJson) {
        if (grafoJson.getVertices() != null) {
            for (String v : grafoJson.getVertices()) {
                instancia.agregarVertice(v);
            }
        }
    }

    private static void cargarAristas(Instancia instancia, GrafoJson grafoJson) {
        if (grafoJson.getAristas() != null) {
            for (AristaJson a : grafoJson.getAristas()) {
                try {
                    instancia.agregarArista(a.getOrigen(), a.getDestino());
                } catch (IllegalArgumentException e) {
                    System.out.println("Aviso: Arista duplicada omitida -> " + a.getOrigen() + " - " + a.getDestino());
                }
            }
        }
    }
}