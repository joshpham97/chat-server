package Beans;

public class ThemeManager implements java.io.Serializable{
    private Theme theme;

    public ThemeManager(){
        theme = Theme.LIGHT;
    }

    public String getBackgroundCSSClass(){
        return (theme == Theme.LIGHT) ? "" : "bg-dark";
    }

    public String getTheme(){
        return theme.toString();
    }

    public void setTheme(String theme){
        this.theme = Theme.valueOf(theme);
    }
}
