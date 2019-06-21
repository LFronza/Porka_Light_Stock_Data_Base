import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISaida } from 'app/shared/model/saida.model';

@Component({
    selector: 'jhi-saida-detail',
    templateUrl: './saida-detail.component.html'
})
export class SaidaDetailComponent implements OnInit {
    saida: ISaida;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ saida }) => {
            this.saida = saida;
        });
    }

    previousState() {
        window.history.back();
    }
}
