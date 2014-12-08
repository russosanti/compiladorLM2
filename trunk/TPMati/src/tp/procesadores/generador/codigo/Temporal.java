package tp.procesadores.generador.codigo;

public class Temporal {
    public String Nombre;
    public String NombreProc;
    //public string NodoCreador { get; set; }
    public String Valor;
    
    public Temporal() { }
    
    public Temporal(String nombre,String nombreProc){
        this.NombreProc = nombreProc;
        this.Nombre = "temp"+nombre+nombreProc;
    }

    public Temporal(String nombre,String nombreProc,String valor)
    {
        this.NombreProc = nombreProc;
        this.Nombre = "temp" + nombre + nombreProc;
        this.Valor = valor;
    }

    public String ToString()
    {
        StringBuilder strBldr = new StringBuilder();
        strBldr.append(NombreProc).append(", ").append("/n");
        //strBldr.AppendLine(NodoCreador);

        return strBldr.toString();
    }

}
