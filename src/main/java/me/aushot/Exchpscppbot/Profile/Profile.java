package me.aushot.Exchpscppbot.Profile;

import java.util.ArrayList;
import java.util.List;

public class Profile {

    private long chat_id;
    private String username;
    private int importpsc;
    private List<String> psc;
    private List<Integer> pscphoto;
    private String email;

    private boolean inizialized;
    private boolean passo1;
    private boolean passo2;
    private boolean passo3;
    private boolean passo4;
    private boolean passo5;
    private boolean confirmed;
    private boolean completed;

    private int count;
    public Profile(long chat_id, String username, int importpsc, List<String> psc, List<Integer> pscphoto, String email,
                   boolean inizialized, boolean passo1, boolean passo2, boolean passo3, boolean passo4, boolean passo5,
                   boolean confirmed, boolean completed, int count){
        this.chat_id = chat_id;
        this.inizialized = inizialized;
        this.passo1 = passo1;
        this.passo2 = passo2;
        this.passo3 = passo3;
        this.passo4 = passo4;
        this.passo5 = passo5;
        this.confirmed = confirmed;
        this.completed = completed;
        this.count = count;


        this.username = username;
        this.importpsc = importpsc;
        this.email = email;

        this.psc = psc;
        this.pscphoto = pscphoto;

    }
    public Profile () {
        this.inizialized = false;
        this.passo1 = false;
        this.passo2 = false;
        this.passo3 = false;
        this.passo4 = false;
        this.passo5 = false;
        this.confirmed = false;
        this.completed = false;
        this.count = 0;


        chat_id = 0;
        username = "";
        importpsc = 0;
        email = "";

        psc = new ArrayList<>();
        pscphoto = new ArrayList<>();
    }

    public void setId(long chat_id) {
        this.chat_id = chat_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public int getImportpsc() {
        return importpsc;
    }

    public List<String> getPsc() {
        return psc;
    }

    public List<Integer> getPscphoto() {
        return pscphoto;
    }

    public String getUsername() {
        return username;
    }

    public boolean isPasso1() {
        return passo1;
    }

    public boolean isPasso2() {
        return passo2;
    }

    public boolean isPasso3() {
        return passo3;
    }

    public boolean isPasso4() {
        return passo4;
    }

    public boolean isPasso5() {
        return passo5;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isInizialized() {
        return inizialized;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        if((username == "") || (importpsc == 0) || (email == "") || (psc.isEmpty())){
            return "";
        } else {
            return  "ID " + chat_id + "\n" +
                    "Username= @" + username + "\n" +
                    "Importo PSC= " + importpsc + "€\n" +
                    "PSC= " + psc + "\n" +
                    "Email= " + email + "\n" +
                    "Confermato= " + (confirmed ? "SI" : "NO");
        }
    }

    public void setImportpsc(int importpsc) {
        this.importpsc = importpsc;
    }

    public void setPsc(List<String> psc) {
        this.psc = psc;
    }

    public void setPscphoto(List<Integer> pscphoto) {
        this.pscphoto = pscphoto;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasso1(boolean passo1) {
        this.passo1 = passo1;
    }

    public void setPasso2(boolean passo2) {
        this.passo2 = passo2;
    }

    public void setPasso3(boolean passo3) {
        this.passo3 = passo3;
    }

    public void setPasso4(boolean passo4) {
        this.passo4 = passo4;
    }

    public void setPasso5(boolean passo5) {
        this.passo5 = passo5;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setInizialized(boolean inizialized) {
        this.inizialized = inizialized;
    }


    public void setCount(int count) {
        this.count = count;
    }

    public void setPasso(boolean passo1, boolean passo2, boolean passo3, boolean passo4, boolean passo5){
        this.passo1 = passo1;
        this.passo2 = passo2;
        this.passo3 = passo3;
        this.passo4 = passo4;
        this.passo5 = passo5;
    }
    public void setPasso(boolean passo1, boolean passo2, boolean passo3, boolean passo4){
        this.passo1 = passo1;
        this.passo2 = passo2;
        this.passo3 = passo3;
        this.passo4 = passo4;
    }
}
