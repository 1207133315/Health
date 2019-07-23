package com.bw.health.bean;

import java.util.List;

/**
 * com.bw.health.bean
 *
 * @author 李宁康
 * @date 2019 2019/07/22 17:18
 */
public class SearchBean {
    public List<DiseaseItme> diseaseSearchVoList;
    public List<DoctorItme> doctorSearchVoList;
    public List<DrugsItme> drugsSearchVoList;

    public class DiseaseItme{
        public long diseaseId;
        public String diseaseName;
    }
    public class DoctorItme{
        public long doctorId;
        public String doctorName;
    }
    public class DrugsItme{
        public long drugsId;
        public String drugsName;
    }

}
