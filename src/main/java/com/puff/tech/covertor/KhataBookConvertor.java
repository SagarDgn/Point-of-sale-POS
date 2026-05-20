package com.puff.tech.covertor;

import com.puff.tech.entity.KhataBookEntity;
import com.puff.tech.usecase.khatabook.create.CreateKhataBookUseCaseRequest;
import com.puff.tech.usecase.khatabook.findall.GetKhataBookUseCaseResponse;
import com.puff.tech.usecase.khatabook.update.UpdateKhataBookUseCaseRequest;
import io.micronaut.http.annotation.Get;


public class KhataBookConvertor {
    private KhataBookConvertor(){}

    public static KhataBookEntity toEntity(CreateKhataBookUseCaseRequest request){
        KhataBookEntity khataBookEntity= new KhataBookEntity();
        khataBookEntity.setName(request.name());
        khataBookEntity.setNumber(request.number());
        khataBookEntity.setAddress(request.address());
        khataBookEntity.setEmail(request.email());
        khataBookEntity.setCompanyName(request.companyName());
        khataBookEntity.setCompanyNumber(request.companyNumber());
        khataBookEntity.setCompanyAddress(request.companyAddress());
        khataBookEntity.setCompanyEmail(request.companyEmail());
        khataBookEntity.setBusinesscategory(request.businessCategory());
        khataBookEntity.setBusinessType(request.businessType());
        khataBookEntity.setTaxVat(request.taxVat());
        khataBookEntity.setBookAccount(String.valueOf(request.bookAccount()));
        khataBookEntity.setKyc(request.kyc());
        khataBookEntity.setImagePath(request.imagePath());

        return khataBookEntity;
    }

    public static GetKhataBookUseCaseResponse getAllKhataBook(KhataBookEntity khataBookEntity){
        return new GetKhataBookUseCaseResponse(
                khataBookEntity.getName(),
                khataBookEntity.getNumber(),
                khataBookEntity.getAddress(),
                khataBookEntity.getEmail(),
                khataBookEntity.getCompanyName(),
                khataBookEntity.getCompanyNumber(),
                khataBookEntity.getCompanyAddress(),
                khataBookEntity.getCompanyEmail(),
                khataBookEntity.getBusinesscategory(),
                khataBookEntity.getBusinessType(),
                khataBookEntity.isTaxVat(),
                khataBookEntity.getBookAccount(),
                khataBookEntity.isKyc(),
                khataBookEntity.getImagePath()
        );
    }

    public static KhataBookEntity toUpdateEntity( UpdateKhataBookUseCaseRequest request){
        KhataBookEntity khataBookEntity= new KhataBookEntity();
        khataBookEntity.setName(request.name());
        khataBookEntity.setNumber(request.number());
        khataBookEntity.setAddress(request.address());
        khataBookEntity.setEmail(request.email());
        khataBookEntity.setCompanyName(request.companyName());
        khataBookEntity.setCompanyNumber(request.companyNumber());
        khataBookEntity.setCompanyAddress(request.companyAddress());
        khataBookEntity.setCompanyEmail(request.companyEmail());
        khataBookEntity.setBusinesscategory(request.businessCategory());
        khataBookEntity.setBusinessType(request.businessType());
        khataBookEntity.setTaxVat(request.taxVat());
        khataBookEntity.setBookAccount(String.valueOf(request.bookAccount()));
        khataBookEntity.setKyc(request.kyc());
        khataBookEntity.setImagePath(request.imagePath());

        return khataBookEntity;
    }
}
