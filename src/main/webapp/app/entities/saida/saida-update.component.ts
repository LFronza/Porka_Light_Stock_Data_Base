import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISaida } from 'app/shared/model/saida.model';
import { SaidaService } from './saida.service';
import { IProduto } from 'app/shared/model/produto.model';
import { ProdutoService } from 'app/entities/produto';

@Component({
    selector: 'jhi-saida-update',
    templateUrl: './saida-update.component.html'
})
export class SaidaUpdateComponent implements OnInit {
    saida: ISaida;
    isSaving: boolean;

    produtos: IProduto[];
    dtSaidaDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected saidaService: SaidaService,
        protected produtoService: ProdutoService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ saida }) => {
            this.saida = saida;
        });
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
        if (this.saida.id !== undefined) {
            this.subscribeToSaveResponse(this.saidaService.update(this.saida));
        } else {
            this.subscribeToSaveResponse(this.saidaService.create(this.saida));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISaida>>) {
        result.subscribe((res: HttpResponse<ISaida>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProdutoById(index: number, item: IProduto) {
        return item.id;
    }
}
