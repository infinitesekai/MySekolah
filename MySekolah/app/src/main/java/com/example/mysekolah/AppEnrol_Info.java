package com.example.mysekolah;

public class AppEnrol_Info {

    private String ICSt;
    private String nameSt;
    private String genderSt;
    private String raceSt;
    private String religionSt;
    private String nationalitySt;
    private String addSt;
    private String pcSt;
    private String stateSt;
    private String districtSt;
    private String telSt;

    private String ICPr;
    private String namePr;
    private String genderPr;
    private String racePr;
    private String religionPr;
    private String nationalityPr;
    private String addPr;
    private String pcPr;
    private String statePr;
    private String districtPr;
    private String telPr;
    private String job;
    private String salary;

    private String type;
    private String stateSc;
    private String districtSc;
    private String nameSc;
    private String distance;
    private String status;

    //constructor
    public AppEnrol_Info(String ICSt, String nameSt, String genderSt,
                         String raceSt,String religionSt,String nationalitySt,String addSt,String pcSt,String stateSt,String districtSt,String telSt,
                         String ICPr, String namePr, String genderPr,
                         String racePr,String religionPr,String nationalityPr,String addPr,String pcPr,String statePr,String districtPr,String telPr,
                         String job,String salary,String type,String stateSc,String districtSc,String nameSc,String distance,String status){
        this.ICSt = ICSt;
        this.nameSt = nameSt;
        this.genderSt=genderSt;
        this.raceSt=raceSt;
        this.religionSt=religionSt;
        this.nationalitySt=nationalitySt;
        this.addSt=addSt;
        this.pcSt=pcSt;
        this.stateSt=stateSt;
        this.districtSt=districtSt;
        this.telSt=telSt;

        this.ICPr=ICPr;
        this.namePr=namePr;
        this.genderPr=genderPr;
        this.racePr=racePr;
        this.religionPr=religionPr;
        this.nationalityPr=nationalityPr;
        this.addPr=addPr;
        this.pcPr=pcPr;
        this.statePr=statePr;
        this.districtPr=districtPr;
        this.telPr=telPr;
        this.job=job;
        this.salary=salary;

        this.type=type;
        this.stateSc=stateSc;
        this.districtSc=districtSc;
        this.nameSc=nameSc;
        this.distance=distance;
        this.status=status;

    }

    //getter function
    public String getStICNo() {
        return ICSt;
    }

    public String getStName() {
        return nameSt;
    }

    public String getgenderSt() {
        return genderSt;
    }
    public String getraceSt() {
        return raceSt;
    }
    public String getreligionSt() {
        return religionSt;
    }
    public String getnationalitySt() {
        return nationalitySt;
    }
    public String getaddSt() {
        return addSt;
    }
    public String getpcSt() {
        return pcSt;
    }
    public String getstateSt() {
        return stateSt;
    }
    public String getdistrictSt() {
        return districtSt;
    }
    public String gettelSt() {
        return telSt;
    }

    public String getPrICNo() {
        return ICPr;
    }
    public String getPrName() {
        return namePr;
    }
    public String getgenderPr() {
        return genderPr;
    }
    public String getracePr() {
        return racePr;
    }
    public String getreligionPr() {
        return religionPr;
    }
    public String getnationalityPr() {
        return nationalityPr;
    }
    public String getaddPr() {
        return addPr;
    }
    public String getpcPr() {
        return pcPr;
    }
    public String getstatePr() {
        return statePr;
    }
    public String getdistrictPr() {
        return districtPr;
    }
    public String gettelPr() {
        return telPr;
    }
    public String getjob() {
        return job;
    }
    public String getsalary() {
        return salary;
    }

    public String gettype() {
        return type;
    }
    public String getstateSc() {
        return stateSc;
    }
    public String getDistrictSc() {
        return districtSc;
    }
    public String getnameSc() {
        return nameSc;
    }
    public String getdistance() {
        return distance;
    }
    public String getstatus() {
        return status;
    }









}
