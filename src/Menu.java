
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int elegir;
        double cantidad, resultado;
        String origenMoneda = "", destinoMoneda = "";

        // Definir cliente HTTP
        HttpClient client = HttpClient.newHttpClient();

        do {
            // Mostrar el menú de opciones
            System.out.println("*************************");
            System.out.println("Sea bienvenido/a al conversor de moneda");
            System.out.println("1- Dólar => Peso argentino");
            System.out.println("2- Peso argentino => Dólar");
            System.out.println("3- Dólar => Dong Vietnaminta");
            System.out.println("4- Dong Vietnamita => Dólar");
            System.out.println("5- Dólar => Peso mexicano");
            System.out.println("6- Peso mexicano => Dólar");
            System.out.println("7- Salir");
            System.out.print("Elija una opción válida: ");
            elegir = scanner.nextInt();

            if (elegir >= 1 && elegir <= 6) {
                // Pedir la cantidad a convertir
                System.out.print("Ingrese la cantidad a convertir: ");
                cantidad = scanner.nextDouble();

                // Determinar las monedas según la opción elegida
                switch (elegir) {
                    case 1:
                        origenMoneda = "USD";
                        destinoMoneda = "ARS";
                        break;
                    case 2:
                        origenMoneda = "ARS";
                        destinoMoneda = "USD";
                        break;
                    case 3:
                        origenMoneda = "USD";
                        destinoMoneda = "VND";
                        break;
                    case 4:
                        origenMoneda = "VND";
                        destinoMoneda = "USD";
                        break;
                    case 5:
                        origenMoneda = "USD";
                        destinoMoneda = "MXN";
                        break;
                    case 6:
                        origenMoneda = "MXN";
                        destinoMoneda = "USD";
                        break;
                }

                // Hacer la solicitud HTTP para obtener la tasa de conversión
                resultado = obtenerTasaDeCambio(client, origenMoneda, destinoMoneda, cantidad);
                if (resultado != -1) {
                    System.out.println("El valor " + cantidad + " " + origenMoneda + " corresponde a " + resultado + " " + destinoMoneda);
                } else {
                    System.out.println("Error al obtener la tasa de cambio.");
                }
            } else if (elegir != 7) {
                System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
            System.out.println();  // Línea en blanco para separación
        } while (elegir != 7);  // Salir cuando el usuario elige la opción 7

        System.out.println("Gracias por su Preferencia, vuelva Pronto!.");
        scanner.close();  // Cerrar el escáner al finalizar
    }

    //Metodo para obtener la tasa de cambio usando HttpClient y analizarla con Gson
    public static double obtenerTasaDeCambio(HttpClient client, String origenMoneda, String destinoMoneda, double cantidad) {
        try {
            // Reemplaza {API-KEY} con tu propia clave de API
            String apiKey = "15a4a05c3073169f959f8b25";  // Pon aquí tu clave
            // Construir la solicitud HTTP con el par de monedas
            String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + origenMoneda + "/" + destinoMoneda;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            // Enviar la solicitud
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Imprimir la respuesta para depuración
            System.out.println("Response: " + response.body());

            // Analizar el JSON usando Gson
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

            if (jsonResponse.get("result").getAsString().equals("success")) {
                // Obtener la tasa de cambio
                double tasaDeCambio = jsonResponse.get("conversion_rate").getAsDouble();
                // Realizar la conversión
                return cantidad * tasaDeCambio;
            } else {
                System.out.println("Error: " + jsonResponse.get("error-type").getAsString());
                return -1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;  // Retornar -1 si ocurre algún error
        }
    }
}