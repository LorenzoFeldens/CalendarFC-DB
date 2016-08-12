package calendar.fc;

import calendar.fc.dao.CalendarDAO;
import calendar.fc.dao.PagesDAO;
import calendar.fc.entidades.Game;
import calendar.fc.entidades.Quadro;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalendarFC {
    private static ArrayList<Game> listaJogosTimes;
    private static ArrayList<Game> listaJogosComp;
    private static ArrayList<Quadro> listaTimes;
    private static ArrayList<Quadro> listaComepticoes;
    
    private static ArrayList<String> listaJogos;
    
    public static void main(String[] args) {
        listaTimes = new PagesDAO().getAllTimes();
        listaJogos = new ArrayList();
        listaJogosComp = new ArrayList();
        listaJogosTimes = new ArrayList();
        
        int cont = 1;
        String atual = "";
        for(int i=0; i<listaTimes.size(); i++){
            if(i==0){
                atual = listaTimes.get(0).getTipo();
            }
            if(!listaTimes.get(i).getTipo().equalsIgnoreCase(atual)){
                atual = listaTimes.get(i).getTipo();
                cont = 1;
            }
            String nome = atual+" ("+cont+")";
            
            String content = getHTMLPage(nome);
            cadastraJogos(content);
            
            cont++;
        }
        
        Set<String> hs = new HashSet<>();
        hs.addAll(listaJogos);
        listaJogos.clear();
        listaJogos.addAll(hs);
        
        for(int i=0; i<listaJogos.size(); i++){
            Game g = new Game();
            
            String[] s = listaJogos.get(i).split("#");
        
            g.setData(s[0]);
            g.setTitulo(s[1]);

            listaJogosTimes.add(g);
        }
        
        listaComepticoes = new PagesDAO().getAllCompeticoes();
        
        cont = 1;
        atual = "";
        for(int i=0; i<listaComepticoes.size(); i++){
            if(i==0){
                atual = listaComepticoes.get(0).getTipo();
            }
            if(!listaComepticoes.get(i).getTipo().equalsIgnoreCase(atual)){
                atual = listaComepticoes.get(i).getTipo();
                cont = 1;
            }
            String nome = atual+" ("+cont+")";
            
            String content = getHTMLPage(nome);
            cadastraJogosComp(content, listaComepticoes.get(i).getIdCalendar());
            
            cont++;
        }
        
        /*for(int i=0; i<listaJogosComp.size(); i++){
            System.out.println(listaJogosComp.get(i).getTitulo()+" "+listaJogosComp.get(i).getCompeticao());
        }*/
        
        
        ArrayList<Integer> duplicados = new ArrayList();
        
        for(int i=0; i<listaJogosComp.size(); i++){
            for(int j=0; j<listaJogosTimes.size(); j++){
                if(listaJogosComp.get(i).getTitulo().equalsIgnoreCase(listaJogosTimes.get(j).getTitulo())
                        && listaJogosComp.get(i).getData().equalsIgnoreCase(listaJogosTimes.get(j).getData())){
                    listaJogosTimes.remove(j);
                    j--;
                }
            }
        }
        
        /*for(int i=duplicados.size()-1; i>0; i++){
            System.out.println(duplicados.get(i));
            listaJogosTimes.remove(duplicados.get(i));
        }*/
        /*int k=0;
        int l=0;
        while(k<listaJogosComp.size()){
            while(l<listaJogosTimes.size()){
                if(listaJogosComp.get(k).getTitulo().equalsIgnoreCase(listaJogosTimes.get(l).getTitulo())
                        && listaJogosComp.get(k).getData().equalsIgnoreCase(listaJogosTimes.get(l).getData())){
                    listaJogosTimes.remove(l);
                    k++;
                    l=0;
                }else{
                    l++;
                }
            }
            k++;
        }*/
        
        listaJogosComp.addAll(listaJogosTimes);
        
        /*for(int i=0; i<listaJogosComp.size(); i++){
            System.out.println(listaJogosComp.get(i).getTitulo()+" "+listaJogosComp.get(i).getCompeticao());
        }*/
        
        for(int i=0; i<listaJogosComp.size(); i++){
            String[] s = listaJogosComp.get(i).getTitulo().split(" - ");
            String[] ss = s[0].trim().split(" x ");
            String[] sss1 = ss[0].trim().split("[(]");
            String[] sss2 = ss[1].trim().split("[(]");
            
            String tCasa = sss1[0].trim();
            String tFora = sss2[0].trim();
            
            int tc = 0;
            int tf = 0;
            for(int j=0; j<listaTimes.size(); j++){
                if(tCasa.equalsIgnoreCase(listaTimes.get(j).getNome())){
                    listaJogosComp.get(i).settCasa(listaTimes.get(j).getIdCalendar());
                    
                    String titulo = listaJogosComp.get(i).getTitulo();
                    String[] s0 = titulo.split(" - ");
                    String[] s1 = s0[0].split(" x ");
                    
                    String time = listaTimes.get(j).getNovoNome();
                    
                    listaJogosComp.get(i).setTitulo(time+" x "+s1[1]+" - "+s0[1]);
                    
                    tc = 1;
                }
                if(tFora.equalsIgnoreCase(listaTimes.get(j).getNome())){
                    listaJogosComp.get(i).settFora(listaTimes.get(j).getIdCalendar());
                    
                    String titulo = listaJogosComp.get(i).getTitulo();
                    String[] s0 = titulo.split(" - ");
                    String[] s1 = s0[0].split(" x ");
                    
                    String time = listaTimes.get(j).getNovoNome();
                    
                    listaJogosComp.get(i).setTitulo(s1[0]+" x "+time+" - "+s0[1]);
                    
                    tf = 1;
                }
                if(tc != 0 && tf != 0){
                    j=listaTimes.size();
                }
            }
        }
        
        for(int i=0; i<listaJogosComp.size(); i++){
            int comp = listaJogosComp.get(i).getCompeticao();
            if(comp != 0){
                String titulo = listaJogosComp.get(i).getTitulo();
                String[] s = titulo.split(" - ");
                
                String nComp = listaComepticoes.get(comp-1).getNome();
                
                listaJogosComp.get(i).setTitulo(s[0]+" - "+nComp);
            }
        }
        
        ArrayList<String> oldFile = getOldFile();
        ArrayList<String> newFile = saveJogos();
        
        newFile.removeAll(oldFile);
        
        saveFile(newFile);
    }
    
    private static void saveFile(ArrayList<String> lista){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("C:/Users/Lorenzo Feldens/Documents/NewUpdate.txt", "UTF-8");
            
            for(int i=0; i<lista.size(); i++){
                writer.println(lista.get(i));
            }
            
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CalendarFC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CalendarFC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
    }
    
    private static ArrayList<String> saveJogos(){
        CalendarDAO gdb = new CalendarDAO();
        ArrayList<String> lista = new ArrayList();
        
        for(int i=0; i<listaJogosComp.size(); i++){
            String tCasa = "null";
            String tFora = "null";
            String comp = "null";
            
            String data = listaJogosComp.get(i).getData();
            String titulo = listaJogosComp.get(i).getTitulo();
            
            if(listaJogosComp.get(i).gettCasa() != 0){
                tCasa = String.valueOf(listaJogosComp.get(i).gettCasa());
            }
            
            if(listaJogosComp.get(i).gettFora() != 0){
                tFora = String.valueOf(listaJogosComp.get(i).gettFora());
            }
            
            if(listaJogosComp.get(i).getCompeticao() != 0){
                comp = String.valueOf(listaJogosComp.get(i).getCompeticao());
            }
            
            lista.add(gdb.insertGame(tCasa, tFora, comp, data, titulo));
            //String[] s = listaJogos.get(i).split("#");
            //gdb.insertGame(s[0], s[1]);
        }
        return lista;
    }
    
    private static ArrayList<String> getOldFile(){
        ArrayList<String> lista = new ArrayList();
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader("C:/Users/Lorenzo Feldens/Documents/Update.txt"));
            String str;
            while ((str = in.readLine()) != null) {
                lista.add(str);
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
        }
        String content = contentBuilder.toString();
        return lista;
    }
    
    private static String getHTMLPage(String nome){
        System.out.println(nome);
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader("C:/Users/Lorenzo Feldens/Desktop/Calendar FC/"+nome+".html"));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
        }
        String content = contentBuilder.toString();
        return content;
    }
    
    private static void cadastraJogosComp(String str, int torneio){
        String[] s0 = str.split("class=\"fs-table tournament-page\"");
        String[] s1 = s0[1].split("-page-fixtures-more\"");
        String content = s1[0];
        
        if(!content.contains("class=\"soccer\"")){
            return;
        }
        
        String[] ss = content.split("class=\"soccer\"");
        
        for(int i=1; i<ss.length; i++){
            separaCampeonatoComp(ss[i], torneio);
        }
    }
    
    private static void separaCampeonatoComp(String str, int idTorneio){
        String[] s0 = str.split("class=\"tournament_part\"");
        String[] s1 = s0[1].split(">");
        String[] s2 = s1[1].split("<");
        String torneio = s2[0];
        
        String resto = "";
        for(int i=1; i<s1.length; i++){
            resto+=s1[i]+">";
        }
        
        String[] s3 = resto.split("<tbody");
        String[] s4 = s3[1].split("<tr");
        
        for(int i=1; i<s4.length; i++){
            separaJogoComp(s4[i], torneio, idTorneio);
        }
    }
    
    private static void separaJogoComp(String str, String torneio, int idTorneio){
        if(!str.contains("class=\"cell_ad time"))
            return;
        
        String[] s0 = str.split("class=\"cell_ad time");
        String[] s1 = s0[1].split(">");
        String[] s2 = s1[1].split("<");
        
        String data = arrumaData(s2[0]);
        
        if(data.equalsIgnoreCase("##")){
            return;
        }
                
        String resto = "";
        for(int i=1; i<s1.length; i++){
            resto+=s1[i]+">";
        }
        
        String[] s3 = resto.split("team-home");
        String[] s4 = s3[1].split(">");
        String[] s5 = s4[2].split("<");
        
        String tCasa = s5[0];
        
        resto = "";
        for(int i=2; i<s4.length; i++){
            resto+=s4[i]+">";
        }
        
        String[] s6 = resto.split("team-away");
        String[] s7 = s6[1].split(">");
        String[] s8 = s7[2].split("<");
        
        String tFora = s8[0];
        
        Game g = new Game();
        
        g.setCompeticao(idTorneio);
        g.setData(data);
        g.setTitulo(tCasa+" x "+tFora+" - "+torneio);
        
        listaJogosComp.add(g);
        
    }
    
    private static void cadastraJogos(String str){
        String[] s0 = str.split("class=\"fs-table tournament-page\"");
        String[] s1 = s0[1].split("-page-fixtures-more\"");
        String content = s1[0];
        
        if(!content.contains("class=\"soccer\"")){
            return;
        }
        
        String[] ss = content.split("class=\"soccer\"");
        
        for(int i=1; i<ss.length; i++){
            separaCampeonato(ss[i]);
        }
    }
    
    private static void separaCampeonato(String str){
        String[] s0 = str.split("class=\"tournament_part\"");
        String[] s1 = s0[1].split(">");
        String[] s2 = s1[1].split("<");
        String torneio = s2[0];
        
        String resto = "";
        for(int i=1; i<s1.length; i++){
            resto+=s1[i]+">";
        }
        
        String[] s3 = resto.split("<tbody");
        String[] s4 = s3[1].split("<tr");
        
        for(int i=1; i<s4.length; i++){
            separaJogo(s4[i], torneio);
        }
    }
    
    private static void separaJogo(String str, String torneio){
        if(!str.contains("class=\"cell_ad time"))
            return;
        String[] s0 = str.split("class=\"cell_ad time");
        String[] s1 = s0[1].split(">");
        String[] s2 = s1[1].split("<");
        
        String data = arrumaData(s2[0]);
        
        if(data.equalsIgnoreCase("##")){
            return;
        }
                
        String resto = "";
        for(int i=1; i<s1.length; i++){
            resto+=s1[i]+">";
        }
        
        String[] s3 = resto.split("team-home");
        String[] s4 = s3[1].split(">");
        String[] s5 = s4[2].split("<");
        
        String tCasa = s5[0];
        
        resto = "";
        for(int i=2; i<s4.length; i++){
            resto+=s4[i]+">";
        }
        
        String[] s6 = resto.split("team-away");
        String[] s7 = s6[1].split(">");
        String[] s8 = s7[2].split("<");
        
        String tFora = s8[0];
        
        listaJogos.add(data+"#"+tCasa+" x "+tFora+" - "+torneio);
        
    }
    
    private static String arrumaData(String str){
        String[] s = str.split("[.]");
        
        if(Integer.valueOf(s[1]) == 7 || Integer.valueOf(s[1]) == 8){
            return "2016-"+s[1]+"-"+s[0]+" "+s[2].trim();
        }
        
        return "##";
    }
    
}
