package estructuraDatosAux;

import java.util.List;

public class GrafoJson {
    private List<String> vertices;
    private List<AristaJson> aristas;

    public List<String> getVertices() {
        return vertices;
    }

    public List<AristaJson> getAristas() {
        return aristas;
    }
}