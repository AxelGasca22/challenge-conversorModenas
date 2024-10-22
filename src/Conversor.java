public class Conversor {
    private String monedaOrigen;
    private String monedaDestino;
    private double cantidad;
    private double resultado;

    public void setMonedaOrigen(String monedaOrigen) {
        this.monedaOrigen = monedaOrigen;
    }

    public void setMonedaDestino(String monedaDestino) {
        this.monedaDestino = monedaDestino;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public void conversion (){
        if(monedaOrigen.equals("Dolar") && monedaDestino.equals("Peso argentino")){
            resultado = (938.19)*cantidad;
            System.out.println( cantidad + "Dólares en pesos argentinos son: " + resultado);
        }
    }
}
