import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILote } from 'app/shared/model/lote.model';

@Component({
    selector: 'jhi-lote-detail',
    templateUrl: './lote-detail.component.html'
})
export class LoteDetailComponent implements OnInit {
    lote: ILote;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ lote }) => {
            this.lote = lote;
        });
    }

    previousState() {
        window.history.back();
    }
}
