package com.puff.tech.covertor;

import com.puff.tech.entity.ExpensesEntity;
import com.puff.tech.usecase.expenses.create.CreateExpensesUseCaseRequest;
import com.puff.tech.usecase.expenses.getall.GetAllExpensesBookUseCaseResponse;
import com.puff.tech.usecase.expenses.update.UpdateExpensesUseCaseRequest;

import java.time.Instant;

public class ExpensesConvertor {
    private ExpensesConvertor(){}

    public static ExpensesEntity toEntity(CreateExpensesUseCaseRequest request,
                                          Integer khataBookId,String photoPath){
        ExpensesEntity expenses= new ExpensesEntity();
        expenses.setKhataBookId(khataBookId);
        expenses.setExpensesNo(request.expensesNo());
        expenses.setDate(request.date());
        expenses.setCategoryId(request.categoryId());
        expenses.setItemId(request.itemId());
        expenses.setPaymentMode(request.paymentMode());
        expenses.setAmount(request.amount());
        expenses.setPhotoPath(photoPath);
        expenses.setRemarks(request.remarks());
        expenses.setCreatedAt(Instant.now());
        return expenses;
    }

    public static GetAllExpensesBookUseCaseResponse response(ExpensesEntity expenses){
        return new GetAllExpensesBookUseCaseResponse(
                expenses.getId(),
                expenses.getExpensesNo(),
                expenses.getDate(),
                expenses.getCategoryId(),
                expenses.getItemId(),
                expenses.getPaymentMode(),
                expenses.getAmount(),
                expenses.getRemarks(),
                expenses.getPhotoPath(),
                expenses.getCreatedAt()
        );
    }

    public static ExpensesEntity toEntityForUpdate(ExpensesEntity existing,
                                                   UpdateExpensesUseCaseRequest request,
                                                   String photoPath) {

        existing.setExpensesNo(request.expensesNo());
        existing.setDate(request.date());
        existing.setCategoryId(request.categoryId());
        existing.setItemId(request.itemId());
        existing.setPaymentMode(request.paymentMode());
        existing.setAmount(request.amount());
        existing.setRemarks(request.remarks());
        existing.setPhotoPath(photoPath);

        return existing;
    }

}

