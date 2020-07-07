
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Widok extends JPanel implements ActionListener {


    JTextField [][] notacjaDH;
    JLabel[] napis;
    JLabel napis1,napis2,napis3,napis4,napis5,napis6,napis7,napis8;
    JLabel[] napisNDH,napisXYZ,wynik;
    JTextArea kom;
    JScrollPane komentarz;
    JRadioButton[] wybierzTryb;
    ButtonGroup gWybierzTryb;
    JButton dalej1,dalej2,dalej3,reset,plus,minus;
    JTextField[] dodatkowe1,dodatkowe2;
    boolean rt=false;

    double[] przegub1,przegub2,przegub3,przegub4,wynik1;

    public int[][] tablicaDH;
    Obliczenia obl = new Obliczenia();

    public Widok() {

    setLayout(null);
    przegub1 = new double[4];
    przegub2 = new double[4];
    przegub3 = new double[4];
    przegub4 = new double[3];
    wynik1 = new double[3];



//Sekwencja wyboru trybu

    napis2 = new JLabel("----------Co obliczyć----------\n");
    napis2.setBounds(25, 5, 300, 30);
    add(napis2);

    gWybierzTryb = new ButtonGroup();
    wybierzTryb = new JRadioButton[6];
    wybierzTryb[0] = new JRadioButton("Położenie punktu TCP", true);
    wybierzTryb[1] = new JRadioButton("Ustawienie przegubów", true);
    wybierzTryb[2] = new JRadioButton("Prędkość punktu TCP", true);
    wybierzTryb[3] = new JRadioButton("Prędkość przegubów", true);
    wybierzTryb[4] = new JRadioButton("Siła jaką przenosi punkt TCP", true);
    wybierzTryb[5] = new JRadioButton("Siła przenoszona przez przeguby", true);


    for (int i = 0; i < 6; i++) {
        wybierzTryb[i].setBounds(25, 25 + i * 30, 230, 30);
        gWybierzTryb.add(wybierzTryb[i]);
        add(wybierzTryb[i]);
    }

    dalej2 = new JButton("Potwierdź");
    dalej2.setBounds(270, 180, 100, 30);
    add(dalej2);
    dalej2.addActionListener(this);


    //Sekwencja wyboru Denavita-Hartenberga
    napis1 = new JLabel("----------Notacja Denavita-Hartenberga----------\n");
    napis1.setBounds(25, 205, 300, 50);
    add(napis1);

    notacjaDH = new JTextField[3][4];
    napisNDH = new JLabel[3];
    for (int j = 0; j < 3; j++) {
        for (int i = 0; i < 4; i++) {
            notacjaDH[j][i] = new JTextField();
            notacjaDH[j][i].setBounds(50 + i * 50, 250 + j * 40, 40, 30);
            add(notacjaDH[j][i]);
            notacjaDH[j][i].setEditable(false);
        }
        napisNDH[j] = new JLabel(Integer.toString(j + 1));
        napisNDH[j].setBounds(30, 250 + j * 40, 20, 30);
        add(napisNDH[j]);
    }

    dalej1 = new JButton("Potwierdź");
    dalej1.setBounds(270, 360, 100, 30);
    add(dalej1);
    dalej1.addActionListener(this);
    dalej1.setVisible(false);

    kom = new JTextArea();
    komentarz = new JScrollPane(kom);
    komentarz.setBounds(400, 25, 350, 300);
    add(komentarz);

//Panele dodatkowe
    napis = new JLabel[10];

    dodatkowe1 = new JTextField[3];
    for (int i = 0; i < 3; i++) {
        dodatkowe1[i] = new JTextField();
        dodatkowe1[i].setBounds(50, 440 + i * 40, 40, 30);
        add(dodatkowe1[i]);
        dodatkowe1[i].setVisible(false);

        napisNDH[i] = new JLabel(Integer.toString(i + 1));
        napisNDH[i].setBounds(25, 440 + i * 40, 20, 30);
        add(napisNDH[i]);
        napisNDH[i].setVisible(false);
    }
    napisXYZ = new JLabel[3];
    napisXYZ[0] = new JLabel("X");
    napisXYZ[1] = new JLabel("Y");
    napisXYZ[2] = new JLabel("Z");

    napis[0] = new JLabel("--Położenie przgubów--\n");
    napis[0].setBounds(25, 400, 200, 30);

    napis[1] = new JLabel("--Położenie punktu TCP względem ostatniego członu--\n");
    napis[1].setBounds(220, 400, 350, 30);

    napis[2] = new JLabel("--Prędkości poszczegolnych prezgubow--\n");
    napis[2].setBounds(220, 400, 350, 30);

    napis[3] = new JLabel("--Prędkość punktu TCP w poszczególnych osiach--\n");
    napis[3].setBounds(220, 400, 350, 30);

    napis[4] = new JLabel("--Siły/momenty poszczególnych przegubów--\n");
    napis[4].setBounds(220, 400, 350, 30);

    napis[5] = new JLabel("--Siła z jaką działa punkt TCP w poszczególnych osiach--\n");
    napis[5].setBounds(220, 400, 350, 30);

    napis[6] = new JLabel("--Położenie punktu TCP względem początku układu wsp. robota--\n");
    napis[6].setBounds(220, 400, 400, 30);


    for (int i = 0; i < 7; i++) {
        add(napis[i]);
        napis[i].setVisible(false);
    }

    dodatkowe2 = new JTextField[3];
    for (int i = 0; i < 3; i++) {
        dodatkowe2[i] = new JTextField();
        dodatkowe2[i].setBounds(250, 440 + i * 40, 40, 30);
        add(dodatkowe2[i]);
        dodatkowe2[i].setVisible(false);

        napisXYZ[i].setBounds(230, 440 + i * 40, 20, 30);
        add(napisXYZ[i]);
        napisXYZ[i].setVisible(false);
    }

    dalej3 = new JButton("Oblicz");
    dalej3.setBounds(350, 520, 100, 30);
    add(dalej3);
    dalej3.addActionListener(this);
    dalej3.setVisible(false);

//Wynik

    napis5 = new JLabel("--Wyniki--\n");
    napis5.setBounds(620, 400, 350, 30);
    add(napis5);
    napis5.setVisible(false);

    wynik = new JLabel[3];
    for (int i = 0; i < 3; i++) {
        wynik[i] = new JLabel();
        wynik[i].setBounds(610, 440 + i * 40, 400, 30);
        add(wynik[i]);
        wynik[i].setVisible(false);
    }

//Reset
    reset = new JButton("Reset");
    reset.setBounds(580, 360, 100, 30);
    add(reset);
    reset.addActionListener(this);
    reset.setVisible(true);

//Zwiekszanie i zmniejszanie L3

        plus = new JButton("+");
        plus.setBounds(740, 520, 50, 30);
        add(plus);
        plus.addActionListener(this);
        plus.setVisible(false);

        minus = new JButton("-");
        minus.setBounds(690, 520, 50, 30);
        add(minus);
        minus.addActionListener(this);
        minus.setVisible(false);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (clicked == dalej1) {
            if (czyPoprawneDH()) {
                kom.append("[OK] Poprawnie dodano notacje Denavita-Hartenberga\n");

            } else {
                kom.append("[BŁĄD] Notacje Denavita-Hartenberga została podana\nnieprawidłowo\n");
                kom.append("[INFO] Należy uzupełnić wszystkie pola liczbami\n");
            }
        }
        if (clicked == dalej2) {

            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 4; i++) {
                    notacjaDH[j][i].setEditable(true);
                }
            }
            notacjaDH[0][3].setEditable(false);
            notacjaDH[1][3].setEditable(false);
            notacjaDH[2][2].setEditable(false);

            dalej1.setVisible(true);


            for (int i = 0; i < 6; i++) {
                napis[i].setVisible(false);
            }

            dalej3.setVisible(true);
            if (wybierzTryb[0].isSelected()) {
                kom.append("[OK] Wybrano obliczanie położenia punktu TCP\n");
                napis[0].setVisible(true);
                napis[1].setVisible(true);
                for (int i = 0; i < 3; i++) {
                    dodatkowe1[i].setVisible(true);
                    napisNDH[i].setVisible(true);
                    dodatkowe2[i].setVisible(true);
                    napisXYZ[i].setVisible(true);
                }
            }
            if (wybierzTryb[1].isSelected()) {
                kom.append("[OK] Wybrano obliczanie położenia poszczególnych\nprzegubów\n");
                notacjaDH[0][0].setText("0");
                notacjaDH[0][0].setEditable(false);
                notacjaDH[1][0].setText("0");
                notacjaDH[1][0].setEditable(false);
                notacjaDH[2][0].setText("90");
                notacjaDH[2][0].setEditable(false);
                notacjaDH[0][1].setText("0");
                notacjaDH[0][1].setEditable(false);
                notacjaDH[0][2].setText("0");
                notacjaDH[0][2].setEditable(false);
                notacjaDH[1][2].setText("0");
                notacjaDH[1][2].setEditable(false);
                notacjaDH[2][3].setText("0");
                notacjaDH[2][3].setEditable(false);

                notacjaDH[0][3].setText("θ1");
                notacjaDH[1][3].setText("θ2");
                notacjaDH[2][2].setText("L3");

                napis[6].setVisible(true);
                for (int i = 0; i < 3; i++) {

                    dodatkowe2[i].setVisible(true);
                    napisXYZ[i].setVisible(true);
                }
            }
            if (wybierzTryb[2].isSelected()) {
                kom.append("[OK] Wybrano obliczanie prędkości członu roboczego \nw poszczególnych osiach TCP\n");
                napis[0].setVisible(true);
                napis[2].setVisible(true);
                for (int i = 0; i < 3; i++) {
                    dodatkowe1[i].setVisible(true);
                    napisNDH[i].setVisible(true);
                    dodatkowe2[i].setVisible(true);
                    napisXYZ[i].setVisible(true);
                }
            }
            if (wybierzTryb[3].isSelected()) {
                kom.append("[OK] Wybrano obliczanie prędkości poszczególnych członów\n");
                napis[0].setVisible(true);
                napis[3].setVisible(true);
                for (int i = 0; i < 3; i++) {
                    dodatkowe1[i].setVisible(true);
                    napisNDH[i].setVisible(true);
                    dodatkowe2[i].setVisible(true);
                    napisXYZ[i].setVisible(true);
                }
            }
            if (wybierzTryb[4].isSelected()) {
                kom.append("[OK] Wybrano obliczanie siły w poszczególnych osiach, ktora\nmoze przeniesc człon roboczy w poszczególnych osiach\n");
                napis[0].setVisible(true);
                napis[4].setVisible(true);
                for (int i = 0; i < 3; i++) {
                    dodatkowe1[i].setVisible(true);
                    napisNDH[i].setVisible(true);
                    dodatkowe2[i].setVisible(true);
                    napisXYZ[i].setVisible(true);
                }
            }
            if (wybierzTryb[5].isSelected()) {
                kom.append("[OK] Wybrano obliczanie siły/momenty przenoszonego \nprzez poszczególne przeguby\n");
                napis[0].setVisible(true);
                napis[5].setVisible(true);
                for (int i = 0; i < 3; i++) {
                    dodatkowe1[i].setVisible(true);
                    napisNDH[i].setVisible(true);
                    dodatkowe2[i].setVisible(true);
                    napisXYZ[i].setVisible(true);
                }
            }

        }

        if (clicked == dalej3) {
            if (czyPoprawneDodatkowe()) {
                kom.append("[OK] Poprawnie wprowadzono dane dodatkowe\n");
                if (czyPoprawneDH()) {
                    wynik1 = oblicz();
                    if(wynik1[0]!=-999) {
                    for (int i = 0; i < 3; i++) {
                        wynik[i].setText("[ " + Double.toString(wynik1[i]) + " ]");
                        napis5.setVisible(true);
                        wynik[i].setVisible(true);
                    }
                }
                    else{
                        kom.append("\n[OK] Pozycja niemożliwa do osiągnięcia");
                    }
                } else {
                    kom.append("[INFO] Należy jeszcze uzupełnić notacje DH\n");
                }
            } else {
                kom.append("[BŁĄD] Dane dodatkowe zostały wprowadzone nieprawidłowo\n");
                kom.append("[INFO] Należy uzupełnić wszystkie pola liczbami\n");
            }
        }

        if(clicked == plus){
            obl.edytujL3(1);
            wynik1 = oblicz();
            kom.append("\nL3="+obl.L3+"\n");
            if(wynik1[0]!=-999) {
                for (int i = 0; i < 3; i++) {
                    wynik[i].setText("[ " + Double.toString(wynik1[i]) + " ]");
                    napis5.setVisible(true);
                    wynik[i].setVisible(true);
                }
            } else{
                kom.append("\n[OK] Pozycja niemożliwa do osiągnięcia");
            }
        }

        if(clicked == minus){
            obl.edytujL3(-1);
            kom.append("\nL3="+obl.L3+"\n");
            wynik1 = oblicz();
            if(wynik1[0]!=-999) {
                for (int i = 0; i < 3; i++) {
                    wynik[i].setText("[ " + Double.toString(wynik1[i]) + " ]");
                    napis5.setVisible(true);
                    wynik[i].setVisible(true);
                }
            }else{
                kom.append("\n[OK] Pozycja niemożliwa do osiągnięcia\n");
            }
        }

        if(clicked==reset){
            kom.append("\n[OK] Zresetowano program\n");
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < 4; i++) {
                    notacjaDH[j][i].setEditable(false);
                    notacjaDH[j][i].setText("");
                }
            }


            for (int i = 0; i < 7; i++) {
                napis[i].setVisible(false);
            }
            for (int i = 0; i < 3; i++) {
                dodatkowe1[i].setVisible(false);
                dodatkowe1[i].setText("");
                napisNDH[i].setVisible(false);
                dodatkowe2[i].setVisible(false);
                dodatkowe2[i].setText("");
                napisXYZ[i].setVisible(false);
                wynik[i].setVisible(false);
                wynik[i].setText("");
                napis5.setVisible(false);
            }
            dalej1.setVisible(false);
            dalej3.setVisible(false);
            plus.setVisible(false);
            minus.setVisible(false);

        }
    }

    public double [] oblicz () {
        double [] wynik = new double[3];

        if (wybierzTryb[0].isSelected()) {
            wynik = obl.prosteZadanie(przegub1, przegub2,przegub3,przegub4);
            kom.append("[OK] Obliczono proste zadanie kinematyki\n[INFO] Wynik przedstawia kolejno położenie TCP w osi X, Y\noraz Z\n");
        }
        else if (wybierzTryb[1].isSelected()) {
            wynik = obl.odwrotneZadanie(przegub1, przegub2,przegub3,przegub4);
            kom.append("\n[OK] Obliczono odwrotne zadanie kinematyki\n[INFO] Wynik przedstawia kolejno: ugięcie przegubu\nobrotowego θ1, ugięcie przegubu " +
                    "obrotowego θ2 oraz\nwysunięcie przegubu translacyjnego L3\n");
            if(!plus.isVisible()) {
                plus.setVisible(true);
                minus.setVisible(true);
                kom.append("\n[INFO] Możliwe zwiekszenie minimalnego wysuniecia\nprzegubu translacyjnego za pomoca przyskow '-' oraz '+'\n");
            }

        }
        else if (wybierzTryb[2].isSelected()) {
            wynik = obl.predkoscTCP(przegub1, przegub2,przegub3,przegub4);
            kom.append("[OK] Obliczono prędkość członu roboczego TCP\n[INFO] Wynik przedstawia kolejno prędkość TCP w osi X, Y\noraz Z\n");
        }
        else if (wybierzTryb[3].isSelected()) {
            wynik = obl.predkoscPrzegubow(przegub1, przegub2,przegub3,przegub4);
            kom.append("[OK] Obliczono prędkości poszczególnych przegubów\n[INFO] Wynik przedstawia kolejno moment pierwszego\nprzegubu obrotowego, " +
                    "moment drugiego członu obrotowego\noraz prędkość członu translacyjnego\n");
        }
        else if (wybierzTryb[4].isSelected()) {
            wynik = obl.silaTCP(przegub1, przegub2,przegub3,przegub4);
            kom.append("[OK] Obliczono siły przenoszone przez człon TCP\n[INFO] Wynik przedstawia kolejno siłę przenosząną przez TCP\nw osi X, Y oraz Z\n");
        }
        else if (wybierzTryb[5].isSelected()) {
            wynik = obl.silaPrzegubow(przegub1, przegub2,przegub3,przegub4);
            kom.append("[OK] Obliczono siły przenoszone przez poszczególne przeguby\n[INFO] Wynik przedstawia kolejno siły przenoszone przez\nposzczególne człony\n");
        }

        for (int i = 0; i < 3; i++) {
                wynik[i] = BigDecimal.valueOf(wynik[i]).setScale(3, RoundingMode.HALF_UP).doubleValue();
        }
        return wynik;
    }

    public  boolean czyPoprawneDH (){
        try{
            przegub1[0]=Double.parseDouble(notacjaDH[0][0].getText());
            przegub1[1]=Double.parseDouble(notacjaDH[0][1].getText());
            przegub1[2]=Double.parseDouble(notacjaDH[0][2].getText());

            przegub2[0]=Double.parseDouble(notacjaDH[1][0].getText());
            przegub2[1]=Double.parseDouble(notacjaDH[1][1].getText());
            przegub2[2]=Double.parseDouble(notacjaDH[1][2].getText());

            przegub3[0]=Double.parseDouble(notacjaDH[2][0].getText());
            przegub3[1]=Double.parseDouble(notacjaDH[2][1].getText());
            przegub3[2]=Double.parseDouble(notacjaDH[2][3].getText());
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public  boolean czyPoprawneDodatkowe(){
        try{
            if(!wybierzTryb[1].isSelected()){
                przegub1[3]=Double.parseDouble(dodatkowe1[0].getText());
                przegub2[3]=Double.parseDouble(dodatkowe1[1].getText());
                przegub3[2]=Double.parseDouble(dodatkowe1[2].getText());
            }

            przegub4[0]=Double.parseDouble(dodatkowe2[0].getText());
            przegub4[1]=Double.parseDouble(dodatkowe2[1].getText());
            przegub4[2]=Double.parseDouble(dodatkowe2[2].getText());


        }catch (Exception e){
            return false;
        }
        return true;
    }


}
