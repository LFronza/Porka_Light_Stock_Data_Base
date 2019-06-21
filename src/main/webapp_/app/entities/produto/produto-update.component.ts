import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from './produto.service';
import { IApresentacao } from 'app/shared/model/apresentacao.model';
import { ApresentacaoService } from 'app/entities/apresentacao';
import { IGrupo } from 'app/shared/model/grupo.model';
import { GrupoService } from 'app/entities/grupo';

@Component({
    selector: 'jhi-produto-update',
    templateUrl: './produto-update.component.html'
})
export class ProdutoUpdateComponent implements OnInit {
    produto: IProduto;
    isSaving: boolean;

    apresentacaos: IApresentacao[];

    grupos: IGrupo[];
    dtVencimentoDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected produtoService: ProdutoService,
        protected apresentacaoService: ApresentacaoService,
        protected grupoService: GrupoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ produto }) => {
            this.produto = produto;
        });
        this.apresentacaoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IApresentacao[]>) => mayBeOk.ok),
                map((response: HttpResponse<IApresentacao[]>) => response.body)
            )
            .subscribe((res: IApresentacao[]) => (this.apresentacaos = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.grupoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IGrupo[]>) => mayBeOk.ok),
                map((response: HttpResponse<IGrupo[]>) => response.body)
            )
            .subscribe((res: IGrupo[]) => (this.grupos = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.produto.id !== undefined) {
            this.subscribeToSaveResponse(this.produtoService.update(this.produto));
        } else {
            this.subscribeToSaveResponse(this.produtoService.create(this.produto));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduto>>) {
        result.subscribe((res: HttpResponse<IProduto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackApresentacaoById(index: number, item: IApresentacao) {
        return item.id;
    }

    trackGrupoById(index: number, item: IGrupo) {
        return item.id;
    }
}
