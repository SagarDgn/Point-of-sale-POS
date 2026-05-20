package com.puff.tech.usecase.expenses.delete;

import com.puff.tech.core.usecases.UseCase;
import com.puff.tech.core.utils.HelperUtils;
import com.puff.tech.repository.ExpensesRepository;
import com.puff.tech.service.implementation.KhataBookImplementation;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

@Singleton
public class DeleteExpensesUseCase implements UseCase<DeleteExpensesUseCaseRequest, DeleteExpensesUseCaseResponse> {

    private final ExpensesRepository repository;
    private final KhataBookImplementation khataBookImplementation;
    private final HelperUtils helperUtils;

    public DeleteExpensesUseCase(ExpensesRepository repository,
                                 KhataBookImplementation khataBookImplementation,
                                 HelperUtils helperUtils) {
        this.repository = repository;
        this.khataBookImplementation = khataBookImplementation;
        this.helperUtils = helperUtils;
    }

    @Override
    public Mono<DeleteExpensesUseCaseResponse> execute(DeleteExpensesUseCaseRequest request) {
        return khataBookImplementation.getCurrentKhataBookId()
                .flatMap(khataBookId ->
                        repository.findById(request.id())
                                .switchIfEmpty(Mono.error(new RuntimeException("Expense not found")))
                                .flatMap(expense -> {
                                    // Delete photo if exists
                                    if (expense.getPhotoPath() != null) {
                                        helperUtils.deleteFile(expense.getPhotoPath());
                                    }
                                    // Delete expense
                                    return repository.delete(expense)
                                            .then(Mono.just(new DeleteExpensesUseCaseResponse("Deleted Successfully")));
                                })
                );
    }
}
