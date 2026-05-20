package com.puff.tech.controller;

import com.puff.tech.core.responses.RestResponse;
import com.puff.tech.core.utils.HelperUtils;
import com.puff.tech.usecase.khatabook.create.CreateKhataBookUseCase;
import com.puff.tech.usecase.khatabook.create.CreateKhataBookUseCaseRequest;
import com.puff.tech.usecase.khatabook.create.CreateKhataBookUseCaseResponse;
import com.puff.tech.usecase.khatabook.delete.DeleteKhataBookUseCase;
import com.puff.tech.usecase.khatabook.delete.DeleteKhataBookUseCaseResponse;
import com.puff.tech.usecase.khatabook.findall.GetKhataBookUseCase;
import com.puff.tech.usecase.khatabook.findall.GetKhataBookUseCaseResponse;
import com.puff.tech.usecase.khatabook.findbyid.GetSingleKhataBookUseCase;
import com.puff.tech.usecase.khatabook.selected.SelectedKhataBookUseCase;
import com.puff.tech.usecase.khatabook.selected.SelectedKhataBookUseCaseResponse;
import com.puff.tech.usecase.khatabook.switchkb.SwitchKhataBookUseCase;
import com.puff.tech.usecase.khatabook.switchkb.SwitchKhataBookUseCaseResponse;
import com.puff.tech.usecase.khatabook.update.UpdateKhataBookUseCase;
import com.puff.tech.usecase.khatabook.update.UpdateKhataBookUseCaseRequest;
import com.puff.tech.usecase.khatabook.update.UpdateKhataBookUseCaseResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Inject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Controller("/api/v1")
public class KhataBookController {

    private final CreateKhataBookUseCase createKhataBookUseCase;
    private final GetKhataBookUseCase getKhataBookUseCase;
    private final GetSingleKhataBookUseCase getSingleKhataBookUseCase;
    private final HelperUtils helperUtils;
    private final DeleteKhataBookUseCase deleteKhataBookUseCase;
    private final SwitchKhataBookUseCase switchKhataBookUseCase;
    private final SelectedKhataBookUseCase selectedKhataBookUseCase;
    private final UpdateKhataBookUseCase updateKhataBookUseCase;

    @Inject
    public KhataBookController(CreateKhataBookUseCase createKhataBookUseCase,
                               GetKhataBookUseCase getKhataBookUseCase,
                               GetSingleKhataBookUseCase getSingleKhataBookUseCase,
                               HelperUtils helperUtils,
                               DeleteKhataBookUseCase deleteKhataBookUseCase,
                               SwitchKhataBookUseCase switchKhataBookUseCase,
                               SelectedKhataBookUseCase selectedKhataBookUseCase,
                               UpdateKhataBookUseCase updateKhataBookUseCase){
        this.createKhataBookUseCase=createKhataBookUseCase;
        this.getKhataBookUseCase=getKhataBookUseCase;
        this.getSingleKhataBookUseCase=getSingleKhataBookUseCase;
        this.helperUtils=helperUtils;
        this.deleteKhataBookUseCase=deleteKhataBookUseCase;
        this.switchKhataBookUseCase=switchKhataBookUseCase;
        this.selectedKhataBookUseCase=selectedKhataBookUseCase;
        this.updateKhataBookUseCase=updateKhataBookUseCase;
    }

    @Post( value = "/khatabook", consumes = "multipart/form-data")
    public Mono<RestResponse<CreateKhataBookUseCaseResponse>> create(@Part CreateKhataBookUseCaseRequest request,
                                                                      @Part(value = "imageFile") CompletedFileUpload imageFile) throws IOException {

        String imagePath = null;

        if (imageFile != null) {
            imagePath = helperUtils.uploadFile(imageFile);
        }
        return createKhataBookUseCase.execute(request)
                .map(RestResponse::success)
                .onErrorResume(err->Mono.just(RestResponse.error("Unexpected happened"+err.getLocalizedMessage())));
    }

    @Get("/khatabooks")
    public Flux<RestResponse<GetKhataBookUseCaseResponse>> getAll(){
        return getKhataBookUseCase.execute()
                .map(RestResponse::success)
                .onErrorResume(err->Flux.just(RestResponse.error("Unexpected happened"+err.getLocalizedMessage())));
    }

    @Get("/khatabook/{id}")
    public Mono<RestResponse<GetKhataBookUseCaseResponse>> getById(@PathVariable Integer id){
        return getSingleKhataBookUseCase.execute(id)
                .map(RestResponse::success)
                .onErrorResume(err->Mono.just(RestResponse.error("Unexpected happened"+err.getLocalizedMessage())));
    }

    @Delete("/khatabook/{id}")
    public Mono<RestResponse<DeleteKhataBookUseCaseResponse>> delete(@PathVariable Integer id){
        return deleteKhataBookUseCase.execute(id)
                .map(RestResponse::success)
                .onErrorResume(err->Mono.just(RestResponse.error("Unexpected happened"+ err.getLocalizedMessage())));
    }

    @Post("/khatabook/{id]")
    public Mono<RestResponse<SwitchKhataBookUseCaseResponse>> switchKhataBook( Integer id){
        return switchKhataBookUseCase.execute(id)
                .map(RestResponse::success)
                .onErrorResume(err->Mono.just(RestResponse.error("Unexpected happened"+err.getLocalizedMessage())));
    }

    @Get("/khatabook/selected")
    public Mono<RestResponse<SelectedKhataBookUseCaseResponse>> selected(){
        return selectedKhataBookUseCase.execute()
                .map(RestResponse::success)
                .onErrorResume(err->Mono.just(RestResponse.error("Unexpected happened" +err.getLocalizedMessage())));
    }

    @Put("/khatabook/{id}")
    public Mono<RestResponse<UpdateKhataBookUseCaseResponse>> update(@Body UpdateKhataBookUseCaseRequest request,
                                                                     @PathVariable Integer id){
        return updateKhataBookUseCase.execute(request, id)
                .map(RestResponse::success)
                .onErrorResume(err->Mono.just(RestResponse.error("Unexpected happened" +err.getLocalizedMessage())));
    }

}
