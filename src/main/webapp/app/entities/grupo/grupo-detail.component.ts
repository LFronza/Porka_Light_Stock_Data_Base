import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGrupo } from 'app/shared/model/grupo.model';

@Component({
    selector: 'jhi-grupo-detail',
    templateUrl: './grupo-detail.component.html'
})
export class GrupoDetailComponent implements OnInit {
    grupo: IGrupo;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ grupo }) => {
            this.grupo = grupo;
        });
    }

    previousState() {
        window.history.back();
    }
}
