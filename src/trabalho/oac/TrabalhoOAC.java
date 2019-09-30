package trabalho.oac;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class TrabalhoOAC {

    public static Configuracoes config = new Configuracoes();
    public static ArrayList<Integer> Testes = new ArrayList<Integer>();
    public static int FalhaNaCache;

    public static void FIFOConjuntoBinary(int usuario, AConjunto ac) {
        int LinhasPrincipal = config.getPrincipal() / config.getPalavra();
        int PalavrasNaLinhaDaCache = config.getLinha();
        int Blocos = LinhasPrincipal / PalavrasNaLinhaDaCache;
        int BitsPrincipal = (int) (Math.log(LinhasPrincipal) / Math.log(2));
        int NumeroConjuntosPrincipal = Blocos / usuario;
        int LinhasCache = config.getCache() / (config.getPalavra() * config.getLinha());
        int NConjuntosCache = LinhasCache / usuario;
        int CONJUNTO = (int) (Math.log(NConjuntosCache) / Math.log(2));
        int Palavra = (int) (Math.log(config.getLinha()) / Math.log(2));
        int TAG = BitsPrincipal - Palavra - CONJUNTO;

        System.out.println(" " + TAG + " " + CONJUNTO + " " + Palavra);

        ArrayList<ArrayList<Integer>> FIFO = new ArrayList<>();

        int cache[][] = new int[NConjuntosCache][usuario];
        FalhaNaCache = 0;
        for (int i = 0; i < NConjuntosCache; i++) {
            for (int j = 0; j < usuario; j++) {
                cache[i][j] = -1;
            }
        }
        for (int i = 0; i < NConjuntosCache; i++) {
            ArrayList<Integer> p = new ArrayList<Integer>();
            p.add(-1);
            FIFO.add(p);
        }

        for (int i = 0; i < Testes.size(); i++) {

            String Teste = Integer.toBinaryString(Testes.get(i));

            while (Teste.length() < BitsPrincipal) {
                Teste = "0" + Teste;
            }
            String TTag = "";
            String TConjunto = "";
            int conjunto = 0;

            for (int j = 0; j < TAG; j++) {
                TTag = TTag + Teste.charAt(j);
            }

            if (CONJUNTO > 0) {
                for (int j = TAG; j < TAG + CONJUNTO; j++) {
                    TConjunto = TConjunto + Teste.charAt(j);
                }
                conjunto = Integer.parseInt(TConjunto, 2);
            }

            int tag = Integer.parseInt(TTag, 2);

            ArrayList<Integer> aux = new ArrayList<Integer>();
            boolean Contem = false;
            for (int j = 0; j < (usuario); j++) {
                if (cache[conjunto][j] == tag) {
                    Contem = true;
                }
            }
            if (!Contem) {
                FalhaNaCache++;

                aux = FIFO.remove(conjunto);
                if (aux.size() < usuario) {
                    if (aux.get(0) == -1) {
                        aux.remove(0);
                    }
                    aux.add(tag);
                    FIFO.add(conjunto, aux);
                    for (int j = 0; j < (usuario); j++) {
                        if (cache[conjunto][j] == -1) {
                            cache[conjunto][j] = tag;
                            j = (usuario);
                        }
                    }

                } else {
                    int primeiro = aux.remove(0);
                    for (int j = 0; j < (usuario); j++) {
                        if (cache[conjunto][j] == primeiro) {
                            cache[conjunto][j] = tag;
                            j = (usuario);
                        }
                    }
                    aux.add(tag);
                    FIFO.add(conjunto, aux);
                }
            }
        }

        float Acerto = (1 - (float) ((float) FalhaNaCache / Testes.size())) * 100;
        System.out.println("Falhas: " + FalhaNaCache + " de " + Testes.size() + "- " + (Acerto) + "%");

        String result = ("Falhas: " + FalhaNaCache + " de " + Testes.size() + "- " + (Acerto) + "%");
        int espacoTAG = ((TAG * LinhasCache) / 8);
        ac.SetValores(Blocos, NConjuntosCache, NumeroConjuntosPrincipal, LinhasCache, LinhasPrincipal, espacoTAG, TAG, CONJUNTO, Palavra, result, "FIFO Conjunto");
        //SetValores(int Blocos, int ConjuntosCache, int ConjuntosPrincipal,  int LinhasCache, int LinhasPrincipal, int ETAG, int TAG, int endereco, int palavra, String resultado, String origem) {
  ac.setVisible(true);
    }

    public static void AleatorioConjuntoBinary(int usuario, AConjunto ac) {

        int LinhasPrincipal = config.getPrincipal() / config.getPalavra();
        int PalavrasNaLinhaDaCache = config.getLinha();
        int Blocos = LinhasPrincipal / PalavrasNaLinhaDaCache;
        int BitsPrincipal = (int) (Math.log(LinhasPrincipal) / Math.log(2));
        int NumeroConjuntosPrincipal = Blocos / usuario;
        int LinhasCache = config.getCache() / (config.getPalavra() * config.getLinha());
        int NConjuntosCache = LinhasCache / usuario;
        int CONJUNTO = (int) (Math.log(NConjuntosCache) / Math.log(2));
        int Palavra = (int) (Math.log(config.getLinha()) / Math.log(2));
        int TAG = BitsPrincipal - Palavra - CONJUNTO;

        System.out.println(" " + TAG + " " + CONJUNTO + " " + Palavra);

        int cache[][] = new int[NConjuntosCache][usuario];
        FalhaNaCache = 0;
        for (int i = 0; i < NConjuntosCache; i++) {
            for (int j = 0; j < usuario; j++) {
                cache[i][j] = -1;
            }
        }

        for (int i = 0; i < Testes.size(); i++) {

            String Teste = Integer.toBinaryString(Testes.get(i));

            while (Teste.length() < BitsPrincipal) {
                Teste = "0" + Teste;
            }
            String TTag = "";
            String TConjunto = "";
            int conjunto = 0;

            for (int j = 0; j < TAG; j++) {
                TTag = TTag + Teste.charAt(j);
            }

            if (CONJUNTO > 0) {
                for (int j = TAG; j < TAG + CONJUNTO; j++) {
                    TConjunto = TConjunto + Teste.charAt(j);
                }
                conjunto = Integer.parseInt(TConjunto, 2);
            }

            int tag = Integer.parseInt(TTag, 2);

            boolean Contem = false;
            for (int j = 0; j < usuario; j++) {
                if (cache[conjunto][j] == tag) {
                    Contem = true;
                }
            }
            if (!Contem) {
                FalhaNaCache++;
                int x = (int) (Math.random() * (usuario - 1));
                cache[conjunto][x] = tag;

            }
        }
        float Acerto = (1 - (float) ((float) FalhaNaCache / Testes.size())) * 100;
        System.out.println("Falhas: " + FalhaNaCache + " de " + Testes.size() + "- " + (Acerto) + "%");

        String result = ("Falhas: " + FalhaNaCache + " de " + Testes.size() + "- " + (Acerto) + "%");
        int espacoTAG = ((TAG * LinhasCache) / 8);
        
        
        ac.SetValores(Blocos, NConjuntosCache, NumeroConjuntosPrincipal, LinhasCache, LinhasPrincipal, espacoTAG, TAG, CONJUNTO, Palavra, result, "ALEATORIO Conjunto");
        //SetValores(int Blocos, int Conjuntos, int LinhasCache, int LinhasPrincipal, int ETAG, int TAG, int endereco, int palavra, String resultado) 
        ac.setVisible(true);
    }

    public static void RecenteConjuntoBinary(int usuario, AConjunto ac) {
        int LinhasPrincipal = config.getPrincipal() / config.getPalavra();
        int PalavrasNaLinhaDaCache = config.getLinha();
        int Blocos = LinhasPrincipal / PalavrasNaLinhaDaCache;
        int BitsPrincipal = (int) (Math.log(LinhasPrincipal) / Math.log(2));
        int NumeroConjuntosPrincipal = Blocos / usuario;
        int LinhasCache = config.getCache() / (config.getPalavra() * config.getLinha());
        int NConjuntosCache = LinhasCache / usuario;
        int CONJUNTO = (int) (Math.log(NConjuntosCache) / Math.log(2));
        int Palavra = (int) (Math.log(config.getLinha()) / Math.log(2));
        int TAG = BitsPrincipal - Palavra - CONJUNTO;

        System.out.println(" " + TAG + " " + CONJUNTO + " " + Palavra);

        ArrayList<ArrayList<Integer>> FIFO = new ArrayList<>();

        int cache[][] = new int[NConjuntosCache][usuario];
        FalhaNaCache = 0;
        for (int i = 0; i < NConjuntosCache; i++) {
            for (int j = 0; j < usuario; j++) {
                cache[i][j] = -1;
            }
        }
        for (int i = 0; i < NConjuntosCache; i++) {
            ArrayList<Integer> p = new ArrayList<>();
            p.add(-1);
            FIFO.add(p);
        }

        for (int i = 0; i < Testes.size(); i++) {

            String Teste = Integer.toBinaryString(Testes.get(i));

            while (Teste.length() < BitsPrincipal) {
                Teste = "0" + Teste;
            }
            String TTag = "";
            String TConjunto = "";
            int conjunto = 0;

            for (int j = 0; j < TAG; j++) {
                TTag = TTag + Teste.charAt(j);
            }

            if (CONJUNTO > 0) {
                for (int j = TAG; j < TAG + CONJUNTO; j++) {
                    TConjunto = TConjunto + Teste.charAt(j);
                }
                conjunto = Integer.parseInt(TConjunto, 2);
            }

            int tag = Integer.parseInt(TTag, 2);

            ArrayList<Integer> aux = new ArrayList<>();
            boolean Contem = false;
            for (int j = 0; j < (usuario); j++) {
                if (cache[conjunto][j] == tag) {
                    Contem = true;
                    FIFO.get(conjunto).remove(new Integer(tag));
                    FIFO.get(conjunto).add(tag);

                    j = usuario;
                }
            }

            if (!Contem) {
                FalhaNaCache++;

                aux = FIFO.remove(conjunto);
                if (aux.size() < usuario) {
                    if (aux.get(0) == -1) {
                        aux.remove(0);
                    }
                    aux.add(tag);
                    FIFO.add(conjunto, aux);
                    for (int j = 0; j < (usuario); j++) {
                        if (cache[conjunto][j] == -1) {
                            cache[conjunto][j] = tag;
                            j = (usuario);
                        }
                    }

                } else {
                    int primeiro = aux.remove(0);
                    for (int j = 0; j < (usuario); j++) {
                        if (cache[conjunto][j] == primeiro) {
                            cache[conjunto][j] = tag;
                            j = (usuario);
                        }
                    }
                    aux.add(tag);
                    FIFO.add(conjunto, aux);
                }
            }
        }

        float Acerto = (1 - (float) ((float) FalhaNaCache / Testes.size())) * 100;
        System.out.println("Falhas: " + FalhaNaCache + " de " + Testes.size() + "- " + (Acerto) + "%");

        String result = ("Falhas: " + FalhaNaCache + " de " + Testes.size() + "- " + (Acerto) + "%");
        int espacoTAG = ((TAG * LinhasCache) / 8);
        ac.SetValores(Blocos, NConjuntosCache, NumeroConjuntosPrincipal, LinhasCache, LinhasPrincipal, espacoTAG, TAG, CONJUNTO, Palavra, result, "RECENTE Conjunto");
        //SetValores(int Blocos, int Conjuntos, int LinhasCache, int LinhasPrincipal, int ETAG, int TAG, int endereco, int palavra, String resultado) 
        ac.setVisible(true);

        //ac.SetValores(Blocos, CONJUNTO, LinhasCache, LinhasPrincipal, TAG, TAG, CONJUNTO, Palavra, result);
        //SetValores(int Blocos, int Conjuntos, int LinhasCache, int LinhasPrincipal, int ETAG, int TAG, int endereco, int palavra, String resultado) 
        // ac.setVisible(true);
    }

    public static void FrequenteConjuntoBinary(int usuario, AConjunto ac) {

        int LinhasPrincipal = config.getPrincipal() / config.getPalavra();
        int PalavrasNaLinhaDaCache = config.getLinha();
        int Blocos = LinhasPrincipal / PalavrasNaLinhaDaCache;
        int BitsPrincipal = (int) (Math.log(LinhasPrincipal) / Math.log(2));
        int NumeroConjuntosPrincipal = Blocos / usuario;
        int LinhasCache = config.getCache() / (config.getPalavra() * config.getLinha());
        int NConjuntosCache = LinhasCache / usuario;
        int CONJUNTO = (int) (Math.log(NConjuntosCache) / Math.log(2));
        int Palavra = (int) (Math.log(config.getLinha()) / Math.log(2));
        int TAG = BitsPrincipal - Palavra - CONJUNTO;

        System.out.println(" " + TAG + " " + CONJUNTO + " " + Palavra);

        ArrayList<ArrayList<Frequente>> FIFO = new ArrayList<>();
        for (int i = 0; i < NConjuntosCache; i++) {
            ArrayList<Frequente> p = new ArrayList<>();
            Frequente f = new Frequente();
            f.setBloco(-1);
            f.setFrequencia(1);
            p.add(f);
            FIFO.add(p);
        }

        int cache[][] = new int[NConjuntosCache][usuario];
        FalhaNaCache = 0;
        for (int i = 0; i < NConjuntosCache; i++) {
            for (int j = 0; j < usuario; j++) {
                cache[i][j] = -1;
            }
        }

        for (int i = 0; i < Testes.size(); i++) {

            String Teste = Integer.toBinaryString(Testes.get(i));

            while (Teste.length() < BitsPrincipal) {
                Teste = "0" + Teste;
            }
            String TTag = "";
            String TConjunto = "";
            int conjunto = 0;

            for (int j = 0; j < TAG; j++) {
                TTag = TTag + Teste.charAt(j);
            }

            if (CONJUNTO > 0) {
                for (int j = TAG; j < TAG + CONJUNTO; j++) {
                    TConjunto = TConjunto + Teste.charAt(j);
                }
                conjunto = Integer.parseInt(TConjunto, 2);
            }

            int tag = Integer.parseInt(TTag, 2);
            ArrayList<Frequente> aux = new ArrayList<>();
            Frequente frequente = new Frequente();
            boolean Contem = false;
            for (int j = 0; j < (usuario); j++) {
                if (cache[conjunto][j] == tag) {
                    Contem = true;
                    for (int k = 0; k < usuario; k++) {
                        if (tag == FIFO.get(conjunto).get(k).getBloco()) {
                            FIFO.get(conjunto).get(k).setFrequencia(FIFO.get(conjunto).get(k).getFrequencia() + 1);
                            k = usuario;
                        }
                    }
                    j = usuario;
                }
            }
            if (!Contem) {
                FalhaNaCache++;

                aux = FIFO.remove(conjunto);
                if (aux.size() < usuario) {
                    if (aux.get(0).getBloco() == -1) {
                        aux.remove(0);
                    }
                    frequente.setBloco(tag);
                    frequente.setFrequencia(1);
                    aux.add(frequente);
                    FIFO.add(conjunto, aux);
                    for (int j = 0; j < (usuario); j++) {
                        if (cache[conjunto][j] == -1) {
                            cache[conjunto][j] = tag;
                            j = (usuario);
                        }
                    }

                } else {

                    //Collection para ordenar frequencia
                    Collections.sort(aux, (Object o1, Object o2) -> {
                        Frequente p1 = (Frequente) o1;
                        Frequente p2 = (Frequente) o2;
                        return p1.getFrequencia() < p2.getFrequencia() ? -1 : (p1.getFrequencia() > p2.getFrequencia() ? +1 : 0);
                    });

                    int primeiro = aux.get(0).getBloco();
                    aux.remove(0);
                    for (int j = 0; j < (usuario); j++) {
                        if (cache[conjunto][j] == primeiro) {
                            cache[conjunto][j] = tag;
                            j = (usuario);
                        }
                    }
                    frequente.setBloco(tag);
                    frequente.setFrequencia(1);
                    aux.add(frequente);
                    FIFO.add(conjunto, aux);
                }
            }
        }

        float Acerto = (1 - (float) ((float) FalhaNaCache / Testes.size())) * 100;
        System.out.println("Falhas: " + FalhaNaCache + " de " + Testes.size() + "- " + (Acerto) + "%");

        String result = ("Falhas: " + FalhaNaCache + " de " + Testes.size() + "- " + (Acerto) + "%");
        int espacoTAG = ((TAG * LinhasCache) / 8);
        ac.SetValores(Blocos, NConjuntosCache, NumeroConjuntosPrincipal, LinhasCache, LinhasPrincipal, espacoTAG, TAG, CONJUNTO, Palavra, result, "FREQUENTE Conjunto");
        //SetValores(int Blocos, int Conjuntos, int LinhasCache, int LinhasPrincipal, int ETAG, int TAG, int endereco, int palavra, String resultado) 
        ac.setVisible(true);
    }

    public static void FIFOAssociativoBinary(Associativo associativo) {
        int LinhasPrincipal = config.getPrincipal() / config.getPalavra();
        int PalavrasNaLinhaDaCache = config.getLinha();
        int Blocos = LinhasPrincipal / PalavrasNaLinhaDaCache;

        int W = (int) (Math.log(PalavrasNaLinhaDaCache) / Math.log(2));
        int BitsPrincipal = (int) (Math.log(LinhasPrincipal) / Math.log(2));
        int TAG = (int) BitsPrincipal - W;
        Hashtable tabela = new Hashtable();
        ArrayList<Integer> FIFO = new ArrayList<Integer>();
        int LinhasCache = config.getCache() / (config.getPalavra() * config.getLinha());
        FalhaNaCache = 0;
        for (int i = 0; i < Testes.size(); i++) {

            String Teste = Integer.toBinaryString(Testes.get(i));

            while (Teste.length() < BitsPrincipal) {
                Teste = "0" + Teste;
            }
            String TTag = "";
            for (int j = 0; j < TAG; j++) {
                TTag = TTag + Teste.charAt(j);
            }
            int tag = Integer.parseInt(TTag, 2);

            if (!tabela.contains(tag)) {
                FalhaNaCache++;
                if (tabela.size() < LinhasCache) {
                    FIFO.add(tag);
                    tabela.put(tag, tag);
                } else {
                    tabela.remove(FIFO.remove(0));
                    tabela.put(tag, tag);
                    FIFO.add(tag);
                }

            }

        }
        float Acerto = (1 - (float) ((float) FalhaNaCache / Testes.size())) * 100;
        String result = ("Falhas: " + FalhaNaCache + " de " + Testes.size() + "- " + (Acerto) + "%");
        System.out.println("Falhas: " + FalhaNaCache + " de " + Testes.size() + "- " + (Acerto) + "%");
        int espacoTAG = ((TAG * LinhasCache) / 8);
        associativo.SetElementos(Blocos, LinhasCache, LinhasPrincipal, espacoTAG, TAG, W, result, "FIFO Associativo");
        //public void SetEle(int Blocos, LinhasCache, LinhasPrincipal, ETAG, TAG, palavra,  resultado, String origem) {
        //   associativo.SetElementos(Blocos, LinhasCache, LinhasPrincipal, ((TAG * Blocos) / 8), TAG, W, result);
    }

    public static void FrequenteAssociativoBinary(Associativo associativo) {

        int LinhasPrincipal = config.getPrincipal() / config.getPalavra();
        int PalavrasNaLinhaDaCache = config.getLinha();
        int Blocos = LinhasPrincipal / PalavrasNaLinhaDaCache;

        int W = (int) (Math.log(PalavrasNaLinhaDaCache) / Math.log(2));
        int BitsPrincipal = (int) (Math.log(LinhasPrincipal) / Math.log(2));
        int TAG = BitsPrincipal - W;
        Hashtable tabela = new Hashtable();
        ArrayList<Frequente> freq = new ArrayList<Frequente>();
        int LinhasCache = config.getCache() / (config.getPalavra() * config.getLinha());
        FalhaNaCache = 0;
        for (int i = 0; i < Testes.size(); i++) {

            String Teste = Integer.toBinaryString(Testes.get(i));

            while (Teste.length() < BitsPrincipal) {
                Teste = "0" + Teste;
            }
            String TTag = "";
            for (int j = 0; j < TAG; j++) {
                TTag = TTag + Teste.charAt(j);
            }
            int tag = Integer.parseInt(TTag, 2);
            if (!tabela.contains(tag)) {
                FalhaNaCache++;
                if (tabela.size() < LinhasCache) {
                    Frequente f = new Frequente();
                    f.setBloco(tag);
                    f.setFrequencia(1);
                    freq.add(f);
                    tabela.put(tag, tag);

                } else {

                    Collections.sort(freq, (Object o1, Object o2) -> {
                        Frequente p1 = (Frequente) o1;
                        Frequente p2 = (Frequente) o2;
                        return p1.getFrequencia() < p2.getFrequencia() ? -1 : (p1.getFrequencia() > p2.getFrequencia() ? +1 : 0);
                    });

                    tabela.remove(freq.get(0).getBloco());
                    freq.remove(0);
                    Frequente f = new Frequente();
                    f.setBloco(tag);
                    f.setFrequencia(1);
                    freq.add(f);
                    tabela.put(tag, tag);
                }
            } else {
                for (int j = 0; j < freq.size(); j++) {
                    if (tag == freq.get(j).getBloco()) {
                        freq.get(j).setFrequencia(freq.get(j).getFrequencia() + 1);
                        j = freq.size();
                    }
                }

            }
        }

        float Acerto = (1 - (float) ((float) FalhaNaCache / Testes.size())) * 100;
        System.out.println("Falhas: " + FalhaNaCache + " de " + Testes.size() + "- " + (Acerto) + "%");

        String result = ("Falhas: " + FalhaNaCache + " de " + Testes.size() + "- " + (Acerto) + "%");

        int espacoTAG = ((TAG * LinhasCache) / 8);
        associativo.SetElementos(Blocos, LinhasCache, LinhasPrincipal, espacoTAG, TAG, W, result, "FREQUENTE Associtivo");

    }

    public static void AleatorioAssociativoBinary(Associativo associativo) {
        int LinhasPrincipal = config.getPrincipal() / config.getPalavra();
        int PalavrasNaLinhaDaCache = config.getLinha();
        int Blocos = LinhasPrincipal / PalavrasNaLinhaDaCache;
        int TAG = (int) (Math.log(Blocos) / Math.log(2));
        int W = (int) (Math.log(PalavrasNaLinhaDaCache) / Math.log(2));
        int BitsPrincipal = (int) (Math.log(LinhasPrincipal) / Math.log(2));

        Hashtable tabela = new Hashtable();
        int LinhasCache = config.getCache() / (config.getPalavra() * config.getLinha());
        FalhaNaCache = 0;
        for (int i = 0; i < Testes.size(); i++) {
            String Teste = Integer.toBinaryString(Testes.get(i));

            while (Teste.length() < BitsPrincipal) {
                Teste = "0" + Teste;
            }
            String TTag = "";
            for (int j = 0; j < TAG; j++) {
                TTag = TTag + Teste.charAt(j);
            }
            int tag = Integer.parseInt(TTag, 2);

            if (!tabela.contains(tag)) {
                FalhaNaCache++;
                int x = (int) (Math.random() * LinhasCache);
                tabela.put(x, tag);
            }
        }

        float Acerto = (1 - (float) ((float) FalhaNaCache / Testes.size())) * 100;
        String result = ("Falhas: " + FalhaNaCache + " de " + Testes.size() + "- " + (Acerto) + "%");
        System.out.println("Falhas: " + FalhaNaCache + " de " + Testes.size() + "- " + (Acerto) + "%");

        int espacoTAG = ((TAG * LinhasCache) / 8);

        associativo.SetElementos(Blocos, LinhasCache, LinhasPrincipal, espacoTAG, TAG, W, result, "ALEATORIO Associativo");

//SetElementos(int Blocos, int LinhasCache, int LinhasPrincipal, int ETAG, int TAG,int palavra, String resultado, String origem) {
    }

    public static void RecenteAssociativoBinary(Associativo associativo) {

        int LinhasPrincipal = config.getPrincipal() / config.getPalavra();
        int PalavrasNaLinhaDaCache = config.getLinha();
        int Blocos = LinhasPrincipal / PalavrasNaLinhaDaCache;

        int W = (int) (Math.log(PalavrasNaLinhaDaCache) / Math.log(2));
        int BitsPrincipal = (int) (Math.log(LinhasPrincipal) / Math.log(2));
        int TAG = (int) BitsPrincipal - W;
        Hashtable tabela = new Hashtable();
        ArrayList<Integer> Recente = new ArrayList<Integer>();
        int LinhasCache = config.getCache() / (config.getPalavra() * config.getLinha());
        FalhaNaCache = 0;
        for (int i = 0; i < Testes.size(); i++) {

            String Teste = Integer.toBinaryString(Testes.get(i));

            while (Teste.length() < BitsPrincipal) {
                Teste = "0" + Teste;
            }
            String TTag = "";
            for (int j = 0; j < TAG; j++) {
                TTag = TTag + Teste.charAt(j);
            }
            int tag = Integer.parseInt(TTag, 2);

            if (!tabela.contains(tag)) {
                FalhaNaCache++;
                if (tabela.size() < LinhasCache) {
                    Recente.add(tag);
                    tabela.put(tag, tag);
                } else {
                    tabela.remove(Recente.remove(0));
                    tabela.put(tag, tag);
                    Recente.add(tag);
                }

            } else {
                Recente.remove(new Integer(tag));
                Recente.add(tag);
            }

        }
        float Acerto = (1 - (float) ((float) FalhaNaCache / Testes.size())) * 100;
        String result = ("Falhas: " + FalhaNaCache + " de " + Testes.size() + "- " + (Acerto) + "%");
        System.out.println("Falhas: " + FalhaNaCache + " de " + Testes.size() + "- " + (Acerto) + "%");

        int espacoTAG = ((TAG * LinhasCache) / 8);
        associativo.SetElementos(Blocos, LinhasCache, LinhasPrincipal, espacoTAG, TAG, W, result, "RECENTE Associtiavo");
        //int Blocos, int LinhasCache, int LinhasPrincipal, int ETAG, int TAG,int palavra, String resultado
        associativo.setVisible(true);
    }

    public static void MapeamentoDiretoBinary(Direto direto) {
        int PalavrasNaLinhaDaCache = config.getLinha();
        int LinhasPrincipal = config.getPrincipal() / config.getPalavra();
        int LinhasCache = config.getCache() / (config.getPalavra() * PalavrasNaLinhaDaCache);
        int Blocos = LinhasPrincipal / PalavrasNaLinhaDaCache;
        int BitsPrincipal = (int) (Math.log(LinhasPrincipal) / Math.log(2));

        int TAG = (int) (Math.log(config.getPrincipal() / config.getCache()) / Math.log(2));

        int W = (int) (Math.log(PalavrasNaLinhaDaCache) / Math.log(2));
        int palavra = BitsPrincipal - TAG - W;

        System.out.println("Endereco:" + TAG + " + " + palavra + " + " + W);

        FalhaNaCache = 0;
        int vetor[] = new int[LinhasCache];

        for (int i = 0; i < LinhasCache; i++) {
            vetor[i] = -1;
        }

        for (int i = 0; i < Testes.size(); i++) {
            String Teste = Integer.toBinaryString(Testes.get(i));

            while (Teste.length() < BitsPrincipal) {
                Teste = "0" + Teste;
            }
            String TTag = "";
            String TLinha = "";
            for (int j = 0; j < TAG; j++) {
                TTag = TTag + Teste.charAt(j);
            }
            for (int j = TAG; j < TAG + palavra; j++) {
                TLinha = TLinha + Teste.charAt(j);
            }
            int linha = Integer.parseInt(TLinha, 2);
            int tag = Integer.parseInt(TTag, 2);
            if (vetor[linha] != tag) {
                FalhaNaCache++;
                vetor[linha] = tag;

            }
        }
        float Acerto = (1 - (float) ((float) FalhaNaCache / Testes.size())) * 100;
        String result = ("Falhas: " + FalhaNaCache + " de " + Testes.size() + "- " + (Acerto) + "%");
        System.out.println("Falhas: " + FalhaNaCache + " de " + Testes.size() + "- " + (Acerto) + "%");
        int ETAG = (LinhasCache * TAG) / 8;
        System.out.println(ETAG);
        direto.SetElementos(ETAG, palavra, Blocos, LinhasCache, LinhasPrincipal, W, TAG, result, "DIRETO");
        //int ETAG, int LC, int NB, int NLC, int NLP, int palavra, int tab, String resultado

    }

    public static InputStreamReader Arquivo() throws FileNotFoundException {

        JFileChooser jfAbrir = new JFileChooser();
        // jfAbrir.setCurrentDirectory(new File("C:\\Users\\Cristiano\\Desktop\\TRABALHO"));
        jfAbrir.setCurrentDirectory(new File("C:\\Users\\Cris-\\Desktop\\Trabalho OAC"));
        int retorno = jfAbrir.showOpenDialog(null);
        String caminho = null;

        if (retorno == JFileChooser.APPROVE_OPTION) {
            caminho = jfAbrir.getSelectedFile().getAbsolutePath();
            String aux = jfAbrir.getSelectedFile().getName();

        }

        FileInputStream arquivo = new FileInputStream(caminho);

        InputStreamReader input = new InputStreamReader(arquivo);

        return input;
    }

    public static void LeituraDasConfiguracos() throws IOException {

        BufferedReader ler = new BufferedReader(Arquivo());
        ArrayList<String> Aux = new ArrayList<String>();
        int i = 0;
        while (i < 4) {
            String linha = ler.readLine();
            if (!(linha == null)) {
                String Auxiliar[] = linha.split("= ");
                String valor[] = Auxiliar[1].split(" ");
                String unidade[] = valor[1].split(";");
                Aux.add(valor[0]);
                Aux.add(unidade[0]);
            }
            i++;
        }

        config.setPrincipal(Integer.parseInt(Aux.get(0)) * Tamanho(Aux.get(1)));
        config.setPalavra(Integer.parseInt(Aux.get(2)) * Tamanho(Aux.get(3)));
        config.setCache(Integer.parseInt(Aux.get(4)) * Tamanho(Aux.get(5)));
        config.setLinha(Integer.parseInt(Aux.get(6)));

    }

    public static void LeituraDosTeste() throws FileNotFoundException, IOException {
        Testes.clear();
        BufferedReader ler = new BufferedReader(Arquivo());
        while (true) {
            String linha = ler.readLine();
            if (!(linha == null)) {
                Testes.add(Integer.parseInt(linha));
            } else {
                break;
            }
        }
    }

    public static int Tamanho(String tamanho) {

        switch (tamanho) {
            case "B":
                // System.out.println("Bytes");
                return 1;
            case "KB":
                //System.out.println("KBytes");
                return 1024;
            case "MB":
                //System.out.println("MBytes");
                return 1024 * 1024;
            case "GB":
                // System.out.println("GBytes");
                return 1024 * 1024 * 1024;

            default:
                System.out.println("Error");
                break;

        }

        return 0;

    }

}
