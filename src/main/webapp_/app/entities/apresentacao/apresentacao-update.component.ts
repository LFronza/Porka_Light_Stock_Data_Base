import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IApresentacao } from 'app/shared/model/apresentacao.model';
import { ApresentacaoService } from './apresentacao.service';

@Component({
    selector: 'jhi-apresentacao-update',
    templateUrl: './apresentacao-update.component.html'
})
export class ApresentacaoUpdateComponent implements OnInit {
    apresentacao: IApresentacao;
    isSaving: boolean;

    constructor(protected apresentacaoService: ApresentacaoService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ apresentacao }) => {
            this.apresentacao = apresentacao;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.apresentacao.id !== undefined) {
            this.subscribeToSaveResponse(this.apresentacaoService.update(this.apresentacao));
        } else {
            this.subscribeToSaveResponse(this.apresentacaoService.create(this.apresentacao));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IApresentacao>>) {
        result.subscribe((res: HttpResponse<IApresentacao>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
