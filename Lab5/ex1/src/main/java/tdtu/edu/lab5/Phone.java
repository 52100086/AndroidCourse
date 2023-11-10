package tdtu.edu.lab5;
public class Phone {
    private boolean checked;
    private String name;

    public Phone(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
