package com.tictactoe;

import java.util.HashMap;

public class Taulell {

    //region atributs
    private static final int COSTAT = 6;
    private static HashMap<String, Boolean> casellesOcupades = new HashMap<String, Boolean>();
    private static int totalOcupat;
    private static String[][] taulell = new String[COSTAT][COSTAT];
    //endregion atributs

    /**
     * Únic mètode constructor del taulell.
     */
    public Taulell() {
        //region cuadricula
        for (int i = 1; i < COSTAT; i++) {
            for (int j = 1; j < COSTAT; j++) {
                taulell[i][j] = "   ";
                if ((i == 2 || i == 4) && (j != 2 || j != 4)) {
                    taulell[i][j] = "---";
                }
                if (j==2 || j==4) {
                    taulell[i][j] = "|";
                }
            }
        }
        taulell[0][0] = "   ";
        //endregion

        //region indicadors horitzontals
        taulell[0][1] = " A ";
        taulell[0][2] = " ";
        taulell[0][3] = " B ";
        taulell[0][4] = " ";
        taulell[0][5] = " C ";
        //endregion
        //region indicadors verticales
        taulell[1][0] = " 1 ";
        taulell[2][0] = "   ";
        taulell[3][0] = " 2 ";
        taulell[4][0] = "   ";
        taulell[5][0] = " 3 ";
        //endregion

        //region estat caselles
        //1
        casellesOcupades.put("1A", false);
        casellesOcupades.put("1B", false);
        casellesOcupades.put("1C", false);
        //2
        casellesOcupades.put("2A", false);
        casellesOcupades.put("2B", false);
        casellesOcupades.put("2C", false);
        //3
        casellesOcupades.put("3A", false);
        casellesOcupades.put("3B", false);
        casellesOcupades.put("3C", false);
        //endregion
        
        totalOcupat = 0;
    }

    //region set

    /**
     * Actualitza el contador de caselles totals ocupades.
     * @param totalOcupat Valor actual + 1.
     */
    public static void setTotalOcupat(int totalOcupat) {
        Taulell.totalOcupat = totalOcupat;
    }

    /**
     * Actualitza el valor de la key introduïda.
     * @param casella Key a comprovar.
     * @param b Boleà a introduïr a la key anterior.
     */
    public void setCasellesOcupades(String casella, boolean b) {
        Taulell.casellesOcupades.put(casella, b);
    }
    //endregion set

    //region get
    /**
     * Entrega el HashMap amb la informació de les caselles.
     * @return HashMap<String, Boolean>
     */
    public HashMap<String, Boolean> getCasellesOcupades() {
        return casellesOcupades;
    }
    public String[][] getTaulell() {
        return taulell;
    }

    /**
     * Entrega el numero de caselles ocupades al taulell.
     * @return
     */
    public static int getTotalOcupat() {
        return totalOcupat;
    }
    //endregion get

    
    //region mètodes
    /**
     * Imprimeix el taulell de joc.
     */
    public void imprimeixTaulell() {
        for (int i = 0; i < COSTAT; i++) {
            System.out.print("    ");
            for (int j = 0; j < COSTAT; j++) {
                System.out.print(Taulell.taulell[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Comprova si la casella està ocupada o existeix.
     * @param casella Posició al taulell a comprovar.
     * @return Boleà o excepció.
     */
    public boolean checkCasellesOcupades(String casella) {
        return Taulell.casellesOcupades.get(casella);
    }
    
    /**
     * Escriure la fitxa del jugador a la posició indicada.
     * @param a Primera posició de l'array bidimensional.
     * @param b Segona posició de l'array bidimensional.
     * @param lletra Fitxa del jugador.
     */
    public void marcaCasella(int a, int b, char lletra) {
        Taulell.taulell[a][b] = " " + lletra + " ";
        setTotalOcupat(getTotalOcupat() + 1);
    }
    //endregion mètodes

}
