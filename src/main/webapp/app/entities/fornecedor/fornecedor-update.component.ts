import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IFornecedor } from 'app/shared/model/fornecedor.model';
import { FornecedorService } from './fornecedor.service';

@Component({
    selector: 'jhi-fornecedor-update',
    templateUrl: './fornecedor-update.component.html'
})
export class FornecedorUpdateComponent implements OnInit {
    fornecedor: IFornecedor;
    isSaving: boolean;

    constructor(protected fornecedorService: FornecedorService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ fornecedor }) => {
            this.fornecedor = fornecedor;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.fornecedor.id !== undefined) {
            this.subscribeToSaveResponse(this.fornecedorService.update(this.fornecedor));
        } else {
            this.subscribeToSaveResponse(this.fornecedorService.create(this.fornecedor));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFornecedor>>) {
        result.subscribe((res: HttpResponse<IFornecedor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
