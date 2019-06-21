import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ILote } from 'app/shared/model/lote.model';
import { LoteService } from './lote.service';
import { IFornecedor } from 'app/shared/model/fornecedor.model';
import { FornecedorService } from 'app/entities/fornecedor';
import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from 'app/entities/produto';

@Component({
    selector: 'jhi-lote-update',
    templateUrl: './lote-update.component.html'
})
export class LoteUpdateComponent implements OnInit {
    lote: ILote;
    isSaving: boolean;

    fornecedors: IFornecedor[];

    produtos: IProduto[];
    dtEntradaDp: any;
    dtVencimentoDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected loteService: LoteService,
        protected fornecedorService: FornecedorService,
        protected produtoService: ProdutoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lote }) => {
            this.lote = lote;
        });
        this.fornecedorService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFornecedor[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFornecedor[]>) => response.body)
            )
            .subscribe((res: IFornecedor[]) => (this.fornecedors = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.produtoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProduto[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProduto[]>) => response.body)
            )
            .subscribe((res: IProduto[]) => (this.produtos = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.lote.id !== undefined) {
            this.subscribeToSaveResponse(this.loteService.update(this.lote));
        } else {
            this.subscribeToSaveResponse(this.loteService.create(this.lote));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ILote>>) {
        result.subscribe((res: HttpResponse<ILote>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackFornecedorById(index: number, item: IFornecedor) {
        return item.id;
    }

    trackProdutoById(index: number, item: IProduto) {
        return item.id;
    }
}
