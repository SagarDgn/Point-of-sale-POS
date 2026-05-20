package com.puff.tech.covertor;

import com.puff.tech.entity.CashBookEntity;
import com.puff.tech.usecase.cashbook.create.CreateCashBookUseCaseRequest;
import com.puff.tech.usecase.cashbook.update.UpdateCashBookUseCaseRequest;

import java.time.Instant;

public class CashBookConvertor {

    private CashBookConvertor(){}

    public static CashBookEntity toEntity(CreateCashBookUseCaseRequest req, Long khataBookId, String photoPath) {
        CashBookEntity cashBookEntity = new CashBookEntity();

        cashBookEntity.setKhataBookId(Math.toIntExact(khataBookId));
        cashBookEntity.setCashBookNo(req.cashbookNo());
        cashBookEntity.setDate(req.date());
        cashBookEntity.setCategoryId(Math.toIntExact(req.categoryId()));
        cashBookEntity.setItemId(Math.toIntExact(req.itemId()));
        cashBookEntity.setPaymentMode(req.paymentMode());
        cashBookEntity.setAmount(req.amount());
        cashBookEntity.setRemarks(req.remarks());
        cashBookEntity.setPhotoPath(photoPath);
        cashBookEntity.setCreatedAt(Instant.now());

        return cashBookEntity;
    }

    public static CashBookEntity updateEntity(CashBookEntity existing,
                                              UpdateCashBookUseCaseRequest request,
                                              String photoPath) {

        existing.setCashBookNo(request.cashbookNo());
        existing.setDate(request.date());
        existing.setCategoryId(Math.toIntExact(request.categoryId()));
        existing.setItemId(Math.toIntExact(request.itemId()));
        existing.setPaymentMode(request.paymentMode());
        existing.setAmount(request.amount());
        existing.setRemarks(request.remarks());

        if (photoPath != null) {
            existing.setPhotoPath(photoPath);
        }


        return existing;
    }
}
