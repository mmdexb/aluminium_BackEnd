package com.hyper.aluminium.service;



public interface CertService {
    String addCert(String cid, String pwd, String level);

    Object delCert(String cid);

    Object modCertLevel(String cid, String level);

    String addCertToTXT(String cid, String level,String password);
}
