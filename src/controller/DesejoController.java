package controller;
import model.Desejo;

import java.util.ArrayList;
import java.util.List;

public class DesejoController {

    private ArrayList<Desejo> listDesejos = new ArrayList<>();
    private int contadorID = 1;

    public void addDesejo(Desejo d){
        d.setID(contadorID++);
        listDesejos.add(d);
    }

    public ArrayList<Desejo> listar(){
        return new ArrayList<>(listDesejos);
    }

    public boolean atualizarDesejo(int id, Desejo novo){
        for(Desejo d : listDesejos){
            if(d.getID() == id){
                d.setNome(novo.getNome());
                d.setPreco(novo.getPreco());
                return true;
            }
        }
        return false;
    }

    public boolean removerDesejo(int id){
        return listDesejos.removeIf(d -> d.getID() == id);
    }
}