package bsoftlabecr.entity;

public enum ArmenianLetter {

    AYB_CAP("0531", "Ա"),
    AYB_SMALL("0561", "ա"),
    BEN_CAP("0532", "Բ"),
    BEN_SMALL("0562", "բ"),
    GIM_CAP("0533", "Գ"),
    GIM_SMALL("0563", "գ"),
    DA_CAP("0534", "Դ"),
    DA_SMALL("0564", "դ"),
    YECH_CAP("0535", "Ե"),
    YECH_SMALL("0565", "ե"),
    ZA_CAP("0536", "Զ"),
    ZA_SMALL("0566", "զ"),
    E_CAP("0537", "Է"),
    E_SMALL("0567", "է"),
    YT_CAP("0538", "Ը"),
    YT_SMALL("0568", "ը"),
    TO_CAP("0539", "Թ"),
    TO_SMALL("0569", "թ"),
    JE_CAP("053A", "Ժ"),
    JE_SMALL("056A", "ժ"),
    INI_CAP("053B", "Ի"),
    INI_SMALL("056B", "ի"),
    LYOWN_CAP("053C", "Լ"),
    LYOWN_SMALL("056C", "լ"),
    KHE_CAP("053D", "Խ"),
    KHE_SMALL("056D", "խ"),
    CA_CAP("053E", "Ծ"),
    CA_SMALL("056E", "ծ"),
    KEN_CAP("053F", "Կ"),
    KEN_SMALL("056F", "կ"),
    HO_CAP("0540", "Հ"),
    HO_SMALL("0570", "հ"),
    DZA_CAP("0541", "Ձ"),
    DZA_SMALL("0571", "ձ"),
    GHAT_CAP("0542", "Ղ"),
    GHAT_SMALL("0572", "ղ"),
    CHE_CAP("0543", "Ճ"),
    CHE_SMALL("0573", "ճ"),
    MEN_CAP("0544", "Մ"),
    MEN_SMALL("0574", "մ"),
    YI_CAP("0545", "Յ"),
    YI_SMALL("0575", "յ"),
    NU_CAP("0546", "Ն"),
    NU_SMALL("0576", "ն"),
    SHA_CAP("0547", "Շ"),
    SHA_SMALL("0577", "շ"),
    VO_CAP("0548", "Ո"),
    VO_SMALL("0578", "ո"),
    CHA_CAP("0549", "Չ"),
    CHA_SMALL("0579", "չ"),
    PE_CAP("054A", "Պ"),
    PE_SMALL("057A", "պ"),
    DJE_CAP("054B", "Ջ"),
    DJE_SMALL("057B", "ջ"),
    RA_CAP("054C", "Ռ"),
    RA_SMALL("057C", "ռ"),
    SE_CAP("054D", "Ս"),
    SE_SMALL("057D", "ս"),
    VEV_CAP("054E", "Վ"),
    VEV_SMALL("057E", "վ"),
    TYUN_CAP("054F", "Տ"),
    TYUN_SMALL("057F", "տ"),
    RE_CAP("0550", "Ր"),
    RE_SMALL("0580", "ր"),
    CO_CAP("0551", "Ց"),
    CO_SMALL("0581", "ց"),
    VYUN_CAP("0552", "Ւ"),
    VYUN_SMALL("0582", "ւ"),
    PYUR_CAP("0553", "Փ"),
    PYUR_SMALL("0583", "փ"),
    QE_CAP("0554", "Ք"),
    QE_SMALL("0584", "ք"),
    O_CAP("0555", "Օ"),
    O_SMALL("0585", "օ"),
    FE_CAP("0556", "Ֆ"),
    FE_SMALL("0586", "ֆ"),
    YEV("0587", "և"),
    L0559("0559", ""),
    L055A("055A", ""),
    L055B("055B", ""),
    L055C("055C", ""),
    L055D("055D", ""),
    L055E("055E", ""),
    L055F("055F", ""),
    L0589("0589", "");

    private final String codering;
    private final String letter;

    ArmenianLetter(String codering, String letter) {
        this.codering = codering;
        this.letter = letter;
    }

    public String getCodering() {
        return this.codering;
    }
    public String getLetter() {
        return this.letter;
    }

    @Override
    public String toString() {
        return "[" +
                this.name() + ": " +
                this.codering + ", " +
                this.letter + "]";
    }
}