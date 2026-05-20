package com.puff.tech.usecase.expenses.update;

import com.puff.tech.core.usecases.UseCase;
import com.puff.tech.core.utils.HelperUtils;
import com.puff.tech.covertor.ExpensesConvertor;
import com.puff.tech.entity.ExpensesEntity;
import com.puff.tech.repository.ExpensesRepository;
import com.puff.tech.service.implementation.KhataBookImplementation;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

@Singleton
public class UpdateExpensesUseCase implements UseCase<UpdateExpensesUseCaseRequest, UpdateExpensesUseCaseResponse> {

    private final ExpensesRepository repository;
    private final KhataBookImplementation khataBookImplementation;
    private final HelperUtils helperUtils;

    public UpdateExpensesUseCase(ExpensesRepository repository,
                                 KhataBookImplementation khataBookImplementation,
                                 HelperUtils helperUtils) {
        this.repository = repository;
        this.khataBookImplementation = khataBookImplementation;
        this.helperUtils = helperUtils;
    }

    @Override
    public Mono<UpdateExpensesUseCaseResponse> execute(UpdateExpensesUseCaseRequest request) {

        return khataBookImplementation.getCurrentKhataBookId()
                .flatMap(khataBookId ->
                        repository.findById(request.id())
                                .switchIfEmpty(Mono.error(new RuntimeException("Expense not found")))
                                .flatMap(existing -> {

                                    Mono<String> photoPathMono;

                                    if (request.photo() != null) {
                                        photoPathMono = Mono.fromCallable(() -> {
                                            // Delete old photo
                                            if (existing.getPhotoPath() != null) {
                                                helperUtils.deleteFile(existing.getPhotoPath());
                                            }
                                            // Upload new photo
                                            return helperUtils.uploadFile(request.photo());
                                        });
                                    } else {
                                        photoPathMono = Mono.justOrEmpty(existing.getPhotoPath());
                                    }

                                    return photoPathMono.flatMap(photoPath -> {
                                        // Use converter to map all fields
                                        ExpensesEntity updated = ExpensesConvertor.toEntityForUpdate(existing, request, photoPath);
                                        return repository.save(updated)
                                                .map(e -> new UpdateExpensesUseCaseResponse("Updated Successfully"));
                                    });

                                })
                );
    }
}