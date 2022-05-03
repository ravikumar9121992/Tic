package com.tictactoe;

import java.util.Scanner;
import java.io.DataInputStream;
import java.io.IOException;

public class Joc {

    // region Variables
    private static String COORDENADES = "";
    private static String GUANYADOR = "";
    private static boolean HA_GUANYAT = false;
    private static boolean EMPAT = false;
    private static boolean ORDRE = true;
    private static boolean ESPERA_BOTO = true;
    // endregion Variables

    // region Errors al programa
    private static boolean ERROR_PROGRAMA = false;
    private static boolean ERROR_TUTORIAL_INICI = false;
    private static boolean ERROR_ENTRADA_JOC = false;
    private static boolean ERROR_CASELLA_OCUPADA_JOC = false;
    private static String ERROR_NOM_JUGADOR_JOC = "";
    private static boolean ERROR_ELECCIO_FINAL = false;
    private static String ERROR_ELECCIO_FINAL_MISSATGE = "";
    // endregion Errors al programa

    // region Objectes
    private static Taulell t;
    private static final Jugador x = new Jugador("Arnau", 'X');
    private static final Jugador o = new Jugador("Uanra", 'O');
    private static final Scanner scan = new Scanner(System.in);
    // endregion Objectes

    // region Maquina d'estats
    private EstatPrograma estat;

    enum EstatPrograma {
        INICI,
        JOC,
        FINAL,
        TANCAR
    }

    /**
     * Estructura de funcionament del joc.
     * 
     * @throws InterruptedException
     * @throws IOException
     */
    public void run() throws InterruptedException, IOException {
        estat = EstatPrograma.INICI;
        while (true) {
            switch (estat) {
                case INICI:
                    inici();
                    estat = EstatPrograma.JOC;
                    break;
                case JOC:
                    joc();
                    estat = EstatPrograma.FINAL;
                    break;
                case FINAL:
                    finalJoc();
                    break;
                case TANCAR:
                    tancar();
                    System.exit(0);
                    break;

                default:
                    break;
            }
        }
    }
    // endregion Maquina d'estats

    // region Inici
    /**
     * Primera "etapa" del programa, pregunta si necesites una explicació de com
     * funciona el programa.
     * 
     * @throws InterruptedException
     * @throws IOException
     */
    public void inici() throws InterruptedException, IOException {
        netejaPantalla();
        if (ERROR_PROGRAMA) {
            System.out.println("\n  ALERTA!");
            if (ERROR_TUTORIAL_INICI) {
                System.out.println("  - Si ja comences així, tu i jo parlarem mes del que sembla...\n");
                ERROR_TUTORIAL_INICI = false;
            }
            ERROR_PROGRAMA = false;
        }
        System.out.println("Necesites una explicació? (Si/No)");
        String tuto = scan.nextLine();
        switch (tuto.toUpperCase()) {
            case "SI":
                tutorial();
                netejaPantalla();
                System.out.print("\n  COMENÇEM!");
                Thread.sleep(3000);
                break;
            case "NO":
                netejaPantalla();
                System.out.print("\n  COMENÇEM!");
                Thread.sleep(3000);
                break;

            default:
                ERROR_PROGRAMA = true;
                ERROR_TUTORIAL_INICI = true;
                inici();
                break;
        }
    }

    /**
     * Petita explicació de com funciona el programa.
     * 
     * @throws InterruptedException
     * @throws IOException
     */
    public void tutorial() throws InterruptedException, IOException {

        netejaPantalla();
        System.out.println("Benvingut al treball del TicTacToe de l'Arnau.\n");
        borrarLinea("continuar");
        System.out.print("Suposo que ja saps com funciona, no és molt complicat.\n\n");
        borrarLinea("continuar");
        System.out.print(
                "Tots hem jugat alguna vegada amb la persona amb la qual compartíem taula a classe quan el tema no interessava... \n\n");
        borrarLinea("continuar");
        System.out.print(
                "Bé, principalment va del fet que col·loquis la teva fitxa a una casella buida d'un taulell de 3x3");
        Thread.sleep(2000);
        System.out.print(", per torns");
        Thread.sleep(2000);
        System.out.print(", \nfins que aconsegueixis alinear 3 fitxes");
        Thread.sleep(2000);
        System.out.print(", pero jugues contra algú, i també vol guanyar, PENSA!\n\n");
        borrarLinea("començar");

    }

    /**
     * "Botó" que s'escriu i s'elimina un cop hem premut la tecla enter.
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    public void borrarLinea(String accio) throws IOException, InterruptedException {
        Thread.sleep(2000);

        System.out.print("(Prem enter per " + accio + ".)");
        while (true) {
            DataInputStream tecla = new DataInputStream(System.in);
            if (tecla.read() == 13) {
                System.out.print("\033[F                                                                          \r");
                break;
            }
        }
        Thread.sleep(250);
    }
    // endregion Inici

    // region Joc
    /**
     * Entorn on s'executa tot el procediment del joc.
     */
    public void joc() {
        t = new Taulell();

        while (true) {

            try {
                netejaPantalla();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            graph();

            if (ORDRE) {
                moviment(x);
            } else {
                moviment(o);
            }

            if (HA_GUANYAT || EMPAT) {
                break;
            }
        }
    }

    /**
     * Imprimeix l'"interfície" del joc.
     */
    public void graph() {
        System.out.println(" Tic-Tac-Toe");
        System.out.println("|===========|\n");
        if (ORDRE) {
            System.out.println(
                    " " + x.getNom() + " vs " + o.getNom() + "\n  Es el torn de -" + x.getNom() + "-\n");
        } else {
            System.out.println(
                    " " + x.getNom() + " vs " + o.getNom() + "\n  Es el torn de -" + o.getNom() + "-\n");
        }
        t.imprimeixTaulell();
        if (ERROR_PROGRAMA) {
            System.out.println("  ALERTA " + ERROR_NOM_JUGADOR_JOC);
            if (ERROR_ENTRADA_JOC) {
                System.out.println("  - Has de seguir les coordenades del taulell.\n");
                ERROR_ENTRADA_JOC = false;
            }
            if (ERROR_CASELLA_OCUPADA_JOC) {
                System.out.println("  - Has de seleccionar una casella que estigui lliure.\n");
                ERROR_CASELLA_OCUPADA_JOC = false;
            }
            ERROR_PROGRAMA = false;
            ERROR_NOM_JUGADOR_JOC = "";
        }
        System.out.print("Indica posició ");
    }

    /**
     * Agafa la coordenada indicada pel jugador i comproba si existeis i si está
     * ocupada, imprimeix errors en diferents casos.
     * 
     * Si el programa falla pel fet de que la casella indicada ja está marcada,
     * salta una alerta.
     * 
     * @param j Jugador del torn actual.
     */
    public void moviment(Jugador j) {
        COORDENADES = scan.nextLine();
        try {
            COORDENADES = Jugador.ordenarEntrada(COORDENADES).toString().toUpperCase();
            if (!t.checkCasellesOcupades(COORDENADES)) {
                j.comprobaCasella(COORDENADES, t);
                ORDRE = (ORDRE == false);
            } else {
                ERROR_PROGRAMA = true;
                ERROR_CASELLA_OCUPADA_JOC = true;
                ERROR_NOM_JUGADOR_JOC = j.getNom();
            }
        } catch (Exception e) {
            ERROR_PROGRAMA = true;
            ERROR_ENTRADA_JOC = true;
            ERROR_NOM_JUGADOR_JOC = j.getNom();
        }
        guanyador(j);
    }

    /**
     * Comprova si ja hi han 3 fitxes en ratlla en alguna posició.
     * 
     * @param j Jugador de la ronda actual.
     */
    public void guanyador(Jugador j) {
        String[][] posicion = t.getTaulell();

        if (// region verticals
        posicion[1][1].equals(" " + String.valueOf(j.getFitxa()) + " ") &&
                posicion[3][1].equals(" " + String.valueOf(j.getFitxa()) + " ") &&
                posicion[5][1].equals(" " + String.valueOf(j.getFitxa()) + " ") ||

                posicion[1][3].equals(" " + String.valueOf(j.getFitxa()) + " ") &&
                        posicion[3][3].equals(" " + String.valueOf(j.getFitxa()) + " ") &&
                        posicion[5][3].equals(" " + String.valueOf(j.getFitxa()) + " ")
                ||

                posicion[1][5].equals(" " + String.valueOf(j.getFitxa()) + " ") &&
                        posicion[3][5].equals(" " + String.valueOf(j.getFitxa()) + " ") &&
                        posicion[5][5].equals(" " + String.valueOf(j.getFitxa()) + " ")
                ||
                // endregion verticals
                // region diagonales
                posicion[1][1].equals(" " + String.valueOf(j.getFitxa()) + " ") &&
                        posicion[3][3].equals(" " + String.valueOf(j.getFitxa()) + " ") &&
                        posicion[5][5].equals(" " + String.valueOf(j.getFitxa()) + " ")
                ||

                posicion[1][5].equals(" " + String.valueOf(j.getFitxa()) + " ") &&
                        posicion[3][3].equals(" " + String.valueOf(j.getFitxa()) + " ") &&
                        posicion[5][1].equals(" " + String.valueOf(j.getFitxa()) + " ")
                ||
                // endregion diagonals
                // region horitzontals
                posicion[1][1].equals(" " + String.valueOf(j.getFitxa()) + " ") &&
                        posicion[1][3].equals(" " + String.valueOf(j.getFitxa()) + " ") &&
                        posicion[1][5].equals(" " + String.valueOf(j.getFitxa()) + " ")
                ||

                posicion[3][1].equals(" " + String.valueOf(j.getFitxa()) + " ") &&
                        posicion[3][3].equals(" " + String.valueOf(j.getFitxa()) + " ") &&
                        posicion[3][5].equals(" " + String.valueOf(j.getFitxa()) + " ")
                ||

                posicion[5][1].equals(" " + String.valueOf(j.getFitxa()) + " ") &&
                        posicion[5][3].equals(" " + String.valueOf(j.getFitxa()) + " ") &&
                        posicion[5][5].equals(" " + String.valueOf(j.getFitxa()) + " ")
        // endregion horitzontals
        ) {
            System.out.println("jiji");
            GUANYADOR = j.getNom();
            HA_GUANYAT = true;
            estat = EstatPrograma.FINAL;
        }
        if (Taulell.getTotalOcupat() == 9) {
            EMPAT = true;
            Taulell.setTotalOcupat(0);
        }
    }
    // endregion Joc

    // region Final
    /**
     * Pantalla final del joc, dona 2 opcions y te avisos d'error.
     * 
     * @throws InterruptedException
     */
    public void finalJoc() throws InterruptedException {
        netejaPantalla();

        if (ERROR_PROGRAMA) {
            System.out.println("\n  ALERTA");
            if (ERROR_ELECCIO_FINAL) {
                System.out.println("  - " + ERROR_ELECCIO_FINAL_MISSATGE + " No es un valor valid.\n");
                System.out.println("  Has d'escollir una d'aquestes.");
                ERROR_ELECCIO_FINAL = false;
            }
            ERROR_PROGRAMA = false;
        } else if (HA_GUANYAT) {
            System.out.println("\n  " + GUANYADOR + " ha resultat guanyador!\n");
            System.out.println("  Tornar a jugar?");
            HA_GUANYAT = false;
        } else if (EMPAT) {
            System.out.println("\n  S'ha empatat.\n");
            System.out.println("  Tornar a jugar?");
            EMPAT = false;
        }

        System.out.println("  Jugar una altra partida - SI\n  Finalitzar el programa - NO\n");
        System.out.print("\n   Opció: ");
        opcioFinalJoc(scan.nextLine());
    }

    /**
     * Determina quina es la seguent acció un cop donada la decisió a la pantalla
     * final.
     * 
     * @param decisio
     * @throws InterruptedException
     */
    private void opcioFinalJoc(String decisio) throws InterruptedException {
        switch (decisio.toUpperCase()) {
            case "SI":
                for (String posicio : t.getCasellesOcupades().keySet()) {
                    t.setCasellesOcupades(posicio, false);
                }
                estat = EstatPrograma.JOC;
                break;
            case "NO":
                netejaPantalla();
                estat = EstatPrograma.TANCAR;
                break;

            default:
                ERROR_PROGRAMA = true;
                ERROR_ELECCIO_FINAL = true;
                ERROR_ELECCIO_FINAL_MISSATGE = decisio;
                break;
        }
    }

    // endregion Final

    // region Tancar
    /**
     * Compte enrere per tancar el programa.
     * 
     * @throws InterruptedException
     */
    private void tancar() throws InterruptedException {
        System.out.print("\n  Fins aviat!\n    Tancant en     ");
        int contTancar = 5;
        while (contTancar != -1) {
            System.out.print("\b\b\b\b" + contTancar + "...");
            Thread.sleep(1000);
            contTancar--;
        }
        netejaPantalla();
    }
    // endregion Tancar

    //region Mètodes
    /**
     * Neteja la terminal esperant un segon per una continuitat menys brusca.
     * 
     * @throws InterruptedException
     */
    public void netejaPantalla() throws InterruptedException {
        Thread.sleep(1000);
        System.out.print("\033[H\033[2J");
    }
    //endregion Mètodes
}
