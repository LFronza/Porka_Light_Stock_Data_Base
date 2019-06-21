import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IGrupo } from 'app/shared/model/grupo.model';
import { GrupoService } from './grupo.service';

@Component({
    selector: 'jhi-grupo-update',
    templateUrl: './grupo-update.component.html'
})
export class GrupoUpdateComponent implements OnInit {
    grupo: IGrupo;
    isSaving: boolean;

    constructor(protected grupoService: GrupoService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ grupo }) => {
            this.grupo = grupo;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.grupo.id !== undefined) {
            this.subscribeToSaveResponse(this.grupoService.update(this.grupo));
        } else {
            this.subscribeToSaveResponse(this.grupoService.create(this.grupo));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrupo>>) {
        result.subscribe((res: HttpResponse<IGrupo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
