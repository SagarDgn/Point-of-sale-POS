package com.puff.tech.usecase.cashbook.create;

import com.puff.tech.core.utils.HelperUtils;
import com.puff.tech.covertor.CashBookConvertor;
import com.puff.tech.entity.CashBookEntity;
import com.puff.tech.repository.CashBookRepository;
import com.puff.tech.repository.CategoryRepository;
import com.puff.tech.repository.ItemRepository;
import com.puff.tech.service.implementation.KhataBookImplementation;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Singleton
public class CreateCashBookUseCase {

    private final CashBookRepository cashBookRepository;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final HelperUtils helperUtils;
    private final KhataBookImplementation khataBookImplementation;

    @Inject
    public CreateCashBookUseCase(CashBookRepository cashBookRepository,
                                 CategoryRepository categoryRepository,
                                 ItemRepository itemRepository,
                                 HelperUtils helperUtils,
                                 KhataBookImplementation khataBookImplementation) {
        this.cashBookRepository = cashBookRepository;
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
        this.helperUtils = helperUtils;
        this.khataBookImplementation = khataBookImplementation;
    }

    public Mono<CreateCashBookUseCaseResponse> execute(CreateCashBookUseCaseRequest request) throws IOException {

        return khataBookImplementation.getCurrentKhataBookId()
                .flatMap(khataBookId ->


                        itemRepository.findById(Math.toIntExact(request.itemId()))
                                .switchIfEmpty(Mono.error(new RuntimeException("Item not found")))


                                .then(categoryRepository.findById(Math.toIntExact(request.categoryId())))
                                .switchIfEmpty(Mono.error(new RuntimeException("Category not found")))

                                .flatMap(category ->
                                        (request.photo() != null
                                                ? Mono.fromCallable(() -> helperUtils.uploadFile(request.photo()))
                                                : Mono.just((String) null))
                                )

                                .flatMap(photoPath -> {
                                    CashBookEntity cashBook =
                                            CashBookConvertor.toEntity(request, Long.valueOf(khataBookId), photoPath);

                                    return cashBookRepository.save(cashBook);
                                })


                                .map(saved -> new CreateCashBookUseCaseResponse(
                                        saved.getId(),
                                        "Cashbook created successfully"
                                ))
                );
    }
}